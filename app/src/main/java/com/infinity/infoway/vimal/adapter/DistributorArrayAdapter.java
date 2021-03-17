package com.infinity.infoway.vimal.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.response.Distributor_Pojo;

import java.util.ArrayList;
import java.util.List;

public class DistributorArrayAdapter extends RecyclerView.Adapter<DistributorArrayAdapter.MyViewHolder>  implements Filterable {


    private Context context;
    private OnItemClickListner onItemClickListner;

    private List<Distributor_Pojo.RECORDSBean> customerGroupListFiltered;
    private List<Distributor_Pojo.RECORDSBean> purposeGroupList;

    public DistributorArrayAdapter(Context context, List<Distributor_Pojo.RECORDSBean> purposeGroupList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.purposeGroupList = purposeGroupList;
        this.customerGroupListFiltered = purposeGroupList;
        System.out.println("purposeGroupList  "+purposeGroupList.size()+"");
        System.out.println("customerGroupListFiltered  "+customerGroupListFiltered.size()+"");
        this.onItemClickListner=onItemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View iteamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_select_city, parent, false);
        return new MyViewHolder(iteamView);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(customerGroupListFiltered!=null && customerGroupListFiltered.size()>0) {
            final Distributor_Pojo.RECORDSBean data = customerGroupListFiltered.get(position);
            holder.bindListener(position,data);
            if(!TextUtils.isEmpty(data.getCus_name())){
                holder.txt_exp_name.setText(data.getCus_name());
            }
        }

    }

    @Override
    public int getItemCount() {
        if(customerGroupListFiltered!=null && customerGroupListFiltered.size()>0) {
            System.out.println("this must print size "+customerGroupListFiltered.size()+"");
            return customerGroupListFiltered.size();
        }else{
            System.out.println("this is size getting ::::::::: 000 ");
            return 0;
        }
    }


    //item click perform listener
    public interface OnItemClickListner{
        public void onItemClicked(int position, Distributor_Pojo.RECORDSBean data);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    customerGroupListFiltered = purposeGroupList;
                } else {
                    List<Distributor_Pojo.RECORDSBean> filteredList = new ArrayList<>();
                    for (Distributor_Pojo.RECORDSBean row : purposeGroupList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCus_name().toLowerCase().contains(charString.toLowerCase())) {
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
                customerGroupListFiltered = (ArrayList<Distributor_Pojo.RECORDSBean>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView txt_exp_name;

        MyViewHolder(View itemView) {
            super(itemView);
            txt_exp_name = itemView.findViewById(R.id.txt_exp_name);
        }

        ///register item clicked listener
        public void bindListener(final int position, final Distributor_Pojo.RECORDSBean data)
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
