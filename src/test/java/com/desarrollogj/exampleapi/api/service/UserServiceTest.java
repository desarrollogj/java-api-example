package com.desarrollogj.exampleapi.api.service;

import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.api.domain.user.UserSearchInput;
import com.desarrollogj.exampleapi.api.domain.user.UserUpdateInput;
import com.desarrollogj.exampleapi.api.repository.UserRepository;
import com.desarrollogj.exampleapi.api.service.impl.UserServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void whenSearchAll_AndActiveUsersExist_ThenReturnSearchResults() {
        var userList = easyRandom.objects(User.class, 5).toList();
        var pageOutput = new PageImpl<>(userList);

        when(repository.findAll(ArgumentMatchers.any(), any(Pageable.class))).thenReturn(pageOutput);

        var input = UserSearchInput.builder()
                .pageNumber(1)
                .pageSize(10)
                .build();
        var result = service.searchAll(input);

        assertNotNull(result);
        assertEquals(userList.size(), result.getTotal());
        assertEquals(userList, result.getData());
    }

    @Test
    void whenSearchAll_AndActiveUsersDoNotExist_ThenReturnEmptySearchResults() {
        when(repository.findAll(ArgumentMatchers.any(), any(Pageable.class))).thenReturn(Page.empty());

        var input = UserSearchInput.builder()
                .pageNumber(1)
                .pageSize(10)
                .build();
        var result = service.searchAll(input);

        assertNotNull(result);
        assertEquals(0, result.getTotal());
    }

    @Test
    void whenGetAll_AndActiveUsersExist_ThenReturnAListOfUsers() {
        var userList = easyRandom.objects(User.class, 5).toList();

        when(repository.findAllActive()).thenReturn(userList);

        var result = service.getAll();

        assertNotNull(result);
        assertEquals(userList, result);
    }

    @Test
    void whenGetAll_AndActiveUsersDoNotExist_ThenReturnAnEmptyListOfUsers() {
        List<User> userList = Collections.emptyList();

        when(repository.findAllActive()).thenReturn(userList);

        var result = service.getAll();

        assertNotNull(result);
        assertEquals(userList, result);
    }

    @Test
    void whenGetById_AndExistsActive_ThenReturnAnUser() {
        var userId = easyRandom.nextLong();
        var user = easyRandom.nextObject(User.class);

        when(repository.findActiveById(userId)).thenReturn(Optional.of(user));

        var result = service.getById(userId);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void whenGetById_AndActiveUserDoesNotExist_ThenReturnEmptyOptional() {
        var userId = easyRandom.nextLong();

        when(repository.findActiveById(userId)).thenReturn(Optional.empty());

        var result = service.getById(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetByReference_AndActiveUserExists_ThenReturnAnUser() {
        var userReference = easyRandom.toString();
        var user = easyRandom.nextObject(User.class);

        when(repository.findActiveByReference(userReference)).thenReturn(Optional.of(user));

        var result = service.getByReference(userReference);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void whenGetByReference_AndActiveUserDoesNotExist_ThenReturnEmptyOptional() {
        var userReference = easyRandom.toString();

        when(repository.findActiveByReference(userReference)).thenReturn(Optional.empty());

        var result = service.getByReference(userReference);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void whenSave_ThenReturnCreatedUser() {
        var user = easyRandom.nextObject(User.class);
        var saveInput = easyRandom.nextObject(UserSaveInput.class);

        when(repository.save(Mockito.any(User.class))).thenReturn(user);

        var result = service.save(saveInput);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void whenUpdate_AndUserExists_ThenReturnUpdatedItem() {
        var user = easyRandom.nextObject(User.class);
        var currentUser = easyRandom.nextObject(User.class);
        var updatedUser = easyRandom.nextObject(User.class);
        var updateInput = easyRandom.nextObject(UserUpdateInput.class);

        when(repository.findById(updateInput.getId())).thenReturn(Optional.of(currentUser));
        when(repository.save(currentUser)).thenReturn(updatedUser);

        var result = service.update(updateInput);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(updatedUser, result.get());
    }

    @Test
    void whenUpdate_AndUserDoesNotExist_ThenReturnEmptyOptional() {
        var updateInput = easyRandom.nextObject(UserUpdateInput.class);

        when(repository.findById(updateInput.getId())).thenReturn(Optional.empty());

        var result = service.update(updateInput);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void whenDelete_AndExists_ThenReturnDeletedUser() {
        var userId = easyRandom.nextLong();
        var currentUser = easyRandom.nextObject(User.class);
        var deletedUser = easyRandom.nextObject(User.class);

        when(repository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(repository.save(currentUser)).thenReturn(deletedUser);

        var result = service.delete(userId);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(deletedUser, result.get());
    }

    @Test
    void whenDelete_AndUserDoesNotExist_ThenReturnEmptyOptional() {
        var userId = easyRandom.nextLong();

        when(repository.findById(userId)).thenReturn(Optional.empty());

        var result = service.delete(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
