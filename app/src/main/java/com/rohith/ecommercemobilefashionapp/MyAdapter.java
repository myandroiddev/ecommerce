package com.rohith.ecommercemobilefashionapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<ProductSchema> productList = new ArrayList<>();
    private final Context context;

    public MyAdapter(ArrayList<ProductSchema> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductSchema currentProduct = productList.get(position);
        holder.product_name.setText(currentProduct.getProductName());
        holder.product_price.setText("$"+currentProduct.getPrice());
        if (currentProduct.getImageUrl() != null) {
            String imageUrl = currentProduct.getImageUrl().toString();
            Glide.with(context)
                    .load(imageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("Glide", "Image load failed: " + e.getMessage());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.product_img.setImageDrawable(resource);
                            return true;
                        }
                    })
                    .into(holder.product_img);
        }
        else {
            Log.i("Glide","Image is empty");
        }
        holder.cardView.setTag(currentProduct.getId());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Product_description.class);
                String productId = (String) view.getTag();
                i.putExtra("PRODUCT_ID", productId);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView product_img;
        TextView product_name,product_price;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.card_product_image);
            product_name = itemView.findViewById(R.id.card_product_name);
            product_price = itemView.findViewById(R.id.card_product_price);
            cardView = itemView.findViewById(R.id.product_card);
        }
    }

}
