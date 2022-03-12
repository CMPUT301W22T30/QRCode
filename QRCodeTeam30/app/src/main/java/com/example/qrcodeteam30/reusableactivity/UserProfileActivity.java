package com.example.qrcodeteam30.reusableactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.MainActivity;
import com.example.qrcodeteam30.PlayerMenuActivity;
import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.controllerclass.MyFirestoreUpload;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.viewallqrcode.ViewAllQRCodeActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {
    private String username;
    private String sessionUsername;
    private TextView textView;
    private UserInformation userInformation;
    private ListenerRegistration listenerRegistration;
    private MyFirestoreUpload myFirestoreUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        myFirestoreUpload = new MyFirestoreUpload(getApplicationContext());

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(UserProfileActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        var intent = new Intent(UserProfileActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        textView = findViewById(R.id.textView_userProfile);
        Button buttonViewAllQRCode = findViewById(R.id.button_userProfile_viewAllQRCode);
        Button buttonDeleteUser = findViewById(R.id.button_userProfile_deleteUser);

        username = getIntent().getStringExtra("Username");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReferenceSignInInformation = db.collection("SignInInformation");
        DocumentReference documentReference = collectionReferenceSignInInformation.document(username);


        listenerRegistration = documentReference.addSnapshotListener((value, error) -> {
            if (value != null) {
                userInformation = value.toObject(UserInformation.class);
                textView.setText(String.format(Locale.CANADA, "Name: %s %s\nUsername: @%s",
                        userInformation.getFirstName(), userInformation.getLastName(), userInformation.getUsername()));
            }
        });

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        buttonViewAllQRCode.setOnClickListener(v -> {
            var intent = new Intent(UserProfileActivity.this, ViewAllQRCodeActivity.class);
            intent.putExtra("Username", username);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        if (sessionUsername.equals("admin") && !username.equals("admin")) {
            buttonDeleteUser.setOnClickListener(v -> {
                myFirestoreUpload.deleteUser(username);
                finish();
            });
        } else {
            buttonDeleteUser.setVisibility(View.GONE);
        }
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

    @Override
    protected void onPause() {
        listenerRegistration.remove();
        super.onPause();
    }
}