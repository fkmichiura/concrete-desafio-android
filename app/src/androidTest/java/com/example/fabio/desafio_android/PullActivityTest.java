package com.example.fabio.desafio_android;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Fabio on 11/01/2018.
 */

@RunWith(AndroidJUnit4.class)
public class PullActivityTest {

    public ActivityTestRule<PullActivity>
            pullActivityActivityTestRule = new ActivityTestRule<>(
            PullActivity.class,
            false,
            true);

    @Test
    public void whenActivityIsLaunched_shouldDisplayInitialState() {
        onView(withId(R.id.tv_pull_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_pull_body)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_pull_username)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_pull_date)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_pull_author)).check(matches(isDisplayed()));
    }
}
