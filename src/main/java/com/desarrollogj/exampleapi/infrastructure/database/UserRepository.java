package com.desarrollogj.exampleapi.infrastructure.database;

import com.desarrollogj.exampleapi.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAllActive();
    Optional<User> findActiveById(long id);
    Optional<User> findActiveByReference(String reference);
    Optional<User> findById(long id);
    User save(User user);
}
