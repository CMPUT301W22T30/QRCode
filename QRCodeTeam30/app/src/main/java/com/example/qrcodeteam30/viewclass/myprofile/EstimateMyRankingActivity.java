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
import com.example.qrcodeteam30.controllerclass.CalculateScoreController;
import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.viewclass.MainActivity;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
            int cfMax = 0;
            int fMax = 0;
            int cfCount = 0;
            int fCount = 0;
            int cfScore = 0;
            int fScore = 0;
            int numQRCodes = 0;
            int numDocs = 0;

            HashSet<String> allQRCodeHashSetString = new HashSet<>();
            ArrayList<Double> allQRCodeArr = new ArrayList<>();
            ArrayList<Double> scoreArr = new ArrayList<>();
            ArrayList<Integer> countArr = new ArrayList<>();

            for (var queryDocumentSnapshot : value) {
                UserInformation userInformation = queryDocumentSnapshot.toObject(UserInformation.class);
                double totalScore = CalculateScoreController.calculateTotalScore(userInformation);
                if (totalScore < score) {
                    cfScore++;
                } else if (totalScore == score) {
                    fScore++;
                }
                scoreArr.add(totalScore);

                if (userInformation.getQrCodeList().size() < count) {
                    cfCount++;
                } else if (userInformation.getQrCodeList().size() == count) {
                    fCount++;
                }
                countArr.add(userInformation.getQrCodeList().size());

                for (var qrCode: userInformation.getQrCodeList()) {
                    if (qrCode.getScore() < max) {
                        cfMax++;
                    } else if (qrCode.getScore() == max) {
                        fMax++;
                    }
                    numQRCodes++;

                    if (!allQRCodeHashSetString.contains(qrCode.getQrCodeContent())) {
                        allQRCodeHashSetString.add(qrCode.getQrCodeContent());
                        allQRCodeArr.add(qrCode.getScore());
                    }
                }

                numDocs++;
            }
            double topPercentileRankSum = 100 - (cfScore + (0.5 * fScore)) / numDocs * 100;
            double topPercentileRankMax = 100 - (cfMax + (0.5 * fMax)) / numQRCodes * 100;
            double topPercentileRankCount = 100 - (cfCount + (0.5 * fCount)) / numDocs * 100;

            BigDecimal strippedValSum = new BigDecimal(Double.toString(topPercentileRankSum)).setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
            BigDecimal strippedValMax = new BigDecimal(Double.toString(topPercentileRankMax)).setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
            BigDecimal strippedValCount = new BigDecimal(Double.toString(topPercentileRankCount)).setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();

            textView.setText(String.format(Locale.CANADA,
                    "Total Score Percentile: Top %s%%\n\nMax Score Percentile: Top %s%%\n\nNumber of Barcodes Percentile: Top %s%%",
                    strippedValSum.toPlainString(), strippedValMax.toPlainString(), strippedValCount.toPlainString()));


            allQRCodeArr.sort(Collections.reverseOrder());
            scoreArr.sort(Collections.reverseOrder());
            countArr.sort(Collections.reverseOrder());

            int rankMax = allQRCodeArr.indexOf(max);
            int rankScore = scoreArr.indexOf(score);
            int rankCount = countArr.indexOf(count);

            if (max == -1) {
                textViewExact.setText(
                        String.format(Locale.CANADA, "Rank Total Score: %d/%d\n\nRank Number of Barcodes: %d/%d",
                                rankScore + 1, allQRCodeArr.size(), rankCount + 1, countArr.size()));
            } else {
                textViewExact.setText(
                        String.format(Locale.CANADA, "Rank Total Score: %d/%d\n\nRank Unique Max Score: %d/%d\n\nRank Number of Barcodes: %d/%d",
                                rankScore + 1, scoreArr.size(), rankMax + 1, allQRCodeArr.size(), rankCount + 1, countArr.size()));
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