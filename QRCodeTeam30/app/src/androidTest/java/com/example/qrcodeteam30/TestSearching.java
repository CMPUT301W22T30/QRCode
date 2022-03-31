package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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

import com.example.qrcodeteam30.viewclass.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Test;

public class TestSearching {

    @Test
    public void searchByUsernameTest() throws Exception {
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

    @Test
    public void searchQRCodeTest() throws Exception {
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
