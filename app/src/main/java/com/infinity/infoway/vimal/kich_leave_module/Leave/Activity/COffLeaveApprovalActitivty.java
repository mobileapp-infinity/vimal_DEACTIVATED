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
import com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter.CoffLeaveAdapter;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.CoffPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.EndlessScrollListener;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;

import java.util.ArrayList;
import java.util.List;

public class COffLeaveApprovalActitivty extends AppCompatActivity {

    /**
     * Coff Approval
     */
    private CustomBoldTextView txt_enroll_no;
    /**
     * Pending/All
     */


    private CustomBoldTextView txt_sr_no;
    public  static Switch cb_check;
    private LinearLayout ll_main_approve_headder_leave;
    private LinearLayout ll_main_heder;
    static ListView lv_coff;
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
    static int PAGE_NUMBER;
    static CoffLeaveAdapter coffLeaveAdapter;
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
    static RequestQueue queue;
    static  Activity activity;
    static MySharedPrefereces mySharedPrefereces;
    static Boolean isChecked_API = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_activity_coff_leave_approval_actitivty);
        queue = Volley.newRequestQueue(this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());

        activity = COffLeaveApprovalActitivty.this;
        cb_check = (Switch) findViewById(R.id.cb_check);
        cb_check.setChecked(false);
        listall = new ArrayList<>();
        listall.clear();
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
        txt_act.setText("Coff Leave Approval");

        CoffApproval(1, isChecked_API);
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                listall = new ArrayList<>();
                listall.clear();
                coffPojo = new CoffPojo();
                //  lvapproveleave.scrollTo(0,0);

                CoffApproval(1, isChecked);

//                System.out.println("page number in on checked chaneg :::::::: " + PAGE_NUMBER);
//                ApprovleaveListingAPICall(PAGE_NUMBER, isChecked);

                if (isChecked)
                {
                    isChecked_API = true;
                }
                else
                {
                    isChecked_API = false;
                }
            }
        });


    }

    public static List<CoffPojo.DataBean> listall;
    public static CoffPojo coffPojo;

    public static void CoffApproval(final int PageNo, final Boolean Isc)
    {
        String url;
        queue = Volley.newRequestQueue(activity);
        if (Isc)
        {

            url = URLS.compensatory_leave_approve_list + "&user_id=" + mySharedPrefereces.getUserID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "2" + "";
        }
        else
        {

            url = URLS.compensatory_leave_approve_list + "&user_id=" + mySharedPrefereces.getUserID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "1" + "";
        }
        url = url.replace(" ","%20");
        System.out.println("compensatory_leave_approve_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";

                System.out.println("response size in leave listing :::::::::::::::::::::::::::::::::::::: " + response.length());


                if (response.length() > 10)
                {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    coffPojo = new CoffPojo();
                    coffPojo = gson.fromJson(response, CoffPojo.class);
                    System.out.println("approve leave listing data size :::::::::::::::::::::::::::::::::::::" + coffPojo.getData().size());

                    listall.addAll(coffPojo.getData());

                    if (coffPojo != null)
                    {
                        if (coffPojo.getData() != null)
                        {
                            if (coffPojo.getData().get(0) != null)
                            {
                                if (coffPojo.getData().size() > 0)
                                {
                                    if (lv_coff!= null)
                                    {
                                        if (coffLeaveAdapter!= null && PageNo > 1)
//                        if (adapter != null && pageNo > 1)
                                        {
                                            System.out.println("page if  ");
                                            coffLeaveAdapter.notifyDataSetChanged();
                                        }
                                        else
                                        {
                                            System.out.println("page else  ");

                                            coffLeaveAdapter = new CoffLeaveAdapter(activity, coffPojo, listall, Isc);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                            lv_coff.setAdapter(coffLeaveAdapter);

                                            lv_coff.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount)
                                                {
                                                    System.out.println("item counts :::::::::::: " + totalItemsCount);
                                                    System.out.println("page number ::::::::::: " + page);
//if(page>=1) {
                                                    PAGE_NUMBER = page;

                                                    CoffApproval(page, isChecked_API);
//}
                                                    return true;
                                                }
                                            });

                                        }


                                    }

                                }
                                else
                                {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(activity, "No Records Found");
                                }

                            }
                        }
                    }
                }
                else
                {
                    if (PageNo == 1)
                    {

                        listall.clear();
                        DialogUtils.Show_Toast(activity, "No Records Found");
                        coffLeaveAdapter = new CoffLeaveAdapter(activity, coffPojo, listall, Isc);
                        coffLeaveAdapter.notifyDataSetChanged();
                        lv_coff.setAdapter(coffLeaveAdapter);

                    }
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                DialogUtils.Show_Toast(activity, "Please Try Again Later");
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
        txt_enroll_no = (CustomBoldTextView) findViewById(R.id.txt_enroll_no);
        txt_sr_no = (CustomBoldTextView) findViewById(R.id.txt_sr_no);
        cb_check = (Switch) findViewById(R.id.cb_check);
        ll_main_approve_headder_leave = (LinearLayout) findViewById(R.id.ll_main_approve_headder_leave);
        ll_main_heder = (LinearLayout) findViewById(R.id.ll_main_heder);
        lv_coff = (ListView) findViewById(R.id.lv_coff);
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
        tv_emp_code.setText(mySharedPrefereces.getEmpCode());
    }
}
