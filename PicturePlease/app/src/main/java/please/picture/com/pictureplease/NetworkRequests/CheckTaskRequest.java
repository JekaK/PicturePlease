package please.picture.com.pictureplease.NetworkRequests;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import please.picture.com.pictureplease.Asynk.CheckRetrofitAsynk;
import please.picture.com.pictureplease.Asynk.LogInRetrofitAsynk;
import please.picture.com.pictureplease.Entity.User;
import please.picture.com.pictureplease.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.Part;

/**
 * Created by jeka on 22.05.17.
 */

public class CheckTaskRequest {
    private Context context;
    private String baseUrl;

    public CheckTaskRequest(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;
    }

    public interface onResultSend {
        public void onResult(String res);
    }

    public void sendCheckTaskRequest(String idUser,
                                     String idPlace,
                                     String latitude,
                                     String longitude,
                                     File photo,
                                     String date,
                                     String people,
                                     String description, final onResultSend callback) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        RequestBody userR = RequestBody.create(MediaType.parse("text/plain"), idUser);
        RequestBody placeR = RequestBody.create(MediaType.parse("text/plain"), idPlace);
        RequestBody latitudeR = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody longitudeR = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody photoR = RequestBody.create(MediaType.parse("image/*"), photo);
        RequestBody dateR = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody peopleR = RequestBody.create(MediaType.parse("text/plain"), people);
        RequestBody descriptionR = RequestBody.create(MediaType.parse("text/plain"), description);

        Retrofit retrofit = builder.build();
        CheckRetrofitAsynk client = retrofit.create(CheckRetrofitAsynk.class);
        /*Call<String> call = client.checkTask(idUser,
                idPlace,
                latitude,
                longitude,
                photo,
                date,
                people,
                description);*/
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        progressDialog.show();
       /* call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                callback.onResult(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });*/
    }

    public void sendCheckTaskRequest2(String idUser,
                                      String idPlace,
                                      String latitude,
                                      String longitude,
                                      File photo,
                                      String date,
                                      String people,
                                      String description, final onResultSend callback) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        progressDialog.show();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        CheckRetrofitAsynk service = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(CheckRetrofitAsynk.class);

        RequestBody userR = RequestBody.create(MediaType.parse("text/plain"), idUser);
        RequestBody placeR = RequestBody.create(MediaType.parse("text/plain"), idPlace);
        RequestBody latitudeR = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody longitudeR = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody photoR = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", photo.getName(), photoR);
        RequestBody dateR = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody peopleR = RequestBody.create(MediaType.parse("text/plain"), people);
        RequestBody descriptionR = RequestBody.create(MediaType.parse("text/plain"), description);
        retrofit2.Call<Response> req = service.checkTask(userR, placeR, latitudeR, longitudeR, body, dateR, peopleR, descriptionR);

        req.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                progressDialog.dismiss();
                callback.onResult(response.body().getResponse());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    public class Response {
        @SerializedName("response")
        @Expose
        private String response;

        public Response(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}
