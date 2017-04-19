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
                sendCheckRequest(email.getText().toString(), pass.getText().toString());
            }
        });
    }

    public void sendCheckRequest(String email, String pass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://pictureplease.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        AsynkRetrofit client = retrofit.create(AsynkRetrofit.class);
        Call<User> call = client.checkUser(email, pass);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LogInActivity.this);
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
                Toast.makeText(LogInActivity.this.getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getUserInfoRequest(Integer id, final ProgressDialog dialog) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://pictureplease.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        System.out.println("From another net method");

        Retrofit retrofit = builder.build();
        AsynkRetrofit client = retrofit.create(AsynkRetrofit.class);
        Call<User> call = client.getUserInfo(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dialog.dismiss();
                Toast.makeText(LogInActivity.this.getApplicationContext(), response.body().getEmail(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(LogInActivity.this.getApplicationContext(),
                        t.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
