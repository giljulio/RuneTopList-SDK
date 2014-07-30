package com.runetoplist;

import com.runetoplist.callbacks.VoteRewardCallback;
import com.runetoplist.tasks.RewardCheckerTask;
import com.runetoplist.tasks.TaskHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class RuneTopList {

    private static List<String> pendingChecks = new ArrayList<String>();
	private static final String LIVE_API_BASE = "http://api.runetoplist.com/";
	private static String VERSION = "3.2";
	public static String SERVER_USERNAME = "undefined";
	public static String PRIVATE_KEY = "undefined";

	public static String getAPIBaseURL(){
		return LIVE_API_BASE + VERSION + "/";
	}

	public static void init(String serverusername, String privateKey){
		SERVER_USERNAME = serverusername;
		PRIVATE_KEY = privateKey;
	}
	
	public static void checkRewards(String username, VoteRewardCallback callback){
        if (pendingChecks.contains(username)) {
            return;
        }
        pendingChecks.add(username);
		TaskHandler.submit(new RewardCheckerTask(username, callback));
	}

    public static void completeCheck(String username) {
        Iterator<String> it = pendingChecks.iterator();
        while (it.hasNext()) {
            String name = it.next();
            if (name.equalsIgnoreCase(username)) {
                it.remove();
                break;
            }
        }
    }
	
}
