
package com.tracker.models.series;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images implements Parcelable
{

    @SerializedName("backdrops")
    @Expose
    public List<Object> backdrops = null;
    @SerializedName("posters")
    @Expose
    public List<Object> posters = null;
    public final static Creator<Images> CREATOR = new Creator<Images>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        public Images[] newArray(int size) {
            return (new Images[size]);
        }

    }
    ;

    protected Images(Parcel in) {
        in.readList(this.backdrops, (Object.class.getClassLoader()));
        in.readList(this.posters, (Object.class.getClassLoader()));
    }

    public Images() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(backdrops);
        dest.writeList(posters);
    }

    public int describeContents() {
        return  0;
    }

}
