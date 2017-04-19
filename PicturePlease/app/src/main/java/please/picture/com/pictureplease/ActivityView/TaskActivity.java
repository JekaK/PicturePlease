package please.picture.com.pictureplease.ActivityView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 22.03.17.
 */

public class TaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
    }
}
