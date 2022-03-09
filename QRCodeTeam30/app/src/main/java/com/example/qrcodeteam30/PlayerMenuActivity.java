package com.example.qrcodeteam30;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.myprofile.MyProfileActivity;
import com.example.qrcodeteam30.viewAllQRCode.ViewAllQRCode;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PlayerMenuActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference collectionReferenceSignInInformation;
    private LocationManager locationManager;
    private String sessionUsername;

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

        db = FirebaseFirestore.getInstance();
        collectionReferenceSignInInformation = db.collection("SignInInformation");

        Button buttonScanQRCode = findViewById(R.id.button_playerMenu_scanQRCode);
        Button buttonSearchQRCode = findViewById(R.id.button_playerMenu_searchQRCode);
        Button buttonSearchUsername = findViewById(R.id.button_playerMenu_searchUsername);
        Button buttonRanking = findViewById(R.id.button_playerMenu_ranking);
        Button buttonMyProfile = findViewById(R.id.button_playerMenu_myProfile);
        Button buttonViewQRCode = findViewById(R.id.button_playerMenu_viewQRCode);
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setVisibility(View.GONE);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        buttonScanQRCode.setOnClickListener(v -> {
            if (!hasLocationPermission()) {
                requestLocationPermission();
            } else {
                var scanOptions = new ScanOptions();
                scanOptions.setBeepEnabled(false);
                scanOptions.setBarcodeImageEnabled(true);
                barcodeLauncher.launch(scanOptions);
            }
        });

        buttonSearchQRCode.setOnClickListener(v -> {
            if (hasWritingLocationPermission()) {
                var intent = new Intent(PlayerMenuActivity.this, SearchQRCodeActivity.class);
                intent.putExtra("SessionUsername", sessionUsername);
                startActivity(intent);
            } else {
                requestWritingLocationPermission();
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
            var intent = new Intent(PlayerMenuActivity.this, ViewAllQRCode.class);
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
                    BigDecimal strippedVal = new BigDecimal(QRCode.calculateScore(str)).stripTrailingZeros();
                    String scoreStrippedFormat = strippedVal.toPlainString();

                    String path = result.getBarcodeImagePath();
                    File file = new File(path);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    Bitmap scaledBitmap = MyBitmap.scaleBitmap(bitmap, 65536);
                    String bitmapResizeString = MyBitmap.bitMapToString(scaledBitmap);

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
                                                            uploadQRCodeToDB_location_noPhoto(str, result.getFormatName()))
                                                    .setNegativeButton("No", (dialogInterface1, i1) ->
                                                            uploadQRCodeToDB_noLocation_noPhoto(str, result.getFormatName()))
                                                    .show();
                                        })
                                        .setPositiveButton("Yes", (dialogInterface14, i14) -> {
                                            var locationDialog = new MaterialAlertDialogBuilder(PlayerMenuActivity.this);
                                            locationDialog.setTitle("Do you want to record the location?")
                                                    .setPositiveButton("Yes", (dialogInterface141, i141) ->
                                                            uploadQRCodeToDB_Location_Photo(str, result.getFormatName(), bitmapResizeString))
                                                    .setNegativeButton("No", (dialogInterface1412, i1412) ->
                                                            uploadQRCodeToDB_noLocation_Photo(str, result.getFormatName(), bitmapResizeString))
                                                    .show();
                                        }).show();
                                ImageView imageView = view.findViewById(R.id.savephoto_dialog_imageview);
                                imageView.setImageBitmap(bitmap);
                            }).show();
                }
            });


    private void uploadQRCodeToDB_Location_Photo(String str, String formatName, String bitmapResizeString) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            uploadQRCodeToDB_Location_Photo(str, formatName, bitmapResizeString);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationManager.removeUpdates(this);

                final var colRefComment = db.collection("Comment");
                final var colRefPhoto = db.collection("Photo");
                Map<String, Object> mapComment = new HashMap<>();
                mapComment.put("CommentList", Arrays.asList());
                Map<String, String> mapPhoto = new HashMap<>();
                mapPhoto.put("photoString", bitmapResizeString);

                colRefComment.add(mapComment).addOnCompleteListener(task -> {
                    String commentDocumentName = task.getResult().getId();
                    colRefPhoto.add(mapPhoto).addOnCompleteListener(task1 -> {
                        String photoDocumentName = task1.getResult().getId();
                        var qrCode = new QRCode(str, location.getLatitude(), location.getLongitude(), sessionUsername,
                                formatName, "Comment/" + commentDocumentName + "/",
                                true, true, "Photo/" + photoDocumentName + "/");
                        final DocumentReference documentReference = collectionReferenceSignInInformation.document(sessionUsername);
                        documentReference.update("qrCodeList", FieldValue.arrayUnion(qrCode));
                        documentReference.update("score", FieldValue.increment(qrCode.getScore()));
                    });
                });
                Toast.makeText(getApplicationContext(), "Scan Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Toast.makeText(getApplicationContext(), "Turn on your GPS", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    private void uploadQRCodeToDB_location_noPhoto(String str, String formatName) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            uploadQRCodeToDB_location_noPhoto(str, formatName);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationManager.removeUpdates(this);

                final var colRefComment = db.collection("Comment");
                Map<String, Object> map = new HashMap<>();
                map.put("CommentList", Arrays.asList());
                colRefComment.add(map).addOnCompleteListener(task -> {
                    String commentDocumentName = task.getResult().getId();
                    var qrCode = new QRCode(str, location.getLatitude(), location.getLongitude(), sessionUsername,
                            formatName, "Comment/" + commentDocumentName + "/",
                            true, false, "N/A");
                    final DocumentReference documentReference = collectionReferenceSignInInformation.document(sessionUsername);
                    documentReference.update("qrCodeList", FieldValue.arrayUnion(qrCode));
                    documentReference.update("score", FieldValue.increment(qrCode.getScore()));
                });
                Toast.makeText(getApplicationContext(), "Scan Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Toast.makeText(getApplicationContext(), "Turn on your GPS", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }
        });
    }


    private void uploadQRCodeToDB_noLocation_Photo(String str, String formatName, String bitmapResizeString) {
        final var colRefPhoto = db.collection("Photo");
        final var colRefComment = db.collection("Comment");

        Map<String, Object> mapComment = new HashMap<>();
        mapComment.put("CommentList", Arrays.asList());
        Map<String, String> mapPhoto = new HashMap<>();
        mapPhoto.put("photoString", bitmapResizeString);

        colRefComment.add(mapComment).addOnCompleteListener(task -> {
            final String commentDocumentName = task.getResult().getId();
            colRefPhoto.add(mapPhoto).addOnCompleteListener(task1 -> {
                final String photoDocumentName = task1.getResult().getId();
                var qrCode = new QRCode(str, 0, 0, sessionUsername, formatName,
                        "Comment/" + commentDocumentName + "/",
                        false, true, "Photo/" + photoDocumentName + "/");
                final DocumentReference documentReference = collectionReferenceSignInInformation.document(sessionUsername);
                documentReference.update("qrCodeList", FieldValue.arrayUnion(qrCode));
                documentReference.update("score", FieldValue.increment(qrCode.getScore()));
            });
        });
        Toast.makeText(getApplicationContext(), "Scan Completed", Toast.LENGTH_SHORT).show();

    }


    private void uploadQRCodeToDB_noLocation_noPhoto(String str, String formatName) {
        final var colRefComment = db.collection("Comment");
        Map<String, Object> map = new HashMap<>();
        map.put("CommentList", Arrays.asList());

        colRefComment.add(map).addOnCompleteListener(task -> {
            String commentDocumentName = task.getResult().getId();
            var qrCode = new QRCode(str, 0, 0, sessionUsername,
                    formatName, "Comment/" + commentDocumentName + "/",
                    false, false, "N/A");
            final DocumentReference documentReference = collectionReferenceSignInInformation.document(sessionUsername);
            documentReference.update("qrCodeList", FieldValue.arrayUnion(qrCode));
            documentReference.update("score", FieldValue.increment(qrCode.getScore()));
        });
        Toast.makeText(getApplicationContext(), "Scan Completed", Toast.LENGTH_SHORT).show();
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


    private boolean hasLocationPermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }


    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
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
}