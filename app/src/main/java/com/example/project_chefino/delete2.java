package com.example.project_chefino;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class delete2 extends AppCompatActivity {

    private ImageView backButton;
    private Button deleteButton, keepAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        backButton = findViewById(R.id.imageView);
        deleteButton = findViewById(R.id.button3);
        keepAccountButton = findViewById(R.id.button4);

        // Set click listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Navigates to the previous activity
            }
        });

        // Set click listener for the Delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDeleteAction();
            }
        });

        // Set click listener for the Keep Account button
        keepAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleKeepAccountAction();
            }
        });
    }

    /**
     * Handles the Delete button click event.
     * Redirects the user to the MainActivity.
     */
    private void handleDeleteAction() {
        // Optionally, you can add confirmation dialogs or deletion logic here

        // Create an Intent to navigate to MainActivity
        Intent intent = new Intent(delete2.this, MainActivity.class);
        startActivity(intent);

        // Optional: Finish the current activity so it's removed from the back stack
        finish();

        // Optional: Show a Toast message indicating successful deletion
        // Toast.makeText(Delete2.this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the Keep Account button click event.
     * Redirects the user to the HomeActivity.
     */
    private void handleKeepAccountAction() {
        // Create an Intent to navigate to HomeActivity
        Intent intent = new Intent(delete2.this, home.class);
        startActivity(intent);

        // Optional: Finish the current activity so it's removed from the back stack
        finish();

        // Optional: Show a Toast message indicating the user decided to keep the account
        // Toast.makeText(Delete2.this, "Account Kept Successfully", Toast.LENGTH_SHORT).show();
    }

    /**
     * Overrides the default back button behavior if needed.
     * Currently, it behaves the same as the default implementation.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Navigates to the previous activity
    }
    }
