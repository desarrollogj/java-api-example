package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.domain.user.UserSaveInput;

public interface SaveUseCase {
    User execute(UserSaveInput input);
}
