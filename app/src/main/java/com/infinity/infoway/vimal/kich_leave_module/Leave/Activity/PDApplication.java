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
import android.widget.RelativeLayout;
import android.widget.Switch;

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
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.EndlessScrollListener;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Adapter.PDAdapter;
import com.infinity.kich.Leave.Pojo.PDAppPojo;

import java.util.ArrayList;
import java.util.List;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
public class PDApplication extends AppCompatActivity
{

    /**
     * PD Application
     */
    private CustomBoldTextView txt_enroll_no;
    /**
     * Pending/All
     */
    private CustomBoldTextView txt_sr_no;
    private Switch cb_check;
    private LinearLayout ll_main_approve_headder_leave;
    private LinearLayout ll_main_heder;
    private ListView lv_pd;
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    private ImageView iv_todays_inout;
    private CustomTextView tv_count_total_pending;
    /**
     *
     */
    private CustomBoldTextView tv_emp_code;
    private CustomTextView tv_total_pending_count;
    /**
     *
     */
    private CustomBoldTextView tv_version;
    /**
     *
     */
    private CustomBoldTextView tv_version_code;
    private LinearLayout ll_bottom;



    static MySharedPrefereces mySharedPrefereces;

    static int PAGE_NUMBER;
    public static PDAppPojo pdAppPojo;
    static Boolean isChecked_API = false;
    static RequestQueue queue;
    static PDAdapter pdAdapter;
    static Activity activity;
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdapplication);
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
        txt_act.setText("PD Application");

        activity = PDApplication.this;
        pdAdapter = null;
        listall = new ArrayList<>();
        listall.clear();
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listall = new ArrayList<>();
                listall.clear();
                pdAppPojo = new PDAppPojo();
                //  lvapproveleave.scrollTo(0,0);

                PDAppApproval(1, isChecked);

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
    protected void onResume()
    {
        super.onResume();


        if (com.infinity.kich.Leave.Activity.PDApplicationDetail.is_back_pd_app)
        {
            com.infinity.kich.Leave.Activity.PDApplicationDetail.is_back_pd_app = false;
        }
        else
        {
            listall = new ArrayList<>();
            listall.clear();
            pdAppPojo = new PDAppPojo();
            cb_check.setChecked(false);
            System.out.println("on resume call patent awarded::::::::::::::::::::::::::::");
            PDAppApproval(1, isChecked_API);

        }

    }

    private void PDAppApproval(final int page_no, final boolean isChecked)
    {
        String url;
        queue = Volley.newRequestQueue(activity);

        if (isChecked)
        {
            url = URLS.Get_PD_Application_list + "&user_id=" + mySharedPrefereces.getUserID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "2" + "&id=" + "0" + "";
        } else {

            url = URLS.Get_PD_Application_list + "&user_id=" + mySharedPrefereces.getUserID() +  "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "1" + "&id=" + "0" + "";
        }
        url = url.replace(" ", "%20");

        System.out.println("Get_PD_Application_list URL on resume ::::::  " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                System.out.println("response size in leave listing Get_PD_Application_list:::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10)
                {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    pdAppPojo = new PDAppPojo();
                    pdAppPojo = gson.fromJson(response, PDAppPojo.class);
                    System.out.println("Get_PD_Application_list listing data size :::::::::::::::::::::::::::::::::::::" + pdAppPojo.getData().size());

                    listall.addAll(pdAppPojo.getData());

                    if (pdAppPojo != null)
                    {
                        if (pdAppPojo.getData() != null)
                        {
                            if (pdAppPojo.getData().get(0) != null)
                            {
                                if (pdAppPojo.getData().size() > 0)
                                {

                                    if (lv_pd != null)
                                    {

                                        if (pdAdapter != null && page_no > 1)
//                        if (adapter != null && pageNo > 1)
                                        {
                                            System.out.println("page if  ");
                                            pdAdapter.notifyDataSetChanged();
                                        } else {
                                            System.out.println("page else  ");

                                            pdAdapter = new PDAdapter(activity, pdAppPojo, listall, isChecked);
                                            lv_pd.setAdapter(pdAdapter);

                                            lv_pd.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount)
                                                {

                                                    System.out.println("item counts :::::::::::: " + totalItemsCount);
                                                    System.out.println("page number ::::::::::: " + page);

                                                    PAGE_NUMBER = page;

                                                    PDAppApproval(page, isChecked_API);
//}
                                                    return true;
                                                }
                                            });

                                        }


                                    }

                                } else {
                                    pdAdapter = new PDAdapter(activity, pdAppPojo, listall, isChecked);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                    lv_pd.setAdapter(pdAdapter);
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
                        pdAdapter = new PDAdapter(activity, pdAppPojo, listall, isChecked);
                        pdAdapter.notifyDataSetChanged();
                        lv_pd.setAdapter(pdAdapter);

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

    public static List<PDAppPojo.DataBean> listall;
    private void initView() {

        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        txt_enroll_no = (CustomBoldTextView) findViewById(R.id.txt_enroll_no);
        txt_sr_no = (CustomBoldTextView) findViewById(R.id.txt_sr_no);
        cb_check = (Switch) findViewById(R.id.cb_check);
        ll_main_approve_headder_leave = (LinearLayout) findViewById(R.id.ll_main_approve_headder_leave);
        ll_main_heder = (LinearLayout) findViewById(R.id.ll_main_heder);
        lv_pd = (ListView) findViewById(R.id.lv_pd);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        rel = (RelativeLayout) findViewById(R.id.rel);
        toolbar_act = (Toolbar) findViewById(R.id.toolbar_act);
        toolbarContainer = (CoordinatorLayout) findViewById(R.id.toolbarContainer);
        iv_todays_inout = (ImageView) findViewById(R.id.iv_todays_inout);
        tv_count_total_pending = (CustomTextView) findViewById(R.id.tv_count_total_pending);
        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_total_pending_count = (CustomTextView) findViewById(R.id.tv_total_pending_count);
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
        tv_emp_code.setText(mySharedPrefereces.getEmpCode());
    }
}
