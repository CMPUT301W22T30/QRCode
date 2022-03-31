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

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.viewclass.MainActivity;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;
import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Display the statistics of the QR Codes the user have (max, min, sum, average)
 */
public class UserStatisticsActivity extends AppCompatActivity {
    private String username;
    private UserInformation userInformation;
    private TextView textView;
    private ArrayList<Double> arrayList;
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

        arrayList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReferenceSignInInformation = db.collection("SignInInformation");
        final DocumentReference documentReference = collectionReferenceSignInInformation.document(username);

        textView = findViewById(R.id.user_statistics_textView);

        documentReference.addSnapshotListener((value, error) -> {
            arrayList.clear();
            if (value != null) {
                double sum = 0;
                userInformation = value.toObject(UserInformation.class);
                for (QRCode qrCode: userInformation.getQrCodeList()) {
                    arrayList.add(qrCode.getScore());
                    sum += qrCode.getScore();
                }

                var strippedSum = new BigDecimal(Double.toString(sum)).stripTrailingZeros();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format(Locale.CANADA, "Number of Barcodes: %d\n", userInformation.getQrCodeList().size()));
                stringBuilder.append(String.format(Locale.CANADA, "Total score: %s\n", strippedSum.toPlainString()));

                if (userInformation.getQrCodeList().size() > 0) {
                    var strippedAverage = new BigDecimal(Double.toString(sum / userInformation.getQrCodeList().size())).stripTrailingZeros();
                    var strippedMax = new BigDecimal(Double.toString(Collections.max(arrayList))).stripTrailingZeros();
                    var strippedMin = new BigDecimal(Double.toString(Collections.min(arrayList))).stripTrailingZeros();
                    stringBuilder.append(String.format(Locale.CANADA, "Average: %s\n", strippedAverage.toPlainString()));
                    stringBuilder.append(String.format(Locale.CANADA, "Max: %s\n", strippedMax.toPlainString()));
                    stringBuilder.append(String.format(Locale.CANADA, "Min: %s", strippedMin.toPlainString()));
                } else {
                    stringBuilder.append("Average: Not applicable\n");
                    stringBuilder.append("Max: Not applicable\n");
                    stringBuilder.append("Min: Not applicable");
                }
                textView.setText(stringBuilder.toString());
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