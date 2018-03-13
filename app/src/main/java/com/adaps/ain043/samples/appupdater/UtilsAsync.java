package com.adaps.ain043.samples.appupdater;

import android.content.Context;
import android.os.AsyncTask;

import com.adaps.ain043.samples.appupdater.enums.AppUpdaterError;
import com.adaps.ain043.samples.appupdater.objects.Update;

import java.lang.ref.WeakReference;

class UtilsAsync {

    static class LatestAppVersion extends AsyncTask<Void, Void, Update> {
        private WeakReference<Context> contextRef;
        private LibraryPreferences libraryPreferences;
        private Boolean fromUtils;
        private String updateFrom;
        private AppUpdater.LibraryListener listener;

        public LatestAppVersion(Context context, Boolean fromUtils, String updateFrom, AppUpdater.LibraryListener listener) {
            this.contextRef = new WeakReference<>(context);
            this.libraryPreferences = new LibraryPreferences(context);
            this.fromUtils = fromUtils;
            this.updateFrom = updateFrom;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Context context = contextRef.get();
            if (context == null || listener == null) {
                cancel(true);
            } else if (UtilsLibrary.isNetworkAvailable(context)) {
                if (!fromUtils && !libraryPreferences.getAppUpdaterShow()) {
                    cancel(true);
                } else {
                }
            } else {
                listener.onFailed(AppUpdaterError.NETWORK_NOT_AVAILABLE);
                cancel(true);
            }
        }

        @Override
        protected Update doInBackground(Void... voids) {
            try {
                Context context = contextRef.get();
                if (context != null) {
                    return UtilsLibrary.getLatestAppVersionHttp(context, updateFrom);
                } else {
                    cancel(true);
                    return null;
                }
            } catch (Exception ex) {
                cancel(true);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Update update) {
            super.onPostExecute(update);

            if (listener != null) {
                if (UtilsLibrary.isStringAVersion(update.getLatestVersion())) {
                    listener.onSuccess(update);
                } else {
                    listener.onFailed(AppUpdaterError.UPDATE_VARIES_BY_DEVICE);
                }
            }
        }
    }

}
