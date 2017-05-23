package please.picture.com.pictureplease.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.ByteArrayOutputStream;
import java.util.List;

import please.picture.com.pictureplease.ActivityView.TaskActivity;
import please.picture.com.pictureplease.Constants.Constants;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 30.04.17.
 */

public class TasksListAdapter extends ArrayAdapter {
    private final Activity context;
    private List<Task> tasks;
    private ImageLoader loader;
    private DisplayImageOptions options;

    public TasksListAdapter(Activity context, List<Task> tasks) {
        super(context, R.layout.task_card, tasks);
        this.context = context;
        this.tasks = tasks;
        loader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                .discCacheExtraOptions(480, 800, null)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024 * 1024))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        loader.init(config);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.task_card, null, true);

        TextView name = (TextView) rootView.findViewById(R.id.place_name);
        final TextView street = (TextView) rootView.findViewById(R.id.place_street);

        name.setText(tasks.get(position).getName());
        street.setText(tasks.get(position).getStreet());
        final ImageView photo = (ImageView) rootView.findViewById(R.id.place_image);
        photo.setScaleType(ImageView.ScaleType.FIT_XY);
        loader.displayImage(Constants.BASE_URL.concat(tasks.get(position).getPhoto()), photo, options);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskActivity.class);
                intent.putExtra("id_place", tasks.get(position).getIdPlace());
                intent.putExtra("name", tasks.get(position).getName());
                intent.putExtra("street", tasks.get(position).getStreet());
                intent.putExtra("date", tasks.get(position).getDate());
                intent.putExtra("people", tasks.get(position).getPeople());
                intent.putExtra("description", tasks.get(position).getDescription());
                intent.putExtra("photo", Constants.BASE_URL.concat(tasks.get(position).getPhoto()));
                context.startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void clear() {
        super.clear();
        tasks = null;
    }
}
