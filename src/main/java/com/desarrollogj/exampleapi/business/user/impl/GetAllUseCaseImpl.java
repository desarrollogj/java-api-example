package com.desarrollogj.exampleapi.business.user.impl;

import com.desarrollogj.exampleapi.business.user.GetAllUseCase;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUseCaseImpl implements GetAllUseCase {
    private final UserRepository repository;

    @Override
    public List<User> execute() {
        return repository.findAllActive();
    }
}
