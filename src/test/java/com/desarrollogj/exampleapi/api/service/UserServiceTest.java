package com.desarrollogj.exampleapi.api.service;

import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.api.domain.user.UserUpdateInput;
import com.desarrollogj.exampleapi.api.repository.UserRepository;
import com.desarrollogj.exampleapi.api.service.impl.UserServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void whenGetAll_AndActiveUsersExist_ThenReturnAListOfUsers() {
        var userList = easyRandom.objects(User.class, 5).toList();

        when(repository.findAllActive()).thenReturn(Flux.fromIterable(userList));

        var result = service.getAll();
        var users = result.collectList().block();

        assertNotNull(users);
        assertEquals(userList, users);
    }

    @Test
    void whenGetAll_AndActiveUsersDoNotExist_ThenReturnAnEmptyListOfUsers() {
        List<User> userList = Collections.emptyList();

        when(repository.findAllActive()).thenReturn(Flux.fromIterable(userList));

        var result = service.getAll();
        var users = result.collectList().block();

        assertNotNull(users);
        assertEquals(userList, users);
    }

    @Test
    void whenGetById_AndExistsActive_ThenReturnAnUser() {
        var userId = easyRandom.nextLong();
        var user = easyRandom.nextObject(User.class);

        when(repository.findActiveById(userId)).thenReturn(Mono.just(user));

        var result = service.getById(userId);
        var userFound = result.block();

        assertNotNull(userFound);
        assertEquals(user, userFound);
    }

    @Test
    void whenGetById_AndActiveUserDoesNotExist_ThenReturnEmpty() {
        var userId = easyRandom.nextLong();

        when(repository.findActiveById(userId)).thenReturn(Mono.empty());

        var result = service.getById(userId);
        var userFound = result.block();

        assertNull(userFound);
    }

    @Test
    void whenGetByReference_AndActiveUserExists_ThenReturnAnUser() {
        var userReference = easyRandom.toString();
        var user = easyRandom.nextObject(User.class);

        when(repository.findActiveByReference(userReference)).thenReturn(Mono.just(user));

        var result = service.getByReference(userReference);
        var userFound = result.block();

        assertNotNull(userFound);
        assertEquals(user, userFound);
    }

    @Test
    void whenGetByReference_AndActiveUserDoesNotExist_ThenReturnEmpty() {
        var userReference = easyRandom.toString();

        when(repository.findActiveByReference(userReference)).thenReturn(Mono.empty());

        var result = service.getByReference(userReference);
        var userFound = result.block();

        assertNull(userFound);
    }

    @Test
    void whenSave_ThenReturnCreatedUser() {
        var user = easyRandom.nextObject(User.class);
        var saveInput = easyRandom.nextObject(UserSaveInput.class);

        when(repository.save(Mockito.any(User.class))).thenReturn(Mono.just(user));

        var result = service.save(saveInput);
        var createdUser = result.block();

        assertNotNull(createdUser);
        assertEquals(user, createdUser);
    }

    @Test
    void whenUpdate_AndUserExists_ThenReturnUpdatedItem() {
        var currentUser = easyRandom.nextObject(User.class);
        var updatedUser = easyRandom.nextObject(User.class);
        var updateInput = easyRandom.nextObject(UserUpdateInput.class);

        when(repository.findById(updateInput.getId())).thenReturn(Mono.just(currentUser));
        when(repository.save(currentUser)).thenReturn(Mono.just(updatedUser));

        var result = service.update(updateInput);
        var updatedUserResult = result.block();

        assertNotNull(updatedUserResult);
        assertEquals(updatedUser, updatedUserResult);
    }

    @Test
    void whenUpdate_AndUserDoesNotExist_ThenReturnEmptyOptional() {
        var updateInput = easyRandom.nextObject(UserUpdateInput.class);

        when(repository.findById(updateInput.getId())).thenReturn(Mono.empty());

        var result = service.update(updateInput);
        var updatedUserResult = result.block();

        assertNull(updatedUserResult);
    }

    @Test
    void whenDelete_AndExists_ThenReturnDeletedUser() {
        var userId = easyRandom.nextLong();
        var currentUser = easyRandom.nextObject(User.class);
        var deletedUser = easyRandom.nextObject(User.class);

        when(repository.findById(userId)).thenReturn(Mono.just(currentUser));
        when(repository.save(currentUser)).thenReturn(Mono.just(deletedUser));

        var result = service.delete(userId);
        var deletedUserResult = result.block();

        assertNotNull(deletedUserResult);
        assertEquals(deletedUser, deletedUserResult);
    }

    @Test
    void whenDelete_AndUserDoesNotExist_ThenReturnEmptyOptional() {
        var userId = easyRandom.nextLong();

        when(repository.findById(userId)).thenReturn(Mono.empty());

        var result = service.delete(userId);
        var deletedUserResult = result.block();

        assertNull(deletedUserResult);
    }
}
