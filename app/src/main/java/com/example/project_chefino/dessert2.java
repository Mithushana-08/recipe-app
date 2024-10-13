package com.example.project_chefino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_chefino.LunchRecipe;
import com.example.project_chefino.LunchRecipeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dessert2 extends AppCompatActivity {

    // RecyclerView to display dessert recipes
    private RecyclerView lunchRecyclerView;

    // Adapter to manage the data displayed in the RecyclerView
    private LunchRecipeAdapter lunchRecipeAdapter;

    // List to store the fetched recipes from Firebase
    private List<LunchRecipe> recipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert1); // Set the layout for the activity

        // Initialize RecyclerView for desserts
        lunchRecyclerView = findViewById(R.id.dessertRecyclerView);
        lunchRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set layout manager for RecyclerView


        // Initialize an empty list to hold the recipe data
        // Initialize the list
        recipeList = new ArrayList<>();

        // Initialize the adapter with the list and set it to RecyclerView
        // Initialize the adapter with the recipe list and set it to RecyclerView
        lunchRecipeAdapter = new LunchRecipeAdapter(recipeList);
        lunchRecyclerView.setAdapter(lunchRecipeAdapter); // Attach adapter to the RecyclerView

        // Get the currently authenticated user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid(); // Fetch the current user's ID

        // Get a reference to the "recipes/dessert" node in Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("recipes/dessert");

        // Fetch data from Firebase and update the list   // Fetch data from Firebase and listen for changes
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear(); // Clear the list before adding new data // Clear the list before adding new data to avoid duplicates

                // Loop through all the child nodes under "recipes/dessert"
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Fetch individual recipe details from each child node
                    String name = snapshot.child("name").getValue(String.class);
                    String cal = snapshot.child("cal").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    String preTime = snapshot.child("pre_time").getValue(String.class);
                    String vedio = snapshot.child("vedio").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);
                    // Check if the recipe is bookmarked by checking the "bookmarked" node
                    // Reference to the user's "bookmarked" recipes to check if this recipe is bookmarked
                    DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmarked").child(userId).child(name);

                    // Check if the recipe is bookmarked
                    bookmarkRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot bookmarkSnapshot) {
                            boolean isBookmarked = bookmarkSnapshot.exists(); // True if recipe is already bookmarked // True if the recipe is bookmarked


                            // Create a new LunchRecipe object // Create a new LunchRecipe object with the fetched data
                            LunchRecipe lunchRecipe = new LunchRecipe(name, preTime, image, cal, isBookmarked,vedio,id,"dessert");

                            // Add the recipe to the recipe list
                            recipeList.add(lunchRecipe);

                            // Notify the adapter that data has changed // Notify the adapter that the dataset has changed, so it updates the UI
                            lunchRecipeAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle possible errors // Handle errors that occur while fetching bookmark data
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors // Handle errors that occur while fetching recipe data from Firebase
            }
        });
    }
}
