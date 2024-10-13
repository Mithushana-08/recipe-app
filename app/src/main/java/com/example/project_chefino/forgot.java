package com.example.project_chefino;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);  // Link to your forgot.xml layout
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize the views
        emailInput = findViewById(R.id.emailorphone);  // EditText
        submitButton = findViewById(R.id.submit2);      // Button

        // Set an onClickListener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get email from the EditText
                String email = emailInput.getText().toString().trim();

                // Check if email is empty
                if (email.isEmpty()) {
                    emailInput.setError("Email is required");
                    emailInput.requestFocus();
                    return;
                }

                // Optionally, validate email format
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInput.setError("Please provide a valid email");
                    emailInput.requestFocus();
                    return;
                }

                // Call the resetPassword method
                resetPassword(email);
            }
        });
    }

    // Method to reset the password using Firebase
    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(forgot.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(forgot.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
