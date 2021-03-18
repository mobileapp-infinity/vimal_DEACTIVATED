package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.infinity.infoway.vimal.R;

public class HiBondListingActivity extends AppCompatActivity
{

    ListView lv;
    HiBondListingAdapter hiBondListingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_bond_listing);

        lv = (ListView) findViewById(R.id.lv);

        hiBondListingAdapter = new HiBondListingAdapter(HiBondListingActivity.this);
        lv.setAdapter(hiBondListingAdapter);


    }
}
