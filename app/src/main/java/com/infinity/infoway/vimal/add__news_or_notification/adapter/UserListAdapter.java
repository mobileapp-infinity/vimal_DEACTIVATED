package com.infinity.infoway.vimal.add__news_or_notification.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add__news_or_notification.pojo.UserListPojo;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<UserListPojo.RECORD> recordArrayList;
    private LayoutInflater layoutInflater;
    private IUser iUser;

    public UserListAdapter(Context context, ArrayList<UserListPojo.RECORD> recordArrayList) {
        this.context = context;
        this.recordArrayList = recordArrayList;
        layoutInflater = LayoutInflater.from(context);
        iUser = (IUser) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.infalter_user_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserListPojo.RECORD record = recordArrayList.get(position);

        holder.cbUser.setChecked(record.isChecked());

        if (!TextUtils.isEmpty(record.getUsrmName())) {
            holder.cbUser.setText(record.getUsrmName() + "");
        }

        holder.cbUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                record.setChecked(isChecked);
                iUser.onUserChecked(recordArrayList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCheckBox cbUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbUser = itemView.findViewById(R.id.cbUser);
        }
    }

    public interface IUser {
        void onUserChecked(ArrayList<UserListPojo.RECORD> recordArrayList);
    }


}
