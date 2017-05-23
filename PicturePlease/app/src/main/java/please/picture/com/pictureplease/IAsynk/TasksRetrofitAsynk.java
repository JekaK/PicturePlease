package please.picture.com.pictureplease.IAsynk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    Call<TasksStruct> getTasks(@Field("id_user") Integer id_user);

    public class TasksStruct {

        @SerializedName("inProgress")
        @Expose
        private List<Task> inProgress = null;
        @SerializedName("done")
        @Expose
        private List<Task> done = null;

        public List<Task> getInProgress() {
            return inProgress;
        }

        public void setInProgress(List<Task> inProgress) {
            this.inProgress = inProgress;
        }

        public List<Task> getDone() {
            return done;
        }

        public void setDone(List<Task> done) {
            this.done = done;
        }
    }

}
