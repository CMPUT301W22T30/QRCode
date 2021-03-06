package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.example.qrcodeteam30.viewclass.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public class TestOwner {
    @Test
    public void storeSmallImageOnline() throws Exception {
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

        Thread.sleep(1000);

        Espresso.onView(withText("Accept")).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withText("Yes")).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withText("No")).perform(click());

        Thread.sleep(1000);

        Espresso.onView(withId(R.id.button_playerMenu_viewQRCode)).perform(click());
        Thread.sleep(1000);

        final int[] numberOfAdapterItems = new int[1];

        Espresso.onView(withId(R.id.viewAllQRCode_listView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;
                numberOfAdapterItems[0] = listView.getAdapter().getCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.viewAllQRCode_listView))
                .atPosition(numberOfAdapterItems[0] - 1)
                .perform(scrollTo())
                .perform(click());
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.button_viewPhoto_qrCode_info)).perform(click());
        Thread.sleep(2000);
        Espresso.onView(withText("OK")).perform(click());

        scenario.close();
    }

    @Test
    public void deleteQRCode() throws Exception {
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

        Espresso.onView(withId(R.id.button_playerMenu_searchUsername)).perform(click());
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.searchView_searchUsername)).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.searchView_searchUsername)).perform(typeSearchViewText("admin"));
        Espresso.onView(withId(R.id.searchView_searchUsername)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.button_userProfile_viewAllQRCode)).perform(click());
        Thread.sleep(1000);
        Espresso.onData(anything()).inAdapterView(withId(R.id.viewAllQRCode_listView)).atPosition(0).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.button_delete_qrCode_info)).perform(click());
        Thread.sleep(1000);
    }

    @Test
    public void testDeletePlayer() throws Exception {
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        Espresso.onView(withId(R.id.signinactivity_sign_up_button)).perform(click());
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.signup_username_editText)).perform(typeText("testDeletePlayer"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_password_editText)).perform(typeText("1234"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_confirm_password_editText)).perform(typeText("1234"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_firstname_editText)).perform(typeText("Jane"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_lastname_editText)).perform(typeText("Doe"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signupactivity_sign_up_button)).perform(click());
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.button_logout)).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withId(android.R.id.button1)).perform(click());  // log out
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.signin_username_editText)).perform(typeText("admin"));
        Espresso.onView(withId(R.id.signin_password_editText)).perform(typeText("pwadmin"));
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());

        Thread.sleep(1000);
        Espresso.onData(anything()).inAdapterView(withId(R.id.chooseGameListView)).atPosition(0).perform(click());
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.button_playerMenu_searchUsername)).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.searchView_searchUsername)).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.searchView_searchUsername)).perform(typeSearchViewText("testDeletePlayer"));
        Espresso.onView(withId(R.id.searchView_searchUsername)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        Thread.sleep(1000);
        Espresso.onView(withId(R.id.button_userProfile_deleteUser)).perform(click());

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
