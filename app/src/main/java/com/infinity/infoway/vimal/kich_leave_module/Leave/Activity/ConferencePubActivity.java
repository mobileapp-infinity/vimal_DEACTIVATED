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
import com.infinity.kich.Leave.Adapter.ConfarancePubAdapter;
import com.infinity.kich.Leave.Pojo.ConfarancePubPojo;

import java.util.ArrayList;
import java.util.List;

public class ConferencePubActivity extends AppCompatActivity {

    static Boolean isChecked_API = false;
    static RequestQueue queue;
    static MySharedPrefereces mySharedPrefereces;

    CustomBoldTextView txtenrollno;
    CustomBoldTextView txtsrno;
    Switch cb_check;
    LinearLayout llmainapproveheadderleave;
    LinearLayout llmainheder;
    static ListView lvconfarancepub;
    ImageView iv_back;
    CustomBoldTextView txt_act;
    static Activity activity;
    static ConfarancePubAdapter confarancePubAdapter;
    static int PAGE_NUMBER;
    public static ConfarancePubPojo confarancePubPojo;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_activity_conference_pub);

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
        txt_act.setText("Conference Pub");
        initView();

        activity = ConferencePubActivity.this;
        cb_check = (Switch) findViewById(R.id.cb_check);
        cb_check.setChecked(false);
        listall = new ArrayList<>();
        listall.clear();
        confarancePubAdapter = null;


        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                listall = new ArrayList<>();
                listall.clear();
                confarancePubPojo = new ConfarancePubPojo();
                //  lvapproveleave.scrollTo(0,0);

                ApproveConfarancePub(1, isChecked);

                System.out.println("page number in on checked chaneg :::::::: " + PAGE_NUMBER);
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

    private void initView() {
        queue = Volley.newRequestQueue(this);

        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        txtenrollno = (CustomBoldTextView) findViewById(R.id.txt_enroll_no);
        txtsrno = (CustomBoldTextView) findViewById(R.id.txt_sr_no);
        cb_check = (Switch) findViewById(R.id.cb_check);
        llmainapproveheadderleave = (LinearLayout) findViewById(R.id.ll_main_approve_headder_leave);
        llmainheder = (LinearLayout) findViewById(R.id.ll_main_heder);
        lvconfarancepub = (ListView) findViewById(R.id.lv_confarance_pub);

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
    protected void onResume()
    {
        super.onResume();

        if (com.infinity.kich.Leave.Activity.ConferencePubApproveReject.is_back_con_approval)
        {
            com.infinity.kich.Leave.Activity.ConferencePubApproveReject.is_back_con_approval = false;

        }

        else
        {
            listall = new ArrayList<>();
            listall.clear();
            confarancePubPojo = new ConfarancePubPojo();
            cb_check.setChecked(false);
            ApproveConfarancePub(1, isChecked_API);

        }

    }
    public static List<ConfarancePubPojo.DataBean> listall;

    public static void ApproveConfarancePub(final int page_no, final Boolean Isc)
    {

        String url;
        queue = Volley.newRequestQueue(activity);

        if (Isc)
        {
            // url = URLS.Get_leave_approve_list + "&user_id=" + "1" + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "2" + "";
            url = URLS.Get_publication_conference_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "2" + "&id="+"0"+"";
        }
        else
            {

            // url = URLS.Get_leave_approve_list + "&user_id=" + "1" + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "1" + "";
            url = URLS.Get_publication_conference_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "1" + "&id="+"0"+"";
        }
        url = url.replace(" ", "%20");

        System.out.println("Get_publication_conference_list URL on resume ::::::  " + url + "");
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
                    confarancePubPojo = new ConfarancePubPojo();
                    confarancePubPojo = gson.fromJson(response, ConfarancePubPojo.class);
                    System.out.println("Get_publication_conference_list listing data size :::::::::::::::::::::::::::::::::::::" + confarancePubPojo.getData().size());

                    listall.addAll(confarancePubPojo.getData());

                    if (confarancePubPojo != null)
                    {
                        if (confarancePubPojo.getData() != null)
                        {
                            if (confarancePubPojo.getData().get(0) != null)
                            {
                                if (confarancePubPojo.getData().size() > 0)
                                {

                                    if (lvconfarancepub != null)
                                    {

                                        if (confarancePubAdapter != null && page_no > 1)
//                        if (adapter != null && pageNo > 1)
                                        {
                                            System.out.println("page if  ");
                                            confarancePubAdapter.notifyDataSetChanged();
                                        }
                                        else
                                            {
                                            System.out.println("page else  ");

                                            confarancePubAdapter = new ConfarancePubAdapter(activity, confarancePubPojo, listall, Isc);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                            lvconfarancepub.setAdapter(confarancePubAdapter);

                                            lvconfarancepub.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount)
                                                {

                                                    System.out.println("item counts :::::::::::: " + totalItemsCount);
                                                    System.out.println("page number ::::::::::: " + page);

                                                    PAGE_NUMBER = page;

                                                    ApproveConfarancePub(page, isChecked_API);
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

                                }
                                else
                                    {
                                    confarancePubAdapter = new ConfarancePubAdapter(activity, confarancePubPojo, listall, Isc);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                    lvconfarancepub.setAdapter(confarancePubAdapter);
                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(activity, "No Records Found");
                                }

                            }
                        }
                    }
                } else {
                    if (page_no == 1) {

                        listall.clear();
                        DialogUtils.Show_Toast(activity, "No Records Found");
                        confarancePubAdapter = new ConfarancePubAdapter(activity, confarancePubPojo, listall, Isc);
                        confarancePubAdapter.notifyDataSetChanged();
                        lvconfarancepub.setAdapter(confarancePubAdapter);

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
}
