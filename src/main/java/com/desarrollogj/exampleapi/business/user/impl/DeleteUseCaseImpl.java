package com.desarrollogj.exampleapi.business.user.impl;

import com.desarrollogj.exampleapi.business.user.DeleteUseCase;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeleteUseCaseImpl implements DeleteUseCase {
    private final UserRepository repository;

    @Override
    public Optional<User> execute(long id) {
        var currentUser = repository.findById(id);
        if (currentUser.isEmpty()) {
            return currentUser;
        }

        var userToDelete = currentUser.get();
        userToDelete.setActive(false);
        userToDelete.setUpdated(ZonedDateTime.now());

        return Optional.of(repository.save(userToDelete));
    }
}
