package please.picture.com.pictureplease.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jeka on 02.05.17.
 */

public class Task implements Parcelable {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Street")
    @Expose
    private String street;
    @SerializedName("Photo")
    @Expose
    private String photo;

    public Task(String name, String latitude, String longitude, String street, String photo) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.photo = photo;
    }

    protected Task(Parcel in) {
        name = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        street = in.readString();
        photo = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(street);
        parcel.writeString(photo);
    }
}

