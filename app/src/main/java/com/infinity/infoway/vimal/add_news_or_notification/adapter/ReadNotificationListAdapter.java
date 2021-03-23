package com.infinity.infoway.vimal.add_news_or_notification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.GetNewsAndMsgListPojo;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DownloadPdfFromUrl;

import java.util.ArrayList;

public class ReadNotificationListAdapter extends RecyclerView.Adapter<ReadNotificationListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetNewsAndMsgListPojo.RECORD> recordArrayList;
    private LayoutInflater layoutInflater;
    private SharedPref getSharedPref;

    public ReadNotificationListAdapter(Context context, ArrayList<GetNewsAndMsgListPojo.RECORD> recordArrayList) {
        this.context = context;
        this.recordArrayList = recordArrayList;
        layoutInflater = LayoutInflater.from(context);
        getSharedPref = new SharedPref(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_readed_notification, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            GetNewsAndMsgListPojo.RECORD record = recordArrayList.get(position);

            if (record.getNewsContent() != null && !record.getNewsContent().isEmpty()) {
                holder.tvNotificationContent.setText(record.getNewsContent() + "");
            }


            if (record.getImgUrl().toString() != null && !record.getImgUrl().toString().isEmpty()) {
                holder.llDownload.setVisibility(View.VISIBLE);
            } else {
                holder.llDownload.setVisibility(View.GONE);
            }

            holder.llDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (record.getImgUrl().toString() != null && !record.getImgUrl().toString().isEmpty()) {
                        String fileUrl = record.getImgUrl().toString();
                        String fileExtension = fileUrl.substring(fileUrl.lastIndexOf("."));
                        new DownloadPdfFromUrl(context, fileUrl.trim(), fileExtension, "Notification");
                    }
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView tvNotificationContent;
        LinearLayout llDownload;
//        LinearLayout llClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotificationContent = itemView.findViewById(R.id.tvNotificationContent);
            llDownload = itemView.findViewById(R.id.llDownload);
//            llClick = itemView.findViewById(R.id.llClick);
        }
    }

}
