package com.roostergames.zvoting;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegRepository extends MongoRepository<UserReg, String>{

}
