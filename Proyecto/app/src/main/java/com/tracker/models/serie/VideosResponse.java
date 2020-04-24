
package com.tracker.models.serie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideosResponse {

    @SerializedName("results")
    @Expose
    public final List<Video> results = null;

    public static class Video implements Serializable {

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


        public Video() {
        }
    }
}
