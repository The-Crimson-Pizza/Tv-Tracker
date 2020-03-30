
package com.tracker.models.series;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Videos implements Parcelable
{

    @SerializedName("results")
    @Expose
    public List<Object> results = null;
    public final static Creator<Videos> CREATOR = new Creator<Videos>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        public Videos[] newArray(int size) {
            return (new Videos[size]);
        }

    }
    ;

    protected Videos(Parcel in) {
        in.readList(this.results, (Object.class.getClassLoader()));
    }

    public Videos() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
