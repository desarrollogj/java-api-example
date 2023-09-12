package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.domain.user.User;

import java.util.List;

public interface GetAllUseCase {
    List<User> execute();
}
