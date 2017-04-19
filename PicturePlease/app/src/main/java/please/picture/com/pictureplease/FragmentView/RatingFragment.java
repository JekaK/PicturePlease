package please.picture.com.pictureplease.FragmentView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import please.picture.com.pictureplease.R;

/**
 * Created by University on 25.03.2017.
 */

public class RatingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.rating_page, null);
        return root;
    }
}
