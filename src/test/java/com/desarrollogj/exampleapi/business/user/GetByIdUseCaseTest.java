package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.business.user.impl.GetByIdUseCaseImpl;
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
public class GetByIdUseCaseTest {
    private final EasyRandom easyRandom = new EasyRandom();
    @Mock
    private UserRepository repository;
    @InjectMocks
    private GetByIdUseCaseImpl useCase;

    @Test
    void whenExecute_AndExistsActive_ThenReturnAnUser() {
        var userId = easyRandom.nextLong();
        var user = easyRandom.nextObject(User.class);

        when(repository.findActiveById(userId)).thenReturn(Optional.of(user));

        var result = useCase.execute(userId);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(repository, times(1)).findActiveById(userId);
    }

    @Test
    void whenExecute_AndActiveUserDoesNotExist_ThenReturnEmptyOptional() {
        var userId = easyRandom.nextLong();

        when(repository.findActiveById(userId)).thenReturn(Optional.empty());

        var result = useCase.execute(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findActiveById(userId);
    }
}
