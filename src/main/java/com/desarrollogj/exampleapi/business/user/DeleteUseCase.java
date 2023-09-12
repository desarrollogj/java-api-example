package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.domain.user.User;

import java.util.Optional;

public interface DeleteUseCase {
    Optional<User> execute(long id);
}
