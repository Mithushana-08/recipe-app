package com.example.project_chefino;




import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView, signUpTextView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Assuming your XML is named login.xml

        getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Bind views
        emailEditText = findViewById(R.id.editTextEmail2);
        passwordEditText = findViewById(R.id.editTextPassword2);
        loginButton = findViewById(R.id.login);
        forgotPasswordTextView = findViewById(R.id.forgotpw);
        signUpTextView = findViewById(R.id.signup);

        // Login button onClick listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        // Sign up TextView onClick listener
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open sign-up activity
                Intent signUpIntent = new Intent(login.this, signup.class);
                startActivity(signUpIntent);
            }
        });

        // Forgot password onClick listener
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open forgot password activity
                Intent forgotIntent = new Intent(login.this, forgot.class);
                startActivity(forgotIntent);
            }
        });
    }

    // Method to login the user
    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please enter your email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Please enter your password");
            return;
        }

        // Firebase authentication for logging in
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful, navigate to HomeActivity
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Intent homeIntent = new Intent(login.this,home.class);
                        startActivity(homeIntent);
                        finish(); // Close login activity
                    } else {
                        // Login failed, show error message
                        Toast.makeText(login.this, "Authentication failed. Check email or password", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
