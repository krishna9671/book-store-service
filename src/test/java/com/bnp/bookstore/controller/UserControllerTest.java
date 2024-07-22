package com.bnp.bookstore.controller;

import com.bnp.bookstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        String username = "testUser";
        String password = "testPass";

        doNothing().when(userService).registerUser(username, password);

        // Act
        ResponseEntity<?> response = userController.registerUser(username, password);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    public void testRegisterUser_Failure() {
        // Arrange
        String username = "testUser";
        String password = "testPass";

        doThrow(new RuntimeException("Registration failed")).when(userService).registerUser(username, password);

        // Act
        ResponseEntity<?> response = userController.registerUser(username, password);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Failed to register user", response.getBody());
    }

    @Test
    public void testLoginUser_Success() {
        // Arrange
        String username = "testUser";
        String password = "testPass";

        when(userService.loginUser(username, password)).thenReturn(true);

        // Act
        ResponseEntity<?> response = userController.loginUser(username, password);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    public void testLoginUser_Failure() {
        // Arrange
        String username = "testUser";
        String password = "testPass";

        when(userService.loginUser(username, password)).thenReturn(false);

        // Act
        ResponseEntity<?> response = userController.loginUser(username, password);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid username or password", response.getBody());
    }
}
