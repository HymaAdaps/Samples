package com.customcalendar;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ain043 on 4/18/2018 at 8:19 AM
 */

public class CalendarApplication extends Application {
    public static ApiInterface apiInterfaceLocal;

    public String AppServer = "http://cic-test-srv1.azurewebsites.net/";
    public static int TIMEOUT = 125;

    @Override
    public void onCreate() {
        super.onCreate();

        apiInterfaceLocal = buildRetrofitClientLocal().create(ApiInterface.class);
    }

    private Retrofit buildRetrofitClientLocal() {
        return new Retrofit.Builder().baseUrl(AppServer)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient().newBuilder().
                        addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).
                        readTimeout(TIMEOUT, TimeUnit.SECONDS).
                        connectTimeout(TIMEOUT, TimeUnit.SECONDS).
                        writeTimeout(TIMEOUT, TimeUnit.SECONDS).
                        build()).build();
    }

    public static ApiInterface getWsClientListener() {
        return apiInterfaceLocal;
    }
}
