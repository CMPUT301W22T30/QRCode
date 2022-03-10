package com.example.qrcodeteam30.reusableactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;

public class CheckSameQRCodeActivity extends AppCompatActivity {
    private QRCode qrCode;
    private String sessionUsername;
    private ArrayList<QRCode> arrayListQRCode;
    private ArrayList<String> arrayListRelevantUsername;

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
                    .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(CheckSameQRCodeActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        });

        qrCode = (QRCode) getIntent().getSerializableExtra("QRCode");
        sessionUsername = getIntent().getStringExtra("SessionUsername");
        arrayListQRCode = new ArrayList<>();
        arrayListRelevantUsername = new ArrayList<>();
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        ListView listView = findViewById(R.id.listView_checkSameQRCode);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.listview_simple_content, arrayListRelevantUsername);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                var intent = new Intent(CheckSameQRCodeActivity.this, UserProfileActivity.class);
                intent.putExtra("SessionUsername", sessionUsername);
                intent.putExtra("Username", arrayAdapter.getItem(i).substring(1));
                startActivity(intent);
            }
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReferenceSignInInformation = db.collection("SignInInformation");
        collectionReferenceSignInInformation.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arrayListQRCode.clear();
                arrayListRelevantUsername.clear();
                for (QueryDocumentSnapshot queryDocumentSnapshot: value) {
                    UserInformation userInformation = queryDocumentSnapshot.toObject(UserInformation.class);
                    arrayListQRCode.addAll(userInformation.getQrCodeList());
                }
                HashSet<String> seen = new HashSet<>();
                for (var qrCode_iterator: arrayListQRCode) {
                    if (qrCode.getQrCodeContent().equals(qrCode_iterator.getQrCodeContent())
                            && Math.abs(qrCode.getLatitude() - qrCode_iterator.getLatitude()) < 0.001
                            && Math.abs(qrCode.getLongitude() - qrCode_iterator.getLongitude()) < 0.001
                            && !qrCode_iterator.getUsername().equals(qrCode.getUsername())) {
                        if (!seen.contains(qrCode_iterator.getUsername())) {
                            arrayListRelevantUsername.add("@" + qrCode_iterator.getUsername());
                            seen.add(qrCode_iterator.getUsername());
                        }
                    }
                }
                arrayAdapter.notifyDataSetChanged();
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