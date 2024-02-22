package com.rohith.ecommercemobilefashionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class profileList extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    LinearLayout linear1,logout;
    FirebaseStorage storage;
    FirebaseFirestore firestore;
    DocumentReference df;
    TextView email,username,noOfItemsInCart;
    ImageView userImage;
    StorageReference storageReference;
    ConstraintLayout relativeLayout5,wishlistLayout;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        linear1 = findViewById(R.id.linear1);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        userImage = findViewById(R.id.userImage);
        noOfItemsInCart = findViewById(R.id.no_of_items_in_cart);

        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        String UID = firebaseAuth.getCurrentUser().getUid();
        df = firestore.collection("user").document(UID);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                username.setText(documentSnapshot.get("username").toString());
                email.setText(documentSnapshot.get("email").toString());
                ArrayList<String> cart = (ArrayList<String>) documentSnapshot.get("cart");
                int count = cart.size();
                noOfItemsInCart.setText(count + " items in wishlist");
            }
        });

        storageReference = storage.getReference().child("user_images/"+UID);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(userImage);
            }
        });

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(i);
            }
        });


        relativeLayout5 = findViewById(R.id.relativeLayout5);
        wishlistLayout = findViewById(R.id.wishlistLayout);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        relativeLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), profilePage.class);
                startActivity(i);
            }
        });

        wishlistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Wishlist.class);
                startActivity(i);
            }
        });

    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("remember");
        editor.apply();
        Intent loginIntent = new Intent(getApplicationContext(), LogPage.class);
        startActivity(loginIntent);
        finish();
    }
}