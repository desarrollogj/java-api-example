package com.desarrollogj.exampleapi.api.controller.v1.mapper;

import com.desarrollogj.exampleapi.api.controller.v1.dto.UserResponse;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserSaveRequest;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserSearchResponse;
import com.desarrollogj.exampleapi.api.controller.v1.dto.UserUpdateRequest;
import com.desarrollogj.exampleapi.api.domain.user.User;
import com.desarrollogj.exampleapi.api.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.api.domain.user.UserSearchOutput;
import com.desarrollogj.exampleapi.api.domain.user.UserUpdateInput;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
  private final ModelMapper modelMapper;

  public UserResponse convertToResponseFromDomain(User user) {
    return this.modelMapper.map(user, UserResponse.class);
  }

  public List<UserResponse> convertToResponseListFromDomainList(List<User> users) {
    return users.stream().map(this::convertToResponseFromDomain).toList();
  }

  public UserSaveInput convertToInputFromSaveRequest(UserSaveRequest user) {
    return this.modelMapper.map(user, UserSaveInput.class);
  }

  public UserUpdateInput convertToInputFromUpdateRequest(long id, UserUpdateRequest user) {
    var mapped = this.modelMapper.map(user, UserUpdateInput.class);
    mapped.setId(id);
    return mapped;
  }

  public UserSearchResponse convertToResponseFromSearchOutput(UserSearchOutput output) {
    return this.modelMapper.map(output, UserSearchResponse.class);
  }
}
