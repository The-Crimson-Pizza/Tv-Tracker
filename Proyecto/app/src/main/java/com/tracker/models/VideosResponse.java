
package com.tracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideosResponse {

    @SerializedName("results")
    @Expose
    public final List<Video> results = null;


    public static class Video implements Parcelable {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("site")
        @Expose
        public String site;
        @SerializedName("type")
        @Expose
        public String type;

        public final Parcelable.Creator<Video> CREATOR = new Creator<Video>() {

            public Video createFromParcel(Parcel in) {
                return new Video(in);
            }

            public Video[] newArray(int size) {
                return (new Video[size]);
            }

        };

        protected Video(Parcel in) {
            this.id = ((String) in.readValue((String.class.getClassLoader())));
            this.key = ((String) in.readValue((String.class.getClassLoader())));
            this.name = ((String) in.readValue((String.class.getClassLoader())));
            this.site = ((String) in.readValue((String.class.getClassLoader())));
            this.type = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Video() {
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(key);
            dest.writeValue(name);
            dest.writeValue(site);
            dest.writeValue(type);
        }

        public int describeContents() {
            return 0;
        }
    }
}
