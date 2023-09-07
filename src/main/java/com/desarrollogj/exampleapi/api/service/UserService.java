package com.desarrollogj.exampleapi.api.service;

import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.api.domain.user.UserUpdateInput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Flux<User> getAll();
    Mono<User> getById(long id);
    Mono<User> getByReference(String reference);
    Mono<User> save(UserSaveInput input);
    Mono<User> update(UserUpdateInput input);
    Mono<User> delete(long id);
}

