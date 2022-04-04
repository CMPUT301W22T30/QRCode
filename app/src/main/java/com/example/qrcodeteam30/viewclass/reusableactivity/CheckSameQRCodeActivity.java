package com.example.qrcodeteam30.viewclass.reusableactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.viewclass.MainActivity;
import com.example.qrcodeteam30.controllerclass.MyCryptographyController;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;
import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Check if any user scanned the same QR Code
 * The criteria of "same": Same content and same location
 * The QR Code that does not have location info will not be include in the searching process
 */
public class CheckSameQRCodeActivity extends AppCompatActivity {
    private QRCode qrCode;
    private String sessionUsername;
    private ArrayList<QRCode> arrayListQRCode;
    private ArrayList<String> arrayListRelevantUsername;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_same_qrcode);
        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(CheckSameQRCodeActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        Intent intent = new Intent(CheckSameQRCodeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        qrCode = (QRCode) getIntent().getSerializableExtra("QRCode");
        sessionUsername = getIntent().getStringExtra("SessionUsername");
        game = (Game) getIntent().getSerializableExtra("Game");
        arrayListQRCode = new ArrayList<>();
        arrayListRelevantUsername = new ArrayList<>();
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Game", game);
            startActivity(intent);
        });

        ListView listView = findViewById(R.id.listView_checkSameQRCode);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.listview_simple_content, arrayListRelevantUsername);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            var intent = new Intent(CheckSameQRCodeActivity.this, UserProfileActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Username", arrayAdapter.getItem(i).substring(2, arrayAdapter.getItem(i).length() - 1));
            intent.putExtra("Game", game);
            startActivity(intent);
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReferenceSignInInformation = db.collection("SignInInformation");
        collectionReferenceSignInInformation.addSnapshotListener((value, error) -> {
            arrayListQRCode.clear();
            arrayListRelevantUsername.clear();
            for (QueryDocumentSnapshot queryDocumentSnapshot: value) {
                UserInformation userInformation = queryDocumentSnapshot.toObject(UserInformation.class);
                arrayListQRCode.addAll(userInformation.getQrCodeList());
            }

            HashSet<String> seen = new HashSet<>();
            for (var qrCode_iterator: arrayListQRCode) {
                if (MyCryptographyController.decrypt(qrCode.getQrCodeContent()).equals(MyCryptographyController.decrypt(qrCode_iterator.getQrCodeContent()))
                        && Math.abs(qrCode.getLatitude() - qrCode_iterator.getLatitude()) < 0.001
                        && Math.abs(qrCode.getLongitude() - qrCode_iterator.getLongitude()) < 0.001
                        && !qrCode_iterator.getUsername().equals(qrCode.getUsername())
                        && qrCode_iterator.getGameName().equals(game.getGameName())) {

                    // Make sure one username is added only one time in the hashset
                    if (!seen.contains(qrCode_iterator.getUsername())) {
                        arrayListRelevantUsername.add("\n@" + qrCode_iterator.getUsername() + "\n");
                        seen.add(qrCode_iterator.getUsername());
                    }
                }
            }
            arrayAdapter.notifyDataSetChanged();
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