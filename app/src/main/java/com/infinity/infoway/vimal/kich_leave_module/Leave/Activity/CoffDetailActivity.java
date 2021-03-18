package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Pojo.CoffDetailPojo;
import com.infinity.kich.Leave.Pojo.CoffPojo;
import com.infinity.kich.Leave.Pojo.CoofApprovePojo;

import java.util.ArrayList;

public class CoffDetailActivity extends AppCompatActivity implements View.OnClickListener {

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
    private LinearLayout ll_bottom;
    /**
     * Update
     */
    private CustomBoldTextView tv_approve;
    /**
     * Delete
     */
    private CustomBoldTextView tv_delete;
    private LinearLayout ll_update_delete;
    private EditText edt_emp_name;
    private LinearLayout ll_emp_name;
    private EditText edt_no_emp;
    private LinearLayout ll_total_emp;
    private EditText edt_req_by;
    private LinearLayout ll_leave_balance;
    private EditText edt_from_date;
    private ImageView iv_calendar;
    private LinearLayout ll_from_date;
    private EditText edt_to_date;
    private ImageView iv_calendar_to_date;
    private LinearLayout ll_to_date;
    private EditText edt_reson;
    private LinearLayout ll_remark;
    private EditText edt_app_by;
    private LinearLayout ll_approved_by;
    private EditText edt_status;
    private LinearLayout ll_contact_no;
    /**
     * Submit
     */
    private CustomBoldTextView tv_submit;
    /**
     * Cancel
     */
    private CustomBoldTextView tv_cancel;
    private LinearLayout ll_submit_cancel;
    /**
     * Apply For Cancel Leave
     */
    private CustomBoldTextView tv_apply_cancel_leave;
    private LinearLayout ll_cancel_approve_leave;

    MySharedPrefereces mySharedPrefereces;

