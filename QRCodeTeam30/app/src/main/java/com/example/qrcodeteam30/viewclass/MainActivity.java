package com.example.qrcodeteam30.viewclass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.controllerclass.DeviceUniqueIDController;
import com.example.qrcodeteam30.controllerclass.MyCryptographyController;
import com.example.qrcodeteam30.controllerclass.MyFirestoreUploadController;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.security.NoSuchAlgorithmException;

/**
 * This activity is the interface for sign in. This is also the "Main" Activity for the app.
 */
public class MainActivity extends AppCompatActivity {
    static private boolean retrievedSignInInformation = false;  // Behave as a lock/mutex
    private CollectionReference collectionReferenceSignInInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_logout);
        setSupportActionBar(toolbar);
        Button buttonLogOut = findViewById(R.id.button_logout);
        buttonLogOut.setVisibility(View.GONE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        collectionReferenceSignInInformation = db.collection("SignInInformation");

        EditText editTextUsername = findViewById(R.id.signin_username_editText);
        EditText editTextPassword = findViewById(R.id.signin_password_editText);
        Button buttonSignIn = findViewById(R.id.sign_in_button);
        Button buttonSignInQRCode = findViewById(R.id.sign_in_with_qrcode_button);
        Button buttonSignUp = findViewById(R.id.signinactivity_sign_up_button);
        Button buttonHome = findViewById(R.id.button_toolbar_home);
        Button signInWithDeviceButton = findViewById(R.id.signinactivity_sign_in_with_device);
        buttonHome.setVisibility(View.GONE);

        buttonSignIn.setOnClickListener(v -> {
            // If retrieving document from FireStore, then clicking this button will not do anything
            if (retrievedSignInInformation) {
                return;
            }

            // Retrieve document from FireStore
            retrievedSignInInformation = true;

            final var username = editTextUsername.getText().toString();
            final var password = editTextPassword.getText().toString();
            if (username.length() == 0 || password.length() == 0) {
                retrievedSignInInformation = false;
                return;
            }

            final var documentReference = collectionReferenceSignInInformation.document(username);
            documentReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        var userInformation = document.toObject(UserInformation.class);
                        String hashedPassword = null;
                        try {
                            hashedPassword = MyCryptographyController.hashSHA256(password);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        if (userInformation.getPassword().equals(hashedPassword)) {
                            var intent = new Intent(MainActivity.this, ChooseGameActivity.class);
                            intent.putExtra("SessionUsername", username);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No such username", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Firestore", "get SignInInformation document failed with ", task.getException());
                }
                // Retrieving document complete
                retrievedSignInInformation = false;
            });

        });

        buttonSignInQRCode.setOnClickListener(v -> {
            var scanOptions = new ScanOptions();
            scanOptions.setBeepEnabled(false);
            barcodeLauncher.launch(scanOptions);
        });

        buttonSignUp.setOnClickListener(v -> {
            var intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        MyFirestoreUploadController myFirestoreUpload = new MyFirestoreUploadController(getApplicationContext());
        String uniqueID = DeviceUniqueIDController.getDeviceUniqueID(this, this);

        signInWithDeviceButton.setOnClickListener(v -> {
            final DocumentReference documentReference = collectionReferenceSignInInformation.document(uniqueID);

            documentReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        var intent = new Intent(MainActivity.this, ChooseGameActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("SessionUsername", uniqueID);
                        startActivity(intent);
                    } else {
                        UserInformation userInformation = null;
                        try {
                            userInformation = new UserInformation(uniqueID, MyCryptographyController.hashSHA256(uniqueID + "QRCodeTeam30"), "", "", 0);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        myFirestoreUpload.signUpNewUser(userInformation);

                        var intent = new Intent(MainActivity.this, ChooseGameActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("SessionUsername", uniqueID);
                        startActivity(intent);
                    }
                    finish();
                }
            });
        });
    }

    @Override
    protected void onResume() {
        retrievedSignInInformation = false;
        super.onResume();
    }


    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                String str = result.getContents();
                if (str == null) {
                    Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    String strList[] = str.split(" ");
                    if (strList.length != 2) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final var username = strList[0];
                    final var password = strList[1];

                    if (username.length() == 0 || password.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final var documentReference = collectionReferenceSignInInformation.document(username);

                    documentReference.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                var userInformation = document.toObject(UserInformation.class);

                                if (userInformation.getPassword().equals(password)) {
                                    var intent = new Intent(MainActivity.this, ChooseGameActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("SessionUsername", username);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("Firestore", "get SignInInformation document failed with ", task.getException());
                        }
                        // Retrieving document complete
                        retrievedSignInInformation = false;
                    });

                }
            });
}