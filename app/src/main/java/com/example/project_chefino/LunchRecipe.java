package com.example.project_chefino;

public class LunchRecipe {// declare class
    private String name;// Name of the recipe
    private String description; // This can be the preparation time or a separate description
    private String image; // Change this to String to hold the URL
    private String cal; // Added for calorie count
    private boolean isBookmarked;// Indicates if the recipe is bookmarked by the user
    private String vedio;
    private String id;
    private String category;

    // Default constructor required for calls to DataSnapshot.getValue(LunchRecipe.class)
    public LunchRecipe() {
    }

    public LunchRecipe(String name, String preTime, String image, String cal, boolean isBookmarked,String video,String id,String category) {
        this.name = name;
        this.description = preTime;
        this.image = image;
        this.isBookmarked = isBookmarked;
        this.vedio=video;
        this.id=id;
        this.category=category;
    }


    // Getters
    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name=name;
    }

    public String getcategory() {
        return category;
    }

    public String getpre_time() {
        return description;
    }

    public String getimage() {
        return image; // This returns the image URL
    }
    public String getvedio() {
        return vedio; // This returns the image URL
    }
    public void setvideo(String video) {
        this.vedio=video;
    }
    public String getcal() {
        return cal; // This returns the calorie count
    }
    public String getid() {
        return id; // This returns the calorie count
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}