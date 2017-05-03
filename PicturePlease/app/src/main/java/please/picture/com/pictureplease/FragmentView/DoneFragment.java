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
import java.util.List;

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
    private TaskCache taskCache;
    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        taskCache = new TaskCache(getActivity(), getResources().getString(R.string.ALL));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.done_fragment, null);
        gridView = (GridView) root.findViewById(R.id.doneGrid);
        adapter = new TaskAdapter(getActivity(), taskCache.getDoneTasks());
        gridView.setAdapter(adapter);
        adjustGridView();

        return root;
    }


    private void adjustGridView() {
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setHorizontalSpacing(8);
        gridView.setVerticalSpacing(8);
    }
}
