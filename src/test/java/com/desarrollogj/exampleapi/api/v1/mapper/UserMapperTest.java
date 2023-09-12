package com.desarrollogj.exampleapi.api.v1.mapper;

import com.desarrollogj.exampleapi.api.v1.dto.UserResponse;
import com.desarrollogj.exampleapi.api.v1.dto.UserSaveRequest;
import com.desarrollogj.exampleapi.api.v1.dto.UserUpdateRequest;
import com.desarrollogj.exampleapi.api.v1.mapper.UserMapper;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.domain.user.UserUpdateInput;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserMapper mapper;

    @Test
    void whenConvertToResponse_shouldReturnUserResponseObject() {
        var user = easyRandom.nextObject(User.class);
        var userResponse = easyRandom.nextObject(UserResponse.class);

        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);

        var mapped = mapper.convertToResponseFromDomain(user);

        assertNotNull(mapped);
        assertEquals(userResponse, mapped);
    }

    @Test
    void whenConvertListToResponse_shouldReturnUserResponseList() {
        var user = easyRandom.nextObject(User.class);
        var userResponse = easyRandom.nextObject(UserResponse.class);
        var userList = List.of(user);
        var userResponseList = List.of(userResponse);

        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);

        var mapped = mapper.convertToResponseListFromDomainList(userList);

        assertNotNull(mapped);
        assertEquals(userResponseList, mapped);
    }

    @Test
    void whenConvertToEntityFromSaveRequest_shouldReturnUserSaveInputEntity() {
        var input = easyRandom.nextObject(UserSaveInput.class);
        var request = easyRandom.nextObject(UserSaveRequest.class);

        when(modelMapper.map(request, UserSaveInput.class)).thenReturn(input);

        var mapped = mapper.convertToInputFromSaveRequest(request);

        assertNotNull(mapped);
        assertEquals(input, mapped);
    }

    @Test
    void whenConvertToEntityFromUpdateRequest_shouldReturnUserEntity() {
        var userId = easyRandom.nextLong();
        var input = easyRandom.nextObject(UserUpdateInput.class);
        var request = easyRandom.nextObject(UserUpdateRequest.class);

        when(modelMapper.map(request, UserUpdateInput.class)).thenReturn(input);

        var mapped = mapper.convertToInputFromUpdateRequest(userId, request);

        assertNotNull(mapped);
        assertEquals(input, mapped);
    }
}
