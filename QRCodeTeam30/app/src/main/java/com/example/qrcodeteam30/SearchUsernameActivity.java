package com.example.qrcodeteam30;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.reusableactivity.UserProfileActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

/**
 * This activity represents the interface for the user to search other user
 * Then, the user can see which QR Codes do the other user have (by press the search button)
 */
public class SearchUsernameActivity extends AppCompatActivity {
    private String sessionUsername;
    FirebaseFirestore db;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_username);
        SearchView searchView = findViewById(R.id.searchView_searchUsername);
        Button buttonQRCode = findViewById(R.id.buttonQRCode_searchUsername);

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(SearchUsernameActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        Intent intent = new Intent(SearchUsernameActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        db = FirebaseFirestore.getInstance();
        arrayList = new ArrayList<>();
        db.collection("SignInInformation").addSnapshotListener((value, error) -> {
            arrayList.clear();
            if (value != null) {
                for (var doc : value) {
                    var userInformation = doc.toObject(UserInformation.class);
                    arrayList.add(userInformation.getUsername());
                }

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        if (arrayList.contains(query)) {
                            var intent = new Intent(SearchUsernameActivity.this, UserProfileActivity.class);
                            intent.putExtra("Username", query);
                            intent.putExtra("SessionUsername", sessionUsername);
                            startActivity(intent);
                            return true;
                        }
                        Toast.makeText(getApplicationContext(), "No such username", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

                buttonQRCode.setOnClickListener(v -> {
                    var scanOptions = new ScanOptions();
                    scanOptions.setBeepEnabled(false);
                    barcodeLauncher.launch(scanOptions);
                });
            }
        });

    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                String str = result.getContents();
                if (str == null) {
                    Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    String[] strList = str.split(" ");
                    if (strList.length != 2) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final var username = strList[0];
                    if (username.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!arrayList.contains(username)) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    var intent = new Intent(SearchUsernameActivity.this, UserProfileActivity.class);
                    intent.putExtra("Username", username);
                    intent.putExtra("SessionUsername", sessionUsername);
                    startActivity(intent);
                }
            });


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