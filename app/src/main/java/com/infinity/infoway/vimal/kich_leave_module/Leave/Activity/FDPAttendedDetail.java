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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.FDPAttendedPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.FdpDocumentPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomTextView;
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

public class FDPAttendedDetail extends AppCompatActivity {

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
    /**
     * Approve
     */
    private CustomBoldTextView tv_approve;
    /**
     * Reject
     */
    private CustomBoldTextView tv_delete;
    private LinearLayout ll_update_delete;
    /**
     * Enter Name
     */
    private EditText edt_emp_name;
    private LinearLayout ll_emp_name;
    /**
     * Enter Academic Year
     */
    private EditText edt_year;
    private LinearLayout ll_leave_balance;
    /**
     * Enter Event Type
     */
    private EditText edt_Event_Type;
    private LinearLayout ll_Event_Type;
    /**
     * Enter PD Framework Credit
     */
    private EditText edt_PD_Framework_Credit;
    private LinearLayout ll_PD_Framework_Credit;
    /**
     * Enter Name of the workshop | seminar | program
     */
    private EditText edt_workshop;
    private LinearLayout ll_work_shop;
    /**
     * Enter Organized By
     */
    private EditText edt_organized;
    private LinearLayout ll_organized;
    /**
     * Enter City
     */
    private EditText edt_City;
    private LinearLayout ll_City;
    /**
     * Enter Event Category
     */
    private EditText edt_Event_Category;
    private LinearLayout ll_Event_Category;
    /**
     * Enter Name of Organizer
     */
    private EditText edt_Name_of_Organizer;
    private LinearLayout ll_Name_of_Organizer;
    /**
     * Enter Level of Journal
     */
    private EditText edt_lvl_jou;
    private ImageView iv_calendar;
    private LinearLayout ll_lvl_jou;
    /**
     * Enter From Date
     */
    private EditText edt_from_date;
    private LinearLayout ll_from_date;
    /**
     * Enter To Date
     */
    private EditText edt_to_date;
    private LinearLayout ll_to_date;
    /**
     * Enter Type of Author
     */
    private EditText edt_type_auth;
    private LinearLayout ll_type_auth;
    /**
     * Enter UGC Care
     */
    private EditText edt_ugc_care;
    private LinearLayout ll_ugc_care;
    /**
     * Enter Level of Journal
     */
    private EditText edt_level_jou;
    private LinearLayout ll_level_jou;
    /**
     * Enter Proceedings ISBN | ISSN
     */
    private EditText edt_isbn;
    private LinearLayout ll_isbn;
    private EditText edt_app_date;
    private LinearLayout ll_approved_dt;
    private EditText edt_app_by;
    private LinearLayout ll_approved_by;
    private ImageView iv_file;
    private LinearLayout ll_doc;
    /**
     * Submit
     */
    private CustomBoldTextView tv_submit;
    /**
     * cancel
     */
    private CustomBoldTextView tv_cancel;
    private LinearLayout ll_submit_cancel;
    RequestQueue queue;
    Activity activity;

    String ID = "", status = "";

