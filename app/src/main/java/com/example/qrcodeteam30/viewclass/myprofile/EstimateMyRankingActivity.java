package com.example.qrcodeteam30.viewclass.myprofile;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Estimate the user's ranking in terms of Max score, Min score, Total score, the number of QR Code
 * Ranking is shown in Percentile
 */
public class EstimateMyRankingActivity extends AppCompatActivity {
    private String sessionUsername;
    private double max;
    private double score;
    private int count;
    private TextView textView;
    private TextView textViewExact;
    private ListenerRegistration listenerRegistration;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_my_ranking);

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        game = (Game) getIntent().getSerializableExtra("Game");

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(EstimateMyRankingActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        var intent = new Intent(EstimateMyRankingActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        textView = findViewById(R.id.textView_estimateRanking);
        textViewExact = findViewById(R.id.textView_estimateRanking_exactRanking);

        max = getIntent().getDoubleExtra("Max", -1);
        score = getIntent().getDoubleExtra("Score", -1);
        count = getIntent().getIntExtra("Count", -1);

        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Game", game);
            startActivity(intent);
        });

        if (count == 0) {
            textView.setText("You have no barcodes scanned");
        }


        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("SignInInformation");

    }

    @Override
    protected void onStart() {
        listenerRegistration = collectionReference.addSnapshotListener((value, error) -> {
            if (value == null) return;
            ArrayList<Object> objectArrayList = StatisticsController.estimateRanking(value, game, score, count, max);

            BigDecimal strippedValSum = new BigDecimal(Double.toString((double) objectArrayList.get(0))).setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
            BigDecimal strippedValMax = new BigDecimal(Double.toString((double) objectArrayList.get(1))).setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
            BigDecimal strippedValCount = new BigDecimal(Double.toString((double) objectArrayList.get(2))).setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();

            textView.setText(String.format(Locale.CANADA,
                    "Total Score Percentile: Top %s%%\n\nMax Score Percentile: Top %s%%\n\nNumber of Barcodes Percentile: Top %s%%",
                    strippedValSum.toPlainString(), strippedValMax.toPlainString(), strippedValCount.toPlainString()));

            int rankScore = (int) objectArrayList.get(3);
            int numberOfScore = (int) objectArrayList.get(4);
            int rankMax = (int) objectArrayList.get(5);
            int numberOfMax = (int) objectArrayList.get(6);
            int rankCount = (int) objectArrayList.get(7);
            int numberOfCount = (int) objectArrayList.get(8);

            if (max == -1) {
                textViewExact.setText(
                        String.format(Locale.CANADA, "Rank Total Score: %d/%d\n\nRank Number of Barcodes: %d/%d",
                                rankScore + 1, numberOfScore, rankCount + 1, numberOfCount));
            } else {
                textViewExact.setText(
                        String.format(Locale.CANADA, "Rank Total Score: %d/%d\n\nRank Unique Max Score: %d/%d\n\nRank Number of Barcodes: %d/%d",
                                rankScore + 1, numberOfScore, rankMax + 1, numberOfMax, rankCount + 1, numberOfCount));
            }
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