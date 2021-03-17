package com.infinity.infoway.vimal.kich_expense.Expense.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_expense.Expense.Pojo.Expense_Listing_Pojo;

public class Expense_View_List_Adapter extends RecyclerView.Adapter<Expense_View_List_Adapter.MyViewHolder> {
    private OnItemClickListner onItemClickListner;
    private Context context;
    private Expense_Listing_Pojo pojo;



    public Expense_View_List_Adapter(Context context, Expense_Listing_Pojo pojo, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.pojo = pojo;
        this.onItemClickListner=onItemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    public interface OnItemClickListner{
        public void onItemClicked(int position, Expense_Listing_Pojo.RECORDSBean deRecordsBeanList, View itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Expense_Listing_Pojo.RECORDSBean data = pojo.getRECORDS().get(position);
        holder.bindListener(position,data);
        if(pojo!=null && pojo.getRECORDS().size()>0){
          if(position==0)
            {
                holder.lin_header.setVisibility(View.VISIBLE);
            }
            else{
                holder.lin_header.setVisibility(View.GONE);
            }

           /* if(!TextUtils.isEmpty(data.getSomed_project_id())){
                holder.txtprojectName.setText(data.getSomed_project_id());
            }
            if(!TextUtils.isEmpty(data.getSomed_transp_id())){
                holder.txttransportername.setText(data.getSomed_transp_id());
            }*/


                holder.tv0.setText(data.getExp_date()+"");
                holder.tv1.setText(data.getExp_name()+"");
                holder.tv2.setText(data.getExp_amount()+"");
          //      String imagename=data.getExp_file_url()+"";
              /*  if(imagename.contains("."))
                {
                    imagename=imagename.substring()
                }*/
            ////    holder.tv3.setText(data.getExp_file_url()+"");
              //  holder.tv4.setText(data.getSdm_retailer_cit_id()+"\n"+data.getSdm_retailer_sta_id());
             //   holder.tv5.setText(data.getSdm_work_deadline_date()+"");


        }

    }

    @Override
    public int getItemCount() {
        if (pojo != null && pojo.getRECORDS().size() > 0) {
            return pojo.getRECORDS().size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv0, tv1,tv2,tv3,tv4,tv5;
        LinearLayout lin_header;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv0 = itemView.findViewById(R.id.tv0);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv5 = itemView.findViewById(R.id.tv5);
            lin_header = (LinearLayout)itemView.findViewById(R.id.lin_header);
        }

        public void bindListener(final int position, final Expense_Listing_Pojo.RECORDSBean recordsBean)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position,recordsBean,itemView);
                }
            });
        }
    }
}
