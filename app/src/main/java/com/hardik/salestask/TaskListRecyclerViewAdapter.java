package com.hardik.salestask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hardik.salestask.models.Task;

import java.util.List;
import java.util.Random;

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final TaskListActivityFragment.OnTaskListFragmentInteractionListener mListener;

    public TaskListRecyclerViewAdapter(List<Task> items, TaskListActivityFragment.OnTaskListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.mItem = mValues.get(position);

    }

    @Override
    public int getItemCount() {
        return 5;
        // return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView mTxtTaskTitle;
        public final TextView mTxtClientName;
        public final TextView mTxtBannerCode;
        public final TextView mTxtDaysLeft;
        public final TextView mTxtContactPersonName;
        public final ImageView mImgDaysLeft;

        public final LinearLayout lnrDays;

        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            LinearLayout lnrParent = (LinearLayout) view.findViewById(R.id.lnr_parent);

            mTxtTaskTitle = (TextView) view.findViewById(R.id.txt_task_title);
            mTxtClientName = (TextView) view.findViewById(R.id.txt_client_name);
            mTxtBannerCode = (TextView) view.findViewById(R.id.txt_client_name);
            mTxtDaysLeft = (TextView) view.findViewById(R.id.txt_days_label);
            mTxtContactPersonName = (TextView) view.findViewById(R.id.txt_contact_person);
            lnrDays = (LinearLayout) view.findViewById(R.id.lnr_days);

            mImgDaysLeft = (ImageView) view.findViewById(R.id.img_days_left);

            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable textDrawable = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(2)
                    .endConfig()
                    .buildRect("" + new Random().nextInt(10), generator.getColor(new Random().nextInt()));

            mImgDaysLeft.setImageDrawable(textDrawable);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onTaskListItemSelected(mItem);
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTxtClientName.getText() + "'";
        }
    }
}
