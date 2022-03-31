package com.example.qrcodeteam30.viewclass.myprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.controllerclass.CalculateScoreController;
import com.example.qrcodeteam30.controllerclass.MyBitmapController;
import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.viewclass.MainActivity;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Locale;

/**
 * Showing the user information (username, name)
 * Includes button to launch activity to change profile, estimate QR Codes ranking
 * Include button to show a unique QR Code for automatic log in
 */
public class MyProfileActivity extends AppCompatActivity {
    private String sessionUsername;
    private String password;
    private FirebaseFirestore db;
    private CollectionReference collectionReferenceSignInInformation;
    private DocumentReference documentReference;
    private ListenerRegistration listenerRegistration;
    private TextView textViewInfo;
    private Button buttonEstimateRanking;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        game = (Game) getIntent().getSerializableExtra("Game");

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(MyProfileActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        db = FirebaseFirestore.getInstance();
        collectionReferenceSignInInformation = db.collection("SignInInformation");
        documentReference = collectionReferenceSignInInformation.document(sessionUsername);

        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Game", game);
            startActivity(intent);
        });

        textViewInfo = findViewById(R.id.textView_myprofile_info);
        Button buttonChangeProfile = findViewById(R.id.button_myProfile_changeProfile);
        Button buttonGenerateQRCode = findViewById(R.id.button_myProfile_generateQRCode);
        buttonEstimateRanking = findViewById(R.id.button_myProfile_myRanking);
        ImageView imageView = findViewById(R.id.MyProfileImageView);

        buttonChangeProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MyProfileActivity.this, ChangeProfileActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Game", game);
            startActivity(intent);
        });


        buttonGenerateQRCode.setOnClickListener(v -> {
            final String qrCodeContent = String.format(Locale.CANADA, "%s %s", sessionUsername, password);
            Bitmap bitmap = MyBitmapController.displayQRCode(qrCodeContent, "QR_CODE");
            imageView.setImageBitmap(bitmap);
        });

    }

    @Override
    protected void onStart() {
        listenerRegistration = documentReference.addSnapshotListener((value, error) -> {
            UserInformation userInformation = value.toObject(UserInformation.class);
            password = userInformation.getPassword();

            if (userInformation.getFirstName().equals("") && userInformation.getLastName().equals("")) {
                textViewInfo.setText(String.format(Locale.CANADA, "Username: @%s", userInformation.getUsername()));
            } else {
                textViewInfo.setText(String.format(Locale.CANADA, "Name: %s %s\nUsername: @%s",
                        userInformation.getFirstName(), userInformation.getLastName(), userInformation.getUsername()));
            }

            final var temp = userInformation.getQrCodeList();
            if (temp.size() == 0) {
                buttonEstimateRanking.setOnClickListener(v -> {
                    Intent intent = new Intent(MyProfileActivity.this, EstimateMyRankingActivity.class);
                    intent.putExtra("SessionUsername", userInformation.getUsername());
                    intent.putExtra("Max", -1);
                    intent.putExtra("Count", temp.size());
                    intent.putExtra("Score", CalculateScoreController.calculateTotalScore(userInformation));
                    intent.putExtra("Game", game);
                    startActivity(intent);
                });
                return;
            }

            final double max = userInformation.maxScoreQRCode();
            buttonEstimateRanking.setOnClickListener(v -> {
                Intent intent = new Intent(MyProfileActivity.this, EstimateMyRankingActivity.class);
                intent.putExtra("SessionUsername", userInformation.getUsername());
                intent.putExtra("Max", max);
                intent.putExtra("Count", temp.size());
                intent.putExtra("Score", CalculateScoreController.calculateTotalScore(userInformation));
                intent.putExtra("Game", game);
                startActivity(intent);
            });
        });
        super.onStart();
    }


    @Override
    protected void onStop() {
        listenerRegistration.remove();
        super.onStop();
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