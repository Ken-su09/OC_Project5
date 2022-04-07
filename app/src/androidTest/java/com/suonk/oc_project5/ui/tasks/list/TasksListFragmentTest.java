package com.suonk.oc_project5.ui.tasks.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.suonk.oc_project5.utils.CreateTaskUtils.createTask;
import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.di.AppModule;
import com.suonk.oc_project5.ui.MainActivity;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.HiltTestApplication;
import dagger.hilt.android.testing.UninstallModules;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
public class TasksListFragmentTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);
    private MainActivity activityRef;

    @Before
    public void setUp() {
        hiltRule.inject();
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> activityRef = activity);
    }

    @Test
    public void tasks_list_should_be_empty() {
        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
    }

    @Test
    public void click_on_add_new_task_open_dialog_fragment() {
        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());
        onView(withId(R.id.add_task_root)).check(matches(isDisplayed()));
    }

    @Test
    public void tasks_list_should_not_be_empty_after_creation() {
        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());

        createTask(1, "Task1");
        onView(withId(R.id.add_task_button)).perform(click());

        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }
}