package com.example.nicolemorris.lifestyle;


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class NewUserTest {
    @Rule
    public ActivityTestRule<NewUserActivity> newUserActivityActivityTestRule =
            new ActivityTestRule<>(NewUserActivity.class);

    @Test
    public void clickButton() throws  Exception{
        onView(withId(R.id.b_profile)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.b_goals)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.b_bmi)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.b_hikes)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.b_weather)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.b_help)).perform().check(matches(isDisplayed()));
    }
}
