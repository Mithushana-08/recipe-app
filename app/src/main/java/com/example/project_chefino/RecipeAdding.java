package com.example.project_chefino;

// Recipe model class
public class RecipeAdding {
    private String name;
    private String cal; // This can be the preparation time or a separate description
    private String image; // Change this to String to hold the URL
    private String pre_time; // Added for calorie count
    private String vedio;
    private String id;
    private String category;

    // Default constructor required for calls to DataSnapshot.getValue(LunchRecipe.class)
    public RecipeAdding() {
    }

    public RecipeAdding(String Name, String pre_time, String image, String cal,String vedioUrl,String id,String category) {
        this.name=Name;
        this.cal=cal;
        this.pre_time=pre_time;
        this.image=image;
        this.vedio=vedioUrl;
        this.id=id;
        this.category=category;
    }


    // Getters
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }

    public String getPre_time() {
        return pre_time;
    }

    public String getImage() {
        return image; // This returns the image URL
    }
    public String getVedio() {
        return vedio; // This returns the image URL
    }

    public String getCal() {
        return cal; // This returns the calorie count
    }
    public String getId() {
        return id; // This returns the calorie count
    }
}
