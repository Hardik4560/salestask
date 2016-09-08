package com.hardik.salestask;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hardik.salestask.models.Department;

import java.util.List;
import java.util.Random;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.hardik.salestask.models.Department} and makes a call to the
 * specified {@link com.hardik.salestask.DashFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDashboardRecyclerViewAdapter extends RecyclerView.Adapter<MyDashboardRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final Context mContext;
    private final List<Department> mValues;
    private final DashFragment.OnListFragmentInteractionListener mListener;

    public MyDashboardRecyclerViewAdapter(Context context, List<Department> items, DashFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.mContext = context;
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

        /*holder.mCount.setText(holder.mItem.getTaskList().size());
        holder.mDepartmentName.setText(holder.mItem.getName());
        holder.mPersonName.setText(holder.mItem.getPerson().getName());*/

        holder.mCount.setText("" + (position + 10));
        holder.mDepartmentName.setText(holder.mItem.getName());
        holder.mPersonName.setText(holder.mItem.getContactPerson());
        holder.lnrHeader.setBackgroundColor(mContext.getResources().getColor(holder.mItem.getColor()));

        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(" ", mContext.getResources().getColor(android.R.color.white));

        TextDrawable textDrawableOut = TextDrawable.builder()
                .buildRound(" ", mContext.getResources().getColor(holder.mItem.getColor()));

        holder.imgAddDummy.setImageDrawable(textDrawable);
        holder.imgAddDummyOut.setImageDrawable(textDrawableOut);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
        public final LinearLayout lnrHeader;
        public final TextView mCount;
        public final TextView mDepartmentName;
        public final TextView mPersonName;
        public final ImageView imgAdd, imgAddDummy, imgAddDummyOut;

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
            lnrHeader = (LinearLayout) view.findViewById(R.id.lnr_header);
//            mCardView.setCardBackgroundColor(view.getResources().getColor(colors[3]));

            mCount = (TextView) view.findViewById(R.id.txt_remaining_task);
            mDepartmentName = (TextView) view.findViewById(R.id.txt_department_name);
            mPersonName = (TextView) view.findViewById(R.id.txt_contact_person);

            imgAddDummy = (ImageView) view.findViewById(R.id.img_add_dummy);
            imgAddDummyOut = (ImageView) view.findViewById(R.id.img_add_dummy_out);

            imgAdd = (ImageView) view.findViewById(R.id.img_add);

            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        Log.d("test", "Itemclicked");
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
