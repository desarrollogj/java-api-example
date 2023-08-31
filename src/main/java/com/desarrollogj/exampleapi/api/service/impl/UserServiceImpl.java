package com.desarrollogj.exampleapi.api.service.impl;

import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.api.domain.user.UserUpdateInput;
import com.desarrollogj.exampleapi.api.repository.UserRepository;
import com.desarrollogj.exampleapi.api.service.UserService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository repository;

  @Override
  public List<User> getAll() {
    var list = new ArrayList<User>();
    repository.findAllActive().forEach(list::add);
    return list;
  }

  @Override
  public Optional<User> getById(long id) {
    return repository.findActiveById(id);
  }

  @Override
  public Optional<User> getByReference(String reference) {
    return repository.findActiveByReference(reference);
  }

  @Override
  public User save(UserSaveInput input) {
    var now = ZonedDateTime.now();
    var user =
        User.builder()
            .reference(UUID.randomUUID().toString())
            .firstName(input.getFirstName())
            .lastName(input.getLastName())
            .email(input.getEmail())
            .active(true)
            .created(now)
            .updated(now)
            .build();
    return repository.save(user);
  }

  @Override
  public Optional<User> update(UserUpdateInput input) {
    var currentUser = repository.findById(input.getId());
    if (currentUser.isEmpty()) {
      return currentUser;
    }

    var userToUpdate = currentUser.get();
    userToUpdate.setFirstName(input.getFirstName());
    userToUpdate.setLastName(input.getLastName());
    userToUpdate.setEmail(input.getEmail());
    userToUpdate.setActive(true);
    userToUpdate.setUpdated(ZonedDateTime.now());

    return Optional.of(repository.save(userToUpdate));
  }

  @Override
  public Optional<User> delete(long id) {
    var currentUser = repository.findById(id);
    if (currentUser.isEmpty()) {
      return currentUser;
    }

    var userToDelete = currentUser.get();
    userToDelete.setActive(false);
    userToDelete.setUpdated(ZonedDateTime.now());

    return Optional.of(repository.save(userToDelete));
  }
}
