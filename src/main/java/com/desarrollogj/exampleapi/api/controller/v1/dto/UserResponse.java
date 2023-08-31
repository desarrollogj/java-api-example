package com.desarrollogj.exampleapi.api.controller.v1.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserResponse {
    private Long id;
    private String reference;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean active;
    private ZonedDateTime created;
    private ZonedDateTime updated;
}
