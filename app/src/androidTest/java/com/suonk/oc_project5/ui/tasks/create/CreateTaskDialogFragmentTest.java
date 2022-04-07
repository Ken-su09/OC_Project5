package com.suonk.oc_project5.ui.tasks.create;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.material.internal.ContextUtils.getActivity;
import static com.suonk.oc_project5.utils.CreateTaskUtils.createTask;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@LargeTest
@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
public class CreateTaskDialogFragmentTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);
    private MainActivity activityRef;

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> activityRef = activity);
        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());
    }

    @Test
    public void click_on_add_new_task_open_dialog_fragment() {
        onView(withId(R.id.add_task_root)).check(matches(isDisplayed()));
    }

    @Test
    public void test_click_add_task_button_close_the_dialog() {
        createTask(1, "Task1");
        onView(withId(R.id.add_task_button)).perform(click());
        onView(withId(R.id.tasks_rv)).check(matches(isDisplayed()));
    }

    @Test
    public void add_task_with_empty_task_name_should_not_close_the_dialog_and_display_toast_msg_error() {
        createTask(1, "");
        onView(withId(R.id.add_task_button)).perform(click());
        onView(withText(R.string.create_task_dialog_empty_task_name_error)).inRoot(withDecorView(not(is(activityRef.getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.add_task_root)).check(matches(isDisplayed()));
    }

    @Test
    public void add_task_with_null_task_name_should_not_close_the_dialog_and_display_toast_msg_error() {
        createTask(2, null);
        onView(withId(R.id.add_task_button)).perform(click());
        onView(withText(R.string.create_task_dialog_empty_task_name_error)).inRoot(withDecorView(not(is(activityRef.getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.add_task_root)).check(matches(isDisplayed()));
    }
}