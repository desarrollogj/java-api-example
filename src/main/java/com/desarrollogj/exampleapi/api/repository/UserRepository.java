package com.desarrollogj.exampleapi.api.repository;

import com.desarrollogj.exampleapi.api.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.active = true")
    List<User> findAllActive();

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.active = true")
    Optional<User> findActiveById(long id);

    @Query("SELECT u FROM User u WHERE u.reference = :reference AND u.active = true")
    Optional<User> findActiveByReference(String reference);
}
