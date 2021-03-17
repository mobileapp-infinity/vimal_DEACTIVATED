package com.infinity.infoway.vimal.delear.activity.add_schedule.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;

public class RoutePlanningListActivity extends AppCompatActivity {
    RecyclerView rvRoutePlanningList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planning_list);
        initView();
    }


    private void initView() {

        rvRoutePlanningList  = findViewById(R.id.rvRoutePlanningList);
        rvRoutePlanningList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


    }
}
