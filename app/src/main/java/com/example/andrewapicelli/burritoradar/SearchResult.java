package com.example.andrewapicelli.burritoradar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * POJO for representing a Places api search result
 */
public class SearchResult {

    public String name;
    public String address;
    public String rating;
    public double latitude;
    public double longitude;

    public SearchResult(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
