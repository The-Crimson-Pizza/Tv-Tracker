package com.thecrimsonpizza.tvtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicResponse {

    @SerializedName("results")
    @Expose
    public final List<SerieBasic> basicSeries;

    public BasicResponse(List<SerieBasic> basicSeries) {
        this.basicSeries = basicSeries;
    }

    public static class SerieBasic implements Parcelable {

        public final Integer id;
        public final String name;
        @SerializedName("poster_path")
        public final String posterPath;
        @SerializedName("vote_average")
        public float voteAverage;

        public SerieBasic(Integer id, String name, String posterPath, float voteAverage) {
            this.id = id;
            this.name = name;
            this.posterPath = posterPath;
            this.voteAverage = voteAverage;
        }

        SerieBasic(Parcel in) {
            name = in.readString();
            posterPath = in.readString();
            id = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeString(name);
            parcel.writeString(posterPath);
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
