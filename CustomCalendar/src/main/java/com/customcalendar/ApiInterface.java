package com.customcalendar;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api/scheduler/usertaskspro?")
    Call<JsonElement> getCalendarPro(@Query("id") String userId, @Query("siteid") String siteId, @Query("startmonth") String startday);
}
