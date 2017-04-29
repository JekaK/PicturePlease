package please.picture.com.pictureplease.ActivityView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import please.picture.com.pictureplease.NetworkRequests.CheckRequest;
import please.picture.com.pictureplease.NetworkRequests.LogInRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Util.Util;

/**
 * Created by jeka on 19.04.17.
 */

public class LogInActivity extends AppCompatActivity {
    private Button login;
    private EditText email, pass;
    private LogInRequest requestLogIn;
    private CheckRequest requestCheck;
    private TextView registration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        registration = (TextView) findViewById(R.id.registration);

        login = (Button) findViewById(R.id.login);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, RegistrationActivity.class));
                finish();
            }
        });
        String baseUrl = getResources().getString(R.string.BASE_URL);
        requestCheck = new CheckRequest(LogInActivity.this, baseUrl);
        requestLogIn = new LogInRequest(LogInActivity.this, baseUrl);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new Util(getApplicationContext()).isOnline()) {
                    requestCheck.sendCheckRequest(email.getText().toString(), pass.getText().toString(), new CheckRequest.callback() {
                        @Override
                        public void doFunction(Integer integer, ProgressDialog dialog) {
                            requestLogIn.getUserInfoRequest(integer, dialog);
                        }
                    });
                } else {
                    Toast.makeText(LogInActivity.this, "Check your Internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
