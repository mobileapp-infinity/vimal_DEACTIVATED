package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

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
import com.infinity.kich.Leave.Adapter.MissPunchapprovalAdapter;
import com.infinity.kich.Leave.Pojo.MissPunchApprovePojo;

import java.util.ArrayList;
import java.util.List;

public class MissPunchApproval extends AppCompatActivity
{
    CustomBoldTextView txt_act;
    ImageView iv_back;
    static ListView lvmisspunchapproval;
    static MissPunchapprovalAdapter missPunchapprovalAdapter;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    static MySharedPrefereces mySharedPrefereces;
    static Activity activity;
    Switch cb_check;
    static Boolean is_check_API = false;
    static RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miss_punch_approval);

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
        txt_act.setText("Miss Punch Approval");

        activity = MissPunchApproval.this;
        initView();
        cb_check = (Switch) findViewById(R.id.cb_check);
        cb_check.setChecked(false);
//        MissPunchApprovalAPiCall(1, true);
//        MissPunchApprovalAPiCall(1, is_check_API);
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
            {
                MissPunchApprovalAPiCall(1, isChecked);
                listall.clear();
                if (isChecked)
                {
                    is_check_API = true;
                }
                else
                {
                    is_check_API = false;
                }
            }
        });

        lvmisspunchapproval = (ListView) findViewById(R.id.lv_miss_punch_approval);
//        missPunchapprovalAdapter = new MissPunchapprovalAdapter(MissPunchApproval.this);
//        lvmisspunchapproval.setAdapter(missPunchapprovalAdapter);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        listall = new ArrayList<>();


    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (com.infinity.kich.Leave.Activity.MissPunchApprovedActivity.is_back_miss_punch_approval)
        {
            com.infinity.kich.Leave.Activity.MissPunchApprovedActivity.is_back_miss_punch_approval = false;

        }

        else
        {
            listall = new ArrayList<>();
            listall.clear();
            missPunchApprovePojo = new MissPunchApprovePojo();
            cb_check.setChecked(false);
            MissPunchApprovalAPiCall(1, is_check_API);

        }

    }

    static List<MissPunchApprovePojo.DataBean> listall;
    static MissPunchApprovePojo missPunchApprovePojo;

    //************ listing of  miss punch approval **************
    public static void MissPunchApprovalAPiCall(final int PageNo, final Boolean Isc)
    {

        String url;
        queue = Volley.newRequestQueue(activity);

        if (Isc)
        {
            url = URLS.Get_miss_pucn_approve_list + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "2" + "";
        }
        else
        {

            url = URLS.Get_miss_pucn_approve_list + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "1" + "";
        }
        url = url.replace(" ","%20");
        System.out.println("Get_miss_pucn_approve_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
//                DialogUtils.hideProgressDialog();

                // System.out.println("response of Get_miss_pucn_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                if (response.length() > 10)
                {

                    System.out.println("response body size :::::::::::::::" + response.length());
                    response = "{\"Data\":" + response + "}";

                    //   System.out.println("sucess response Get_miss_pucn_approve_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    missPunchApprovePojo = gson.fromJson(response, MissPunchApprovePojo.class);
                    listall.addAll(missPunchApprovePojo.getData());

                    if (missPunchApprovePojo != null)
                    {
                        if (missPunchApprovePojo.getData() != null)
                        {
                            if (missPunchApprovePojo.getData().get(0) != null)
                            {
                                if (missPunchApprovePojo.getData().size() > 0)
                                {

                                    if (lvmisspunchapproval != null)
                                    {

                                        if (missPunchapprovalAdapter != null && PageNo > 1)
//                        if (adapter != null && pageNo > 1)
                                        {

                                            System.out.println("call if  PageNo > 0 &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                                            missPunchapprovalAdapter.notifyDataSetChanged();
                                        }
                                        else
                                        {

                                            System.out.println("call  else PageNo > 0 &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

                                            missPunchapprovalAdapter = new MissPunchapprovalAdapter(activity, missPunchApprovePojo, listall);
                                            //  missPunchapprovalAdapter.notifyDataSetChanged();
                                            lvmisspunchapproval.setAdapter(missPunchapprovalAdapter);

                                            lvmisspunchapproval.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount)
                                                {

                                                    MissPunchApprovalAPiCall(page, is_check_API);

                                                    return true;
                                                }
                                            });
                                        }
                                      /*  System.out.println("call normally  &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                                        int pg_ = PageNo + 1;

                                        System.out.println("*********Get_miss_pucn_approve_list  *******again called+++++++++++  ===== " + pg_ + "");

                                        //******** for page >1
                                        MissPunchApprovalAPiCall(pg_, Isc);

                                        missPunchapprovalAdapter = new MissPunchapprovalAdapter(MissPunchApproval.this, missPunchApprovePojo, listall);
                                        lvmisspunchapproval.setAdapter(missPunchapprovalAdapter);
*/
                                    }

                                }
                                else
                                {
                                    System.out.println("call data size is >0 else &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(activity, "No Records Found");
                                }

                            }
                        }
                    }
                } else {
                    if (PageNo == 1) {
                        System.out.println("call data size is <5 else &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        listall.clear();
                        DialogUtils.Show_Toast(activity, "No Records Found");
                        missPunchapprovalAdapter = new MissPunchapprovalAdapter(activity, missPunchApprovePojo, listall);
                        missPunchapprovalAdapter.notifyDataSetChanged();
                        lvmisspunchapproval.setAdapter(missPunchapprovalAdapter);
                    }

                    /*else {
                        listall.clear();
                        DialogUtils.Show_Toast(MissPunchApproval.this, "No Records Found");
                        missPunchapprovalAdapter = new MissPunchapprovalAdapter(MissPunchApproval.this, missPunchApprovePojo, listall);
                        missPunchapprovalAdapter.notifyDataSetChanged();
                        lvmisspunchapproval.setAdapter(missPunchapprovalAdapter);
                    }*/
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

        queue = Volley.newRequestQueue(MissPunchApproval.this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
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
