package com.infinity.infoway.vimal.delear.activity.UpdateCallList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.OrderPlaceToCompanyActivity;


public class UpdateCallListAdapter extends RecyclerView.Adapter<UpdateCallListAdapter.MyViewHolder> {

    Context context;
    Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo get_retailer_rout_detail_of_login_distributor_pojo;


    public UpdateCallListAdapter(Context context, Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo get_retailer_rout_detail_of_login_distributor_pojo) {
        this.context = context;
        this.get_retailer_rout_detail_of_login_distributor_pojo = get_retailer_rout_detail_of_login_distributor_pojo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.update_call_list_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getCus_Name() != null) {
            holder.tv_customer_name.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getCus_Name() + "");
        } else {
            holder.tv_customer_name.setText("-");
        }

        if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getContact_Person() != null) {
            holder.tv_contact_person.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getContact_Person() + "");
        } else {
            holder.tv_contact_person.setText("-");
        }

        if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getShop_Name() != null) {
            holder.tv_shop_name.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getShop_Name() + "");
        } else {
            holder.tv_shop_name.setText("-");
        }

        if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getMobile_No() != null) {
            holder.tv_mobile.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getMobile_No() + "");
        } else {
            holder.tv_mobile.setText("-");
        }

        holder.btnViewDetials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.app.AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.update_call_list_alert, null, false);


                alertDialog.setView(view);
                TextView tv_customer_alert = view.findViewById(R.id.tv_customer_alert);
                TextView tv_contact_person_alert = view.findViewById(R.id.tv_contact_person_alert);
                TextView tv_shop_name_alert = view.findViewById(R.id.tv_shop_name_alert);
                TextView tv_mobile_alert = view.findViewById(R.id.tv_mobile_alert);
                TextView tv_full_address_alert = view.findViewById(R.id.tv_full_address_alert);
                TextView tv_pin_alert = view.findViewById(R.id.tv_pin_alert);
                TextView tv_city2_alert = view.findViewById(R.id.tv_city2_alert);
                TextView tv_district_alert = view.findViewById(R.id.tv_district_alert);
                TextView tv_state_alert = view.findViewById(R.id.tv_state_alert);

                if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getCus_Name() != null) {
                    tv_customer_alert.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getCus_Name() + "");
                } else {
                    tv_customer_alert.setText("-");
                }

                if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getContact_Person() != null) {
                    tv_contact_person_alert.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getContact_Person() + "");
                } else {
                    tv_contact_person_alert.setText("-");
                }

                if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getShop_Name() != null) {
                    tv_shop_name_alert.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getShop_Name() + "");
                } else {
                    tv_shop_name_alert.setText("-");
                }

                if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getMobile_No() != null) {
                    tv_mobile_alert.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getMobile_No() + "");
                } else {
                    tv_mobile_alert.setText("-");
                }
                if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getFull_address() != null) {
                    tv_full_address_alert.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getFull_address() + "");
                } else {
                    tv_full_address_alert.setText("-");
                }

                if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getPinCode() != null) {
                    tv_pin_alert.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getPinCode() + "");
                } else {
                    tv_pin_alert.setText("-");
                }


                if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getCity_Name() != null) {
                    tv_city2_alert.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getCity_Name() + "");
                } else {
                    tv_city2_alert.setText("-");
                }


                if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getDistrict_Name() != null) {
                    tv_district_alert.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getDistrict_Name() + "");
                } else {
                    tv_district_alert.setText("-");
                }


                if (get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getState_Name() != null) {
                    tv_state_alert.setText(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getState_Name() + "");
                } else {
                    tv_state_alert.setText("-");
                }

                alertDialog.show();

            }
        });

        holder.tvCreateSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent slaeOrdeIntent = new Intent(context, OrderPlaceToCompanyActivity.class);
                //  slaeOrdeIntent.putExtra("routeId", "70");
                //slaeOrdeIntent.putExtra("CustId", "412");
                // slaeOrdeIntent.putExtra("vehicleNO", "Gj03R3322");
                slaeOrdeIntent.putExtra("CustId", get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getId() + "");
                slaeOrdeIntent.putExtra("routeId", get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getRoute_id() + "");
                slaeOrdeIntent.putExtra("vehicleNO", get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(position).getRvpm_vehicle_no() + "");
                slaeOrdeIntent.putExtra("title_screen", "Place Order");
                context.startActivity(slaeOrdeIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_customer_name, tv_shop_name, tv_contact_person, tv_mobile, tvCreateSo;
        TextView btnViewDetials;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_customer_name = itemView.findViewById(R.id.tv_customer_name);
            tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
            tv_contact_person = itemView.findViewById(R.id.tv_contact_person);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            btnViewDetials = itemView.findViewById(R.id.btnViewDetials);
            tvCreateSo = itemView.findViewById(R.id.tvCreateSo);

        }
    }
}
