package com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;

public class PerFromInVoiceAdapter extends RecyclerView.Adapter<PerFromInVoiceAdapter.MyViewHolder> {

    private Context context;
    private Get_Distributor_Wise_Sales_Invoice_List_POJO get_distributor_wise_sales_invoice_list_pojo;
    private GETPDFID getpdfid;
    private OnItemClickListner onItemClickListner;


    public PerFromInVoiceAdapter(Context context, Get_Distributor_Wise_Sales_Invoice_List_POJO get_distributor_wise_sales_invoice_list_pojo,OnItemClickListner onItemClickListner) {
        this.context = context;
        this.get_distributor_wise_sales_invoice_list_pojo = get_distributor_wise_sales_invoice_list_pojo;
        this.getpdfid = (GETPDFID) context;
        this.onItemClickListner = onItemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.perform_invoice_list_view, parent, false);
        return new MyViewHolder(view);

    }

    public interface OnItemClickListner{
        public void onItemClicked(int position, Get_Distributor_Wise_Sales_Invoice_List_POJO.RECORDSBean deRecordsBeanList, View itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {



        holder.tv_invoice_name.setText(get_distributor_wise_sales_invoice_list_pojo.getRECORDS().get(position).getInvoice_Name());
       holder.bindListener(position,get_distributor_wise_sales_invoice_list_pojo.getRECORDS().get(position));


    }

    @Override
    public int getItemCount() {
        return get_distributor_wise_sales_invoice_list_pojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_invoice_name;
        ImageView img_download_pdf;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_invoice_name = itemView.findViewById(R.id.tv_invoice_name);
            img_download_pdf = itemView.findViewById(R.id.img_download_pdf);
        }

        public void bindListener(final int position, final Get_Distributor_Wise_Sales_Invoice_List_POJO.RECORDSBean recordsBean) {




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListner.onItemClicked(position, recordsBean, itemView);
                }
            });

        }
    }

    public interface GETPDFID {
        void get_id(String id);

    }


}
