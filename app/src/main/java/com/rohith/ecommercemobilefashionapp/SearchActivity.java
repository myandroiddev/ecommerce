package com.rohith.ecommercemobilefashionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    SearchView searchBar;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firestore;
    CollectionReference productsReferences;
    ArrayList<ProductSchema> productList;
    SearchAdapter searchAdapter;
    RecyclerView search_recycle_view;
    TextView no_resultsfound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchBar = findViewById(R.id.searchBar);
        firestore = FirebaseFirestore.getInstance();
        search_recycle_view = findViewById(R.id.search_recycle_view);
        productsReferences = firestore.collection("Products");
        no_resultsfound = findViewById(R.id.no_results);
        productList = new ArrayList<>();
        searchBar.clearFocus();
        productsReferences.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                productList.clear();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    ProductSchema product = document.toObject(ProductSchema.class);
                    if (product.id == null) {
                        product.id = document.getId();
                    }
                    productList.add(product);
                }
            }
        });


        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                results(newText);
                return false;
            }
        });
    }

    public void results(String newText) {
        ArrayList<ProductSchema> resultList = new ArrayList<>();
        for (ProductSchema item: productList) {
            if (item.getProductName().toLowerCase().contains(newText.toLowerCase())) {
                resultList.add(item);
            }
        }
        if (resultList.isEmpty() && !newText.isEmpty()) {
            no_resultsfound.setVisibility(View.VISIBLE);
            search_recycle_view.setVisibility(View.INVISIBLE);
        }
        else {
            if (searchAdapter == null) {
                searchAdapter = new SearchAdapter(productList,this);
                search_recycle_view.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                search_recycle_view.setAdapter(searchAdapter);
            }
            searchAdapter.setFilteredList(resultList);
        }

    }


}