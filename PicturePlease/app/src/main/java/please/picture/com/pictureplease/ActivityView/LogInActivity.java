package please.picture.com.pictureplease.ActivityView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import please.picture.com.pictureplease.AsynkLogIn.AsynkRetrofit;
import please.picture.com.pictureplease.Entity.User;
import please.picture.com.pictureplease.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jeka on 19.04.17.
 */

public class LogInActivity extends AppCompatActivity {
    private Button login;
    private EditText email, pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("from button");
                User user = new User(email.getText().toString(), pass.getText().toString());
                sendNetworkRequest(user);
                System.out.println("from button2");
            }
        });
    }

    public void sendNetworkRequest(User user) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://pictureplease.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        System.out.println("From send");
        AsynkRetrofit client = retrofit.create(AsynkRetrofit.class);
        Call<User> call = client.checkUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("From response: "+ response.body().getId());
                Toast.makeText(LogInActivity.this.getApplicationContext(), "Yeah id: " + response.body().getId(), Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LogInActivity.this, "Failure", Toast.LENGTH_LONG);
            }
        });

    }
}
