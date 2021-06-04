package com.infinity.infoway.vimal.Freeze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomEditText;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class AddFreezeRequestActivity extends AppCompatActivity {

    SearchableSpinner spinDistributorName,spinSalesPerson;
    CustomEditText edtrefNo,edtApproxSales;
    AppCompatTextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_freeze_request);
        initView();
        getDistributor();
    }

    private void initView(){

        edtrefNo = findViewById(R.id.edtRefNo);
        edtApproxSales = findViewById(R.id.edtApproxSales);
        spinDistributorName = findViewById(R.id.spinDistributorName);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("Add Freeze Request");
    }



    private void getDistributor(){
        getSalesPerson();
        ArrayList<String>distributorList = new ArrayList<>();
        distributorList.add("Select Distributor");
        distributorList.add("Distributo 1");
        distributorList.add("Distributo 2");
        distributorList.add("Distributo 3");
        distributorList.add("Distributo 4");
        ArrayAdapter<String> salesOrderAdapter = new ArrayAdapter<String>
                (AddFreezeRequestActivity.this, R.layout.searchable_spinner_text_view,
                        distributorList);
        salesOrderAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
        spinDistributorName.setAdapter(salesOrderAdapter);
        spinDistributorName.setTitle("Select Distributor");
        spinDistributorName.setPositiveButton("Cancel");
    }


    private void getSalesPerson(){
        ArrayList<String>distributorList = new ArrayList<>();
        distributorList.add("Select Distributor");
        distributorList.add("SalesPerson 1");
        distributorList.add("SalesPerson 2");
        distributorList.add("SalesPerson 3");
        distributorList.add("SalesPerson 4");
        ArrayAdapter<String> salesOrderAdapter = new ArrayAdapter<String>
                (AddFreezeRequestActivity.this, R.layout.searchable_spinner_text_view,
                        distributorList);
        salesOrderAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
        spinDistributorName.setAdapter(salesOrderAdapter);
        spinDistributorName.setTitle("Select Distributor");
        spinDistributorName.setPositiveButton("Cancel");
    }
}