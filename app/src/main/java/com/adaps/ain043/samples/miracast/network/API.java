package com.adaps.ain043.samples.miracast.network;

import android.text.TextUtils;

import com.adaps.ain043.samples.miracast.CoreApplication;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import javax.xml.transform.ErrorListener;


public class API {
    final String TAG = "API";

    public void getApp(final RequestListener requestListener) {
//        StringRequest request = new StringRequest(0, FirebaseRemoteConfig.getInstance().getString("ABOUT_URL"), new Response.Listener<String>() {
//            public void onResponse(String response) {
//                CoreApplication.getInstance().tinyDB.putString("MYAPP", response);
//                requestListener.onRequestSuccess(response);
//            }
//        }, new ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.m15d("API", "Error: " + error.getMessage());
//                String cache = CoreApplication.getInstance().tinyDB.getString("MYAPP");
//                if (TextUtils.isEmpty(cache)) {
//                    requestListener.onRequestFail(error);
//                } else {
//                    requestListener.onRequestSuccess(cache);
//                }
//            }
//        });
//        requestListener.onRequestStart();
//        CoreApplication.getInstance().addToRequestQueue(request);
    }
}
