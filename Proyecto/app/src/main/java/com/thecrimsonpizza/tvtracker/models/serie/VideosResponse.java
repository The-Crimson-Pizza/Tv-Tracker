
package com.thecrimsonpizza.tvtracker.models.serie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideosResponse {

    @SerializedName("results")
    @Expose
    public final List<Video> results = null;

    public static class Video implements Serializable {

        private String key;
        private String site;
        private String type;

        public String getKey() {
            return key;
        }

        public String getSite() {
            return site;
        }

        public String getType() {
            return type;
        }

        public Video() {
//        Empty constructor for Firebase serialize
        }
    }
}
