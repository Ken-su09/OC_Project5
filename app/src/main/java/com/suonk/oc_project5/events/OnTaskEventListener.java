package com.suonk.oc_project5.events;

import android.view.View;

public interface OnTaskEventListener {
    void onTaskClick(View view, long id);

    void onTaskDelete(long id);
}
