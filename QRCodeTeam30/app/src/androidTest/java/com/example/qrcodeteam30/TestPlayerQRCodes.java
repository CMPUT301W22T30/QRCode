package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.anything;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qrcodeteam30.viewclass.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestPlayerQRCodes {
    @Test
    public void testGenerateQRCode() throws Exception {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        // Type "admin" to username editText
        Espresso.onView(withId(R.id.signin_username_editText))
                .perform(typeText("admin"));
        // Type password
        Espresso.onView(withId(R.id.signin_password_editText))
                .perform(typeText("pwadmin"));

        // Press button to sign in
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
        Thread.sleep(1000);
        Espresso.onData(anything()).inAdapterView(withId(R.id.chooseGameListView)).atPosition(0).perform(click());
        Thread.sleep(1000);

        // Press My Profile button to view QR Codes
        Espresso.onView(withId(R.id.button_playerMenu_myProfile)).perform(click());
        Thread.sleep(1000);

        // Press Generate QR Code button
        Espresso.onView(withId(R.id.button_myProfile_generateQRCode)).perform(click());
        Thread.sleep(1000);

        // Check the QR Code
        Espresso.onView(withId(R.id.MyProfileImageView)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void loginQRCode() throws Exception {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        Espresso.onView(withId(R.id.sign_in_with_qrcode_button)).check(matches(isClickable()));
        // Type "admin" to username editText
        Espresso.onView(withId(R.id.signin_username_editText))
                .perform(typeText("admin"));
        // Type password
        Espresso.onView(withId(R.id.signin_password_editText))
                .perform(typeText("pwadmin"));

        // Press button to sign in
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
        Thread.sleep(1000);

        Thread.sleep(1000);
        Espresso.onData(anything()).inAdapterView(withId(R.id.chooseGameListView)).atPosition(0).perform(click());

        // Press My Profile button to view QR Codes
        Espresso.onView(withId(R.id.button_playerMenu_myProfile)).perform(click());
        Thread.sleep(1000);

        // Press Generate QR Code button
        Espresso.onView(withId(R.id.button_myProfile_generateQRCode)).perform(click());
        Thread.sleep(1000);

        // Check the QR Code
        Espresso.onView(withId(R.id.MyProfileImageView)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void searchUserRCode() throws Exception {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        Espresso.onView(withId(R.id.sign_in_with_qrcode_button)).check(matches(isClickable()));
        // Type "admin" to username editText
        Espresso.onView(withId(R.id.signin_username_editText))
                .perform(typeText("admin"));
        // Type password
        Espresso.onView(withId(R.id.signin_password_editText))
                .perform(typeText("pwadmin"));

        // Press button to sign in
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
        Thread.sleep(1000);
        Espresso.onData(anything()).inAdapterView(withId(R.id.chooseGameListView)).atPosition(0).perform(click());

        Thread.sleep(1000);



        // Press search username button
        Espresso.onView(withId(R.id.button_playerMenu_searchUsername)).perform(click());
        Thread.sleep(1000);

        // Check if QR code can be used to search username
        Espresso.onView(withId(R.id.buttonQRCode_searchUsername)).check(matches(isClickable()));

        scenario.close();
    }

}
