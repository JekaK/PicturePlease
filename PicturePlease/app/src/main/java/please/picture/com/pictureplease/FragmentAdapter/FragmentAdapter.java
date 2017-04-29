package please.picture.com.pictureplease.FragmentAdapter;

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
                return InProgressFragment.newInstance(position);
            }
            case 1: {
                return DoneFragment.newInstance(position);
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
                return "In progress";
            }
            case 1: {
                return "Done";
            }
        }
        return null;
    }
}
