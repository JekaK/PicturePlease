package please.picture.com.pictureplease.FragmentView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class InProgressFragment extends Fragment {
    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private TaskAdapter adapter;
    private GridView gridView;
    private TaskListRequest listRequest;
    private SessionManager manager;
    private HashMap<String, String> user;
    private Task[] tasks;
    private TaskCache taskCache;

    public static InProgressFragment newInstance(int page) {
        InProgressFragment pageFragment = new InProgressFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new SessionManager(getActivity());
        user = manager.getUserDetails();
        listRequest = new TaskListRequest(getActivity());
        taskCache = new TaskCache(getActivity(), getResources().getString(R.string.INPROGRESS_PREF));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.in_progress_fragment, null);
        tasks = taskCache.getTasks();
        if (tasks == null) {
            listRequest.loadTaskInfo(Integer.valueOf(user.get(SessionManager.KEY_ID)),
                    new TaskListRequest.callback() {
                        @Override
                        public void afterLoad(Task[] list) {
                            tasks = list;
                            taskCache.saveTasks(tasks);
                            adapter = new TaskAdapter(getActivity(), tasks);
                            gridView = (GridView) root.findViewById(R.id.gvMain);
                            gridView.setAdapter(adapter);
                            adjustGridView();
                        }
                    }, true);
        } else {
            adapter = new TaskAdapter(getActivity(), tasks);
            gridView = (GridView) root.findViewById(R.id.gvMain);
            gridView.setAdapter(adapter);
            adjustGridView();
        }
        return root;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    private void adjustGridView() {
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setHorizontalSpacing(8);
        gridView.setVerticalSpacing(8);
    }
}
