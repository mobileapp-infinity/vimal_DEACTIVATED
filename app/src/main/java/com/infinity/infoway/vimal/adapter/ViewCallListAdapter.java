package com.infinity.infoway.vimal.adapter;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.response.GetAllCallListResponse;
import com.infinity.infoway.vimal.config.Config;

import java.util.List;

public class ViewCallListAdapter extends RecyclerView.Adapter<ViewCallListAdapter.MyViewHolder>{

    private OnItemClickListner onItemClickListner;
    private Context context;
    private List<GetAllCallListResponse.RECORD> viewCallList;
    private Dialog fullScreenDialog;


    //listener for item click
    public interface OnItemClickListner {
        public void onItemClicked(int position, GetAllCallListResponse.RECORD dataModel, View itemView);
    }

    public ViewCallListAdapter(Context context, List<GetAllCallListResponse.RECORD> viewCallList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.viewCallList = viewCallList;
        this.onItemClickListner = onItemClickListner;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_shop_name, txt_contact_person, txt_mobile, txt_city,txt_date_time;
        ImageView img_photo;

        public MyViewHolder(View view) {
            super(view);
            img_photo = view.findViewById(R.id.img_photo);
            txt_shop_name = view.findViewById(R.id.txt_shop_name);
            txt_contact_person = view.findViewById(R.id.txt_contact_person);
            txt_mobile = view.findViewById(R.id.txt_mobile);
            txt_city = view.findViewById(R.id.txt_city);
            txt_date_time=view.findViewById(R.id.txt_date_time);
        }

        public void bindListener(final int position, final GetAllCallListResponse.RECORD dataModel) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position, dataModel, itemView);
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_view_call, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(viewCallList!=null && viewCallList.size()>0){
            final GetAllCallListResponse.RECORD data = viewCallList.get(position);
            ///bind listner
            holder.bindListener(position,data);

            ///set data to item textview
            if(!TextUtils.isEmpty(data.getCUSNAME())){
                if(!TextUtils.isEmpty(data.getCALLTYPE())){
                    SpannableString spanUOM= SpannableString.valueOf(" ( " + data.getCALLTYPE() + " )");
                    if(data.getCALLTYPENO().equalsIgnoreCase("7")){
                        spanUOM.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_attendance_absent)), 0, spanUOM.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else if(data.getCALLTYPENO().equalsIgnoreCase("1")){
                        spanUOM.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_order_dash_board)), 0, spanUOM.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else if(data.getCALLTYPENO().equalsIgnoreCase("2")){
                        spanUOM.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_self_cert_receive_dark)), 0, spanUOM.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(data.getCALLTYPENO().equalsIgnoreCase("8")){
                        spanUOM.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_dealername_bg)), 0, spanUOM.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else if(data.getCALLTYPENO().equalsIgnoreCase("9")){
                        spanUOM.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_myschedule_dash_board)), 0, spanUOM.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else if(data.getCALLTYPENO().equalsIgnoreCase("3")){
                        spanUOM.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_pending)), 0, spanUOM.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    SpannableStringBuilder sbProduct = new SpannableStringBuilder();
                    sbProduct.append(data.getCUSNAME());
                    sbProduct.append(spanUOM);

                    holder.txt_shop_name.setText(sbProduct);
                }else{
                    holder.txt_shop_name.setText(data.getCUSNAME());
                }

            }

            if(!TextUtils.isEmpty(data.getCONTACTPERSON())){
                holder.txt_contact_person.setText(data.getCONTACTPERSON());
            }

            if(!TextUtils.isEmpty(data.getMOBILE())){
                holder.txt_mobile.setText(data.getMOBILE());
            }

            if((!TextUtils.isEmpty(data.getCITY()))){
                holder.txt_city.setVisibility(View.VISIBLE);
                holder.txt_city.setText(data.getCITY());
            }else{
                holder.txt_city.setVisibility(View.GONE);
                holder.txt_city.setText("");
            }


            /*if(!TextUtils.isEmpty(data.getCITY())){
                holder.txt_city.setText(data.getCITY());
            }*/

            if(!TextUtils.isEmpty(data.getDATE())){
                holder.txt_date_time.setText(data.getDATE());
            }
            if(!TextUtils.isEmpty(data.getFILEURL())){
                holder.img_photo.setVisibility(View.VISIBLE);
                try{
                    Glide.with(context).load(Config.IMAGE_URL+data.getFILEURL()).into(holder.img_photo);
                }catch (Exception ex){}

                holder.img_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fullScreenImageDisplay(Config.IMAGE_URL+data.getFILEURL());
                    }
                });
            }else{
                holder.img_photo.setVisibility(View.INVISIBLE);
            }

        }

    }

    @Override
    public int getItemCount() {
        if(viewCallList!=null && viewCallList.size()>0){
            return viewCallList.size();
        }else{
            return 0;
        }

    }

    private void fullScreenImageDisplay(String resImageUrl) {

        if (fullScreenDialog != null && fullScreenDialog.isShowing()) {
            fullScreenDialog.dismiss();
        }

        try {
            fullScreenDialog = new Dialog(context);
            fullScreenDialog.setContentView(R.layout.layout_dialog_full_screen_image);
            fullScreenDialog.setCancelable(false);

            ImageView imgViewDisplay = fullScreenDialog.findViewById(R.id.imgViewDisplay);

            try{
                Glide.with(context).load(resImageUrl).into(imgViewDisplay);
            }catch (Exception ex){}

            ImageButton imgBtnClose = fullScreenDialog.findViewById(R.id.imgBtnClose);
            imgBtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fullScreenDialog.dismiss();
                }
            });

            fullScreenDialog.show();

        } catch (Exception ex) {
        }


    }
}
