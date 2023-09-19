package com.desarrollogj.exampleapi.api.controller.v1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserSearchResponse {
    private List<UserResponse> data;
    private int pageNumber;
    private int pageSize;
    private long total;
}
