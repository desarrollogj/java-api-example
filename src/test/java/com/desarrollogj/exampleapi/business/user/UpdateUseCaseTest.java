package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.business.user.impl.UpdateUseCaseImpl;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.domain.user.UserUpdateInput;
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
public class UpdateUseCaseTest {
    private final EasyRandom easyRandom = new EasyRandom();
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UpdateUseCaseImpl useCase;

    @Test
    void whenExecute_AndUserExists_ThenReturnUpdatedItem() {
        var user = easyRandom.nextObject(User.class);
        var currentUser = easyRandom.nextObject(User.class);
        var updatedUser = easyRandom.nextObject(User.class);
        var updateInput = easyRandom.nextObject(UserUpdateInput.class);

        when(repository.findById(updateInput.getId())).thenReturn(Optional.of(currentUser));
        when(repository.save(currentUser)).thenReturn(updatedUser);

        var result = useCase.execute(updateInput);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(updatedUser, result.get());

        verify(repository, times(1)).findById(updateInput.getId());
        verify(repository, times(1)).save(currentUser);
    }

    @Test
    void whenExecute_AndUserDoesNotExist_ThenReturnEmptyOptional() {
        var updateInput = easyRandom.nextObject(UserUpdateInput.class);

        when(repository.findById(updateInput.getId())).thenReturn(Optional.empty());

        var result = useCase.execute(updateInput);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findById(updateInput.getId());
    }
}
