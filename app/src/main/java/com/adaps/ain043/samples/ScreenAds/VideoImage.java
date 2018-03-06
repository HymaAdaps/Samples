package com.adaps.ain043.samples.ScreenAds;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ain043 on 3/5/2018 at 1:53 PM
 */

public class VideoImage implements Parcelable {
    public String name, contentType, tileId, timeToDisplay, url, startAtTime;

    protected VideoImage(Parcel in) {
        name = in.readString();
        contentType = in.readString();
        tileId = in.readString();
        timeToDisplay = in.readString();
        url = in.readString();
        startAtTime = in.readString();
    }

    public static final Creator<VideoImage> CREATOR = new Creator<VideoImage>() {
        @Override
        public VideoImage createFromParcel(Parcel in) {
            return new VideoImage(in);
        }

        @Override
        public VideoImage[] newArray(int size) {
            return new VideoImage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(contentType);
        dest.writeString(tileId);
        dest.writeString(timeToDisplay);
        dest.writeString(url);
        dest.writeString(startAtTime);
    }
}
