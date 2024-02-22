package com.rohith.ecommercemobilefashionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rohith.ecommercemobilefashionapp.MyAdapter;
import com.rohith.ecommercemobilefashionapp.ProductSchema;
import com.rohith.ecommercemobilefashionapp.R;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Allproducts extends AppCompatActivity {

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
    ImageButton wishlist;
    ImageView allProductBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allproducts);
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        collectionReference = firestore.collection("Products");
        recyclerView = findViewById(R.id.allproducts);
        allProductBack = findViewById(R.id.all_product_back);
        allProductBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        adapter = new MyAdapter(productList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(7), true));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        fetchProductData();
    }

    @Override

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void fetchProductData() {
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    products = documentSnapshot.getData();
                    String imageName = documentSnapshot.getId();
                    storageReference = storage.getReference().child("product_images/" + imageName);
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ProductSchema newProduct = documentSnapshot.toObject(ProductSchema.class);
                            newProduct.setImageUrl(uri);
                            newProduct.setId(imageName);
                            productList.add(newProduct);
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        });
    }

    // Helper method to convert dp to pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    // Custom ItemDecoration class to add spacing between items
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}