package please.picture.com.pictureplease.FragmentView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 30.04.17.
 */

public class DoneFragment extends Fragment {

    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    public static DoneFragment newInstance(int page) {
        DoneFragment pageFragment = new DoneFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.done_fragment,null);

        return root;
    }
}
