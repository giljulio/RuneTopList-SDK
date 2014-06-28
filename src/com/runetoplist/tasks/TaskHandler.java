package com.runetoplist.tasks;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.runetoplist.callbacks.RuneTopListCallback;

public class TaskHandler {

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    
    //key username, value array of callbacks.
    private static ConcurrentHashMap<String, RuneTopListCallback> callbacks = new ConcurrentHashMap<String, RuneTopListCallback>();
    
    public static void submit(Runnable runnable){
    	executor.submit(runnable);
    }
	
    public static ConcurrentHashMap<String, RuneTopListCallback> getCallbackMap(){
    	return callbacks;
    }
}
