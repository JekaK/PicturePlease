package please.picture.com.pictureplease.SavedPreferences;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by jeka on 26.04.17.
 */

public class BitmapOperations {
    private Context context;
    private Bitmap bitmap;

    public BitmapOperations(Context context) {
        this.context = context;
    }

    public BitmapOperations(Context context, Bitmap bitmap) {
        this.context = context;
        this.bitmap = bitmap;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public interface callback {
        public void done();
    }

    public void saveImage(callback callback) {
        try {
            FileOutputStream fos = context.openFileOutput("desiredFilename.jpg", Context.MODE_PRIVATE);
            this.bitmap.compress(Bitmap.CompressFormat.PNG, 0, fos);
            fos.close();
            callback.done();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isSdReadable() {

        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = true;
            Log.i("isSdReadable", "External storage card is readable.");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.i("isSdReadable", "External storage card is readable.");
            mExternalStorageAvailable = true;
        } else {

            mExternalStorageAvailable = false;
        }
        return mExternalStorageAvailable;
    }

    public Bitmap getThumbnail(String filename) {
        Bitmap bitmap = null;
        try {
            if (isSdReadable()) {
                bitmap = BitmapFactory.decodeFile(context.getFilesDir() + "/" + filename);
                System.out.println(bitmap);
            }
        } catch (Exception e) {
            Log.e("getThumbnail()", e.getMessage());
        }

        if (bitmap == null) {
            try {
                File filePath = context.getFileStreamPath(filename);
                FileInputStream fi = new FileInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(fi);
            } catch (Exception ex) {
                Log.e("getThumbnail()", ex.getMessage());
            }
        }
        return bitmap;
    }

    public void deleteThumbnail(String filename) {
        try {
            String fullPath = context.getFilesDir() + "/" + filename;
            File file = new File(fullPath);
            boolean deleted = file.delete();
            System.out.println(deleted);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
