package please.picture.com.pictureplease.IAsynk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import please.picture.com.pictureplease.Entity.Rating;
import please.picture.com.pictureplease.Entity.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jeka on 23.05.17.
 */

public interface RatingRetrofitAsynk {

    @POST("sendRating")
    Call<RatingStruct> sendRating();

    public class RatingStruct {
        @SerializedName("rating")
        @Expose
        private List<Rating> rating = null;

        public RatingStruct(List<Rating> rating) {
            this.rating = rating;
        }

        public List<Rating> getRating() {
            return rating;
        }

        public void setRating(List<Rating> rating) {
            this.rating = rating;
        }
    }
}
