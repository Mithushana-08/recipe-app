package com.example.project_chefino;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class home extends AppCompatActivity {

    private ImageView profileButton, searchButton, savedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Assuming your layout file is activity_home.xml


        // Change the status bar color to black
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));

        // Initialize buttons
        Button breakfastButton = findViewById(R.id.button1);
        Button lunchButton = findViewById(R.id.button2);
        Button dinnerButton = findViewById(R.id.button3);
        Button dessertButton = findViewById(R.id.button4);
        FloatingActionButton fab = findViewById(R.id.fab); // Floating Action Button

        profileButton = findViewById(R.id.imageView13);
        searchButton = findViewById(R.id.imageView7); // Search button (ImageView)
        savedButton = findViewById(R.id.imageView9); // New Saved button (ImageView)
        // Set onClick listeners for each button
        breakfastButton.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, breakfast2.class);
            startActivity(intent);
        });

        dessertButton.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, dessert2.class);
            startActivity(intent);
        });
        lunchButton.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, lunch2.class);
            startActivity(intent);
        });

        dinnerButton.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, dinner2.class);
            startActivity(intent);
        });

        // Set onClickListener for Floating Action Button to navigate to the Add Recipe page
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, addrecipe1.class); // Replace with your Add Recipe activity class
            startActivity(intent);
        });

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, profile11.class);
            startActivity(intent);
        });

        // Set onClickListener for Search Button to navigate to the Search page
        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, SearchActivity.class); // Replace with your Search activity class
            startActivity(intent);
        });

        // Set onClickListener for Saved Button to navigate to the Saved page
        savedButton.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, save.class); // Replace with your Saved activity class
            startActivity(intent);
        });
    }
}
