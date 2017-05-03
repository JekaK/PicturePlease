package please.picture.com.pictureplease.NetworkRequests;

import android.app.ProgressDialog;
import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import please.picture.com.pictureplease.Asynk.TasksRetrofitAsynk;
import please.picture.com.pictureplease.Callback.callback;
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
    private Context context;

    public TaskListRequest(Context context) {
        this.context = context;
    }

    public interface callback {
        public void afterLoad(Task[] list);
        public void afterloadException();
    }

    public void loadTaskInfo(Integer id_user, final callback callback, boolean isUndone) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        TasksRetrofitAsynk client = retrofit.create(TasksRetrofitAsynk.class);
        Call<Task[]> call;
        if (isUndone)
            call = client.getUndoneTasks(id_user);
        else
            call = client.getDoneTasks(id_user);


        call.enqueue(new Callback<Task[]>() {
            @Override
            public void onResponse(Call<Task[]> call, Response<Task[]> response) {
                callback.afterLoad(response.body());
            }

            @Override
            public void onFailure(Call<Task[]> call, Throwable t) {
                callback.afterloadException();
            }
        });
    }

}
