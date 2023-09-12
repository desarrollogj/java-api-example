package com.desarrollogj.exampleapi.business.user.impl;

import com.desarrollogj.exampleapi.business.user.SaveUseCase;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveUseCaseImpl implements SaveUseCase {
    private final UserRepository repository;

    @Override
    public User execute(UserSaveInput input) {
        var now = ZonedDateTime.now();
        var user =
                User.builder()
                        .reference(UUID.randomUUID().toString())
                        .firstName(input.getFirstName())
                        .lastName(input.getLastName())
                        .email(input.getEmail())
                        .active(true)
                        .created(now)
                        .updated(now)
                        .build();
        return repository.save(user);
    }
}
