package com.desarrollogj.exampleapi.business.user.impl;

import com.desarrollogj.exampleapi.business.user.GetByIdUseCase;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetByIdUseCaseImpl implements GetByIdUseCase {
    private final UserRepository repository;

    @Override
    public Optional<User> execute(long id) {
        return repository.findActiveById(id);
    }
}
