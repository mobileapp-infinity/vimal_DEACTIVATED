package com.infinity.infoway.vimal.gsb_and_transfer_list_transfer_entry;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.DialogUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GsbAndDeepFreezeListTransferEntryActivity extends AppCompatActivity {

    private SharedPref mySharedPrefereces;
    private String appVersion;
    private String androidId;
    private String deviceId;
    private String userId;
    private String compId;

    private RecyclerView rvTransferEntryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsb_and_deep_freeze_list_transfer_entry);
        initView();
        getAllTransferEntryListApiCall();
    }


    private void initView() {
        mySharedPrefereces = new SharedPref(GsbAndDeepFreezeListTransferEntryActivity.this);

        appVersion = String.valueOf(mySharedPrefereces.getAppVersionCode());
        androidId = mySharedPrefereces.getAppAndroidId();
        deviceId = String.valueOf(mySharedPrefereces.getRegisteredId());
        userId = mySharedPrefereces.getRegisteredUserId();
        compId = mySharedPrefereces.getCompanyId();
        rvTransferEntryList = findViewById(R.id.rvTransferEntryList);
    }

    private void getAllTransferEntryListApiCall() {
        DialogUtils.showProgressDialogNotCancelable(GsbAndDeepFreezeListTransferEntryActivity.this, "");
        ApiImplementer.getAllTransferEntryApiImplementer(appVersion, androidId, deviceId, userId, compId, new Callback<GetAllTransferEntryPojo>() {
            @Override
            public void onResponse(Call<GetAllTransferEntryPojo> call, Response<GetAllTransferEntryPojo> response) {
                DialogUtils.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null && response.body().getRecords() != null &&
                        response.body().getRecords().size() > 0) {
                    rvTransferEntryList.setAdapter(new GsbAndDeepFreezeListAdapter(GsbAndDeepFreezeListTransferEntryActivity.this,
                            (ArrayList<GetAllTransferEntryPojo.Record>) response.body().getRecords()));
                } else {
                    Toast.makeText(GsbAndDeepFreezeListTransferEntryActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllTransferEntryPojo> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                Toast.makeText(GsbAndDeepFreezeListTransferEntryActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}