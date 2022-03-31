package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.viewclass.MainActivity;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
public class TestPrivacy {
    @Test
    public void noCodeRecord() {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
        QRCode qrCode = new QRCode("abc", 0, 0, "admin", "QR_CODE",
                "placeHolder", false, false, "placeHolder", "DefaultAdmin", "admin");
        assertNotEquals("abc", qrCode.getQrCodeContent());
    }

    @Test
    public void scanQRCodeForScoreDeclineLocation() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), PlayerMenuActivity.class);
        intent.putExtra("SessionUsername", "admin");
        Game game = new Game("DefaultAdmin", "admin", "1648684521764");
        intent.putExtra("Game", game);
        ActivityScenario scenario = ActivityScenario.launch(intent);

        Espresso.onView(withId(R.id.button_playerMenu_scanQRCode)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Accept/Decline
        Espresso.onView(withText("Accept")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ask photo
        Espresso.onView(withText("No")).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ask location
        Espresso.onView(withText("No")).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
