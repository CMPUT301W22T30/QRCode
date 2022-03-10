package com.example.qrcodeteam30.reusableactivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.qrcodeteam30.MainActivity;
import com.example.qrcodeteam30.MyBitmap;
import com.example.qrcodeteam30.PlayerMenuActivity;
import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.math.BigDecimal;
import java.util.Locale;

public class QRCodeInfoActivity extends AppCompatActivity {
    private ImageView imageView;
    private int listViewPosition;
    private String sessionUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_info);
        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(QRCodeInfoActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(QRCodeInfoActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        });

        QRCode qrCode = (QRCode) getIntent().getSerializableExtra("QRCode");
        sessionUsername = getIntent().getStringExtra("SessionUsername");
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });
        listViewPosition = getIntent().getIntExtra("ListViewPosition", -1);

        imageView = findViewById(R.id.imageView_qrCode_info);
        TextView textViewScore = findViewById(R.id.textView_score_qrCode_info);
        Button checkSameQRCodeButton = findViewById(R.id.button_checkSameQRCode_qrCode_info);
        Button viewLocationButton = findViewById(R.id.button_viewLocation_qrCode_info);
        Button viewPhotoButton = findViewById(R.id.button_viewPhoto_qrCode_info);
        Button viewCommentButton = findViewById(R.id.button_viewComment_qrCode_info);

        if (qrCode.isRecordPhoto()) {
            displayQRCode(qrCode.getQrCodeContent(), qrCode.getFormat());
        } else {
            imageView.setVisibility(View.GONE);
        }


        var strippedScore = new BigDecimal(Double.toString(qrCode.getScore())).stripTrailingZeros();
        textViewScore.setText(String.format(Locale.CANADA, "Score: %s\n@%s\n%s", strippedScore.toPlainString(), qrCode.getUsername(), qrCode.getDate()));

        checkSameQRCodeButton.setOnClickListener(v -> {
            var intent = new Intent(QRCodeInfoActivity.this, CheckSameQRCodeActivity.class);
            intent.putExtra("QRCode", qrCode);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        viewCommentButton.setOnClickListener(v -> {
            var intent = new Intent(QRCodeInfoActivity.this, ViewAllCommentActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            intent.putExtra("QRCodeUsername", qrCode.getUsername());
            intent.putExtra("QRCode", qrCode);
            startActivity(intent);
        });

    }

    private void displayQRCode(String str, String format) {
        var barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = null;
        int width;
        int height;
        try {
            if (format.equals("QR_CODE")) {
                width = 1000;
                height = 1000;
            } else {
                width = 1000;
                height = 500;
            }
            bitmap = barcodeEncoder.encodeBitmap(str, BarcodeFormat.valueOf(format), width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        var intent = new Intent();
        intent.putExtra("Delete", false);
        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean hasWritingLocationPermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestWritingLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
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