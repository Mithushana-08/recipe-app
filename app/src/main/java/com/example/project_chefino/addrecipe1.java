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

    private EditText nameInput, categoryInput, ingredientsInput, descriptionInput;
    private Button addButton;

    // Firebase reference
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 2;
    private ImageView imgRecipePreview;
    private VideoView videoRecipePreview;
    private Uri imageUri, videoUri;
    private String imageUrl, vedioUrl;
    private StorageReference storageRef;
    private Button btnUploadImage, btnUploadVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe1);

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

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST);
            }
        });

        btnUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_VIDEO_REQUEST);
            }
        });

        // Set the add button click listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFiles();
            }
        });
    }

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType(requestCode == PICK_IMAGE_REQUEST ? "image/*" : "video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                imageUri = data.getData();
                imgRecipePreview.setImageURI(imageUri); // Preview selected image
            } else if (requestCode == PICK_VIDEO_REQUEST) {
                videoUri = data.getData();
                videoRecipePreview.setVideoURI(videoUri); // Preview selected video
                videoRecipePreview.start(); // Play video
            }
        }
    }

    private void uploadFiles() {
        if (imageUri != null && videoUri != null) {
            uploadImageAndVideo(); // Handle both uploads
        } else if (imageUri != null) {
            uploadImage(); // Only image upload
        } else if (videoUri != null) {
            uploadVideo(); // Only video upload
        } else {
            // If neither image nor video is selected, directly add the recipe without media
            addRecipeToFirebase();
        }
    }

    private void uploadImage() {
        StorageReference fileRef = storageRef.child("images/" + System.currentTimeMillis() + ".jpg");
        fileRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageUrl = uri.toString();
                                System.out.println("Image uploaded: " + imageUrl);
                                checkAndAddRecipe(); // Check if both URLs are ready
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(addrecipe1.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void uploadVideo() {
        StorageReference fileRef = storageRef.child("videos/" + System.currentTimeMillis() + ".mp4");
        fileRef.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                vedioUrl = uri.toString();
                                System.out.println("Video uploaded: " + vedioUrl);
                                checkAndAddRecipe(); // Check if both URLs are ready
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(addrecipe1.this, "Video upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void uploadImageAndVideo() {
        uploadImage();
        uploadVideo();
    }

    private void checkAndAddRecipe() {
        if (imageUrl != null && vedioUrl != null) {
            addRecipeToFirebase(); // Both URLs are ready, add recipe to Firebase
        }
    }

    private void addRecipeToFirebase() {
        // Get the input from the EditText fields
        System.out.println("Video URL: " + vedioUrl);
        System.out.println("Image URL: " + imageUrl);
        String name = nameInput.getText().toString();
        String category = categoryInput.getText().toString();
        String ingredients = ingredientsInput.getText().toString();
        String description = descriptionInput.getText().toString();

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

        // Create a unique key for each recipe entry
        String recipeId = databaseReference.push().getKey();

        // Create a Recipe object
        RecipeAdding recipe = new RecipeAdding(name, description, imageUrl, ingredients, vedioUrl,recipeId,category);

        // Save to Firebase
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