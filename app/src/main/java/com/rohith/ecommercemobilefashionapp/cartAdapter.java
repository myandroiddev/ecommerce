package com.rohith.ecommercemobilefashionapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.MyViewHolder> {

    ArrayList<ProductSchema> arrayList = new ArrayList<>();
    Context context;

    public cartAdapter(ArrayList<ProductSchema> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductSchema newProduct = arrayList.get(position);
        if (newProduct != null) {
            Log.d("CartAdapter", "Product Name: " + newProduct.getProductName());
            holder.productName.setText(newProduct.getProductName());
            holder.productPrice.setText("$"+newProduct.getPrice());
            Uri imageUrl = newProduct.getImageUrl();
            if (imageUrl != null) {
                Glide.with(context)
                        .load(imageUrl)
                        .into(holder.productImage);
            } else {
                holder.productImage.setImageResource(R.drawable.i1);
            }
        } else {
            Log.e("CartAdapter", "Product at position " + position + " is null");
        }

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cartProductImage);
            productName = itemView.findViewById(R.id.cartProductName);
            productPrice = itemView.findViewById(R.id.cartProductPrice);
        }
    }

}
