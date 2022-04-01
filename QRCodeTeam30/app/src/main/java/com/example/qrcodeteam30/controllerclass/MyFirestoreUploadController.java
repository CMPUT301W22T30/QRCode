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
import com.example.qrcodeteam30.modelclass.Game;
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

/**
 * Handling anything related to modify the Google Firestore database (on cloud)
 * Because of the limitation of Android, unfortunately for the download part, it is integrated in the Activity (View)
 * class, and we cannot separate it into its own class like this
 */
public class MyFirestoreUploadController {
    private FirebaseFirestore db;
    private CollectionReference collectionReferenceSignInInformation;
    private LocationManager locationManager;
    private Context context;

    /**
     * Constructor
     * @param context
     */
    public MyFirestoreUploadController(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        this.collectionReferenceSignInInformation = db.collection("SignInInformation");
    }

    /**
     * Upload QR Code to database, with location and photo
     * @param str
     * @param formatName
     * @param bitmapResizeString
     * @param sessionUsername
     */
    public void uploadQRCodeToDBLocationPhoto(String str, String formatName, String bitmapResizeString, String sessionUsername, Game game) {
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
                                true, true, "Photo/" + photoDocumentName + "/", game);
                        final DocumentReference documentReference = collectionReferenceSignInInformation.document(sessionUsername);
                        documentReference.update("qrCodeList", FieldValue.arrayUnion(qrCode));
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

    /**
     * Upload QR Code to database, with location and no photo
     * @param str
     * @param formatName
     * @param sessionUsername
     */
    public void uploadQRCodeToDBLocationNoPhoto(String str, String formatName, String sessionUsername, Game game) {
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
                            true, false, "N/A", game);
                    final DocumentReference documentReference = collectionReferenceSignInInformation.document(sessionUsername);
                    documentReference.update("qrCodeList", FieldValue.arrayUnion(qrCode));
                    //documentReference.update("score", FieldValue.increment(qrCode.getScore()));
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

    /**
     * Upload QR Code to database, with no location and photo
     * @param str
     * @param formatName
     * @param bitmapResizeString
     * @param sessionUsername
     */
    public void uploadQRCodeToDBNoLocationPhoto(String str, String formatName, String bitmapResizeString, String sessionUsername, Game game) {
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
                        false, true, "Photo/" + photoDocumentName + "/", game);
                final DocumentReference documentReference = collectionReferenceSignInInformation.document(sessionUsername);
                documentReference.update("qrCodeList", FieldValue.arrayUnion(qrCode));
            });
        });
        Toast.makeText(context, "Scan Completed", Toast.LENGTH_SHORT).show();

    }


    /**
     * Upload QR Code to database, with no location and no photo
     * @param str
     * @param formatName
     * @param sessionUsername
     */
    public void uploadQRCodeToDBNoLocationNoPhoto(String str, String formatName, String sessionUsername, Game game) {
        final var colRefComment = db.collection("Comment");
        Map<String, Object> map = new HashMap<>();
        map.put("CommentList", Arrays.asList());

        colRefComment.add(map).addOnCompleteListener(task -> {
            String commentDocumentName = task.getResult().getId();
            var qrCode = new QRCode(str, 0, 0, sessionUsername,
                    formatName, "Comment/" + commentDocumentName + "/",
                    false, false, "N/A", game);
            final DocumentReference documentReference = collectionReferenceSignInInformation.document(sessionUsername);
            documentReference.update("qrCodeList", FieldValue.arrayUnion(qrCode));
        });
        Toast.makeText(context, "Scan Completed", Toast.LENGTH_SHORT).show();
    }


    /**
     * Upload the new user (which just signed up) to the cloud
     * @param userInformation
     */
    public void signUpNewUser(@NonNull UserInformation userInformation) {
        collectionReferenceSignInInformation.document(userInformation.getUsername()).set(userInformation);
    }


    /**
     * Add the comment to the cloud
     * @param comment
     * @param qrCode
     */
    public void addComment(Comment comment, @NonNull QRCode qrCode) {
        final var documentReference = db.document(qrCode.getCommentListReference());
        documentReference.update("CommentList", FieldValue.arrayUnion(comment));
    }


    /**
     * Delete the comment from the cloud
     * @param comment
     * @param qrCode
     */
    public void deleteComment(Comment comment, @NonNull QRCode qrCode) {
        final var documentReference = db.document(qrCode.getCommentListReference());
        documentReference.update("CommentList", FieldValue.arrayRemove(comment));
    }


    /**
     * Delete user from the cloud
     * This includes delete all of his/her QR code and its associated photos and comments
     * @param username
     */
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

    /**
     * Delete all QR code in a game of a user on Firestore
     * @param username
     * @param game
     */
    public void serverScopeDeleteUser(String username, Game game) {
        DocumentReference docRef = collectionReferenceSignInInformation.document(username);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var document = task.getResult();
                if (document.exists()) {
                    UserInformation userInformation = document.toObject(UserInformation.class);
                    for (var qrCode: userInformation.getQrCodeList()) {
                        if (qrCode.getGameName().equals(game.getGameName())) {
                            db.document(qrCode.getCommentListReference()).delete();
                            db.document(qrCode.getPhotoReference()).delete();
                            docRef.update("qrCodeList", FieldValue.arrayRemove(qrCode));
                        }
                    }
                }
            }
        });
    }
}
