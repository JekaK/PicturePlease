package please.picture.com.pictureplease.ActivityView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 04.05.17.
 */

public class TaskActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView title, place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page);
        initToolbar();
        setSupportActionBar(toolbar);
        title.setText(getIntent().getStringExtra("name"));
        place = (TextView) findViewById(R.id.place_text);
        place.setText(getIntent().getStringExtra("street"));
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = new TextView(this);
        ViewGroup.MarginLayoutParams layoutParams =
                new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dpToPxConverter(72), dpToPxConverter(15),
                dpToPxConverter(8), dpToPxConverter(8));
        title.setSingleLine();
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setTextSize(20);
        title.setTextColor(Color.WHITE);
        title.setLayoutParams(layoutParams);
        toolbar.addView(title);
    }

    private int dpToPxConverter(int dp) {

        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                this.getResources().getDisplayMetrics()
        );
    }
}
