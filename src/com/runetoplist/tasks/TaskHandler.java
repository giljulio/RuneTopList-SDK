package com.runetoplist.tasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskHandler {

    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void submit(Runnable runnable) {
        executor.submit(runnable);
    }

}
