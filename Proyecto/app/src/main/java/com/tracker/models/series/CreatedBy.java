
package com.tracker.models.series;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedBy implements Parcelable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    public final static Creator<CreatedBy> CREATOR = new Creator<CreatedBy>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CreatedBy createFromParcel(Parcel in) {
            return new CreatedBy(in);
        }

        public CreatedBy[] newArray(int size) {
            return (new CreatedBy[size]);
        }

    }
    ;

    protected CreatedBy(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.creditId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((int) in.readValue((int.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CreatedBy() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(creditId);
        dest.writeValue(name);
        dest.writeValue(gender);
        dest.writeValue(profilePath);
    }

    public int describeContents() {
        return  0;
    }

}
