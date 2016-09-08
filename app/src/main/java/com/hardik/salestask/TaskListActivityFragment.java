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

import com.hardik.salestask.models.Task;

import java.util.ArrayList;

public class TaskListActivityFragment extends Fragment {

    private int mColumnCount = 1;
    private OnTaskListFragmentInteractionListener mListener;

    public TaskListActivityFragment() {
    }

    public static TaskListActivityFragment newInstance() {
        TaskListActivityFragment fragment = new TaskListActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        TaskListRecyclerViewAdapter adapter = new TaskListRecyclerViewAdapter(new ArrayList<Task>(), mListener);
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTaskListFragmentInteractionListener) {
            mListener = (OnTaskListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTaskListFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnTaskListFragmentInteractionListener) {
            mListener = (OnTaskListFragmentInteractionListener) activity;
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

    public interface OnTaskListFragmentInteractionListener {
        void onTaskListItemSelected(Task task);
    }
}
