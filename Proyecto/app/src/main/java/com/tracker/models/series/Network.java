
package com.tracker.models.series;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Network implements Parcelable
{

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("logo_path")
    @Expose
    public String logoPath;
    @SerializedName("origin_country")
    @Expose
    public String originCountry;
    public final static Creator<Network> CREATOR = new Creator<Network>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Network createFromParcel(Parcel in) {
            return new Network(in);
        }

        public Network[] newArray(int size) {
            return (new Network[size]);
        }

    }
    ;

    protected Network(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.logoPath = ((String) in.readValue((String.class.getClassLoader())));
        this.originCountry = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Network() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(id);
        dest.writeValue(logoPath);
        dest.writeValue(originCountry);
    }

    public int describeContents() {
        return  0;
    }

}
