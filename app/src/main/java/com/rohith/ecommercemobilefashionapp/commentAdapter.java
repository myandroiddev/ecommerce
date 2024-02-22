package com.rohith.ecommercemobilefashionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.MyViewHolder> {

    ArrayList<Comment> commentsArrayList = new ArrayList<>();
    Context context;

    public commentAdapter(ArrayList<Comment> commentsArrayList, Context context) {
        this.commentsArrayList = commentsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comment currentComment = commentsArrayList.get(position);
        if (currentComment != null) {
            holder.userName.setText(currentComment.getUserName().toString());
            holder.rating.setRating(currentComment.getRating());
            Date currentDate = currentComment.getCommentUploadDate();
            String date = ""+ currentDate.getDate() + " " + currentDate.getMonth() + ", " +  currentDate.getYear();
            holder.commentTime.setText(date);
            String imageUrl = currentComment.getImageUrl();
            if (imageUrl != null) {
                Glide.with(context)
                        .load(imageUrl)
                        .into(holder.userImg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userImg;
        TextView commentText,userName,commentTime;
        RatingBar rating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.comment_user_image);
            commentText = itemView.findViewById(R.id.comment);
            userName = itemView.findViewById(R.id.comment_user_name);
            rating = itemView.findViewById(R.id.ratingBar);
            commentTime = itemView.findViewById(R.id.comment_time);
        }
    }

}
