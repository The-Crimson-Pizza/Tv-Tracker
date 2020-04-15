package com.tracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicResponse {

    @SerializedName("results")
    @Expose
    public final List<SerieBasic> basicSeries = null;

    public static class SerieBasic implements Parcelable {

        public String name;
        public String poster_path;
        public Integer id;
        @SerializedName("vote_average")
        public float voteAverage;

        public SerieBasic(Parcel in) {
            name = in.readString();
            poster_path = in.readString();
            id = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeString(name);
            parcel.writeString(poster_path);
            parcel.writeInt(id);
        }

        public static final Parcelable.Creator<SerieBasic> CREATOR =
                new Parcelable.Creator<SerieBasic>() {
                    public SerieBasic createFromParcel(Parcel in) {
                        return new SerieBasic(in);
                    }

                    public SerieBasic[] newArray(int size) {
                        return new SerieBasic[size];
                    }
                };
    }
}
