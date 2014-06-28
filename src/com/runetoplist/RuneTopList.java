package com.runetoplist;

import com.runetoplist.callbacks.RuneTopListCallback;
import com.runetoplist.callbacks.VoteRewardCallback;
import com.runetoplist.tasks.RewardCheckerTask;
import com.runetoplist.tasks.TaskHandler;

public abstract class RuneTopList {

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
		TaskHandler.submit(new RewardCheckerTask(username, callback));
	}
	
	public static boolean runPendingCallbacks(String username, Object object){
		RuneTopListCallback callback = TaskHandler.getCallbackMap().get(username);
		
		//check if there is any callbacks awaiting
		if(callback == null){
			return false;
		}
		
		callback.callback(object);//hack to get around constraints of generic types...
		
		TaskHandler.getCallbackMap().remove(username);
		return true;
	}
}
