package please.picture.com.pictureplease.FragmentView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 30.04.17.
 */

public class InProgressFragment extends Fragment {
    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

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
        View view = inflater.inflate(R.layout.in_progress_fragment, null);


        return view;
    }
}