    RequestQueue queue;
    String ID, Status,approval_ID,req_emp_id;
    CustomBoldTextView tv_approve_coff;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coff_detail);
        queue = Volley.newRequestQueue(this);

        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("Coff Detail");
        Intent intent = getIntent();

        ID = intent.getStringExtra("ID");
        Status = intent.getStringExtra("status");
        approval_ID = intent.getStringExtra("App_ID");
        req_emp_id = intent.getStringExtra("req_emp_id");

        if (ID!=null && !ID.contentEquals(""))
        {
            CoffDetailBind(ID);
        }

        if (Status!=null && !Status.contentEquals(""))
        {
            if (Status.compareToIgnoreCase("Pending")==0)
            {
                tv_approve_coff.setVisibility(View.VISIBLE);
            }

            else
            {
                tv_approve_coff.setVisibility(View.GONE);
            }
        }

        tv_approve_coff.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (approval_ID!=null&&!approval_ID.contentEquals(""))
                {
                    ApproveLeave(approval_ID);
                }

            }
        });

    }

    private void ApproveLeave(String approval_ID)
    {

        String URLs = URLS.compensatory_leave_approve + "&id=" + approval_ID + "" + "&ip=" + "1" + "&user_id=" + mySharedPrefereces.getUserID() + "&ex_req_emp_id=" + req_emp_id + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("compensatory_leave_approve calls   !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = response + "";


                        System.out.println("THIS compensatory_leave_approve  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");


                        System.out.println("response length :::::::::::::: " + response.length());
                        System.out.println("response data size  :::::::::::::: " + response);

                        if (response.length() > 5) {
                            response = "{\"Data\":" + response + "}";
                            Gson gson = new Gson();


                            CoofApprovePojo coofApprovePojo = gson.fromJson(response, CoofApprovePojo.class);

                            if (coofApprovePojo != null)
                            {
                                if (coofApprovePojo.getData().size() > 0)
                                {
                                    DialogUtils.Show_Toast(CoffDetailActivity.this, coofApprovePojo.getData().get(0).getMsg() + "");

                                    com.infinity.kich.Leave.Activity.COffLeaveApprovalActitivty.listall = new ArrayList<>();
                                    com.infinity.kich.Leave.Activity.COffLeaveApprovalActitivty.listall.clear();
                                    com.infinity.kich.Leave.Activity.COffLeaveApprovalActitivty.coffPojo = new CoffPojo();
                                    com.infinity.kich.Leave.Activity.COffLeaveApprovalActitivty.CoffApproval(1,false);

                                    Intent intent =new Intent(CoffDetailActivity.this, com.infinity.kich.Leave.Activity.COffLeaveApprovalActitivty.class);
                                    startActivity(intent);
                                    finish();





                                }
                            }


                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println();
            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);

    }

    CoffDetailPojo coffDetailPojo;

    private void CoffDetailBind(String ID)
    {
        String URLs = URLS.compensatory_leave_approve_detail + "&edew_id=" +ID + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("compensatory_leave_approve_detail calls   !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS compensatory_leave_approve_detail  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");

                        if (response.length() > 5)
                        {
                            Gson gson = new Gson();

                            coffDetailPojo = gson.fromJson(response, CoffDetailPojo.class);

                            if (coffDetailPojo != null)
                            {
                                if (coffDetailPojo.getData() != null)
                                {
                                    if (coffDetailPojo.getData().size() > 0)
                                    {

                                        edt_emp_name.setText(coffDetailPojo.getData().get(0).getEx_req_emp() + "");
                                        edt_no_emp.setText(coffDetailPojo.getData().get(0).getEx_req_tot_emp() + "");
                                        edt_req_by.setText(coffDetailPojo.getData().get(0).getEx_req_by_emp() + "");
                                        edt_from_date.setText(coffDetailPojo.getData().get(0).getEx_req_from_date() + "");
                                        edt_to_date.setText(coffDetailPojo.getData().get(0).getEx_req_to_date() + "");
                                        edt_reson.setText(coffDetailPojo.getData().get(0).getEx_req_reason() + "");
                                        edt_status.setText(coffDetailPojo.getData().get(0).getApp_status() + "");


                                    }


                                }
                            }


                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println();
            }
        });
        queue.add(req);


    }

    private void initView()
    {
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        rel = (RelativeLayout) findViewById(R.id.rel);
        toolbar_act = (Toolbar) findViewById(R.id.toolbar_act);
        toolbarContainer = (CoordinatorLayout) findViewById(R.id.toolbarContainer);
        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_approve_coff = (CustomBoldTextView) findViewById(R.id.tv_approve_coff);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tv_approve = (CustomBoldTextView) findViewById(R.id.tv_approve);
        tv_delete = (CustomBoldTextView) findViewById(R.id.tv_delete);
        ll_update_delete = (LinearLayout) findViewById(R.id.ll_update_delete);
        edt_emp_name = (EditText) findViewById(R.id.edt_emp_name);
        ll_emp_name = (LinearLayout) findViewById(R.id.ll_emp_name);
        edt_no_emp = (EditText) findViewById(R.id.edt_no_emp);
        ll_total_emp = (LinearLayout) findViewById(R.id.ll_total_emp);
        edt_req_by = (EditText) findViewById(R.id.edt_req_by);
        ll_leave_balance = (LinearLayout) findViewById(R.id.ll_leave_balance);
        edt_from_date = (EditText) findViewById(R.id.edt_from_date);
        iv_calendar = (ImageView) findViewById(R.id.iv_calendar);
        ll_from_date = (LinearLayout) findViewById(R.id.ll_from_date);
        edt_to_date = (EditText) findViewById(R.id.edt_to_date);
        iv_calendar_to_date = (ImageView) findViewById(R.id.iv_calendar_to_date);
        ll_to_date = (LinearLayout) findViewById(R.id.ll_to_date);
        edt_reson = (EditText) findViewById(R.id.edt_reson);
        ll_remark = (LinearLayout) findViewById(R.id.ll_remark);
        edt_app_by = (EditText) findViewById(R.id.edt_app_by);
        ll_approved_by = (LinearLayout) findViewById(R.id.ll_approved_by);
        edt_status = (EditText) findViewById(R.id.edt_status);
        ll_contact_no = (LinearLayout) findViewById(R.id.ll_contact_no);
        tv_submit = (CustomBoldTextView) findViewById(R.id.tv_submit);
        tv_cancel = (CustomBoldTextView) findViewById(R.id.tv_cancel);
        ll_submit_cancel = (LinearLayout) findViewById(R.id.ll_submit_cancel);
        tv_apply_cancel_leave = (CustomBoldTextView) findViewById(R.id.tv_apply_cancel_leave);
        ll_cancel_approve_leave = (LinearLayout) findViewById(R.id.ll_cancel_approve_leave);

        PackageInfo pInfo = null;
        assert pInfo != null;
        try
        {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        tv_version.setText(pInfo.versionName);
        tv_emp_code.setText(mySharedPrefereces.getEmpCode());

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            default:
                break;
            case R.id.iv_back:
                break;
        }
    }
}
