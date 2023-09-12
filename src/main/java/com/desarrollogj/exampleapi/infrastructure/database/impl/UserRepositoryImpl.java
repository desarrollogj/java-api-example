package com.desarrollogj.exampleapi.infrastructure.database.impl;

import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.infrastructure.database.mapper.UserRepositoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJPARepository jpaRepository;
    private final UserRepositoryMapper mapper;

    @Override
    public List<User> findAllActive() {
        return jpaRepository
                .findAllActive()
                .stream()
                .map(mapper::convertToDomainFromEntity)
                .toList();
    }

    @Override
    public Optional<User> findActiveById(long id) {
        return jpaRepository
                .findActiveById(id)
                .map(mapper::convertToDomainFromEntity);
    }

    @Override
    public Optional<User> findActiveByReference(String reference) {
        return jpaRepository
                .findActiveByReference(reference)
                .map(mapper::convertToDomainFromEntity);
    }

    @Override
    public Optional<User> findById(long id) {
        return jpaRepository
                .findById(id)
                .map(mapper::convertToDomainFromEntity);
    }

    @Override
    public User save(User user) {
        var mapped = mapper.convertToEntityFromDomain(user);
        var saved = jpaRepository.save(mapped);
        return mapper.convertToDomainFromEntity(saved);
    }
}
