package com.example.qrcodeteam30.viewAllQRCode;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.MainActivity;
import com.example.qrcodeteam30.PlayerMenuActivity;
import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.listviewadapter.CustomListViewAllQRCode;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.reusableactivity.QRCodeInfoActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewAllQRCode extends AppCompatActivity {
    private String username;
    private String sessionUsername;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private UserInformation userInformation;
    private ArrayAdapter<QRCode> arrayAdapter;
    private ArrayList<QRCode> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_qrcode);
        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ViewAllQRCode.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            var intent = new Intent(ViewAllQRCode.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        });

        username = getIntent().getStringExtra("Username");
        sessionUsername = getIntent().getStringExtra("SessionUsername");
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        ListView listView = findViewById(R.id.viewAllQRCode_listView);
        Button button = findViewById(R.id.button_viewAllQRCode_statistics);

        arrayList = new ArrayList<>();
        arrayAdapter = new CustomListViewAllQRCode(this, arrayList);
        listView.setAdapter(arrayAdapter);

        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReferenceSignInInformation = db.collection("SignInInformation");
        documentReference = collectionReferenceSignInInformation.document(username);

        documentReference.addSnapshotListener((value, error) -> {
            if (value != null) {
                arrayList.clear();
                userInformation = value.toObject(UserInformation.class);
                arrayList.addAll(userInformation.getQrCodeList());
                arrayAdapter.notifyDataSetChanged();
            }
        });


        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            var intent = new Intent(ViewAllQRCode.this, QRCodeInfoActivity.class);
            var qrCode = arrayAdapter.getItem(i);
            intent.putExtra("QRCode", qrCode);
            intent.putExtra("ListViewPosition", i);
            intent.putExtra("SessionUsername", sessionUsername);
            activityResultLauncher.launch(intent);
        });


        button.setOnClickListener(v -> {
            var intent = new Intent(ViewAllQRCode.this, UserStatisticsActivity.class);
            intent.putExtra("Username", username);
            startActivity(intent);
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                var intent = result.getData();
                boolean isDelete = intent.getBooleanExtra("Delete", false);
                if (isDelete) {
                    int listViewPosition = intent.getIntExtra("ListViewPosition", -1);
                    var qrCode = arrayAdapter.getItem(listViewPosition);

                    db.document(qrCode.getCommentListReference()).delete();
                    db.document(qrCode.getPhotoReference()).delete();

                    documentReference.update("qrCodeList", FieldValue.arrayRemove(qrCode));
                    documentReference.update("score", FieldValue.increment(-qrCode.getScore()));
                }
            }
    );

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