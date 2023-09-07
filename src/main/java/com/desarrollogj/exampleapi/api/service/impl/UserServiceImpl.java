package com.desarrollogj.exampleapi.api.service.impl;

import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.api.domain.user.UserUpdateInput;
import com.desarrollogj.exampleapi.api.repository.UserRepository;
import com.desarrollogj.exampleapi.api.service.UserService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public Flux<User> getAll() {
        return repository.findAllActive();
    }

    @Override
    public Mono<User> getById(long id) {
        return repository.findActiveById(id);
    }

    @Override
    public Mono<User> getByReference(String reference) {
        return repository.findActiveByReference(reference);
    }

    @Override
    public Mono<User> save(UserSaveInput input) {
        var now = ZonedDateTime.now();
        return Mono.just(User.builder()
                        .reference(UUID.randomUUID().toString())
                        .firstName(input.getFirstName())
                        .lastName(input.getLastName())
                        .email(input.getEmail())
                        .active(true)
                        .created(now)
                        .updated(now)
                        .build())
                .flatMap(repository::save);
    }

    @Override
    public Mono<User> update(UserUpdateInput input) {
        return repository.findById(input.getId())
                .map(userToUpdate -> {
                    userToUpdate.setFirstName(input.getFirstName());
                    userToUpdate.setLastName(input.getLastName());
                    userToUpdate.setEmail(input.getEmail());
                    userToUpdate.setActive(true);
                    userToUpdate.setUpdated(ZonedDateTime.now());
                    return userToUpdate;
                })
                .flatMap(repository::save)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<User> delete(long id) {
        return repository.findById(id)
                .map(userToDelete -> {
                    userToDelete.setActive(false);
                    userToDelete.setUpdated(ZonedDateTime.now());
                    return userToDelete;
                })
                .flatMap(repository::save)
                .switchIfEmpty(Mono.empty());
    }
}
