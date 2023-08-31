package com.desarrollogj.exampleapi.api.service;

import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.api.domain.user.UserUpdateInput;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> getById(long id);
    Optional<User> getByReference(String reference);
    User save(UserSaveInput input);
    Optional<User> update(UserUpdateInput input);
    Optional<User> delete(long id);
}

