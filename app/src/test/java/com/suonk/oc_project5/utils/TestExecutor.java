package com.suonk.oc_project5.utils;

import java.util.concurrent.Executor;

public class TestExecutor implements Executor {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
