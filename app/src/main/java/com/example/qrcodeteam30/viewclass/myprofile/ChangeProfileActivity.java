package com.example.qrcodeteam30.viewclass.myprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.viewclass.MainActivity;
import com.example.qrcodeteam30.controllerclass.MyCryptographyController;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;
import com.example.qrcodeteam30.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.NoSuchAlgorithmException;

/**
 * An interface for changing the profile of the user (password, first name, last name)
 */
public class ChangeProfileActivity extends AppCompatActivity {
    private String sessionUsername;
    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        game = (Game) getIntent().getSerializableExtra("Game");

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ChangeProfileActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        var intent = new Intent(ChangeProfileActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Game", game);
            startActivity(intent);
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final var collectionReferenceSignInInformation = db.collection("SignInInformation");

        EditText editTextFirstName = findViewById(R.id.editText_changeProfile_firstName);
        EditText editTextLastName = findViewById(R.id.editText_changeProfile_lastName);
        EditText editTextPassword = findViewById(R.id.editText_changeProfile_password);
        Button buttonUpdate = findViewById(R.id.button_myProfile_update);

        buttonUpdate.setOnClickListener(v -> {
            final var firstNameUpdate = editTextFirstName.getText().toString();
            final var lastNameUpdate = editTextLastName.getText().toString();
            final var passwordUpdate = editTextPassword.getText().toString();

            final DocumentReference documentReference = collectionReferenceSignInInformation.document(sessionUsername);

            if (firstNameUpdate.length() > 0) {
                documentReference.update("firstName", firstNameUpdate);
                Toast.makeText(getApplicationContext(), "Update First Name Success", Toast.LENGTH_SHORT).show();
                editTextFirstName.setText("");
            }
            if (lastNameUpdate.length() > 0) {
                documentReference.update("lastName", lastNameUpdate);
                Toast.makeText(getApplicationContext(), "Update Last Name Success", Toast.LENGTH_SHORT).show();
                editTextLastName.setText("");
            }
            if (passwordUpdate.length() > 0) {
                try {
                    documentReference.update("password", MyCryptographyController.hashSHA256(passwordUpdate));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Update Password Success", Toast.LENGTH_SHORT).show();
                editTextPassword.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}