package com.example.nicolemorris.lifestyle;

import android.content.Intent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ReviewFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityScenarioRule
            = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp(){
        Intent i = new Intent();
        activityScenarioRule.launchActivity(i);
        onView((withId(R.id.ib_profile))).perform(click());
    }

    @Test
    public void linearLayoutsCompletelyDisplayed(){
        onView((withId(R.id.vll_rev_frag))).check(matches(isCompletelyDisplayed()));
        onView((withId(R.id.hll_1_rev_frag))).check(matches(isCompletelyDisplayed()));
        onView((withId(R.id.hll_2_rev_frag))).check(matches(isCompletelyDisplayed()));
        onView((withId(R.id.hll_3_rev_frag))).check(matches(isCompletelyDisplayed()));
        onView((withId(R.id.hll_4_rev_frag))).check(matches(isCompletelyDisplayed()));
        onView((withId(R.id.hll_5_rev_frag))).check(matches(isCompletelyDisplayed()));
        onView((withId(R.id.hll_6_rev_frag))).check(matches(isCompletelyDisplayed()));
        onView((withId(R.id.hll_7_rev_frag))).check(matches(isCompletelyDisplayed()));

    }

    @Test
    public void checkHardCodedTextLabels(){




    }

//    tv_**_hc: bold, left of tv_**_d, expected text
//    tv_**_d: not bold, right of tv_**_hc, expected text
//    b_edit_profile: clickable, changes to Edit Profile View //change to ib_prof_edit

}
