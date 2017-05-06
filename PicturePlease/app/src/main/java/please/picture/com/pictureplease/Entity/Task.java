package please.picture.com.pictureplease.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jeka on 02.05.17.
 */

public class Task {
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
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("people")
    @Expose
    private String people;
    @SerializedName("description")
    @Expose
    private String description;

    public Task(String name, String latitude, String longitude, String street, String photo, String date, String people, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.photo = photo;
        this.date = date;
        this.people = people;
        this.description = description;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

