package com.hardik.salestask;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardik.salestask.models.Department;

import java.util.ArrayList;

public class DashFragment extends Fragment {

    public static final String KEY_COLUMN_COUNT = "COLUMN_COUNT";
    private static final String TAG = DashFragment.class.getSimpleName();

    private int mColumnCount = 2;

    private String[] CONTACT_PERSON = {"Mehul", "Shweta", "Vijay", "Abhilash"};
    private String[] NAME = {"Support", "Sales", "Production", "Marketing"};
    private int[] COLORS = {android.R.color.holo_green_light, android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light};

    private OnListFragmentInteractionListener mListener;

    public DashFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(KEY_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_item_list, container, false);

        View home = view.findViewById(android.R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashboardActivity) getActivity()).toggleDrawer();
            }
        });

        // Set the adapter
        Context context = view.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }


        recyclerView.setAdapter(new MyDashboardRecyclerViewAdapter(getActivity(), generateDepartments(), mListener));

        return view;
    }

    private ArrayList generateDepartments() {
        ArrayList depArrayList = new ArrayList<Department>();

        for (int i = 0; i < 4; i++) {
            Department department = new Department(CONTACT_PERSON[i], COLORS[i], NAME[i]);
            depArrayList.add(department);
        }

        return depArrayList;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTaskListFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnTaskListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListItemClicked(Department department);

        void onAddTaskClicked(Department department);
    }

    public static String getMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
}
