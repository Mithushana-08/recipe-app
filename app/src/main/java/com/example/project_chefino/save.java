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

public class save extends AppCompatActivity {

    private RecyclerView lunchRecyclerView;//display a list of recipes
    private LunchRecipeAdapter_new lunchRecipeAdapter;//handles the display of recipe items in the RecyclerView.
    private List<LunchRecipe> recipeList;//that holds LunchRecipe objects, which represent the individual recipes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_new_2);//Sets the layout for this activity using the XML file

        //Gets a reference to the RecyclerView defined in the layout XML.
        lunchRecyclerView = findViewById(R.id.lunchRecyclerView);
        //Sets a layout manager for the RecyclerView, arranging items in a vertical list.
        lunchRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list
        recipeList = new ArrayList<>();

        // Initialize the adapter with the list and set it to RecyclerView
        lunchRecipeAdapter = new LunchRecipeAdapter_new(recipeList); //Retrieves the currently authenticated user
        lunchRecyclerView.setAdapter(lunchRecipeAdapter);//Gets the unique user ID
        //Creates a reference to the "bookmarked" node in the Firebase database specific to the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();//
        String userId = currentUser.getUid();
        // Get Firebase reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bookmarked/"+userId);

        // Fetch data from Firebase and update the list
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Fetch each field directly
                    String name = snapshot.child("name").getValue(String.class);
                    String cal = snapshot.child("cal").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    String preTime = snapshot.child("pre_time").getValue(String.class);
                    String vedio = snapshot.child("vedio").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);

                    // Create a new LunchRecipe object
                    LunchRecipe lunchRecipe = new LunchRecipe(name, preTime, image, cal,false,vedio,id,"not defined");
                    recipeList.add(lunchRecipe);
                }
                // Notify the adapter that data has changed
                lunchRecipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
}