package com.example.movify;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayerActivity extends AppCompatActivity {

    private PlayerView videoPlayer;
    private SimpleExoPlayer simpleExoPlayer;
    private static final String File_URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_player);

        videoPlayer = findViewById(R.id.exo_player);
        setupExoPlayer(getIntent().getStringExtra("URL"));
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        simpleExoPlayer.release();
    }

    @Override
    protected void onPause () {

        super.onPause();
    }

    @Override
    protected void onResume () {
        super.onResume();
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (Util.SDK_INT > 23) {
            simpleExoPlayer.release();
        }
    }


    private void setupExoPlayer(String URL) {

        if (simpleExoPlayer == null) {

            DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
            simpleExoPlayer = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
        }
        videoPlayer.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "movieapp"));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(Uri.parse(URL)));
        simpleExoPlayer.setMediaSource(mediaSource);
        simpleExoPlayer.prepare();
        simpleExoPlayer.setPlayWhenReady(true);

        }


    }
