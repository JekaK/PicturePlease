package please.picture.com.pictureplease.FragmentView;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import please.picture.com.pictureplease.R;

/**
 * Created by University on 24.03.2017.
 */

public class FirstFragment extends Fragment {
    public static Fragment newInstance(Context context) {
        FirstFragment f = new FirstFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.first_fragment, null);
        return root;
    }
}
