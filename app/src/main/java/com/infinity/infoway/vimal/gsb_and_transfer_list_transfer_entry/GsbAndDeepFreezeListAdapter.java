package com.infinity.infoway.vimal.gsb_and_transfer_list_transfer_entry;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_view_transfer_entery.GsbAndDeepFreezeViewTransferEntryActivity;
import com.infinity.infoway.vimal.util.common.DialogUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GsbAndDeepFreezeListAdapter extends RecyclerView.Adapter<GsbAndDeepFreezeListAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<GetAllTransferEntryPojo.Record> getAllTransferEntry;
    private SharedPref mySharedPrefereces;
    private String appVersion;
    private String androidId;
    private String deviceId;
    private String userId;
    private String compId;

    public GsbAndDeepFreezeListAdapter(Context context, ArrayList<GetAllTransferEntryPojo.Record> getAllTransferEntry) {
        this.context = context;
        this.getAllTransferEntry = getAllTransferEntry;
        layoutInflater = LayoutInflater.from(context);
        mySharedPrefereces = new SharedPref(context);

        appVersion = String.valueOf(mySharedPrefereces.getAppVersionCode());
        androidId = mySharedPrefereces.getAppAndroidId();
        deviceId = String.valueOf(mySharedPrefereces.getRegisteredId());
        userId = mySharedPrefereces.getRegisteredUserId();
        compId = mySharedPrefereces.getCompanyId();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_gsb_and_deep_freeze_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetAllTransferEntryPojo.Record record = getAllTransferEntry.get(position);

        if (record.getGsbTransferDate() != null && !record.getGsbTransferDate().isEmpty()) {
            holder.tvTransferDate.setText("Transfer Date: " + record.getGsbTransferDate());
        }

        if (record.getFromDistributor() != null && !record.getFromDistributor().isEmpty()) {
            holder.tvFromDistributor.setText(record.getFromDistributor());
        }

        if (record.getToDistributor() != null && !record.getToDistributor().isEmpty()) {
            holder.tvToDistributor.setText(record.getToDistributor());
        }

        if (record.getFromRetailer() != null && !record.getFromRetailer().isEmpty()) {
            holder.tvFromRetailer.setText(record.getFromRetailer());
        }

        if (record.getToRetailer() != null && !record.getToRetailer().isEmpty()) {
            holder.tvToRetailer.setText(record.getToRetailer());
        }

        if (record.getFridgeType() != null && !record.getFridgeType().isEmpty()) {
            holder.tvFreezeType.setText(record.getFridgeType());
        }

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = record.getId() + "";
                dialogBox(position, getAllTransferEntry, id);
            }
        });

        holder.tvTapToView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = record.getId() + "";
                Intent intent = new Intent(context, GsbAndDeepFreezeViewTransferEntryActivity.class);
                intent.putExtra(GsbAndDeepFreezeViewTransferEntryActivity.TRANSFER_ENTRY_ID, id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getAllTransferEntry.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvTransferDate;
        AppCompatTextView tvFromDistributor;
        AppCompatTextView tvToDistributor;
        AppCompatTextView tvFromRetailer;
        AppCompatTextView tvToRetailer;
        AppCompatTextView tvFreezeType;
        AppCompatTextView tvDelete;
        AppCompatTextView tvTapToView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransferDate = itemView.findViewById(R.id.tvTransferDate);
            tvFromDistributor = itemView.findViewById(R.id.tvFromDistributor);
            tvToDistributor = itemView.findViewById(R.id.tvToDistributor);
            tvFromRetailer = itemView.findViewById(R.id.tvFromRetailer);
            tvToRetailer = itemView.findViewById(R.id.tvToRetailer);
            tvFreezeType = itemView.findViewById(R.id.tvFreezeType);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            tvTapToView = itemView.findViewById(R.id.tvTapToView);
        }
    }

    private void deleteTransferEntryApiCall(int position, ArrayList<GetAllTransferEntryPojo.Record> getAllTransferEntry, String id) {
        DialogUtils.showProgressDialogNotCancelable(context, "");
        ApiImplementer.deleteFreezeTransferEntryApiImplementer(appVersion, androidId, deviceId, userId, compId, id, new Callback<DeleteFreezeTransferEntryPojo>() {
            @Override
            public void onResponse(Call<DeleteFreezeTransferEntryPojo> call, Response<DeleteFreezeTransferEntryPojo> response) {
                DialogUtils.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage() != null && !response.body().getMessage().isEmpty()) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        getAllTransferEntry.remove(position);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Record deleted", Toast.LENGTH_SHORT).show();
                        getAllTransferEntry.remove(position);
                        notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(context, "Failed to delete entry!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteFreezeTransferEntryPojo> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dialogBox(int position, ArrayList<GetAllTransferEntryPojo.Record> getAllTransferEntry, String id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to delete ?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        deleteTransferEntryApiCall(position, getAllTransferEntry, id);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
