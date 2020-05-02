
package com.thecrimsonpizza.tvtracker.models.serie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Credits implements Serializable {

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;

    public Credits() {
//        Empty constructor for Firebase serialize
    }

    public static class Cast implements Serializable {

        public int id;
        public String name;
        public String character;
        public int gender;
        @SerializedName("profile_path")
        public String profilePath;

        public Cast() {
//            Empty constructor for Firebase serialize
        }
    }
}
