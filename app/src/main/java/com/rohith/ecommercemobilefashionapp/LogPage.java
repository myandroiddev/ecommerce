package com.rohith.ecommercemobilefashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogPage extends AppCompatActivity {
    Button button2, button3;
    ImageView backBtn;
    EditText email, password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_page);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        checkBox();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Signup.class);
                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void checkBox() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("remember", "");
        if ("true".equals(check)) {
            Intent i = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(i);
            finish();
        }
    }

    public void login() {
        if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {
            if (firebaseAuth.getCurrentUser() != null) {
                FirebaseAuth.getInstance().signOut();
            }

            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    String UID = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference df = firestore.collection("user").document(UID);
                    df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String isUser = documentSnapshot.getString("isUser");
                            if ("1".equals(isUser)) {
                                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("remember", "true");
                                editor.apply();
                                startActivity(i);
                            } else if ("0".equals(isUser)) {
                                Log.d("TAg","Admin");
                                Intent adminActivity = new Intent(getApplicationContext(), AddProudct.class);
                                startActivity(adminActivity);
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void logout(View v) {
        FirebaseAuth.getInstance().signOut();

        // Clear the remember flag from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("remember");
        editor.apply();

        // Redirect the user to the login page
        Intent loginIntent = new Intent(getApplicationContext(), LogPage.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

}
