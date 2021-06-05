package com.infinity.infoway.vimal.DeepFridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;

import java.util.ArrayList;

//4-06-2021 pragna for listing deep fridge with folter option
public class Fridge_Listing extends AppCompatActivity {

//StringRequest request;
    RequestQueue queue;
    private Spinner sp_status;
    ArrayList<String> filter_status = new ArrayList<>();
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    private ImageView iv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_listing);
        getSupportActionBar().hide();
        queue = Volley.newRequestQueue(this);
        filter_status = new ArrayList<>();
        // filter_status.add("Please");

        filter_status.add("Pending");
        filter_status.add("Partially Approved");
        filter_status.add("Approved");
        filter_status.add("Hold");
        filter_status.add("Reject");
        filter_status.add("All");
        initViews();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                        filter_status);
        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
        sp_status.setAdapter(adapter);
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Fridge_Listing.this,Fridge_Request_Add.class);
                startActivity(i);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        sp_status = findViewById(R.id.sp_status);
        iv_back = findViewById(R.id.iv_back);
        txt_act = findViewById(R.id.txt_act);
        iv_info = findViewById(R.id.iv_info);
        iv_profile = findViewById(R.id.iv_profile);
        rel = findViewById(R.id.rel);
        toolbar_act = findViewById(R.id.toolbar_act);
        toolbarContainer = findViewById(R.id.toolbarContainer);
        iv_add = findViewById(R.id.iv_add);
    }
}