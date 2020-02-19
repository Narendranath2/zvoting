package com.roostergames.zvoting;
import org.springframework.data.mongodb.repository.MongoRepository;;
public interface VoteRepository extends MongoRepository<UserVote, String>{

}
