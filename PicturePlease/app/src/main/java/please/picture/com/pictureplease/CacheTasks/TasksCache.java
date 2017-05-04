package please.picture.com.pictureplease.CacheTasks;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import please.picture.com.pictureplease.Entity.Task;

/**
 * Created by jeka on 02.05.17.
 */

public class TasksCache {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private Gson gson;
    private String prefName;

    public TasksCache(Context context, String prefName) {
        this.context = context;
        this.prefName = prefName;
        pref = this.context.getSharedPreferences(prefName, PRIVATE_MODE);
        editor = pref.edit();
        gson = new Gson();
    }

    public void saveTasks(List<Task> inPr, List<Task> done) {
        if (inPr == null) {
            inPr = new ArrayList<>();
        }
        if (done == null) {
            done = new ArrayList<>();
        }
        editor.putString("inProgress", gson.toJson(inPr));
        editor.putString("done", gson.toJson(done));
        editor.commit();
    }

    public List<Task> getInPrTasks() {
        Type type = new TypeToken<List<Task>>() {
        }.getType();
        String inProgress = pref.getString("inProgress", null);
        List<Task> inPr = gson.fromJson(inProgress, type);
        return inPr;
    }

    public List<Task> getDoneTasks() {
        Type type = new TypeToken<List<Task>>() {
        }.getType();
        String done = pref.getString("done", null);
        List<Task> d = gson.fromJson(done, type);
        return d;
    }

    public void deleteTasks() {
        editor.clear();
        editor.commit();
    }
}
