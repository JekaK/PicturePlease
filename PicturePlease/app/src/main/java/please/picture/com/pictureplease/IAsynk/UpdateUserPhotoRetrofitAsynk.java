package please.picture.com.pictureplease.IAsynk;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import please.picture.com.pictureplease.NetworkRequests.CheckTaskRequest;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by jeka on 23.05.17.
 */

public interface UpdateUserPhotoRetrofitAsynk {
    @Multipart
    @POST("updateUserPhoto")
    Call<CheckTaskRequest.Response> updateUserPhoto(@Part("id_user") RequestBody id,
                                                    @Part MultipartBody.Part photo);
}
