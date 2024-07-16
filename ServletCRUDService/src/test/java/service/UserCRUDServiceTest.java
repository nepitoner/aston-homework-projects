package service;

import org.crudservice.dto.UserDto;
import org.crudservice.entity.User;
import org.crudservice.repository.UserRepository;
import org.crudservice.service.UserCRUDService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserCRUDServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserCRUDService userCRUDService = new UserCRUDService(userRepository);

    @Test
    @DisplayName("Getting user by id test")
    public void getByIdTest() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setName("Ivanon");
        user.setSessionId("1");
        user.setMessages(List.of());
        when(userRepository.getById(userId)).thenReturn(user);

        UserDto userDto = userCRUDService.getById(userId);
        assertEquals(userId, userDto.getId());
        verify(userRepository, times(1)).getById(userId);
    }

    @Test
    @DisplayName("Getting all users test")
    public void getByAllTest() throws SQLException {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setName("Ivanon");
        user.setSessionId("1");
        user.setMessages(List.of());
        users.add(user);
        when(userRepository.getAll()).thenReturn(users);

        Collection<UserDto> usersDto = userCRUDService.getAll();
        assertEquals(users.size(), usersDto.size());
        verify(userRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Creating new user test")
    public void createTest() throws SQLException {
        UserDto userDto = new UserDto();
        userCRUDService.create(userDto);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Updating user test")
    public void updateTest() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        when(userRepository.getById(userId)).thenReturn(user);

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userCRUDService.update(userDto);
        verify(userRepository, times(1)).update(any(User.class));
    }

    @Test
    @DisplayName("Deleting user test")
    public void deleteTest() {
        int userId = 1;
        userCRUDService.deleteById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }
}
