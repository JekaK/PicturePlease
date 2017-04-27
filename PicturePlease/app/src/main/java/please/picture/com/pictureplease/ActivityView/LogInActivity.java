package please.picture.com.pictureplease.ActivityView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import please.picture.com.pictureplease.AsynkLogIn.AsynkRetrofit;
import please.picture.com.pictureplease.Entity.User;
import please.picture.com.pictureplease.NetworkRequests.LogInRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.SavedPreferences.SaveBitmap;
import please.picture.com.pictureplease.Util.Util;
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
    private LogInRequest request;

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
                if (new Util(getApplicationContext()).isOnline()) {
                    request = new LogInRequest(LogInActivity.this, getResources());
                    request.sendCheckRequest(email.getText().toString(), pass.getText().toString());
                } else {
                    Toast.makeText(LogInActivity.this, "Check your Internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
