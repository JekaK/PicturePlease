package please.picture.com.pictureplease.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import please.picture.com.pictureplease.R;

/**
 * Created by jeka on 06.05.17.
 */

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.ViewHolder> {
    private ArrayList<String> people;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView button;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.people_name);
            button = (ImageView) itemView.findViewById(R.id.people_delete_button);
        }
    }

    public PeopleListAdapter(ArrayList<String> people) {
        this.people = people;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.people_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        holder.mTextView.setText(people.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                people.remove(pos);
                people.trimToSize();
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, people.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
