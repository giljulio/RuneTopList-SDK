package com.runetoplist.callbacks;

import java.util.ArrayList;

import com.runetoplist.model.VoteReward;

public abstract class VoteRewardCallback extends RuneTopListCallback {

	private ArrayList<VoteReward> voteRewards = new ArrayList<VoteReward>(); 
	
	public ArrayList<VoteReward> getVoteRewards(){
		return voteRewards;
	}
	
}
