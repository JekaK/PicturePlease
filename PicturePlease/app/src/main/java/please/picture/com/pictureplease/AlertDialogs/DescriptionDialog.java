package please.picture.com.pictureplease.AlertDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import please.picture.com.pictureplease.ActivityView.TaskActivity;
import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 06.05.17.
 */

public class DescriptionDialog extends DialogFragment {
    private EditText description;
    private String text;
    private TaskActivity activity;

    public interface DescriptionDialogListener {
        public void onFinishDescription(String text);

        public String onDescriptionNotNull(String text);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity = (TaskActivity) getActivity();
        text = activity.onDescriptionNotNull(text);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.description, null);
        description = (EditText) v.findViewById(R.id.desc);
        description.setText(text);
        description.setSingleLine(false);
        description.setLines(3);
        description.setMaxLines(6);
        description.setHorizontalScrollBarEnabled(false);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Add description")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.onFinishDescription(description.getText().toString());
                                dismiss();
                            }
                        })
                .create();
    }
}
