package com.desarrollogj.exampleapi.business.user.impl;

import com.desarrollogj.exampleapi.business.user.GetByReferenceUseCase;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetByReferenceUseCaseImpl implements GetByReferenceUseCase {
    private final UserRepository repository;

    @Override
    public Optional<User> execute(String reference) {
        return repository.findActiveByReference(reference);
    }
}
