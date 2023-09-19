package com.desarrollogj.exampleapi.api.domain.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSearchInput {
    private String firstName;
    private String lastName;
    private String email;
    private int pageNumber;
    private int pageSize;
}
