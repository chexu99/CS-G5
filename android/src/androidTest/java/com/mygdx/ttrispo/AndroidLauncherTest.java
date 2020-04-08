package com.mygdx.ttrispo;



import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AndroidLauncherTest {
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(AndroidLauncher.class);

    @Test
    public void performClickOnTextView() {
        //onView(withText("Start")).perform(click());
    }
}