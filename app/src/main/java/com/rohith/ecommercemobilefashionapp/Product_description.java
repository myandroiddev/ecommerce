package com.rohith.ecommercemobilefashionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Product_description extends AppCompatActivity {
    TextView name, price, description;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    DocumentReference documentReference, userCollection;
    ImageView product_img;
    String productId;
    RecyclerView commentRecycle;
    String selectedSize;
    Button addTOCart, s, m, l, xl, xxl, addComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        product_img = findViewById(R.id.productImage);
        commentRecycle = findViewById(R.id.comments_recycle_view);
        Intent intent = getIntent();
        addTOCart = findViewById(R.id.addtocart);
        if (intent != null) {
            productId = intent.getStringExtra("PRODUCT_ID");
        }
        firebaseAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.productName);
        price = findViewById(R.id.productPrice);
        description = findViewById(R.id.productDescription);
        s = findViewById(R.id.product_size_s);
        m = findViewById(R.id.product_size_m);
        l = findViewById(R.id.product_size_l);
        xl = findViewById(R.id.product_size_xl);
        xxl = findViewById(R.id.product_size_xxl);
        addComment = findViewById(R.id.addComment);
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("product_images/" + productId);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Product_description.this).load(uri.toString()).into(product_img);
            }
        });
        documentReference = firestore.collection("Products").document(productId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    name.setText(documentSnapshot.getString("productName"));
                    price.setText("$" + documentSnapshot.getString("price"));
                    description.setText(documentSnapshot.getString("description"));
                    ArrayList<String> size = (ArrayList<String>) documentSnapshot.get("size");
                    int color = ContextCompat.getColor(Product_description.this, R.color.grey);
                    ColorStateList colorStateList = ColorStateList.valueOf(color);
                    if (!size.contains("s")) {
                        SpannableString spannableString = new SpannableString(s.getText());
                        spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        s.setText(spannableString);
                        s.setBackgroundColor(getResources().getColor(R.color.grey));
                        s.setBackgroundTintList(colorStateList);
                    }
                    if (!size.contains("m")) {
                        SpannableString spannableString = new SpannableString(m.getText());
                        spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        m.setText(spannableString);
                        m.setBackgroundColor(getResources().getColor(R.color.grey));
                        m.setBackgroundTintList(colorStateList);
                    }
                    if (!size.contains("l")) {
                        SpannableString spannableString = new SpannableString(l.getText());
                        spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        l.setText(spannableString);
                        l.setBackgroundColor(getResources().getColor(R.color.grey));
                        l.setBackgroundTintList(colorStateList);
                    }
                    if (!size.contains("xl")) {
                        SpannableString spannableString = new SpannableString(xl.getText());
                        spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        xl.setText(spannableString);
                        xl.setBackgroundColor(getResources().getColor(R.color.grey));
                        xl.setBackgroundTintList(colorStateList);
                    }
                    if (!size.contains("xxl")) {
                        SpannableString spannableString = new SpannableString(xxl.getText());
                        spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        xxl.setText(spannableString);
                        xxl.setBackgroundColor(getResources().getColor(R.color.grey));
                        xxl.setBackgroundTintList(colorStateList);
                    }
                    ArrayList<HashMap<String, Object>> commentMapList = (ArrayList<HashMap<String, Object>>) documentSnapshot.get("comments");
                    ArrayList<Comment> commentArrayList = new ArrayList<>();

                    if (commentMapList != null) {
                        for (HashMap<String, Object> commentMap : commentMapList) {
                            String userName = (String) commentMap.get("userName");
                            String comment = (String) commentMap.get("comment");
                            com.google.firebase.Timestamp timestamp = (com.google.firebase.Timestamp) commentMap.get("commentUploadDate");
                            Date commentUploadDate = timestamp.toDate();
                            String imageUrl = (String) commentMap.get("imageUrl");
                            float rating = ((Double) commentMap.get("rating")).floatValue();
                            String userId = (String) commentMap.get("userId");
                            Comment commentObject = new Comment(userName, comment, commentUploadDate, imageUrl, rating, userId);
                            commentArrayList.add(commentObject);
                        }
                    }
                    if (commentArrayList.size() != 0) {
                        commentAdapter commentAdapter = new commentAdapter(commentArrayList, getApplicationContext());
                        commentRecycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        commentRecycle.setAdapter(commentAdapter);
                    }
                } else {
                    Toast.makeText(Product_description.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String UID = firebaseAuth.getCurrentUser().getUid();
        userCollection = firestore.collection("user").document(UID);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddComment.class);
                i.putExtra("product_id", productId);
                startActivity(i);
            }
        });

        addTOCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productId != null) {
                    userCollection.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            ArrayList<String> cart = (ArrayList<String>) documentSnapshot.get("cart");
                            if (cart == null) {
                                cart = new ArrayList<>();
                            }
                            if (!cart.contains(productId)) {
                                cart.add(productId);
                                userCollection.update("cart", cart).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Product_description.this, "Item added to cart", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Product already exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}