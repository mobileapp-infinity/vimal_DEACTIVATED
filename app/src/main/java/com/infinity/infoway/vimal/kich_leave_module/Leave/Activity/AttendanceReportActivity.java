package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.ExpandableHeightListView;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Adapter.AttReportAdapter;
import com.infinity.kich.Leave.Adapter.SummeryAttAdapter;
import com.infinity.kich.Leave.App.MonthYearPicker;
import com.infinity.kich.Leave.Pojo.AttReportDetailPojo;
import com.infinity.kich.Leave.Pojo.AttfReportSummryPojo;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class AttendanceReportActivity extends AppCompatActivity implements View.OnClickListener {


    private ExpandableHeightListView lv_att_report_summerry;
    private ImageView iv_down;
    private ExpandableHeightListView lv_att_report;
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
    private LinearLayout lv_sub;

    RequestQueue queue;
    MySharedPrefereces mySharedPrefereces;
    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    int currentMonth;
    int currentYear;
    FloatingActionButton fab;
    private MonthYearPicker myp;
    SimpleTooltip tooltip;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_activity_attendance_report);

        myp = new MonthYearPicker(this);
        myp.build(new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                attReportDetailPojo = new AttReportDetailPojo();
                System.out.println("select call ::::: month ::::::: " + String.valueOf(myp.getSelectedMonth() + 1) + "year ::::::: " + String.valueOf(myp.getSelectedYear()));
                //AttendanceReportApi(String.valueOf(myp.getSelectedMonth() + 1), String.valueOf(myp.getSelectedYear()));
                SummeryDisplayApi(String.valueOf(myp.getSelectedMonth() + 1), String.valueOf(myp.getSelectedYear()));

                //tvFromDate.setText(myp.getSelectedMonthName() + "-" + myp.getSelectedYear());
            }
        }, null);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                myp.show();
            }
        });
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
        txt_act.setText("Attendance Report");
        iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_info.setVisibility(View.VISIBLE);
        iv_info.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                showTooltip(view);
            }
        });

        Date currentTime = localCalendar.getTime();
        int currentDay = localCalendar.get(Calendar.DATE);
        currentMonth = localCalendar.get(Calendar.MONTH) + 1;
        currentYear = localCalendar.get(Calendar.YEAR);
        int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
        int currentDayOfMonth = localCalendar.get(Calendar.DAY_OF_MONTH);
        int CurrentDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);


        System.out.println("Current Date: " + currentTime);
        System.out.println("Current Day: " + currentDay);
        System.out.println("Current Month: " + currentMonth);
        System.out.println("Current Year: " + currentYear);
        System.out.println("Current Day of Week: " + currentDayOfWeek);
        System.out.println("Current Day of Month: " + currentDayOfMonth);
        System.out.println("Current Day of Year: " + CurrentDayOfYear);

        SummeryDisplayApi(String.valueOf(currentMonth), String.valueOf(currentYear));


    }

    private void showTooltip(View view) {

        tooltip = new SimpleTooltip.Builder(this)
                .anchorView(view)
                .gravity(Gravity.BOTTOM)
                .modal(true)
                .arrowColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .text(getString(R.string.app_name))
                .contentView(R.layout.tooltip_attendance)
                .build();
        tooltip.show();


    }

    AttReportDetailPojo attReportDetailPojo;

    private void AttendanceReportApi(String month, String year) {
        DialogUtils.showProgressDialog(AttendanceReportActivity.this, "");
        String url = URLS.Get_employee_attendance_report_detail + "&month=" + month + "&year=" + year + "&emp_id=" + mySharedPrefereces.getEmpID() + "&user_id=" + mySharedPrefereces.getUserID() + "";


        System.out.println("Get_employee_attendance_report_summary URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();

                // System.out.println("response of Get_miss_pucn_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                if (response.length() > 10) {

                    System.out.println("Get_employee_attendance_report_summary body size :::::::::::::::" + response.length());
                    response = "{\"Data\":" + response + "}";

                    //   System.out.println("sucess response Get_miss_pucn_approve_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    attReportDetailPojo = new AttReportDetailPojo();
                    attReportDetailPojo = gson.fromJson(response, AttReportDetailPojo.class);


                    if (attReportDetailPojo != null) {
                        if (attReportDetailPojo.getData() != null) {
                            if (attReportDetailPojo.getData().get(0) != null) {
                                if (attReportDetailPojo.getData().size() > 0) {

                                    if (lv_att_report != null) {

                                        AttReportAdapter attReportAdapter = new AttReportAdapter(AttendanceReportActivity.this, attReportDetailPojo);
                                        lv_att_report.setExpanded(true);
                                        lv_att_report.setAdapter(attReportAdapter);
                                        attReportAdapter.notifyDataSetChanged();
                                    }

                                } else {

                                    AttReportAdapter attReportAdapter = new AttReportAdapter(AttendanceReportActivity.this, attReportDetailPojo);
                                    lv_att_report.setAdapter(attReportAdapter);
                                    attReportAdapter.notifyDataSetChanged();
                                    System.out.println("call data size is >0 else &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                                    System.out.println("else  calll ################");

                                }

                            }
                        }
                    }
                } else {
                    DialogUtils.Show_Toast(AttendanceReportActivity.this, "No Records Found");
                    System.out.println("data not available ::::::::: ");
                    response = "{\"Data\":" + response + "}";

                    //   System.out.println("sucess response Get_miss_pucn_approve_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    attReportDetailPojo = new AttReportDetailPojo();
                    attReportDetailPojo = gson.fromJson(response, AttReportDetailPojo.class);
                    AttReportAdapter attReportAdapter = new AttReportAdapter(AttendanceReportActivity.this, attReportDetailPojo);
                    lv_att_report.setAdapter(attReportAdapter);
                    attReportAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AttendanceReportActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    AttfReportSummryPojo attfReportSummryPojo;

    private void SummeryDisplayApi(final String month, final String year) {

        DialogUtils.showProgressDialog(AttendanceReportActivity.this, "");
        String url = URLS.Get_employee_attendance_report_summary + "&month=" + month + "&year=" + year + "&emp_id=" + mySharedPrefereces.getEmpID() + "&user_id=" + mySharedPrefereces.getUserID() + "";

        url = url.replace(" ","%20");
        System.out.println("Get_employee_attendance_report_summary URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();

                // System.out.println("response of Get_miss_pucn_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                if (response.length() > 10) {

                    System.out.println("Get_employee_attendance_report_summary body size :::::::::::::::" + response.length());
                    response = "{\"Data\":" + response + "}";

                    //   System.out.println("sucess response Get_miss_pucn_approve_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    attfReportSummryPojo = gson.fromJson(response, AttfReportSummryPojo.class);


                    if (attfReportSummryPojo != null) {
                        if (attfReportSummryPojo.getData() != null) {
                            if (attfReportSummryPojo.getData().get(0) != null) {
                                if (attfReportSummryPojo.getData().size() > 0) {

                                    if (lv_att_report_summerry != null) {


                                        SummeryAttAdapter summeryAttAdapter = new SummeryAttAdapter(AttendanceReportActivity.this, attfReportSummryPojo);
                                        lv_att_report_summerry.setExpanded(true);
                                        lv_att_report_summerry.setAdapter(summeryAttAdapter);

                                        AttendanceReportApi(month, year);

                                    }

                                } else {
                                    System.out.println("call data size is >0 else &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                                    System.out.println("else  calll ################");

                                }

                            }
                        }
                    }
                }

                else
                {
                    response = "{\"Data\":" + response + "}";

                    //   System.out.println("sucess response Get_miss_pucn_approve_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    attfReportSummryPojo = new AttfReportSummryPojo();
                    attfReportSummryPojo = gson.fromJson(response, AttfReportSummryPojo.class);
                    SummeryAttAdapter summeryAttAdapter = new SummeryAttAdapter(AttendanceReportActivity.this, attfReportSummryPojo);
                    lv_att_report_summerry.setExpanded(true);
                    lv_att_report_summerry.setAdapter(summeryAttAdapter);
                    summeryAttAdapter.notifyDataSetChanged();
                    AttendanceReportApi(month, year);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AttendanceReportActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void initView() {
        queue = Volley.newRequestQueue(this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        lv_att_report_summerry = (ExpandableHeightListView) findViewById(R.id.lv_att_report_summerry);
        iv_down = (ImageView) findViewById(R.id.iv_down);
        iv_down.setOnClickListener(this);
        lv_att_report = (ExpandableHeightListView) findViewById(R.id.lv_att_report);
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
        lv_sub = (LinearLayout) findViewById(R.id.lv_sub);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.iv_down:
//                AttendanceReportApi(String.valueOf(currentMonth), String.valueOf(currentYear));
                if (lv_sub.getVisibility() == View.GONE) {
                    lv_sub.setVisibility(View.VISIBLE);

                } else if (lv_sub.getVisibility() == View.VISIBLE) {
                    lv_sub.setVisibility(View.GONE);
                }

                break;
        }
    }
}
