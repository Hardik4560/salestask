package com.hardik.salestask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hardik.salestask.core.BaseActivity;
import com.hardik.salestask.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViewTaskActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        ImageView mImgDaysLeft = (ImageView) findViewById(R.id.img_days_left);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable textDrawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(2)
                .endConfig()
                .buildRect("" + new Random().nextInt(10), generator.getColor(new Random().nextInt()));

        mImgDaysLeft.setImageDrawable(textDrawable);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");

        CommentsAdapter adapter = new CommentsAdapter(list);
        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(list.size() - 1);

        ImageView imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

        final List<String> mValues;

        public CommentsAdapter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 2 == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == 0) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_comments_incoming, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_comments_outgoing, parent, false);
            }
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentsAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public Task mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                LinearLayout lnrParent = (LinearLayout) view.findViewById(R.id.lnr_parent);

            }
        }
    }
}
