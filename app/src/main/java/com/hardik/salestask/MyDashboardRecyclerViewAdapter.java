package com.hardik.salestask;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardik.salestask.models.Department;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.hardik.salestask.models.Department} and makes a call to the
 * specified {@link com.hardik.salestask.DashFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDashboardRecyclerViewAdapter extends RecyclerView.Adapter<MyDashboardRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Department> mValues;
    private final DashFragment.OnListFragmentInteractionListener mListener;

    public MyDashboardRecyclerViewAdapter(List<Department> items, DashFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_dashboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mValues.isEmpty()) {
            return;
        }

        holder.mItem = mValues.get(position);

        holder.mCount.setText(holder.mItem.getTaskList().size());
        holder.mDepartmentName.setText(holder.mItem.getName());
        holder.mPersonName.setText(holder.mItem.getPerson().getName());
    }

    @Override
    public int getItemCount() {
        return 4;
        //return mValues.size();
    }

    @Override
    public void onClick(View view) {
        Log.d("test", "Itemclicked");
    }

    static int[] colors = {
            android.R.color.holo_orange_dark,
            android.R.color.holo_red_dark,
            android.R.color.holo_blue_dark,
            android.R.color.holo_green_dark
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final CardView mCardView;
        public final TextView mCount;
        public final TextView mDepartmentName;
        public final TextView mPersonName;
        public final ImageView imgAdd;

        public Department mItem;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        Log.d("test", "Itemcliked");
                        //mListener.onListItemClicked(mValues.get(getLayoutPosition()));
                        mListener.onListItemClicked(null);
                    }
                }
            });

            mCardView = (CardView) view.findViewById(R.id.card_view);

//            mCardView.setCardBackgroundColor(view.getResources().getColor(colors[3]));

            mCount = (TextView) view.findViewById(R.id.txt_remaining_task);
            mDepartmentName = (TextView) view.findViewById(R.id.txt_department_name);
            mPersonName = (TextView) view.findViewById(R.id.txt_person);

            imgAdd = (ImageView) view.findViewById(R.id.img_add);
            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        Log.d("test", "Itemcliked");
                        //mListener.onListItemClicked(mValues.get(getLayoutPosition()));
                        mListener.onAddTaskClicked(null);
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDepartmentName.getText() + "'";
        }
    }
}
