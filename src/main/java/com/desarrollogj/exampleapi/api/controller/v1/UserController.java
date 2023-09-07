package com.desarrollogj.exampleapi.api.controller.v1;

import com.desarrollogj.exampleapi.api.controller.v1.dto.UserResponse;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserSaveRequest;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserUpdateRequest;
import com.desarrollogj.exampleapi.api.controller.v1.mapper.UserMapper;
import com.desarrollogj.exampleapi.api.domain.validation.ErrorCode;
import com.desarrollogj.exampleapi.api.service.UserService;
import com.desarrollogj.exampleapi.commons.helper.exception.BadRequestException;
import com.desarrollogj.exampleapi.commons.helper.exception.NotFoundException;
import com.desarrollogj.exampleapi.commons.helper.web.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    @Operation(summary = "Get a list of active users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active users")
    })
    @GetMapping
    public Flux<UserResponse> getAll() {
        return service.getAll().map(mapper::convertToResponseFromDomain);
    }

    /**
     * Return an user by its id
     *
     * @param id User id
     * @return {@link UserResponse} data
     */
    @Operation(summary = "Get an active user by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/{id}")
    public Mono<UserResponse> getById(@Parameter(description = "User id") @PathVariable(value = "id") long id) {
        return service.getById(id)
                .map(mapper::convertToResponseFromDomain)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        ErrorCode.USER_NOT_FOUND.getCode(),
                        ErrorCode.USER_NOT_FOUND.getDescription(id))));
    }

    /**
     * Return an user by its reference
     *
     * @param reference User reference
     * @return {@link UserResponse} data
     */
    @Operation(summary = "Get an active user by it's reference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/references/{reference}")
    public Mono<UserResponse> getByReference(@Parameter(description = "User reference") @PathVariable(value = "reference") String reference) {
        return service.getByReference(reference)
                .map(mapper::convertToResponseFromDomain)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        ErrorCode.USER_BY_REFERENCE_NOT_FOUND.getCode(),
                        ErrorCode.USER_BY_REFERENCE_NOT_FOUND.getDescription(reference))));
    }

    /**
     * Save an user
     *
     * @param user {@link UserSaveRequest} data to save
     * @return {@link UserResponse} created user
     */
    @Operation(summary = "Create an User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created user", content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not valid user data", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<UserResponse> save(@Parameter(description = "User data") @Valid @RequestBody UserSaveRequest user) {
        return service.save(mapper.convertToInputFromSaveRequest(user))
                .map(mapper::convertToResponseFromDomain);
    }

    /**
     * Update an user
     *
     * @param user {@link UserUpdateRequest} data to update
     * @return {@link UserResponse} updated user
     */
    @Operation(summary = "Update an existing User. Also active inactive users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated user", content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not valid user data", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping(value = "/{id}")
    public Mono<UserResponse> update(@Parameter(description = "User id") @PathVariable(value = "id") long id, @Parameter(description = "User data") @Valid @RequestBody UserUpdateRequest user) {
        return service.update(mapper.convertToInputFromUpdateRequest(id, user))
                .map(mapper::convertToResponseFromDomain)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        ErrorCode.USER_NOT_FOUND.getCode(),
                        ErrorCode.USER_NOT_FOUND.getDescription(id))));
    }

    /**
     * Delete an user
     *
     * @param id User id
     * @return {@link UserResponse} deleted user
     */
    @Operation(summary = "Delete an user. Marks the User as inactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted user", content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping(value = "/{id}")
    public Mono<UserResponse> delete(@Parameter(description = "User id") @PathVariable(value = "id") long id) {
        return service.delete(id)
                .map(mapper::convertToResponseFromDomain)
                .switchIfEmpty(Mono.error(new BadRequestException(
                        ErrorCode.USER_COULD_NOT_BE_DELETED.getCode(),
                        ErrorCode.USER_COULD_NOT_BE_DELETED.getDescription(id))));
    }
}
