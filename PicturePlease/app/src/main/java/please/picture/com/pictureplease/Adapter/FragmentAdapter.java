package please.picture.com.pictureplease.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import please.picture.com.pictureplease.FragmentView.DoneFragment;
import please.picture.com.pictureplease.FragmentView.InProgressFragment;

/**
 * Created by jeka on 30.04.17.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    static final int PAGE_COUNT = 2;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new InProgressFragment();
            }
            case 1: {
                return new DoneFragment();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return "IN PROGRESS";
            }
            case 1: {
                return "DONE";
            }
        }
        return null;
    }
}
