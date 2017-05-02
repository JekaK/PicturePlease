package please.picture.com.pictureplease.ActivityView;

import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import please.picture.com.pictureplease.CashingTasks;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.FragmentView.RatingFragment;
import please.picture.com.pictureplease.FragmentView.TaskFragment;
import please.picture.com.pictureplease.NetworkRequests.TaskListRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.SavedPreferences.BitmapOperations;
import please.picture.com.pictureplease.Session.SessionManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ImageView photoUser;
    private View navHeader;
    private TextView email, login;
    private SessionManager sessionManager;
    private TextView title;
    private ActionBarDrawerToggle mDrawerToggle;
    private Task[] tasks;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.include);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        title = (TextView) findViewById(R.id.toolbar_title);
        setupDrawerContent(nvDrawer);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, null,
                R.string.OPEN, R.string.CLOSE) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        sessionManager = new SessionManager(MainActivity.this);
        if (!sessionManager.isLoggedIn()) {
            sessionManager.checkLogin();
        } else {
            navHeader = nvDrawer.getHeaderView(0);
            photoUser = (ImageView) navHeader.findViewById(R.id.photoUser);
            HashMap<String, String> user = sessionManager.getUserDetails();
            if (user.get("photo").equals("desiredFilename.jpg")) {
                Bitmap b = new BitmapOperations(getApplicationContext())
                        .getThumbnail(user.get(SessionManager.KEY_PHOTO));
                photoUser.setImageBitmap(b);
                //photoUser.setScaleType(ImageView.ScaleType.FIT_XY);
            }

            String loginString = user.get(SessionManager.KEY_LOGIN);
            String emailString = user.get(SessionManager.KEY_EMAIL);

            login = (TextView) navHeader.findViewById(R.id.Login);
            email = (TextView) navHeader.findViewById(R.id.Email);
            login.setTextSize(20);
            login.setText(loginString);
            email.setText(emailString);
        }
        Fragment fragment = new TaskFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
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

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        android.support.v4.app.Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragment = new TaskFragment();
                break;
            case R.id.nav_second_fragment:
                fragment = new RatingFragment();

                break;
            case R.id.exit: {
                sessionManager.logoutUser();
            }
            default:
                fragment = new TaskFragment();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CashingTasks(this).deleteTasks();
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