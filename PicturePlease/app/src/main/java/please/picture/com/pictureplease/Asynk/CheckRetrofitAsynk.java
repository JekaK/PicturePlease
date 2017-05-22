package please.picture.com.pictureplease.Asynk;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import please.picture.com.pictureplease.Entity.User;
import please.picture.com.pictureplease.NetworkRequests.CheckTaskRequest;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by jeka on 22.05.17.
 */

public interface CheckRetrofitAsynk {

    @Multipart
    @POST("checkTask")
    Call<CheckTaskRequest.Response> checkTask(@Part("id_user") RequestBody idUser,
                                              @Part("id_place") RequestBody idPlace,
                                              @Part("latitude") RequestBody latitude,
                                              @Part("longitude") RequestBody longitude,
                                              @Part MultipartBody.Part photo,
                                              @Part("date") RequestBody date,
                                              @Part("people") RequestBody people,
                                              @Part("description") RequestBody description);
}
