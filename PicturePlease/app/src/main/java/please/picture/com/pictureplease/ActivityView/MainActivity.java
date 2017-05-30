package please.picture.com.pictureplease.ActivityView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import please.picture.com.pictureplease.Cache.RatingCache;
import please.picture.com.pictureplease.Cache.TasksCache;
import please.picture.com.pictureplease.Constants.Constants;
import please.picture.com.pictureplease.CustomView.RoundedImageView;
import please.picture.com.pictureplease.FragmentView.RatingFragment;
import please.picture.com.pictureplease.FragmentView.TaskFragment;
import please.picture.com.pictureplease.NetworkRequests.UpdateUserPhotoRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Session.SessionManager;

import static com.nostra13.universalimageloader.utils.DiskCacheUtils.findInCache;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private RoundedImageView photoUser;
    private View navHeader;
    private TextView email, login;
    private SessionManager sessionManager;
    private TextView title;
    private ActionBarDrawerToggle mDrawerToggle;
    private SessionManager manager;
    private HashMap<String, String> user;
    private Fragment transactFragment;
    private Fragment[] fragments;
    private String userId;
    private ImageLoader loader;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        setSupportActionBar(toolbar);
        initSession();
        initImageLoader();
        setUpDrawerToogle();
        createSession();
        fragments = new Fragment[]{new TaskFragment(), new RatingFragment()};
        transactFragment = fragments[0];
        createTransaction(transactFragment);

    }


    private void createSession() {
        if (!sessionManager.isLoggedIn()) {
            sessionManager.checkLogin();
        } else {
            navHeader = nvDrawer.getHeaderView(0);
            photoUser = (RoundedImageView) navHeader.findViewById(R.id.photoUser);
            photoUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 1);
                }
            });
            user = sessionManager.getUserDetails();
            userId = user.get(SessionManager.KEY_ID);
            if (user.get(SessionManager.KEY_PHOTO) != null)
                loader.displayImage(Constants.BASE_URL.concat(user.get(SessionManager.KEY_PHOTO)),
                        photoUser, new SimpleImageLoadingListener() {
                            boolean cacheFound;

                            @Override
                            public void onLoadingStarted(String url, View view) {
                                List<String> memCache = MemoryCacheUtils.findCacheKeysForImageUri(url, ImageLoader.getInstance().getMemoryCache());
                                cacheFound = !memCache.isEmpty();
                                if (!cacheFound) {
                                    File discCache = findInCache(url, ImageLoader.getInstance().getDiscCache());
                                    if (discCache != null) {
                                        cacheFound = discCache.exists();
                                    }
                                }
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                if (cacheFound) {
                                    MemoryCacheUtils.removeFromCache(imageUri, loader.getMemoryCache());
                                    DiskCacheUtils.removeFromCache(imageUri, loader.getDiskCache());
                                    File imageFile = loader.getDiscCache().get(imageUri);
                                    if (imageFile.exists()) {
                                        imageFile.delete();
                                    }

                                    ImageLoader.getInstance().displayImage(imageUri, (ImageView) view);
                                }
                            }
                        });
            navHeader.setBackgroundResource(R.drawable.gradient);
            initTextViewHeaderContent(user.get(SessionManager.KEY_LOGIN), user.get(SessionManager.KEY_EMAIL));
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.include);
        title = new TextView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        title.setLayoutParams(layoutParams);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD_ITALIC);
        toolbar.addView(title);
    }

    private void initImageLoader() {

        loader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)
                .discCacheExtraOptions(480, 800, null)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024 * 1024))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(this))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        loader.init(config);
    }

    private void initTextViewHeaderContent(String loginString, String emailString) {
        login = (TextView) navHeader.findViewById(R.id.Login);
        email = (TextView) navHeader.findViewById(R.id.Email);
        login.setTextSize(20);
        login.setText(loginString);
        email.setText(emailString);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void setTitle(String t) {
        title.setText(t);
    }

    private void initSession() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        sessionManager = new SessionManager(MainActivity.this);
        manager = new SessionManager(this);
        user = manager.getUserDetails();
    }

    public void createTransaction(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        fragmentManager.executePendingTransactions();
    }

    public void setUpDrawerToogle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, null,
                R.string.OPEN, R.string.CLOSE) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void selectDrawerItem(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment: {
                if (!(transactFragment instanceof TaskFragment)) {
                    transactFragment = fragments[0];
                    createFragmentTransaction(transactFragment, menuItem);
                } else {
                    mDrawer.closeDrawers();
                }
                break;
            }
            case R.id.nav_second_fragment: {
                if (!(transactFragment instanceof RatingFragment)) {
                    transactFragment = fragments[1];
                    createFragmentTransaction(transactFragment, menuItem);
                } else {
                    mDrawer.closeDrawers();
                }
                break;
            }
            case R.id.exit: {
                sessionManager.logoutUser();
                TasksCache tasksCache =
                        new TasksCache(this, getResources().getString(R.string.ALL));
                RatingCache ratingCache =
                        new RatingCache(this, getResources().getString(R.string.RATING));
                ratingCache.deleteTasks();
                tasksCache.deleteTasks();
                Intent i = new Intent(this, LogInActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                break;
            }
            default: {
                if (!(transactFragment instanceof RatingFragment)) {
                    transactFragment = fragments[1];
                    createFragmentTransaction(transactFragment, menuItem);
                } else {
                    mDrawer.closeDrawers();
                }
                break;
            }
        }
    }

    private void createFragmentTransaction(Fragment fragment, MenuItem menuItem) {
        createTransaction(fragment);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                File targetFile = new File(this.getCacheDir(), "r");
                OutputStream outStream = new FileOutputStream(targetFile);

                final byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outStream.close();
                UpdateUserPhotoRequest request = new UpdateUserPhotoRequest(this);
                request.updateUserPhoto(userId, targetFile, new UpdateUserPhotoRequest.UserPhotoUpdate() {
                    @Override
                    public void onPhotoUpdate(String s) {
                        if (user.get(SessionManager.KEY_PHOTO) == null) {
                            sessionManager.updateUserPhoto("/User/" + sessionManager.getUserId() + ".jpg");
                        }
                        user = sessionManager.getUserDetails();
                        String s1 = Constants.BASE_URL.concat(user.get(SessionManager.KEY_PHOTO));
                        System.out.println(s1);
                        loader.displayImage(Constants.BASE_URL.concat(user.get(SessionManager.KEY_PHOTO)),
                                photoUser);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}