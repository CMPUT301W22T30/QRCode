package com.example.qrcodeteam30;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qrcodeteam30.viewclass.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class StartMainActivityTest {
    @Test
    public void testLogInSuccess() throws Exception {
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

        // Sleep 1 second (await for data from Firestore to download to device)
        // Must sleep every time we switch activity (1 second is enough)
        // The reason is because Espresso is too fast, so the new activity has not been registered
        // yet after this code is run => Error
        Thread.sleep(1000);

        Espresso.onData(anything()).inAdapterView(withId(R.id.chooseGameListView)).atPosition(0).perform(click());

        // Check if ranking button is displayed
        Espresso.onView(withId(R.id.button_playerMenu_ranking)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testSignUpButton() throws Exception {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        // Press Sign Up button
        Espresso.onView(withId(R.id.signinactivity_sign_up_button)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check if sign up screen is displayed
        Espresso.onView(withId(R.id.signup_confirm_password_editText)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testQRCodeButton() throws Exception {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        // Press QR Code button
        Espresso.onView(withId(R.id.sign_in_with_qrcode_button)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Go back
        //Espresso.onView(isRoot()).perform(pressBack());

        // Sleep
        Thread.sleep(1000);

        // Check if sign in button is displayed
        Espresso.onView(withId(R.id.sign_in_button)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testSeeMyProfile() throws Exception {
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



        // Press my profile button to sign in
        Espresso.onView(withId(R.id.button_playerMenu_myProfile)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check my profile is displayed
        Espresso.onView(withId(R.id.textView_myprofile_info)).check(matches(isDisplayed()));
    }

    @ Test
    public void testChangeMyProfile() throws Exception {
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

        // Press my profile button to sign in
        Espresso.onView(withId(R.id.button_playerMenu_myProfile)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Press Change profile button
        Espresso.onView(withId(R.id.button_myProfile_changeProfile)).perform(click());

        // type first name
        Espresso.onView(withId(R.id.editText_changeProfile_firstName))
                .perform(typeText("Harry"), closeSoftKeyboard()).check(matches(withText("Harry")));

        // type last name
        Espresso.onView(withId(R.id.editText_changeProfile_lastName))
                .perform(typeText("Maguire"), closeSoftKeyboard()).check(matches(withText("Maguire")));

        // type password
        Espresso.onView(withId(R.id.editText_changeProfile_password))
                .perform(typeText("pwadmin"), closeSoftKeyboard()).check(matches(withText("pwadmin")));

        // Click button update
        Espresso.onView(withId(R.id.button_myProfile_update)).perform(click());

        // Pressback
        Espresso.onView(isRoot()).perform(pressBack());

        // Sleep
        Thread.sleep(1000);

        // Check Update profile
        Espresso.onView(withId(R.id.textView_myprofile_info)).check(matches(withText("Name: Harry Maguire\nUsername: @admin")));

        scenario.close();
    }

    @Test
    public void testGenerateMyQRCode() throws Exception {
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

        // Press my profile button to sign in
        Espresso.onView(withId(R.id.button_playerMenu_myProfile)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Click Generate QR Code button
        onView(withId(R.id.button_myProfile_generateQRCode)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check QR Code
        onView(withId(R.id.MyProfileImageView)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testEstimateMyRanking() throws Exception {
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

        // Press my profile button to sign in
        Espresso.onView(withId(R.id.button_playerMenu_myProfile)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Click Generate My Ranking button
        onView(withId(R.id.button_myProfile_myRanking)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check Ranking is displayed
        onView(withId(R.id.textView_estimateRanking)).check(matches(isDisplayed()));
    }

    @Test
    public void testScanQRCodeButton() throws Exception {
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

        // Press Scan QR Code button in main menu
        Espresso.onView(withId(R.id.button_playerMenu_scanQRCode)).perform(click());

        // Press back
        Espresso.onView(isRoot()).perform(pressBack());

        // Check the main menu is displayed
        Espresso.onView(withId(R.id.button_playerMenu_scanQRCode)).check(matches(isDisplayed()));
    }
}
