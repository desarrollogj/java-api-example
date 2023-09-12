package com.desarrollogj.exampleapi.domain.user;

import lombok.Data;

@Data
public class UserSaveInput {
    private String firstName;
    private String lastName;
    private String email;
}
