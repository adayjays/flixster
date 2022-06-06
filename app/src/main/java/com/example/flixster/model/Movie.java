package com.example.flixster.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    int movieId;
    String posterpath;
    String title;
    String description;
    double rating;

    //need for Parcel
    public Movie(){}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterpath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        description = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
    }
    public static List<Movie> fromJsonArray(JSONArray moviejsonarray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i=0; i < moviejsonarray.length(); i++){
            movies.add(new Movie(moviejsonarray.getJSONObject(i)));
        }
        return movies;
    }

    public int getMovieId(){
        return movieId;
    }

    public String getBackdropPath(){
        return "";
    }

    public double getRating(){
        return rating;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterpath);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){
        return description;
    }
}
