package com.roostergames.zvoting;

import java.util.Map;

public class VoteResult {
	private int totalNoOfVotes;
	private Map<String, Integer> partyVotes;
	public int getTotalNoOfVotes() {
		return totalNoOfVotes;
	}
	public void setTotalNoOfVotes(int totalNoOfVotes) {
		this.totalNoOfVotes = totalNoOfVotes;
	}
	public Map<String, Integer> getPartyVotes() {
		return partyVotes;
	}
	public void setPartyVotes(Map<String, Integer> partyVotes) {
		this.partyVotes = partyVotes;
	}
	public VoteResult(int totalNoOfVotes, Map<String, Integer> partyVotes) {
		super();
		this.totalNoOfVotes = totalNoOfVotes;
		this.partyVotes = partyVotes;
	}
	public VoteResult() {
		super();
	}
	
}
