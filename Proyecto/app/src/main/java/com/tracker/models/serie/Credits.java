
package com.tracker.models.serie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credits implements Parcelable {

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;
    public final static Creator<Credits> CREATOR = new Creator<Credits>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Credits createFromParcel(Parcel in) {
            return new Credits(in);
        }

        public Credits[] newArray(int size) {
            return (new Credits[size]);
        }

    };

    protected Credits(Parcel in) {
        in.readList(this.cast, (Cast.class.getClassLoader()));
    }

    public Credits() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cast);
    }

    public int describeContents() {
        return 0;
    }

    public class Cast implements Parcelable {

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

        public final Creator<Cast> CREATOR = new Creator<Cast>() {

            @SuppressWarnings({
                    "unchecked"
            })
            public Cast createFromParcel(Parcel in) {
                return new Cast(in);
            }

            public Cast[] newArray(int size) {
                return (new Cast[size]);
            }

        };

        protected Cast(Parcel in) {
            this.character = ((String) in.readValue((String.class.getClassLoader())));
            this.id = ((int) in.readValue((int.class.getClassLoader())));
            this.name = ((String) in.readValue((String.class.getClassLoader())));
            this.gender = ((int) in.readValue((int.class.getClassLoader())));
            this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(character);
            dest.writeValue(id);
            dest.writeValue(name);
            dest.writeValue(gender);
            dest.writeValue(profilePath);
        }

        public int describeContents() {
            return 0;
        }
    }
}
