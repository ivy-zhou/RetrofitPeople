package me.ivyzhou.pretendfriends.networking;

import me.ivyzhou.pretendfriends.models.UserList;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ivyzhou on 2017-10-11.
 */

public class FriendsRetrofitClient {

    private static FriendsRetrofitClient instance = null;

    private static Retrofit retrofit;
    private static UserClient client;

    private FriendsRetrofitClient() {
        retrofit = new Retrofit.Builder()
                                .baseUrl("https://randomuser.me/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(new OkHttpClient())
                                .build();

        client = retrofit.create(UserClient.class);
    }

    // Singleton pattern
    public static FriendsRetrofitClient getInstance() {
        if(instance == null) {
            instance = new FriendsRetrofitClient();
        }
        return instance;
    }

    // Use client to async call random user.me
    public void loadUserData(int noOfResults, Callback<UserList> callback) {
        Call<UserList> call = client.listUsers(noOfResults);
        call.enqueue(callback);
    }

}
