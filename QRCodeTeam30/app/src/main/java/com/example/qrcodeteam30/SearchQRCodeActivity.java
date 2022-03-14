package com.example.qrcodeteam30;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.controllerclass.MyPermissionController;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.reusableactivity.QRCodeInfoActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Locale;

/**
 * <p>
 *     This activity represents the activity that allows user to browse QR Code from a map
 *     Clicking on any marker to see that QR Code information
 *     Automatically centered the map at the current location
 *     If the location is not turned on, centered the map at the University of Alberta
 * </p>
 */
public class SearchQRCodeActivity extends AppCompatActivity {
    private String sessionUsername;
    private MapView mapView;
    private ProgressBar progressBar;
    private LocationManager locationManager;
    private IMapController mapController;
    private ArrayList<QRCode> arrayListQRCode;
    private ListenerRegistration listenerRegistration;
    private MyPermissionController myPermission;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReferenceSignInInformation = db.collection("SignInInformation");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_search_qrcode);

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        myPermission = new MyPermissionController(this, this);

        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setOnClickListener(v -> {
            var materialAlertDialogBuilder = new MaterialAlertDialogBuilder(SearchQRCodeActivity.this);
            materialAlertDialogBuilder.setTitle("Log out").setMessage("Do you want to log out?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Log out", (dialogInterface, i) -> {
                        var intent = new Intent(SearchQRCodeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }).show();
        });

        sessionUsername = getIntent().getStringExtra("SessionUsername");
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        buttonHome.setOnClickListener(v -> {
            var intent = new Intent(this, PlayerMenuActivity.class);
            intent.putExtra("SessionUsername", sessionUsername);
            startActivity(intent);
        });

        progressBar = findViewById(R.id.searchQRCode_progressBar);
        mapView = findViewById(R.id.searchQRCode_mapView);

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapController = mapView.getController();
        mapController.setZoom(16.0);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        centerMapToCurrentLocation();

        // Get current location
        MyLocationNewOverlay myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), this.mapView);
        myLocationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(myLocationNewOverlay);

        // Get compass
        CompassOverlay compassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        arrayListQRCode = new ArrayList<>();
    }


    @SuppressLint("MissingPermission")
    private void centerMapToCurrentLocation() {
        if (myPermission.hasLocationPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    locationManager.removeUpdates(this);
                    mapController.setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
                    progressBar.setVisibility(View.GONE);
                    mapView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    Toast.makeText(getApplicationContext(), "No GPS signal", Toast.LENGTH_SHORT).show();
                    // Default location is the University of Alberta
                    mapController.setCenter(new GeoPoint(53.5232, -113.5263));
                    progressBar.setVisibility(View.GONE);
                    mapView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No GPS signal", Toast.LENGTH_SHORT).show();
            // Default location is the University of Alberta
            mapController.setCenter(new GeoPoint(53.5232, 113.5263));
            progressBar.setVisibility(View.GONE);
            mapView.setVisibility(View.VISIBLE);

        }
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

    @Override
    public void onBackPressed() {
        listenerRegistration.remove();
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        listenerRegistration = collectionReferenceSignInInformation.addSnapshotListener((value, error) -> {
            arrayListQRCode.clear();

            for (QueryDocumentSnapshot queryDocumentSnapshot: value) {
                var userInformation = queryDocumentSnapshot.toObject(UserInformation.class);
                arrayListQRCode.addAll(userInformation.getQrCodeList());
            }

            ArrayList<Marker> arrayList = new ArrayList<>();
            for (QRCode qrCode: arrayListQRCode) {
                if (!qrCode.isRecordLocation()) {
                    continue;
                }
                var marker = new Marker(mapView);
                marker.setPosition(new GeoPoint(qrCode.getLatitude(), qrCode.getLongitude()));
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle(String.format(Locale.CANADA, "Score: %f\n@%s", qrCode.getScore(), qrCode.getUsername()));
                arrayList.add(marker);

                marker.setOnMarkerClickListener((marker1, mapView) -> {
                    var intent = new Intent(SearchQRCodeActivity.this, QRCodeInfoActivity.class);
                    intent.putExtra("SessionUsername", sessionUsername);
                    intent.putExtra("QRCode", qrCode);
                    startActivity(intent);
                    return false;
                });
            }

            mapView.getOverlays().addAll(arrayList);
        });
        super.onStart();
    }

    @Override
    protected void onStop() {
        listenerRegistration.remove();
        super.onStop();
    }
}