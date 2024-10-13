package com.example.project_chefino;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Delete1 extends AppCompatActivity {

    private ImageView backButton;
    private RadioGroup radioGroup;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        backButton = findViewById(R.id.imageView);
        radioGroup = findViewById(R.id.radioGroup);
        deleteButton = findViewById(R.id.button);

        // Set click listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Navigates to the previous activity
            }
        });

        // Set click listener for the delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDelete();
            }
        });
    }

    /**
     * Handles the delete button click event.
     * Validates if a reason is selected and navigates to the next activity.
     */
    private void handleDelete() {
        // Get the selected RadioButton's ID
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            // No RadioButton is selected
            Toast.makeText(Delete1.this, "Please select a reason for deleting your account.", Toast.LENGTH_SHORT).show();
        } else {
            // A RadioButton is selected
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedReason = selectedRadioButton.getText().toString();

            // Optionally, you can pass the selected reason to the next activity
            Intent intent = new Intent(Delete1.this, delete2.class);
            intent.putExtra("DELETION_REASON", selectedReason);
            startActivity(intent);
            // Optionally, finish the current activity if you don't want the user to return to it
            // finish();
        }

    }
}



