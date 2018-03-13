package com.adaps.ain043.samples.miracast.network;

import com.android.volley.VolleyError;

public interface RequestListener {
    void onRequestFail(VolleyError volleyError);

    void onRequestStart();

    void onRequestSuccess(String str);
}
