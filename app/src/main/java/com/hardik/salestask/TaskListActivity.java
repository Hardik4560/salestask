package com.hardik.salestask;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hardik.salestask.core.BaseActivity;
import com.hardik.salestask.models.Task;

import java.util.Random;

public class TaskListActivity extends BaseActivity implements TaskListActivityFragment.OnTaskListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        ImageView imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TaskListActivityFragment taskListFragment = TaskListActivityFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.place_holder, taskListFragment)
                .commit();
    }

    @Override
    public void onTaskListItemSelected(Task task) {
        Intent intent = new Intent(this, ViewTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
