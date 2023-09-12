package com.desarrollogj.exampleapi.infrastructure.database;

import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.infrastructure.database.impl.UserJPARepository;
import com.desarrollogj.exampleapi.infrastructure.database.impl.UserRepositoryImpl;
import com.desarrollogj.exampleapi.infrastructure.database.mapper.UserRepositoryMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private UserJPARepository jpaRepository;
    @Mock
    private UserRepositoryMapper mapper;
    @InjectMocks
    private UserRepositoryImpl repository;

    @Test
    void whenFindAllActive_ThenReturnAListOfUsers() {
        var repositoryUser = easyRandom.nextObject(com.desarrollogj.exampleapi.infrastructure.database.entities.User.class);
        var repositoryUsers = List.of(repositoryUser);
        var domainUser = easyRandom.nextObject(User.class);
        var domainUsers = List.of(domainUser);

        when(jpaRepository.findAllActive()).thenReturn(repositoryUsers);
        when(mapper.convertToDomainFromEntity(repositoryUser)).thenReturn(domainUser);

        var result = repository.findAllActive();

        assertNotNull(result);
        assertEquals(domainUsers, result);

        verify(jpaRepository, times(1)).findAllActive();
        verify(mapper, times(1)).convertToDomainFromEntity(repositoryUser);
    }

    @Test
    void whenFindActiveById_AndExists_ThenReturnAnUser() {
        var userId = easyRandom.nextLong();
        var repositoryUser = easyRandom.nextObject(com.desarrollogj.exampleapi.infrastructure.database.entities.User.class);
        var domainUser = easyRandom.nextObject(User.class);

        when(jpaRepository.findActiveById(userId)).thenReturn(Optional.of(repositoryUser));
        when(mapper.convertToDomainFromEntity(repositoryUser)).thenReturn(domainUser);

        var result = repository.findActiveById(userId);

        assertNotNull(result);
        assertEquals(Optional.of(domainUser), result);

        verify(jpaRepository, times(1)).findActiveById(userId);
        verify(mapper, times(1)).convertToDomainFromEntity(repositoryUser);
    }

    @Test
    void whenFindActiveById_AndDoesNotExist_ThenReturnOptionalEmpty() {
        var userId = easyRandom.nextLong();

        when(jpaRepository.findActiveById(userId)).thenReturn(Optional.empty());

        var result = repository.findActiveById(userId);

        assertFalse(result.isPresent());

        verify(jpaRepository, times(1)).findActiveById(userId);
        verifyNoInteractions(mapper);
    }

    @Test
    void whenFindActiveByReference_AndExists_ThenReturnAnUser() {
        var reference = easyRandom.nextObject(String.class);
        var repositoryUser = easyRandom.nextObject(com.desarrollogj.exampleapi.infrastructure.database.entities.User.class);
        var domainUser = easyRandom.nextObject(User.class);

        when(jpaRepository.findActiveByReference(reference)).thenReturn(Optional.of(repositoryUser));
        when(mapper.convertToDomainFromEntity(repositoryUser)).thenReturn(domainUser);

        var result = repository.findActiveByReference(reference);

        assertNotNull(result);
        assertEquals(Optional.of(domainUser), result);

        verify(jpaRepository, times(1)).findActiveByReference(reference);
        verify(mapper, times(1)).convertToDomainFromEntity(repositoryUser);
    }

    @Test
    void whenFindActiveByReference_AndDoesNotExist_ThenReturnOptionalEmpty() {
        var reference = easyRandom.nextObject(String.class);

        when(jpaRepository.findActiveByReference(reference)).thenReturn(Optional.empty());

        var result = repository.findActiveByReference(reference);

        assertFalse(result.isPresent());

        verify(jpaRepository, times(1)).findActiveByReference(reference);
        verifyNoInteractions(mapper);
    }

    @Test
    void whenFindById_AndExists_ThenReturnAnUser() {
        var userId = easyRandom.nextLong();
        var repositoryUser = easyRandom.nextObject(com.desarrollogj.exampleapi.infrastructure.database.entities.User.class);
        var domainUser = easyRandom.nextObject(User.class);

        when(jpaRepository.findById(userId)).thenReturn(Optional.of(repositoryUser));
        when(mapper.convertToDomainFromEntity(repositoryUser)).thenReturn(domainUser);

        var result = repository.findById(userId);

        assertNotNull(result);
        assertEquals(Optional.of(domainUser), result);

        verify(jpaRepository, times(1)).findById(userId);
        verify(mapper, times(1)).convertToDomainFromEntity(repositoryUser);
    }

    @Test
    void whenFindById_AndDoesNotExist_ThenReturnOptionalEmpty() {
        var userId = easyRandom.nextLong();

        when(jpaRepository.findById(userId)).thenReturn(Optional.empty());

        var result = repository.findById(userId);

        assertFalse(result.isPresent());

        verify(jpaRepository, times(1)).findById(userId);
        verifyNoInteractions(mapper);
    }

    @Test
    void whenSave_ThenReturnSavedUser() {
        var userToSave = easyRandom.nextObject(User.class);
        var mappedUserToSave = easyRandom.nextObject(com.desarrollogj.exampleapi.infrastructure.database.entities.User.class);
        var savedUser = easyRandom.nextObject(com.desarrollogj.exampleapi.infrastructure.database.entities.User.class);
        var mappedSavedUser = easyRandom.nextObject(User.class);

        when(mapper.convertToEntityFromDomain(userToSave)).thenReturn(mappedUserToSave);
        when(jpaRepository.save(mappedUserToSave)).thenReturn(savedUser);
        when(mapper.convertToDomainFromEntity(savedUser)).thenReturn(mappedSavedUser);

        var result = repository.save(userToSave);

        assertNotNull(result);
        assertEquals(mappedSavedUser, result);

        verify(mapper, times(1)).convertToEntityFromDomain(userToSave);
        verify(jpaRepository, times(1)).save(mappedUserToSave);
        verify(mapper, times(1)).convertToDomainFromEntity(savedUser);
    }
}
