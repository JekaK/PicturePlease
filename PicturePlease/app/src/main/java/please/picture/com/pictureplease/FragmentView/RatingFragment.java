package please.picture.com.pictureplease.FragmentView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import please.picture.com.pictureplease.ActivityView.MainActivity;
import please.picture.com.pictureplease.Adapter.RatingAdapter;
import please.picture.com.pictureplease.Cache.RatingCache;
import please.picture.com.pictureplease.Constants.Constants;
import please.picture.com.pictureplease.Entity.Rating;
import please.picture.com.pictureplease.Entity.Task;
import please.picture.com.pictureplease.NetworkRequests.RatingRequest;
import please.picture.com.pictureplease.NetworkRequests.TaskListRequest;
import please.picture.com.pictureplease.R;
import please.picture.com.pictureplease.Session.SessionManager;

/**
 * Created by University on 25.03.2017.
 */

public class RatingFragment extends Fragment {
    private ListView listView;
    private RatingAdapter adapter;
    private RatingCache cache;
    private RatingRequest ratingRequest;
    private List<Rating> ratings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        cache = new RatingCache(getActivity(), getResources().getString(R.string.RATING));
        ratingRequest = new RatingRequest(getActivity(), Constants.BASE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.rating_page, null);
        ((MainActivity) getActivity()).setTitle("Rating");
        listView = (ListView) root.findViewById(R.id.rating);
        if (cache.getRatings() == null) {
            loadRatingList();
        } else {
            adapter = new RatingAdapter(getActivity(), cache.getRatings());
            listView.setAdapter(adapter);
        }
        return root;
    }

    private void loadRatingList() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ratingRequest.getRating(new RatingRequest.ratingCallback() {
            @Override
            public void onRatingResponse(List<Rating> ratingStructs) {
                ratings = ratingStructs;
                cache.deleteTasks();
                cache.saveTasks(ratings);
                adapter = new RatingAdapter(getActivity(), cache.getRatings());
                listView.setAdapter(adapter);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh: {
                loadRatingList();
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }
}
