package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.viewclass.MainActivity;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DemoTest {
    @Test
    public void testLogInSuccess() throws Exception {
        // Launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        // Type "admin" to username editText
        Espresso.onView(withId(R.id.signin_username_editText))
                .perform(typeText("admin")).check(matches(withText("admin")));
        // Type password
        Espresso.onView(withId(R.id.signin_password_editText))
                .perform(typeText("pwadmin"));

        // Press button to sign in
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());

        // Sleep 1 second (await for data from Firestore to download to device)
        // Must sleep every time we switch activity (1 second is enough)
        // The reason is because Espresso is too fast, so the new activity has not been registered
        // yet after this code is run => Error
        Thread.sleep(1000);

        Espresso.onData(anything()).inAdapterView(withId(R.id.chooseGameListView)).atPosition(0).perform(click());

        Thread.sleep(1000);

        // Check if ranking button is displayed
        Espresso.onView(withId(R.id.button_playerMenu_ranking)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testMyProfile() throws Exception {
        // Launch PlayerMenuActivity
        // This activity has an input "SessionUsername"
        // We simulate the input here
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), PlayerMenuActivity.class);
        intent.putExtra("SessionUsername", "admin");
        Game game = new Game("DefaultAdmin", "admin", "1648684521764");
        intent.putExtra("Game", game);
        ActivityScenario scenario = ActivityScenario.launch(intent);

        // Click on myProfile button to view my profile
        Espresso.onView(withId(R.id.button_playerMenu_myProfile)).perform(click());

        // Check if estimate ranking button is displayed
        Espresso.onView(withId(R.id.button_myProfile_myRanking)).check(matches(isDisplayed()));

        scenario.close();
    }
}
