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
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.App.MarshMallowPermission;
import com.infinity.kich.Leave.Pojo.BookChapterPubPojo;
import com.infinity.kich.Leave.Pojo.CPDAppPojo;
import com.infinity.kich.Leave.Pojo.ConAppRejPojo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
public class CpdApplicationDetail extends AppCompatActivity {

    CustomBoldTextView tvapprove;
    CustomBoldTextView tvdelete;
    LinearLayout llupdatedelete;
    EditText edtempname;
    LinearLayout llempname;
    EditText edtyear;
    LinearLayout llleavebalance;
    EditText edttitleprogram;
    LinearLayout lltitleprogram;
    EditText edtto;
    LinearLayout llto;
    EditText edtdate;
    ImageView ivcalendar;
    LinearLayout lldate;
    EditText edtTotalParticipants;
    LinearLayout llTotalParticipants;
    EditText edtLinkFeedback;
    LinearLayout llLinkFeedback;
    EditText edtappdate;
    LinearLayout llapproveddt;
    EditText edtappby;
    LinearLayout llapprovedby;
    ImageView ivfile;
    LinearLayout lldoc;
    ImageView ivfilereport,iv_to_date;
    LinearLayout lldocreport;
    CustomBoldTextView tvsubmit;
    CustomBoldTextView tvcancel;
    LinearLayout llsubmitcancel;

    MySharedPrefereces mySharedPrefereces;
    ImageView iv_back;
    CustomBoldTextView txt_act;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    BookChapterPubPojo bookChapterPubPojo;
    RequestQueue queue;
    Activity activity;

    String ID = "", status = "";

    CPDAppPojo cpdAppPojo;
    MarshMallowPermission marshMallowPermission;
    public static boolean is_back_cpd_app =false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpd_application_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                is_back_cpd_app =true;
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("CPD Application Detail");
        activity = CpdApplicationDetail.this;
        initView();

        Intent intent = getIntent();

        ID = intent.getStringExtra("ID");
        System.out.println("Id of ConferencePubApproveReject from intent ::::::::::::::::::::::::: " + ID);
        status = intent.getStringExtra("status");

        if (ID != null && status != null && !ID.contentEquals("") && !status.contentEquals(""))
        {
            CPDAppDetailAPI(ID, status);
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
        is_back_cpd_app= true;
        super.onBackPressed();
    }

