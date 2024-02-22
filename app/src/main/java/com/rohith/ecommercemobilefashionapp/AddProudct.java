//package com.rohith.ecommercemobilefashionapp;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class AddProudct extends AppCompatActivity {
//    FirebaseStorage firebaseStorage;
//    StorageReference storageReference;
//    FirebaseFirestore firestore;
//    Button uploadBtn, submitBtn;
//    ImageView product_img;
//    Uri imageUri;
//    EditText product_name, price, description, category,gender,brand,available,sellerName,sellerCompany;
//    String productId;
//    ArrayList<String> size;
//    ToggleButton xs,small,medium,large,xl,xxl;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_proudct);
//        uploadBtn = findViewById(R.id.photo_upload_btn);
//        firebaseStorage = FirebaseStorage.getInstance();
//        storageReference = firebaseStorage.getReference();
//        product_name = findViewById(R.id.product_name);
//        price = findViewById(R.id.price);
//        description = findViewById(R.id.description);
//        submitBtn = findViewById(R.id.image_submit_btn);
//        category = findViewById(R.id.category);
//        gender = findViewById(R.id.gender);
//        brand = findViewById(R.id.brand);
//        available = findViewById(R.id.no_of_units);
//        sellerName = findViewById(R.id.seller_name);
//        sellerCompany = findViewById(R.id.seller_company);
//        firestore = FirebaseFirestore.getInstance();
//        xs = findViewById(R.id.size_xs);
//        small = findViewById(R.id.size_small);
//        medium = findViewById(R.id.size_medium);
//        large = findViewById(R.id.size_large);
//        xl = findViewById(R.id.size_xl);
//        xxl = findViewById(R.id.size_2xl);
//
//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent();
//                i.setType("image/*");
//                i.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(i, 100);
//            }
//        });
//
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                uploadImg();
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            imageUri = data.getData();
//            // Now you have the imageUri, you can display it or perform other actions.
//        } else {
//            // Handle the case where the image URI is null or the user canceled the operation.
//            Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void uploadImg() {
//        if (imageUri != null) {
//            size = new ArrayList<>();
//            if (xs.isChecked()) {
//                size.add("xs");
//            }
//            if (small.isChecked()) {
//                size.add("s");
//            }
//            if (medium.isChecked()) {
//                size.add("m");
//            }
//            if (large.isChecked()) {
//                size.add("l");
//            }
//            if (xl.isChecked()) {
//                size.add("xl");
//            }
//            if (xxl.isChecked()) {
//                size.add("xs");
//            }
//            HashMap<String, Object> newproduct = new HashMap<>();
//            newproduct.put("productName", product_name.getText().toString());
//            newproduct.put("price", price.getText().toString());
//            newproduct.put("category", category.getText().toString());
//            newproduct.put("description", description.getText().toString());
//            newproduct.put("gender",gender.getText().toString());
//            newproduct.put("size",size);
//            newproduct.put("brand",brand.getText().toString());
//            newproduct.put("available",available.getText().toString());
//            newproduct.put("sellerName",sellerName.getText().toString());
//            newproduct.put("sellerCompany",sellerCompany.getText().toString());
//            newproduct.put("uri",null);
//            ArrayList<String> comments = new ArrayList<>();
//            newproduct.put("comments", comments);
//            ArrayList<Integer> ratings = new ArrayList<>();
//            ratings.add(0);
//            newproduct.put("ratings", ratings);
//
//            firestore.collection("Products").add(newproduct).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                @Override
//                public void onSuccess(DocumentReference documentReference) {
//                    productId = documentReference.getId();
//
//                    storageReference.child("product_images/" + productId).putFile(imageUri)
//                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            Toast.makeText(getApplicationContext(),"Added successfully",Toast.LENGTH_SHORT).show();
//                                            documentReference.update("uri",uri).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
//                                                    Intent i = new Intent(getApplicationContext(), AddProudct.class);
//                                                    startActivity(i);
//                                                }
//                                            });
//
//                                        }
//                                    });
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                }
//            });
//        } else {
//            Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
//        }
//    }
//}


package com.rohith.ecommercemobilefashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AddProudct extends AppCompatActivity {
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore firestore;
    Button uploadBtn, submitBtn;
    ImageView product_img;
    Uri imageUri;
    EditText product_name, price, description, category, gender, brand, available, sellerName, sellerCompany;
    String productId;
    ArrayList<String> size;
    ToggleButton xs, small, medium, large, xl, xxl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_proudct);
        uploadBtn = findViewById(R.id.photo_upload_btn);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        product_name = findViewById(R.id.product_name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        submitBtn = findViewById(R.id.image_submit_btn);
        category = findViewById(R.id.category);
        gender = findViewById(R.id.gender);
        brand = findViewById(R.id.brand);
        available = findViewById(R.id.no_of_units);
        sellerName = findViewById(R.id.seller_name);
        sellerCompany = findViewById(R.id.seller_company);
        firestore = FirebaseFirestore.getInstance();
        xs = findViewById(R.id.size_xs);
        small = findViewById(R.id.size_small);
        medium = findViewById(R.id.size_medium);
        large = findViewById(R.id.size_large);
        xl = findViewById(R.id.size_xl);
        xxl = findViewById(R.id.size_2xl);


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 100);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImg();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Now you have the imageUri, you can display it or perform other actions.
        } else {
            // Handle the case where the image URI is null or the user canceled the operation.
            Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadImg() {
        if (imageUri != null) {
            size = new ArrayList<>();
            if (xs.isChecked()) {
                size.add("xs");
            }
            if (small.isChecked()) {
                size.add("s");
            }
            if (medium.isChecked()) {
                size.add("m");
            }
            if (large.isChecked()) {
                size.add("l");
            }
            if (xl.isChecked()) {
                size.add("xl");
            }
            if (xxl.isChecked()) {
                size.add("xxl");
            }
            HashMap<String, Object> newproduct = new HashMap<>();
            newproduct.put("productName", product_name.getText().toString());
            newproduct.put("price", price.getText().toString());
            newproduct.put("category", category.getText().toString());
            newproduct.put("description", description.getText().toString());
            newproduct.put("gender", gender.getText().toString());
            newproduct.put("size", size);
            newproduct.put("brand", brand.getText().toString());
            newproduct.put("available", available.getText().toString());
            newproduct.put("sellerName", sellerName.getText().toString());
            newproduct.put("sellerCompany", sellerCompany.getText().toString());
            newproduct.put("uri", null);
            ArrayList<Comment> comments = new ArrayList<>();
            newproduct.put("comments", comments);
            ArrayList<Integer> ratings = new ArrayList<>();
            ratings.add(0);
            newproduct.put("ratings", ratings);

            firestore.collection("Products").add(newproduct).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    productId = documentReference.getId();

                    storageReference.child("product_images/" + productId).putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                                            documentReference.update("uri", uri).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Intent i = new Intent(getApplicationContext(), AddProudct.class);
                                                    startActivity(i);
                                                }
                                            });

                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                                    Log.e("FirebaseUpload", "Failed to upload image: " + e.getMessage());
                                }
                            });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed to add product", Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreAdd", "Failed to add product: " + e.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
        }
    }
}
