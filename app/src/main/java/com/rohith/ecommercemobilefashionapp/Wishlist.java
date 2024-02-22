package com.rohith.ecommercemobilefashionapp;//package com.rohith.ecommercemobilefashionapp;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.Tasks;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//public class Wishlist extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//    FirebaseAuth firebaseAuth;
//    FirebaseFirestore userFirestore, productFirestore;
//    DocumentReference userDocumentRef;
//    CollectionReference orderReference;
//    ArrayList<ProductSchema> products;
//    TextView addressStreet, addressCity;
//    Button checkOut;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wishlist);
//
//        addressStreet = findViewById(R.id.address_street);
//        addressCity = findViewById(R.id.address_city);
//
//        recyclerView = findViewById(R.id.cartlayout);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        checkOut = findViewById(R.id.checkout);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        String UID = firebaseAuth.getCurrentUser().getUid();
//        userFirestore = FirebaseFirestore.getInstance();
//        userDocumentRef = userFirestore.collection("user").document(UID);
//        productFirestore = FirebaseFirestore.getInstance();
//
//        products = new ArrayList<>();
//
//        userDocumentRef.get().addOnSuccessListener(documentSnapshot -> {
//            Map<String, Object> addressData = (Map<String, Object>) documentSnapshot.get("address");
//            String city = (String) addressData.get("city");
//            String street = (String) addressData.get("street");
//
//            addressStreet.setText(street);
//            addressCity.setText(city);
//
//            ArrayList<String> cart = (ArrayList<String>) documentSnapshot.get("cart");
//            List<Task<Void>> tasks = new ArrayList<>();
//
//            for (String productId : cart) {
//                DocumentReference productDocumentRef = productFirestore.collection("Products").document(productId);
//                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("product_images/" + productId);
//
//                // Add tasks for each asynchronous operation
//                Task<ProductSchema> productTask = productDocumentRef.get().continueWith(task -> {
//                    DocumentSnapshot productSnapshot = task.getResult();
//                    return productSnapshot.toObject(ProductSchema.class);
//                });
//
//                Task<Uri> downloadUrlTask = storageRef.getDownloadUrl();
//
//                // Chain tasks to handle completion
//                Task<Void> combinedTask = Tasks.whenAllSuccess(productTask, downloadUrlTask).continueWithTask(task -> {
//                    ProductSchema newProduct = productTask.getResult();
//                    Uri uri = downloadUrlTask.getResult();
//
//                    if (newProduct != null) {
//                        newProduct.setImageUrl(uri);
//                        products.add(newProduct);
//                    } else {
//                        Log.e("Wishlist", "Product is null");
//                    }
//
//                    return null;
//                });
//
//                // Add the combined task to the list
//                tasks.add(combinedTask);
//            }
//
//            // Wait for all tasks to complete
//            Tasks.whenAllComplete(tasks).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    // All tasks completed successfully, update UI
//                    cartAdapter cartAdapter = new cartAdapter(products, Wishlist.this);
//                    recyclerView.setAdapter(cartAdapter);
//                } else {
//                    // Handle errors
//                    Log.e("Wishlist", "Error in one or more tasks", task.getException());
//                }
//            });
//        }).addOnFailureListener(e -> Log.e("Wishlist", "Error fetching user document", e));
//
//        OnProductDataLoadedListener listener = new OnProductDataLoadedListener() {
//        };
//
//        checkOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                userDocumentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        ArrayList<String> cartProducts = (ArrayList<String>) documentSnapshot.get("cart");
//                        orderReference = FirebaseFirestore.getInstance().collection("orders");
//                        Map<String, Object> addressData = (Map<String, Object>) documentSnapshot.get("address");
//                        Address address = new Address(addressData.get("street").toString(),addressData.get("city").toString(),addressData.get("state").toString(),addressData.get("country").toString(),addressData.get("zipCode").toString());
//                        Date currentDate = new Date();
//                        ArrayList<ProductSchema> orderedProduct = new ArrayList<>();
//                        for (String s : cartProducts) {
//                            orderedProduct.add(getProductData(s,listener));
//                        }
//                        Order newOrder = new Order(documentSnapshot.get("username").toString(), documentSnapshot.getId(), documentSnapshot.get("email").toString(), documentSnapshot.get("phone").toString(), address, currentDate, orderedProduct, false, null);
//                        orderReference.add(newOrder).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Toast.makeText(getApplicationContext(), "Ordered Successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//            }
//        });
//
//    }
//
//    public ProductSchema getProductData(String s,OnProductDataLoadedListener listener) {
//
//        DocumentReference products = FirebaseFirestore.getInstance().collection("Products").document(s);
//        StorageReference productImage = FirebaseStorage.getInstance().getReference().child("product_images/" + s);
//        products.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                ProductSchema product = new ProductSchema();
//                product.setProductName(documentSnapshot.get("productName").toString());
//                product.setPrice(documentSnapshot.get("price").toString());
//                product.setCategory(documentSnapshot.get("category").toString());
//                product.setDescription(documentSnapshot.get("description").toString()) ;
//                product.setId(documentSnapshot.getId());
//                product.setGender(documentSnapshot.get("gender").toString()) ;
//                product.setSize(documentSnapshot.get("size").toString());
//                product.setBrand(documentSnapshot.get("brand").toString());
//                product.setAvailable(documentSnapshot.get("available").toString());
//                product.setSellerName(documentSnapshot.get("sellerName").toString());
//                product.setSellerCompany(documentSnapshot.get("sellerCompany").toString());
//                productImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        product.setImageUrl(uri);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.i("Tag",""+e);
//                        return;
//                    }
//                });
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("Tag",""+e);
//                return;
//            }
//        });
//        return null;
//    }
//
//    public interface OnProductDataLoadedListener {
//        void onProductDataLoaded(ProductSchema product);
//        void onProductDataError(String errorMessage);
//    }
//
//}

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rohith.ecommercemobilefashionapp.Address;
import com.rohith.ecommercemobilefashionapp.Order;
import com.rohith.ecommercemobilefashionapp.ProductSchema;
import com.rohith.ecommercemobilefashionapp.R;
import com.rohith.ecommercemobilefashionapp.cartAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Wishlist extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore userFirestore, productFirestore;
    DocumentReference userDocumentRef;
    CollectionReference orderReference;
    ArrayList<ProductSchema> products;
    TextView addressStreet, addressCity;
    Button checkOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        addressStreet = findViewById(R.id.address_street);
        addressCity = findViewById(R.id.address_city);

        recyclerView = findViewById(R.id.cartlayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkOut = findViewById(R.id.checkout);

        firebaseAuth = FirebaseAuth.getInstance();
        String UID = firebaseAuth.getCurrentUser().getUid();
        userFirestore = FirebaseFirestore.getInstance();
        userDocumentRef = userFirestore.collection("user").document(UID);
        productFirestore = FirebaseFirestore.getInstance();

        products = new ArrayList<>();

        userDocumentRef.get().addOnSuccessListener(documentSnapshot -> {
            Map<String, Object> addressData = (Map<String, Object>) documentSnapshot.get("address");
            String city = (String) addressData.get("city");
            String street = (String) addressData.get("street");

            addressStreet.setText(street);
            addressCity.setText(city);

            ArrayList<String> cart = (ArrayList<String>) documentSnapshot.get("cart");
            List<Task<Void>> tasks = new ArrayList<>();

            if (cart.size() == 0) {
                Intent i = new Intent(getApplicationContext(), EmptyCart.class);
                startActivity(i);
            }
            else {
                for (String productId : cart) {
                    getProductData(productId, new OnProductDataLoadedListener() {
                        @Override
                        public void onProductDataLoaded(ProductSchema product) {
                            products.add(product);

                            if (products.size() == cart.size()) {
                                cartAdapter cartAdapter = new cartAdapter(products, Wishlist.this);
                                recyclerView.setAdapter(cartAdapter);
                            }
                        }

                        @Override
                        public void onProductDataError(String errorMessage) {
                            Log.e("Wishlist", errorMessage);
                        }
                    });
                }
            }
        }).addOnFailureListener(e -> Log.e("Wishlist", "Error fetching user document", e));

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDocumentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ArrayList<String> cartProducts = (ArrayList<String>) documentSnapshot.get("cart");
                        orderReference = FirebaseFirestore.getInstance().collection("orders");
                        Map<String, Object> addressData = (Map<String, Object>) documentSnapshot.get("address");
                        Address address = new Address(addressData.get("street").toString(), addressData.get("city").toString(), addressData.get("state").toString(), addressData.get("country").toString(), addressData.get("zipCode").toString());
                        Date currentDate = new Date();
                        ArrayList<ProductSchema> orderedProduct = new ArrayList<>();
                        for (String s : cartProducts) {
                            getProductData(s, new OnProductDataLoadedListener() {
                                @Override
                                public void onProductDataLoaded(ProductSchema product) {
                                    orderedProduct.add(product);

                                    if (orderedProduct.size() == cartProducts.size()) {
                                        // All products loaded, create order
                                        Order newOrder = new Order(documentSnapshot.get("username").toString(), documentSnapshot.getId(), documentSnapshot.get("email").toString(), documentSnapshot.get("phone").toString(), address, currentDate, orderedProduct, false, null);
                                        orderReference.add(newOrder).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                ArrayList<String> cartUpdated = new ArrayList();
                                                userDocumentRef.update("cart",new ArrayList<String>());
                                                Intent i = new Intent(getApplicationContext(), orderConfirmed.class);
                                                startActivity(i);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("Wishlist", "Error creating order", e);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onProductDataError(String errorMessage) {
                                    Log.e("Wishlist", errorMessage);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public void getProductData(String s, OnProductDataLoadedListener listener) {
        DocumentReference productRef = FirebaseFirestore.getInstance().collection("Products").document(s);
        StorageReference productImageRef = FirebaseStorage.getInstance().getReference().child("product_images/" + s);

        productRef.get().addOnSuccessListener(documentSnapshot -> {
            ProductSchema product = documentSnapshot.toObject(ProductSchema.class);
            if (product != null) {
                product.setId(documentSnapshot.getId());
                productImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    product.setImageUrl(uri);
                    listener.onProductDataLoaded(product);
                }).addOnFailureListener(e -> {
                    Log.e("Wishlist", "Error getting product image URL", e);
                    listener.onProductDataError("Error getting product image URL");
                });
            } else {
                Log.e("Wishlist", "Product is null");
                listener.onProductDataError("Product is null");
            }
        }).addOnFailureListener(e -> {
            Log.e("Wishlist", "Error getting product data", e);
            listener.onProductDataError("Error getting product data");
        });
    }

    public interface OnProductDataLoadedListener {
        void onProductDataLoaded(ProductSchema product);
        void onProductDataError(String errorMessage);
    }
}
