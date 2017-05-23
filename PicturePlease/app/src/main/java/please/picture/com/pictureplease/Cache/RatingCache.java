package please.picture.com.pictureplease.Cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import please.picture.com.pictureplease.Entity.Rating;
import please.picture.com.pictureplease.Entity.Task;

/**
 * Created by jeka on 23.05.17.
 */

public class RatingCache {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private Gson gson;

    public RatingCache(Context context, String prefName) {
        this.context = context;
        pref = this.context.getSharedPreferences(prefName, PRIVATE_MODE);
        editor = pref.edit();
        gson = new Gson();
    }

    public void saveTasks(List<Rating> ratings) {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }

        editor.putString("ratings", gson.toJson(ratings));
        editor.commit();
    }

    public List<Rating> getRatings() {
        Type type = new TypeToken<List<Rating>>() {
        }.getType();
        String ratingList = pref.getString("ratings", null);
        List<Rating> ratings = gson.fromJson(ratingList, type);
        return ratings;
    }



    public void deleteTasks() {
        editor.clear();
        editor.commit();
    }
}
