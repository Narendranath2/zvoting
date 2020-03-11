package com.roostergames.zvoting;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DB;
import com.mongodb.util.JSON;
@CrossOrigin(origins = "*", allowedHeaders="*")
@RestController
public class VoteConnector {
	@Autowired
	private VoteRepository repository;
	
	@Autowired
	private RegRepository regpository;
	@GetMapping("/")
	public String sayHello() {
		return "Welcome to Zvoting";
	}
	
	@GetMapping("/validate")
	public String validate() {
		return String.valueOf(isChainValid());
	}
	
	@PostMapping("/addVote")
	public String saveVote(@RequestBody UserVote vote) {
		List<UserVote> allVotes = repository.findAll(Sort.by(Sort.Direction.DESC, "_id"));
		for(int i=0;i<allVotes.size();i++) {
			if(allVotes.get(i).getVoterId().equals(vote.getVoterId())) {
				return "Already Voted";
			}
		}
		if(allVotes.size() == 0) {
			vote.setCurHash("justanewhashforgenesisblock");
			vote.setPrevHash("justaprevioushashforgenesisblock");
			vote.setTimeStamp(String.valueOf(System.currentTimeMillis()));
		}else {
			UserVote latestVoteObject = allVotes.get(0);
			String currentHashCalculatedForNewBlock = applySha256(
					latestVoteObject.getPrevHash() + latestVoteObject.getTimeStamp() +
					latestVoteObject.getVoterId() + latestVoteObject.getCandidateId()
			);
			vote.setCurHash(currentHashCalculatedForNewBlock);
			vote.setPrevHash(latestVoteObject.getCurHash());
			vote.setTimeStamp(String.valueOf(System.currentTimeMillis()));
		}
		repository.save(vote);
		return "Successfully voted";
	}
	
	@PostMapping("/login")
	public String login(@Valid @RequestBody UserLogin log, BindingResult result) {
		if(result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			for(ObjectError error: result.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}
			return errors.toString();
		}
		List<UserReg> users = regpository.findAll();
		for(int i=0;i<users.size();i++) {
			if(users.get(i).getPhone().equals(log.getUsername()) && users.get(i).getPassword().equals(log.getPassword())) {
				return users.get(i).getVoterId() + " " + users.get(i).getFirstName();
			}
		}
		return "Invalid details";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid @RequestBody UserReg reg, BindingResult result) {
		if(result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			for(ObjectError error: result.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}
			return errors.toString();
		}
		
		List<UserReg> allUsers = regpository.findAll();
		for(int i=0;i<allUsers.size();i++) {
			System.out.print(allUsers.get(i).getVoterId());
			System.out.print(allUsers.get(i).getAdharId());
		}
		for(int i=0;i<allUsers.size();i++) {
			if(allUsers.get(i).getVoterId().equals(reg.getVoterId()) || allUsers.get(i).getAdharId().equals(reg.getAdharId())) {
				System.out.println("Hello"+allUsers.get(i).getVoterId().equals(reg.getVoterId()));
				System.out.println("Hello"+allUsers.get(i).getAdharId().equals(reg.getAdharId()));
				return "Already registered";
			}
		}
		regpository.save(reg);
		return "Successfully registered";
	}
	
	@GetMapping("/getResult")
	public VoteResult getVotingResult() {
		int nofp = 8;
		List<UserVote> allVotes = repository.findAll();
		VoteResult finalVoteObj = new VoteResult();
		finalVoteObj.setTotalNoOfVotes(allVotes.size());
		int a[] = new int[nofp];
		for(int i=0;i<allVotes.size();i++) {
			a[Integer.valueOf(allVotes.get(i).getCandidateId())-1]++;
		}
		Map<String, Integer> pVotes = new HashMap<String, Integer>();
		for(int i=0;i<a.length;i++) {
			pVotes.put(String.valueOf(i+1), a[i]);
		}
		finalVoteObj.setPartyVotes(pVotes);
		return finalVoteObj;
	}
	public static String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isChainValid() {
		List<UserVote> allVotes = repository.findAll(Sort.by(Sort.Direction.ASC, "_id"));
		for(int i=1;i<allVotes.size();i++) {
			String hash1 = allVotes.get(i).getPrevHash().trim();
			String hash2 = allVotes.get(i-1).getCurHash().trim();
			String hash3 = allVotes.get(i).getCurHash().trim();
			if(!hash1.equals(hash2)) {
				
				return false;
			}
			String currentHashCalculatedForNewBlock = applySha256(
					allVotes.get(i-1).getPrevHash().trim() + allVotes.get(i-1).getTimeStamp() +
					allVotes.get(i-1).getVoterId() + allVotes.get(i-1).getCandidateId()
			);
			if(!hash3.equals(currentHashCalculatedForNewBlock)) {
				return false;
			}
		}
		return true;
	}
}
