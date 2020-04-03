package com.tracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tracker.models.series.Serie;

import java.util.List;

public class BasicResponse {

    @SerializedName("results")
    @Expose
    public final List<SerieBasic> trendingSeries = null;

    public static class SerieBasic implements Parcelable {

        public String name = null;
        public String poster_path = null;
        public Integer id = null;

        public SerieBasic(String title, String poster, Integer id) {
            this.name = title;
            this.poster_path = poster;
            this.id = id;
        }

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
