package com.tracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static com.tracker.util.Constants.BASE_URL_IMAGES;

public class SerieTrendingResponse {
    @SerializedName("results")
    @Expose
    public final ArrayList<SerieTrending> trendingSeries = null;

    public static class SerieTrending implements Parcelable {

        public String name = null;
        public String poster_path = null;
        public Integer id = null;

        public SerieTrending(String title, String poster, Integer id) {
            this.name = title;
            this.poster_path = poster;
            this.id = id;
        }

        public SerieTrending(Parcel in) {
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

        public static final Parcelable.Creator<SerieTrending> CREATOR =
                new Parcelable.Creator<SerieTrending>() {
                    public SerieTrending createFromParcel(Parcel in) {
                        return new SerieTrending(in);
                    }

                    public SerieTrending[] newArray(int size) {
                        return new SerieTrending[size];
                    }
                };
    }
}
