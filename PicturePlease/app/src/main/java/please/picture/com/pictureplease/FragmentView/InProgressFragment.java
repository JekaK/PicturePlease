package please.picture.com.pictureplease.FragmentView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.HashMap;
import java.util.List;

import please.picture.com.pictureplease.Adapter.TasksListAdapter;
import please.picture.com.pictureplease.CacheTasks.TasksCache;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.NetworkRequests.TaskListRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Session.SessionManager;

/**
 * Created by jeka on 30.04.17.
 */

public class InProgressFragment extends Fragment {
    private TasksListAdapter adapter;
    private GridView gridView;
    private TaskListRequest listRequest;
    private SessionManager manager;
    private HashMap<String, String> user;
    private List<Task> tasks;
    private TasksCache tasksCache;
    private SwipeRefreshLayout swipeContainer;
    private View root;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        manager = new SessionManager(getActivity());
        user = manager.getUserDetails();
        listRequest = new TaskListRequest();
        tasksCache = new TasksCache(getActivity(), getResources().getString(R.string.ALL));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.in_progress_fragment, null);
        gridView = (GridView) root.findViewById(R.id.gvMain);
        adapter = new TasksListAdapter(getActivity(), tasksCache.getInPrTasks());
        gridView.setAdapter(adapter);
        adjustGridView();

        return root;
    }

    private void adjustGridView() {
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setHorizontalSpacing(8);
        gridView.setVerticalSpacing(8);
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
