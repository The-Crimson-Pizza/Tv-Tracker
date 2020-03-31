
package com.tracker.models.series;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Similar implements Parcelable {

    @SerializedName("results")
    @Expose
    public List<Result> results = null;
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

    };

    protected Similar(Parcel in) {
        in.readList(this.results, (Result.class.getClassLoader()));
    }

    public Similar() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
    }

    public int describeContents() {
        return 0;
    }

    public class Result implements Parcelable {

        @SerializedName("original_name")
        @Expose
        public String originalName;
        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("popularity")
        @Expose
        public float popularity;

        public final Creator<Result> CREATOR = new Creator<Result>() {

            @SuppressWarnings({
                    "unchecked"
            })
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            public Result[] newArray(int size) {
                return (new Result[size]);
            }

        };

        protected Result(Parcel in) {
            this.originalName = ((String) in.readValue((String.class.getClassLoader())));
            this.id = ((int) in.readValue((int.class.getClassLoader())));
            this.name = ((String) in.readValue((String.class.getClassLoader())));
            this.popularity = ((float) in.readValue((float.class.getClassLoader())));
        }

        public Result() {
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(originalName);
            dest.writeValue(id);
            dest.writeValue(name);
            dest.writeValue(popularity);
        }

        public int describeContents() {
            return 0;
        }

    }

}
