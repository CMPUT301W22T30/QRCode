package com.example.qrcodeteam30.viewclass;

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

import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.controllerclass.listviewadapter.CustomListRankingController;
import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.modelclass.UserScoreGameSession;
import com.example.qrcodeteam30.viewclass.reusableactivity.UserProfileActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Display the ranking of all users based on their total score
 */
public class RankingActivity extends AppCompatActivity {
    private ArrayAdapter<UserScoreGameSession> arrayAdapter;
    private ArrayList<UserScoreGameSession> arrayList;
    private String sessionUsername;
    private FirebaseFirestore db;
    private CollectionReference collectionReferenceSignInInformation;
    private ListenerRegistration listenerRegistration;
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        game = (Game) getIntent().getSerializableExtra("Game");

        db = FirebaseFirestore.getInstance();
        collectionReferenceSignInInformation = db.collection("SignInInformation");

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(RankingActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        var intent = new Intent(RankingActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        ListView listView = findViewById(R.id.listView_ranking);

        arrayList = new ArrayList<>();
        arrayAdapter = new CustomListRankingController(this, arrayList);
        listView.setAdapter(arrayAdapter);

        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Game", game);
            startActivity(intent);
        });


        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            var intent = new Intent(RankingActivity.this, UserProfileActivity.class);
            intent.putExtra("Username", arrayAdapter.getItem(i).getUsername());
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Game", game);
            startActivity(intent);
        });
    }


    @Override
    protected void onStart() {
        // Update listview in realtime
        listenerRegistration = collectionReferenceSignInInformation.addSnapshotListener((value, error) -> {
            arrayList.clear();
            for (var doc: value) {
                var userInformation = doc.toObject(UserInformation.class);
                var userScoreGameSession = new UserScoreGameSession(userInformation, game);
                if (userScoreGameSession.getScore() > 0) {
                    arrayList.add(new UserScoreGameSession(userInformation, game));
                }
            }
            arrayList.sort(Comparator.reverseOrder());
            arrayAdapter.notifyDataSetChanged();
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