package com.customcalendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vishn on 22/05/2017.
 */

public class SchedulerEvent {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("startdate")
    @Expose
    public String startdate;
    @SerializedName("enddate")
    @Expose
    public String enddate;
    @SerializedName("starttime")
    @Expose
    public String starttime;
    @SerializedName("endtime")
    @Expose
    public String endtime;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("imgurl")
    @Expose
    public String imgurl;
    @SerializedName("taskurl")
    @Expose
    public String taskurl;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("tasktype")
    @Expose
    public Boolean tasktype;
    @SerializedName("residentid")
    @Expose
    public String residentid;
    @SerializedName("siteid")
    @Expose
    public Integer siteid;
    @SerializedName("recursive")
    @Expose
    public Boolean recursive;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("entrysourceclientid")
    @Expose
    public String entrysourceclientid;
    @SerializedName("entrysourceid")
    @Expose
    public String entrysourceid;
    @SerializedName("entrysource")
    @Expose
    public String entrysource;
    @SerializedName("entrysourceurl")
    @Expose
    public String entrysourceurl;
    @SerializedName("entrysourceeditable")
    @Expose
    public Boolean entrysourceeditable;

    public long timeInMillis;
    public int type;
    public String date;

    SchedulerEvent(String date) {
        this.name = date;
        this.type = 3;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof SchedulerEvent) {
            SchedulerEvent ptr = (SchedulerEvent) v;
            retVal = ptr.id.longValue() == this.id;
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
