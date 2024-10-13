package com.example.project_chefino;

public class LunchRecipe {// declare class
    private String name;
    private String description;
    private String image;
    private String cal;
    private boolean isBookmarked;
    private String vedio;
    private String id;
    private String category;

    // Default constructor required for calls to DataSnapshot.getValue(LunchRecipe.class)
    public LunchRecipe() {
    }
    // Parameterized constructor to initialize a LunchRecipe object with given values
    public LunchRecipe(String name, String preTime, String image, String cal, boolean isBookmarked,String video,String id,String category) {
        this.name = name;// Set the recipe name
        this.description = preTime;
        this.image = image;
        this.isBookmarked = isBookmarked;
        this.vedio=video;
        this.id=id;
        this.category=category;
    }


    // Getters & setters
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
    }  // Method to check if the recipe is bookmarked

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
} // Setter for the bookmark status