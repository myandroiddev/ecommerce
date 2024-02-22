package com.rohith.ecommercemobilefashionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity {
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    RecyclerView recyclerView;
    Map<String, Object> products = new HashMap<>();
    MyAdapter adapter;
    ArrayList<ProductSchema> productList = new ArrayList<>();
    FirebaseAuth auth;
    DocumentReference userDoc;
    ImageButton wishlist,searchBtn;
    Button viewAll;
    GridLayout gridLayout;
    CardView card1,card2,card3,card4,card5,card6,card7,card8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        auth = FirebaseAuth.getInstance();
        String UID = auth.getCurrentUser().getUid();
        userDoc = FirebaseFirestore.getInstance().collection("user").document(UID);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        gridLayout = findViewById(R.id.gridLayout);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        card7 = findViewById(R.id.card7);
        card8 = findViewById(R.id.card8);
        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, SearchActivity.class);
                startActivity(i);
            }
        });
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        wishlist = findViewById(R.id.wishlist);
        viewAll = findViewById(R.id.button4);

        if (screenHeight > 0 && screenWidth > 2000) {
            gridLayout.setRowCount(2);
            gridLayout.setColumnCount(7);
            gridLayout.setPadding(70,0,70,0);
        }
        else {
            gridLayout.setRowCount(2);
            gridLayout.setColumnCount(2);
        }

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Allproducts.class);
                startActivity(i);
            }
        });

        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ArrayList<String> cart = (ArrayList<String>) documentSnapshot.get("cart");
                        if (cart.size() == 0) {
                            Intent i = new Intent(Dashboard.this, EmptyCart.class);
                            startActivity(i);
                        } else if (cart.size() > 0) {
                            Intent i = new Intent(Dashboard.this, Wishlist.class);
                            startActivity(i);
                        }
                    }
                });
            }
        });

    }

    public void cardOnClick(View v) {
        Intent i = new Intent(this, Product_description.class);
        String productId = (String) v.getTag();
        i.putExtra("PRODUCT_ID", productId);
        startActivity(i);
    }

    public void gotoProfile(View v) {
        Intent i = new Intent(getApplicationContext(), profileList.class);
        startActivity(i);
    }

    /*

    */


}