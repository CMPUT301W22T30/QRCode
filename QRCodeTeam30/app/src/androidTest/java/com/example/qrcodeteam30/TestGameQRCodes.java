package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestGameQRCodes {
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);


//    @Test
//    public void testQRCodeScan() throws Exception {
//        // Launch MainActivity
//        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
//
//        // Type "admin" to username editText
//        Espresso.onView(withId(R.id.signin_username_editText))
//                .perform(typeText("admin"));
//        // Type password
//        Espresso.onView(withId(R.id.signin_password_editText))
//                .perform(typeText("pwadmin"));
//
//        // Press button to sign in
//        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
//        Thread.sleep(10000);
//        // Press Scan QR Code button to open camera
//        Espresso.onView(withId(R.id.button_playerMenu_scanQRCode)).perform(click());
//        Thread.sleep(10000);
//        // Check if camera is opened
//        intended(toPackage("com.journeyapps"));
//        Thread.sleep(10000);
//        scenario.close();
//    }

    @Test
    public void testComment() throws Exception {
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

        // Press View QR Code button to view QR Codes
        Espresso.onView(withId(R.id.button_playerMenu_viewQRCode)).perform(click());
        Thread.sleep(1000);

        // Press on the first QR Code to view it
        Espresso.onData(anything()).inAdapterView(withId(R.id.viewAllQRCode_listView))
                .atPosition(0)
                .perform(click());
        Thread.sleep(5000);

        // Press on the view comment button to view and add comments
        Espresso.onView(withId(R.id.button_viewComment_qrCode_info));
        Thread.sleep(5000);

        // Type comment
        Espresso.onView(withId(R.id.editText_addComment_viewAllComment))
                .perform(typeText("admin_test_comment"));

        Thread.sleep(1000);

        // Add comment
        Espresso.onView(withId(R.id.button_addComment_viewAllComment)).perform(click());

        // Check comment
        //Espresso.onData(anything()).inAdapterView(withId(R.id.listView_viewAllComment)).check("context")

        scenario.close();
    }

    @Test
    public void testBrowseQRCode() throws  Exception{
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

        // Press ranking button to view ranking
        Espresso.onView(withId(R.id.button_playerMenu_ranking)).perform(click());
        Thread.sleep(1000);

        // Click on the first player to see their profile
        Espresso.onData(anything()).inAdapterView(withId(R.id.listView_ranking))
                .atPosition(0)
                .perform(click());
        Thread.sleep(1000);

        // Press view all A=QR code button to see their QR codes
        Espresso.onView(withId(R.id.button_userProfile_viewAllQRCode)).perform(click());
        Thread.sleep(1000);

        // Check if their codes are displayed
        Espresso.onView(withId(R.id.viewAllQRCode_listView));

        scenario.close();
    }

    @Test
    public void testSameQRCode() throws  Exception {
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

        // Press View QR Code button to view QR Codes
        Espresso.onView(withId(R.id.button_playerMenu_viewQRCode)).perform(click());
        Thread.sleep(1000);

        // Press on the first QR Code to view it
        Espresso.onData(anything()).inAdapterView(withId(R.id.viewAllQRCode_listView))
                .atPosition(0)
                .perform(click());
        Thread.sleep(5000);

        // Press on the Check same QR code button to show players who scanned the same code
        Espresso.onView(withId(R.id.button_checkSameQRCode_qrCode_info)).perform(click());
        Thread.sleep(1000);

        // Check if the listview is displayed
        Espresso.onView(withId(R.id.listView_checkSameQRCode)).check(matches(isDisplayed()));


        scenario.close();
    }
}
