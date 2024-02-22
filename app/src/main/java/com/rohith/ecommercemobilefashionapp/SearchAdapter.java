package com.rohith.ecommercemobilefashionapp;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    ArrayList<ProductSchema> productSchemaArrayList = new ArrayList<>();
    Context context;

    public SearchAdapter(ArrayList<ProductSchema> productSchemaArrayList, Context context) {
        this.productSchemaArrayList = productSchemaArrayList;
        this.context = context;
    }

    public void setFilteredList(ArrayList<ProductSchema> filteredList) {
        this.productSchemaArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductSchema currentProduct = productSchemaArrayList.get(position);
        holder.name.setText(currentProduct.getProductName());
        holder.itemView.setTag(currentProduct.getId());
        final Context context1 = holder.itemView.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Product_description.class);
                i.putExtra("PRODUCT_ID", currentProduct.id);
                context1.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productSchemaArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ConstraintLayout searchCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.searchedName);
            searchCard = itemView.findViewById(R.id.search_card);
        }
    }
}

