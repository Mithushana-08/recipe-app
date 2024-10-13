package com.example.project_chefino;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ImageView;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ImageView; // Correct import

public class editProfile1 extends AppCompatActivity {

        private ImageView box1, box2, profileImage, backButton, home, search, bookmark, account;
        private AppCompatButton changePictureButton, updateButton;
        private EditText usernameInput, passwordInput, emailInput, phoneNumberInput;


        @SuppressLint("WrongViewCast")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_profile1); // Make sure this matches your layout XML file name

            // Linking XML views to Java code
            profileImage = findViewById(R.id.profileImage);
            backButton = findViewById(R.id.imageView); // Assuming this is your back button




            changePictureButton = findViewById(R.id.button1);
            updateButton = findViewById(R.id.button2);

            usernameInput = findViewById(R.id.textInput1);
            passwordInput = findViewById(R.id.textInput2);
            emailInput = findViewById(R.id.textInput3);
            phoneNumberInput = findViewById(R.id.textInput4);

            // Set click listener for 'Change Picture' button
            changePictureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform action to change the profile picture
                    Toast.makeText(editProfile1.this, "Change Picture clicked", Toast.LENGTH_SHORT).show();
                    // Add logic to change the picture here
                }
            });

            // Set click listener for 'Update' button
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get text from input fields and process the update
                    String username = usernameInput.getText().toString();
                    String password = passwordInput.getText().toString();
                    String email = emailInput.getText().toString();
                    String phoneNumber = phoneNumberInput.getText().toString();

                    // Perform validation or send the data to the server
                    if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                        Toast.makeText(editProfile1.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(editProfile1.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        // Add logic for profile update here
                        // Go back to the previous activity
                        Intent intent = new Intent(editProfile1.this, settings.class); // Replace with the correct previous activity
                        startActivity(intent);
                        finish();
                    }
                }
            });

            // Set click listener for the back button
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Close the activity and return to the previous one
                }
            });
    }
}