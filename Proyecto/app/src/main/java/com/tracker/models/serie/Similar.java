
package com.tracker.models.serie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Similar implements Serializable {

    @SerializedName("results")
    @Expose
    public List<Result> results = null;

    public Similar() {
    }


    public static class Result implements Serializable {

        @SerializedName("original_name")
        @Expose
        public String originalName;
        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("popularity")
        @Expose
        public float popularity;

        public Result() {
        }

    }
}
