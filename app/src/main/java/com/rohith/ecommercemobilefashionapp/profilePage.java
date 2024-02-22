package com.rohith.ecommercemobilefashionapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.guieffect.qual.UI;

import java.util.HashMap;
import java.util.Map;

public class profilePage extends AppCompatActivity {
    private static final int Gallery_Req_Code = 1000;
    ImageView circleImageView;
    Button save;
    EditText username,email,phone,street,city,state,country,zipcode;
    String UID;
    StorageReference storageReference;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    FirebaseAuth firebaseAuth;
    DocumentReference df;
    ImageButton addImage;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        firestore = FirebaseFirestore.getInstance();
        save = findViewById(R.id.save);
        username = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);
        zipcode = findViewById(R.id.zipcode);
        storage = FirebaseStorage.getInstance();
        circleImageView = findViewById(R.id.circleImageView3);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = storage.getReference();
        UID = firebaseAuth.getCurrentUser().getUid();
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addImage = findViewById(R.id.add_Image);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Gallery_Req_Code);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        df = firestore.collection("user").document(UID);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    UserSchema user = documentSnapshot.toObject(UserSchema.class);
                    username.setText(user.getUsername());
                    email.setText(user.getEmail());
                    phone.setText(user.getPhone());
                    street.setText(user.address.getStreet());
                    city.setText(user.address.getCity());
                    state.setText(user.address.getState());
                    country.setText(user.address.getCountry());
                    zipcode.setText(user.address.getZipCode());
                }
            }
        });
        loadProfileImage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Gallery_Req_Code) {
                circleImageView.setImageURI(data.getData());
                Uri Selected_Image = data.getData();
                uploadImage(Selected_Image);
            }
        }

    }

    private void uploadImage(Uri imageUri) {
        if (imageUri != null) {
            StorageReference imageRef = storageReference.child("user_images/" + UID);
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(profilePage.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful uploads
                            Toast.makeText(profilePage.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void loadProfileImage () {
        StorageReference imageRef = storageReference.child("user_images/" + UID);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(circleImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "Error downloading image", e);
            }
        });

    }

    public void save() {

        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("street", street.getText().toString());
        addressMap.put("city", city.getText().toString());
        addressMap.put("state", state.getText().toString());
        addressMap.put("country", country.getText().toString());
        addressMap.put("zipCode", zipcode.getText().toString());


        Map<String,Object> updateData = new HashMap<>();
        updateData.put("username",username.getText().toString());
        updateData.put("email",email.getText().toString());
        updateData.put("phone",phone.getText().toString());
        updateData.put("address",addressMap);

        df.update(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent i = new Intent(getApplicationContext(), profilePage.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Saved successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}