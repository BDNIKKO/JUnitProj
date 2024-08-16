package org.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private User mockUser;

    @BeforeClass
    public static void setUpBeforeClass() {
        // Executed once, before the start of all tests
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // Executed once, after all tests are finished
    }

    @Before
    public void setUp() {
        // Executed before each test
        userService = new UserService();
        mockUser = Mockito.mock(User.class);
    }

    @After
    public void tearDown() {
        // Executed after each test
    }

    @Test
    public void testRegisterUser_Positive() {
        when(mockUser.getUsername()).thenReturn("uniqueUser");
        when(mockUser.getPassword()).thenReturn("password123");
        when(mockUser.getEmail()).thenReturn("email@example.com");

        assertTrue(userService.registerUser(mockUser));
    }

    @Test
    public void testRegisterUser_Negative() {
        when(mockUser.getUsername()).thenReturn("existingUser");

        userService.registerUser(mockUser);  // First registration should succeed
        assertFalse(userService.registerUser(mockUser));  // Second registration with the same username should fail
    }

    @Test
    public void testRegisterUser_EdgeCase() {
        when(mockUser.getUsername()).thenReturn(""); // Empty username
        assertFalse(userService.registerUser(mockUser));

        when(mockUser.getUsername()).thenReturn(null); // Null username
        assertFalse(userService.registerUser(mockUser));
    }

    @Test
    public void testLoginUser_Positive() {
        when(mockUser.getUsername()).thenReturn("validUser");
        when(mockUser.getPassword()).thenReturn("password123");

        userService.registerUser(mockUser);
        assertNotNull(userService.loginUser("validUser", "password123"));
    }

    @Test
    public void testLoginUser_Negative() {
        when(mockUser.getUsername()).thenReturn("validUser");
        when(mockUser.getPassword()).thenReturn("password123");

        userService.registerUser(mockUser);
        assertNull(userService.loginUser("validUser", "wrongPassword"));  // Wrong password
        assertNull(userService.loginUser("invalidUser", "password123"));  // Non-existing user
    }

    @Test
    public void testLoginUser_EdgeCase() {
        when(mockUser.getUsername()).thenReturn("validUser");
        when(mockUser.getPassword()).thenReturn("password123");

        userService.registerUser(mockUser);
        assertNull(userService.loginUser("", "password123"));  // Empty username
        assertNull(userService.loginUser("validUser", ""));    // Empty password
        assertNull(userService.loginUser(null, null));          // Null values
    }

    @Test
    public void testUpdateUserProfile_Positive() {
        when(mockUser.getUsername()).thenReturn("userToUpdate");
        when(mockUser.getPassword()).thenReturn("password123");
        when(mockUser.getEmail()).thenReturn("email@example.com");

        userService.registerUser(mockUser);

        when(mockUser.getUsername()).thenReturn("newUsername");
        when(mockUser.getPassword()).thenReturn("newPassword");
        when(mockUser.getEmail()).thenReturn("newEmail@example.com");

        assertTrue(userService.updateUserProfile(mockUser, "newUsername", "newPassword", "newEmail@example.com"));
    }

    @Test
    public void testUpdateUserProfile_Negative() {
        when(mockUser.getUsername()).thenReturn("userToUpdate");

        userService.registerUser(mockUser);

        User anotherUser = Mockito.mock(User.class);
        when(anotherUser.getUsername()).thenReturn("existingUser");

        userService.registerUser(anotherUser);

        assertFalse(userService.updateUserProfile(mockUser, "existingUser", "newPassword", "newEmail@example.com")); // Username already taken
    }

    @Test
    public void testUpdateUserProfile_EdgeCase() {
        when(mockUser.getUsername()).thenReturn("userToUpdate");

        userService.registerUser(mockUser);
        assertFalse(userService.updateUserProfile(mockUser, "", "", "")); // Empty new username, password, and email
        assertFalse(userService.updateUserProfile(mockUser, null, null, null)); // Null new username, password, and email
    }
}
