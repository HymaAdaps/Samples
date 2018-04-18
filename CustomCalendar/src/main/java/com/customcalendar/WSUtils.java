package com.customcalendar;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonElement;

import java.net.HttpURLConnection;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WSUtils {

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    public static final int REQ_GET_CALENDER_PRO = 100;

    public static void requestForJsonObject(final Context context, final int requestCode, Call<JsonElement> call, final IParserListener<JsonElement> iParserListener) {
        try {
            if (!CommonUtils.checkInternetConnection(context)) {
                iParserListener.noInternetConnection(requestCode);
            } else {
                Callback<JsonElement> callback = new Callback<JsonElement>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                        if (response.body() != null && /*response.body() instanceof JsonObject && */(response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_CREATED)) {
//                            iParserListener.successResponse(requestCode, response.body().getAsJsonObject());
                            if (response.body().isJsonObject()) {
                                iParserListener.successResponse(requestCode, response.body().getAsJsonObject());
                            } else {
                                iParserListener.successResponse(requestCode, response.body().getAsJsonArray());
                            }

                        } else if (requestCode == 100 && response.code() == HttpURLConnection.HTTP_CONFLICT)
                            iParserListener.errorResponse(requestCode, "error occured");
                        else
                            iParserListener.errorResponse(requestCode, response.message());
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable throwable) {
                        iParserListener.errorResponse(requestCode, throwable.toString());
                    }
                };
                call.enqueue(callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestForJsonObjectLicense(final Context context, final int requestCode, Call<JsonElement> call, final IParserListener<JsonElement> iParserListener) {
        try {
            if (!CommonUtils.checkInternetConnection(context)) {
                iParserListener.noInternetConnection(requestCode);
            } else {
                Callback<JsonElement> callback = new Callback<JsonElement>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                        if (response.body() != null && /*response.body() instanceof JsonObject && */(response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_CREATED)) {
//                            iParserListener.successResponse(requestCode, response.body().getAsJsonObject());
                            if (response.body().isJsonObject()) {
                                iParserListener.successResponse(requestCode, response.body().getAsJsonObject());
                            } else {
                                iParserListener.successResponse(requestCode, response.body().getAsJsonArray());
                            }

                        } else if (requestCode == 100 && response.code() == HttpURLConnection.HTTP_CONFLICT)
                            iParserListener.errorResponse(requestCode, "error occured");
                        else
                            iParserListener.errorResponse(requestCode, response.message());
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable throwable) {
                        iParserListener.errorResponse(requestCode, throwable.toString());
                    }
                };
                call.enqueue(callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}