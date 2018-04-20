package com.customcalendar;

import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ain043 on 4/18/2018 at 8:25 AM
 */

public class CommonUtils {
    private static ProgressDialog mProgressDialog;

    public static void showLoadingDialog(Context context) {
        showLoadingDialog(context, "Please wait...", false);
    }

    public static void showLoadingDialog(Context context, String message) {
        showLoadingDialog(context, message, false);
    }

    public static void showLoadingDialog(Context context, String title, boolean isCancelable) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {

        } else {
            try {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage(title);
//            mProgressDialog.setProgressDrawable(context.getResources().getDrawable(R.drawable.app_progress));
                mProgressDialog.setCancelable(isCancelable);
                mProgressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void hideLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public static boolean checkInternetConnection(Context context) {
        NetworkInfo _activeNetwork = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return _activeNetwork != null && _activeNetwork.isConnectedOrConnecting();
    }

    public static int getNoOfDaysOfMonth(int month, int year) {
        switch (month % 2) {
            case 1:
                return 31;
            case 0:
                if (month == 2) {
                    if (year % 4 == 0) {
                        return 29;
                    } else {
                        return 28;
                    }
                } else {
                    return 30;
                }
        }
        return 0;
    }

    public static String getDayName(int i) {
        String day = "";
        switch (i) {
            case 1:
                day = "Sun";
                break;
            case 2:
                day = "Mon";
                break;
            case 3:
                day = "Tue";
                break;
            case 4:
                day = "Wed";
                break;
            case 5:
                day = "Thu";
                break;
            case 6:
                day = "Fri";
                break;
            case 7:
                day = "Sat";
                break;
        }
        return day;
    }

//    public static void scheduleJob(Context context) {
//        ComponentName serviceComponent = new ComponentName(context, TestJobService.class);
//        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
//        builder.setMinimumLatency(1 * 1000); // wait at least
//        builder.setOverrideDeadline(3 * 1000); // maximum delay
//        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
//        //builder.setRequiresDeviceIdle(true); // device should be idle
//        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
//        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
//        jobScheduler.schedule(builder.build());
//    }
}
