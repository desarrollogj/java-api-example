package com.desarrollogj.exampleapi.api.controller.v1;

import com.desarrollogj.exampleapi.api.controller.v1.dto.UserResponse;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserSaveRequest;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserUpdateRequest;
import com.desarrollogj.exampleapi.api.controller.v1.mapper.UserMapper;
import com.desarrollogj.exampleapi.api.domain.validation.ErrorCode;
import com.desarrollogj.exampleapi.api.service.UserService;
import com.desarrollogj.exampleapi.commons.helper.exception.BadRequestException;
import com.desarrollogj.exampleapi.commons.helper.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService service;
  private final UserMapper mapper;

  /**
   * Return a list of users
   *
   * @return A list of {@link UserResponse}
   */
  @GetMapping
  public List<UserResponse> getAll() {
    return mapper.convertToResponseListFromDomainList(service.getAll());
  }

  /**
   * Return an user by its id
   *
   * @param id User id
   * @return {@link UserResponse} data
   */
  @GetMapping(value = "/{id}")
  public UserResponse getById(@PathVariable(value = "id") long id) {
    var user =
        service
            .getById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        ErrorCode.USER_NOT_FOUND.getCode(),
                        ErrorCode.USER_NOT_FOUND.getDescription(id)));
    return mapper.convertToResponseFromDomain(user);
  }

  /**
   * Return an user by its reference
   *
   * @param reference User reference
   * @return {@link UserResponse} data
   */
  @GetMapping(value = "/references/{reference}")
  public UserResponse getByReference(@PathVariable(value = "reference") String reference) {
    var user =
        service
            .getByReference(reference)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        ErrorCode.USER_BY_REFERENCE_NOT_FOUND.getCode(),
                        ErrorCode.USER_BY_REFERENCE_NOT_FOUND.getDescription(reference)));
    return mapper.convertToResponseFromDomain(user);
  }

  /**
   * Save an user
   *
   * @param user {@link UserSaveRequest} data to save
   * @return {@link UserResponse} created user
   */
  @PostMapping()
  @ResponseStatus(value = HttpStatus.CREATED)
  public UserResponse save(@Valid @RequestBody UserSaveRequest user) {
    var userToCreate = mapper.convertToInputFromSaveRequest(user);
    var createdUser = service.save(userToCreate);
    return mapper.convertToResponseFromDomain(createdUser);
  }

  /**
   * Update an user
   *
   * @param user {@link UserUpdateRequest} data to update
   * @return {@link UserResponse} updated user
   */
  @PutMapping(value = "/{id}")
  public UserResponse update(
      @PathVariable(value = "id") long id, @RequestBody UserUpdateRequest user) {
    var userToUpdate = mapper.convertToInputFromUpdateRequest(id, user);
    var updatedUser =
        service
            .update(userToUpdate)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        ErrorCode.USER_NOT_FOUND.getCode(),
                        ErrorCode.USER_NOT_FOUND.getDescription(id)));

    return mapper.convertToResponseFromDomain(updatedUser);
  }

  /**
   * Delete an user
   *
   * @param id User id
   * @return {@link UserResponse} deleted user
   */
  @DeleteMapping(value = "/{id}")
  public UserResponse delete(@PathVariable(value = "id") long id) {
    var deletedUser =
        service
            .delete(id)
            .orElseThrow(
                () ->
                    new BadRequestException(
                        ErrorCode.USER_COULD_NOT_BE_DELETED.getCode(),
                        ErrorCode.USER_COULD_NOT_BE_DELETED.getDescription(id)));
    return mapper.convertToResponseFromDomain(deletedUser);
  }
}
