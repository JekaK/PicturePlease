package please.picture.com.pictureplease.Asynk;

import please.picture.com.pictureplease.Entity.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jeka on 19.04.17.
 */

public interface LogInRetrofitAsynk {
    @FormUrlEncoded
    @POST("checkUser")
    Call<User> checkUser(@Field("email") String email, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("getUserInfo")
    Call<User> getUserInfo(@Field("id") Integer id);
}
