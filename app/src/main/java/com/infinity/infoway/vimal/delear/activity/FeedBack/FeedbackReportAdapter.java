package com.infinity.infoway.vimal.delear.activity.FeedBack;

import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;

public class FeedbackReportAdapter extends RecyclerView.Adapter<FeedbackReportAdapter.MyViewHolder> {

    Context context;
    Get_Sale_RoutWise_Feedback_Report_Pojo get_sale_routWise_feedback_report_pojo;

    public FeedbackReportAdapter(Context context, Get_Sale_RoutWise_Feedback_Report_Pojo get_sale_routWise_feedback_report_pojo) {
        this.context = context;
        this.get_sale_routWise_feedback_report_pojo = get_sale_routWise_feedback_report_pojo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.feedback_report_single_view, parent, false);
        View view = LayoutInflater.from(context).inflate(R.layout.feedback_report_view_single_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

      /*  if (position == 0) {

            holder.ll_header.setVisibility(View.VISIBLE);

        } else {
            holder.ll_header.setVisibility(View.INVISIBLE);
        }*/


       /* if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getSrNo()+"" != null){
            holder.tv_sr_no.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getSrNo()+"");
        }else {
            holder.tv_sr_no.setText("-");
        }
*/
        if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDate() != null) {
            holder.tv_date.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDate() + "");
        } else {
            holder.tv_date.setText("-");
        }

        if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistributor_Name() != null) {
            holder.tv_distriutor_name.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistributor_Name() + "");
        } else {
            holder.tv_distriutor_name.setText("-");
        }


       /* if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistributor_City() != null){
            holder.tv_city.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistributor_City()+"");
        }else {
            holder.tv_city.setText("-");
        }*/

        if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getShop_Name() != null) {
            holder.tv_shop_name.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getShop_Name() + "");
        } else {
            holder.tv_shop_name.setText("-");
        }


        if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getRetailer_Name() != null) {
            holder.tv_ret_name.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getRetailer_Name() + "");
        } else {
            holder.tv_ret_name.setText("-");
        }


        if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getMobile_No() != null) {
            holder.tv_mobile.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getMobile_No() + "");
        } else {
            holder.tv_mobile.setText("-");
        }


     /*   if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getArea() != null){
            holder.tv_area.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getArea()+"");
        }else {
            holder.tv_area.setText("-");
        }*/


     /*   if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getVillage() != null){
            holder.tv_village.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getVillage()+"");
        }else {
            holder.tv_village.setText("-");
        }*/

       /* if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getArea() != null){
            holder.tv_area.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getArea()+"");
        }else {
            holder.tv_area.setText("-");
        }*/


      /*  if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getCity() != null){
            holder.tv_city_2.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getCity()+"");
        }else {
            holder.tv_city_2.setText("-");
        }*/

      /*  if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistrict() != null){
            holder.tv_district.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistrict()+"");
        }else {
            holder.tv_district.setText("-");
        }*/

        if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getFeedback() != null) {
            holder.tv_feedback.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getFeedback() + "");
        } else {
            holder.tv_feedback.setText("-");
        }


        holder.btnViewDetials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.app.AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.feedback_alert_view, null, false);


                alertDialog.setView(view);
                TextView tv_date_alert = view.findViewById(R.id.tv_date_alert);
                TextView tv_distriutor_name_alert = view.findViewById(R.id.tv_distriutor_name_alert);
                TextView tv_city_alert = view.findViewById(R.id.tv_city_alert);
                TextView tv_shop_name_alert = view.findViewById(R.id.tv_shop_name_alert);
                TextView tv_ret_name_alert = view.findViewById(R.id.tv_ret_name_alert);
                TextView tv_mobile_alert = view.findViewById(R.id.tv_mobile_alert);
                TextView tv_area_alert = view.findViewById(R.id.tv_area_alert);
                TextView tv_village_aleret = view.findViewById(R.id.tv_village_aleret);
                TextView tv_city2_alert = view.findViewById(R.id.tv_city2_alert);
                TextView tv_district_alert = view.findViewById(R.id.tv_district_alert);
                TextView tv_feedback_alert = view.findViewById(R.id.tv_feedback_alert);

               /* ImageView close = view.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });*/

                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDate() != null) {
                    tv_date_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDate() + "");
                } else {
                    tv_date_alert.setText("-");
                }

                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistributor_Name() != null) {
                    tv_distriutor_name_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistributor_Name() + "");
                } else {
                    tv_distriutor_name_alert.setText("-");
                }

                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistributor_City() != null) {
                    tv_city_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistributor_City() + "");
                } else {
                    tv_city_alert.setText("-");
                }


                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getShop_Name() != null) {
                    tv_shop_name_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getShop_Name() + "");
                } else {
                    tv_shop_name_alert.setText("-");
                }


                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getRetailer_Name() != null) {
                    tv_ret_name_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getRetailer_Name() + "");
                } else {
                    tv_ret_name_alert.setText("-");
                }

                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getMobile_No() != null) {
                    tv_mobile_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getMobile_No() + "");
                } else {
                    tv_mobile_alert.setText("-");
                }

                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getArea() != null) {
                    tv_area_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getArea() + "");
                } else {
                    tv_area_alert.setText("-");
                }

                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getVillage() != null) {
                    tv_village_aleret.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getVillage() + "");
                } else {
                    tv_village_aleret.setText("-");
                }

                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getCity() != null) {
                    tv_city2_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getCity() + "");
                } else {
                    tv_city2_alert.setText("-");
                }

                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistrict() != null) {
                    tv_district_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getDistrict() + "");
                } else {
                    tv_district_alert.setText("-");
                }

                if (get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getFeedback() != null) {
                    tv_feedback_alert.setText(get_sale_routWise_feedback_report_pojo.getRECORDS().get(position).getFeedback() + "");
                } else {
                    tv_feedback_alert.setText("-");
                }














                alertDialog.show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return get_sale_routWise_feedback_report_pojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // LinearLayout ll_header;
        // old horizontal layout
        // TextView tv_sr_no,tv_date,tv_distriutor_name,tv_city,tv_shop_name,tv_ret_name,tv_mobile,tv_area,tv_city_2,tv_district,tv_feedback,tv_village;

        //vertical layout
        TextView tv_distriutor_name, tv_shop_name, tv_ret_name, tv_mobile, tv_feedback, tv_date;
        TextView btnViewDetials;


        public MyViewHolder(View itemView) {
            super(itemView);

            /*ll_header = itemView.findViewById(R.id.ll_header);
            tv_sr_no = itemView.findViewById(R.id.tv_sr_no);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_distriutor_name = itemView.findViewById(R.id.tv_distriutor_name);
            tv_city = itemView.findViewById(R.id.tv_city);
            tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
            tv_ret_name = itemView.findViewById(R.id.tv_ret_name);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tv_village = itemView.findViewById(R.id.tv_village);


           tv_area = itemView.findViewById(R.id.tv_area);
            tv_city_2 = itemView.findViewById(R.id.tv_city_2);
            tv_district = itemView.findViewById(R.id.tv_district);
            tv_feedback = itemView.findViewById(R.id.tv_feedback);*/

            btnViewDetials = itemView.findViewById(R.id.btnViewDetials);
            tv_distriutor_name = itemView.findViewById(R.id.tv_distriutor_name);
            tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
            tv_ret_name = itemView.findViewById(R.id.tv_ret_name);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tv_feedback = itemView.findViewById(R.id.tv_feedback);
            tv_date = itemView.findViewById(R.id.tv_date);


        }
    }
}
