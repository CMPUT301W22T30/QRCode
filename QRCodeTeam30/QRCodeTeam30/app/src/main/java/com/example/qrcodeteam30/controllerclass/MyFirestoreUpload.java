package com.example.qrcodeteam30.controllerclass;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.qrcodeteam30.modelclass.Comment;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MyFirestoreUpload {
    private FirebaseFirestore db;
    private CollectionReference collectionReferenceSignInInformation;
    private LocationManager locationManager;
    private Context context;

    public MyFirestoreUpload(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        this.collectionReferenceSignInInformation = db.collection("SignInInformation");
    }

    public void uploadQRCodeToDBLocationPhoto(String str, String formatName, String bitmapResizeString, String sessionUsername) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If no location permission, return
            Toast.makeText(context, "No location permission", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Scan Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Toast.makeText(context, "Turn on your GPS", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    public void uploadQRCodeToDBLocationNoPhoto(String str, String formatName, String sessionUsername) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If no location permission, return
            Toast.makeText(context, "No location permission", Toast.LENGTH_SHORT).show();
            return;
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
                Toast.makeText(context, "Scan Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Toast.makeText(context, "Turn on your GPS", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }
        });

    }

    public void uploadQRCodeToDBNoLocationPhoto(String str, String formatName, String bitmapResizeString, String sessionUsername) {
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
        Toast.makeText(context, "Scan Completed", Toast.LENGTH_SHORT).show();

    }


    public void uploadQRCodeToDBNoLocationNoPhoto(String str, String formatName, String sessionUsername) {
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
        Toast.makeText(context, "Scan Completed", Toast.LENGTH_SHORT).show();
    }


    public void signUpNewUser(@NonNull UserInformation userInformation) {
        collectionReferenceSignInInformation.document(userInformation.getUsername()).set(userInformation);
    }


    public void addComment(Comment comment, @NonNull QRCode qrCode) {
        final var documentReference = db.document(qrCode.getCommentListReference());
        documentReference.update("CommentList", FieldValue.arrayUnion(comment));
    }


    public void deleteComment(Comment comment, @NonNull QRCode qrCode) {
        final var documentReference = db.document(qrCode.getCommentListReference());
        documentReference.update("CommentList", FieldValue.arrayRemove(comment));
    }


    public void deleteUser(String username) {
        if (username.equals("admin")) {
            return;
        }
        collectionReferenceSignInInformation.document(username).get().addOnCompleteListener(task -> {
            var doc = task.getResult();
            var userInformation = doc.toObject(UserInformation.class);
            for (QRCode qrCode: userInformation.getQrCodeList()) {
                db.document(qrCode.getCommentListReference()).delete();
                db.document(qrCode.getPhotoReference()).delete();
            }
            db.collection("SignInInformation").document(username).delete();
            Toast.makeText(context, String.format(Locale.CANADA, "Delete @%s complete", username), Toast.LENGTH_SHORT).show();
        });

    }
}
