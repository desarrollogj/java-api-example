package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.domain.user.User;

import java.util.Optional;

public interface GetByReferenceUseCase {
    Optional<User> execute(String reference);
}
