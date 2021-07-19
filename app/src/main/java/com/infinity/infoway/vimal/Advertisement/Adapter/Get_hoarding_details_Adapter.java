package com.infinity.infoway.vimal.Advertisement.Adapter;

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
import com.infinity.infoway.vimal.Advertisement.Pojo.Adv_ListingPojo;
import com.infinity.infoway.vimal.DeepFridge.Pojo.GetFridge_Request_MasterPojo;
import com.infinity.infoway.vimal.DeepFridge.Pojo.PrintAgreementPOJO;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.FeedBack.Get_Distributor_Wise_Retailer_Pojo;
import com.infinity.infoway.vimal.delear.activity.FeedBack.RetailerAdapter;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Pragna on 16-7-2021. for fridge listing adapter
 */

public class Get_hoarding_details_Adapter extends BaseAdapter {
    private Get_hoarding_details_Adapter.OnItemClickListner onItemClickListner;


    private Context ctx;
    ViewHolder holder = null;
    public FragmentManager f_manager;
    private LayoutInflater inflater;
    SharedPreferences prefs;
    View view;
    Activity a;
    Boolean b;
    Adv_ListingPojo pojo;
    Activity _activity;
    RequestQueue queue;
    public Get_hoarding_details_Adapter(Context ctx, Adv_ListingPojo pojo, Activity _activity, OnItemClickListner onItemClickListner) {

        this.ctx = ctx;
        this.b = b;
        inflater = LayoutInflater.from(this.ctx);
        //this.pojo = new GetFridge_Request_MasterPojo.RECORDSBean();
        this.pojo = pojo;
        this. _activity=_activity;
        getSharedPref = new SharedPref(this._activity);
        queue = Volley.newRequestQueue(this._activity);
        this.onItemClickListner = onItemClickListner;
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
        CustomButtonView btn_view,btn_edit,btn_approve;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.adv_listing_adapter, null);
            holder = new ViewHolder();
            holder.tv_1 = (TextView) view.findViewById(R.id.tv_1);
            holder.tv_2 = (TextView) view.findViewById(R.id.tv_2);
            holder.tv_3 = (TextView) view.findViewById(R.id.tv_3);
            holder.tv_4 = (TextView) view.findViewById(R.id.tv_4);
            holder.tv_5 = (TextView) view.findViewById(R.id.tv_5);
            holder.tv_6 = (TextView) view.findViewById(R.id.tv_6);
            holder.btn_view = (CustomButtonView) view.findViewById(R.id.btn_view);
            holder.btn_edit = (CustomButtonView) view.findViewById(R.id.btn_edit);
            holder.btn_approve = (CustomButtonView) view.findViewById(R.id.btn_approve);
            holder.tv_aggrement = (TextView) view.findViewById(R.id.tv_aggrement);
            holder.tv_confirmation = (TextView) view.findViewById(R.id.tv_confirmation);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_1.setText(pojo.RECORDS.get(position).hrm_no + "");
        holder.tv_2.setText(pojo.RECORDS.get(position).hrm_applier_name + "");
        holder.tv_3.setText(pojo.RECORDS.get(position).hrm_hoarding_print_name + "");
        holder.tv_4.setText(pojo.RECORDS.get(position).hrm_address1 + "");
        holder.tv_5.setText(pojo.RECORDS.get(position).hrm_cit_id + "");
        holder.tv_6.setText(pojo.RECORDS.get(position).ven_name + "");

        holder.tv_aggrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Get_Print_Aggrement(pojo.RECORDS.get(position).id);
            }
        });holder.tv_confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Get_Fridge_Confirmation_Print(pojo.RECORDS.get(position).id);
            }
        });


        holder.btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onItemClicked(position,pojo.RECORDS.get(position));
            }
        });
        return view;
    }
    SharedPref getSharedPref;
    String extension;
    public interface OnItemClickListner{
        public void onItemClicked(int position, Adv_ListingPojo.RECORDSBean recordsBean);
    }


}