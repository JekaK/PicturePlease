package please.picture.com.pictureplease.FragmentView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import please.picture.com.pictureplease.ActivityView.MainActivity;
import please.picture.com.pictureplease.R;

/**
 * Created by University on 25.03.2017.
 */

public class RatingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.rating_page, null);
        ((MainActivity) getActivity()).setTitle("Rating");
        TextView view = (TextView) root.findViewById(R.id.rating_text);
        view.setText("test");
        return root;
    }
}
