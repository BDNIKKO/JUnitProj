package org.example;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    // A simple map to simulate a database
    private Map<String, User> userDatabase = new HashMap<>();

    public boolean registerUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return false; // Invalid user data
        }
        if (userDatabase.containsKey(user.getUsername())) {
            return false; // User already exists
        }
        userDatabase.put(user.getUsername(), user);
        return true; // User registered successfully
    }

    public User loginUser(String username, String password) {
        User user = userDatabase.get(username);

        if (user == null) {
            return null; // User not found
        }

        if (!user.getPassword().equals(password)) {
            return null; // Wrong password
        }

        return user; // Login successful
    }

    public boolean updateUserProfile(User user, String newUsername, String newPassword, String newEmail) {
        if (user == null || newUsername == null || newUsername.isEmpty() ||
                newPassword == null || newPassword.isEmpty() ||
                newEmail == null || newEmail.isEmpty()) {
            return false; // Invalid update data
        }
        if (userDatabase.containsKey(newUsername) && !user.getUsername().equals(newUsername)) {
            return false; // New username is already taken
        }

        user.setUsername(newUsername);
        user.setPassword(newPassword);
        user.setEmail(newEmail);

        userDatabase.put(newUsername, user);
        return true; // User profile updated successfully
    }
}
