package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.business.user.impl.SaveUseCaseImpl;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.domain.user.UserSaveInput;
import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveUseCaseTest {
    private final EasyRandom easyRandom = new EasyRandom();
    @Mock
    private UserRepository repository;
    @InjectMocks
    private SaveUseCaseImpl useCase;

    @Test
    void whenExecute_ThenReturnCreatedUser() {
        var user = easyRandom.nextObject(User.class);
        var saveInput = easyRandom.nextObject(UserSaveInput.class);

        when(repository.save(Mockito.any(User.class))).thenReturn(user);

        var result = useCase.execute(saveInput);

        assertNotNull(result);
        assertEquals(user, result);

        verify(repository, times(1)).save(Mockito.any(User.class));
    }
}
