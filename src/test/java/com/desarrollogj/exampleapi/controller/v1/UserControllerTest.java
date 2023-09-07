package com.desarrollogj.exampleapi.controller.v1;

import com.desarrollogj.exampleapi.api.controller.v1.UserController;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserResponse;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserSaveRequest;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserUpdateRequest;
import com.desarrollogj.exampleapi.api.controller.v1.mapper.UserMapper;
import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.api.domain.user.UserUpdateInput;
import com.desarrollogj.exampleapi.api.domain.validation.ErrorCode;
import com.desarrollogj.exampleapi.api.service.UserService;
import com.desarrollogj.exampleapi.commons.helper.exception.BadRequestException;
import com.desarrollogj.exampleapi.commons.helper.exception.NotFoundException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
  private final EasyRandom easyRandom = new EasyRandom();

  @Mock private UserService service;
  @Mock private UserMapper mapper;
  @InjectMocks private UserController controller;

  @Test
  void whenGetAll_AndActiveUsersExist_ThenReturnAListOfUsers() {
    var user = easyRandom.nextObject(User.class);
    var users = List.of(user);
    var userResponse = easyRandom.nextObject(UserResponse.class);
    var usersResponse = List.of(userResponse);

    when(service.getAll()).thenReturn(Flux.fromIterable(users));
    when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

    var response = controller.getAll();
    var resultResponse = response.collectList().block();

    assertNotNull(resultResponse);
    assertEquals(usersResponse, resultResponse);
  }

  @Test
  void whenGetAll_AndActiveUsersDoNotExist_ThenReturnAnEmptyListOfUsers() {
    when(service.getAll()).thenReturn(Flux.empty());

    var response = controller.getAll();
    var resultResponse = response.collectList().block();

    assertNotNull(resultResponse);
    assertEquals(0, resultResponse.size());

    verifyNoInteractions(mapper);
  }

  @Test
  void whenGetById_AndExists_ThenReturnAnUser() {
    var userId = easyRandom.nextLong();
    var user = easyRandom.nextObject(User.class);
    var userResponse = easyRandom.nextObject(UserResponse.class);

    when(service.getById(userId)).thenReturn(Mono.just(user));
    when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

    var response = controller.getById(userId);
    var resultResponse = response.block();

    assertNotNull(resultResponse);
    assertEquals(userResponse, resultResponse);
  }

  @Test
  void whenGetById_AndDoesNotExist_ThenThrowNotFoundException() {
    var userId = easyRandom.nextLong();

    when(service.getById(userId)).thenReturn(Mono.empty());

    assertThrows(NotFoundException.class, () -> controller.getById(userId).block());

    verifyNoInteractions(mapper);
  }

  @Test
  void whenGetByReference_AndExists_ThenReturnAnUser() {
    var userReference = easyRandom.toString();
    var user = easyRandom.nextObject(User.class);
    var userResponse = easyRandom.nextObject(UserResponse.class);

    when(service.getByReference(userReference)).thenReturn(Mono.just(user));
    when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

    var response = controller.getByReference(userReference);
    var resultResponse = response.block();

    assertNotNull(resultResponse);
    assertEquals(userResponse, resultResponse);
  }

  @Test
  void whenGetByReference_AndDoesNotExist_ThenThrowNotFoundException() {
    var userReference = easyRandom.toString();

    when(service.getByReference(userReference)).thenReturn(Mono.empty());

    var ex = assertThrows(NotFoundException.class, () -> controller.getByReference(userReference).block());

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
    when(service.save(input)).thenReturn(Mono.just(created));
    when(mapper.convertToResponseFromDomain(created)).thenReturn(response);

    var user = controller.save(request);
    var resultUser = user.block();

    assertNotNull(resultUser);
    assertEquals(response, resultUser);
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
    when(service.update(input)).thenReturn(Mono.just(updated));
    when(mapper.convertToResponseFromDomain(updated)).thenReturn(response);

    var user = controller.update(userId, request);
    var resultUser = user.block();

    assertNotNull(resultUser);
    assertEquals(response, resultUser);
  }

  @Test
  void whenUpdate_AndUserDoesNotExists_ThenReturnHttpStatusNotFound() {
    var userId = easyRandom.nextLong();
    var request = easyRandom.nextObject(UserUpdateRequest.class);
    var input = easyRandom.nextObject(UserUpdateInput.class);
    input.setId(userId);

    when(mapper.convertToInputFromUpdateRequest(userId, request)).thenReturn(input);
    when(service.update(input)).thenReturn(Mono.empty());

    assertThrows(NotFoundException.class, () -> controller.update(userId, request).block());
  }

  @Test
  void whenDelete_AndItsSuccessful_ThenReturnDeletedUser() {
    var userId = easyRandom.nextLong();
    var user = easyRandom.nextObject(User.class);
    var userResponse = easyRandom.nextObject(UserResponse.class);

    when(service.delete(userId)).thenReturn(Mono.just(user));
    when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

    var response = controller.delete(userId);
    var responseUser = response.block();

    assertNotNull(responseUser);
    assertEquals(userResponse, responseUser);
  }

  @Test
  void whenDelete_AndUserDoesNotExist_ThenReturnHttpStatusBadRequest() {
    var userId = easyRandom.nextLong();

    when(service.delete(userId)).thenReturn(Mono.empty());

    assertThrows(BadRequestException.class, () -> controller.delete(userId).block());

    verifyNoInteractions(mapper);
  }
}
