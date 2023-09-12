package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.business.user.impl.DeleteUseCaseImpl;
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
public class DeleteUseCaseTest {
    private final EasyRandom easyRandom = new EasyRandom();
    @Mock
    private UserRepository repository;
    @InjectMocks
    private DeleteUseCaseImpl useCase;

    @Test
    void whenExecute_AndExists_ThenReturnDeletedUser() {
        var userId = easyRandom.nextLong();
        var currentUser = easyRandom.nextObject(User.class);
        var deletedUser = easyRandom.nextObject(User.class);

        when(repository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(repository.save(currentUser)).thenReturn(deletedUser);

        var result = useCase.execute(userId);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(deletedUser, result.get());

        verify(repository, times(1)).findById(userId);
        verify(repository, times(1)).save(currentUser);
    }

    @Test
    void whenExecute_AndUserDoesNotExist_ThenReturnEmptyOptional() {
        var userId = easyRandom.nextLong();

        when(repository.findById(userId)).thenReturn(Optional.empty());

        var result = useCase.execute(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findById(userId);
    }
}
