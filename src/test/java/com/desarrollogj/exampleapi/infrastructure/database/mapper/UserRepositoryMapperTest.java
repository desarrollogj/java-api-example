package com.desarrollogj.exampleapi.infrastructure.database.mapper;

import com.desarrollogj.exampleapi.infrastructure.database.entities.User;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryMapperTest {
    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserRepositoryMapper mapper;

    @Test
    void whenConvertEntityToDomain_shouldReturnUserDomainObject() {
        var repositoryUser = easyRandom.nextObject(User.class);
        var domainUser = easyRandom.nextObject(com.desarrollogj.exampleapi.domain.user.User.class);

        when(modelMapper.map(repositoryUser, com.desarrollogj.exampleapi.domain.user.User.class)).thenReturn(domainUser);

        var mapped = mapper.convertToDomainFromEntity(repositoryUser);

        assertNotNull(mapped);
        assertEquals(domainUser, mapped);
    }

    @Test
    void whenConvertDomainToEntity_shouldReturnUserEntityObject() {
        var repositoryUser = easyRandom.nextObject(User.class);
        var domainUser = easyRandom.nextObject(com.desarrollogj.exampleapi.domain.user.User.class);

        when(modelMapper.map(domainUser, User.class)).thenReturn(repositoryUser);

        var mapped = mapper.convertToEntityFromDomain(domainUser);

        assertNotNull(mapped);
        assertEquals(repositoryUser, mapped);
    }
}
