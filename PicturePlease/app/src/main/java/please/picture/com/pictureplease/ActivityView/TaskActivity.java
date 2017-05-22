package please.picture.com.pictureplease.ActivityView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

import please.picture.com.pictureplease.AlertDialogs.DescriptionDialog;
import please.picture.com.pictureplease.AlertDialogs.PeopleAddDialog;
import please.picture.com.pictureplease.CacheTasks.TasksCache;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.NetworkRequests.CheckTaskRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Session.SessionManager;
import please.picture.com.pictureplease.Util.Parser;
import please.picture.com.pictureplease.Util.Util;

/**
 * Created by jeka on 04.05.17.
 */

public class TaskActivity extends AppCompatActivity implements PeopleAddDialog.PeopleDialogListener, DescriptionDialog.DescriptionDialogListener {
    private Toolbar toolbar;
    private CardView submit;
    private TextView title, date, people, description;
    private String PEOPLE, DESC, dateS, peopleS, descriptionS, streetS, path, latitude, longitude;
    private ImageView back, pictureTask;
    private ImageLoader loader;
    private byte[] jpegData;

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
        layoutParams.setMargins(Util.dpToPxConverter(72, this), Util.dpToPxConverter(15, this),
                Util.dpToPxConverter(8, this), Util.dpToPxConverter(8, this));
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

        pictureTask = (ImageView) findViewById(R.id.place_picture_task);
        pictureTask.setScaleType(ImageView.ScaleType.FIT_XY);
        pictureTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class res;
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    res = CameraView.class;
                } else {
                    res = Camera2View.class;
                }
                startActivityForResult(new Intent(TaskActivity.this, res), 1);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File f = new File(TaskActivity.this.getCacheDir(), "res");
                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(jpegData);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new CheckTaskRequest(TaskActivity.this, getResources().getString(R.string.BASE_URL))
                        .sendCheckTaskRequest2(new SessionManager(TaskActivity.this).getUserId(),
                                String.valueOf(getIntent().getIntExtra("id_place", 0)), latitude, longitude,
                                f, date.getText().toString(), people.getText().toString(), description.getText().toString(),
                                new CheckTaskRequest.onResultSend() {
                                    @Override
                                    public void onResult(String res) {
                                        if (res.equals("Ok")) {
                                            TasksCache cache = new TasksCache(TaskActivity.this, getResources().getString(R.string.ALL));
                                            cache.deleteTasks();
                                            try {
                                                if (jpegData != null || path != null)
                                                    save(jpegData, new File(path));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(TaskActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(TaskActivity.this, res, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
            }
        });
        setIntentExtra();

        loader = ImageLoader.getInstance();
        loader.displayImage(getIntent().getStringExtra("photo"), pictureTask);
        place.setText(streetS);

        setViewText();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        path = data.getStringExtra("path");
        String dataStringExtra = data.getStringExtra("date");
        Bitmap bmp = null;
        if (resultCode == RESULT_OK) {
            jpegData = App.getInstance().getCapturedPhotoData();
            bmp = Util.decodeSampledBitmapFromResourceMemOpt(jpegData, pictureTask.getWidth(),
                    pictureTask.getHeight());
            latitude = String.valueOf(data.getDoubleExtra("latitude", 0));
            longitude = String.valueOf(data.getDoubleExtra("longitude", 0));

            App.getInstance().setCapturedPhotoData(null);
        }
        loader = ImageLoader.getInstance();
        pictureTask.setImageBitmap(bmp);
        date.setText(dataStringExtra);
    }

    private void save(byte[] bytes, File file) throws IOException {
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            output.write(bytes);
        } finally {
            if (null != output) {
                output.close();
            }
        }
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
