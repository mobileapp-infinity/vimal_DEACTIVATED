package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;


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
import androidx.cardview.widget.CardView;
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
import com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter.StatisticsAdapter;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.StatisticsPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;
public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener
{

    private ListView lv_statistics;
    private CardView card_leave_balance;
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    private ImageView iv_todays_inout;
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

    RequestQueue queue;
    MySharedPrefereces mySharedPrefereces;

    StatisticsAdapter statisticsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
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
        txt_act.setText("Statistics");
        initView();

        //********* display ststistics api call ***********
        StatisticsDisplay();
    }

    //*********statistics  data APi call as per web***********
    private void StatisticsDisplay()
    {

        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        String url = URLS.Get_Other_Statistics + "&user_id=" + mySharedPrefereces.getUserID() + "";
        url = url.replace(" ","%20");
        System.out.println("Get_Other_Statistics URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
//                DialogUtils.hideProgressDialog();
                if (response.length()>10)
                {
                    System.out.println("response of Get_Other_Statistics !!!!!!!!!!! " + response);
                    response = response + "";
                    response = "{\"Data\":" + response + "}";

                    System.out.println("sucess response Get_Other_Statistics !!!!!!!!!!!!!!!!!!!" + response + "");

                    Gson gson = new Gson();
                    StatisticsPojo statisticsPojo = gson.fromJson(response, StatisticsPojo.class);
                    if (statisticsPojo != null)
                    {
                        if (statisticsPojo.getData() != null)
                        {
                            if (statisticsPojo.getData().get(0) != null)
                            {
                                if (statisticsPojo.getData().size() > 0)
                                {
                                    card_leave_balance.setVisibility(View.VISIBLE);
                                    if (lv_statistics != null)
                                    {
                                        card_leave_balance.setVisibility(View.VISIBLE);
                                        statisticsAdapter = new StatisticsAdapter(StatisticsActivity.this, statisticsPojo);
                                        lv_statistics.setAdapter(statisticsAdapter);

                                    }

                                }
                                else
                                {
                                    card_leave_balance.setVisibility(View.GONE);
                                    DialogUtils.Show_Toast(StatisticsActivity.this,"No Records Found");

                                }

                            }
                        }
                    }
                }

                else
                {
                    card_leave_balance.setVisibility(View.GONE);
                    DialogUtils.Show_Toast(StatisticsActivity.this,"No Records Found");
                }



            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                DialogUtils.Show_Toast(StatisticsActivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void initView()
    {
        lv_statistics = (ListView) findViewById(R.id.lv_statistics);
        card_leave_balance = (CardView) findViewById(R.id.card_leave_balance);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        rel = (RelativeLayout) findViewById(R.id.rel);
        toolbar_act = (Toolbar) findViewById(R.id.toolbar_act);
        toolbarContainer = (CoordinatorLayout) findViewById(R.id.toolbarContainer);
        iv_todays_inout = (ImageView) findViewById(R.id.iv_todays_inout);
        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);

        queue = Volley.newRequestQueue(StatisticsActivity.this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
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
                onBackPressed();

                break;
        }
    }
}
