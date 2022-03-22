package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;

import org.junit.Test;

public class TestPlayerProfile {
    @Test
    public void uniqueUsernameTest() throws Exception {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        // Sign up new account with existed username
        // Press sign up button
        Espresso.onView(withId(R.id.signinactivity_sign_up_button)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Type existed username
        Espresso.onView(withId(R.id.signup_username_editText))
                .perform(typeText("BiBo1604"), closeSoftKeyboard()).check(matches(withText("BiBo1604")));

        // Type a new password
        Espresso.onView(withId(R.id.signup_password_editText))
                .perform(typeText("12345"), closeSoftKeyboard()).check(matches(withText("12345")));

        // Type a confirm password
        Espresso.onView(withId(R.id.signup_confirm_password_editText))
                .perform(typeText("12345"), closeSoftKeyboard()).check(matches(withText("12345")));

        // Type first name
        Espresso.onView(withId(R.id.signup_firstname_editText))
                .perform(typeText("Lionel"), closeSoftKeyboard()).check(matches(withText("Lionel")));

        // Type last name
        Espresso.onView(withId(R.id.signup_lastname_editText))
                .perform(typeText("Messi"), closeSoftKeyboard()).check(matches(withText("Messi")));

        // Press sign up button
        Espresso.onView(withId(R.id.signupactivity_sign_up_button)).perform(click());

        // Press back
        Espresso.onView(isRoot()).perform(pressBack());

        // Sleep
        Thread.sleep(1000);

        // Try to sign in with the new account with an exited username
        // Type the existed username
        Espresso.onView(withId(R.id.signin_username_editText))
                .perform(typeText("BiBo1604")).check(matches(withText("BiBo1604")));
        // Type a new password
        Espresso.onView(withId(R.id.signin_password_editText))
                .perform(typeText("12345")).check(matches(withText("12345")));

        // Press sign in button
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());

        // Can not sign in and main menu is not displayed
        Espresso.onView(withId(R.id.signinactivity_sign_up_button)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void usingQRToLogInTest() throws Exception {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        Espresso.onView(withId(R.id.sign_in_with_qrcode_button)).check(matches(isClickable()));

        scenario.close();
    }
}
