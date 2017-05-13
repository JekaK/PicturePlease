package please.picture.com.pictureplease.Session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

import please.picture.com.pictureplease.ActivityView.LogInActivity;
import please.picture.com.pictureplease.SavedPreferences.BitmapOperations;

/**
 * Created by jeka on 27.04.17.
 */

public class SessionManager {
    private SharedPreferences pref;
    private Editor editor;
    private Context context;
    private BitmapOperations bitmapOperations;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "UserInfo";

    private static final String IS_LOGIN = "IsLoggedIn";

//{"id_user":9,"login_name":"Loud","email":"krukun1998@gmail.com","pass":"1","Photo":"/User/9.jpg"}

    public static final String KEY_ID = "id";

    public static final String KEY_LOGIN = "login";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_PASS = "pass";

    public static final String KEY_PHOTO = "photo";


    public SessionManager(Context context) {
        this.context = context;
        this.bitmapOperations = new BitmapOperations(context);
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(Integer id, String login, String email, String pass, String photo) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_LOGIN, login);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASS, pass);
        editor.putString(KEY_PHOTO, photo);
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, String.valueOf(pref.getInt(KEY_ID, 0)));

        user.put(KEY_LOGIN, pref.getString(KEY_LOGIN, null));

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_PASS, pref.getString(KEY_PASS, null));

        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null));

        return user;
    }


    public void logoutUser() {
        editor.clear();
        editor.commit();
        bitmapOperations.deleteThumbnail("desiredFilename.jpg");
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context, LogInActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((AppCompatActivity) context).finish();
        }
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
