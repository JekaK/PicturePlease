package please.picture.com.pictureplease;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.DisplayContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import please.picture.com.pictureplease.ActivityView.MainActivity;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.SavedPreferences.BitmapOperations;

/**
 * Created by jeka on 02.05.17.
 */

public class CashingTasks {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Tasks";
    private Gson gson;
    private static final String IS_LOGIN = "IsLoggedIn";


    public static final String KEY_ARRAY = "tasks";

    public CashingTasks(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        gson = new Gson();
    }

    public void saveTasks(Task[] tasks) {
        editor.putString("tasks", gson.toJson(tasks));
        editor.commit();
    }

    public Task[] getTasks() {
        Type type = new TypeToken<Task[]>() {
        }.getType();
        String json = pref.getString("tasks", null);
        Task[] tasks = gson.fromJson(json, type);
        return tasks;
    }

    public void deleteTasks() {
        editor.clear();
        editor.commit();
    }
}
