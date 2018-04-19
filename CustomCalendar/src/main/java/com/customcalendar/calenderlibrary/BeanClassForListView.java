package com.customcalendar.calenderlibrary;

/**
 * Created by admin on 3/22/2016.
 */
public class BeanClassForListView {
    private Integer id;
    private String title;
    private String description;
    private String startdate;
    private String starttime;
    private String enddate;
    private String endtime;
    private boolean recursive;
    //private String time;
    //private String task;
    private String profile_image;
    private String entrysourceid;
    private String entrysource;
    private String entrysourceurl;
    private boolean entrysourceeditable;


    public BeanClassForListView(int id, String profile_image, String title, String description,
                                String startdate, String starttime, String enddate, String endtime, boolean recursive,
                                String entrysourceid, String entrysource, String entrysourceurl, boolean entrysourceeditable) {
        this.id = id;
        this.profile_image = profile_image;
        this.title = title;
        this.description = description;
        this.startdate = startdate;
        this.starttime = starttime;
        this.enddate = enddate;
        this.endtime = endtime;
        this.recursive = recursive;
        this.entrysourceid = entrysourceid;
        this.entrysource = entrysource;
        this.entrysourceurl = entrysourceurl;
        this.entrysourceeditable = entrysourceeditable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String task) {
        this.description = task;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String time) {
        this.startdate = time;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String time) {
        this.starttime = time;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String date) {
        this.enddate = date;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String time) {
        this.endtime = time;
    }

    public boolean getRecursive() {
        return recursive;
    }

    public void setRecursive(boolean rec) {
        this.recursive = rec;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntrysourceid() {
        return entrysourceid;
    }

    public void setEntrysourceid(String entrysourceid) {
        this.entrysourceid = entrysourceid;
    }

    public String getEntrysource() {
        return entrysource;
    }

    public void setEntrysource(String entrysource) {
        this.entrysource = entrysource;
    }

    public String getEntrysourceurl() {
        return entrysourceurl;
    }

    public void setEntrysourceurl(String entrysourceurl) {
        this.entrysourceurl = entrysourceurl;
    }

    public boolean getEntrysourceeditable() {
        return entrysourceeditable;
    }

    public void setEntrysourceeditable(boolean entrysourceeditable) {
        this.entrysourceeditable = entrysourceeditable;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof BeanClassForListView) {
            BeanClassForListView ptr = (BeanClassForListView) v;
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



