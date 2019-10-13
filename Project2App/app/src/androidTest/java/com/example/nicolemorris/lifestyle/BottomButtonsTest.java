package com.example.nicolemorris.lifestyle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.matcher.ViewMatchers;

@RunWith(AndroidJUnit4.class)
public class BottomButtonsTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    //Click Profile Button
    @Test
    public void clickProfileButton(){

        //Click ProfileButton
        onView((withId(R.id.ib_profile))).perform(click());

        //Displays ReviewFragment
        onView((withId(R.id.frag_rev))).check(matches(isDisplayed()));

        // BottomButtonsFragment
    }

    //Click Goals Button (Displays ChangeGoalsFragment,BottomButtonsFragment)

    //Click BMI Button (Displays BMIFragment,BottomButtonsFragment)

    //Click Hikes Button (Leaves app)

    //Click Weather Button (Displays WeatherFragment,BottomButtonsFragment)

    //Click Help Button (Displays HelpFragment)

}
