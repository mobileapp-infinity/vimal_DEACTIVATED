package com.infinity.infoway.vimal.DeepFridge.Adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.DeepFridge.Fridge_Listing;
import com.infinity.infoway.vimal.DeepFridge.Fridge_Request_Add;
import com.infinity.infoway.vimal.DeepFridge.Pojo.GetCity_List_POJO;
import com.infinity.infoway.vimal.DeepFridge.Pojo.GetFridge_Request_MasterPojo;
import com.infinity.infoway.vimal.DeepFridge.Pojo.PrintAgreementPOJO;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.PerFormInvoiceAndLedgerActivity;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Pragna on 06-6-2021. for fridge listing adapter
 */

public class GetFridge_Request_MasterAdapter extends BaseAdapter {


    private Context ctx;
    ViewHolder holder = null;
    public FragmentManager f_manager;
    private LayoutInflater inflater;
    SharedPreferences prefs;
    View view;
    Activity a;
    Boolean b;
    GetFridge_Request_MasterPojo pojo;
    Activity _activity;
    RequestQueue queue;
    public GetFridge_Request_MasterAdapter(Context ctx, GetFridge_Request_MasterPojo pojo,Activity _activity) {

        this.ctx = ctx;
        this.b = b;
        inflater = LayoutInflater.from(this.ctx);
        //this.pojo = new GetFridge_Request_MasterPojo.RECORDSBean();
        this.pojo = pojo;
       this. _activity=_activity;
        getSharedPref = new SharedPref(this._activity);
        queue = Volley.newRequestQueue(this._activity);

    }

    @Override
    public int getCount() {
        // return customBenifitList.size();
        return pojo.RECORDS.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
        //  return customBenifitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {

        CheckBox itemCheckBox;

        private TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_aggrement,tv_confirmation;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.fridge_listing_adapter, null);
            holder = new ViewHolder();
            holder.tv_1 = (TextView) view.findViewById(R.id.tv_1);
            holder.tv_2 = (TextView) view.findViewById(R.id.tv_2);
            holder.tv_3 = (TextView) view.findViewById(R.id.tv_3);
            holder.tv_4 = (TextView) view.findViewById(R.id.tv_4);
            holder.tv_5 = (TextView) view.findViewById(R.id.tv_5);
            holder.tv_6 = (TextView) view.findViewById(R.id.tv_6);
            holder.tv_aggrement = (TextView) view.findViewById(R.id.tv_aggrement);
            holder.tv_confirmation = (TextView) view.findViewById(R.id.tv_confirmation);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_1.setText(pojo.RECORDS.get(position).frm_no + "");
        holder.tv_2.setText(pojo.RECORDS.get(position).frm_date + "");
        holder.tv_3.setText(pojo.RECORDS.get(position).cus_name + "");
        holder.tv_4.setText(pojo.RECORDS.get(position).frm_retailer_name + "");
        holder.tv_5.setText(pojo.RECORDS.get(position).frm_company_name + "");
        holder.tv_6.setText(pojo.RECORDS.get(position).Fridge_Size + "");

        holder.tv_aggrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Get_Print_Aggrement(pojo.RECORDS.get(position).id);
            }
        });holder.tv_confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Get_Fridge_Confirmation_Print(pojo.RECORDS.get(position).id);
            }
        });
        return view;
    }
    SharedPref getSharedPref;
    String extension;
    private void Get_Print_Aggrement(int frm_id) {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
        DialogUtils.showProgressDialog(_activity, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_Print_Aggrement + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&del_date=" + "" + "&frm_id=" + frm_id+"";
        //  list_Fridge_Type = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_Print_Aggrement URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);

                Gson gson = new Gson();
//                list_all_city = new ArrayList<>();
//                list_all_city.add("Select City");
                PrintAgreementPOJO printAgreementPOJO =new PrintAgreementPOJO();

                // getCity_list_pojo = new GetFridge_Request_MasterPojo();
                printAgreementPOJO = gson.fromJson(response, PrintAgreementPOJO.class);

                if (printAgreementPOJO != null&&printAgreementPOJO.getDocuument_file()!=null){// &&
                    String baser64String = printAgreementPOJO.getDocuument_file();
                   download_andOpen_PDF(baser64String+"");
                } else {

                    DialogUtils.Show_Toast(_activity    ,ctx.getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }
                DialogUtils.hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    private void download_andOpen_PDF(String baser64String) {
        try {
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Fridge/");
            folder.mkdirs();


            File file = new File(folder, "file.pdf");
            file.createNewFile();
            FileOutputStream output = new FileOutputStream(file);
            extension = file.toString().substring(file.toString().lastIndexOf("."), file.toString().length());
            System.out.println("hi" + extension);
            byte[] pdfAsBytes = Base64.decode(baser64String, Base64.NO_WRAP);
            output.write(pdfAsBytes);
            output.flush();
            // closing streams
            output.close();

            //open file/////
            File file1 = new File(folder + "/file.pdf" + "");
            System.out.println(file1);
            Uri uri = FileProvider.getUriForFile(  _activity,   _activity.getPackageName() + ".provider", file1);

            _activity.grantUriPermission(  _activity.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent intent = null;
            if (extension.compareToIgnoreCase(".pdf") == 0 || extension.compareToIgnoreCase("pdf") == 0) {
                target.setDataAndType(uri, "application/pdf");
            }
            intent = Intent.createChooser(target, "Open File");
            try {
                _activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // DialogUtils.Show_Toast(ctx, "No Apps can performs This action");
            }






        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Toast.makeText(  _activity, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void Get_Fridge_Confirmation_Print(int frm_id) {
        DialogUtils.showProgressDialog(_activity, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_Fridge_Confirmation_Print + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&del_date=" + "" + "&frm_id=" + frm_id+"";
        //  list_Fridge_Type = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_Fridge_Confirmation_Print URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);

                Gson gson = new Gson();
//                list_all_city = new ArrayList<>();
//                list_all_city.add("Select City");
                PrintAgreementPOJO pojo =new PrintAgreementPOJO();

                // getCity_list_pojo = new GetFridge_Request_MasterPojo();
                pojo = gson.fromJson(response, PrintAgreementPOJO.class);

                if (pojo != null&&pojo.getDocuument_file()!=null){// &&
                    //    printAgreementPOJO. != null) {
//                    if (printAgreementPOJO.getTOTAL() > 0) {
//
//
//                    } else {
//                        DialogUtils.Show_Toast(_activity, ctx.getResources().getString(R.string.no_data_available));
//                        //  lv.setVisibility(View.INVISIBLE);
//                    }
                    String baser64String = pojo.getDocuument_file();
                    download_andOpen_PDF(baser64String+"");
                } else {

                    DialogUtils.Show_Toast(_activity    ,ctx.getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }
                DialogUtils.hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }


}