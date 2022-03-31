package com.example.qrcodeteam30;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;

import com.example.qrcodeteam30.viewclass.MainActivity;

import org.junit.Test;

public class TestScoring {

    @Test
    public void gameWideHighScoreTest() throws Exception {
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

        // Press ranking button
        Espresso.onView(withId(R.id.button_playerMenu_ranking)).perform(click());

        // Sleep
        Thread.sleep(1000);

        // Check ranking is displayed
        Espresso.onView(withId(R.id.listView_ranking)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void myRankingForTheHighestScoreTest() throws Exception {
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

        // Check my ranking for highest score is displayed
        onView(withId(R.id.textView_estimateRanking)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void myRankingForTotalNumberOfQRCodesTest() throws Exception {
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

        // Check my ranking for total number of QR Code is displayed
        onView(withId(R.id.textView_estimateRanking)).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void myRankingForTotalSumOfScoreTest() throws Exception {
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

        // Check my ranking for total sum of score is displayed
        onView(withId(R.id.textView_estimateRanking)).check(matches(isDisplayed()));

        scenario.close();
    }
}
