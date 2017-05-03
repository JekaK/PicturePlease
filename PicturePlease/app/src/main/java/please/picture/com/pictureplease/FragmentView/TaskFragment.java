package please.picture.com.pictureplease.FragmentView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import please.picture.com.pictureplease.ActivityView.MainActivity;
import please.picture.com.pictureplease.CacheTasks.TaskCache;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.FragmentAdapter.FragmentAdapter;
import please.picture.com.pictureplease.NetworkRequests.TaskListRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Session.SessionManager;

/**
 * Created by University on 24.03.2017.
 */

public class TaskFragment extends Fragment {
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;


    public static TaskFragment newInstance(SavedState savedState) {
        TaskFragment frag = new TaskFragment();
        frag.setInitialSavedState(savedState);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        initDivider();
        return root;
    }

    private void initDivider() {
        for (int i = 1; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, tabLayout, false);
            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
