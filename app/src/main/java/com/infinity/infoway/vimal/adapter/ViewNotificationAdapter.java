package com.infinity.infoway.vimal.adapter;

import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.model.NotificationBean;

import java.util.List;


//
public class ViewNotificationAdapter extends RecyclerView.Adapter<ViewNotificationAdapter.MyViewHolder> {

    Context context;
    List<NotificationBean> notificationModelList;
    SharedPref mySharedPreferenses;
    private int Status = 0;
    private String StatusMessage = "";
    OnItemClickListner onItemClickListner;

    //listener for item click
    public interface OnItemClickListner{
        public void onItemClicked(int position, NotificationBean notificationModel);
    }

    public ViewNotificationAdapter(Context context, List<NotificationBean> viewExpensesModelList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.notificationModelList = viewExpensesModelList;
        this.onItemClickListner=onItemClickListner;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotificationTitle, tvNotificationDescription;
        ImageView ivExpenseDocument;
        LinearLayout llNotificationContainer;

        public MyViewHolder(View view) {
            super(view);
            ivExpenseDocument = (ImageView) view.findViewById(R.id.ivExpenseDocument);
            tvNotificationTitle = (TextView) view.findViewById(R.id.tvNotificationTitle);
            tvNotificationDescription = (TextView) view.findViewById(R.id.tvNotificationDescription);
            llNotificationContainer = (LinearLayout) view.findViewById(R.id.llNotificationContainer);


        }
        public void bindListener(final int position, final NotificationBean notificationModel)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position,notificationModel);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_notification_list_item_inflater, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        NotificationBean data = notificationModelList.get(position);
        ///bind listner
        holder.bindListener(position,data);
        holder.tvNotificationTitle.setText(data.Title+"");
        holder.tvNotificationDescription.setText(data.Description+"");
        if(data.attach_file!=null &&!data.attach_file.equals(""))
        {
            holder.ivExpenseDocument.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.ivExpenseDocument.setVisibility(View.INVISIBLE);
        }
        if(data.read.contentEquals("1"))
        {
            holder.tvNotificationTitle.setTypeface(null, Typeface.NORMAL);      // for Normal Text
        }
        else
        {
            holder.tvNotificationTitle.setTypeface(null, Typeface.BOLD);
        }
        /*if(position%2==0)
        {
            holder.llNotificationContainer.setBackgroundColor(context.getResources().getColor(R.color.attendanceList));
        }
        else
        {
            holder.llNotificationContainer.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }*/
        ///set data to item textview
        //holder.tvExpenseName.setText(data.getEdu_expense_name());
        //String edu_date=data.getEdu_date().split("T")[0];
        //holder.tvExpenseDate.setText(edu_date);
        //holder.tvExpenseRs.setText("INR. " + data.getEdu_amount());
        ////show this record has attechment or not
        /*if(data.getEdu_doc_photo_original_filename()!=null && !data.getEdu_doc_photo_original_filename().equals("") && data.getEdu_doc_photo_file_size_kb()!=0)
        {
            holder.ivExpenseDocument.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.ivExpenseDocument.setVisibility(View.INVISIBLE);
        }
*/

    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }



}
