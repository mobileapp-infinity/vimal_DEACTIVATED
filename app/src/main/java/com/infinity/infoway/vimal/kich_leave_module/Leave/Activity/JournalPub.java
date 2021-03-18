package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.Activity;
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
import com.infinity.kich.Leave.Adapter.JournalPublicationadapter;
import com.infinity.kich.Leave.Pojo.JournalPojo;

import java.util.ArrayList;
import java.util.List;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
public class JournalPub extends AppCompatActivity {

    CustomBoldTextView txtenrollno;
    CustomBoldTextView txtsrno;
    Switch cbcheck;
    LinearLayout llmainapproveheadderleave;
    LinearLayout llmainheder;
    static ListView lvjournal;
    static MySharedPrefereces mySharedPrefereces;
    static RequestQueue queue;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;

    static Boolean isChecked_API = false;

    public static JournalPojo journalPojo;
    ImageView iv_back;
    static JournalPublicationadapter journalPublicationadapter;
    CustomBoldTextView txt_act;
    static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_pub);

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
        txt_act.setText("Journal Pub");
        initView();

        activity = JournalPub.this;
        cbcheck = (Switch) findViewById(R.id.cb_check);
        cbcheck.setChecked(false);
        listall = new ArrayList<>();
        listall.clear();
        journalPublicationadapter = null;


        cbcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                listall = new ArrayList<>();
                listall.clear();
                journalPojo = new JournalPojo();
                //  lvapproveleave.scrollTo(0,0);

                JournalPublicationListing(1, isChecked);


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

    public static void JournalPublicationListing(final int page_no, Boolean isc)
    {
        String url;
        queue = Volley.newRequestQueue(activity);

        if (isc)
        {
            url = URLS.Get_publication_in_journal_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "2" + "&id=" + "0" + "";
        } else {

            url = URLS.Get_publication_in_journal_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + page_no + "&status=" + "1" + "&id=" + "0" + "";
        }
        url = url.replace(" ", "%20");

        System.out.println("Get_publication_in_journal_list URL on resume ::::::  " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                System.out.println("response size in leave listing Get_publication_in_journal_list:::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    journalPojo = new JournalPojo();
                    journalPojo = gson.fromJson(response, JournalPojo.class);
                    System.out.println("Get_publication_in_journal_list listing data size :::::::::::::::::::::::::::::::::::::" + journalPojo.getData().size());

                    listall.addAll(journalPojo.getData());

                    if (journalPojo != null)
                    {
                        if (journalPojo.getData() != null)
                        {
                            if (journalPojo.getData().get(0) != null)
                            {
                                if (journalPojo.getData().size() > 0)
                                {

                                    if (lvjournal != null)
                                    {

                                        if (journalPublicationadapter != null && page_no > 1)
                                        {
                                            System.out.println("page if  ");
                                            journalPublicationadapter.notifyDataSetChanged();
                                        } else {
                                            System.out.println("page else  ");

                                            journalPublicationadapter = new JournalPublicationadapter(activity, journalPojo, listall, isChecked_API);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                            lvjournal.setAdapter(journalPublicationadapter);

                                            lvjournal.setOnScrollListener(new EndlessScrollListener()
                                            {
                                                @Override
                                                public boolean onLoadMore(int page, int totalItemsCount)
                                                {

                                                    System.out.println("item counts :::::::::::: " + totalItemsCount);
                                                    System.out.println("page number ::::::::::: " + page);

                                                    JournalPublicationListing(page, isChecked_API);

                                                    return true;
                                                }
                                            });

                                        }


                                    }

                                } else {
                                    journalPublicationadapter = new JournalPublicationadapter(activity, journalPojo, listall, isChecked_API);
//                                             cancelLeavesAdapter.notifyDataSetChanged();
                                    lvjournal.setAdapter(journalPublicationadapter);
                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(activity, "No Records Found");
                                }

                            }
                        }
                        else
                        {
                            System.out.println("journalPojo.getData() = nullllll");
                        }
                    }
                    else
                    {
                        System.out.println("adapter is nullllllll");
                    }
                }
                else
                {
                    if (page_no == 1)
                    {
                        listall.clear();
                        DialogUtils.Show_Toast(activity, "No Records Found");
                        journalPublicationadapter = new JournalPublicationadapter(activity, journalPojo, listall, isChecked_API);
                        journalPublicationadapter.notifyDataSetChanged();
                        lvjournal.setAdapter(journalPublicationadapter);

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

    public static List<JournalPojo.DataBean> listall;

    private void initView()
    {

        queue = Volley.newRequestQueue(this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        txtenrollno = (CustomBoldTextView) findViewById(R.id.txt_enroll_no);
        txtsrno = (CustomBoldTextView) findViewById(R.id.txt_sr_no);
        cbcheck = (Switch) findViewById(R.id.cb_check);
        llmainapproveheadderleave = (LinearLayout) findViewById(R.id.ll_main_approve_headder_leave);
        llmainheder = (LinearLayout) findViewById(R.id.ll_main_heder);
        lvjournal = (ListView) findViewById(R.id.lv_journal);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if ( com.infinity.kich.Leave.Activity.JournalPubDetail.is_back_general_pub_approval)
        {
            com.infinity.kich.Leave.Activity.JournalPubDetail.is_back_general_pub_approval = false;

        }

        else
        {
        listall = new ArrayList<>();
        listall.clear();
        journalPojo = new JournalPojo();
        cbcheck.setChecked(false);
        JournalPublicationListing(1, isChecked_API);

        }

    }
}
