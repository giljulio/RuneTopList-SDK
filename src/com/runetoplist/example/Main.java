package com.runetoplist.example;

import com.runetoplist.RuneTopList;
import com.runetoplist.callbacks.VoteRewardCallback;
import com.runetoplist.model.VoteReward;

public class Main {
	


	public static void main(String args[]) throws InterruptedException{

		
		//Add this line in when the server is initializing
		RuneTopList.init("testpkother", "qaq");
		
		

		final Player player = new Player();
		
		
		//Add this code in the commands file
		RuneTopList.checkRewards("king", new VoteRewardCallback() {
			
			@Override
			public void callback() {
				synchronized (player) {					
					if(getVoteRewards().size() == 0){					
						player.sendMessage("You haven't voted! Do ::vote to vote.");
					} else {
						
						//TODO: Should check if there is enough space to add items in inventory, if not then bank
						for(VoteReward reward : getVoteRewards()){
							switch (reward.getRewardid()) {//find reward id in the vote4reward control panel at runtoplist.com/YOUR_SERVERUSERNAME/cp
							case 401:
								player.sendMessage("Add potions here");
								break;
							case 7332:
								player.sendMessage("Add new battle staff");
								break;
							default:
								System.out.println("Missing reward id: " + reward.getRewardid());
								break;
							}
							player.votes++;
						}
					}
				}
				
			}
		});
		

		
		
		
		
	}
}
