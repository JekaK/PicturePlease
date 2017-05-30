package please.picture.com.pictureplease.NetworkRequests;

import android.app.ProgressDialog;
import android.content.Context;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import please.picture.com.pictureplease.IAsynk.UpdateUserPhotoRetrofitAsynk;
import please.picture.com.pictureplease.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jeka on 23.05.17.
 */

public class UpdateUserPhotoRequest {
    private Context context;
    private String baseUrl;

    public UpdateUserPhotoRequest(Context context) {
        this.context = context;
    }

    public interface UserPhotoUpdate {
        public void onPhotoUpdate(String s);
    }

    public void updateUserPhoto(String id, File photo, final UserPhotoUpdate callback) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        progressDialog.show();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        UpdateUserPhotoRetrofitAsynk service = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(UpdateUserPhotoRetrofitAsynk.class);

        RequestBody userR = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody photoR = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", photo.getName(), photoR);

        retrofit2.Call<CheckTaskRequest.Response> req = service.updateUserPhoto(userR, body);

        req.enqueue(new Callback<CheckTaskRequest.Response>() {
            @Override
            public void onResponse(Call<CheckTaskRequest.Response> call, Response<CheckTaskRequest.Response> response) {
                System.out.println("From resp");
                progressDialog.dismiss();
                callback.onPhotoUpdate(response.body().getResponse());
            }

            @Override
            public void onFailure(Call<CheckTaskRequest.Response> call, Throwable t) {
                System.out.println("From resp");
                progressDialog.dismiss();
                callback.onPhotoUpdate(t.toString());
            }
        });
    }
}
