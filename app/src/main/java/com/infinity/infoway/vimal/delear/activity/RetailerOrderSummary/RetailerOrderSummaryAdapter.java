package com.infinity.infoway.vimal.delear.activity.RetailerOrderSummary;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;

public class RetailerOrderSummaryAdapter extends RecyclerView.Adapter<RetailerOrderSummaryAdapter.MyViewHolder> {


    /**
     * Created on 26-09-2020 by harsh
     **/
    Context context;

    Get_Retailer_Order_Summary_Report_Pojo get_retailer_order_summary_report_pojo;

    public RetailerOrderSummaryAdapter(Context context, Get_Retailer_Order_Summary_Report_Pojo get_retailer_order_summary_report_pojo) {
        this.context = context;
        this.get_retailer_order_summary_report_pojo = get_retailer_order_summary_report_pojo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.retailer_order_summary_single_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        if (position == 0) {
            holder.ll_header.setVisibility(View.VISIBLE);
            holder.first_divider.setVisibility(View.VISIBLE);
        } else {
            holder.first_divider.setVisibility(View.GONE);
            holder.ll_header.setVisibility(View.GONE);
        }



        if (position == get_retailer_order_summary_report_pojo.getRECORDS().size() - 1) {
            holder.last_divider.setVisibility(View.VISIBLE);
            holder.ll_total.setVisibility(View.VISIBLE);
        } else {
            holder.last_divider.setVisibility(View.GONE);
            holder.ll_total.setVisibility(View.GONE);
        }

        if (get_retailer_order_summary_report_pojo.getRECORDS().get(position).getArea_Name() == null) {
            holder.tv_area_name.setText("-");

        } else {
            holder.tv_area_name.setText(get_retailer_order_summary_report_pojo.getRECORDS().get(position).getArea_Name() + "");
        }


        if (get_retailer_order_summary_report_pojo.getRECORDS().get(position).getRetailer_Name() == null) {
            holder.tv_retailer_name.setText("-");

        } else {
            holder.tv_retailer_name.setText(get_retailer_order_summary_report_pojo.getRECORDS().get(position).getRetailer_Name() + "");
        }


        if (get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_Pcs() + "" == null) {
            holder.tv_total_pic.setText("-");

        } else {
            holder.tv_total_pic.setText(get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_Pcs() + "");
        }


        if (get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_Box() + "" == null) {
            holder.tv_total_box.setText("-");

        } else {
            holder.tv_total_box.setText(get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_Box() + "");
        }


        if (get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_Amount() + "" == null) {
            holder.tv_amount.setText("-");

        } else {
            holder.tv_amount.setText(get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_Amount() + "");
        }

        if (get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_carat() + "" == null) {
            holder.tv_total_caret.setText("-");

        } else {
            holder.tv_total_caret.setText(get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_carat() + "");
        }

        if (get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_Ltr() + "" == null) {
            holder.tv_total_ltr.setText("-");

        } else {
            holder.tv_total_ltr.setText(get_retailer_order_summary_report_pojo.getRECORDS().get(position).getTotal_Ltr() + "");
        }


        holder.tv_total_pic_total.setText(grandPicTotal() + "");
        holder.tv_total_box_total.setText(grandBoxTotal() + "");
        holder.tv_total_amount_total.setText(grandAmountTotal() + "");


        holder.tv_total_ltr_total.setText(grandTotalLtr() + "");
        holder.tv_total_caret_total.setText(grandTotalCarat() + "");


    }

    @Override
    public int getItemCount() {
        return get_retailer_order_summary_report_pojo.getRECORDS().size();
    }


    private double grandPicTotal() {
        double totalPic = 0;
        for (int i = 0; i < get_retailer_order_summary_report_pojo.getRECORDS().size(); i++) {
            totalPic += get_retailer_order_summary_report_pojo.getRECORDS().get(i).getTotal_Pcs();
        }
        return totalPic;
    }

    private double grandBoxTotal() {
        double totalBox = 0.00;
        for (int i = 0; i < get_retailer_order_summary_report_pojo.getRECORDS().size(); i++) {
            totalBox += get_retailer_order_summary_report_pojo.getRECORDS().get(i).getTotal_Box();
        }
        return totalBox;
    }

    private double grandAmountTotal() {
        double totalAmount = 0.00;
        for (int i = 0; i < get_retailer_order_summary_report_pojo.getRECORDS().size(); i++) {
            totalAmount += get_retailer_order_summary_report_pojo.getRECORDS().get(i).getTotal_Amount();
        }
        return totalAmount;
    }

    private double grandTotalCarat() {
        double totalCarat = 0.00;
        for (int i = 0; i < get_retailer_order_summary_report_pojo.getRECORDS().size(); i++) {
            totalCarat += get_retailer_order_summary_report_pojo.getRECORDS().get(i).getTotal_carat();
        }
        return totalCarat;
    }

    private double grandTotalLtr() {
        double totalLtr = 0.00;
        for (int i = 0; i < get_retailer_order_summary_report_pojo.getRECORDS().size(); i++) {
            totalLtr += get_retailer_order_summary_report_pojo.getRECORDS().get(i).getTotal_Ltr();
        }
        return totalLtr;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_retailer_name, tv_area_name, tv_total_box, tv_amount, tv_total_pic_total, tv_total_box_total, tv_total_amount_total, tv_total_pic, tv_total_caret, tv_total_ltr, tv_total_ltr_total, tv_total_caret_total;
        LinearLayout ll_header;
        LinearLayout ll_total;
        View first_divider, last_divider;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_retailer_name = itemView.findViewById(R.id.tv_retailer_name);
            tv_total_pic = itemView.findViewById(R.id.tv_total_pic);
            tv_area_name = itemView.findViewById(R.id.tv_area_name);
            tv_total_box = itemView.findViewById(R.id.tv_total_box);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_total_pic_total = itemView.findViewById(R.id.tv_total_pic_total);
            tv_total_box_total = itemView.findViewById(R.id.tv_total_box_total);
            tv_total_amount_total = itemView.findViewById(R.id.tv_total_amount_total);
            tv_total_caret = itemView.findViewById(R.id.tv_total_caret);
            tv_total_ltr = itemView.findViewById(R.id.tv_total_ltr);
            tv_total_ltr_total = itemView.findViewById(R.id.tv_total_ltr_total);
            tv_total_caret_total = itemView.findViewById(R.id.tv_total_caret_total);

            first_divider = itemView.findViewById(R.id.first_divider);
            last_divider = itemView.findViewById(R.id.last_divider);
            ll_header = itemView.findViewById(R.id.ll_header);
            ll_total = itemView.findViewById(R.id.ll_total);
        }
    }
}
