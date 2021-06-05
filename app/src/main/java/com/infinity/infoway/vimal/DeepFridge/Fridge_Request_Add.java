package com.infinity.infoway.vimal.DeepFridge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class Fridge_Request_Add extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private ImageView iv_add;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    private Button tv_pg_1;
    private Button tv_pg_2;
    private Button tv_pg_3;
    private Button tv_pg_4;
    private LinearLayout ll_btn_header;
    private EditText ed_ref_no;
    private EditText ed_date;
    private EditText ed_approx_sale;
    private EditText ed_Distributor_Firm_Name;
    private EditText ed_city;
    private SearchableSpinner sp_Sales_person;
    private SearchableSpinner sp_Retailer_Firm_Name;
    private EditText ed_Mobile_No;
    private EditText ed_address_1;
    private EditText ed_address_2;
    private EditText ed_address_3;
    private EditText ed_retailer_city;
    private EditText ed_retailer_state;
    private EditText ed_owner_Name;
    private EditText ed_owner_Mobile_No;
    private EditText ed_owner_address_1;
    private EditText ed_owner_address_2;
    private EditText ed_owner_address_3;
    private SearchableSpinner sp_owner_city;
    private SearchableSpinner sp_owner_state;
    private EditText ed_owner_pincode;
    private SearchableSpinner sp_Distributor_Fridge_Type;
    private SearchableSpinner sp_item_name;
    private SearchableSpinner sp_fridge_type;
    private EditText ed_Fridge_Company_Name;
    private EditText ed_Coupon_From_No;
    private EditText ed_Coupon_To_No;
    private EditText ed_Total_Coupon;
    private SearchableSpinner sp_payment_mode;
    private EditText ed_bank_dd_no;
    private EditText ed_bank_DD_Date;
    private LinearLayout lin_bank_dd;
    private SearchableSpinner sp_bank_cheque_account;
    private EditText ed_bank_Cheque_No;
    private EditText ed_bank_Cheque_Date;
    private LinearLayout lin_bank_cheque;
    private EditText ed_Deposite;
    private EditText ed_Service_Charge;
    private EditText ed_Other_Charge;
    private EditText ed_Total;
    private TextView ed_Retailer_Photo;
    private TextView ed_Retailer_Signature;
    private EditText ed_Remarks;
    View pg_two, pg_one, pg_three, pg_four;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.fridge_request_add);
        initViews();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_act.setText("Fridge Request");
        tv_pg_1.setOnClickListener(this);
        tv_pg_2.setOnClickListener(this);
        tv_pg_3.setOnClickListener(this);
        tv_pg_4.setOnClickListener(this);
    }

    private void initViews() {
        iv_back = findViewById(R.id.iv_back);
        pg_one = findViewById(R.id.pg_one);
        pg_three = findViewById(R.id.pg_three);
        pg_two = findViewById(R.id.pg_two);
        pg_four = findViewById(R.id.pg_four);
        txt_act = findViewById(R.id.txt_act);
        iv_info = findViewById(R.id.iv_info);
        iv_profile = findViewById(R.id.iv_profile);
        iv_add = findViewById(R.id.iv_add);
        rel = findViewById(R.id.rel);
        toolbar_act = findViewById(R.id.toolbar_act);
        toolbarContainer = findViewById(R.id.toolbarContainer);
        tv_pg_1 = findViewById(R.id.tv_pg_1);
        tv_pg_2 = findViewById(R.id.tv_pg_2);
        tv_pg_3 = findViewById(R.id.tv_pg_3);
        tv_pg_4 = findViewById(R.id.tv_pg_4);
        ll_btn_header = findViewById(R.id.ll_btn_header);
        ed_ref_no = findViewById(R.id.ed_ref_no);
        ed_date = findViewById(R.id.ed_date);
        ed_approx_sale = findViewById(R.id.ed_approx_sale);
        ed_Distributor_Firm_Name = findViewById(R.id.ed_Distributor_Firm_Name);
        ed_city = findViewById(R.id.ed_city);
        sp_Sales_person = findViewById(R.id.sp_Sales_person);
        sp_Retailer_Firm_Name = findViewById(R.id.sp_Retailer_Firm_Name);
        ed_Mobile_No = findViewById(R.id.ed_Mobile_No);
        ed_address_1 = findViewById(R.id.ed_address_1);
        ed_address_2 = findViewById(R.id.ed_address_2);
        ed_address_3 = findViewById(R.id.ed_address_3);
        ed_retailer_city = findViewById(R.id.ed_retailer_city);
        ed_retailer_state = findViewById(R.id.ed_retailer_state);
        ed_owner_Name = findViewById(R.id.ed_owner_Name);
        ed_owner_Mobile_No = findViewById(R.id.ed_owner_Mobile_No);
        ed_owner_address_1 = findViewById(R.id.ed_owner_address_1);
        ed_owner_address_2 = findViewById(R.id.ed_owner_address_2);
        ed_owner_address_3 = findViewById(R.id.ed_owner_address_3);
        sp_owner_city = findViewById(R.id.sp_owner_city);
        sp_owner_state = findViewById(R.id.sp_owner_state);
        ed_owner_pincode = findViewById(R.id.ed_owner_pincode);
        sp_Distributor_Fridge_Type = findViewById(R.id.sp_Distributor_Fridge_Type);
        sp_item_name = findViewById(R.id.sp_item_name);
        sp_fridge_type = findViewById(R.id.sp_fridge_type);
        ed_Fridge_Company_Name = findViewById(R.id.ed_Fridge_Company_Name);
        ed_Coupon_From_No = findViewById(R.id.ed_Coupon_From_No);
        ed_Coupon_To_No = findViewById(R.id.ed_Coupon_To_No);
        ed_Total_Coupon = findViewById(R.id.ed_Total_Coupon);
        sp_payment_mode = findViewById(R.id.sp_payment_mode);
        ed_bank_dd_no = findViewById(R.id.ed_bank_dd_no);
        ed_bank_DD_Date = findViewById(R.id.ed_bank_DD_Date);
        lin_bank_dd = findViewById(R.id.lin_bank_dd);
        sp_bank_cheque_account = findViewById(R.id.sp_bank_cheque_account);
        ed_bank_Cheque_No = findViewById(R.id.ed_bank_Cheque_No);
        ed_bank_Cheque_Date = findViewById(R.id.ed_bank_Cheque_Date);
        lin_bank_cheque = findViewById(R.id.lin_bank_cheque);
        ed_Deposite = findViewById(R.id.ed_Deposite);
        ed_Service_Charge = findViewById(R.id.ed_Service_Charge);
        ed_Other_Charge = findViewById(R.id.ed_Other_Charge);
        ed_Total = findViewById(R.id.ed_Total);
        ed_Retailer_Photo = findViewById(R.id.ed_Retailer_Photo);
        ed_Retailer_Signature = findViewById(R.id.ed_Retailer_Signature);
        ed_Remarks = findViewById(R.id.ed_Remarks);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pg_1:
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_3.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_4.setBackgroundColor(getResources().getColor(R.color.redlight));

                pg_two.setVisibility(View.GONE);
                pg_four.setVisibility(View.GONE);
                pg_three.setVisibility(View.GONE);
                pg_one.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pg_2:
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_3.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_4.setBackgroundColor(getResources().getColor(R.color.redlight));


                pg_one.setVisibility(View.GONE);
                pg_four.setVisibility(View.GONE);
                pg_three.setVisibility(View.GONE);
                pg_two.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pg_3:

                tv_pg_3.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_4.setBackgroundColor(getResources().getColor(R.color.redlight));


                pg_one.setVisibility(View.GONE);
                pg_four.setVisibility(View.GONE);
                pg_three.setVisibility(View.VISIBLE);
                pg_two.setVisibility(View.GONE);
                break;
            case R.id.tv_pg_4:
                tv_pg_4.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_3.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.redlight));

                pg_one.setVisibility(View.GONE);
                pg_four.setVisibility(View.VISIBLE);
                pg_three.setVisibility(View.GONE);
                pg_two.setVisibility(View.GONE);
                break;
        }
    }
}
