package com.example.movify.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movify.CategoryItem;
import com.example.movify.R;
import com.example.movify.VideoDetails;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {

    Context context;
    List<CategoryItem> categoryItemList;

    public ItemRecyclerAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemRecyclerAdapter.ItemViewHolder holder, int position) {

        Glide.with(context).load(categoryItemList.get(position).getImageURL()).into(holder.itemImage);
            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, VideoDetails.class);
                    i.putExtra("movieID",categoryItemList.get(position).getID());
                    i.putExtra("movieName",categoryItemList.get(position).getMovieNames());
                    i.putExtra("imageURL",categoryItemList.get(position).getImageURL());
                    i.putExtra("movieFile",categoryItemList.get(position).getFileURL());
                    context.startActivity(i);
                }
            });

    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;

        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.item_Image);
        }
    }

}
