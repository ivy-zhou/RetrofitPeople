package me.ivyzhou.pretendfriends.networking;

import me.ivyzhou.pretendfriends.models.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ivyzhou on 2017-10-11.
 */


public interface UserClient {
    @GET("?")
    Call<UserList> listUsers(@Query("results") int results);
}