    FDPAttendedPojo fdpAttendedPojo;
    MySharedPrefereces mySharedPrefereces;
    MarshMallowPermission marshMallowPermission;
    FdpDocumentPojo fdpDocumentPojo;
    LinearLayout ll_main_doc_file;
    public static boolean is_back_fdp_approval = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdpattended_detail);
        initView();

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_back_fdp_approval = true;

                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("FDP Attended Detail");

        activity = FDPAttendedDetail.this;
        Intent intent = getIntent();

        ID = intent.getStringExtra("ID");
        System.out.println("Id of ConferencePubApproveReject from intent ::::::::::::::::::::::::: " + ID);
        status = intent.getStringExtra("status");

        status = status.trim();
        System.out.println("status of fdp attended======= :::::::::::" + status.trim());
        if (status != null && !status.contentEquals("")) {

            System.out.println("statusssssssssss=== " + status);
            System.out.println("call 1===========");
            ll_update_delete.setVisibility(View.GONE);

            if (status.contentEquals("Pending")) {
                ll_update_delete.setVisibility(View.VISIBLE);
                System.out.println("call 2==========");
            } else {
                ll_update_delete.setVisibility(View.GONE);
                System.out.println("call 3============");
            }
        }
        if (ID != null && status != null && !ID.contentEquals("") && !status.contentEquals("")) {
            FDPAttendedDetailAPI(ID, status);
            FDPDocumentAPI(ID);

        }

    }

    @Override
    public void onBackPressed() {
        is_back_fdp_approval = true;
        super.onBackPressed();
    }

    private void FDPDocumentAPI(String id) {


        if (status.compareToIgnoreCase("Pending") == 0) {
            status = "1";
        } else {
            status = "2";
        }
        queue = Volley.newRequestQueue(FDPAttendedDetail.this);

        String url = URLS.Get_FDP_Attended_list_Document + "&id=" + id + "";

        url = url.replace(" ", "%20");
        System.out.println("Get_FDP_Attended_list_Document URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";

                System.out.println("Get_FDP_Attended_list_Document response size in leave listing :::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    fdpDocumentPojo = gson.fromJson(response, FdpDocumentPojo.class);


                    if (fdpDocumentPojo != null) {
                        if (fdpDocumentPojo.getData() != null) {
                            if (fdpDocumentPojo.getData().get(0) != null) {
                                if (fdpDocumentPojo.getData().size() > 0) {
                                    ImageView imageView = null;
                                    String url = "";

                                    // fdpDocumentPojo.getData().size() = 3;
                                    for (int i = 0; i < fdpDocumentPojo.getData().size(); i++) {
                                        LinearLayout ll = new LinearLayout(FDPAttendedDetail.this);
                                        LinearLayout.LayoutParams params_main = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        params_main.setMargins(0, 20, 0, 0);
                                        ll.setPadding(10, 10, 10, 10);
                                        ll.setLayoutParams(params_main);
                                        ll.setBackground(getResources().getDrawable(R.drawable.add_leave_border));
                                        ll.setOrientation(LinearLayout.HORIZONTAL);

                                        ll.setWeightSum(2);
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.8f);
                                        LinearLayout.LayoutParams params_image = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f);

                                        TextView customBoldTextView = new TextView(FDPAttendedDetail.this);
                                        customBoldTextView.setPadding(15, 15, 15, 15);
                                        customBoldTextView.setLayoutParams(params);
//                                        customBoldTextView.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
//                                        customBoldTextView.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                                        customBoldTextView.setTextSize(17);
                                        customBoldTextView.setTextColor(getResources().getColor(R.color.black));
                                        customBoldTextView.setText("  FDP Document  ");

                                        imageView = new ImageView(FDPAttendedDetail.this);
                                        imageView.setPadding(15, 15, 15, 15);
                                        imageView.setLayoutParams(params_image);
                                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.download_salary_slip));

                                        ll.addView(customBoldTextView);
                                        ll.addView(imageView);
                                        ll_main_doc_file.addView(ll);

                                        if (fdpDocumentPojo.getData().get(i).getFile_name() != null && !fdpDocumentPojo.getData().get(i).getFile_name().contentEquals("")) {
                                            url = fdpDocumentPojo.getData().get(i).getFile_name() + "";
                                        }


                                        String finalUrl = url;
                                        if (finalUrl.contains(" ")) {
                                            finalUrl = finalUrl.replace(" ", "%20");
                                        } else {
                                            finalUrl = finalUrl;
                                        }

                                        final String urll = finalUrl;
                                        imageView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                System.out.println("URL OF DOCUMENT *********************" + urll);

                                                String url = urll;
                                                if (url != null && url.length() > 5) {
                                                    String extention = url.substring(url.lastIndexOf("."), url.length());
                                                    //Log.d("syllabuspdfurl", syllabusdetail.get(position).getPdf());
                                                    new DownloadFileFromURL(extention).execute(url);
                                                } else {
                                                    DialogUtils.Show_Toast(FDPAttendedDetail.this, "No Documents Available");
                                                }

                                            }
                                        });
                                    }


                                } else {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(FDPAttendedDetail.this, "No Records Found");
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
                DialogUtils.Show_Toast(FDPAttendedDetail.this, "Please Try Again Later");
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
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Infinity/" + "/FDPDocument/");
                dir.mkdirs();


                System.out.println("path of file>>>>>" + dir.getAbsolutePath());
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                //  Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());


                OutputStream output = new FileOutputStream("sdcard/Infinity/" + "/FDPDocument/" + nameoffile);

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

            DialogUtils.Show_Toast(FDPAttendedDetail.this, "Download Completed Successfully");

            System.out.println("EXTENSION OF FILE onPostExecute::::::::::" + Extenson);

            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Infinity/" + "/FDPDocument/" + nameoffile);
            Log.d("pathoffile", String.valueOf(file11));
//                                    Uri uri = FileProvider.getUriForFile(AssignmentActivity.this, "com.infinity.infoway.atmiya.activity.AssignmentActivity.fileprovider", file11);
//

            /// for  opening downloaded documentssssssssssss
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24) {


                System.out.println("path of file is :::::::::::::: " + file11.getPath());
                Uri uri = FileProvider.getUriForFile(FDPAttendedDetail.this, FDPAttendedDetail.this.getPackageName() + ".fileprovider", file11);
//                Uri uri = FileProvider.getUriForFile(_context, _context.getPackageName() , file11);
                FDPAttendedDetail.this.grantUriPermission(FDPAttendedDetail.this.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = null;
                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                }
                intent = Intent.createChooser(target, "Open File");
                try {
                    FDPAttendedDetail.this.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(FDPAttendedDetail.this, "No Apps can performs This action");
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
                    FDPAttendedDetail.this.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(FDPAttendedDetail.this, "No Apps can performs This action");

                }

            }


        }
    }

    private void FDPAttendedDetailAPI(String id, String status) {
        if (status.compareToIgnoreCase("Pending") == 0) {
            status = "1";
        } else {
            status = "2";
        }
        queue = Volley.newRequestQueue(FDPAttendedDetail.this);

        String url = URLS.Get_FDP_Attended_list + "&user_id=" + mySharedPrefereces.getUserID() + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + "1" + "&status=" + status + "&id=" + id + "";

        url = url.replace(" ", "%20");
        System.out.println("Get_FDP_Attended_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";

                System.out.println("Get_FDP_Attended_list response size in leave listing :::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    fdpAttendedPojo = gson.fromJson(response, FDPAttendedPojo.class);
                    System.out.println("Get_FDP_Attended_list approve leave listing data size :::::::::::::::::::::::::::::::::::::" + fdpAttendedPojo.getData().size());


                    if (fdpAttendedPojo != null) {
                        if (fdpAttendedPojo.getData() != null) {
                            if (fdpAttendedPojo.getData().get(0) != null) {
                                if (fdpAttendedPojo.getData().size() > 0) {


                                    edt_emp_name.setText(fdpAttendedPojo.getData().get(0).getEmployee_name() + "");
                                    edt_year.setText(fdpAttendedPojo.getData().get(0).getAcademic_Year());
                                    edt_Event_Type.setText(fdpAttendedPojo.getData().get(0).getEvent_type());
                                    edt_workshop.setText(fdpAttendedPojo.getData().get(0).getFdpa_workshop_name() + "");
                                    edt_PD_Framework_Credit.setText(fdpAttendedPojo.getData().get(0).getFdpa_pd_credit() + "");
                                    edt_app_by.setText(fdpAttendedPojo.getData().get(0).getApprove_reject_by() + "");
                                    edt_app_date.setText(fdpAttendedPojo.getData().get(0).getApproved_reject_date() + "");
                                    edt_organized.setText(fdpAttendedPojo.getData().get(0).getFdpa_organized_by() + "");
                                    edt_City.setText(fdpAttendedPojo.getData().get(0).getFdpa_organizer_city() + "");
                                    edt_Event_Category.setText(fdpAttendedPojo.getData().get(0).getEvent_Category() + "");
                                    edt_to_date.setText(fdpAttendedPojo.getData().get(0).getFdpa_to_date() + "");
                                    edt_from_date.setText(fdpAttendedPojo.getData().get(0).getFdpa_from_date() + "");
                                    edt_Name_of_Organizer.setText(fdpAttendedPojo.getData().get(0).getFdpa_organizer_name() + "");


                                } else {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(FDPAttendedDetail.this, "No Records Found");
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
                DialogUtils.Show_Toast(FDPAttendedDetail.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void initView() {
        queue = Volley.newRequestQueue(this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(FDPAttendedDetail.this);
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
        ll_main_doc_file = (LinearLayout) findViewById(R.id.ll_main_doc_file);
        tv_approve = (CustomBoldTextView) findViewById(R.id.tv_approve);
        tv_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(FDPAttendedDetail.this, "approve", ID);

            }
        });
        tv_delete = (CustomBoldTextView) findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog4YNo(FDPAttendedDetail.this, "", "Are You Sure To Reject ?", new DialogUtils.DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {
                        showDialog(FDPAttendedDetail.this, "reject", ID);


                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick() {
                    @Override
                    public void onDialogCancelButtonClicked() {

                    }
                });
            }
        });
        ll_update_delete = (LinearLayout) findViewById(R.id.ll_update_delete);
        edt_emp_name = (EditText) findViewById(R.id.edt_emp_name);
        ll_emp_name = (LinearLayout) findViewById(R.id.ll_emp_name);
        edt_year = (EditText) findViewById(R.id.edt_year);
        ll_leave_balance = (LinearLayout) findViewById(R.id.ll_leave_balance);
        edt_Event_Type = (EditText) findViewById(R.id.edt_Event_Type);
        ll_Event_Type = (LinearLayout) findViewById(R.id.ll_Event_Type);
        edt_PD_Framework_Credit = (EditText) findViewById(R.id.edt_PD_Framework_Credit);
        ll_PD_Framework_Credit = (LinearLayout) findViewById(R.id.ll_PD_Framework_Credit);
        edt_workshop = (EditText) findViewById(R.id.edt_workshop);
        ll_work_shop = (LinearLayout) findViewById(R.id.ll_work_shop);
        edt_organized = (EditText) findViewById(R.id.edt_organized);
        ll_organized = (LinearLayout) findViewById(R.id.ll_organized);
        edt_City = (EditText) findViewById(R.id.edt_City);
        ll_City = (LinearLayout) findViewById(R.id.ll_City);
        edt_Event_Category = (EditText) findViewById(R.id.edt_Event_Category);
        ll_Event_Category = (LinearLayout) findViewById(R.id.ll_Event_Category);
        edt_Name_of_Organizer = (EditText) findViewById(R.id.edt_Name_of_Organizer);
        ll_Name_of_Organizer = (LinearLayout) findViewById(R.id.ll_Name_of_Organizer);
        edt_lvl_jou = (EditText) findViewById(R.id.edt_lvl_jou);
        iv_calendar = (ImageView) findViewById(R.id.iv_calendar);
        ll_lvl_jou = (LinearLayout) findViewById(R.id.ll_lvl_jou);
        edt_from_date = (EditText) findViewById(R.id.edt_from_date);
        ll_from_date = (LinearLayout) findViewById(R.id.ll_from_date);
        edt_to_date = (EditText) findViewById(R.id.edt_to_date);
        ll_to_date = (LinearLayout) findViewById(R.id.ll_to_date);
        edt_type_auth = (EditText) findViewById(R.id.edt_type_auth);
        ll_type_auth = (LinearLayout) findViewById(R.id.ll_type_auth);
        edt_ugc_care = (EditText) findViewById(R.id.edt_ugc_care);
        ll_ugc_care = (LinearLayout) findViewById(R.id.ll_ugc_care);
        edt_level_jou = (EditText) findViewById(R.id.edt_level_jou);
        ll_level_jou = (LinearLayout) findViewById(R.id.ll_level_jou);
        edt_isbn = (EditText) findViewById(R.id.edt_isbn);
        ll_isbn = (LinearLayout) findViewById(R.id.ll_isbn);
        edt_app_date = (EditText) findViewById(R.id.edt_app_date);
        ll_approved_dt = (LinearLayout) findViewById(R.id.ll_approved_dt);
        edt_app_by = (EditText) findViewById(R.id.edt_app_by);
        ll_approved_by = (LinearLayout) findViewById(R.id.ll_approved_by);
        iv_file = (ImageView) findViewById(R.id.iv_file);
        //  ll_doc = (LinearLayout) findViewById(R.id.ll_doc);
        tv_submit = (CustomBoldTextView) findViewById(R.id.tv_submit);
        tv_cancel = (CustomBoldTextView) findViewById(R.id.tv_cancel);
        ll_submit_cancel = (LinearLayout) findViewById(R.id.ll_submit_cancel);


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

    public void showDialog(final Context context, final String approve_reject, final String ID) {

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
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isvalidate(edt_reason)) {
                    show.dismiss();


                    if (approve_reject.compareToIgnoreCase("approve") == 0) {
                        ApproveConfarancePub(edt_reason.getText().toString().trim(), show, ID, "approve");
                    } else {
                        ApproveConfarancePub(edt_reason.getText().toString().trim(), show, ID, "reject");
                        // RejectLeave(edt_reason.getText().toString().trim(), show, pos,ID,"reject");
                    }

                }
            }
        });
    }

    ConAppRejPojo conAppRejPojo;

    private void ApproveConfarancePub(String reason, AlertDialog show, final String ID, String app_rej) {

        DialogUtils.showProgressDialog(FDPAttendedDetail.this, "");
        String url;

        if (app_rej.compareToIgnoreCase("approve") == 0) {
            url = URLS.Get_FDP_Attended_Approved_or_Reject + "&id=" + ID + "&remarks=" + reason + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "" + "&approve_reject=" + "1" + "";

        } else {
            url = URLS.Get_FDP_Attended_Approved_or_Reject + "&id=" + ID + "&remarks=" + reason + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "" + "&approve_reject=" + "2" + "";

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
                            if (conAppRejPojo != null) {
                                if (conAppRejPojo.getData() != null) {
                                    if (conAppRejPojo.getData().get(0) != null && conAppRejPojo.getData().size() > 0) {

                                        if (!conAppRejPojo.getData().get(0).getMsg().contentEquals("")) {
                                            DialogUtils.Show_Toast(FDPAttendedDetail.this, conAppRejPojo.getData().get(0).getMsg());

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
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);


    }


    private boolean isvalidate(EditText edt_reason) {
        if (edt_reason.getText().toString().trim().isEmpty() || edt_reason.getText().toString().contentEquals("") || edt_reason.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(FDPAttendedDetail.this, "Enter Reason");
            return false;
        }

        return true;
    }

}
