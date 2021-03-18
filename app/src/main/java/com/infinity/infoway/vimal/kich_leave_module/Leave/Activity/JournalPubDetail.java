package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_leave_module.Leave.App.MarshMallowPermission;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.ConAppRejPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.ConfarancePubPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.JournalPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class JournalPubDetail extends AppCompatActivity {

    CustomBoldTextView tvapprove;
    CustomBoldTextView tvdelete;
    LinearLayout llupdatedelete;
    EditText edtempname;
    EditText edt_ugc_care;
    LinearLayout llempname;
    EditText edtyear;
    LinearLayout llleavebalance;
    EditText edtnoauth;
    LinearLayout llnoauth;
    EditText edttitleofreachpaper;
    LinearLayout lltitleofreachpaper;
    EditText edtyearpun;
    LinearLayout llyearpub;
    EditText edtlvljou;
    ImageView ivcalendar;
    LinearLayout lllvljou;
    EditText edtnamejou;
    LinearLayout llnamejou;
    EditText edttypeauth;
    LinearLayout lltypeauth;
    EditText edtleveljou;
    LinearLayout llleveljou;
    EditText edtisbn;
    LinearLayout llisbn;
    EditText edtappdate;
    LinearLayout llapproveddt;
    EditText edtappby;
    LinearLayout llapprovedby;
    ImageView ivfile;
    LinearLayout lldoc;
    CustomBoldTextView tvsubmit;
    CustomBoldTextView tvcancel;
    LinearLayout llsubmitcancel;

    JournalPojo journalPojo;

    MySharedPrefereces mySharedPrefereces;
    ImageView iv_back;
    CustomBoldTextView txt_act;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    RequestQueue queue;
    Activity activity;
    public static boolean is_back_general_pub_approval =false;

    String ID = "", status = "";

    ConfarancePubPojo confarancePubPojo;
    MarshMallowPermission marshMallowPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_pub_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_back_general_pub_approval = true;
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("Journal Publication Detail");
        activity = JournalPubDetail.this;

        initView();

        Intent intent = getIntent();

        ID = intent.getStringExtra("ID");
        System.out.println("Id of ConferencePubApproveReject from intent ::::::::::::::::::::::::: " + ID);
        status = intent.getStringExtra("status");

        if (ID != null && status != null && !ID.contentEquals("") && !status.contentEquals(""))
        {
            JournalPublicationDetail(ID, status);
        }

        if (status != null  && !status.contentEquals(""))
        {
            if(status.compareToIgnoreCase("pending")!=0)
            {
                llupdatedelete.setVisibility(View.GONE);
            }
            else
            {
                llupdatedelete.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        is_back_general_pub_approval = true;
        super.onBackPressed();
    }

    private void JournalPublicationDetail(String id, String status)
    {

        if (status.compareToIgnoreCase("Pending")==0)
        {
            status = "1";
        }
        else

        {
            status ="2";
        }
        queue = Volley.newRequestQueue(JournalPubDetail.this);

        String url = URLS.Get_publication_in_journal_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + "1" + "&status=" + status + "&id=" + id + "";

        url = url.replace(" ", "%20");
        System.out.println("Get_publication_in_journal_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";

                System.out.println("Get_publication_in_journal_list response size in leave listing :::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    journalPojo = gson.fromJson(response, JournalPojo.class);
                    System.out.println("Get_publication_in_journal_list data size :::::::::::::::::::::::::::::::::::::" + journalPojo.getData().size());


                    if (journalPojo != null) {
                        if (journalPojo.getData() != null) {
                            if (journalPojo.getData().get(0) != null) {
                                if (journalPojo.getData().size() > 0) {


                                    edtempname.setText(journalPojo.getData().get(0).getIpc_emp_name() + "");
                                    edt_ugc_care.setText(journalPojo.getData().get(0).getUGC_Care() + "");
                                    edtyear.setText(journalPojo.getData().get(0).getAcademic_Year() + "");
                                    edtnoauth.setText(journalPojo.getData().get(0).getNos_of_Authors() + "");
                                    edttitleofreachpaper.setText(journalPojo.getData().get(0).getTitle_of_Research_Paper() + "");
                                    edtyearpun.setText(journalPojo.getData().get(0).getYear_of_Publication() + "");
                                    // edttitleproceeding.setText(confarancePubPojo.getData().get(0).() + "");
                                    edtlvljou.setText(journalPojo.getData().get(0).getLevel_of_Journal() + "");
                                    edtnamejou.setText(journalPojo.getData().get(0).getName_of_Journal() + "");
                                    edttypeauth.setText(journalPojo.getData().get(0).getType_of_Author() + "");
                                    edtisbn.setText(journalPojo.getData().get(0).getProceedings_ISBN_ISSN() + "");
                                    edtappby.setText(journalPojo.getData().get(0).getApprove_reject_by_user() + "");
                                    edtappdate.setText(journalPojo.getData().get(0).getApprove_rejct_date() + "");

                                    ivfile.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                                                marshMallowPermission.requestPermissionForExternalStorage();
                                            } else {


                                                String url_con = journalPojo.getData().get(0).getDownload() + "";
                                                System.out.println("url_con ::::::::::::::::::::: " + url_con);

                                                String[] file2 = url_con.split("/");


                                                System.out.println("file after split::::::>>>>***" + String.valueOf(file2));
                                                System.out.println("file after split::::::" + file2.toString());

                                                String result1 = file2[file2.length - 1];

                                                System.out.println("result1::::" + result1);
                                                String nameoffile1 = result1;


                                                System.out.println("nameoffile1****" + System.currentTimeMillis() + "_" + nameoffile1);


                                                String url = url_con;
                                                if (url != null && url.length() > 5) {
                                                    String extention = url.substring(url.lastIndexOf("."), url.length());
                                                    //Log.d("syllabuspdfurl", syllabusdetail.get(position).getPdf());
                                                    new DownloadFileFromURL(extention).execute(url);
                                                } else {
                                                    DialogUtils.Show_Toast(JournalPubDetail.this, "No Documents Available");
                                                }


                                            }

                                        }
                                    });


                                } else {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(JournalPubDetail.this, "No Records Found");
                                }

                            }
                        }
                    }
                } else {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(JournalPubDetail.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }
    private class DownloadFileFromURL extends AsyncTask<String, String, String> {


        String nameoffile;
        String Extenson;

        DownloadFileFromURL(String Extenson) {
            this.Extenson = Extenson;

            System.out.println("EXTENSION OF FILE::::::::::" + Extenson);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            DialogUtils.showProgressDialog(activity, "");

        }

        /**
         * Downloading file in menubackground thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);


                String urofysllabusl = f_url[0];
                System.out.println("urofysllabusl::::::" + urofysllabusl);
                String[] parts = urofysllabusl.split("/");
                System.out.println("parts::::::::::::::" + parts);
                String result = parts[parts.length - 1];
                System.out.println("result:::::::::::" + result);
                nameoffile = result;

                System.out.println("result::::::doInback::::" + result);
                System.out.println("name in  doInBackground>>>>" + nameoffile);
                URLConnection conection = url.openConnection();
                conection.connect();
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Infinity/" + "/JournalPub/");
                dir.mkdirs();


                System.out.println("path of file>>>>>" + dir.getAbsolutePath());
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                //  Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());


                OutputStream output = new FileOutputStream("sdcard/Infinity/" + "/JournalPub/" + nameoffile);

                System.out.println("output:::::::::" + output.toString());
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //pDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            DialogUtils.hideProgressDialog();

            DialogUtils.Show_Toast(JournalPubDetail.this, "Download Completed Successfully");

            System.out.println("EXTENSION OF FILE onPostExecute::::::::::" + Extenson);

            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Infinity/" + "/JournalPub/" + nameoffile);
            Log.d("pathoffile", String.valueOf(file11));
//                                    Uri uri = FileProvider.getUriForFile(AssignmentActivity.this, "com.infinity.infoway.atmiya.activity.AssignmentActivity.fileprovider", file11);
//

            /// for  opening downloaded documentssssssssssss
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24) {


                System.out.println("path of file is :::::::::::::: " + file11.getPath());
                Uri uri = FileProvider.getUriForFile(JournalPubDetail.this, JournalPubDetail.this.getPackageName() + ".fileprovider", file11);
//                Uri uri = FileProvider.getUriForFile(_context, _context.getPackageName() , file11);
                JournalPubDetail.this.grantUriPermission(JournalPubDetail.this.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = null;
                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                }
                intent = Intent.createChooser(target, "Open File");
                try {
                    JournalPubDetail.this.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(JournalPubDetail.this, "No Apps can performs This action");
                }
            } else {


                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.setDataAndType(Uri.fromFile(file11), "image/*");

                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                }

                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    JournalPubDetail.this.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(JournalPubDetail.this, "No Apps can performs This action");

                }

            }


        }

    }

    private void initView()
    {
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        queue = Volley.newRequestQueue(this);
        marshMallowPermission = new  MarshMallowPermission(activity);
        tvapprove = (CustomBoldTextView) findViewById(R.id.tv_approve);
        tvapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(JournalPubDetail.this,"approve",ID);

            }
        });
        tvdelete = (CustomBoldTextView) findViewById(R.id.tv_delete);
        tvdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog4YNo(JournalPubDetail.this, "", "Are You Sure To Reject ?", new DialogUtils.DailogCallBackOkButtonClick()
                {
                    @Override
                    public void onDialogOkButtonClicked()
                    {
                        //showDialog(ctx, position);


                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick()
                {
                    @Override
                    public void onDialogCancelButtonClicked()
                    {
                        showDialog(JournalPubDetail.this, "reject",ID);

                    }
                });
            }
        });
        llupdatedelete = (LinearLayout) findViewById(R.id.ll_update_delete);
        edtempname = (EditText) findViewById(R.id.edt_emp_name);
        edt_ugc_care = (EditText) findViewById(R.id.edt_ugc_care);
        llempname = (LinearLayout) findViewById(R.id.ll_emp_name);
        edtyear = (EditText) findViewById(R.id.edt_year);
        llleavebalance = (LinearLayout) findViewById(R.id.ll_leave_balance);
        edtnoauth = (EditText) findViewById(R.id.edt_no_auth);
        llnoauth = (LinearLayout) findViewById(R.id.ll_no_auth);
        edttitleofreachpaper = (EditText) findViewById(R.id.edt_title_of_reach_paper);
        lltitleofreachpaper = (LinearLayout) findViewById(R.id.ll_title_of_reach_paper);
        edtyearpun = (EditText) findViewById(R.id.edt_year_pun);
        llyearpub = (LinearLayout) findViewById(R.id.ll_year_pub);
        edtlvljou = (EditText) findViewById(R.id.edt_lvl_jou);
        ivcalendar = (ImageView) findViewById(R.id.iv_calendar);
        lllvljou = (LinearLayout) findViewById(R.id.ll_lvl_jou);
        edtnamejou = (EditText) findViewById(R.id.edt_name_jou);
        llnamejou = (LinearLayout) findViewById(R.id.ll_name_jou);
        edttypeauth = (EditText) findViewById(R.id.edt_type_auth);
        lltypeauth = (LinearLayout) findViewById(R.id.ll_type_auth);
        edtleveljou = (EditText) findViewById(R.id.edt_level_jou);
        llleveljou = (LinearLayout) findViewById(R.id.ll_level_jou);
        edtisbn = (EditText) findViewById(R.id.edt_isbn);
        llisbn = (LinearLayout) findViewById(R.id.ll_isbn);
        edtappdate = (EditText) findViewById(R.id.edt_app_date);
        llapproveddt = (LinearLayout) findViewById(R.id.ll_approved_dt);
        edtappby = (EditText) findViewById(R.id.edt_app_by);
        llapprovedby = (LinearLayout) findViewById(R.id.ll_approved_by);
        ivfile = (ImageView) findViewById(R.id.iv_file);
        lldoc = (LinearLayout) findViewById(R.id.ll_doc);
        tvsubmit = (CustomBoldTextView) findViewById(R.id.tv_submit);
        tvcancel = (CustomBoldTextView) findViewById(R.id.tv_cancel);
        llsubmitcancel = (LinearLayout) findViewById(R.id.ll_submit_cancel);

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

    public void showDialog(final Context context, final String approve_reject, final String ID)
    {

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.popup_miss_punch, null);

        final EditText edt_reason = (EditText) dialogView.findViewById(R.id.edt_reason);
        CustomBoldTextView tv_titile = (CustomBoldTextView) dialogView.findViewById(R.id.tv_titile);
        tv_titile.setText(context.getResources().getString(R.string.app_name_));
        CustomButtonView dialogButtonCancel = (CustomButtonView) dialogView.findViewById(R.id.dialogButtonCancel);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //  final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        final AlertDialog b = builder.create();
        //  builder.setTitle("Material Style Dialog");
        builder.setCancelable(true);
        builder.setView(dialogView);
        b.setCanceledOnTouchOutside(true);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //  builder.
        final AlertDialog show = builder.show();
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();

            }
        });
        CustomButtonView dialogButtonOK = (CustomButtonView) dialogView.findViewById(R.id.dialogButtonOK);
        dialogButtonOK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isvalidate(edt_reason))
                {
                    show.dismiss();


                    if (approve_reject.compareToIgnoreCase("approve")==0)
                    {
                        ApproveConfarancePub(edt_reason.getText().toString().trim(),show,ID,"approve");
                    }

                    else
                    {
                        ApproveConfarancePub(edt_reason.getText().toString().trim(),show,ID,"reject");
                        // RejectLeave(edt_reason.getText().toString().trim(), show, pos,ID,"reject");
                    }

                }
            }
        });
    }

    ConAppRejPojo conAppRejPojo;
    private  void ApproveConfarancePub(String reason, AlertDialog show, final String ID,String app_rej)
    {

        DialogUtils.showProgressDialog(JournalPubDetail.this,"");
        String url;

        if (app_rej.compareToIgnoreCase("approve")==0)
        {
            url = URLS.Get_publication_in_journal_Approved_or_Reject +"&id="+ID+"&remarks="+reason+ "&user_id="+mySharedPrefereces.getUserID()+"&ip="+""+"&approve_reject=" + "1" + "";

        }
        else
        {
            url = URLS.Get_publication_in_journal_Approved_or_Reject +"&id="+ID+"&remarks="+reason+ "&user_id="+mySharedPrefereces.getUserID()+"&ip="+""+"&approve_reject=" + "2" + "";

        }

        url = url.replace(" ", "%20");
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        DialogUtils.hideProgressDialog();
                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS Get_publication_conference_Approved_or_Reject  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");


                        System.out.println("response length :::::::::::::: " + response.length());
                        System.out.println("response data size  :::::::::::::: " + response);

                        if (response.length() > 10) {
                            Gson gson = new Gson();

                            conAppRejPojo = gson.fromJson(response, ConAppRejPojo.class);
                            if (conAppRejPojo!=null)
                            {
                                if (conAppRejPojo.getData()!=null)
                                {
                                    if (conAppRejPojo.getData().get(0)!=null&&conAppRejPojo.getData().size()>0)
                                    {

                                        if (!conAppRejPojo.getData().get(0).getMsg().contentEquals(""))
                                        {
                                            DialogUtils.Show_Toast(JournalPubDetail.this,conAppRejPojo.getData().get(0).getMsg());
                                            finish();
                                        }


                                    }
                                }
                            }


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
                System.out.println();
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);




    }

    private boolean isvalidate(EditText edt_reason) {
        if (edt_reason.getText().toString().trim().isEmpty() || edt_reason.getText().toString().contentEquals("") || edt_reason.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(JournalPubDetail.this, "Enter Reason");
            return false;
        }

        return true;
    }

}
