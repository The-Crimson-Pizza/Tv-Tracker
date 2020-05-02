
package com.thecrimsonpizza.tvtracker.models.serie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Similar implements Serializable {

    @SerializedName("results")
    @Expose
    public List<Result> results = null;

    public Similar() {
//        Empty constructor for Firebase serialize
    }


    public static class Result implements Serializable {

        public int id;
        @SerializedName("original_name")
        public String originalName;
        public String name;
        public float popularity;

        public Result() {
//            Empty constructor for Firebase serialize
        }

    }
}
