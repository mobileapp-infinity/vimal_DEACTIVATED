package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter.CancelLeavesAdapter;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter.ViewAll_LastInOutAdapter;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.LeaveApproveLPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.EndlessScrollListener;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;

import java.util.ArrayList;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class ApproveLeaveActivity extends AppCompatActivity {

    static ListView lvapproveleave;
    CustomBoldTextView txt_act;
    ImageView iv_back;
    ViewAll_LastInOutAdapter viewAll_lastInOutAdapter;
    //    ApprovCancellevesAdapter approvCancellevesAdapter;
    static CancelLeavesAdapter cancelLeavesAdapter;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    static Boolean isChecked_API = false;
    Switch cb_check;
    static RequestQueue queue;
    static MySharedPrefereces mySharedPrefereces;

    static Activity activity;
    private long lastClickTime = 0;

    static int PAGE_NUMBER;
    /*17-dec-19 pragna for info display :) */
    private ImageView iv_info;
    SimpleTooltip tooltip;

    private void showTooltip(View view) {

        tooltip = new SimpleTooltip.Builder(ApproveLeaveActivity.this)
                .anchorView(view)
                .gravity(Gravity.BOTTOM)
                .modal(true)
                .arrowColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .text(getString(R.string.app_name))
                .contentView(R.layout.tooltip_approveleave)
                .build();
        tooltip.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_activity_approve_leave);
//        setContentView(R.layout.dummy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        /*17-dec-19 pragna for info display :) */

        iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_info.setVisibility(View.VISIBLE);
        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTooltip(view);
            }
        });


        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("Leave Approval");
        initView();
        activity = ApproveLeaveActivity.this;
        cb_check = (Switch) findViewById(R.id.cb_check);
        cb_check.setChecked(false);
        listall = new ArrayList<>();
        listall.clear();
        cancelLeavesAdapter = null;
//        if (isChecked_API) {

        //    System.out.println("page load api call ******************");
//        ApprovleaveListingAPICall(1, isChecked_API);
//        } else {
//
//        }

        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               /* if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN)
                {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();*/

                listall = new ArrayList<>();
                listall.clear();
                leaveApproveLPojo = new LeaveApproveLPojo();
                //  lvapproveleave.scrollTo(0,0);

                ApprovleaveListingAPICall(1, isChecked);

                System.out.println("page number in on checked chaneg :::::::: " + PAGE_NUMBER);
//                ApprovleaveListingAPICall(PAGE_NUMBER, isChecked);

                if (isChecked) {
                    isChecked_API = true;
                } else {
                    isChecked_API = false;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LeaveApplicationApproveReject.is_back_leave_app_approval) {
            LeaveApplicationApproveReject.is_back_leave_app_approval = false;
        } else {
            cb_check.setChecked(false);
            listall = new ArrayList<>();
            listall.clear();
            cancelLeavesAdapter = null;
            ApprovleaveListingAPICall(1, isChecked_API);
        }

    }

    public static List<LeaveApproveLPojo.DataBean> listall;
    public static LeaveApproveLPojo leaveApproveLPojo;

    public static void ApprovleaveListingAPICall(final int PageNo, final Boolean Isc) {

        String url;
        queue = Volley.newRequestQueue(activity);

        if (Isc) {
            // url = URLS.Get_leave_approve_list + "&user_id=" + "1" + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "2" + "";
            url = URLS.Get_leave_approve_list + "&user_id=" + mySharedPrefereces.getUserID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "2" + "";
        } else {

            // url = URLS.Get_leave_approve_list + "&user_id=" + "1" + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "1" + "";
            url = URLS.Get_leave_approve_list + "&user_id=" + mySharedPrefereces.getUserID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "1" + "";
        }
        url = url.replace(" ", "%20");
        System.out.println("Get_leave_approve_list URL on resume ::::::  " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                System.out.println("response size in leave listing :::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    leaveApproveLPojo = new LeaveApproveLPojo();
                    leaveApproveLPojo = gson.fromJson(response, LeaveApproveLPojo.class);
                    System.out.println("approve leave listing data size :::::::::::::::::::::::::::::::::::::" + leaveApproveLPojo.getData().size());

                    listall.addAll(leaveApproveLPojo.getData());

                    if (leaveApproveLPojo != null) {
                        if (leaveApproveLPojo.getData() != null) {
                            if (leaveApproveLPojo.getData().get(0) != null) {
                                if (leaveApproveLPojo.getData().size() > 0) {

                                    if (lvapproveleave != null) {

                                        if (cancelLeavesAdapter != null && PageNo > 1)
//                        if (adapter != null && pageNo > 1)
                                        {
                                            System.out.println("page if  ");
                                            cancelLeavesAdapter.notifyDataSetChanged();
                                        } else {
                                            System.out.println("page else  ");

                                            cancelLeavesAdapter = new CancelLeavesAdapter(activity, leaveApproveLPojo, listall, Isc);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                            lvapproveleave.setAdapter(cancelLeavesAdapter);

                                            lvapproveleave.setOnScrollListener(new EndlessScrollListener() {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount) {

                                                    System.out.println("item counts :::::::::::: " + totalItemsCount);
                                                    System.out.println("page number ::::::::::: " + page);

                                                    PAGE_NUMBER = page;

                                                    ApprovleaveListingAPICall(page, isChecked_API);
//}
                                                    return true;
                                                }
                                            });
                                           /* lvapproveleave.setOnScrollListener(new EndlessScrollListener() {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount) {

                                                    System.out.println("page number1111 ::::::::::: "+page);
                                                    ApprovleaveListingAPICall(page, true);

                                                    return false;
                                                }
                                            });*/
                                        }


                                    }

                                } else {
                                    cancelLeavesAdapter = new CancelLeavesAdapter(activity, leaveApproveLPojo, listall, Isc);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                    lvapproveleave.setAdapter(cancelLeavesAdapter);
                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(activity, "No Records Found");
                                }

                            }
                        }
                    }
                } else {
                    if (PageNo == 1) {

                        listall.clear();
                        DialogUtils.Show_Toast(activity, "No Records Found");
                        cancelLeavesAdapter = new CancelLeavesAdapter(activity, leaveApproveLPojo, listall, Isc);
                        cancelLeavesAdapter.notifyDataSetChanged();
                        lvapproveleave.setAdapter(cancelLeavesAdapter);

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(activity, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void initView() {
        queue = Volley.newRequestQueue(this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        lvapproveleave = (ListView) findViewById(R.id.lv_approve_leave);

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
        tv_emp_code.setText(mySharedPrefereces.getEmpCode());
    }
}
