package me.ivyzhou.pretendfriends.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton model containing all user data
 */
public class UserContent {

    private static UserContent instance = null;

    /**
     * A map of users, by ID.
     */
    private static final Map<String, User> item_map = new HashMap<String, User>();

    public void setUsers(UserList userList) {
        List<User> users = userList.getUsers();
        for(User user : users) {
            item_map.put(user.getId(), user);
        }
    }

    public User getUser(String id) {
        return item_map.get(id);
    }

    public void updateUser(String id, User user) {
        item_map.put(id, user);
    }

    private UserContent() {}

    // Singleton pattern
    public static UserContent getInstance() {
        if(instance == null) {
            instance = new UserContent();
        }
        return instance;
    }
}
