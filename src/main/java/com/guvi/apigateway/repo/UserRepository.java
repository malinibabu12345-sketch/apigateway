package com.guvi.apigateway.repo;

import com.guvi.apigateway.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findByName(String name);  //name
    boolean existsByName(String name);
}
