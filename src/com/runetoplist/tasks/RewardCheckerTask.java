package com.runetoplist.tasks;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.runetoplist.RuneTopList;
import com.runetoplist.callbacks.VoteRewardCallback;
import com.runetoplist.model.VoteReward;

public class RewardCheckerTask implements Runnable {

	
	VoteRewardCallback callback;
	String username;
	
	
	public RewardCheckerTask(String username, VoteRewardCallback callback) {
		this.username = username;
		this.callback = callback;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Checking if any pending rewards on runetoplist");

	        URL rtl = new URL(RuneTopList.getAPIBaseURL() + RuneTopList.SERVER_USERNAME + "/vote4rewards/status");
	        HttpURLConnection conn = (HttpURLConnection) rtl.openConnection();
	        conn.setReadTimeout(15 * 1000);
	        conn.setRequestProperty("Content-Type", 
	                "application/x-www-form-urlencoded");

	        String urlParameters =
	        		"private_key=" + URLEncoder.encode(RuneTopList.PRIVATE_KEY, "UTF-8") +
	        		"&username=" + URLEncoder.encode(username, "UTF-8");
	        
	        
	        conn.setRequestProperty("Content-Length", "" + 
	                 Integer.toString(urlParameters.getBytes().length));
	        conn.setRequestMethod("POST"); 
	        conn.setInstanceFollowRedirects(true);
	        conn.setUseCaches(false);
	        conn.setDoOutput(true);

	                
	        //Send request
	        DataOutputStream wr = new DataOutputStream (conn.getOutputStream ());
	        wr.writeBytes (urlParameters);
	        wr.flush ();
	        wr.close ();

	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        String body = "";
	        while ((inputLine = in.readLine()) != null) 
	            body += inputLine;
	        in.close();
	        conn.disconnect();
	        
	        Gson gson = new Gson();
	        Type listType = new TypeToken<List<VoteReward>>(){}.getType();
	        // In this test code i just shove the JSON here as string.
	        List<VoteReward> list = gson.fromJson(body, listType);
			
			//adds the data to the callback
			callback.getVoteRewards().addAll(list);
		
		} catch (JsonSyntaxException e) {
			System.out.println("Incorrect JSON sytax, please check if the runetoplist lib is out of date.");
		} catch (MalformedURLException e) {
			System.out.println("Malformed Url");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to check whether vote has been made. Incorrect key? Incorrect serverusername? ");
		}
		
		//adds the callback to the blocking map, key as the username
		TaskHandler.getCallbackMap().put(username, callback);
	}

	
}