    private void CPDAppDetailAPI(String id, String status)
    {

        if (status.compareToIgnoreCase("Pending")==0)
        {
            status = "1";
        }
        else

        {
            status ="2";
        }

        queue = Volley.newRequestQueue(CpdApplicationDetail.this);

        String url = URLS.Get_CPD_Application_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + "1" + "&status=" + status + "&id=" + id + "";

        url = url.replace(" ", "%20");
        System.out.println("Get_leave_approve_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";

                System.out.println("response size in leave listing :::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    cpdAppPojo = gson.fromJson(response, CPDAppPojo.class);
                    System.out.println("approve leave listing data size :::::::::::::::::::::::::::::::::::::" + cpdAppPojo.getData().size());


                    if (cpdAppPojo != null) {
                        if (cpdAppPojo.getData() != null) {
                            if (cpdAppPojo.getData().get(0) != null) {
                                if (cpdAppPojo.getData().size() > 0) {


                                    edtempname.setText(cpdAppPojo.getData().get(0).getEmployee_name() + "");
                                    edtyear.setText(cpdAppPojo.getData().get(0).getAcademic_Year());
                                    edttitleprogram.setText(cpdAppPojo.getData().get(0).getTitle_of_Workshop_Seminar_Program() + "");
                                    edtdate.setText(cpdAppPojo.getData().get(0).getDate_From() + "");
                                    edtto.setText(cpdAppPojo.getData().get(0).getDate_To() + "");
                                    edtTotalParticipants.setText(cpdAppPojo.getData().get(0).getTotal_Participants() + "");
                                    edtLinkFeedback.setText(cpdAppPojo.getData().get(0).getLink_of_Feedback() + "");
                                    edtappby.setText(cpdAppPojo.getData().get(0).getApprove_reject_by_user() + "");
                                    edtappdate.setText(cpdAppPojo.getData().get(0).getApprove_rejct_date() + "");

                                    ivfile.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                                                marshMallowPermission.requestPermissionForExternalStorage();
                                            } else {


                                                String url_con_form = cpdAppPojo.getData().get(0).getCPD_Application_Form_Download() + "";
                                                System.out.println("url_con ::::::::::::::::::::: " + url_con_form);

                                                String[] file2 = url_con_form.split("/");


                                                System.out.println("file after split::::::>>>>***" + String.valueOf(file2));
                                                System.out.println("file after split::::::" + file2.toString());

                                                String result1 = file2[file2.length - 1];

                                                System.out.println("result1::::" + result1);
                                                String nameoffile1 = result1;


                                                System.out.println("nameoffile1****" + System.currentTimeMillis() + "_" + nameoffile1);


                                                String url = url_con_form;
                                                if (url != null && url.length() > 5) {
                                                    String extention = url.substring(url.lastIndexOf("."), url.length());
                                                    //Log.d("syllabuspdfurl", syllabusdetail.get(position).getPdf());
                                                    new DownloadFileFromURL(extention).execute(url);
                                                } else {
                                                    DialogUtils.Show_Toast(CpdApplicationDetail.this, "No Documents Available");
                                                }


                                            }

                                        }
                                    });

                                    ivfilereport.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
                                            if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                                                marshMallowPermission.requestPermissionForExternalStorage();
                                            } else {


                                                String url_con_report = cpdAppPojo.getData().get(0).getConducation_Report_Download() + "";
                                                System.out.println("url_con ::::::::::::::::::::: " + url_con_report);

                                                String[] file2 = url_con_report.split("/");


                                                System.out.println("file after split::::::>>>>***" + String.valueOf(file2));
                                                System.out.println("file after split::::::" + file2.toString());

                                                String result1 = file2[file2.length - 1];

                                                System.out.println("result1::::" + result1);
                                                String nameoffile1 = result1;


                                                System.out.println("nameoffile1****" + System.currentTimeMillis() + "_" + nameoffile1);


                                                String url = url_con_report;
                                                if (url != null && url.length() > 5) {
                                                    String extention = url.substring(url.lastIndexOf("."), url.length());
                                                    //Log.d("syllabuspdfurl", syllabusdetail.get(position).getPdf());
                                                    new DownloadFileFromURL(extention).execute(url);
                                                } else {
                                                    DialogUtils.Show_Toast(CpdApplicationDetail.this, "No Documents Available");
                                                }


                                            }


                                        }
                                    });


                                } else {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(CpdApplicationDetail.this, "No Records Found");
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
                DialogUtils.Show_Toast(CpdApplicationDetail.this, "Please Try Again Later");
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
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Infinity/" + "/CpdApp/");
                dir.mkdirs();


                System.out.println("path of file>>>>>" + dir.getAbsolutePath());
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                //  Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());


                OutputStream output = new FileOutputStream("sdcard/Infinity/" + "/CpdApp/" + nameoffile);

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

            DialogUtils.Show_Toast(CpdApplicationDetail.this, "Download Completed Successfully");

            System.out.println("EXTENSION OF FILE onPostExecute::::::::::" + Extenson);

            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Infinity/" + "/CpdApp/" + nameoffile);
            Log.d("pathoffile", String.valueOf(file11));
//                                    Uri uri = FileProvider.getUriForFile(AssignmentActivity.this, "com.infinity.infoway.atmiya.activity.AssignmentActivity.fileprovider", file11);
//

            /// for  opening downloaded documentssssssssssss
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24) {


                System.out.println("path of file is :::::::::::::: " + file11.getPath());
                Uri uri = FileProvider.getUriForFile(CpdApplicationDetail.this, CpdApplicationDetail.this.getPackageName() + ".fileprovider", file11);
//                Uri uri = FileProvider.getUriForFile(_context, _context.getPackageName() , file11);
                CpdApplicationDetail.this.grantUriPermission(CpdApplicationDetail.this.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = null;
                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                }
                intent = Intent.createChooser(target, "Open File");
                try {
                    CpdApplicationDetail.this.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(CpdApplicationDetail.this, "No Apps can performs This action");
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
                    CpdApplicationDetail.this.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(CpdApplicationDetail.this, "No Apps can performs This action");

                }

            }


        }

    }
    private void initView()
    {
        queue = Volley.newRequestQueue(this);
        marshMallowPermission =new  MarshMallowPermission(CpdApplicationDetail.this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        tvapprove = (CustomBoldTextView) findViewById(R.id.tv_approve);
        tvapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CpdApplicationDetail.this,"approve",ID);

            }
        });
        tvdelete = (CustomBoldTextView) findViewById(R.id.tv_delete);
        tvdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog4YNo(CpdApplicationDetail.this, "", "Are You Sure To Reject ?", new DialogUtils.DailogCallBackOkButtonClick()
                {
                    @Override
                    public void onDialogOkButtonClicked()
                    {
                        showDialog(CpdApplicationDetail.this,"reject",ID);


                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick()
                {
                    @Override
                    public void onDialogCancelButtonClicked()
                    {

                    }
                });
            }
        });
        llupdatedelete = (LinearLayout) findViewById(R.id.ll_update_delete);
        edtempname = (EditText) findViewById(R.id.edt_emp_name);
        llempname = (LinearLayout) findViewById(R.id.ll_emp_name);
        edtyear = (EditText) findViewById(R.id.edt_year);
        llleavebalance = (LinearLayout) findViewById(R.id.ll_leave_balance);
        edttitleprogram = (EditText) findViewById(R.id.edt_title_program);
        lltitleprogram = (LinearLayout) findViewById(R.id.ll_title_program);
        edtto = (EditText) findViewById(R.id.edt_to);
        llto = (LinearLayout) findViewById(R.id.ll_to);
        edtdate = (EditText) findViewById(R.id.edt_date);
        ivcalendar = (ImageView) findViewById(R.id.iv_calendar);
        lldate = (LinearLayout) findViewById(R.id.ll_date);
        edtTotalParticipants = (EditText) findViewById(R.id.edt_Total_Participants);
        llTotalParticipants = (LinearLayout) findViewById(R.id.ll_Total_Participants);
        edtLinkFeedback = (EditText) findViewById(R.id.edt_Link_Feedback);
        llLinkFeedback = (LinearLayout) findViewById(R.id.ll_Link_Feedback);
        edtappdate = (EditText) findViewById(R.id.edt_app_date);
        llapproveddt = (LinearLayout) findViewById(R.id.ll_approved_dt);
        edtappby = (EditText) findViewById(R.id.edt_app_by);
        llapprovedby = (LinearLayout) findViewById(R.id.ll_approved_by);
        ivfile = (ImageView) findViewById(R.id.iv_file);
        iv_to_date = (ImageView) findViewById(R.id.iv_to_date);
        lldoc = (LinearLayout) findViewById(R.id.ll_doc);
        ivfilereport = (ImageView) findViewById(R.id.iv_file_report);
        lldocreport = (LinearLayout) findViewById(R.id.ll_doc_report);
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


    public void showDialog(final Context context,  final String approve_reject, final String ID)
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

    private boolean isvalidate(EditText edt_reason) {
        if (edt_reason.getText().toString().trim().isEmpty() || edt_reason.getText().toString().contentEquals("") || edt_reason.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(CpdApplicationDetail.this, "Enter Reason");
            return false;
        }

        return true;
    }

    ConAppRejPojo conAppRejPojo;
    private  void ApproveConfarancePub(String reason, AlertDialog show,  final String ID,String app_rej)
    {

        DialogUtils.showProgressDialog(CpdApplicationDetail.this,"");
        String url;

        if (app_rej.compareToIgnoreCase("approve")==0)
        {
            url = URLS.Get_CPD_Application_Approved_or_Reject +"&id="+ID+"&remarks="+reason+ "&user_id="+mySharedPrefereces.getUserID()+"&ip="+""+"&approve_reject=" + "1" + "";

        }
        else
        {
            url = URLS.Get_CPD_Application_Approved_or_Reject +"&id="+ID+"&remarks="+reason+ "&user_id="+mySharedPrefereces.getUserID()+"&ip="+""+"&approve_reject=" + "2" + "";

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
                                            DialogUtils.Show_Toast(CpdApplicationDetail.this,conAppRejPojo.getData().get(0).getMsg());
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
}
