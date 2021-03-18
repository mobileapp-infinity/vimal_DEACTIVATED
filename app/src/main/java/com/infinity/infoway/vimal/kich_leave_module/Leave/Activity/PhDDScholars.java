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
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Adapter.Phd_scolars_awardedAdapter;
import com.infinity.kich.Leave.Pojo.PhdScolarGuidedPojo;

import java.util.ArrayList;
import java.util.List;

public class PhDDScholars extends AppCompatActivity {

    CustomBoldTextView txtenrollno;
    CustomBoldTextView txtsrno;
    Switch cbcheck;
    LinearLayout llmainapproveheadderleave;
    LinearLayout llmainheder;
    static ListView lvphdscolars;

    static  MySharedPrefereces mySharedPrefereces;
    ImageView iv_back;
    CustomBoldTextView txt_act;
    static int PAGE_NUMBER;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    public static PhdScolarGuidedPojo phdScolarGuidedPojo;
    static Boolean isChecked_API = false;
    static RequestQueue queue;
    static Phd_scolars_awardedAdapter phdScolarsAwardedAdapter;
    static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph_ddscholars);

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
        txt_act.setText("PhDD Scholars");
        initView();


        activity = PhDDScholars.this;
        phdScolarsAwardedAdapter = null;
        listall = new ArrayList<>();
        listall.clear();
        cbcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listall = new ArrayList<>();
                listall.clear();
                phdScolarGuidedPojo = new PhdScolarGuidedPojo();

                Phddscholarsapprovelist(1,isChecked);

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


    public static List<PhdScolarGuidedPojo.DataBean> listall;

    public static void Phddscholarsapprovelist(final int page_no, boolean isc)
    {

        String url;
        queue = Volley.newRequestQueue(activity);

        if (isc)
        {
            url = URLS.Get_PhD_Scholars_Guided_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "2" + "&id=" + "0" + "";
        } else {

            url = URLS.Get_PhD_Scholars_Guided_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "1" + "&id=" + "0" + "";
        }
        url = url.replace(" ", "%20");

        System.out.println("Get_PhD_Scholars_Guided_list URL on resume ::::::  " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                System.out.println("response size in leave listing Get_PhD_Scholars_Guided_list:::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    phdScolarGuidedPojo = new PhdScolarGuidedPojo();
                    phdScolarGuidedPojo = gson.fromJson(response, PhdScolarGuidedPojo.class);
                    System.out.println("Get_PhD_Scholars_Guided_list listing data size :::::::::::::::::::::::::::::::::::::" + phdScolarGuidedPojo.getData().size());

                    listall.addAll(phdScolarGuidedPojo.getData());

                    if (phdScolarGuidedPojo != null)
                    {
                        if (phdScolarGuidedPojo.getData() != null)
                        {
                            if (phdScolarGuidedPojo.getData().get(0) != null)
                            {
                                if (phdScolarGuidedPojo.getData().size() > 0)
                                {

                                    if (lvphdscolars != null)
                                    {

                                        if (phdScolarsAwardedAdapter != null && page_no > 1)
                                        {
                                            System.out.println("page if  ");
                                            phdScolarsAwardedAdapter.notifyDataSetChanged();
                                        } else {
                                            System.out.println("page else  ");

                                            phdScolarsAwardedAdapter = new Phd_scolars_awardedAdapter(activity, phdScolarGuidedPojo, listall, isChecked_API);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                            lvphdscolars.setAdapter(phdScolarsAwardedAdapter);

                                            lvphdscolars.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount)
                                                {

                                                    System.out.println("item counts :::::::::::: " + totalItemsCount);
                                                    System.out.println("page number ::::::::::: " + page);

                                                    PAGE_NUMBER = page;

                                                    Phddscholarsapprovelist(page, isChecked_API);
//}
                                                    return true;
                                                }
                                            });

                                        }


                                    }

                                } else {
                                    phdScolarsAwardedAdapter = new Phd_scolars_awardedAdapter(activity, phdScolarGuidedPojo, listall, isChecked_API);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                    lvphdscolars.setAdapter(phdScolarsAwardedAdapter);
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
                        phdScolarsAwardedAdapter = new Phd_scolars_awardedAdapter(activity, phdScolarGuidedPojo, listall, isChecked_API);
                        phdScolarsAwardedAdapter.notifyDataSetChanged();
                        lvphdscolars.setAdapter(phdScolarsAwardedAdapter);

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
    protected void onResume()
    {
        super.onResume();

        if (com.infinity.kich.Leave.Activity.PhDDScholarsDetail.is_back_phd)
        {
            com.infinity.kich.Leave.Activity.PhDDScholarsDetail.is_back_phd = false;

        }

        else
        {
        listall = new ArrayList<>();
        listall.clear();
        phdScolarsAwardedAdapter=null;
        phdScolarGuidedPojo = new PhdScolarGuidedPojo();
        cbcheck.setChecked(false);
        Phddscholarsapprovelist(1, isChecked_API);

        }

    }
    private void initView()
    {
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        queue = Volley.newRequestQueue(this);
        txtenrollno = (CustomBoldTextView) findViewById(R.id.txt_enroll_no);
        txtsrno = (CustomBoldTextView) findViewById(R.id.txt_sr_no);
        cbcheck = (Switch) findViewById(R.id.cb_check);
        llmainapproveheadderleave = (LinearLayout) findViewById(R.id.ll_main_approve_headder_leave);
        llmainheder = (LinearLayout) findViewById(R.id.ll_main_heder);
        lvphdscolars = (ListView) findViewById(R.id.lv_phd_scolars);


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
}
