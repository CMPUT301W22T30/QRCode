package com.example.qrcodeteam30.viewclass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.controllerclass.MyCryptographyController;
import com.example.qrcodeteam30.controllerclass.MyFirestoreUploadController;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.NoSuchAlgorithmException;

/**
 * This activity is the interface for sign up.
 * Upon sign up completion, it automatically launches PlayerMenuActivity (The main menu).
 */
public class SignUpActivity extends AppCompatActivity {
    static private boolean retrievedSignUpInformation = false;  // Behave as a lock/mutex
    MyFirestoreUploadController myFirestoreUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        myFirestoreUpload = new MyFirestoreUploadController(getApplicationContext());

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setVisibility(View.GONE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final var collectionReferenceSignInInformation = db.collection("SignInInformation");

        EditText editTextUsername = findViewById(R.id.signup_username_editText);
        EditText editTextPassword = findViewById(R.id.signup_password_editText);
        EditText editTextConfirmPassword = findViewById(R.id.signup_confirm_password_editText);
        EditText editTextFirstName = findViewById(R.id.signup_firstname_editText);
        EditText editTextLastName = findViewById(R.id.signup_lastname_editText);
        Button buttonSignUp = findViewById(R.id.signupactivity_sign_up_button);
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setVisibility(View.GONE);

        buttonSignUp.setOnClickListener(v -> {
            final var username = editTextUsername.getText().toString();
            final var password = editTextPassword.getText().toString();
            final var firstName = editTextFirstName.getText().toString();
            final var lastName = editTextLastName.getText().toString();
            if (username.length() == 0 || password.length() == 0 || firstName.length() == 0 || lastName.length() == 0) {
                return;
            }
            if (!editTextConfirmPassword.getText().toString().equals(password)) {
                Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_SHORT).show();
                return;
            }

            final DocumentReference documentReference = collectionReferenceSignInInformation.document(username);

            documentReference.get().addOnCompleteListener(task -> {
                // If retrieving document from FireStore, then clicking this button will not do anything
                if (retrievedSignUpInformation) {
                    return;
                }
                // Retrieve document from FireStore
                retrievedSignUpInformation = true;

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getApplicationContext(), "This username is already existed", Toast.LENGTH_SHORT).show();
                    } else {
                        UserInformation userInformation = null;
                        try {
                            userInformation = new UserInformation(username, MyCryptographyController.hashSHA256(password), firstName, lastName, 0);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        // Add new user credential to Firestore
                        myFirestoreUpload.signUpNewUser(userInformation);

                        Toast.makeText(getApplicationContext(), "Sign up success", Toast.LENGTH_SHORT).show();

                        retrievedSignUpInformation = false;

                        // Auto sign-in after sign up
                        var intent = new Intent(SignUpActivity.this, PlayerMenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("SessionUsername", username);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Log.d("Firestore", "get SignInInformation document failed with ", task.getException());
                }
                // Retrieving document complete
                retrievedSignUpInformation = false;
            });
        });
    }

    @Override
    protected void onResume() {
        retrievedSignUpInformation = false;
        super.onResume();
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