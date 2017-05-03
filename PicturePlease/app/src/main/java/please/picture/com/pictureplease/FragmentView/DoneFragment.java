package please.picture.com.pictureplease.FragmentView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.HashMap;

import please.picture.com.pictureplease.Adapter.TaskAdapter;
import please.picture.com.pictureplease.CacheTasks.TaskCache;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.NetworkRequests.TaskListRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Session.SessionManager;

/**
 * Created by jeka on 30.04.17.
 */

public class DoneFragment extends Fragment {

    private TaskAdapter adapter;
    private GridView gridView;
    private TaskListRequest listRequest;
    private SessionManager manager;
    private HashMap<String, String> user;
    private Task[] tasks;
    private TaskCache taskCache;
    private SwipeRefreshLayout swipeContainer;
    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        manager = new SessionManager(getActivity());
        user = manager.getUserDetails();
        listRequest = new TaskListRequest(getActivity());
        taskCache = new TaskCache(getActivity(), getResources().getString(R.string.DONE_PREF));
        tasks = new Task[0];
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* root = inflater.inflate(R.layout.done_fragment, null);
        gridView = (GridView) root.findViewById(R.id.doneGrid);
        adjustGridView();
        swipeContainer = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainerDone);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTasks();
            }
        });*/
        return root;
    }

    public void loadTasks() {
        listRequest.loadTaskInfo(Integer.valueOf(user.get(SessionManager.KEY_ID)),
                new TaskListRequest.callback() {
                    @Override
                    public void afterLoad(Task[] list) {
                        tasks = list;
                        reInitAdapter();
                        swipeContainer.setRefreshing(false);
                    }

                    @Override
                    public void afterloadException() {
                        tasks = new Task[0];
                        reInitAdapter();
                        swipeContainer.setRefreshing(false);
                    }
                }, false);
    }

    public void reInitAdapter() {
        taskCache.deleteTasks();
        taskCache.saveTasks(tasks);
        adapter = new TaskAdapter(getActivity(), tasks);
        gridView.setAdapter(adapter);
    }

    private void adjustGridView() {
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setHorizontalSpacing(8);
        gridView.setVerticalSpacing(8);
    }
}
