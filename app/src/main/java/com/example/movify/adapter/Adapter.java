package com.example.movify.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.movify.BannerMovies;
import com.example.movify.R;
import com.example.movify.VideoDetails;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter extends PagerAdapter {

    Context context;
    List<BannerMovies> bannerMoviesList;

    public Adapter(Context context, List<BannerMovies> bannerMoviesList) {
        this.context = context;
        this.bannerMoviesList = bannerMoviesList;
    }

    @Override
    public int getCount() {
        return bannerMoviesList.size();
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull @org.jetbrains.annotations.NotNull View view, @NonNull @org.jetbrains.annotations.NotNull Object object) {
        return view == object;


    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.banner_movie_layout, null);

        ImageView bannerImage = view.findViewById(R.id.banner_image);

        Glide.with(context).load(bannerMoviesList.get(position).getImageURL()).into(bannerImage);
        container.addView(view);

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, VideoDetails.class);
                i.putExtra("movieID",bannerMoviesList.get(position).getID());
                i.putExtra("movieName",bannerMoviesList.get(position).getMovieNames());
                i.putExtra("imageURL",bannerMoviesList.get(position).getImageURL());
                i.putExtra("movieFile",bannerMoviesList.get(position).getFileURL());
                context.startActivity(i);
            }
        });

        return view;

    }
}
