package com.suonk.oc_project5.ui.tasks.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.suonk.oc_project5.utils.CreateTaskUtils.createTask;
import static com.suonk.oc_project5.utils.MatchersAtPositionUtils.atPosition;
import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
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
public class TasksListFragmentTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        hiltRule.inject();
    }

    @Test
    public void tasks_list_should_be_empty() {
        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
    }

    @Test
    public void if_tasks_list_empty_should_display_img_and_message() {
        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
        onView(withId(R.id.no_task_text)).check(matches(isDisplayed()));
        onView(withId(R.id.no_task_image)).check(matches(isDisplayed()));
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

        createTask(0, "Task1");
        onView(allOf(withId(R.id.add_task_button), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));
        onView(allOf(withId(R.id.img_delete), isDisplayed())).perform(click());
    }

    @Test
    public void tasks_list_should_be_empty_after_creation_then_delete() {
        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());

        createTask(1, "Task1");
        onView(allOf(withId(R.id.add_task_button), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));
        onView(allOf(withId(R.id.img_delete), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
    }

    @Test
    public void tasks_list_add_two_size_should_be_two() {
        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));

        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());
        createTask(1, "Task1");
        onView(allOf(withId(R.id.add_task_button), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());
        createTask(2, "Task2");
        onView(allOf(withId(R.id.add_task_button), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(hasMinimumChildCount(2)));
    }

    @Test
    public void tasks_list_add_two_task_then_sort_by_name() {
        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());
        createTask(1, "Task1");
        onView(allOf(withId(R.id.add_task_button), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());
        createTask(1, "Aaaaaaa");
        onView(allOf(withId(R.id.add_task_button), isDisplayed())).perform(click());

        onView(withId(R.id.sort_by_name)).perform();

        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(atPosition(0, hasDescendant(withText("Aaaaaaa")))));
    }

//    @Test
//    public void tasks_list_add_two_task_then_sort_by_date() {
//        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());
//        createTask(2, "Ask2");
//        onView(allOf(withId(R.id.add_task_button), isDisplayed())).perform(click());
//
//        onView(allOf(withId(R.id.add_task), isDisplayed())).perform(click());
//        createTask(0, "Zzzzzz");
//        onView(allOf(withId(R.id.add_task_button), isDisplayed())).perform(click());
//
//        onView(withId(R.id.sort_by_date)).perform();
//
//        onView(allOf(withId(R.id.tasks_rv), isDisplayed())).check(matches(atPosition(0, hasDescendant(withText("Aaaaaaa")))));
//    }
}