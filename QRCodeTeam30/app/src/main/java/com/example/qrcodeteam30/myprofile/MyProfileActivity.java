package com.example.qrcodeteam30.myprofile;

import android.content.DialogInterface;
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

import com.example.qrcodeteam30.MainActivity;
import com.example.qrcodeteam30.PlayerMenuActivity;
import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

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
    TextView textViewInfo;
    Button buttonEstimateRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
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


        sessionUsername = getIntent().getStringExtra("SessionUsername");
        db = FirebaseFirestore.getInstance();
        collectionReferenceSignInInformation = db.collection("SignInInformation");
        documentReference = collectionReferenceSignInInformation.document(sessionUsername);

        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
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
            startActivity(intent);
        });


        buttonGenerateQRCode.setOnClickListener(v -> {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = null;
            try {
                final String qrCodeFormat = String.format(Locale.CANADA, "%s %s", sessionUsername, password);
                bitmap = barcodeEncoder.encodeBitmap(qrCodeFormat, BarcodeFormat.QR_CODE, 1000, 1000);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        });

    }

    @Override
    protected void onStart() {
        listenerRegistration = documentReference.addSnapshotListener((value, error) -> {
            UserInformation userInformation = value.toObject(UserInformation.class);
            password = userInformation.getPassword();

            textViewInfo.setText(String.format(Locale.CANADA, "Name: %s %s\nUsername: @%s",
                    userInformation.getFirstName(), userInformation.getLastName(), userInformation.getUsername()));

            final var temp = userInformation.getQrCodeList();
            if (temp.size() == 0) {
                buttonEstimateRanking.setOnClickListener(v -> {
                    Intent intent = new Intent(MyProfileActivity.this, EstimateMyRankingActivity.class);
                    intent.putExtra("SessionUsername", userInformation.getUsername());
                    intent.putExtra("Max", -1);
                    intent.putExtra("Count", temp.size());
                    intent.putExtra("Score", userInformation.getScore());
                    startActivity(intent);
                });
                return;
            }

            double max = temp.get(0).getScore();
            for (QRCode qrCode: temp) {
                if (qrCode.getScore() > max) {
                    max = qrCode.getScore();
                }
            }

            final double finalMax = max;
            buttonEstimateRanking.setOnClickListener(v -> {
                Intent intent = new Intent(MyProfileActivity.this, EstimateMyRankingActivity.class);
                intent.putExtra("SessionUsername", userInformation.getUsername());
                intent.putExtra("Max", finalMax);
                intent.putExtra("Count", temp.size());
                intent.putExtra("Score", userInformation.getScore());
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