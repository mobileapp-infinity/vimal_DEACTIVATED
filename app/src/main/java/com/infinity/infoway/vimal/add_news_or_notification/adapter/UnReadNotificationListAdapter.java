package com.infinity.infoway.vimal.add_news_or_notification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add_news_or_notification.NewsOrNotificationImplementer;
import com.infinity.infoway.vimal.add_news_or_notification.fragments.UnReadNotificationFragment;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.GetNewsAndMsgListPojo;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.UpdateReadUnReadStatusPojo;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.DownloadPdfFromUrl;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnReadNotificationListAdapter extends RecyclerView.Adapter<UnReadNotificationListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetNewsAndMsgListPojo.RECORD> recordArrayList;
    private LayoutInflater layoutInflater;
    private SharedPref getSharedPref;
    private IOnListEmpty iOnListEmpty;

    public UnReadNotificationListAdapter(Context context, ArrayList<GetNewsAndMsgListPojo.RECORD> recordArrayList,UnReadNotificationFragment unReadNotificationFragment) {
        this.context = context;
        this.recordArrayList = recordArrayList;
        layoutInflater = LayoutInflater.from(context);
        getSharedPref = new SharedPref(context);
        iOnListEmpty = unReadNotificationFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_reade_unread_notification_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            GetNewsAndMsgListPojo.RECORD record = recordArrayList.get(position);

            if (record.getIsRead() == 0) {
                holder.llIsUnRead.setVisibility(View.VISIBLE);
            } else {
                holder.llIsUnRead.setVisibility(View.GONE);
            }

            if (record.getNewsContent() != null && !record.getNewsContent().isEmpty()) {
                holder.tvNotificationContent.setText(HtmlCompat.fromHtml(record.getNewsContent(), 0));
            }

            if (record.getImgUrl() != null && !record.getImgUrl().toString().isEmpty()) {
                holder.llDownload.setVisibility(View.VISIBLE);
            } else {
                holder.llDownload.setVisibility(View.GONE);
            }

            holder.llDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (record.getImgUrl() != null && !record.getImgUrl().toString().isEmpty()) {
                        String fileUrl = record.getImgUrl().toString();
                        String fileExtension = fileUrl.substring(fileUrl.lastIndexOf("."));
                        new DownloadPdfFromUrl(context, fileUrl.trim(), fileExtension, "Notification");
                    }
                }
            });

            holder.llClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (record.getIsRead() == 0) {
                        updateReadUnReadStatus(recordArrayList, position, record.getId().toString());
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    private void updateReadUnReadStatus(ArrayList<GetNewsAndMsgListPojo.RECORD> recordArrayList, int pos, String cnm_id) {
        DialogUtils.showProgressDialogNotCancelable(context, "");
        NewsOrNotificationImplementer.updateReadUnReadStatusApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), String.valueOf(getSharedPref.getCompanyId()), cnm_id, getSharedPref.getRegisteredUserId(), new Callback<UpdateReadUnReadStatusPojo>() {
                    @Override
                    public void onResponse(Call<UpdateReadUnReadStatusPojo> call, Response<UpdateReadUnReadStatusPojo> response) {
                        DialogUtils.hideProgressDialog();
                        try {
                            if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                                Toast.makeText(context, "" + response.body().getMESSAGE(), Toast.LENGTH_SHORT).show();
                                recordArrayList.remove(pos);
                                notifyDataSetChanged();
                                if (recordArrayList.size() <= 0){
                                    iOnListEmpty.onClear();
                                }
                            } else {
                                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateReadUnReadStatusPojo> call, Throwable t) {
                        DialogUtils.hideProgressDialog();
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llIsUnRead;
        CustomTextView tvNotificationContent;
        LinearLayout llDownload;
        LinearLayout llClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            llIsUnRead = itemView.findViewById(R.id.llIsUnRead);
            tvNotificationContent = itemView.findViewById(R.id.tvNotificationContent);
            llDownload = itemView.findViewById(R.id.llDownload);
            llClick = itemView.findViewById(R.id.llClick);
        }
    }

    public interface IOnListEmpty{
        void onClear();
    }

}
