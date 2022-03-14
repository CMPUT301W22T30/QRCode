package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;

import org.junit.Test;

public class TestLocation {
    @Test
    public void geoLocationNearbyQRCodes() throws Exception {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        // Type "admin" to username editText
        Espresso.onView(withId(R.id.signin_username_editText))
                .perform(typeText("admin")).check(matches(withText("admin")));
        // Type password
        Espresso.onView(withId(R.id.signin_password_editText))
                .perform(typeText("pwadmin")).check(matches(withText("pwadmin")));

        // Press button to sign in
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Press Search QR Code button
        Espresso.onView(withId(R.id.button_playerMenu_searchQRCode)).perform(click());

        scenario.close();
    }
}
