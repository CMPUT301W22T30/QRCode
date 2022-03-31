package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;

import com.example.qrcodeteam30.viewclass.MainActivity;

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

        Thread.sleep(1000);

        Espresso.onData(anything()).inAdapterView(withId(R.id.chooseGameListView)).atPosition(0).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Press Search QR Code button
        Espresso.onView(withId(R.id.button_playerMenu_searchQRCode)).perform(click());
        Thread.sleep(5000);

        Espresso.onView(withId(R.id.searchQRCode_mapView)).check(matches(isDisplayed()));

        scenario.close();
    }
}
