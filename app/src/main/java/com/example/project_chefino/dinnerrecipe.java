package com.example.project_chefino;

public class dinnerrecipe {
    private String name;
    private String description;
    private int imageResourceId;

    // Constructor
    public  dinnerrecipe(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Getter for image resource ID
    public int getImageResourceId() {
        return imageResourceId;
    }
}
