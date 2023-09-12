package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.business.user.impl.GetByReferenceUseCaseImpl;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetByReferenceUseCaseTest {
    private final EasyRandom easyRandom = new EasyRandom();
    @Mock
    private UserRepository repository;
    @InjectMocks
    private GetByReferenceUseCaseImpl useCase;

    @Test
    void whenExecute_AndActiveUserExists_ThenReturnAnUser() {
        var userReference = easyRandom.toString();
        var user = easyRandom.nextObject(User.class);

        when(repository.findActiveByReference(userReference)).thenReturn(Optional.of(user));

        var result = useCase.execute(userReference);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(repository, times(1)).findActiveByReference(userReference);
    }

    @Test
    void whenExecute_AndActiveUserDoesNotExist_ThenReturnEmptyOptional() {
        var userReference = easyRandom.toString();

        when(repository.findActiveByReference(userReference)).thenReturn(Optional.empty());

        var result = useCase.execute(userReference);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findActiveByReference(userReference);
    }
}
