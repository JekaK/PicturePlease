package please.picture.com.pictureplease.NetworkRequests;

import java.util.List;

import please.picture.com.pictureplease.Asynk.TasksRetrofitAsynk;
import please.picture.com.pictureplease.Constants.Constants;
import please.picture.com.pictureplease.Entity.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jeka on 02.05.17.
 */

public class TaskListRequest {

    public TaskListRequest() {
    }

    public interface callback {
        public void afterLoad(List<Task> inPr, List<Task> done);

        public void afterloadException();
    }

    public void loadTaskInfo(Integer id_user, final callback callback) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        TasksRetrofitAsynk client = retrofit.create(TasksRetrofitAsynk.class);
        Call<TasksRetrofitAsynk.TasksStruct> call = client.getTasks(id_user);

        call.enqueue(new Callback<TasksRetrofitAsynk.TasksStruct>() {
            @Override
            public void onResponse(Call<TasksRetrofitAsynk.TasksStruct> call, Response<TasksRetrofitAsynk.TasksStruct> response) {

                callback.afterLoad(response.body().getInProgress(), response.body().getDone());
            }

            @Override
            public void onFailure(Call<TasksRetrofitAsynk.TasksStruct> call, Throwable t) {
                callback.afterloadException();
            }
        });
    }

}
