package com.desarrollogj.exampleapi.controller.v1;

import com.desarrollogj.exampleapi.api.controller.v1.UserController;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserResponse;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserSaveRequest;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserUpdateRequest;
import com.desarrollogj.exampleapi.api.controller.v1.mapper.UserMapper;
import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.api.domain.user.UserUpdateInput;
import com.desarrollogj.exampleapi.api.service.UserService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebFluxTest(UserController.class)
@ContextConfiguration(classes = UserControllerWebClientTest.class)
@ComponentScan(basePackages = "com.desarrollogj.exampleapi")
public class UserControllerWebClientTest {
    private final EasyRandom easyRandom = new EasyRandom();
    @Autowired
    private WebTestClient webClient;
    @MockBean
    private UserService service;
    @MockBean
    private UserMapper mapper;

    @Test
    void whenGetAll_AndActiveUsersExist_ThenReturnAListOfUsers() {
        var user = easyRandom.nextObject(User.class);
        var users = List.of(user);
        var userResponse = easyRandom.nextObject(UserResponse.class);

        when(service.getAll()).thenReturn(Flux.fromIterable(users));
        when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

        webClient.get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(UserResponse.class);
    }

    @Test
    void whenGetAll_AndActiveUsersDoNotExist_ThenReturnAnEmptyListOfUsers() {
        when(service.getAll()).thenReturn(Flux.empty());

        webClient.get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus()
                .isOk();

        verifyNoInteractions(mapper);
    }

    @Test
    void whenGetById_AndExists_ThenReturnAnUser() {
        var userId = easyRandom.nextLong();
        var user = easyRandom.nextObject(User.class);
        var userResponse = easyRandom.nextObject(UserResponse.class);

        when(service.getById(userId)).thenReturn(Mono.just(user));
        when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

        webClient.get()
                .uri(String.format("/api/v1/users/%d", userId))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserResponse.class);
    }

    @Test
    void whenGetById_AndDoesNotExist_ThenThrowNotFoundException() {
        var userId = easyRandom.nextLong();

        when(service.getById(userId)).thenReturn(Mono.empty());

        webClient.get()
                .uri(String.format("/api/v1/users/%d", userId))
                .exchange()
                .expectStatus()
                .isNotFound();

        verifyNoInteractions(mapper);
    }

    @Test
    void whenGetByReference_AndExists_ThenReturnAnUser() {
        var userReference = easyRandom.nextObject(String.class);
        var user = easyRandom.nextObject(User.class);
        var userResponse = easyRandom.nextObject(UserResponse.class);

        when(service.getByReference(userReference)).thenReturn(Mono.just(user));
        when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

        webClient.get()
                .uri(String.format("/api/v1/users/references/%s", userReference))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserResponse.class);
    }

    @Test
    void whenGetByReference_AndDoesNotExist_ThenThrowNotFoundException() {
        var userReference = easyRandom.nextObject(String.class);

        when(service.getByReference(userReference)).thenReturn(Mono.empty());

        webClient.get()
                .uri(String.format("/api/v1/users/references/%s", userReference))
                .exchange()
                .expectStatus()
                .isNotFound();

        verifyNoInteractions(mapper);
    }

    @Test
    void whenSave_ThenReturnCreatedUser() {
        var request = easyRandom.nextObject(UserSaveRequest.class);
        request.setEmail("test@foobar.com.ar");
        var input = easyRandom.nextObject(UserSaveInput.class);
        var created = easyRandom.nextObject(User.class);
        var response = easyRandom.nextObject(UserResponse.class);

        when(mapper.convertToInputFromSaveRequest(request)).thenReturn(input);
        when(service.save(input)).thenReturn(Mono.just(created));
        when(mapper.convertToResponseFromDomain(created)).thenReturn(response);

        webClient.post()
                .uri("/api/v1/users")
                .body(Mono.just(request), UserSaveRequest.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(UserResponse.class);
    }

    @Test
    void whenUpdate_AndItsSuccessful_ThenReturnUpdatedUser() {
        var userId = easyRandom.nextLong();
        var request = easyRandom.nextObject(UserUpdateRequest.class);
        request.setEmail("test@foobar.com.ar");
        var input = easyRandom.nextObject(UserUpdateInput.class);
        input.setId(userId);
        var updated = easyRandom.nextObject(User.class);
        var response = easyRandom.nextObject(UserResponse.class);

        when(mapper.convertToInputFromUpdateRequest(userId, request)).thenReturn(input);
        when(service.update(input)).thenReturn(Mono.just(updated));
        when(mapper.convertToResponseFromDomain(updated)).thenReturn(response);

        webClient.put()
                .uri(String.format("/api/v1/users/%d", userId))
                .body(Mono.just(request), UserUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserResponse.class);
    }

    @Test
    void whenUpdate_AndUserDoesNotExists_ThenReturnHttpStatusNotFound() {
        var userId = easyRandom.nextLong();
        var request = easyRandom.nextObject(UserUpdateRequest.class);
        request.setEmail("test@foobar.com.ar");
        var input = easyRandom.nextObject(UserUpdateInput.class);
        input.setId(userId);

        when(mapper.convertToInputFromUpdateRequest(userId, request)).thenReturn(input);
        when(service.update(input)).thenReturn(Mono.empty());

        webClient.put()
                .uri(String.format("/api/v1/users/%d", userId))
                .body(Mono.just(request), UserUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void whenDelete_AndItsSuccessful_ThenReturnDeletedUser() {
        var userId = easyRandom.nextLong();
        var user = easyRandom.nextObject(User.class);
        var userResponse = easyRandom.nextObject(UserResponse.class);

        when(service.delete(userId)).thenReturn(Mono.just(user));
        when(mapper.convertToResponseFromDomain(user)).thenReturn(userResponse);

        webClient.delete()
                .uri(String.format("/api/v1/users/%d", userId))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserResponse.class);
    }

    @Test
    void whenDelete_AndUserDoesNotExist_ThenReturnHttpStatusBadRequest() {
        var userId = easyRandom.nextLong();

        when(service.delete(userId)).thenReturn(Mono.empty());

        webClient.delete()
                .uri(String.format("/api/v1/users/%d", userId))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verifyNoInteractions(mapper);
    }
}
