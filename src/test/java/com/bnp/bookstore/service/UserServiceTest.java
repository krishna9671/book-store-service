package com.bnp.bookstore.service;

import com.bnp.bookstore.model.User;
import com.bnp.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mindrot.jbcrypt.BCrypt;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUser() {
        // Arrange
        String username = "testUser";
        String password = "testPass";

        // Act
        userService.registerUser(username, password);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLoginUser_Success() {
        // Arrange
        String username = "testUser";
        String password = "testPass";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        when(userRepository.findByusername(username)).thenReturn(Optional.of(user));

        // Act
        boolean result = userService.loginUser(username, password);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testLoginUser_Failure() {
        // Arrange
        String username = "testUser";
        String password = "testPass";
        String hashedPassword = BCrypt.hashpw("wrongPass", BCrypt.gensalt());
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        when(userRepository.findByusername(username)).thenReturn(Optional.of(user));

        // Act
        boolean result = userService.loginUser(username, password);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testLoginUser_UserNotFound() {
        // Arrange
        String username = "testUser";
        String password = "testPass";

        when(userRepository.findByusername(username)).thenReturn(Optional.empty());

        // Act
        boolean result = userService.loginUser(username, password);

        // Assert
        assertFalse(result);
    }
}
