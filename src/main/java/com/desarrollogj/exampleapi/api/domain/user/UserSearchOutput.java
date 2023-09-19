package com.desarrollogj.exampleapi.api.domain.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserSearchOutput {
    private List<User> data;
    private int pageNumber;
    private int pageSize;
    private long total;
}
