package please.picture.com.pictureplease.ActivityView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import please.picture.com.pictureplease.AlertDialogs.PeopleAddDialog;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Util.Parser;

/**
 * Created by jeka on 04.05.17.
 */

public class TaskActivity extends AppCompatActivity implements PeopleAddDialog.DateDialogListener {
    private Toolbar toolbar;
    private TextView title, place, date, people;
    private final String PEOPLE = "People, that be with You";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page);
        initToolbar();
        setSupportActionBar(toolbar);
        initTexView();
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

    private void initTexView() {
        title.setText(getIntent().getStringExtra("name"));
        people = (TextView) findViewById(R.id.people_text);
        date = (TextView) findViewById(R.id.date_text);
        place = (TextView) findViewById(R.id.place_text);
        Intent intent = getIntent();
        String dateS = intent.getStringExtra("date");
        String peopleS = intent.getStringExtra("people");
        place.setText(intent.getStringExtra("street"));
        if (dateS != null)
            date.setText(dateS);
        else date.setText("Your date");
        if (peopleS != null)
            people.setText(peopleS);
        else people.setText(PEOPLE);
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeopleAddDialog addDialog = new PeopleAddDialog();
                addDialog.show(getFragmentManager(), "TEST");
            }
        });
    }

    private int dpToPxConverter(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                this.getResources().getDisplayMetrics()
        );
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
        }else{
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
}
