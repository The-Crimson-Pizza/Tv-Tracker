
package com.tracker.models.series;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credits implements Parcelable
{

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    public List<Object> crew = null;
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

    }
    ;

    protected Credits(Parcel in) {
        in.readList(this.cast, (Cast.class.getClassLoader()));
        in.readList(this.crew, (Object.class.getClassLoader()));
    }

    public Credits() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents() {
        return  0;
    }

}
