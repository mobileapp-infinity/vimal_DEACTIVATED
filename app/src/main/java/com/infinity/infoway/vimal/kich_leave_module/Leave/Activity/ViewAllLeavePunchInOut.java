package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;


import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Adapter.ViewAll_LastInOutAdapter;
import com.infinity.kich.Leave.App.MonthYearPicker;
import com.infinity.kich.Leave.Pojo.PunchDetailPojo;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class ViewAllLeavePunchInOut extends AppCompatActivity {

    CardView cardroot;
    ListView lvviewall;
    ViewAll_LastInOutAdapter viewAll_lastInOutAdapter;
    CustomBoldTextView txt_act, tv_emp_code, tv_version, tv_version_code;
    ImageView iv_back;
    RequestQueue queue;
    MySharedPrefereces mySharedPreferenses;
    private MonthYearPicker myp;
    FloatingActionButton fab;
    ImageView iv_info;
    PunchDetailPojo punchDetailPojo;
    SimpleTooltip tooltip;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_leave_punch_in_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_info.setVisibility(View.VISIBLE);
        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTooltip(view);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("View All");
        initView();
        PunchDetailAPICall("0", "0");

    }

    private void showTooltip(View view)
    {
        tooltip = new SimpleTooltip.Builder(this)
                .anchorView(view)
                .gravity(Gravity.BOTTOM)
                .modal(true)
                .arrowColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .text(getString(R.string.app_name))
                .contentView(R.layout.tooltip_view_punch_in)
                .build();
        tooltip.show();

    }

    private void PunchDetailAPICall(String month, String year) {

        mySharedPreferenses = new MySharedPrefereces(getApplicationContext());
        String url = URLS.Get_employee_punch_detail + "&emp_id=" + mySharedPreferenses.getEmpID() + "&month=" + month + "&year=" + year + "";
        url = url.replace(" ","%20");
        System.out.println("Get_employee_punch_detail URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                System.out.println("response of Get_employee_punch_detail !!!!!!!!!!! " + response);
                response = response + "";
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";

                    System.out.println("sucess response Get_employee_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    punchDetailPojo = gson.fromJson(response, PunchDetailPojo.class);
                    if (punchDetailPojo != null) {
                        if (punchDetailPojo.getData() != null) {
                            if (punchDetailPojo.getData().get(0) != null) {
                                if (punchDetailPojo.getData().size() > 0) {

                                    if (lvviewall != null) {

                                        viewAll_lastInOutAdapter = new ViewAll_LastInOutAdapter(ViewAllLeavePunchInOut.this, punchDetailPojo);
                                        lvviewall.setAdapter(viewAll_lastInOutAdapter);
                                        // viewAll_lastInOutAdapter.notifyDataSetChanged();
                                    }

                                } else {

                                }

                            }
                        }
                    }
                } else {
                    DialogUtils.Show_Toast(ViewAllLeavePunchInOut.this, "No Records Found");
                    if (lvviewall != null)
                    {

                        punchDetailPojo = new PunchDetailPojo();
                        viewAll_lastInOutAdapter = new ViewAll_LastInOutAdapter(ViewAllLeavePunchInOut.this, punchDetailPojo);
                        lvviewall.setAdapter(viewAll_lastInOutAdapter);
                        viewAll_lastInOutAdapter.notifyDataSetChanged();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(ViewAllLeavePunchInOut.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void initView() {

        mySharedPreferenses = new MySharedPrefereces(getApplicationContext());
        queue = Volley.newRequestQueue(ViewAllLeavePunchInOut.this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myp.show();

            }
        });


        myp = new MonthYearPicker(this);
        myp.build(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                punchDetailPojo = new PunchDetailPojo();
                System.out.println("select call ::::: month ::::::: " + String.valueOf(myp.getSelectedMonth() + 1) + "year ::::::: " + String.valueOf(myp.getSelectedYear()));
                PunchDetailAPICall(String.valueOf(myp.getSelectedMonth() + 1), String.valueOf(myp.getSelectedYear()));


                //tvFromDate.setText(myp.getSelectedMonthName() + "-" + myp.getSelectedYear());
            }
        }, null);
        cardroot = (CardView) findViewById(R.id.card_root);
        lvviewall = (ListView) findViewById(R.id.lv_view_all);


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
