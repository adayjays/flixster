package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.flixster.adaptor.MovieAdaptor;
import com.example.flixster.model.Movie;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String now_playing_url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String tag = "MainActivity";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //create com.example.flixster1.adaptor
        MovieAdaptor movieAdaptor = new MovieAdaptor(this, movies);
        //set the com.example.flixster1.adaptor on UI
        rvMovies.setAdapter(movieAdaptor);

        // set layout on ui
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(now_playing_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(tag,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(tag, "Results" + results.toString());

                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdaptor.notifyDataSetChanged();

                    Log.i(tag, "Movies:" + movies.size());

                } catch (JSONException e) {
                    Log.e(tag, "Hit json exception", e);
                }


            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(tag, "onFailure");
            }
        });

    }
}