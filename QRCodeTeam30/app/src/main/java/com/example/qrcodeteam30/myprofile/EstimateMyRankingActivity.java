package com.example.qrcodeteam30.myprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.MainActivity;
import com.example.qrcodeteam30.PlayerMenuActivity;
import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigDecimal;
import java.util.Locale;

public class EstimateMyRankingActivity extends AppCompatActivity {
    private String sessionUsername;
    private double max;
    private double score;
    private int count;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_my_ranking);

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

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        max = getIntent().getDoubleExtra("Max", -1);
        score = getIntent().getDoubleExtra("Score", -1);
        count = getIntent().getIntExtra("Count", -1);

        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        if (count == 0) {
            textView.setText("You have no barcodes scanned");
            return;
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("SignInInformation");
        collectionReference.addSnapshotListener((value, error) -> {
            int cfMax = 0;
            int fMax = 0;
            int cfCount = 0;
            int fCount = 0;
            int cfScore = 0;
            int fScore = 0;
            int numQRCodes = 0;
            int numDocs = 0;

            for (var queryDocumentSnapshot : value) {
                UserInformation userInformation = queryDocumentSnapshot.toObject(UserInformation.class);
                if (userInformation.getScore() < score) {
                    cfScore++;
                } else if (userInformation.getScore() == score) {
                    fScore++;
                }

                if (userInformation.getQrCodeList().size() < count) {
                    cfCount++;
                } else if (userInformation.getQrCodeList().size() == count) {
                    fCount++;
                }
                for (var qrCode: userInformation.getQrCodeList()) {
                    if (qrCode.getScore() < max) {
                        cfMax++;
                    } else if (qrCode.getScore() == max) {
                        fMax++;
                    }
                    numQRCodes++;
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