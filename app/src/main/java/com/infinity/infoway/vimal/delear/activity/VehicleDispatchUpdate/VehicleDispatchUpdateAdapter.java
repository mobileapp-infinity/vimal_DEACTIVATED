package com.infinity.infoway.vimal.delear.activity.VehicleDispatchUpdate;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleDispatchUpdateAdapter extends RecyclerView.Adapter<VehicleDispatchUpdateAdapter.MyViewHolder> {
    Context context;
    Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo get_distributor_wise_dispatched_sales_invoice_list_pojo;
    private OnItemClickListner onItemClickListner;
    private SharedPref sharedPref;
    private ApiInterface apiInterface;
    private Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo get_distributor_wise_dispatched_sales_invoice_detail_pojo;
    private ProgressDialog progressDialog;
    private boolean firstClicked = false;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int currentItemCount = 0;
    private int totalItemCount = 0;
    private int scrolledOutItemCount = 0;
    private boolean scrolled = false;

    public VehicleDispatchUpdateAdapter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager, Context context, Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo get_distributor_wise_dispatched_sales_invoice_list_pojo, OnItemClickListner onItemClickListner, SharedPref sharedPref) {
        this.context = context;
        this.get_distributor_wise_dispatched_sales_invoice_list_pojo = get_distributor_wise_dispatched_sales_invoice_list_pojo;
        this.onItemClickListner = onItemClickListner;
        this.sharedPref = sharedPref;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        this.recyclerView = recyclerView;
        this.linearLayoutManager = linearLayoutManager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vehicle_dipatch_update_view, parent, false);
        return new MyViewHolder(view);
    }

    public interface OnItemClickListner {
        public void onItemClicked(int position, Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo.RECORDSBean deRecordsBeanList, View itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println(currentItemCount + scrolledOutItemCount + "==" + totalItemCount);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                scrolledOutItemCount = linearLayoutManager.findFirstVisibleItemPosition();


                if (currentItemCount + scrolledOutItemCount == totalItemCount) {

                    scrolled = true;
                    System.out.println(scrolled);


                }


            }
        });

        if (holder.getAdapterPosition() == 0) {

            /*if (scrolled && holder.llinnerDetails.getVisibility() == View.VISIBLE ){
                Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail(get_distributor_wise_dispatched_sales_invoice_list_pojo.getRECORDS().get(0).getId() + "", holder.tv_invoice_name, holder.tv_invoice_date, holder.tv_driver_name, holder.tv_driver_no, holder.tv_vehicle_no, holder.tv_delivery_city, holder.tv_delivery_area);
                holder.llinnerDetails.setVisibility(View.VISIBLE);
                holder.tv_vehicle_dispatch_update_invoice_name.setText("Today`s Gate Pass");
            }*/


            if (scrolled && holder.llinnerDetails.getVisibility() == View.GONE) {
                holder.tv_vehicle_dispatch_update_invoice_name.setText("Today`s Gate Pass");
                holder.llinnerDetails.setVisibility(View.GONE);


            } else if (scrolled && holder.llinnerDetails.getVisibility() == View.VISIBLE) {
                holder.llinnerDetails.setVisibility(View.VISIBLE);
                Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail(get_distributor_wise_dispatched_sales_invoice_list_pojo.getRECORDS().get(0).getId() + "", holder.tv_invoice_name, holder.tv_invoice_date, holder.tv_driver_name, holder.tv_driver_no, holder.tv_vehicle_no, holder.tv_delivery_city, holder.tv_delivery_area,holder.tv_customer_city);
                holder.tv_vehicle_dispatch_update_invoice_name.setText("Today`s Gate Pass");
            } else {
                Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail(get_distributor_wise_dispatched_sales_invoice_list_pojo.getRECORDS().get(0).getId() + "", holder.tv_invoice_name, holder.tv_invoice_date, holder.tv_driver_name, holder.tv_driver_no, holder.tv_vehicle_no, holder.tv_delivery_city, holder.tv_delivery_area,holder.tv_customer_city);
                holder.llinnerDetails.setVisibility(View.VISIBLE);
                holder.tv_vehicle_dispatch_update_invoice_name.setText("Today`s Gate Pass");

            }




        } else {
            holder.llinnerDetails.setVisibility(View.GONE);
            holder.tv_vehicle_dispatch_update_invoice_name.setText(get_distributor_wise_dispatched_sales_invoice_list_pojo.getRECORDS().get(position).getInvoice_Name() + "");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (holder.llinnerDetails.getVisibility() == View.VISIBLE) {
                    holder.llinnerDetails.setVisibility(View.GONE);


                } else {

                    Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail(get_distributor_wise_dispatched_sales_invoice_list_pojo.getRECORDS().get(position).getId() + "", holder.tv_invoice_name, holder.tv_invoice_date, holder.tv_driver_name, holder.tv_driver_no, holder.tv_vehicle_no, holder.tv_delivery_city, holder.tv_delivery_area,holder.tv_customer_city);

                    holder.llinnerDetails.setVisibility(View.VISIBLE);
                }


            }
        });

        //  holder.bindListner(position, get_distributor_wise_dispatched_sales_invoice_list_pojo.getRECORDS().get(position));


    }

    @Override
    public int getItemCount() {
        return get_distributor_wise_dispatched_sales_invoice_list_pojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_vehicle_dispatch_update_invoice_name;
        RecyclerView rvExpendedDetails;
        LinearLayoutManager linearLayoutManager;
        LinearLayout llinnerDetails;


        /**
         * inner view
         **/
        TextView tv_invoice_name, tv_invoice_date, tv_driver_name, tv_driver_no, tv_vehicle_no, tv_customer_city, tv_delivery_city, tv_delivery_area;


        /**
         * inner view
         **/

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_vehicle_dispatch_update_invoice_name = itemView.findViewById(R.id.tv_vehicle_dispatch_update_invoice_name);
            rvExpendedDetails = itemView.findViewById(R.id.rvExpendedDetails);

            linearLayoutManager = new LinearLayoutManager(context);


            tv_invoice_name = itemView.findViewById(R.id.tv_invoice_name);
            tv_invoice_date = itemView.findViewById(R.id.tv_invoice_date);
            tv_driver_name = itemView.findViewById(R.id.tv_driver_name);
            tv_driver_no = itemView.findViewById(R.id.tv_driver_no);
            tv_vehicle_no = itemView.findViewById(R.id.tv_vehicle_no);
            tv_delivery_city = itemView.findViewById(R.id.tv_delivery_city);
            tv_delivery_area = itemView.findViewById(R.id.tv_delivery_area);
            tv_customer_city = itemView.findViewById(R.id.tv_customer_city);
            llinnerDetails = itemView.findViewById(R.id.llinnerDetails);


           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (llinnerDetails.getVisibility() == View.VISIBLE){
                        llinnerDetails.setVisibility(View.GONE);
                    }else if (llinnerDetails.getVisibility() == View.GONE){
                        llinnerDetails.setVisibility(View.VISIBLE);
                      //  Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail(get_distributor_wise_dispatched_sales_invoice_list_pojo.getRECORDS().get(getAdapterPosition()).getId() + "",rvExpendedDetails,linearLayoutManager);
                    }



                }
            });*/


        }


        private void bindListner(final int position, final Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo.RECORDSBean recordsBean) {


        }
    }

    private void Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail(String inv_id, final TextView tv_invoice_name, final TextView tv_invoice_date, final TextView tv_driver_name, final TextView tv_driver_no, final TextView tv_vehicle_no, final TextView tv_delivery_city, final TextView tv_delivery_area,final TextView tv_customer_city) {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(context.getResources().getString(R.string.processing_request));
            progressDialog.setCancelable(false);
            progressDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo> call = apiInterface.get_distributor_wise_dispatched_sales_invoice_detail(
                sharedPref.getAppVersionCode() + "",
                sharedPref.getAppAndroidId() + "",
                sharedPref.getRegisteredId() + "",
                sharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                sharedPref.getCompanyId() + "",
                inv_id


        );

        call.enqueue(new Callback<Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo>() {
            @Override
            public void onResponse(Call<Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo> call, Response<Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {


                    get_distributor_wise_dispatched_sales_invoice_detail_pojo = response.body();
                    if (get_distributor_wise_dispatched_sales_invoice_detail_pojo != null && get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().size() > 0) {


                        tv_invoice_name.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(0).getInvoice_No() + "");
                        tv_invoice_date.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(0).getInvoice_Date() + "");
                        tv_driver_name.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(0).getDriver_Name() + "");
                        tv_driver_no.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(0).getDriver_No() + "");
                        tv_vehicle_no.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(0).getVehicle_No() + "");
                        tv_delivery_city.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(0).getDelivery_City() + "");
                        tv_delivery_area.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(0).getDelivery_Area() + "");
                        tv_customer_city.setText(get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(0).getCustomer_City() + "");


                        // VehicleDipatchUpdateInnerAdapter vehicleDipatchUpdateInnerAdapter = new VehicleDipatchUpdateInnerAdapter(context,get_distributor_wise_dispatched_sales_invoice_detail_pojo);
                        //   rvExpendedDetails.setLayoutManager(linearLayoutManager);
                        //  rvExpendedDetails.setAdapter(vehicleDipatchUpdateInnerAdapter);
                        //  vehicleDipatchUpdateInnerAdapter.notifyDataSetChanged();

                        // System.out.println("2nd api"+get_distributor_wise_dispatched_sales_invoice_detail_pojo.getRECORDS().get(0).getDelivery_Area()+"");

                    } else {

                        progressDialog.dismiss();
                        Toast.makeText(context, get_distributor_wise_dispatched_sales_invoice_detail_pojo.getMESSAGE() + "", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo> call, Throwable t) {

                progressDialog.dismiss();

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}
