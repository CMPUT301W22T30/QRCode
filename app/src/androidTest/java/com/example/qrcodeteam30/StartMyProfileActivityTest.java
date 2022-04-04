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

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.viewclass.myprofile.MyProfileActivity;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;

import org.junit.Test;

public class StartMyProfileActivityTest {
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
        onView(withId(R.id.button_playerMenu_myProfile)).perform(click());

        // Check if estimate ranking button is displayed
        onView(withId(R.id.button_myProfile_myRanking)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testChangeProfile() throws Exception {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MyProfileActivity.class);
        intent.putExtra("SessionUsername", "admin");
        Game game = new Game("DefaultAdmin", "admin", "1648684521764");
        intent.putExtra("Game", game);
        ActivityScenario scenario = ActivityScenario.launch(intent);

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
    public void testGenerateQRCode() throws Exception {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MyProfileActivity.class);
        intent.putExtra("SessionUsername", "admin");
        Game game = new Game("DefaultAdmin", "admin", "1648684521764");
        intent.putExtra("Game", game);
        ActivityScenario scenario = ActivityScenario.launch(intent);

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
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MyProfileActivity.class);
        intent.putExtra("SessionUsername", "admin");
        Game game = new Game("DefaultAdmin", "admin", "1648684521764");
        intent.putExtra("Game", game);
        ActivityScenario scenario = ActivityScenario.launch(intent);

        // Click Generate My Ranking button
        onView(withId(R.id.button_myProfile_myRanking)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check Ranking is displayed
        onView(withId(R.id.textView_estimateRanking)).check(matches(isDisplayed()));
    }
}
