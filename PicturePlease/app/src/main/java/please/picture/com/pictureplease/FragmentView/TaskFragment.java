package please.picture.com.pictureplease.FragmentView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import please.picture.com.pictureplease.FragmentAdapter.FragmentAdapter;
import please.picture.com.pictureplease.R;

/**
 * Created by University on 24.03.2017.
 */

public class TaskFragment extends Fragment {
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.task_fragment, null);

        pager = (ViewPager) root.findViewById(R.id.pager);
        pagerAdapter = new FragmentAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setBackgroundResource(R.drawable.gradient);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setSelectedTabIndicatorHeight(7);
        tabLayout.setupWithViewPager(pager);
        return root;
    }

}
