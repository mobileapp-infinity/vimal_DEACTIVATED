package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.EndlessScrollListener;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Adapter.ViewLeaveListingAdapter;
import com.infinity.kich.Leave.Pojo.Leave_list_pojo;

import java.util.ArrayList;
import java.util.List;
public class ViewLeaveListingActivity extends AppCompatActivity {

    ListView lvviewleave;
    CustomBoldTextView txt_act;
    ImageView iv_back;
    MySharedPrefereces mySharedPreferenses;
    ViewLeaveListingAdapter viewLeaveListingAdapter;
    RequestQueue queue;
    //    Boolean is_checked_final = false;
    Boolean b = true;
    Boolean is_check_API = false;
    FloatingActionButton fab;
    Switch cb_check;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_leave_listing);
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
        txt_act.setText("View Leaves");
        mySharedPreferenses = new MySharedPrefereces(getApplicationContext());
        initView();
        listall = new ArrayList<>();
        listall.clear();
        cb_check = (Switch) findViewById(R.id.cb_check);
        cb_check.setChecked(false);
        ViewLeaveApiCall(1, is_check_API);

        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {


                listall = new ArrayList<>();
                listall.clear();
                ViewLeaveApiCall(1, isChecked);

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
        lvviewleave = (ListView) findViewById(R.id.lv_view_leave);
        listall = new ArrayList<>();



    }

    List<Leave_list_pojo.DataBean> listall;
    Leave_list_pojo leave_list_pojo;

    private void ViewLeaveApiCall(final int PageNo, final Boolean Isc)
    {
        String url;
        queue = Volley.newRequestQueue(ViewLeaveListingActivity.this);


        if (Isc)
        {
            url = URLS.Get_leave_appliation_list + "&emp_id=" + mySharedPreferenses.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "2" + "";
        }
        else
        {

            url = URLS.Get_leave_appliation_list + "&emp_id=" + mySharedPreferenses.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "&status=" + "1" + "";
        }
        url = url.replace(" ","%20");
        System.out.println("Get_leave_appliation_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
//                DialogUtils.hideProgressDialog();

                // System.out.println("response of Get_User_LeaveBalance !!!!!!!!!!! " + response);
                response = response + "";

                if (response.length() > 10)
                {
                    System.out.println("response size of leave listing ::::::::::::::::::: " + response.length());

                    response = "{\"Data\":" + response + "}";

                    // System.out.println("sucess response Get_User_LeaveBalance !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    leave_list_pojo = gson.fromJson(response, Leave_list_pojo.class);
                    listall.addAll(leave_list_pojo.getData());
                    System.out.println("response data size of leave listing ::::::::::::::::::: " + leave_list_pojo.getData().size());
                    if (leave_list_pojo != null)
                    {
                        if (leave_list_pojo.getData() != null)
                        {
                            if (leave_list_pojo.getData().get(0) != null)
                            {
                                if (leave_list_pojo.getData().size() > 0)
                                {

                                    if (lvviewleave != null)
                                    {

                                        if (viewLeaveListingAdapter != null && PageNo > 1)
//                        if (adapter != null && pageNo > 1)
                                        {

                                            viewLeaveListingAdapter.notifyDataSetChanged();
                                        }
                                        else
                                        {

                                            viewLeaveListingAdapter = new ViewLeaveListingAdapter(ViewLeaveListingActivity.this, leave_list_pojo, listall);
                                            // viewLeaveListingAdapter.notifyDataSetChanged();
                                            lvviewleave.setAdapter(viewLeaveListingAdapter);
                                            lvviewleave.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page,int totalItemsCount)
                                                {
                                                    System.out.println("page number view listing API ::::::::: " + page);
                                                    ViewLeaveApiCall(page,is_check_API);

                                                    return true;
                                                }
                                            });
                                        }

                                        //int pg_ = PageNo + 1;

                                        // System.out.println("*********WISHLIST *******again called+++++++++++ " + pg_ + "");

                                        //******** for page >1
                                        // ViewLeaveApiCall(pg_, Isc);

//                                        viewLeaveListingAdapter = new ViewLeaveListingAdapter(ViewLeaveListingActivity.this, leave_list_pojo, listall);
//                                        lvviewleave.setAdapter(viewLeaveListingAdapter);

                                    }

                                }
                                else
                                {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(ViewLeaveListingActivity.this, "No Records Found");
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
                        DialogUtils.Show_Toast(ViewLeaveListingActivity.this, "No Records Found");
                        viewLeaveListingAdapter = new ViewLeaveListingAdapter(ViewLeaveListingActivity.this, leave_list_pojo, listall);
                        viewLeaveListingAdapter.notifyDataSetChanged();
                        lvviewleave.setAdapter(viewLeaveListingAdapter);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(ViewLeaveListingActivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void initView() {


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
