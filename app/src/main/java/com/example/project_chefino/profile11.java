package com.example.project_chefino;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class profile11 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile11);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        // Get the currently logged-in user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView myMailText = findViewById(R.id.htext);
        String email="Mahesh";
        if (currentUser != null) {
            // Get the email of the logged-in user
             email = currentUser.getEmail();

        } else{

        }
        myMailText.setText(email);
            // Find the TextViews and ImageViews by their ID
            TextView myAccountText = findViewById(R.id.text1);
            ImageView myAccountVector = findViewById(R.id.v1);
            ImageView navigateNext1 = findViewById(R.id.imageView1);

            TextView settingsText = findViewById(R.id.text2);
            ImageView settingsVector = findViewById(R.id.v2);
            ImageView navigateNext2 = findViewById(R.id.imageView2);

            TextView contactText = findViewById(R.id.text3);
            ImageView contactVector = findViewById(R.id.v3);
            ImageView navigateNext3 = findViewById(R.id.imageView3);

            TextView aboutText = findViewById(R.id.text4);
            ImageView aboutVector = findViewById(R.id.v4);
            ImageView navigateNext4 = findViewById(R.id.imageView4);

            TextView logoutText = findViewById(R.id.text5);
            ImageView logoutVector = findViewById(R.id.v5);
            ImageView navigateNext5 = findViewById(R.id.imageView5);

            // Set click listeners for My Account Text and Icons
            myAccountText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirect to MainActivity2
                    Intent intent = new Intent(profile11.this, myAccount.class);
                    startActivity(intent);
                }
            });

            myAccountVector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, myAccount.class);
                    startActivity(intent);
                }
            });

            navigateNext1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, myAccount.class);
                    startActivity(intent);
                }
            });

            // Set click listeners for Settings Text and Icons
            settingsText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirect to MainActivity2 or any other activity
                    Intent intent = new Intent(profile11.this, settings.class);
                    startActivity(intent);
                }
            });

            settingsVector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, settings.class);
                    startActivity(intent);
                }
            });

            navigateNext2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, settings.class);
                    startActivity(intent);
                }
            });

            // Set click listeners for Contact us Text and Icons
            contactText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirect to a ContactActivity
                    Intent intent = new Intent(profile11.this, aboutus.class);
                    startActivity(intent);
                }
            });

            contactVector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, aboutus.class);
                    startActivity(intent);
                }
            });

            navigateNext3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, aboutus.class);
                    startActivity(intent);
                }
            });

            // Set click listeners for About us Text and Icons
            aboutText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirect to an AboutActivity
                    Intent intent = new Intent(profile11.this, aboutus.class);
                    startActivity(intent);
                }
            });

            aboutVector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, aboutus.class);
                    startActivity(intent);
                }
            });

            navigateNext4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, aboutus.class);
                    startActivity(intent);
                }
            });

            // Set click listeners for About us Text and Icons
            logoutText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirect to an AboutActivity
                    Intent intent = new Intent(profile11.this,MainActivity.class);
                    startActivity(intent);
                }
            });

            logoutVector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            navigateNext4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile11.this, MainActivity.class);
                    startActivity(intent);
                }
            });
    }
}