package please.picture.com.pictureplease.NetworkRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.JsonObject;

import please.picture.com.pictureplease.ActivityView.MainActivity;
import please.picture.com.pictureplease.Asynk.RegistrationRetrofitAsynk;
import please.picture.com.pictureplease.Session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jeka on 29.04.17.
 */

public class RegistrationRequest {
    private Context context;
    private String baseUrl;
    private SessionManager manager;

    public RegistrationRequest(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;
    }

    public void createUser(final String email, final String login, final String pass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RegistrationRetrofitAsynk client = retrofit.create(RegistrationRetrofitAsynk.class);
        Call<JsonObject> call = client.createUser(login, email, pass);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        progressDialog.show();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                Intent intent = new Intent(context, MainActivity.class);
                manager = new SessionManager(context);
                manager.createLoginSession(Integer.valueOf(response.body().get("id_user").toString()),
                        login,
                        email,
                        pass,
                        "none");
                context.startActivity(intent);
                ((AppCompatActivity) context).finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}
