package com.suonk.oc_project5.utils;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.anything;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.suonk.oc_project5.R;

public class CreateTaskUtils {

    public static void createTask(@Nullable final Integer position,
                                  @Nullable final String taskName) {
        if (taskName != null) {
            setTaskName(taskName);
        } else {
            setTaskName("");
        }

        if (position != null) {
            setProjectId(position);
        } else {
            setProjectId(0);
        }
    }

    public static void setTaskName(@NonNull String taskName) {
        onView(withId(R.id.add_task_edit_text)).perform(
                replaceText(taskName),
                closeSoftKeyboard());
    }

    public static void setProjectId(@NonNull Integer position) {
        onView(withId(R.id.add_task_spinner)).perform(click());
        onData(anything()).atPosition(position).perform(click());
    }
}
