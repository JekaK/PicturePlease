package please.picture.com.pictureplease.NetworkRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import please.picture.com.pictureplease.ActivityView.MainActivity;
import please.picture.com.pictureplease.IAsynk.LogInRetrofitAsynk;
import please.picture.com.pictureplease.Callback.callback;
import please.picture.com.pictureplease.Entity.User;
import please.picture.com.pictureplease.SavedPreferences.BitmapOperations;
import please.picture.com.pictureplease.Session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jeka on 27.04.17.
 */

public class LogInRequest {
    private Context context;
    private String baseUrl;
    private SessionManager manager;

    public LogInRequest(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public void getUserInfoRequest(Integer id, final ProgressDialog progressDialog) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://pictureplease.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        final User[] user = new User[1];
        Retrofit retrofit = builder.build();
        LogInRetrofitAsynk client = retrofit.create(LogInRetrofitAsynk.class);
        Call<User> call = client.getUserInfo(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user[0] = response.body();
                progressDialog.dismiss();
                Intent intent = new Intent(context, MainActivity.class);
                manager = new SessionManager(context);
                manager.createLoginSession(user[0].getIdUser(),
                        user[0].getLoginName(),
                        user[0].getEmail(),
                        user[0].getPass(),
                        user[0].getPhoto());
                context.startActivity(intent);
                ((AppCompatActivity) context).finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context.getApplicationContext(),
                        t.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
