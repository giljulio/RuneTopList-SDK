package com.runetoplist.model;

public class VoteReward {

	String username;
	int rewardid;

	public VoteReward(String username, int rewardid) {
		this.username = username;
		this.rewardid = rewardid;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getRewardid() {
		return rewardid;
	}
	
}
