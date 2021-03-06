package com.example.qrcodeteam30;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.viewclass.PlayerMenuActivity;

import org.junit.Test;

public class StartSearchUserNameActivityTest {
    @Test
    public void testSearchUsername() throws Exception {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), PlayerMenuActivity.class);
        intent.putExtra("SessionUsername", "admin");
        Game game = new Game("DefaultAdmin", "admin", "1648684521764");
        intent.putExtra("Game", game);
        ActivityScenario scenario = ActivityScenario.launch(intent);

        Espresso.onView(withId(R.id.button_playerMenu_searchUsername)).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.buttonQRCode_searchUsername)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.searchView_searchUsername)).check(matches(isDisplayed()));
    }
}
