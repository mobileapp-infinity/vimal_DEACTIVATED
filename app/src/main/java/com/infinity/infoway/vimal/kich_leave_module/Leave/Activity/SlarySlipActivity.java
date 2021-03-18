package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Adapter.SalarySlipAdapter;
import com.infinity.kich.Leave.Pojo.SalarySlipPojo;
public class SlarySlipActivity extends AppCompatActivity {

    private LinearLayout ll_main_heder;
    private ListView lv_salary_slip;
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    /**
     *
     */
    private CustomBoldTextView tv_emp_code;
    /**
     *
     */
    private CustomBoldTextView tv_version;
    /**
     *
     */
    private CustomBoldTextView tv_version_code;
    private LinearLayout ll_bottom,ll_main_;

    MySharedPrefereces mySharedPreferenses;

    Activity activity;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slary_slip);
        mySharedPreferenses = new MySharedPrefereces(getApplicationContext());
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("Salary Slip");
        activity = SlarySlipActivity.this;

        SalarySlipDisplay();
    }

    SalarySlipPojo salarySlipPojo;

    private void SalarySlipDisplay() {
        String url = URLS.Get_employee_salary_slip + "&user_id=" + mySharedPreferenses.getUserID() + "";


        System.out.println("Get_employee_salary_slip URL " + url + "");
        url = url.replace(" ","%20");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                // System.out.println("response of Get_miss_pucn_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                if (response.length() > 10) {
                    ll_main_.setVisibility(View.VISIBLE);
                    System.out.println("Get_employee_salary_slip body size :::::::::::::::" + response.length());
                    response = "{\"Data\":" + response + "}";

                    //   System.out.println("sucess response Get_miss_pucn_approve_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    salarySlipPojo = gson.fromJson(response, SalarySlipPojo.class);


                    if (salarySlipPojo != null)
                    {
                        if (salarySlipPojo.getData() != null)
                        {
                            if (salarySlipPojo.getData().get(0) != null)
                            {
                                if (salarySlipPojo.getData().size() > 0)
                                {

                                    if (lv_salary_slip != null)
                                    {


                                        SalarySlipAdapter salarySlipAdapter = new SalarySlipAdapter(SlarySlipActivity.this, salarySlipPojo,activity);

                                        lv_salary_slip.setAdapter(salarySlipAdapter);

                                    }

                                } else {
                                    ll_main_.setVisibility(View.GONE);
                                    DialogUtils.Show_Toast(SlarySlipActivity.this,"No Records Found");

                                    System.out.println("call data size is >0 else &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                                    System.out.println("else  calll ################");

                                }

                            }
                        }
                    }
                }
                else
                {
                    ll_main_.setVisibility(View.GONE);
                    DialogUtils.Show_Toast(SlarySlipActivity.this,"No Records Found");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(SlarySlipActivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }

    private void initView() {
        queue = Volley.newRequestQueue(this);
        ll_main_heder = (LinearLayout) findViewById(R.id.ll_main_heder);
        ll_main_ = (LinearLayout) findViewById(R.id.ll_main_);
        lv_salary_slip = (ListView) findViewById(R.id.lv_salary_slip);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        rel = (RelativeLayout) findViewById(R.id.rel);
        toolbar_act = (Toolbar) findViewById(R.id.toolbar_act);
        toolbarContainer = (CoordinatorLayout) findViewById(R.id.toolbarContainer);
        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);

        PackageInfo pInfo = null;
        assert pInfo != null;

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        tv_version.setText(pInfo.versionName);
        tv_emp_code.setText(mySharedPreferenses.getEmpCode());
    }
}
