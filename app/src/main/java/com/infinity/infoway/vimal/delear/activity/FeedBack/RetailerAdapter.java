package com.infinity.infoway.vimal.delear.activity.FeedBack;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;

import java.util.ArrayList;
import java.util.List;

public class RetailerAdapter extends RecyclerView.Adapter<RetailerAdapter.MyViewHolder> implements Filterable  {
    Context context;
    Get_Distributor_Wise_Retailer_Pojo get_distributor_wise_retailer_pojo;
    private OnItemClickListner onItemClickListner;



    private List<Get_Distributor_Wise_Retailer_Pojo.RECORDSBean> customerGroupListFiltered;
    private List<Get_Distributor_Wise_Retailer_Pojo.RECORDSBean> customerGroupList;

    public RetailerAdapter(Context context, List<Get_Distributor_Wise_Retailer_Pojo.RECORDSBean> customerGroupListFiltered, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.customerGroupList = customerGroupListFiltered;
        this.onItemClickListner = onItemClickListner;
        this.customerGroupListFiltered = customerGroupListFiltered;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_select_city,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    customerGroupListFiltered = customerGroupList;
                } else {
                    List<Get_Distributor_Wise_Retailer_Pojo.RECORDSBean> filteredList = new ArrayList<>();
                    for (Get_Distributor_Wise_Retailer_Pojo.RECORDSBean row : customerGroupList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCus_Name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    customerGroupListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = customerGroupListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                customerGroupListFiltered = (ArrayList<Get_Distributor_Wise_Retailer_Pojo.RECORDSBean>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }



    //item click perform listener
    public interface OnItemClickListner{
        public void onItemClicked(int position, Get_Distributor_Wise_Retailer_Pojo.RECORDSBean data);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


       /* if(get_distributor_wise_retailer_pojo!=null && get_distributor_wise_retailer_pojo.getRECORDS().size()>0) {
            final Get_Distributor_Wise_Retailer_Pojo.RECORDSBean data = get_distributor_wise_retailer_pojo.getRECORDS().get(position);
            holder.bindListener(position,data);
            if(!TextUtils.isEmpty(data.getCus_Name())){
                holder.txt_exp_name.setText(data.getCus_Name());
            }
        }*/

        if(customerGroupListFiltered!=null && customerGroupListFiltered.size()>0) {
            final Get_Distributor_Wise_Retailer_Pojo.RECORDSBean data= customerGroupListFiltered.get(position);
            holder.bindListener(position,data);
            if(!TextUtils.isEmpty(data.getCus_Name())){
                holder.txt_exp_name.setText(data.getCus_Name());
            }
        }

    }

    @Override
    public int getItemCount() {
        if(customerGroupListFiltered!=null && customerGroupListFiltered.size()>0) {
            return customerGroupListFiltered.size();
        }else{
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_exp_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_exp_name = itemView.findViewById(R.id.txt_exp_name);
        }
        ///register item clicked listener
        public void bindListener(final int position, final Get_Distributor_Wise_Retailer_Pojo.RECORDSBean data)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position,data);
                }
            });
        }
    }
}
