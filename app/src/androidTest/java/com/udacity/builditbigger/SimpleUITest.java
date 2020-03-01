package com.udacity.builditbigger;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class SimpleUITest {

    private CountingIdlingResource idlingResource;

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            idlingResource = activity.getIdlingResource();
        });
    }

    @Test
    public void checkInitialStateUI() {
        onView(withId(R.id.pb_loader)).check(matches(not(isDisplayed())));
        onView(withId(R.id.instructions_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.button_telljoke)).check(matches(isDisplayed()));
    }

    @Test
    public void checkActionUI() {
        onView(withId(R.id.pb_loader)).check(matches(not(isDisplayed())));
        onView(withId(R.id.instructions_text_view)).check(matches(isDisplayed()));

        onView(withId(R.id.button_telljoke)).perform(click());

        if (idlingResource != null) {
            IdlingRegistry.getInstance().register(idlingResource);
        }

        onView(withId(R.id.tv_joke)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_joke)).check(matches(anyOf(withText("This is totally a funny joke"), withText("There is no joke!"))));
    }

    @After
    public void unregister() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
