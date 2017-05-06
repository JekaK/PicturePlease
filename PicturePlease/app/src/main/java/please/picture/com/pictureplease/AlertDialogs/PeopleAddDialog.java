package please.picture.com.pictureplease.AlertDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import please.picture.com.pictureplease.ActivityView.TaskActivity;
import please.picture.com.pictureplease.Adapter.PeopleListAdapter;
import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 06.05.17.
 */

public class PeopleAddDialog extends DialogFragment {
    private EditText text;
    private RecyclerView listView;
    private ImageView button;
    private ArrayList<String> list;
    private TaskActivity activity;

    public interface PeopleDialogListener {
        void onFinishDialog(ArrayList<String> res);

        ArrayList<String> onPeopleViewNotNull(ArrayList<String> res);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity = (TaskActivity) getActivity();
        list = activity.onPeopleViewNotNull(list);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.people_picker, null);
        text = (EditText) v.findViewById(R.id.people_add_text);
        listView = (RecyclerView) v.findViewById(R.id.people_list);

        final PeopleListAdapter adapter = new PeopleListAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(adapter);

        button = (ImageView) v.findViewById(R.id.people_add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!text.getText().toString().equals("")) {
                    list.add(text.getText().toString());
                    adapter.notifyItemInserted(list.size() - 1);
                    adapter.notifyItemRangeChanged(list.size() - 1, list.size());
                }
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Add people")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.onFinishDialog(list);
                                dismiss();
                            }
                        })
                .create();
    }
}
