package please.picture.com.pictureplease.NetworkRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import please.picture.com.pictureplease.Asynk.LogInRetrofitAsynk;
import please.picture.com.pictureplease.Entity.User;
import please.picture.com.pictureplease.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jeka on 29.04.17.
 */

public class CheckRequest {
    private Context context;
    private String baseUrl;

    public CheckRequest(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;
    }

    public interface callback {
        public void doFunction(Integer integer, ProgressDialog dialog);
    }

    public void sendCheckRequest(String email, String pass, final callback callback) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        LogInRetrofitAsynk client = retrofit.create(LogInRetrofitAsynk.class);
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
                callback.doFunction(response.body().getIdUser(), progressDialog);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}
