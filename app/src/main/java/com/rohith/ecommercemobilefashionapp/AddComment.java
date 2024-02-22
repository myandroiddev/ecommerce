package com.rohith.ecommercemobilefashionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class AddComment extends AppCompatActivity {
    RatingBar rating;
    EditText comment;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseStorage firebaseStorage;
    StorageReference userImage;
    DocumentReference userCollection,productCollection;
    String productId;
    Button addComment;
    ImageView commentCardImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        rating = findViewById(R.id.ratingBar);
        comment = findViewById(R.id.comment);
        commentCardImg = findViewById(R.id.comment_card_img);
        Intent intent = getIntent();
        if (intent != null) {
            productId = intent.getStringExtra("product_id");
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userCollection = firestore.collection("user").document(firebaseAuth.getCurrentUser().getUid());
        productCollection = firestore.collection("Products").document(productId);
        firebaseStorage = FirebaseStorage.getInstance();
        addComment = findViewById(R.id.addComment);

        productCollection.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String uri = documentSnapshot.get("uri").toString();
                Glide.with(getApplicationContext()).load(uri).into(commentCardImg);
            }
        });

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment.getText().toString() != "" && rating.getRating() != 0) {
                    productCollection.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot productDocumentSnapshot) {
                            ArrayList<Comment> commentList = (ArrayList<Comment>) productDocumentSnapshot.get("comments");
                            userCollection.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    userImage = firebaseStorage.getReference().child("product_images/"+productId);
                                    userImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Date currentDate = new Date();
                                            Comment newComment = new Comment(documentSnapshot.get("username").toString(),comment.getText().toString(),currentDate,uri.toString(),rating.getRating(),documentSnapshot.getId().toString());
                                            commentList.add(newComment);
                                            productCollection.update("comments",commentList).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Intent i = new Intent(getApplicationContext(), Product_description.class);
                                                    i.putExtra("PRODUCT_ID",productId);
                                                    startActivity(i);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}