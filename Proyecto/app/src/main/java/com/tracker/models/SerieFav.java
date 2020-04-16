package com.tracker.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.tracker.adapters.FillSerie;
import com.tracker.data.RepositoryAPI;
import com.tracker.data.RxBus;
import com.tracker.data.SeriesViewModel;
import com.tracker.models.seasons.Season;
import com.tracker.models.serie.SerieResponse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

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

    public SerieFav(int id, String name, String status,
                    String posterPath, List<Integer> episodeRunTime,
                    int numberOfEpisodes, int numberOfSeasons, List<Season> seasons) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.seasons = seasons;
        this.posterPath = posterPath;
        this.episodeRunTime = episodeRunTime;
        this.numberOfSeasons = numberOfSeasons;
        this.numberOfEpisodes = numberOfEpisodes;
        getSeasons();

    }

    public SerieFav() {

    }

    private void getSeasons() {

    }

    public static void readFav(String file, SeriesViewModel model) {
        try {
            try (Reader reader = new FileReader(file)) {
                Gson gson = new Gson();
                Type types = new TypeToken<List<SerieFav>>() {
                }.getType();
                List<SerieFav> fav = gson.fromJson(reader, types);
                model.setFavs(fav);
            }
        } catch (IOException e) {
            Log.e("ERROR", e.toString());
        }
    }

    public static boolean checkFav(SerieResponse.Serie serie, List<SerieFav> favs) {
        for (SerieFav s : favs) {
            if (serie.id == s.id) {
                serie.setFav(true);
                return true;
            }
        }
        return false;
    }

    public static void writeFav(List<SerieFav> favs, SerieResponse.Serie serie, String file, SeriesViewModel model) {
        try {
            Writer writer = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            serie.setFav(true);
            SerieFav s = serie.convertSerieToFav();

            RepositoryAPI.getInstance().getSeasons(s.id, s.seasons.get(0).seasonNumber, s.numberOfSeasons)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(lista -> {
                        s.seasons = lista;
                        favs.add(s);
                        gson.toJson(favs, writer);
                        writer.close();
//                        model.setFavs(favs);
                    });
        } catch (IOException e) {

            e.printStackTrace();
        }
//            mAdapter.notifyDataSetChanged();
    }

    protected SerieFav(Parcel in) {
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
