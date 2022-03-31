package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.viewclass.myprofile.ChangeProfileActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class StartChangeProfileActivityTest {
    @Test
    public void testChangeProfile() throws Exception {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ChangeProfileActivity.class);
        intent.putExtra("SessionUsername", "admin");
        Game game = new Game("DefaultAdmin", "admin", "1648684521764");
        intent.putExtra("Game", game);
        ActivityScenario scenario = ActivityScenario.launch(intent);

        // type first name
        Espresso.onView(withId(R.id.editText_changeProfile_firstName))
                .perform(typeText("Harry")).check(matches(withText("Harry")));

        // type last name
        Espresso.onView(withId(R.id.editText_changeProfile_lastName))
                .perform(typeText("Maguire")).check(matches(withText("Maguire")));

        // type password
        Espresso.onView(withId(R.id.editText_changeProfile_password))
                .perform(typeText("pwadmin")).check(matches(withText("pwadmin")));

        // Click button update
        Espresso.onView(withId(R.id.button_myProfile_update)).perform(click());

        // Check button displayed
        Espresso.onView(withId(R.id.button_myProfile_update)).check(matches(isDisplayed()));

        scenario.close();
    }
}
