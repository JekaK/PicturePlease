package please.picture.com.pictureplease.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;

import please.picture.com.pictureplease.Constants.Constants;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 30.04.17.
 */

public class TaskAdapter extends ArrayAdapter {
    private final Activity context;
    private Task[] tasks;
    private ImageLoader loader;
    private DisplayImageOptions options;
    private ProgressBar spinner;

    public TaskAdapter(Activity context, Task[] tasks) {
        super(context, R.layout.task_card, tasks);
        this.context = context;
        this.tasks = tasks;
        loader = ImageLoader.getInstance();
        spinner = new ProgressBar(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // width, height
                .discCacheExtraOptions(480, 800, null) // width, height, compress format, quality
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024 * 1024)) // 2 Mb
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context)) // connectTimeout (5 s), readTimeout (30 s)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading()
                .cacheInMemory()
                .cacheOnDisc()
                .build();
        loader.init(config);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rootView = inflater.inflate(R.layout.task_card, null, true);

        TextView name = (TextView) rootView.findViewById(R.id.place_name);
        TextView street = (TextView) rootView.findViewById(R.id.place_street);
        name.setText(tasks[position].getName());
        street.setText(tasks[position].getStreet());
        ImageView photo = (ImageView) rootView.findViewById(R.id.place_image);
        photo.setScaleType(ImageView.ScaleType.FIT_XY);
        loader.displayImage(Constants.BASE_URL.concat(tasks[position].getPhoto()), photo, options,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spinner.setVisibility(View.GONE);
            }
        });
        return rootView;
    }
}
