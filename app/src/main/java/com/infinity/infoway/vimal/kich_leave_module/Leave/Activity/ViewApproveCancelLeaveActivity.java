package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.infinity.infoway.vimal.util.common.EndlessScrollListener;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Adapter.CancelApproveLeaveAdapter;
import com.infinity.kich.Leave.Pojo.CancelApproveLPojo;

import java.util.ArrayList;

public class ViewApproveCancelLeaveActivity extends AppCompatActivity
{

    /**
     * View Approve Cancel Leave
     */
    private CustomBoldTextView txtEnrollNo;
    /**
     * Pending/All
     */
    private CustomBoldTextView txtSrNo;
    private Switch cb_check;
    private LinearLayout llMainApproveHeadderLeave;
    private LinearLayout llMainHeder;
    private ListView lvViewCancelLeaveApprove;
    private ImageView ivBack;
    /**
     *
     */
    private CustomBoldTextView txtAct;
    private Toolbar toolbarAct;
    private LinearLayout toolbarContainer;
    /**
     *
     */
    private CustomBoldTextView tvEmpCode;
    /**
     *
     */
    private CustomBoldTextView tvVersion;
    /**
     *
     */
    private CustomBoldTextView tvVersionCode;
    private LinearLayout llBottom;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;

    MySharedPrefereces mySharedPreferenses;
    CancelApproveLeaveAdapter cancel_app_leave_adapter;

    ImageView iv_back;
    CustomBoldTextView txt_act;
    RequestQueue queue;
    Boolean is_check_API = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_approve_cancel_leave);
        mySharedPreferenses = new MySharedPrefereces(getApplicationContext());
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
        txt_act.setText("Cancel Leave Approval");
        queue = Volley.newRequestQueue(this);
        initView();
        listall = new ArrayList<>();
        listall.clear();
        cb_check = (Switch) findViewById(R.id.cb_check);
        cb_check.setChecked(false);
//        ApprovecancelLeaveAPICall(1, is_check_API);

        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {


                listall = new ArrayList<>();
                listall.clear();
                ApprovecancelLeaveAPICall(1, isChecked);

                if (isChecked)
                {
                    is_check_API = true;
                }
                else
                    {
                    is_check_API = false;
                }
            }
        });
        //********* data is not properly display ****


    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (com.infinity.kich.Leave.Activity.CancelLeaveApproveRejectActivity.is_back_cancel_leave)
        {
            System.out.println("is Backkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            com.infinity.kich.Leave.Activity.CancelLeaveApproveRejectActivity.is_back_cancel_leave = false;

          /*  listall = new ArrayList<>();
            listall.clear();
            ApprovecancelLeaveAPICall(1, is_check_API);*/
        }
        else
        {
            listall = new ArrayList<>();
            listall.clear();
            cb_check.setChecked(false);
            ApprovecancelLeaveAPICall(1, is_check_API);

        }


    }

    ArrayList<CancelApproveLPojo.DataBean> listall;
    CancelApproveLPojo cancelApproveLPojo;

    private void ApprovecancelLeaveAPICall(final int PageNo, Boolean Isc)
    {


        String url;
        queue = Volley.newRequestQueue(ViewApproveCancelLeaveActivity.this);

        if (Isc)
        {
            url = URLS.Get_cancel_leave_appliation_approve_list + "&user_id=" + mySharedPreferenses.getUserID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "2" + "";
        }
        else
        {

            url = URLS.Get_cancel_leave_appliation_approve_list + "&user_id=" + mySharedPreferenses.getUserID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "1" + "";
        }

        url = url.replace(" ","%20");
        System.out.println("Get_cancel_leave_appliation_approve_list URL onResume Call *****" + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                // System.out.println("response of Get_User_LeaveBalance !!!!!!!!!!! " + response);
                response = response + "";

                if (response.length() > 10) {
//                    System.out.println("response size of Get_apply_cancel_leave_appliation_list listing ::::::::::::::::::: " + response.length());

                    response = "{\"Data\":" + response + "}";

                    // System.out.println("sucess response Get_User_LeaveBalance !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    cancelApproveLPojo = gson.fromJson(response, CancelApproveLPojo.class);
                    listall.addAll(cancelApproveLPojo.getData());
                    //System.out.println("response data size of cancelLeaveleave listing ::::::::::::::::::: " + cancelLeavePojo.getData().size());
                    if (cancelApproveLPojo != null) {
                        if (cancelApproveLPojo.getData() != null)
                        {
                            if (cancelApproveLPojo.getData().get(0) != null) {
                                if (cancelApproveLPojo.getData().size() > 0)
                                {

                                    if (lvViewCancelLeaveApprove != null) {

                                        if (cancel_app_leave_adapter != null && PageNo > 1)
//                        if (adapter != null && pageNo > 1)
                                        {
                                            cancel_app_leave_adapter.notifyDataSetChanged();
                                        } else {

                                            cancel_app_leave_adapter = new CancelApproveLeaveAdapter(ViewApproveCancelLeaveActivity.this, cancelApproveLPojo, listall);
                                            // viewLeaveListingAdapter.notifyDataSetChanged();
                                            lvViewCancelLeaveApprove.setAdapter(cancel_app_leave_adapter);
                                            lvViewCancelLeaveApprove.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount)
                                                {
                                                    System.out.println("page number view listing API ::::::::: " + page);
                                                    ApprovecancelLeaveAPICall(page, is_check_API);

                                                    return true;
                                                }
                                            });
                                        }

                                        //int pg_ = PageNo + 1;

                                        // System.out.println("*********WISHLIST *******again called+++++++++++ " + pg_ + "");

                                        //******** for page >1
                                        // ViewLeaveApiCall(pg_, Isc);

//                                        viewLeaveListingAdapter = new ViewLeaveListingAdapter(ViewLeaveListingActivity.this, leave_list_pojo, listall);
//                                        lvviewleave.setAdapter(viewLeaveListingAdapter);

                                    }

                                } else {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(ViewApproveCancelLeaveActivity.this, "No Records Found");
                                }

                            }
                        }
                    }
                } else {
                    if (PageNo == 1) {

                        listall.clear();
                        DialogUtils.Show_Toast(ViewApproveCancelLeaveActivity.this, "No Records Found");
                        cancel_app_leave_adapter = new CancelApproveLeaveAdapter(ViewApproveCancelLeaveActivity.this, cancelApproveLPojo, listall);
                        cancel_app_leave_adapter.notifyDataSetChanged();
                        lvViewCancelLeaveApprove.setAdapter(cancel_app_leave_adapter);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(ViewApproveCancelLeaveActivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void initView() {
        txtEnrollNo = (CustomBoldTextView) findViewById(R.id.txt_enroll_no);
        txtSrNo = (CustomBoldTextView) findViewById(R.id.txt_sr_no);
        cb_check = (Switch) findViewById(R.id.cb_check);
        llMainApproveHeadderLeave = (LinearLayout) findViewById(R.id.ll_main_approve_headder_leave);
        llMainHeder = (LinearLayout) findViewById(R.id.ll_main_heder);
        lvViewCancelLeaveApprove = (ListView) findViewById(R.id.lv_view_cancel_leave_approve);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        txtAct = (CustomBoldTextView) findViewById(R.id.txt_act);
        toolbarAct = (Toolbar) findViewById(R.id.toolbar_act);
        toolbarContainer = (LinearLayout) findViewById(R.id.toolbarContainer);
        tvEmpCode = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tvVersion = (CustomBoldTextView) findViewById(R.id.tv_version);
        tvVersionCode = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);

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
