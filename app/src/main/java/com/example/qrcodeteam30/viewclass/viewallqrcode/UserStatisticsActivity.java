package com.example.qrcodeteam30.viewclass.viewallqrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.controllerclass.StatisticsController;
import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.viewclass.MainActivity;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Display the statistics of the QR Codes the user have (max, min, sum, average)
 */
public class UserStatisticsActivity extends AppCompatActivity {
    private String username;
    private TextView textView;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_statistics);
        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        username = getIntent().getStringExtra("Username");
        game = (Game) getIntent().getSerializableExtra("Game");

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(UserStatisticsActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        var intent = new Intent(UserStatisticsActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", username);
            intent.putExtra("Game", game);
            startActivity(intent);
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReferenceSignInInformation = db.collection("SignInInformation");
        final DocumentReference documentReference = collectionReferenceSignInInformation.document(username);

        textView = findViewById(R.id.user_statistics_textView);

        documentReference.addSnapshotListener((value, error) -> {
            if (value != null) {
                textView.setText(StatisticsController.userStatistic(value, game));
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