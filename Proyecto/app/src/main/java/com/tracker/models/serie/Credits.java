
package com.tracker.models.serie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Credits implements Serializable {

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;

    public Credits() {
    }


    public static class Cast implements Serializable {

        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("character")
        @Expose
        public String character;
        @SerializedName("gender")
        @Expose
        public int gender;
        @SerializedName("profile_path")
        @Expose
        public String profilePath;

        public Cast() {

        }
    }
}
