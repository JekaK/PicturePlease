package please.picture.com.pictureplease.ActivityView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import please.picture.com.pictureplease.Entity.User;
import please.picture.com.pictureplease.FragmentView.FirstFragment;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.FragmentView.RatingFragment;
import please.picture.com.pictureplease.SavedPreferences.SaveBitmap;
import please.picture.com.pictureplease.Session.SessionManager;

import static please.picture.com.pictureplease.R.layout.nav_header;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ImageView photoUser;
    private ActionBarDrawerToggle drawerToggle;
    private View navHeader;
    private TextView email, login;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        setupDrawerContent(nvDrawer);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new FirstFragment()).commit();

        sessionManager = new SessionManager(MainActivity.this);
        if (!sessionManager.isLoggedIn()) {
            sessionManager.checkLogin();
        } else {
            HashMap<String, String> user = sessionManager.getUserDetails();
            Bitmap b = new SaveBitmap(getApplicationContext())
                    .getThumbnail(user.get(SessionManager.KEY_PHOTO));

            navHeader = nvDrawer.getHeaderView(0);
            photoUser = (ImageView) navHeader.findViewById(R.id.photoUser);
            photoUser.setImageBitmap(b);
            photoUser.setScaleType(ImageView.ScaleType.FIT_XY);

            String loginString = user.get(SessionManager.KEY_LOGIN);
            String emailString = user.get(SessionManager.KEY_EMAIL);

            login = (TextView) navHeader.findViewById(R.id.Login);
            email = (TextView) navHeader.findViewById(R.id.Email);
            login.setTextSize(20);
            login.setText(loginString);
            email.setText(emailString);
        }
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

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragment = new FirstFragment();
                break;
            case R.id.nav_second_fragment:
                fragment = new RatingFragment();
                break;
            case R.id.exit:
                sessionManager.logoutUser();
            default:
                fragment = new FirstFragment();
        }


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


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