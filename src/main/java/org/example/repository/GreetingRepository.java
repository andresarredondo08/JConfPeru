package org.example.repository;

import org.example.model.Greeting;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GreetingRepository extends ReactiveMongoRepository<Greeting, String> {
}