package com.example.qrcodeteam30.viewclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.controllerclass.listviewadapter.CustomListChooseGame;
import com.example.qrcodeteam30.modelclass.Game;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

/**
 * This activity allows user to choose game session or create new one
 */
public class ChooseGameActivity extends AppCompatActivity {
    private CollectionReference colRefGame;
    private ArrayList<Game> arrayList;
    private ArrayAdapter<Game> arrayAdapter;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        String sessionUsername = getIntent().getStringExtra("SessionUsername");

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ChooseGameActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        var intent = new Intent(ChooseGameActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setVisibility(View.GONE);

        Button button = findViewById(R.id.button_chooseGame);
        ListView listView = findViewById(R.id.chooseGameListView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        colRefGame = db.collection("Game");

        arrayList = new ArrayList<>();
        arrayAdapter = new CustomListChooseGame(this, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            var intent = new Intent(ChooseGameActivity.this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Game", arrayList.get(i));
            startActivity(intent);
        });


        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Game game = arrayAdapter.getItem(i);
            if (!sessionUsername.equals("admin") && !game.getOwnerUsername().equals(sessionUsername)) {
                return true;
            }
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ChooseGameActivity.this);
            materialAlertDialogBuilder
                    .setTitle("Delete This Game?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Delete", (dialogInterface, i1) -> {
                        colRefGame.document(game.getGameName()).delete();
                    }).show();
            return true;
        });


        button.setOnClickListener(v -> {
            var view = getLayoutInflater().inflate(R.layout.choosing_game_dialog_layout, null);
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ChooseGameActivity.this);
            materialAlertDialogBuilder.setTitle("Create New Game Session")
                    .setView(view)
                    .setPositiveButton("Create", (dialogInterface, i) -> {
                        EditText editText = view.findViewById(R.id.editText_addGame);
                        final String gameName = editText.getText().toString();
                        if (gameName.length() > 0) {
                            DocumentReference docRef = colRefGame.document(gameName);
                            docRef.get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Toast.makeText(getApplicationContext(), "Game name already exists", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Game game = new Game(gameName, sessionUsername);
                                        docRef.set(game).addOnCompleteListener(task1 -> {
                                            var intent = new Intent(ChooseGameActivity.this, PlayerMenuActivity.class);
                                            intent.putExtra("SessionUsername", sessionUsername);
                                            intent.putExtra("Game", game);
                                            startActivity(intent);
                                        });
                                    }
                                }
                            });
                        }
                    }).setNegativeButton("Cancel", null).show();
        });
    }


    @Override
    protected void onStart() {
        listenerRegistration = colRefGame.addSnapshotListener((value, error) -> {
            if (value == null) {
                return;
            }
            arrayList.clear();
            for (var doc: value) {
                var game = doc.toObject(Game.class);
                arrayList.add(game);
            }
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