package please.picture.com.pictureplease.ActivityView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import please.picture.com.pictureplease.AlertDialogs.DescriptionDialog;
import please.picture.com.pictureplease.AlertDialogs.PeopleAddDialog;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Util.Parser;

/**
 * Created by jeka on 04.05.17.
 */

public class TaskActivity extends AppCompatActivity implements PeopleAddDialog.PeopleDialogListener, DescriptionDialog.DescriptionDialogListener {
    private Toolbar toolbar;
    private CardView submit;
    private TextView title, date, people, description;
    private String PEOPLE, DESC, dateS, peopleS, descriptionS, streetS;
    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page);
        initToolbar();
        setSupportActionBar(toolbar);
        initView();
        initListeners();
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

    private void initView() {
        PEOPLE = getResources().getString(R.string.People);
        DESC = getResources().getString(R.string.Desc);
        title.setText(getIntent().getStringExtra("name"));
        people = (TextView) findViewById(R.id.people_text);
        date = (TextView) findViewById(R.id.date_text);
        TextView place = (TextView) findViewById(R.id.place_text);
        description = (TextView) findViewById(R.id.description);
        submit = (CardView) findViewById(R.id.submit);
        back = (ImageView) findViewById(R.id.back_button);
        ImageView pictureTask = (ImageView) findViewById(R.id.place_picture_task);
        pictureTask.setScaleType(ImageView.ScaleType.FIT_XY);
        setIntentExtra();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(getIntent().getStringExtra("photo"), pictureTask);
        place.setText(streetS);
        setViewText();
    }

    private int dpToPxConverter(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                this.getResources().getDisplayMetrics()
        );
    }

    private void setIntentExtra() {
        Intent intent = getIntent();
        dateS = intent.getStringExtra("date");
        peopleS = intent.getStringExtra("people");
        descriptionS = intent.getStringExtra("description");
        streetS = intent.getStringExtra("street");
    }

    private void setViewText() {
        if (dateS != null) {
            date.setText(dateS);
            submit.setVisibility(View.GONE);
        } else date.setText("Your date");
        if (peopleS != null)
            people.setText(peopleS);
        else people.setText(PEOPLE);

        if (descriptionS != null) {
            description.setText(descriptionS);
        } else description.setText(DESC);
    }

    private void initListeners() {
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeopleAddDialog addDialog = new PeopleAddDialog();
                addDialog.show(getFragmentManager(), "ADD_PEOPLE");
            }
        });
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DescriptionDialog descriptionDialog = new DescriptionDialog();
                descriptionDialog.show(getFragmentManager(), "DESCRIPTION");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onFinishDialog(ArrayList<String> res) {
        if (res.size() != 0) {
            StringBuilder bf = new StringBuilder();
            for (String i : res) {
                bf.append(i.concat(", "));
            }
            bf.delete(bf.length() - 2, bf.length() - 1);
            people.setText(bf);
        } else {
            people.setText(PEOPLE);
        }

    }

    @Override
    public ArrayList<String> onPeopleViewNotNull(ArrayList<String> res) {
        if (!people.getText().equals(PEOPLE)) {
            res = new ArrayList<>(new Parser(people.getText().toString()).getStringRes());
        } else {
            res = new ArrayList<>();
        }
        return res;
    }

    @Override
    public void onFinishDescription(String text) {
        if (!text.equals("")) {
            description.setText(text);
        } else {
            description.setText(DESC);
        }
    }

    @Override
    public String onDescriptionNotNull(String text) {
        if (!description.getText().equals(DESC)) {
            text = description.getText().toString();
        } else {
            text = new String("");
        }
        return text;
    }
}
