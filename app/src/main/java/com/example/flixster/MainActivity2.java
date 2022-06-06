package com.example.flixster;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.model.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MainActivity2 extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "ef26e58067267dad4e98ba21b2d1b578fd7d908f";
    public static final String VIDEO_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    TextView titleD;
    TextView description;
    YouTubePlayerView playerView;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        titleD = findViewById(R.id.title_d);
        description = findViewById(R.id.description);
        ratingBar = findViewById(R.id.ratingBar);
        playerView = findViewById(R.id.player);
        youTubePlayerView = findViewById(R.id.player);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        titleD.setText(movie.getTitle());
        description.setText(movie.getDescription());
        ratingBar.setRating((float) movie.getRating());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("MainActivity2:::", youtubeKey);
                    initializeYoutube(youtubeKey);

                } catch (JSONException e) {
                    Log.e("MainActivity2", "Failed to parse Json");
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });
    }

    private void initializeYoutube(String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("MainActivity2", "onInitializationSuccess");
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("MainActivity2", "onInitializationFailure");
            }
        });




    }
    }
