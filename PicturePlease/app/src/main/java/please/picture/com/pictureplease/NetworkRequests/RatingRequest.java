package please.picture.com.pictureplease.NetworkRequests;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import please.picture.com.pictureplease.Entity.Rating;
import please.picture.com.pictureplease.Entity.User;
import please.picture.com.pictureplease.IAsynk.LogInRetrofitAsynk;
import please.picture.com.pictureplease.IAsynk.RatingRetrofitAsynk;
import please.picture.com.pictureplease.Session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jeka on 23.05.17.
 */

public class RatingRequest {

    private Context context;
    private String baseUrl;
    private SessionManager manager;

    public RatingRequest(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface ratingCallback {
        public void onRatingResponse(List<Rating> ratingStructs);
    }

    public void getRating(final ratingCallback callback) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://pictureplease.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RatingRetrofitAsynk client = retrofit.create(RatingRetrofitAsynk.class);
        Call<RatingRetrofitAsynk.RatingStruct> call = client.sendRating();
        call.enqueue(new Callback<RatingRetrofitAsynk.RatingStruct>() {
            @Override
            public void onResponse(Call<RatingRetrofitAsynk.RatingStruct> call, Response<RatingRetrofitAsynk.RatingStruct> response) {
                callback.onRatingResponse(response.body().getRating());
            }

            @Override
            public void onFailure(Call<RatingRetrofitAsynk.RatingStruct> call, Throwable t) {
                Toast.makeText(context, "Oops. Something wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

}
