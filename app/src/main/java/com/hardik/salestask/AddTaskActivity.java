package com.hardik.salestask;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.hardik.salestask.core.BaseActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTaskActivity extends BaseActivity {

    /* @BindView(R.id.spnr_select_client)
     MaterialBetterSpinner spnrClients;
 */
    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ButterKnife.bind(this);

        ImageView imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        //  spnrClients.setAdapter(adapter);
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
