package please.picture.com.pictureplease.AsynkLogIn;

import org.json.JSONObject;

import please.picture.com.pictureplease.Entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jeka on 19.04.17.
 */

public interface AsynkRetrofit {
    @POST("checkUser")
    Call<User> checkUser(@Body User user);
}
