package please.picture.com.pictureplease.FragmentView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import please.picture.com.pictureplease.Adapter.TaskAdapter;
import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 30.04.17.
 */

public class InProgressFragment extends Fragment {
    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private ArrayList<String> names, streets;
    private TaskAdapter adapter;
    private GridView gridView;

    public static InProgressFragment newInstance(int page) {
        InProgressFragment pageFragment = new InProgressFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.in_progress_fragment, null);
        names = new ArrayList<>();
        streets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            names.add("asd");
            streets.add("asdasad");
        }
        adapter = new TaskAdapter(getActivity(), names, streets);
        gridView = (GridView) root.findViewById(R.id.gvMain);
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
