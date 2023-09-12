package com.desarrollogj.exampleapi.api.v1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSaveRequest {
    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email required")
    @Email(message = "Email has not a valid format")
    private String email;
}
