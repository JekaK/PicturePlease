package please.picture.com.pictureplease.ActivityView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import please.picture.com.pictureplease.Constants.Constants;
import please.picture.com.pictureplease.NetworkRequests.RegistrationRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Util.Util;

/**
 * Created by jeka on 29.04.17.
 */

public class RegistrationActivity extends AppCompatActivity {
    private EditText email, login, pass, passAgain;
    private Button registrationBtn;
    private String emailStr, loginStr, passStr, passAgainStr;
    private RegistrationRequest registrationRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        init();
        registrationRequest = new RegistrationRequest(RegistrationActivity.this, Constants.BASE_URL);
        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStrings();
                if ((passStr.equals(passAgainStr)) && Util.isValidEmail(emailStr))
                    registrationRequest.createUser(emailStr, loginStr, passStr);
                else {
                    Toast.makeText(RegistrationActivity.this, "Email not valid or passes don't matches!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        email = (EditText) findViewById(R.id.email_reg);
        login = (EditText) findViewById(R.id.login_reg);
        pass = (EditText) findViewById(R.id.pass_reg);
        passAgain = (EditText) findViewById(R.id.pass_again_reg);
        registrationBtn = (Button) findViewById(R.id.reg_btn);
    }

    private void setStrings() {
        emailStr = email.getText().toString();
        loginStr = login.getText().toString();
        passStr = pass.getText().toString();
        passAgainStr = passAgain.getText().toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegistrationActivity.this, LogInActivity.class));
        finish();
    }
}
