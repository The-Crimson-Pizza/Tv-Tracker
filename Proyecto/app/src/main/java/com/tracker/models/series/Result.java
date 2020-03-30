
package com.tracker.models.series;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Parcelable
{

    @SerializedName("original_name")
    @Expose
    public String originalName;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    @SerializedName("first_air_date")
    @Expose
    public String firstAirDate;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("genre_ids")
    @Expose
    public List<Integer> genreIds = null;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("origin_country")
    @Expose
    public List<String> originCountry = null;
    @SerializedName("popularity")
    @Expose
    public float popularity;
    public final static Creator<Result> CREATOR = new Creator<Result>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return (new Result[size]);
        }

    }
    ;

    protected Result(Parcel in) {
        this.originalName = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.voteCount = ((int) in.readValue((int.class.getClassLoader())));
        this.voteAverage = ((float) in.readValue((float.class.getClassLoader())));
        this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.genreIds, (Integer.class.getClassLoader()));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.originCountry, (String.class.getClassLoader()));
        this.popularity = ((float) in.readValue((float.class.getClassLoader())));
    }

    public Result() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(originalName);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(voteCount);
        dest.writeValue(voteAverage);
        dest.writeValue(firstAirDate);
        dest.writeValue(posterPath);
        dest.writeList(genreIds);
        dest.writeValue(originalLanguage);
        dest.writeValue(backdropPath);
        dest.writeValue(overview);
        dest.writeList(originCountry);
        dest.writeValue(popularity);
    }

    public int describeContents() {
        return  0;
    }

}
