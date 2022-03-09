package com.example.qrcodeteam30;

import android.content.DialogInterface;
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

import com.example.qrcodeteam30.listviewadapter.CustomListRanking;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.reusableactivity.UserProfileActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;

public class RankingActivity extends AppCompatActivity {
    private ArrayAdapter<UserInformation> arrayAdapter;
    private ArrayList<UserInformation> arrayList;
    private String sessionUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(RankingActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            var intent = new Intent(RankingActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        });

        ListView listView = findViewById(R.id.listView_ranking);

        arrayList = new ArrayList<>();
        arrayAdapter = new CustomListRanking(this, arrayList);
        listView.setAdapter(arrayAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReferenceSignInInformation = db.collection("SignInInformation");

        // Update listview in realtime
        collectionReferenceSignInInformation.addSnapshotListener((value, error) -> {
            arrayList.clear();
            for (var doc: value) {
                var userInformation = doc.toObject(UserInformation.class);
                arrayList.add(userInformation);
            }
            arrayList.sort(Comparator.reverseOrder());
            arrayAdapter.notifyDataSetChanged();
        });

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });


        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            var intent = new Intent(RankingActivity.this, UserProfileActivity.class);
            intent.putExtra("Username", arrayAdapter.getItem(i).getUsername());
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
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