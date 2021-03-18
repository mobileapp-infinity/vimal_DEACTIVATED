package com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
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
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.SalaryDwnloadPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.SalarySlipPojo;
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

public class SalarySlipAdapter extends BaseAdapter {

    Context ctx;
    MarshMallowPermission marshMallowPermission;

    SalarySlipPojo salarySlipPojo;

    MySharedPrefereces mySharedPreferenses;

    View view_;


    Activity activity;
    RequestQueue queue;

    public SalarySlipAdapter(Context ctx, SalarySlipPojo salarySlipPojo,Activity activity) {

        this.ctx = ctx;
        this.salarySlipPojo = salarySlipPojo;

        this.activity=activity;
        marshMallowPermission = new MarshMallowPermission(activity);
        mySharedPreferenses = new MySharedPrefereces(ctx);
        queue = Volley.newRequestQueue(ctx);
        System.out.println("call ################ ");

    }

    class ViewHolder {


        private CustomTextView tv_month;
        private CustomTextView tv_amount;
        LinearLayout ll_main;

        ImageView iv_download;
    }

    ViewHolder viewHolder;

    @Override
    public int getCount() {
        // return college.getTable().size();

        return salarySlipPojo.getData().size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = LayoutInflater.from(ctx);

        view_ = view;
        if (view_ == null) {
            view_ = mInflater.inflate(R.layout.adapter_salary, viewGroup, false);

            viewHolder = new ViewHolder();
            initView(view_);
            view_.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view_.getTag();
        }


        viewHolder.tv_month.setText(salarySlipPojo.getData().get(i).getMonth_Name() + "");
        viewHolder.tv_amount.setText(salarySlipPojo.getData().get(i).getEssm_net_salary() + "");
        viewHolder.iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (i % 2 == 0) {
            viewHolder.ll_main.setBackgroundColor(ctx.getResources().getColor(R.color.test));
        }

        viewHolder.iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SalarySlipDownload(i);


            }
        });

        return view_;
    }


    SalaryDwnloadPojo salaryDwnloadPojo;

    private void SalarySlipDownload(int pos) {
        String url = URLS.Get_Employee_salary_slip_download + "&id=" + salarySlipPojo.getData().get(pos).getId() + "&user_name=" + mySharedPreferenses.getUserID() + "";


        System.out.println("Get_Employee_salary_slip_download URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                // System.out.println("response of Get_miss_pucn_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                if (response.length() > 5) {


                    System.out.println("response of salary slip ::::: "+response.toString());

                    //   System.out.println("sucess response Get_miss_pucn_approve_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    salaryDwnloadPojo = gson.fromJson(response, SalaryDwnloadPojo.class);


                    if (salaryDwnloadPojo != null) {
                        marshMallowPermission = new MarshMallowPermission(activity);
                        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                            marshMallowPermission.requestPermissionForExternalStorage();
                        } else {

                            String url_salary_slip = salaryDwnloadPojo.getURL();
                            System.out.println("url_salary_slip ::::::::::::::::::::: "+url_salary_slip);

                            String[] file2 = url_salary_slip.split("/");


                            System.out.println("file after split::::::>>>>***" + String.valueOf(file2));
                            System.out.println("file after split::::::" + file2.toString());

                            String result1 = file2[file2.length - 1];

                            System.out.println("result1::::" + result1);
                            String nameoffile1 = result1;


                            System.out.println("nameoffile1****" + System.currentTimeMillis() + "_" + nameoffile1);


                            String url = url_salary_slip;
                            if (url != null && url.length() > 5) {
                                String extention = url.substring(url.lastIndexOf("."), url.length());
                                //Log.d("syllabuspdfurl", syllabusdetail.get(position).getPdf());
                                new DownloadFileFromURL(extention).execute(url);
                            } else {
                                DialogUtils.Show_Toast(ctx, "No Documents Available");
                            }

                        }

                    }

                    else
                    {
                        System.out.println("nulllllllllllllll ");
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(ctx, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void initView(@NonNull final View itemView) {


        viewHolder.ll_main = (LinearLayout) itemView.findViewById(R.id.ll_main);
        viewHolder.tv_month = (CustomTextView) itemView.findViewById(R.id.tv_month);
        viewHolder.tv_amount = (CustomTextView) itemView.findViewById(R.id.tv_amount);
        viewHolder.iv_download = (ImageView) itemView.findViewById(R.id.iv_download);
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
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Infinity/"+"/Salary_slip/");
                dir.mkdirs();



                System.out.println("path of file>>>>>" + dir.getAbsolutePath());
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                //  Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());


                OutputStream output = new FileOutputStream("sdcard/Infinity/"+"/Salary_slip/" + nameoffile);

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

            DialogUtils.Show_Toast(ctx, "Download Completed Successfully");

            System.out.println("EXTENSION OF FILE onPostExecute::::::::::" + Extenson);

            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Infinity/"+"/Salary_slip/" + nameoffile);
            Log.d("pathoffile", String.valueOf(file11));
//                                    Uri uri = FileProvider.getUriForFile(AssignmentActivity.this, "com.infinity.infoway.atmiya.activity.AssignmentActivity.fileprovider", file11);
//

            /// for  opening downloaded documentssssssssssss
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24)
            {


                System.out.println("path of file is :::::::::::::: "+file11.getPath());
                Uri uri = FileProvider.getUriForFile(ctx, ctx.getPackageName() + ".fileprovider", file11);
//                Uri uri = FileProvider.getUriForFile(_context, _context.getPackageName() , file11);
                ctx.grantUriPermission(ctx.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = null;
                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                }
                intent = Intent.createChooser(target, "Open File");
                try {
                    ctx.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(ctx, "No Apps can performs This acttion");
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
                    ctx.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(ctx, "No Apps can performs This acttion");

                }

            }


        }

    }
}
