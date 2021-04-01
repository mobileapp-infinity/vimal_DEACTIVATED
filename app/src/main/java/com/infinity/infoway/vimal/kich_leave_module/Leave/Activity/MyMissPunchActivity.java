package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

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
import com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter.MyMissPunchAdapter;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.MissPunchListPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;

import java.util.ArrayList;
import java.util.List;

public class MyMissPunchActivity extends AppCompatActivity
{
    CustomBoldTextView txt_act;
    ImageView iv_back;
    ListView lvmisspunch;
    MyMissPunchAdapter myMissPunchAdapter;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    MySharedPrefereces mySharedPrefereces;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_miss_punch);

        try {
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
            txt_act.setText("View Miss Punch");
            initView();

            MyMissPunchViewAPICall(1);
        }catch (Throwable ex){
         ex.printStackTrace();
        }
    }

    List<MissPunchListPojo.DataBean> listall;
    MissPunchListPojo missPunchListPojo;

    private void MyMissPunchViewAPICall(final int PageNo)
    {
        String url = URLS.Get_miss_punch_request_list + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "";
        url = url.replace(" ","%20");
        System.out.println("Get_miss_punch_request_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
//                DialogUtils.hideProgressDialog();

                System.out.println("response of Get_miss_punch_request_list !!!!!!!!!!! " + response);
                response = response + "";

                if (response.length() > 5)
                {
                    response = "{\"Data\":" + response + "}";

                    System.out.println("sucess response Get_miss_punch_request_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    missPunchListPojo = gson.fromJson(response, MissPunchListPojo.class);
                    listall.addAll(missPunchListPojo.getData());

                    if (missPunchListPojo != null)
                    {
                        if (missPunchListPojo.getData() != null)
                        {
                            if (missPunchListPojo.getData().get(0) != null)
                            {
                                if (missPunchListPojo.getData().size() > 0)
                                {

                                    if (lvmisspunch != null)
                                    {

                                        if (myMissPunchAdapter != null && PageNo > 0)
//                        if (adapter != null && pageNo > 1)
                                        {
                                            myMissPunchAdapter.notifyDataSetChanged();
                                        }
                                        else
                                        {

                                            myMissPunchAdapter = new MyMissPunchAdapter(MyMissPunchActivity.this, missPunchListPojo, listall);
                                            myMissPunchAdapter.notifyDataSetChanged();
                                            lvmisspunch.setAdapter(myMissPunchAdapter);
                                        }

                                        int pg_ = PageNo + 1;

                                        System.out.println("*********miss punch view  *******again called+++++++++++ " + pg_ + "");

                                        //******** for page >1
                                        MyMissPunchViewAPICall(pg_);

                                        myMissPunchAdapter = new MyMissPunchAdapter(MyMissPunchActivity.this, missPunchListPojo, listall);
                                        lvmisspunch.setAdapter(myMissPunchAdapter);

                                    }

                                }
                                else
                                {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(MyMissPunchActivity.this, "No Records Found");
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
                        DialogUtils.Show_Toast(MyMissPunchActivity.this, "No Records Found");
                        myMissPunchAdapter = new MyMissPunchAdapter(MyMissPunchActivity.this, missPunchListPojo, listall);
                        myMissPunchAdapter.notifyDataSetChanged();
                        lvmisspunch.setAdapter(myMissPunchAdapter);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(MyMissPunchActivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void initView() {
        queue = Volley.newRequestQueue(MyMissPunchActivity.this);
        listall = new ArrayList<>();
        lvmisspunch = (ListView) findViewById(R.id.lv_miss_punch);

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
