package me.ivyzhou.pretendfriends.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ivyzhou on 2017-10-11.
 */

public class User {
    @SerializedName("gender")
    String gender;

    @SerializedName("name")
    UserName userName;

    String firstName = "";
    String lastName = "";

    public static class UserName {
        @SerializedName("title")
        String title;

        @SerializedName("first")
        String firstName;

        @SerializedName("last")
        String lastName;
    }

    @SerializedName("email")
    String email;

    @SerializedName("dob")
    String dateOfBirth;

    @SerializedName("phone")
    String phone;

    @SerializedName("picture")
    UserPicture userPicture;

    public static class UserPicture {
        @SerializedName("large")
        String largeUrl;

        @SerializedName("medium")
        String mediumUrl;

        @SerializedName("thumbnail")
        String thumbnailUrl;
    }

    @SerializedName("id")
    UserId userId;
    public static class UserId {
        @SerializedName("value")
        String value;
    }

    public String getName() {
        if(TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(userName.firstName)) {
            firstName = userName.firstName.substring(0, 1).toUpperCase() + userName.firstName.substring(1).toLowerCase();
        }

        if(TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(userName.lastName)) {
            lastName = userName.lastName.substring(0, 1).toUpperCase() + userName.lastName.substring(1).toLowerCase();
        }

        return firstName + " " + lastName;
    }

    public String getId() {
        return userId.value;
    }

    public String getEmail() {
        return email;
    }

    public String getThumbnailUrl() {
        return userPicture.largeUrl;
    }
}
