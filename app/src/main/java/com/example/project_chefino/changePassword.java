package com.example.project_chefino;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class changePassword extends AppCompatActivity {

    private EditText etCurrentPassword, etNewPassword, etConfirmPassword;
    private Button btnUpdatePassword;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);
        backButton = findViewById(R.id.imageView);

        // Handle back button click (goes to previous page)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish current activity, return to the previous screen
                finish();
            }
        });

        // Handle update password button click
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = etCurrentPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                    // If any field is empty, show an error
                    Toast.makeText(changePassword.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    // If new password and confirm password don't match, show an error
                    Toast.makeText(changePassword.this, "New Password and Confirm Password don't match", Toast.LENGTH_SHORT).show();
                } else if (newPassword.equals(currentPassword)) {
                    // If the new password matches the current password, show an error
                    Toast.makeText(changePassword.this, "New password can't be the same as the current password", Toast.LENGTH_SHORT).show();
                } else {
                    // Password update success
                    Toast.makeText(changePassword.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                    // Redirect to settings page (replace 'SettingsActivity.class' with your actual settings page activity)
                    Intent intent = new Intent(changePassword.this, settings.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}