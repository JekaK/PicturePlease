package please.picture.com.pictureplease.IAsynk;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jeka on 29.04.17.
 */

public interface RegistrationRetrofitAsynk {
    @FormUrlEncoded
    @POST("createUser")
    Call<JsonObject> createUser(@Field("login") String login, @Field("email") String email,
                                @Field("pass") String pass);

}
