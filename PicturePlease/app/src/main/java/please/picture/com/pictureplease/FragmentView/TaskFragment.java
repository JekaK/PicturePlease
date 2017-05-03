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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

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
    private TaskListRequest listRequest;
    private SessionManager manager;
    private HashMap<String, String> user;
    private List<Task> tasksInPr, tasksDone;
    private TaskCache taskCache;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        manager = new SessionManager(getActivity());
        user = manager.getUserDetails();
        listRequest = new TaskListRequest(getActivity());
        taskCache = new TaskCache(getActivity(), getResources().getString(R.string.ALL));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.task_fragment, null);
        pager = (ViewPager) root.findViewById(R.id.pager);
        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        pagerAdapter = new FragmentAdapter(getChildFragmentManager());
        ((MainActivity) getActivity()).setTitle("Tasks");
        if (taskCache.getInPrTasks() == null || taskCache.getDoneTasks() == null)
            loadTasks();
        else {
            pager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(pager);
        }

        tabLayout.setBackgroundResource(R.drawable.gradient);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setSelectedTabIndicatorHeight(5);
        initDivider();
        return root;
    }

    public void loadTasks() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        listRequest.loadTaskInfo(Integer.valueOf(user.get(SessionManager.KEY_ID)),
                new TaskListRequest.callback() {

                    @Override
                    public void afterLoad(List<Task> inPr, List<Task> done) {
                        tasksInPr = inPr;
                        tasksDone = done;
                        taskCache.deleteTasks();
                        taskCache.saveTasks(inPr, done);
                        pager.setAdapter(pagerAdapter);
                        tabLayout.setupWithViewPager(pager);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void afterloadException() {

                    }
                });
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh: {
                loadTasks();
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }
}
