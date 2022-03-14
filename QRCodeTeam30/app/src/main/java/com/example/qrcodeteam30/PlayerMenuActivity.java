package com.example.qrcodeteam30;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.controllerclass.CalculateScoreController;
import com.example.qrcodeteam30.controllerclass.MyBitmapController;
import com.example.qrcodeteam30.controllerclass.MyFirestoreUploadController;
import com.example.qrcodeteam30.controllerclass.MyPermissionController;
import com.example.qrcodeteam30.myprofile.MyProfileActivity;
import com.example.qrcodeteam30.viewallqrcode.ViewAllQRCodeActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.File;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * <p>
 *     The main menu for the app, with five buttons represent five major features of the code.
 *     Button 1: Scan QR Code (Which launch the camera and scan the QR Code)
 *     Button 2: Search QR Code (Choosing from a map)
 *     Button 3: Search username
 *     Button 4: Check my profile
 *     Button 5: View all of my QR Codes
 * </p>
 */
public class PlayerMenuActivity extends AppCompatActivity {
    private String sessionUsername;
    private MyFirestoreUploadController myFirestoreUpload;
    private MyPermissionController myPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_menu);

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(PlayerMenuActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        var intent = new Intent(PlayerMenuActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        myFirestoreUpload = new MyFirestoreUploadController(getApplicationContext());
        myPermission = new MyPermissionController(this, this);

        Button buttonScanQRCode = findViewById(R.id.button_playerMenu_scanQRCode);
        Button buttonSearchQRCode = findViewById(R.id.button_playerMenu_searchQRCode);
        Button buttonSearchUsername = findViewById(R.id.button_playerMenu_searchUsername);
        Button buttonRanking = findViewById(R.id.button_playerMenu_ranking);
        Button buttonMyProfile = findViewById(R.id.button_playerMenu_myProfile);
        Button buttonViewQRCode = findViewById(R.id.button_playerMenu_viewQRCode);
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setVisibility(View.GONE);


        buttonScanQRCode.setOnClickListener(v -> {
            if (!myPermission.hasLocationPermission()) {
                myPermission.requestLocationPermission();
            } else {
                var scanOptions = new ScanOptions();
                scanOptions.setBeepEnabled(false);
                scanOptions.setBarcodeImageEnabled(true);
                barcodeLauncher.launch(scanOptions);
            }
        });

        buttonSearchQRCode.setOnClickListener(v -> {
            if (myPermission.hasWritingLocationPermission()) {
                var intent = new Intent(PlayerMenuActivity.this, SearchQRCodeActivity.class);
                intent.putExtra("SessionUsername", sessionUsername);
                startActivity(intent);
            } else {
                myPermission.requestWritingLocationPermission();
            }

        });

        buttonSearchUsername.setOnClickListener(v -> {
            var intent = new Intent(PlayerMenuActivity.this, SearchUsernameActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);

        });

        buttonRanking.setOnClickListener(v -> {
            var intent = new Intent(PlayerMenuActivity.this, RankingActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        buttonMyProfile.setOnClickListener(v -> {
            var intent = new Intent(PlayerMenuActivity.this, MyProfileActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        buttonViewQRCode.setOnClickListener(v -> {
            var intent = new Intent(PlayerMenuActivity.this, ViewAllQRCodeActivity.class);
            intent.putExtra("Username", sessionUsername);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

    }


    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                String str = result.getContents();
                if (str == null) {
                    Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    BigDecimal strippedVal = new BigDecimal(CalculateScoreController.calculateScore(str)).stripTrailingZeros();
                    String scoreStrippedFormat = strippedVal.toPlainString();

                    String path = result.getBarcodeImagePath();
                    File file = new File(path);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    Bitmap scaledBitmap = MyBitmapController.scaleBitmap(bitmap, 65536);
                    String bitmapResizeString = MyBitmapController.bitMapToString(scaledBitmap);

                    var acceptDeclineDialog = new MaterialAlertDialogBuilder(PlayerMenuActivity.this);
                    acceptDeclineDialog.setTitle("Do you want to record this barcode?")
                            .setMessage(String.format(Locale.CANADA, "Score: %s", scoreStrippedFormat))
                            .setNegativeButton("Decline", null)
                            .setPositiveButton("Accept", (dialogInterface, i) -> {
                                var recordPhotoDialog = new MaterialAlertDialogBuilder(PlayerMenuActivity.this);
                                var view = getLayoutInflater().inflate(R.layout.imageview_savephoto_dialog_layout, null);
                                recordPhotoDialog.setTitle("Save photo?")
                                        .setView(view)
                                        .setNegativeButton("No", (dialogInterface13, i13) -> {
                                            var locationDialog = new MaterialAlertDialogBuilder(PlayerMenuActivity.this);
                                            locationDialog.setTitle("Do you want to record the location?")
                                                    .setPositiveButton("Yes", (dialogInterface12, i12) ->
                                                            myFirestoreUpload.uploadQRCodeToDBLocationNoPhoto(
                                                                    str, result.getFormatName(), sessionUsername)
                                                    )
                                                    .setNegativeButton("No", (dialogInterface1, i1) ->
                                                            myFirestoreUpload.uploadQRCodeToDBNoLocationNoPhoto(
                                                                    str, result.getFormatName(), sessionUsername)
                                                    )
                                                    .show();
                                        })
                                        .setPositiveButton("Yes", (dialogInterface14, i14) -> {
                                            var locationDialog = new MaterialAlertDialogBuilder(PlayerMenuActivity.this);
                                            locationDialog.setTitle("Do you want to record the location?")
                                                    .setPositiveButton("Yes", (dialogInterface141, i141) ->
                                                            myFirestoreUpload.uploadQRCodeToDBLocationPhoto(
                                                                    str, result.getFormatName(), bitmapResizeString, sessionUsername)
                                                    )
                                                    .setNegativeButton("No", (dialogInterface1412, i1412) ->
                                                            myFirestoreUpload.uploadQRCodeToDBNoLocationPhoto(
                                                                    str, result.getFormatName(), bitmapResizeString, sessionUsername)
                                                    )
                                                    .show();
                                        }).show();
                                ImageView imageView = view.findViewById(R.id.savephoto_dialog_imageview);
                                imageView.setImageBitmap(bitmap);
                            }).show();
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