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

public class lunch2 extends AppCompatActivity {// Declare the lunch2 class extending AppCompatActivity

    private RecyclerView lunchRecyclerView;
    private LunchRecipeAdapter lunchRecipeAdapter;
    private List<LunchRecipe> recipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch2);

        lunchRecyclerView = findViewById(R.id.lunchRecyclerView);// Get the RecyclerView from the layout
        lunchRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list to hold recipes
        recipeList = new ArrayList<>();


        lunchRecipeAdapter = new LunchRecipeAdapter(recipeList);// Create the adapter with the recipe list
        lunchRecyclerView.setAdapter(lunchRecipeAdapter);// Set the adapter to the RecyclerView
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser(); // Get the current logged-in user
        String userId = currentUser.getUid();// Retrieve the user ID


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("recipes/lunch");// Get reference to the "recipes/lunch" node in Firebase
        //String recipeId = databaseReference.push().getKey();


        // Fetch data from Firebase and update the list
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {// Callback for data change
                    String name = snapshot.child("name").getValue(String.class);//get information
                    String cal = snapshot.child("cal").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    String preTime = snapshot.child("pre_time").getValue(String.class);
                    String vedio = snapshot.child("vedio").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);

                    // Check if the recipe is bookmarked by checking the "bookmarked" node
                    DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmarked").child(userId).child(name);
                    bookmarkRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot bookmarkSnapshot) {// Callback for bookmark data change
                            boolean isBookmarked = bookmarkSnapshot.exists();

                            // Create a new LunchRecipe object
                            LunchRecipe lunchRecipe = new LunchRecipe(name, preTime, image, cal, isBookmarked,vedio,id,"lunch");
                            recipeList.add(lunchRecipe);

                            // Notify the adapter that data has changed
                            lunchRecipeAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {// Handle database errors
                            // Handle possible errors
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {// Handle database errors
                // Handle possible errors
            }
        });
    }
    }
