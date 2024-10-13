package com.example.project_chefino;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

// Main Activity for adding a recipe
public class addrecipe1 extends AppCompatActivity {

    // UI elements
    private EditText nameInput, categoryInput, ingredientsInput, descriptionInput;
    private Button addButton;

    // Firebase reference  // Firebase references for database and storage
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static final int PICK_IMAGE_REQUEST = 1; // Constant for image file chooser request code
    private static final int PICK_VIDEO_REQUEST = 2; // Constant for image file chooser request code

    // UI elements for previewing selected media
    private ImageView imgRecipePreview;
    private VideoView videoRecipePreview;
    private Uri imageUri, videoUri;// To hold selected image and video URIs
    private String imageUrl, vedioUrl; // To store uploaded media URLs from Firebase
    private StorageReference storageRef; // Firebase storage reference
    private Button btnUploadImage, btnUploadVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe1);  // Set the layout for this activity

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Link UI elements to Java code
        nameInput = findViewById(R.id.textInput1);
        categoryInput = findViewById(R.id.textInput2);
        ingredientsInput = findViewById(R.id.textInput3);
        descriptionInput = findViewById(R.id.textInput4);
        addButton = findViewById(R.id.btn_add);
        imgRecipePreview = findViewById(R.id.img_recipe_preview);
        videoRecipePreview = findViewById(R.id.video_recipe_preview);
        btnUploadImage = findViewById(R.id.btn_upload_image);
        btnUploadVideo = findViewById(R.id.btn_upload_video);
        storageRef = FirebaseStorage.getInstance().getReference("uploads");

        // Set the button click listener for image upload
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST); // Open file chooser for selecting an image
            }
        });
        // Set the button click listener for video upload
        btnUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_VIDEO_REQUEST); // Open file chooser for selecting a video
            }
        });

        // Set the add button click listener to upload files and submit the recipe
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFiles();    // Upload media files and submit the recipe data
            }
        });
    }

    // Method to open file chooser for selecting either an image or video based on requestCode
    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType(requestCode == PICK_IMAGE_REQUEST ? "image/*" : "video/*"); // Select image or video type
        intent.setAction(Intent.ACTION_GET_CONTENT); // Set intent action to get content
        startActivityForResult(Intent.createChooser(intent, "Select File"), requestCode);  // Start file chooser
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                imageUri = data.getData(); // Store selected image URI
                imgRecipePreview.setImageURI(imageUri); // Preview selected image
            } else if (requestCode == PICK_VIDEO_REQUEST) {
                videoUri = data.getData(); // Store selected video URI
                videoRecipePreview.setVideoURI(videoUri); // Preview selected video
                videoRecipePreview.start(); // automatically Play selected video
            }
        }
    }
    // Method to handle uploading both image and video or either one
    private void uploadFiles() {
        if (imageUri != null && videoUri != null) {
            uploadImageAndVideo(); // If both image and video are selected, upload both
        } else if (imageUri != null) {
            uploadImage(); // If only image is selected, upload image
        } else if (videoUri != null) {
            uploadVideo(); // If only video is selected, upload video
        } else {
            // If neither image nor video is selected, directly add the recipe without media
            addRecipeToFirebase();
        }
    }
    // Method to upload the selected image to Firebase Storage
    private void uploadImage() {
        StorageReference fileRef = storageRef.child("images/" + System.currentTimeMillis() + ".jpg"); // Generate unique image name
        fileRef.putFile(imageUri)// Upload the image file to Firebase Storage
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageUrl = uri.toString(); // Store the image URL for later use
                                System.out.println("Image uploaded: " + imageUrl);
                                checkAndAddRecipe();// Check if both image and video URLs are available
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(addrecipe1.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Method to upload the selected video to Firebase Storage
    private void uploadVideo() {
        StorageReference fileRef = storageRef.child("videos/" + System.currentTimeMillis() + ".mp4");  // Generate unique video name

        fileRef.putFile(videoUri) // Upload the video file to Firebase Storage
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                vedioUrl = uri.toString(); // Store the video URL for later use
                                System.out.println("Video uploaded: " + vedioUrl);
                                checkAndAddRecipe(); // Check if both image and video URLs are available
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(addrecipe1.this, "Video upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    // Method to upload both image and video
    private void uploadImageAndVideo() {
        uploadImage(); // Upload the image first
        uploadVideo(); // Then upload the video
    }
    // Method to check if both image and video URLs are available before submitting the recipe
    private void checkAndAddRecipe() {
        if (imageUrl != null && vedioUrl != null) {
            addRecipeToFirebase(); // Both URLs are ready, add recipe to Firebase
        }
    }

    // Method to add the recipe to Firebase Realtime Database
    private void addRecipeToFirebase() {
        // Get the input from the EditText fields
        System.out.println("Video URL: " + vedioUrl);
        System.out.println("Image URL: " + imageUrl);
        String name = nameInput.getText().toString();
        String category = categoryInput.getText().toString();
        String ingredients = ingredientsInput.getText().toString();
        String description = descriptionInput.getText().toString();

        // Check which category the recipe belongs to and get the corresponding database reference
        if (category.equalsIgnoreCase("lunch")) {
            databaseReference = firebaseDatabase.getReference("recipes/lunch");
        } else if (category.equalsIgnoreCase("dinner")) {
            databaseReference = firebaseDatabase.getReference("recipes/dinner");
        } else if (category.equalsIgnoreCase("breakfast")) {
            databaseReference = firebaseDatabase.getReference("recipes/breakfast");
        } else if (category.equalsIgnoreCase("dessert")) {
            databaseReference = firebaseDatabase.getReference("recipes/dessert");
        }

        // Validate that none of the fields are empty
        if (name.isEmpty() || category.isEmpty() || ingredients.isEmpty() || description.isEmpty()) {
            Toast.makeText(addrecipe1.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a unique key for the recipe entry in Firebase
        String recipeId = databaseReference.push().getKey();

        // Create a Recipe object with the gathered details
        RecipeAdding recipe = new RecipeAdding(name, description, imageUrl, ingredients, vedioUrl,recipeId,category);

        // Save the recipe to Firebase Realtime Database
        databaseReference.child(recipeId).setValue(recipe)
                .addOnCompleteListener(task -> {
                    System.out.println("asasas id: "+recipeId);
                    if (task.isSuccessful()) {
                        Toast.makeText(addrecipe1.this, "Recipe added successfully", Toast.LENGTH_SHORT).show();
                        // Optionally clear the fields after submission
                        nameInput.setText("");
                        categoryInput.setText("");
                        ingredientsInput.setText("");
                        descriptionInput.setText("");
                    } else {
                        Toast.makeText(addrecipe1.this, "Failed to add recipe", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}