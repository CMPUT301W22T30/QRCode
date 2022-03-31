package com.example.qrcodeteam30.viewclass.reusableactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.viewclass.MainActivity;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;
import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.controllerclass.MyFirestoreUploadController;
import com.example.qrcodeteam30.controllerclass.listviewadapter.CustomListViewAllCommentController;
import com.example.qrcodeteam30.modelclass.Comment;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

/**
 * An interface for showing all the comments of the QR Code, adding and deleting comment
 */
public class ViewAllCommentActivity extends AppCompatActivity {
    private ArrayAdapter<Comment> arrayAdapter;
    private ArrayList<Comment> arrayList;
    private EditText editText;
    private String sessionUsername;
    private String qrCodeUsername;
    private DocumentReference documentReference;
    private MyFirestoreUploadController myFirestoreUpload;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comment);
        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        game = (Game) getIntent().getSerializableExtra("Game");
        qrCodeUsername = getIntent().getStringExtra("QRCodeUsername");
        QRCode qrCode = (QRCode) getIntent().getSerializableExtra("QRCode");

        myFirestoreUpload = new MyFirestoreUploadController(getApplicationContext());

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ViewAllCommentActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        var intent = new Intent(ViewAllCommentActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("Game", game);
            startActivity(intent);
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        documentReference = db.document(qrCode.getCommentListReference());

        ListView listView = findViewById(R.id.listView_viewAllComment);
        Button addButton = findViewById(R.id.button_addComment_viewAllComment);
        editText = findViewById(R.id.editText_addComment_viewAllComment);

        arrayList = new ArrayList<>();
        arrayAdapter = new CustomListViewAllCommentController(this, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (!arrayAdapter.getItem(i).getAuthor().equals(sessionUsername)
                    && !sessionUsername.equals("admin")
                    && !arrayAdapter.getItem(i).getUsername().equals(sessionUsername)) {
                return;
            }
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ViewAllCommentActivity.this);
            materialAlertDialogBuilder.setTitle("Delete").setMessage("Do you want to delete this comment?")
                    .setPositiveButton("Delete", (dialogInterface, j) -> {
                        Comment commentTemp = arrayAdapter.getItem(i);
                        arrayList.remove(i);
                        arrayAdapter.notifyDataSetChanged();
                        myFirestoreUpload.deleteComment(commentTemp, qrCode);

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        documentReference.addSnapshotListener((value, error) -> {
            if (value != null) {
                if (value.exists()) {
                    arrayList.clear();
                    ArrayList<Map<String, String>> mapList = (ArrayList<Map<String, String>>) value.get("CommentList");
                    if (mapList != null) {
                        for (Map<String, String> map: mapList) {
                            var comment = new Comment(map.get("username"), map.get("author"), map.get("content"), map.get("date"));
                            arrayList.add(comment);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });


        addButton.setOnClickListener(view -> {
            final var commentContent = editText.getText().toString();
            if (commentContent.length() == 0) {
                return;
            }
            editText.setText("");
            var comment = new Comment(qrCodeUsername, sessionUsername, commentContent);
            myFirestoreUpload.addComment(comment, qrCode);

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