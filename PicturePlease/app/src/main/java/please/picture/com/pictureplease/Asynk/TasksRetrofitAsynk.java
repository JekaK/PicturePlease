package please.picture.com.pictureplease.Asynk;

import please.picture.com.pictureplease.Entity.Task;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jeka on 02.05.17.
 */

public interface TasksRetrofitAsynk {
    @FormUrlEncoded
    @POST("sendTasks")
    Call<Task[]> getUndoneTasks(@Field("id_user") Integer id_user);

    @FormUrlEncoded
    @POST("getDoneTasks")
    Call<Task[]> getDoneTasks(@Field("id_user") Integer id_user);
}
