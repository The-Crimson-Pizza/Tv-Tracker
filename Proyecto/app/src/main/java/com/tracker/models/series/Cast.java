
package com.tracker.models.series;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast implements Parcelable
{

    @SerializedName("character")
    @Expose
    public String character;
    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    @SerializedName("order")
    @Expose
    public int order;
    public final static Creator<Cast> CREATOR = new Creator<Cast>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        public Cast[] newArray(int size) {
            return (new Cast[size]);
        }

    }
    ;

    protected Cast(Parcel in) {
        this.character = ((String) in.readValue((String.class.getClassLoader())));
        this.creditId = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((int) in.readValue((int.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Cast() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(character);
        dest.writeValue(creditId);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(gender);
        dest.writeValue(profilePath);
        dest.writeValue(order);
    }

    public int describeContents() {
        return  0;
    }

}
