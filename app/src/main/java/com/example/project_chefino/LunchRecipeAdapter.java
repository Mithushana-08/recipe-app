package com.example.project_chefino;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LunchRecipeAdapter extends RecyclerView.Adapter<LunchRecipeAdapter.RecipeViewHolder> {

    private List<LunchRecipe> recipeList;

    public LunchRecipeAdapter(List<LunchRecipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_lunch_item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        LunchRecipe recipe = recipeList.get(position);
        holder.recipeNameTextView.setText(recipe.getname());
        holder.recipeDescriptionTextView.setText(recipe.getpre_time());
        //holder.recipeImageView.setImageResource(recipe.getImage());
        Glide.with(holder.itemView.getContext())
                .load(recipe.getimage())
                .into(holder.recipeImageView);

        holder.recipeImageView.setOnClickListener(v -> {
            // Create an Intent to start the new activity
            Intent intent = new Intent(holder.itemView.getContext(), recipescrollview.class);

            // Pass the recipe ID as an extra to the new activity
            intent.putExtra("recipeId", recipe.getid()); // Assuming recipe.getId() gives the database ID
            intent.putExtra("category", recipe.getcategory()); // Assuming recipe.getId() gives the database ID
            intent.putExtra("video", recipe.getvedio()); // Assuming recipe.getId() gives the database ID
            System.out.println("asasas vedio this : "+recipe.getvedio());
            // Start the new activity
            holder.itemView.getContext().startActivity(intent);
        });

        // Set the bookmark icon based on the bookmark status
        if (recipe.isBookmarked()) {
            holder.bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_24); // Filled bookmark
        } else {
            holder.bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_border_24); // Empty bookmark
        }

        // Handle bookmark icon click
        holder.bookmarkIcon.setOnClickListener(v -> {
            boolean isBookmarked = recipe.isBookmarked();

            if (isBookmarked) {
                // Remove bookmark from Firebase
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser.getUid();
                String currentName="name";
                if(currentUser!=null){
                    currentName=currentUser.toString();
                }
                DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmarked").child(userId).child(recipe.getname());
                bookmarkRef.removeValue();
                holder.bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_border_24); // Set to unfilled
            } else {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String currentName="name";
                String userId = currentUser.getUid();
                if(currentUser!=null){
                    currentName=currentUser.toString();
                }
                // Save bookmark to Firebase under the "bookmarked" node
                DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmarked").child(userId).child(recipe.getname());
                bookmarkRef.setValue(recipe);  // Save the recipe object under the user's bookmarked node
                holder.bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_24); // Set to filled
            }

            // Toggle the bookmark status locally
            recipe.setBookmarked(!isBookmarked);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeNameTextView;
        TextView recipeDescriptionTextView;
        ImageView recipeImageView;
        ImageView bookmarkIcon;


        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipeNameTextView);
            recipeDescriptionTextView = itemView.findViewById(R.id.recipeDescriptionTextView);
            recipeImageView = itemView.findViewById(R.id.recipeImageView);
            bookmarkIcon = itemView.findViewById(R.id.bookmarkIcon);
        }
    }
}
