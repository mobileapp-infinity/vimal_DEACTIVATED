package com.infinity.infoway.vimal.Advertisement;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class Adv_Save extends AppCompatActivity {
    private RadioButton rb_new;
    private RadioButton rb_existing;
    private RadioGroup rg;
    private SearchableSpinner spdistributor_name;
    private SearchableSpinner spvendor_name;
    private SearchableSpinner spretailer_name;
    private CustomEditText ed_hording_print_name;
    private CustomEditText ed_address_1;
    private CustomEditText ed_address_2;
    private SearchableSpinner sp_city;
    private SearchableSpinner sp_state;
    private SearchableSpinner sp_country;
    private CustomEditText ed_pin_code;
    private CustomEditText et_from_date;
    private CustomEditText et_todate;
    private CustomEditText et_photo;
    private TextInputLayout et_input_complaint_photo;
    private CustomEditText et_contact_no;
    private CustomEditText et_dealer_start_date;
    private CustomEditText et_dealer_ytd;
    private CustomEditText et_last_date;
    private CustomEditText et_gsb_given_amt;
    private RadioButton rb_OwnBase;
    private RadioButton rb_T_Base;
    private RadioButton rb_DepositBase;
    private RadioButton rb_FOCBase;
    private RadioGroup rg_category;
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private ImageView iv_add;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    private CustomButtonView tv_submit;
    private CustomButtonView tv_cancel;
    private LinearLayout ll_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adv_save_hoarding_request);
        initViews();

    }


    private void initViews() {
        rb_new = findViewById(R.id.rb_new);
        rb_existing = findViewById(R.id.rb_existing);
        rg = findViewById(R.id.rg);
        spdistributor_name = findViewById(R.id.spdistributor_name);
        spvendor_name = findViewById(R.id.spvendor_name);
        spretailer_name = findViewById(R.id.spretailer_name);
        ed_hording_print_name = findViewById(R.id.ed_hording_print_name);
        ed_address_1 = findViewById(R.id.ed_address_1);
        ed_address_2 = findViewById(R.id.ed_address_2);
        sp_city = findViewById(R.id.sp_city);
        sp_state = findViewById(R.id.sp_state);
        sp_country = findViewById(R.id.sp_country);
        ed_pin_code = findViewById(R.id.ed_pin_code);
        et_from_date = findViewById(R.id.et_from_date);
        et_todate = findViewById(R.id.et_todate);
        et_photo = findViewById(R.id.et_photo);
        et_input_complaint_photo = findViewById(R.id.et_input_complaint_photo);
        et_contact_no = findViewById(R.id.et_contact_no);
        et_dealer_start_date = findViewById(R.id.et_dealer_start_date);
        et_dealer_ytd = findViewById(R.id.et_dealer_ytd);
        et_last_date = findViewById(R.id.et_last_date);
        et_gsb_given_amt = findViewById(R.id.et_gsb_given_amt);
        rb_OwnBase = findViewById(R.id.rb_OwnBase);
        rb_T_Base = findViewById(R.id.rb_T_Base);
        rb_DepositBase = findViewById(R.id.rb_DepositBase);
        rb_FOCBase = findViewById(R.id.rb_FOCBase);
        rg_category = findViewById(R.id.rg_category);
        iv_back = findViewById(R.id.iv_back);
        txt_act = findViewById(R.id.txt_act);
        iv_info = findViewById(R.id.iv_info);
        iv_profile = findViewById(R.id.iv_profile);
        iv_add = findViewById(R.id.iv_add);
        rel = findViewById(R.id.rel);
        toolbar_act = findViewById(R.id.toolbar_act);
        toolbarContainer = findViewById(R.id.toolbarContainer);
        tv_submit = findViewById(R.id.tv_submit);
        tv_cancel = findViewById(R.id.tv_cancel);
        ll_btn = findViewById(R.id.ll_btn);
    }
}
