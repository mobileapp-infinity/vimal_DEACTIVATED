package com.infinity.infoway.vimal.delear.activity.SalesAndStockDetails;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;

public class SalesAndStockDetailsAdapter extends RecyclerView.Adapter<SalesAndStockDetailsAdapter.MyViewHolder> {
    private Context context;
    private Get_Distributor_Wise_Sales_and_Stock_Report_Pojo get_distributor_wise_sales_and_stock_report_pojo;

    public SalesAndStockDetailsAdapter(Context context, Get_Distributor_Wise_Sales_and_Stock_Report_Pojo get_distributor_wise_sales_and_stock_report_pojo) {
        this.context = context;
        this.get_distributor_wise_sales_and_stock_report_pojo = get_distributor_wise_sales_and_stock_report_pojo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sales_and_stock_view, parent, false);
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


        if( position == get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().size() - 1 ){
            holder.last_divider.setVisibility(View.VISIBLE);
            holder.ll_total.setVisibility(View.VISIBLE);
        }else{
            holder.last_divider.setVisibility(View.GONE);
            holder.ll_total.setVisibility(View.GONE);
        }
        if (!get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(position).getProduct_Name().contentEquals("")) {
            holder.tv_product_name.setText(get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(position).getProduct_Name());
        }


        if (!get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(position).getSize().contentEquals("")) {
            holder.tv_ml.setText(get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(position).getSize());
        }


        holder.tv_op_stock.setText(get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(position).getOpening() + "");
        holder.tv_cl_stock.setText(get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(position).getClosing() + "");
        holder.tv_purchase.setText(get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(position).getPurchase() + "");
        holder.tv_sales.setText(get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(position).getSales() + "");
        holder.tv_op_stock_total.setText(grandOpeningTotal()+ "");
        holder.tv_purchase_total.setText(grandPurchaseTotal()+ "");
        holder.tv_close_stock_total.setText(grandClosingTotal()+ "");
        holder.tv_sales_total.setText(grandSalesTotal()+ "");


    }

    @Override
    public int getItemCount() {
        return get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().size();
    }


    private double grandOpeningTotal() {
        double totalOpening = 0;
        for (int i = 0; i < get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().size(); i++) {
            totalOpening += get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(i).getOpening();
        }
        return totalOpening;
    }

    private double grandPurchaseTotal() {
        double totalPurchse = 0.00;
        for (int i = 0; i < get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().size(); i++) {
            totalPurchse += get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(i).getPurchase();
        }
        return totalPurchse;
    }

    private double grandSalesTotal(){
        double totalSales = 0.00;
        for (int i = 0; i < get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().size(); i++) {
            totalSales += get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(i).getSales();
        }
        return totalSales;
    }

    private double grandClosingTotal(){
        double totalClosingStock = 0.00;
        for (int i = 0; i < get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().size(); i++) {
            totalClosingStock += get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(i).getClosing();
        }
        return totalClosingStock;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_name, tv_ml, tv_op_stock, tv_purchase, tv_sales, tv_cl_stock,tv_op_stock_total,tv_purchase_total,tv_close_stock_total,tv_sales_total;
        LinearLayout ll_header;
        LinearLayout ll_total;
        View first_divider,last_divider;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_ml = itemView.findViewById(R.id.tv_ml);
            tv_op_stock = itemView.findViewById(R.id.tv_op_stock);
            tv_purchase = itemView.findViewById(R.id.tv_purchase);
            tv_sales = itemView.findViewById(R.id.tv_sales);
            tv_cl_stock = itemView.findViewById(R.id.tv_cl_stock);
            tv_op_stock_total = itemView.findViewById(R.id.tv_op_stock_total);
            tv_op_stock_total = itemView.findViewById(R.id.tv_op_stock_total);
            tv_purchase_total = itemView.findViewById(R.id.tv_purchase_total);
            tv_sales_total = itemView.findViewById(R.id.tv_sales_total);
            tv_close_stock_total = itemView.findViewById(R.id.tv_close_stock_total);
            first_divider = itemView.findViewById(R.id.first_divider);
            last_divider = itemView.findViewById(R.id.last_divider);
            ll_header = itemView.findViewById(R.id.ll_header);
            ll_total = itemView.findViewById(R.id.ll_total);


        }
    }
}
