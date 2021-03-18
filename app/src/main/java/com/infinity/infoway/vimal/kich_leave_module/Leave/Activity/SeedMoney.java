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
import com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter.SeedMoneyAdapter;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.SeedMoneyPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.EndlessScrollListener;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;

import java.util.ArrayList;
import java.util.List;
public class SeedMoney extends AppCompatActivity {

    /**
     * Seed Money
     */
    private CustomBoldTextView txt_enroll_no;
    /**
     * Pending/All
     */
    private CustomBoldTextView txt_sr_no;
    private Switch cb_check;
    private LinearLayout ll_main_approve_headder_leave;
    private LinearLayout ll_main_heder;
    private static ListView lv_seed_money;
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

    static Activity activity;
    static   Boolean isChecked_API = false;
    static  RequestQueue queue;
    static  MySharedPrefereces mySharedPrefereces;
  public   static SeedMoneyPojo seedMoneyPojo;
    static SeedMoneyAdapter seedMoneyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_money);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        initView();
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("Seed Money");

        activity = SeedMoney.this;
        seedMoneyAdapter = null;
        listall = new ArrayList<>();
        listall.clear();
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listall = new ArrayList<>();
                listall.clear();
                seedMoneyPojo = new SeedMoneyPojo();
                //  lvapproveleave.scrollTo(0,0);

                SeedMoneyListing(1, isChecked);


//                ApprovleaveListingAPICall(PAGE_NUMBER, isChecked);

                if (isChecked) {
                    isChecked_API = true;
                } else {
                    isChecked_API = false;
                }
            }
        });
    }

    public static List<SeedMoneyPojo.DataBean> listall;
    public static void SeedMoneyListing(final int page_no, final boolean isChecked)
    {


        String url;
        queue = Volley.newRequestQueue(activity);

        if (isChecked)
        {
            url = URLS.Get_Seed_Money_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "2" + "&id=" + "0" + "";
        } else {

            url = URLS.Get_Seed_Money_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "1" + "&id=" + "0" + "";
        }
        url = url.replace(" ", "%20");

        System.out.println("Get_Seed_Money_list URL on resume ::::::  " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                System.out.println("response size in leave listing Get_Seed_Money_list:::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    seedMoneyPojo = new SeedMoneyPojo();
                    seedMoneyPojo = gson.fromJson(response, SeedMoneyPojo.class);
                    System.out.println("Get_Seed_Money_list listing data size :::::::::::::::::::::::::::::::::::::" + seedMoneyPojo.getData().size());

                    listall.addAll(seedMoneyPojo.getData());

                    if (seedMoneyPojo != null) {
                        if (seedMoneyPojo.getData() != null) {
                            if (seedMoneyPojo.getData().get(0) != null) {
                                if (seedMoneyPojo.getData().size() > 0) {

                                    if (lv_seed_money != null) {

                                        if (seedMoneyAdapter != null && page_no > 1)
//                        if (adapter != null && pageNo > 1)
                                        {
                                            System.out.println("page if  ");
                                            seedMoneyAdapter.notifyDataSetChanged();
                                        } else {
                                            System.out.println("page else  ");

                                            seedMoneyAdapter = new SeedMoneyAdapter(activity, seedMoneyPojo, listall, isChecked);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                            lv_seed_money.setAdapter(seedMoneyAdapter);

                                            lv_seed_money.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount)
                                                {

                                                    System.out.println("item counts :::::::::::: " + totalItemsCount);
                                                    System.out.println("page number ::::::::::: " + page);



                                                    SeedMoneyListing(page, isChecked_API);
//}
                                                    return true;
                                                }
                                            });

                                        }


                                    }

                                } else {
                                    seedMoneyAdapter = new SeedMoneyAdapter(activity, seedMoneyPojo, listall, isChecked);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                    lv_seed_money.setAdapter(seedMoneyAdapter);
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
                        seedMoneyAdapter = new SeedMoneyAdapter(activity, seedMoneyPojo, listall, isChecked);
                        seedMoneyAdapter.notifyDataSetChanged();
                        lv_seed_money.setAdapter(seedMoneyAdapter);

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

        if (SeedMoneyDetail.is_back_seed_money)
        {
            SeedMoneyDetail.is_back_seed_money =false;
        }
        else {
            listall = new ArrayList<>();
            listall.clear();
            seedMoneyPojo =new SeedMoneyPojo();
            seedMoneyAdapter = null;
            cb_check.setChecked(false);
            System.out.println("on resume call patent awarded::::::::::::::::::::::::::::");
            SeedMoneyListing(1, isChecked_API);
        }
    }

    private void initView() {
        mySharedPrefereces =new MySharedPrefereces(getApplicationContext());
        queue = Volley.newRequestQueue(this);
        txt_enroll_no = (CustomBoldTextView) findViewById(R.id.txt_enroll_no);
        txt_sr_no = (CustomBoldTextView) findViewById(R.id.txt_sr_no);
        cb_check = (Switch) findViewById(R.id.cb_check);
        ll_main_approve_headder_leave = (LinearLayout) findViewById(R.id.ll_main_approve_headder_leave);
        ll_main_heder = (LinearLayout) findViewById(R.id.ll_main_heder);
        lv_seed_money = (ListView) findViewById(R.id.lv_seed_money);
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
