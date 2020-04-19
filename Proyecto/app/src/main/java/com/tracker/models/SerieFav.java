package com.tracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;

import java.util.Date;
import java.util.List;

public class SerieFav implements Parcelable {

    public int id;
    public String name;
    public String status;
    public List<Season> seasons;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("number_of_seasons")
    public int numberOfSeasons;
    @SerializedName("number_of_episodes")
    public int numberOfEpisodes;
    @SerializedName("episode_run_time")
    public List<Integer> episodeRunTime = null;
    @SerializedName("vote_average")
    public float voteAverage;
    public Date addedDate;
    public Date finishDate;

    public SerieFav() {
    }

    public SerieFav(int id, String name, String status, String posterPath, List<Integer> episodeRunTime,
                    int numberOfEpisodes, int numberOfSeasons, List<Season> seasons, float voteAverage) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.seasons = seasons;
        this.posterPath = posterPath;
        this.numberOfSeasons = numberOfSeasons;
        this.numberOfEpisodes = numberOfEpisodes;
        this.episodeRunTime = episodeRunTime;
        this.voteAverage = voteAverage;
    }

    public BasicResponse.SerieBasic toBasic() {
        return new BasicResponse.SerieBasic(this.id, this.name, this.posterPath, this.voteAverage);
    }

    public static boolean checkFav(SerieResponse.Serie serie, List<SerieFav> favs) {
        for (SerieFav s : favs) {
            if (serie.id == s.id) {
                serie.isFav = true;
                return true;
            }
        }
        return false;
    }

    public static int getPosition(SerieResponse.Serie serie, List<SerieFav> favs) {
        for (int i = 0; i < favs.size(); i++) {
            if (favs.get(i).id == serie.id) {
                return i;
            }
        }
        return -1;
    }

    private SerieFav(Parcel in) {
        in.readList(this.seasons, (Season.class.getClassLoader()));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.episodeRunTime, (Integer.class.getClassLoader()));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.numberOfSeasons = ((int) in.readValue((String.class.getClassLoader())));
        this.numberOfEpisodes = ((int) in.readValue((String.class.getClassLoader())));
    }

    public static final Creator<SerieFav> CREATOR = new Creator<SerieFav>() {
        @Override
        public SerieFav createFromParcel(Parcel in) {
            return new SerieFav(in);
        }

        @Override
        public SerieFav[] newArray(int size) {
            return new SerieFav[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(status);
        dest.writeValue(seasons);
        dest.writeValue(posterPath);
        dest.writeValue(episodeRunTime);
        dest.writeValue(numberOfSeasons);
        dest.writeValue(numberOfEpisodes);
    }
}
