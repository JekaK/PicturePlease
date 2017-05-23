package please.picture.com.pictureplease.ActivityView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import please.picture.com.pictureplease.GPS.GPSTracker;
import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 17.05.17.
 */

public class CameraView extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener, Camera.PictureCallback, Camera.PreviewCallback, Camera.AutoFocusCallback {
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView preview;
    private ImageView shotBtn;
    private GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.camera_view);

        preview = (SurfaceView) findViewById(R.id.SurfaceView01);

        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        gps = new GPSTracker(this);
        if (!gps.canGetLocation()) {
            gps.showSettingsAlert();
        }
        shotBtn = (ImageView) findViewById(R.id.camera_button);
        shotBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Size previewSize = camera.getParameters().getPreviewSize();
        float aspect = (float) previewSize.width / previewSize.height;

        int previewSurfaceWidth = preview.getWidth();
        int previewSurfaceHeight = preview.getHeight();

        LayoutParams lp = preview.getLayoutParams();

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(90);
            lp.height = previewSurfaceHeight;
            lp.width = (int) (previewSurfaceHeight / aspect);
            ;
        } else {
            camera.setDisplayOrientation(0);
            lp.width = previewSurfaceWidth;
            lp.height = (int) (previewSurfaceWidth / aspect);
        }

        preview.setLayoutParams(lp);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void onClick(View v) {
        if (v == shotBtn) {
            camera.autoFocus(this);
        }
    }

    @Override
    public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera) {
        try {
            File saveDir = new File("/sdcard/DCIM/Camera/");

            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            String res = getResources().getString(R.string.CAMERA_IMAGE) + String.valueOf(System.currentTimeMillis() + ".jpg");
            Intent intent = new Intent();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());
            intent.putExtra("path",
                    res);
            intent.putExtra("date", currentDateandTime);

            double latitude, longitude;
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            App.getInstance().setCapturedPhotoData(paramArrayOfByte);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAutoFocus(boolean paramBoolean, Camera paramCamera) {
        if (paramBoolean) {
            paramCamera.takePicture(null, null, null, this);
        }
    }

    @Override
    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera) {
    }
}
