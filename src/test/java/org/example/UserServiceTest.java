
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

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Executed once, before the start of all tests
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Executed once, after all tests are finished
    }

    @Before
    public void setUp() throws Exception {
        // Executed before each test
        userService = new UserService();
    }

    @After
    public void tearDown() throws Exception {
        // Executed after each test
    }

    @Test
    public void testRegisterUser_Positive() {
        User user = new User("uniqueUser", "password123", "email@example.com");
        assertTrue(userService.registerUser(user));
    }

    @Test
    public void testRegisterUser_Negative() {
        User user = new User("existingUser", "password123", "email@example.com");
        userService.registerUser(user);  // First registration should succeed
        assertFalse(userService.registerUser(user));  // Second registration with the same username should fail
    }

    @Test
    public void testRegisterUser_EdgeCase() {
        User user = new User("", "password123", "email@example.com"); // Empty username
        assertFalse(userService.registerUser(user));

        user = new User(null, "password123", "email@example.com"); // Null username
        assertFalse(userService.registerUser(user));
    }

    @Test
    public void testLoginUser_Positive() {
        User user = new User("validUser", "password123", "email@example.com");
        userService.registerUser(user);
        assertNotNull(userService.loginUser("validUser", "password123"));
    }

    @Test
    public void testLoginUser_Negative() {
        User user = new User("validUser", "password123", "email@example.com");
        userService.registerUser(user);
        assertNull(userService.loginUser("validUser", "wrongPassword"));  // Wrong password
        assertNull(userService.loginUser("invalidUser", "password123"));  // Non-existing user
    }

    @Test
    public void testLoginUser_EdgeCase() {
        User user = new User("validUser", "password123", "email@example.com");
        userService.registerUser(user);
        assertNull(userService.loginUser("", "password123"));  // Empty username
        assertNull(userService.loginUser("validUser", ""));    // Empty password
        assertNull(userService.loginUser(null, null));          // Null values
    }

    @Test
    public void testUpdateUserProfile_Positive() {
        User user = new User("userToUpdate", "password123", "email@example.com");
        userService.registerUser(user);
        assertTrue(userService.updateUserProfile(user, "newUsername", "newPassword", "newEmail@example.com"));
    }

    @Test
    public void testUpdateUserProfile_Negative() {
        User user = new User("userToUpdate", "password123", "email@example.com");
        userService.registerUser(user);
        User anotherUser = new User("existingUser", "password123", "email@example.com");
        userService.registerUser(anotherUser);

        assertFalse(userService.updateUserProfile(user, "existingUser", "newPassword", "newEmail@example.com")); // Username already taken
    }

    @Test
    public void testUpdateUserProfile_EdgeCase() {
        User user = new User("userToUpdate", "password123", "email@example.com");
        userService.registerUser(user);
        assertFalse(userService.updateUserProfile(user, "", "", "")); // Empty new username, password, and email
        assertFalse(userService.updateUserProfile(user, null, null, null)); // Null new username, password, and email
    }
}
