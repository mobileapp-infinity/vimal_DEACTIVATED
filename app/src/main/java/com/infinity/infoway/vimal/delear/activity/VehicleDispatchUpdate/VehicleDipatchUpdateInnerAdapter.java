package com.infinity.infoway.vimal.delear.activity.VehicleDispatchUpdate;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;

public class VehicleDipatchUpdateInnerAdapter extends RecyclerView.Adapter<VehicleDipatchUpdateInnerAdapter.MyViewHolder> {

    Context context;
    Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo get_distributor_wise_dispatched_sales_invoice_detail_pojo;

    public VehicleDipatchUpdateInnerAdapter(Context context, Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo get_distributor_wise_dispatched_sales_invoice_detail_pojo) {
        this.context = context;
        this.get_distributor_wise_dispatched_sales_invoice_detail_pojo = get_distributor_wise_dispatched_sales_invoice_detail_pojo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vehicle_dispatch_inner_details, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (!get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getInvoice_No().contentEquals("")) {

            holder.tv_invoice_name.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getInvoice_No() + "");

        }
        if (!get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getInvoice_Date().contentEquals("")) {

            holder.tv_invoice_date.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getInvoice_Date() + "");

        }

        if (!get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getDriver_Name().contentEquals("")) {

            holder.tv_driver_name.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getDriver_Name() + "");

        }

        if (!get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getDriver_No().contentEquals("")) {

            holder.tv_driver_no.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getDriver_No() + "");

        }

        if (!get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getVehicle_No().contentEquals("")) {

            holder.tv_vehicle_no.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getVehicle_No() + "");

        }

        if (!get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getDelivery_City().contentEquals("")) {

            holder.tv_delivery_city.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getDelivery_City() + "");

        }

        if (!get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getDelivery_Area().contentEquals("")) {

            holder.tv_delivery_area.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(position).getDelivery_Area() + "");

        }


    }

    @Override
    public int getItemCount() {
        return get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_invoice_name, tv_invoice_date, tv_driver_name, tv_driver_no, tv_vehicle_no, tv_customer_city, tv_delivery_city, tv_delivery_area;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_invoice_name = itemView.findViewById(R.id.tv_invoice_name);
            tv_invoice_date = itemView.findViewById(R.id.tv_invoice_date);
            tv_driver_name = itemView.findViewById(R.id.tv_driver_name);
            tv_driver_no = itemView.findViewById(R.id.tv_driver_no);
            tv_vehicle_no = itemView.findViewById(R.id.tv_vehicle_no);
            tv_delivery_city = itemView.findViewById(R.id.tv_delivery_city);
            tv_delivery_area = itemView.findViewById(R.id.tv_delivery_area);

        }
    }
}
