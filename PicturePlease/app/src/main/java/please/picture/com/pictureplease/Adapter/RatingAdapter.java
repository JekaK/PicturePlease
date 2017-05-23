package please.picture.com.pictureplease.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.List;

import please.picture.com.pictureplease.Constants.Constants;
import please.picture.com.pictureplease.Entity.Rating;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 23.05.17.
 */

public class RatingAdapter extends ArrayAdapter {
    private final Activity context;
    private List<Rating> ratings;
    private ImageLoader loader;
    private DisplayImageOptions options;

    public RatingAdapter(Activity context, List<Rating> ratings) {
        super(context, R.layout.rating_card, ratings);
        this.context = context;
        this.ratings = ratings;
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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View root = inflater.inflate(R.layout.rating_card, null, true);
        TextView login, score;
        ImageView photo;
        login = (TextView) root.findViewById(R.id.login);
        score = (TextView) root.findViewById(R.id.score);
        photo = (ImageView) root.findViewById(R.id.roundedImageView);
        login.setText(ratings.get(position).getLoginName());
        score.setText(ratings.get(position).getC());
        String s = Constants.BASE_URL.concat(ratings.get(position).getPhoto());
        loader.displayImage(s, photo, options);
        return root;
    }
}
