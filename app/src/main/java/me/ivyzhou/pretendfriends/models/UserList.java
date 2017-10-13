package me.ivyzhou.pretendfriends.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ivyzhou on 2017-10-11.
 */

public class UserList {
    @SerializedName("results")
    List<User> userList;

    public List<User> getUsers() {
        return userList;
    }


}
