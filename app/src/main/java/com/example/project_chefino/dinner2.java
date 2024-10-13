package com.example.project_chefino;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dinner2 extends AppCompatActivity {

    // Declare the RecyclerView and the adapter for displaying recipes
    private RecyclerView lunchRecyclerView;
    private LunchRecipeAdapter lunchRecipeAdapter;
    private List<LunchRecipe> recipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner2);     // Set the layout for the activity

        // Initialize the RecyclerView and set its layout manager (Linear layout)
        lunchRecyclerView = findViewById(R.id.lunchRecyclerView);
        lunchRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list to store recipe objects
        recipeList = new ArrayList<>();

        // Get the currently authenticated user to access user-specific data
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        // Initialize the adapter with the list and set it to RecyclerView
        lunchRecipeAdapter = new LunchRecipeAdapter(recipeList);
        lunchRecyclerView.setAdapter(lunchRecipeAdapter);

        // Get reference to the Firebase database for "recipes/dinner"
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("recipes/dinner");

        // Fetch data from Firebase and update the list
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Extract recipe information from each snapshot
                    String name = snapshot.child("name").getValue(String.class);
                    String cal = snapshot.child("cal").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    String preTime = snapshot.child("pre_time").getValue(String.class);
                    String vedio = snapshot.child("vedio").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);

                    // Check if the recipe is bookmarked by the current user
                    DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmarked").child(userId).child(name);

                    // Add a listener to check if the recipe exists in the user's bookmarked list
                    bookmarkRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot bookmarkSnapshot) {
                            boolean isBookmarked = bookmarkSnapshot.exists(); // True if recipe is already bookmarked

                            // Create a new LunchRecipe object with the retrieved data
                            LunchRecipe lunchRecipe = new LunchRecipe(name, preTime, image, cal, isBookmarked,vedio,id,"dinner");
                            recipeList.add(lunchRecipe);    // Add the recipe to the list

                            // Notify the adapter that the dataset has changed so the RecyclerView can update
                            lunchRecipeAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors related to database access
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
}
