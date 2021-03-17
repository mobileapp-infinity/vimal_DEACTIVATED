package com.infinity.infoway.vimal.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.response.GetAttendanceResponse;

import java.util.List;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.MyViewHolder> {


    private OnItemClickListner onItemClickListner;
    private Context context;
    private List<GetAttendanceResponse.RECORD> viewExpensesModelList;

    public AttendanceListAdapter(Context context, List<GetAttendanceResponse.RECORD> viewExpensesModelList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.viewExpensesModelList = viewExpensesModelList;
        this.onItemClickListner=onItemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_attendance, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetAttendanceResponse.RECORD data = viewExpensesModelList.get(position);
        ///bind listner
        holder.bindListener(position,data);

        ///set data to item textview
        holder.txt_punch_date.setText(data.getDATE());
        if(!TextUtils.isEmpty(data.getPUNCHINTIME())){
            holder.txt_punch_in.setText(data.getPUNCHINTIME());
        }

        if(!TextUtils.isEmpty(data.getPUNCHOUTTIME())){
            holder.txt_punch_out.setText(data.getPUNCHOUTTIME());
        }


        if((!TextUtils.isEmpty(data.getATTFLAG()))){
            holder.btn_attendance_status_disp.setText(data.getATTFLAG());
            if(data.getATTFLAG().equalsIgnoreCase("P")){
                holder.btn_attendance_status_disp.setBackgroundResource(R.drawable.bg_item_attendance_present);
            }else if(data.getATTFLAG().equalsIgnoreCase("A")){
                holder.btn_attendance_status_disp.setBackgroundResource(R.drawable.bg_item_attendance_absent);
            }else if(data.getATTFLAG().equalsIgnoreCase("WO")){
                holder.btn_attendance_status_disp.setBackgroundResource(R.drawable.bg_item_attendance_wo);
            }else{
                holder.btn_attendance_status_disp.setBackgroundResource(R.drawable.bg_item_attendance_default);
            }
        }
    }

    @Override
    public int getItemCount() {
        return viewExpensesModelList.size();
    }

    //listener for item click
    public interface OnItemClickListner{
        public void onItemClicked(int position, GetAttendanceResponse.RECORD viewExpensesModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_punch_date, txt_punch_in, txt_punch_out;
        Button btn_attendance_status_disp;

        public MyViewHolder(View view) {
            super(view);
            txt_punch_date =  view.findViewById(R.id.txt_punch_date);
            txt_punch_in =  view.findViewById(R.id.txt_punch_in);
            txt_punch_out =  view.findViewById(R.id.txt_punch_out);
            btn_attendance_status_disp =  view.findViewById(R.id.btn_attendance_status_disp);


        }
        public void bindListener(final int position, final GetAttendanceResponse.RECORD viewExpensesModel)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position,viewExpensesModel);
                }
            });
        }
    }

}
