package com.desarrollogj.exampleapi.api.repository;

import com.desarrollogj.exampleapi.api.domain.user.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    @Query("SELECT u.* FROM Users u WHERE u.is_active = true")
    Flux<User> findAllActive();

    @Query("SELECT u.* FROM Users u WHERE u.id = :id AND u.is_active = true")
    Mono<User> findActiveById(long id);

    @Query("SELECT u.* FROM Users u WHERE u.reference = :reference AND u.is_active = true")
    Mono<User> findActiveByReference(String reference);
}
