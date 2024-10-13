package com.example.project_chefino;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class recipescrollview extends AppCompatActivity {
    private TextView ingredientsText, recipeName;
    private VideoView videoView;
    private String recipeId, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipescrollview);

        // Initialize Views
        videoView = findViewById(R.id.recipe_video_view1);
        Button addReviewButton = findViewById(R.id.btn_add_review);
        ingredientsText = findViewById(R.id.tv_ingredients_list);
        recipeName = findViewById(R.id.recipeName);

        // Get Intent data
        recipeId = getIntent().getStringExtra("recipeId");
        category = getIntent().getStringExtra("category");

        // Load recipe data
        if (recipeId != null && category != null) {
            loadRecipeData(recipeId, category);
        } else {
            Toast.makeText(this, "Invalid recipe data", Toast.LENGTH_SHORT).show();
        }

        // Add media controls for the VideoView
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        // Add functionality for the "Add Review" button
        addReviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(recipescrollview.this, review1.class);
            startActivity(intent);
        });
    }

    private void loadRecipeData(String recipeId, String categoryThis) {
        DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("recipes").child(categoryThis).child(recipeId);
        recipeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    RecipeAdding recipe = snapshot.getValue(RecipeAdding.class);
                    if (recipe != null) {
                        // Set the retrieved data to the respective fields
                        recipeName.setText(recipe.getName());
                        ingredientsText.setText(recipe.getCal() != null ? recipe.getCal() : "No ingredients listed");

                        // Set up video if video URL is available
                        String videoUrl = recipe.getVedio(); // Use the corrected getter
                        if (videoUrl != null && !videoUrl.isEmpty()) {
                            Uri videoUri = Uri.parse(videoUrl);
                            videoView.setVideoURI(videoUri);
                            videoView.start(); // Start the video after setting URI
                        } else {
                            Toast.makeText(recipescrollview.this, "No video available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(recipescrollview.this, "Error retrieving recipe data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(recipescrollview.this, "Recipe not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(recipescrollview.this, "Failed to load recipe: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}