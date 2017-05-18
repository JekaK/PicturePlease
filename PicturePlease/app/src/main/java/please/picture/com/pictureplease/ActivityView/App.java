package please.picture.com.pictureplease.ActivityView;

import android.app.Application;

/**
 * Created by jeka on 18.05.17.
 */

public final class App extends Application {
    private static App sInstance;
    private byte[] mCapturedPhotoData;

    public byte[] getCapturedPhotoData() {
        return mCapturedPhotoData;
    }

    public void setCapturedPhotoData(byte[] capturedPhotoData) {
        mCapturedPhotoData = capturedPhotoData;
    }

    public static App getInstance() { return sInstance; }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}