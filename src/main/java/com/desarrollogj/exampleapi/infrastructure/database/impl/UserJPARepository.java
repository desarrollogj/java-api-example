package com.desarrollogj.exampleapi.infrastructure.database.impl;

import com.desarrollogj.exampleapi.infrastructure.database.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserJPARepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.active = true")
    List<User> findAllActive();

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.active = true")
    Optional<User> findActiveById(long id);

    @Query("SELECT u FROM User u WHERE u.reference = :reference AND u.active = true")
    Optional<User> findActiveByReference(String reference);
}
