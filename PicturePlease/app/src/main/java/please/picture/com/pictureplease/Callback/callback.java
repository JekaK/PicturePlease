package please.picture.com.pictureplease.Callback;

import android.app.ProgressDialog;

/**
 * Created by jeka on 02.05.17.
 */

public interface callback {
    public void getUserInfo(Integer integer, ProgressDialog dialog);

    public void done();

}
