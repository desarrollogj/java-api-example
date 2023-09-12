package com.desarrollogj.exampleapi.domain.user;

import lombok.Data;

@Data
public class UserUpdateInput {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
