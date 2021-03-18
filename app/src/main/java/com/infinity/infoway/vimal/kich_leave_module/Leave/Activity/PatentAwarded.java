package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.Activity;
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
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Adapter.PatentAwardedAdapter;
import com.infinity.kich.Leave.Pojo.PatentAwaredPojo;

import java.util.ArrayList;
import java.util.List;

public class PatentAwarded extends AppCompatActivity {

    CustomBoldTextView txtenrollno;
    CustomBoldTextView txtsrno;
    Switch cbcheck;
    LinearLayout llmainapproveheadderleave;
    LinearLayout llmainheder;
    static ListView lv_patent_award;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    static  PatentAwardedAdapter patentAwardedAdapter;
    static MySharedPrefereces mySharedPrefereces;
    ImageView iv_back;
    CustomBoldTextView txt_act;
    static int PAGE_NUMBER;


    public static PatentAwaredPojo patentAwaredPojo;
    static  Boolean isChecked_API = false;
    static RequestQueue queue;

    static  Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patent_awarded);

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
        txt_act.setText("Patent Awarded");
        initView();

        activity = PatentAwarded.this;
        patentAwardedAdapter = null;
        listall = new ArrayList<>();
        listall.clear();
        cbcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listall = new ArrayList<>();
                listall.clear();
                patentAwaredPojo = new PatentAwaredPojo();
                //  lvapproveleave.scrollTo(0,0);

                ApprovePatentAwarded(1, isChecked);

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

    public static void ApprovePatentAwarded(final int page_no, final boolean isChecked)
    {


        String url;
        queue = Volley.newRequestQueue(activity);

        if (isChecked)
        {
            url = URLS.Get_Patent_awarded_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "2" + "&id=" + "0" + "";
        } else {

            url = URLS.Get_Patent_awarded_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "1" + "&id=" + "0" + "";
        }
        url = url.replace(" ", "%20");

        System.out.println("Get_Patent_awarded_list URL on resume ::::::  " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                System.out.println("response size in leave listing Get_Patent_awarded_list:::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    patentAwaredPojo = new PatentAwaredPojo();
                    patentAwaredPojo = gson.fromJson(response, PatentAwaredPojo.class);
                    System.out.println("Get_Patent_awarded_list listing data size :::::::::::::::::::::::::::::::::::::" + patentAwaredPojo.getData().size());

                    listall.addAll(patentAwaredPojo.getData());

                    if (patentAwaredPojo != null) {
                        if (patentAwaredPojo.getData() != null) {
                            if (patentAwaredPojo.getData().get(0) != null) {
                                if (patentAwaredPojo.getData().size() > 0) {

                                    if (lv_patent_award != null) {

                                        if (patentAwardedAdapter != null && page_no > 1)
//                        if (adapter != null && pageNo > 1)
                                        {
                                            System.out.println("page if  ");
                                            patentAwardedAdapter.notifyDataSetChanged();
                                        } else {
                                            System.out.println("page else  ");

                                            patentAwardedAdapter = new PatentAwardedAdapter(activity, patentAwaredPojo, listall, isChecked);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                            lv_patent_award.setAdapter(patentAwardedAdapter);

                                            lv_patent_award.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount)
                                                {

                                                    System.out.println("item counts :::::::::::: " + totalItemsCount);
                                                    System.out.println("page number ::::::::::: " + page);

                                                    PAGE_NUMBER = page;

                                                    ApprovePatentAwarded(page, isChecked_API);
//}
                                                    return true;
                                                }
                                            });

                                        }


                                    }

                                } else {
                                    patentAwardedAdapter = new PatentAwardedAdapter(activity, patentAwaredPojo, listall, isChecked);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                    lv_patent_award.setAdapter(patentAwardedAdapter);
                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(activity, "No Records Found");
                                }

                            }
                        }
                    }
                }
                else
                    {
                    if (page_no == 1)
                    {

                        listall.clear();
                        DialogUtils.Show_Toast(activity, "No Records Found");
                        patentAwardedAdapter = new PatentAwardedAdapter(activity, patentAwaredPojo, listall, isChecked);
                        patentAwardedAdapter.notifyDataSetChanged();
                        lv_patent_award.setAdapter(patentAwardedAdapter);

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

    @Override
    protected void onResume() {
        super.onResume();

        if (com.infinity.kich.Leave.Activity.PatentAwardedDetail.is_back_jounral_pub)
        {
            com.infinity.kich.Leave.Activity.PatentAwardedDetail.is_back_jounral_pub = false;
        }
        else {
            cbcheck.setChecked(false);
            listall = new ArrayList<>();
            listall.clear();
            patentAwardedAdapter = null;
            System.out.println("on resume call patent awarded::::::::::::::::::::::::::::");
            ApprovePatentAwarded(1, isChecked_API);
        }


    }

    public static List<PatentAwaredPojo.DataBean> listall;

    private void initView() {
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        queue = Volley.newRequestQueue(PatentAwarded.this);
        txtenrollno = (CustomBoldTextView) findViewById(R.id.txt_enroll_no);
        txtsrno = (CustomBoldTextView) findViewById(R.id.txt_sr_no);
        cbcheck = (Switch) findViewById(R.id.cb_check);
        llmainapproveheadderleave = (LinearLayout) findViewById(R.id.ll_main_approve_headder_leave);
        llmainheder = (LinearLayout) findViewById(R.id.ll_main_heder);
        lv_patent_award = (ListView) findViewById(R.id.lv_patent_award);

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
