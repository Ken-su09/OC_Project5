package com.suonk.oc_project5.utils;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.ui.tasks.create.CreateTaskViewState;

public class CreateTaskUtils {

    public static void createTask(int position,
                                  @Nullable final String taskName) {
        if (taskName != null) {
            setTaskName(taskName);
        } else {
            setTaskName("");
        }

        setProjectId(position);
    }

    public static void setTaskName(@NonNull String taskName) {
        onView(withId(R.id.add_task_edit_text)).perform(
                replaceText(taskName),
                closeSoftKeyboard());
    }

    public static void setProjectId(int position) {
        onView(withId(R.id.add_task_spinner)).perform(click());

        onData(isA(CreateTaskViewState.class))
                .inRoot(isPlatformPopup())
                .atPosition(position)
                .perform(
                        scrollTo(),
                        click()
                );
    }
}
