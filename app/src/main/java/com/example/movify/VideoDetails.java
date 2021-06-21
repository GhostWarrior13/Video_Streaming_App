package com.example.movify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class VideoDetails extends AppCompatActivity {

    ImageView Video_image;
    TextView video_name;
    Button Play_button;

    String mName, mImage, mID, mFileURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);

        Video_image = findViewById(R.id.Video_image);
        video_name = findViewById(R.id.video_name);
        Play_button= findViewById(R.id.Play_button);

        mID = getIntent().getStringExtra("movieID");
        mName = getIntent().getStringExtra("movieName");
        mImage = getIntent().getStringExtra("imageURL");
        mFileURL = getIntent().getStringExtra("movieFile");

        Glide.with(this).load(mImage).into(Video_image);
        video_name.setText(mName);

        Play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VideoDetails.this, VideoPlayerActivity.class);
                i.putExtra("URL", mFileURL);
                startActivity(i);
            }
        });



    }
}