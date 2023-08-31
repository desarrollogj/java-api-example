package com.desarrollogj.exampleapi.api.domain.user;

import lombok.Data;

@Data
public class UserUpdateInput {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
