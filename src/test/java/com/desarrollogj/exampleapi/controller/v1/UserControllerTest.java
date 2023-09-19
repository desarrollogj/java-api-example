package com.desarrollogj.exampleapi.controller.v1;

import com.desarrollogj.exampleapi.api.controller.v1.UserController;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserResponse;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserSaveRequest;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserSearchResponse;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserUpdateRequest;
import com.desarrollogj.exampleapi.api.controller.v1.mapper.UserMapper;
import com.desarrollogj.exampleapi.api.domain.user.*;
import com.desarrollogj.exampleapi.api.domain.validation.ErrorCode;
import com.desarrollogj.exampleapi.api.service.UserService;
import com.desarrollogj.exampleapi.commons.helper.exception.BadRequestException;
import com.desarrollogj.exampleapi.commons.helper.exception.NotFoundException;
import org.hamcrest.Matchers;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private UserService service;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserController controller;

    @Test
    void whenSearch_AndActiveUsersExist_ThenReturnAListOfUsers() {
        var pageNumber = 1;
        var pageSize = 10;
        var users = easyRandom.objects(User.class, 5).toList();
        var searchOutput = UserSearchOutput.builder()
                .data(users)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
        var usersResponse = easyRandom.objects(UserResponse.class, 5).toList();
        var searchResponse = new UserSearchResponse();
        searchResponse.setData(usersResponse);
        searchResponse.setPageNumber(pageNumber);
        searchResponse.setPageSize(pageSize);
        searchResponse.setTotal(users.size());

        when(service.searchAll(any(UserSearchInput.class))).thenReturn(searchOutput);
        when(mapper.convertToResponseFromSearchOutput(searchOutput)).thenReturn(searchResponse);

        var response = controller.searchAll(null, null, null, pageNumber, pageSize);

        assertNotNull(response);
        assertEquals(usersResponse, searchResponse.getData());
        assertEquals(pageNumber, searchResponse.getPageNumber());
        assertEquals(pageSize, searchResponse.getPageSize());
    }

  @Test
  void whenSearch_AndActiveUsersDoNotExist_ThenReturnAnEmptyListOfUsers() {
    var pageNumber = 1;
    var pageSize = 10;
    var users = new ArrayList<User>();
    var searchOutput = UserSearchOutput.builder()
            .data(users)
            .pageNumber(pageNumber)
            .pageSize(pageSize)
            .build();
    var usersResponse = new ArrayList<UserResponse>();
    var searchResponse = new UserSearchResponse();
    searchResponse.setData(usersResponse);
    searchResponse.setPageNumber(pageNumber);
    searchResponse.setPageSize(pageSize);
    searchResponse.setTotal(users.size());

    when(service.searchAll(any(UserSearchInput.class))).thenReturn(searchOutput);
    when(mapper.convertToResponseFromSearchOutput(searchOutput)).thenReturn(searchResponse);

    var response = controller.searchAll(null, null, null, pageNumber, pageSize);

    assertNotNull(response);
    assertEquals(usersResponse, searchResponse.getData());
    assertEquals(pageNumber, searchResponse.getPageNumber());
    assertEquals(pageSize, searchResponse.getPageSize());
  }

    @Test
    void whenGetAll_AndActiveUsersExist_ThenReturnAListOfUsers() {
        var users = easyRandom.objects(User.class, 5).toList();
        var usersResponse = easyRandom.objects(UserResponse.class, 5).toList();

        when(service.getAll()).thenReturn(users);
        when(mapper.convertToResponseListFromDomainList(users)).thenReturn(usersResponse);

        var response = controller.getAll();

        assertNotNull(response);
        assertEquals(response, usersResponse);
    }

    @Test
    void whenGetAll_AndActiveUsersDoNotExist_ThenReturnAnEmptyListOfUsers() {
        when(service.getAll()).thenReturn(Collections.emptyList());
        when(mapper.convertToResponseListFromDomainList(anyList())).thenReturn(Collections.emptyList());

        var response = controller.getAll();

        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void whenGetById_AndExists_ThenReturnAnUser() {
        var userId = easyRandom.nextLong();
        var user = easyRandom.nextObject(User.class);
        var userResponse = easyRandom.nextObject(UserResponse.class);

        when(service.getById(userId)).thenReturn(Optional.of(user));
        when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

        var response = controller.getById(userId);

        assertNotNull(response);
        assertEquals(response, userResponse);
    }

    @Test
    void whenGetById_AndDoesNotExist_ThenThrowNotFoundException() {
        var userId = easyRandom.nextLong();

        when(service.getById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> controller.getById(userId));

        verifyNoInteractions(mapper);
    }

    @Test
    void whenGetByReference_AndExists_ThenReturnAnUser() {
        var userReference = easyRandom.toString();
        var user = easyRandom.nextObject(User.class);
        var userResponse = easyRandom.nextObject(UserResponse.class);

        when(service.getByReference(userReference)).thenReturn(Optional.of(user));
        when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

        var response = controller.getByReference(userReference);

        assertNotNull(response);
        assertEquals(response, userResponse);
    }

    @Test
    void whenGetByReference_AndDoesNotExist_ThenThrowNotFoundException() {
        var userReference = easyRandom.toString();

        when(service.getByReference(userReference)).thenReturn(Optional.empty());

        var ex = assertThrows(NotFoundException.class, () -> controller.getByReference(userReference));

        assertEquals(ex.getCode(), ErrorCode.USER_BY_REFERENCE_NOT_FOUND.getCode());

        verifyNoInteractions(mapper);
    }

    @Test
    void whenSave_ThenReturnCreatedUser() {
        var request = easyRandom.nextObject(UserSaveRequest.class);
        var input = easyRandom.nextObject(UserSaveInput.class);
        var created = easyRandom.nextObject(User.class);
        var response = easyRandom.nextObject(UserResponse.class);

        when(mapper.convertToInputFromSaveRequest(request)).thenReturn(input);
        when(service.save(input)).thenReturn(created);
        when(mapper.convertToResponseFromDomain(created)).thenReturn(response);

        var user = controller.save(request);

        assertNotNull(user);
        assertEquals(user, response);
    }

    @Test
    void whenUpdate_AndItsSuccessful_ThenReturnUpdatedUser() {
        var userId = easyRandom.nextLong();
        var request = easyRandom.nextObject(UserUpdateRequest.class);
        var input = easyRandom.nextObject(UserUpdateInput.class);
        input.setId(userId);
        var updated = easyRandom.nextObject(User.class);
        var response = easyRandom.nextObject(UserResponse.class);

        when(mapper.convertToInputFromUpdateRequest(userId, request)).thenReturn(input);
        when(service.update(input)).thenReturn(Optional.of(updated));
        when(mapper.convertToResponseFromDomain(updated)).thenReturn(response);

        var user = controller.update(userId, request);

        assertNotNull(user);
        assertEquals(user, response);
    }

    @Test
    void whenUpdate_AndUserDoesNotExists_ThenReturnHttpStatusNotFound() {
        var userId = easyRandom.nextLong();
        var request = easyRandom.nextObject(UserUpdateRequest.class);
        var input = easyRandom.nextObject(UserUpdateInput.class);
        input.setId(userId);

        when(mapper.convertToInputFromUpdateRequest(userId, request)).thenReturn(input);
        when(service.update(input)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> controller.update(userId, request));
    }

    @Test
    void whenDelete_AndItsSuccessful_ThenReturnDeletedUser() {
        var userId = easyRandom.nextLong();
        var user = easyRandom.nextObject(User.class);
        var userDto = easyRandom.nextObject(UserResponse.class);

        when(service.delete(userId)).thenReturn(Optional.of(user));
        when(mapper.convertToResponseFromDomain(user)).thenReturn(userDto);

        var response = controller.delete(userId);

        assertNotNull(response);
        assertEquals(response, userDto);
    }

    @Test
    void whenDelete_AndUserDoesNotExist_ThenReturnHttpStatusBadRequest() {
        var userId = easyRandom.nextLong();

        when(service.delete(userId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> controller.delete(userId));

        verifyNoInteractions(mapper);
    }
}
