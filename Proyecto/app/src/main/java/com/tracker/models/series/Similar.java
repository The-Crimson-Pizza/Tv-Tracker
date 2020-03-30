
package com.tracker.models.series;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Similar implements Parcelable
{

    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
    @SerializedName("total_results")
    @Expose
    public int totalResults;
    public final static Creator<Similar> CREATOR = new Creator<Similar>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Similar createFromParcel(Parcel in) {
            return new Similar(in);
        }

        public Similar[] newArray(int size) {
            return (new Similar[size]);
        }

    }
    ;

    protected Similar(Parcel in) {
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.results, (Result.class.getClassLoader()));
        this.totalPages = ((int) in.readValue((int.class.getClassLoader())));
        this.totalResults = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Similar() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(results);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public int describeContents() {
        return  0;
    }

}
