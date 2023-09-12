package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.domain.user.UserUpdateInput;

import java.util.Optional;

public interface UpdateUseCase {
    Optional<User> execute(UserUpdateInput input);
}
