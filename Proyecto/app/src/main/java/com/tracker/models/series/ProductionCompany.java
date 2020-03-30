
package com.tracker.models.series;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompany implements Parcelable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("logo_path")
    @Expose
    public Object logoPath;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("origin_country")
    @Expose
    public String originCountry;
    public final static Creator<ProductionCompany> CREATOR = new Creator<ProductionCompany>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ProductionCompany createFromParcel(Parcel in) {
            return new ProductionCompany(in);
        }

        public ProductionCompany[] newArray(int size) {
            return (new ProductionCompany[size]);
        }

    }
    ;

    protected ProductionCompany(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.logoPath = ((Object) in.readValue((Object.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.originCountry = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ProductionCompany() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(logoPath);
        dest.writeValue(name);
        dest.writeValue(originCountry);
    }

    public int describeContents() {
        return  0;
    }

}
