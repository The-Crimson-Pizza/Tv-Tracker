
package com.tracker.models.people;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person implements Parcelable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("birthday")
    @Expose
    public String birthday;
    @SerializedName("deathday")
    @Expose
    public String deathday;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("biography")
    @Expose
    public String biography;
    @SerializedName("place_of_birth")
    @Expose
    public String placeOfBirth;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    @SerializedName("homepage")
    @Expose
    public String homepage;

    @SerializedName("tv_credits")
    @Expose
    public TvCredits tvCredits;
    @SerializedName("external_ids")
    @Expose
    public ExternalIds externalIds;
    @SerializedName("movie_credits")
    @Expose
    public MovieCredits movieCredits;

    public final static Creator<Person> CREATOR =
            new Creator<Person>() {
                public Person createFromParcel(Parcel in) {
                    return new Person(in);
                }

                public Person[] newArray(int size) {
                    return (new Person[size]);
                }
            };

    protected Person(Parcel in) {
        this.birthday = ((String) in.readValue((String.class.getClassLoader())));
        this.deathday = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.tvCredits = ((TvCredits) in.readValue((TvCredits.class.getClassLoader())));
        this.movieCredits = ((MovieCredits) in.readValue((MovieCredits.class.getClassLoader())));
        this.gender = ((int) in.readValue((int.class.getClassLoader())));
        this.biography = ((String) in.readValue((String.class.getClassLoader())));
        this.placeOfBirth = ((String) in.readValue((String.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
        this.homepage = ((String) in.readValue((String.class.getClassLoader())));
        this.externalIds = ((ExternalIds) in.readValue((ExternalIds.class.getClassLoader())));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(birthday);
        dest.writeValue(deathday);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(tvCredits);
        dest.writeValue(movieCredits);
        dest.writeValue(gender);
        dest.writeValue(biography);
        dest.writeValue(placeOfBirth);
        dest.writeValue(profilePath);
        dest.writeValue(homepage);
        dest.writeValue(externalIds);
    }

    public int describeContents() {
        return 0;
    }

    public class ExternalIds implements Parcelable {

        @SerializedName("instagram_id")
        @Expose
        public String instagramId;
        @SerializedName("twitter_id")
        @Expose
        public String twitterId;
        @SerializedName("imdb_id")
        @Expose
        public String imdbId;

        public final Parcelable.Creator<ExternalIds> CREATOR =
                new Creator<ExternalIds>() {
                    public ExternalIds createFromParcel(Parcel in) {
                        return new ExternalIds(in);
                    }

                    public ExternalIds[] newArray(int size) {
                        return (new ExternalIds[size]);
                    }
                };

        ExternalIds(Parcel in) {
            this.instagramId = ((String) in.readValue((String.class.getClassLoader())));
            this.twitterId = ((String) in.readValue((String.class.getClassLoader())));
            this.imdbId = ((String) in.readValue((String.class.getClassLoader())));
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(instagramId);
            dest.writeValue(twitterId);
            dest.writeValue(imdbId);
        }

        public int describeContents() {
            return 0;
        }
    }
}
