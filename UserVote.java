package com.roostergames.zvoting;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vote")
public class UserVote {
	@Id
	private String _id;
	private String voterId;
	private String candidateId;
	private String curHash;
	private String prevHash;
	private String timeStamp;
	private String voterName;
	
	public UserVote(String voterId, String candidateId, String curHash, String prevHash, String timeStamp,
			String voterName) {
		super();
		this.voterId = voterId;
		this.candidateId = candidateId;
		this.curHash = curHash;
		this.prevHash = prevHash;
		this.timeStamp = timeStamp;
		this.voterName = voterName;
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getCurHash() {
		return curHash;
	}

	public void setCurHash(String curHash) {
		this.curHash = curHash;
	}

	public String getPrevHash() {
		return prevHash;
	}

	public void setPrevHash(String prevHash) {
		this.prevHash = prevHash;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getVoterName() {
		return voterName;
	}

	public void setVoterName(String voterName) {
		this.voterName = voterName;
	}
	
	
}
