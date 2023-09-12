package com.desarrollogj.exampleapi.business.user.impl;

import com.desarrollogj.exampleapi.business.user.UpdateUseCase;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.domain.user.UserUpdateInput;
import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateUseCaseImpl implements UpdateUseCase {
    private final UserRepository repository;

    @Override
    public Optional<User> execute(UserUpdateInput input) {
        var currentUser = repository.findById(input.getId());
        if (currentUser.isEmpty()) {
            return currentUser;
        }

        var userToUpdate = currentUser.get();
        userToUpdate.setFirstName(input.getFirstName());
        userToUpdate.setLastName(input.getLastName());
        userToUpdate.setEmail(input.getEmail());
        userToUpdate.setActive(true);
        userToUpdate.setUpdated(ZonedDateTime.now());

        return Optional.of(repository.save(userToUpdate));
    }
}
