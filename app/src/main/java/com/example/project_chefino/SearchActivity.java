package com.example.project_chefino;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private androidx.appcompat.widget.SearchView searchView;
    private RecyclerView recyclerView;
    private LunchRecipeAdapter recipeAdapter; // Custom adapter for your RecyclerView
    private ArrayList<LunchRecipe> recipeList; // A list to hold recipe objects
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("recipes");

        // Initialize UI components
        searchView = findViewById(R.id.search);
        // Access the TextView inside the SearchView to change the text color
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

// Set the text color and hint color
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeList = new ArrayList<>();
        recipeAdapter = new LunchRecipeAdapter( recipeList); // Custom adapter
        recyclerView.setAdapter(recipeAdapter);

        // Set up the SearchView listener
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    searchRecipes(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    searchRecipes(newText);
                } else {
                    recipeList.clear(); // Clear the list if search is empty
                    recipeAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    // Function to search recipes in Firebase
    private void searchRecipes(String searchText) {
        // Get a reference to the root node "recipes"
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear(); // Clear the previous search results

                if (dataSnapshot.exists()) {
                    for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                        // Iterate over each recipe in the category
                        for (DataSnapshot recipeSnapshot : categorySnapshot.getChildren()) {
                            LunchRecipe recipe = recipeSnapshot.getValue(LunchRecipe.class);

                            if (recipe != null && recipe.getname().toLowerCase().contains(searchText.toLowerCase())) {
                                recipeList.add(recipe); // Add matching recipes to the list
                            }
                        }
                    }

                    recipeAdapter.notifyDataSetChanged(); // Notify the adapter of new data

                    if (recipeList.isEmpty()) {
                        Toast.makeText(SearchActivity.this, "No recipes found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}