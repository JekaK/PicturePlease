package please.picture.com.pictureplease.FragmentView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import please.picture.com.pictureplease.ActivityView.MainActivity;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.task_fragment, null);
        pager = (ViewPager) root.findViewById(R.id.pager);
        pagerAdapter = new FragmentAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        ((MainActivity) getActivity()).setTitle("Tasks");

        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setBackgroundResource(R.drawable.gradient);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setSelectedTabIndicatorHeight(5);

        tabLayout.setupWithViewPager(pager);
        for (int i = 1; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, tabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
        }
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
