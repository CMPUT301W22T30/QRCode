package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.allOf;

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

public class TestOwner {
    @Test
    public void testDeletePlayer() throws Exception {
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        Espresso.onView(withId(R.id.signinactivity_sign_up_button)).perform(click());
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.signup_username_editText)).perform(typeText("testDeletePlayer"));
        Espresso.onView(withId(R.id.signup_password_editText)).perform(typeText("1234"));
        Espresso.onView(withId(R.id.signup_confirm_password_editText)).perform(typeText("1234"));
        Espresso.onView(withId(R.id.signup_firstname_editText)).perform(typeText("Jane"));
        Espresso.onView(withId(R.id.signup_lastname_editText)).perform(typeText("Doe"));
        Espresso.onView(withId(R.id.signupactivity_sign_up_button)).perform(click());
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.button_logout)).perform(click());
        Espresso.onView(withId(android.R.id.button1)).perform(click());  // log out
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.signin_username_editText)).perform(typeText("admin"));
        Espresso.onView(withId(R.id.signin_password_editText)).perform(typeText("pwadmin"));
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
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
