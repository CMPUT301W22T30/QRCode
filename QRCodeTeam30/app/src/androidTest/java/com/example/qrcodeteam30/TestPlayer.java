package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;

import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qrcodeteam30.viewclass.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestPlayer {

    @Test
    public void addNewQRCodeTest() throws Exception {
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

        // Press Scan QR Code button in main menu
        Espresso.onView(withId(R.id.button_playerMenu_scanQRCode)).perform(click());

        // Press back
        Espresso.onView(isRoot()).perform(pressBack());

        // Check the main menu is displayed
        Espresso.onView(withId(R.id.button_playerMenu_scanQRCode)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void viewMyQRCodeTest() throws Exception {
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

        // Press View QR Code button
        Espresso.onView(withId(R.id.button_playerMenu_viewQRCode)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check My QR Code is displayed
        Espresso.onView(withId(R.id.viewAllQRCode_listView)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void removeQRCodeTest() throws Exception {
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

        // Press View QR Code button
        Espresso.onView(withId(R.id.button_playerMenu_viewQRCode)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Select a QR Code to delete
        Espresso.onData(anything()).inAdapterView(withId(R.id.viewAllQRCode_listView)).atPosition(0)
                .perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check button delete is displayed
        Espresso.onView(withId(R.id.button_delete_qrCode_info)).check(matches(isDisplayed()));

        // Delete QR Code
        Espresso.onView(withId(R.id.button_delete_qrCode_info)).perform(click());

        scenario.close();
    }

    @Test
    public void myHighestAndLowestScoringQRCodeTest() throws Exception {
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

        // Press View QR Code button
        Espresso.onView(withId(R.id.button_playerMenu_viewQRCode)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Press Statistics button
        Espresso.onView(withId(R.id.button_viewAllQRCode_statistics)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check highest and lowest scores are displayed
        Espresso.onView(withId(R.id.user_statistics_textView)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void sumOfScoreOfQRCodesTest() throws Exception {
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

        // Press View QR Code button
        Espresso.onView(withId(R.id.button_playerMenu_viewQRCode)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Press Statistics button
        Espresso.onView(withId(R.id.button_viewAllQRCode_statistics)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check highest and lowest scores are displayed
        Espresso.onView(withId(R.id.user_statistics_textView)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void totalNumberOfQRCodesTest() throws Exception {
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

        // Press View QR Code button
        Espresso.onView(withId(R.id.button_playerMenu_viewQRCode)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Press Statistics button
        Espresso.onView(withId(R.id.button_viewAllQRCode_statistics)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check highest and lowest scores are displayed
        Espresso.onView(withId(R.id.user_statistics_textView)).check(matches(isDisplayed()));

        scenario.close();
    }


    // Not done yet
    @Test
    public void otherPlayerProfile() throws Exception {
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

        // Press Search Username button
        Espresso.onView(withId(R.id.button_playerMenu_searchUsername)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Click to search view
        Espresso.onView(withId(R.id.searchView_searchUsername)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Type username
        Espresso.onView(withId(R.id.searchView_searchUsername)).perform(typeSearchViewText("BiBo1604"));

        // Press Search Button
        Espresso.onView(withId(R.id.searchView_searchUsername)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        // Sleep
        Thread.sleep(1000);

        // Check profile of other player is displayed
        Espresso.onView(withId(R.id.textView_userProfile)).check(matches(isDisplayed()));

        scenario.close();
    }

    public static ViewAction typeSearchViewText(final String text) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(SearchView.class));
            }

            @Override
            public String getDescription() {
                return "Change view text";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((SearchView) view).setQuery(text, false);
            }
        };
    }
}
