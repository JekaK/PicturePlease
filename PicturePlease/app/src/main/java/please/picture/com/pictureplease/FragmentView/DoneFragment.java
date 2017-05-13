package please.picture.com.pictureplease.FragmentView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import please.picture.com.pictureplease.Adapter.TasksListAdapter;
import please.picture.com.pictureplease.CacheTasks.TasksCache;
import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 30.04.17.
 */

public class DoneFragment extends Fragment {

    private TasksListAdapter adapter;
    private GridView gridView;
    private TasksCache tasksCache;
    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        tasksCache = new TasksCache(getActivity(), getResources().getString(R.string.ALL));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.done_fragment, null);
        gridView = (GridView) root.findViewById(R.id.doneGrid);
        adapter = new TasksListAdapter(getActivity(), tasksCache.getDoneTasks());
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
