package please.picture.com.pictureplease.NetworkRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import please.picture.com.pictureplease.ActivityView.MainActivity;
import please.picture.com.pictureplease.AsynkLogIn.AsynkRetrofit;
import please.picture.com.pictureplease.Entity.User;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.SavedPreferences.SaveBitmap;
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
    private Resources resources;
    private SessionManager manager;

    public LogInRequest(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void sendCheckRequest(String email, String pass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://pictureplease.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        AsynkRetrofit client = retrofit.create(AsynkRetrofit.class);
        Call<User> call = client.checkUser(email, pass);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        progressDialog.show();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                getUserInfoRequest(response.body().getIdUser(), progressDialog);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getUserInfoRequest(Integer id, final ProgressDialog dialog) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://pictureplease.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        final User[] user = new User[1];
        Retrofit retrofit = builder.build();
        final AsynkRetrofit client = retrofit.create(AsynkRetrofit.class);
        Call<User> call = client.getUserInfo(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user[0] = response.body();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(resources.getString(R.string.BASE_URL).concat(response.body().getPhoto()))
                        .build();
                okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                        new SaveBitmap(context, bitmap).
                                saveImage(new SaveBitmap.callback() {
                                    @Override
                                    public void done() {
                                        dialog.dismiss();
                                        Intent intent = new Intent(context, MainActivity.class);
                                        manager = new SessionManager(context);
                                        manager.createLoginSession(user[0].getIdUser(),
                                                user[0].getLoginName(),
                                                user[0].getEmail(),
                                                user[0].getPass(),
                                                "desiredFilename.jpg");
                                        context.startActivity(intent);
                                        ((AppCompatActivity)context).finish();
                                    }
                                });
                    }
                });

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context.getApplicationContext(),
                        t.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
