package please.picture.com.pictureplease.CacheTasks;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import please.picture.com.pictureplease.Entity.Task;

/**
 * Created by jeka on 02.05.17.
 */

public class TaskCache {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private Gson gson;
    private String prefName;

    public TaskCache(Context context, String prefName) {
        this.context = context;
        this.prefName = prefName;
        pref = this.context.getSharedPreferences(prefName, PRIVATE_MODE);
        editor = pref.edit();
        gson = new Gson();
    }


    public void saveTasks(Task[] tasks) {
        editor.putString(prefName, gson.toJson(tasks));
        editor.commit();
    }

    public Task[] getTasks() {
        Type type = new TypeToken<Task[]>() {
        }.getType();
        String json = pref.getString(prefName, null);
        Task[] tasks = gson.fromJson(json, type);
        return tasks;
    }

    public void deleteTasks() {
        editor.clear();
        editor.commit();
    }
}
