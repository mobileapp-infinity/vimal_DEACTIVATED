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
import com.infinity.infoway.vimal.api.response.City_State_Taluka_CountryPojo;

import java.util.ArrayList;
import java.util.List;

public class City_State_TalukaArrayAdapter extends RecyclerView.Adapter<City_State_TalukaArrayAdapter.MyViewHolder>  implements Filterable {


    private Context context;
    private OnItemClickListner onItemClickListner;

    private List<City_State_Taluka_CountryPojo.RECORDSBean> customerGroupListFiltered;
    private List<City_State_Taluka_CountryPojo.RECORDSBean> customerGroupList;

    public City_State_TalukaArrayAdapter(Context context, List<City_State_Taluka_CountryPojo.RECORDSBean> customerGroupList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.customerGroupList = customerGroupList;
        this.customerGroupListFiltered = customerGroupList;
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
            final City_State_Taluka_CountryPojo.RECORDSBean data = customerGroupListFiltered.get(position);
            holder.bindListener(position,data);
            if(!TextUtils.isEmpty(data.getCity_Name())){
                holder.txt_exp_name.setText(data.getCity_Name());
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


    //item click perform listener
    public interface OnItemClickListner{
        public void onItemClicked(int position, City_State_Taluka_CountryPojo.RECORDSBean data);
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
                    List<City_State_Taluka_CountryPojo.RECORDSBean> filteredList = new ArrayList<>();
                    for (City_State_Taluka_CountryPojo.RECORDSBean row : customerGroupList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCity_Name().toLowerCase().contains(charString.toLowerCase())) {
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
                customerGroupListFiltered = (ArrayList<City_State_Taluka_CountryPojo.RECORDSBean>) filterResults.values;

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
        public void bindListener(final int position, final City_State_Taluka_CountryPojo.RECORDSBean data)
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
