
package com.tracker.models.series;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalIds implements Parcelable
{

    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
    @SerializedName("freebase_mid")
    @Expose
    public Object freebaseMid;
    @SerializedName("freebase_id")
    @Expose
    public Object freebaseId;
    @SerializedName("tvdb_id")
    @Expose
    public int tvdbId;
    @SerializedName("tvrage_id")
    @Expose
    public int tvrageId;
    @SerializedName("facebook_id")
    @Expose
    public String facebookId;
    @SerializedName("instagram_id")
    @Expose
    public String instagramId;
    @SerializedName("twitter_id")
    @Expose
    public String twitterId;
    public final static Creator<ExternalIds> CREATOR = new Creator<ExternalIds>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ExternalIds createFromParcel(Parcel in) {
            return new ExternalIds(in);
        }

        public ExternalIds[] newArray(int size) {
            return (new ExternalIds[size]);
        }

    }
    ;

    protected ExternalIds(Parcel in) {
        this.imdbId = ((String) in.readValue((String.class.getClassLoader())));
        this.freebaseMid = ((Object) in.readValue((Object.class.getClassLoader())));
        this.freebaseId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.tvdbId = ((int) in.readValue((int.class.getClassLoader())));
        this.tvrageId = ((int) in.readValue((int.class.getClassLoader())));
        this.facebookId = ((String) in.readValue((String.class.getClassLoader())));
        this.instagramId = ((String) in.readValue((String.class.getClassLoader())));
        this.twitterId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ExternalIds() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(imdbId);
        dest.writeValue(freebaseMid);
        dest.writeValue(freebaseId);
        dest.writeValue(tvdbId);
        dest.writeValue(tvrageId);
        dest.writeValue(facebookId);
        dest.writeValue(instagramId);
        dest.writeValue(twitterId);
    }

    public int describeContents() {
        return  0;
    }

}
