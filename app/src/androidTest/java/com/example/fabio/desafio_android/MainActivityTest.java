package com.example.fabio.desafio_android;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Created by Fabio on 11/01/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public ActivityTestRule<MainActivity>
        mainActivityActivityTestRule = new ActivityTestRule<>(
                MainActivity.class,
            false,
            true);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.fabio.desafio_android", appContext.getPackageName());
    }

    @Test
    public void whenActivityIsLaunched_shouldDisplayInitialState() {
        onView(withId(R.id.tv_rep_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_rep_descr)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_star_number)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_fork_number)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_rep_fullname)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_rep_username)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_rep_author)).check(matches(isDisplayed()));
    }

    //Verifica se o item da CardView é clicável
    @Test
    public void testRepositoryCardViewIsClickable() {
        onView(withId(R.id.main_cardview)).check(matches(isClickable()));
    }

    @Test
    public void whenClickOnCardViewItem_shouldOpenPullActivity() {
        Intents.init();

        Matcher<Intent> matcher = hasComponent(MainActivity.class.getName());

        onView(withId(R.id.main_cardview)).perform(click());
        intended(matcher);
        Intents.release();
    }
}
