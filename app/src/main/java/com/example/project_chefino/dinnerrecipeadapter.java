package com.example.project_chefino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class dinnerrecipeadapter extends RecyclerView.Adapter<dinnerrecipeadapter.RecipeViewHolder> {

    private List<LunchRecipe> recipeList;

    public dinnerrecipeadapter(List<LunchRecipe> recipeList) {
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
        Glide.with(holder.itemView.getContext())
                .load(recipe.getimage())
                .into(holder.recipeImageView);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeNameTextView;
        TextView recipeDescriptionTextView;
        ImageView recipeImageView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipeNameTextView);
            recipeDescriptionTextView = itemView.findViewById(R.id.recipeDescriptionTextView);
            recipeImageView = itemView.findViewById(R.id.recipeImageView);
        }
    }
}
