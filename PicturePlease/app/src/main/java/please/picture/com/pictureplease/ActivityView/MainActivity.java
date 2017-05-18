package please.picture.com.pictureplease.ActivityView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.TextView;

import java.util.HashMap;

import please.picture.com.pictureplease.CacheTasks.TasksCache;
import please.picture.com.pictureplease.CustomView.RoundedImageView;
import please.picture.com.pictureplease.FragmentView.RatingFragment;
import please.picture.com.pictureplease.FragmentView.TaskFragment;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.SavedPreferences.BitmapOperations;
import please.picture.com.pictureplease.Session.SessionManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        setSupportActionBar(toolbar);
        initSession();
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
            HashMap<String, String> user = sessionManager.getUserDetails();
            if (user.get("photo").equals("desiredFilename.jpg")) {
                Bitmap b = new BitmapOperations(getApplicationContext())
                        .getThumbnail(user.get(SessionManager.KEY_PHOTO));
                photoUser.setImageBitmap(b);
                navHeader.setBackgroundResource(R.drawable.gradient);
                //photoUser.setScaleType(ImageView.ScaleType.FIT_XY);
            }

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
        toolbar.addView(title);
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
}