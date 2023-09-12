package com.desarrollogj.exampleapi.business.user;

import com.desarrollogj.exampleapi.business.user.impl.GetAllUseCaseImpl;
import com.desarrollogj.exampleapi.domain.user.User;
import com.desarrollogj.exampleapi.infrastructure.database.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllUseCaseTest {
    private final EasyRandom easyRandom = new EasyRandom();
    @Mock
    private UserRepository repository;
    @InjectMocks
    private GetAllUseCaseImpl useCase;

    @Test
    void whenExecute_AndActiveUsersExist_ThenReturnAListOfUsers() {
        var userList = easyRandom.objects(User.class, 5).toList();

        when(repository.findAllActive()).thenReturn(userList);

        var result = useCase.execute();

        assertNotNull(result);
        assertEquals(userList, result);

        verify(repository, times(1)).findAllActive();
    }

    @Test
    void whenExecute_AndActiveUsersDoNotExist_ThenReturnAnEmptyListOfUsers() {
        List<User> userList = Collections.emptyList();

        when(repository.findAllActive()).thenReturn(userList);

        var result = useCase.execute();

        assertNotNull(result);
        assertEquals(userList, result);

        verify(repository, times(1)).findAllActive();
    }
}
