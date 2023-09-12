package com.desarrollogj.exampleapi.infrastructure.database.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryMapper {
    private final ModelMapper modelMapper;

    public com.desarrollogj.exampleapi.infrastructure.database.entities.User convertToEntityFromDomain(com.desarrollogj.exampleapi.domain.user.User user) {
        return this.modelMapper.map(user, com.desarrollogj.exampleapi.infrastructure.database.entities.User.class);
    }

    public com.desarrollogj.exampleapi.domain.user.User convertToDomainFromEntity(com.desarrollogj.exampleapi.infrastructure.database.entities.User user) {
        return this.modelMapper.map(user, com.desarrollogj.exampleapi.domain.user.User .class);
    }
}
