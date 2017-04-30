package please.picture.com.pictureplease.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 30.04.17.
 */

public class TaskAdapter extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<String> names,streets;

    public TaskAdapter(Activity context, ArrayList<String> names, ArrayList<String> streets) {
        super(context, R.layout.task_card, names);
        this.context = context;
        this.names = names;
        this.streets = streets;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.task_card, null, true);
        TextView name = (TextView) rootView.findViewById(R.id.place_name);
        TextView street = (TextView) rootView.findViewById(R.id.place_street);
        name.setText(names.get(position));
        street.setText(streets.get(position));

        return rootView;
    }
}
