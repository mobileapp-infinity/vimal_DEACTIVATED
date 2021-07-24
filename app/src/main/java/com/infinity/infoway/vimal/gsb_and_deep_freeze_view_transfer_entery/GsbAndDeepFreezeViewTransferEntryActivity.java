package com.infinity.infoway.vimal.gsb_and_deep_freeze_view_transfer_entery;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.DialogUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GsbAndDeepFreezeViewTransferEntryActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TRANSFER_ENTRY_ID = " TRANSFER_ENTRY_ID";

    private SharedPref mySharedPrefereces;
    private AppCompatImageView ivBack;
    private RadioButton rbtnGsb;
    private RadioButton rbtnDeepFreeze;
    private TextInputEditText tvTransferDate;
    private TextInputEditText tvFromDistributor;
    private TextInputEditText tvToDistributor;
    private TextInputEditText tvFromRetailerName;
    private TextInputEditText tvToRetailerName;
    private TextInputEditText tvRemarks;
    private LinearLayout llForDeepFreeze;
    private TextInputEditText tiedtSerialNo;
    private TextInputEditText tiedtFridgeCompanyName;
    private TextInputEditText tiedtFridgeType;
    private LinearLayout llFreezeItemList;
    private AppCompatTextView tvQuantity;
    private AppCompatTextView tvUOM;
    private AppCompatTextView tvTransferQty;
    private RecyclerView rvItemList;

    private String appVersion;
    private String androidId;
    private String deviceId;
    private String userId;
    private String compId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsb_and_deep_freeze_view_transfer_entry_activity);
        initView();
        if (getIntent().hasExtra(TRANSFER_ENTRY_ID)) {
            getGsbAndDeepFreezeDetailsApiCall(getIntent().getStringExtra(TRANSFER_ENTRY_ID));
        } else {
            Toast.makeText(this, "Id not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void initView() {
        mySharedPrefereces = new SharedPref(GsbAndDeepFreezeViewTransferEntryActivity.this);

        appVersion = String.valueOf(mySharedPrefereces.getAppVersionCode());
        androidId = mySharedPrefereces.getAppAndroidId();
        deviceId = String.valueOf(mySharedPrefereces.getRegisteredId());
        userId = mySharedPrefereces.getRegisteredUserId();
        compId = mySharedPrefereces.getCompanyId();

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        rbtnGsb = findViewById(R.id.rbtnGsb);
        rbtnDeepFreeze = findViewById(R.id.rbtnDeepFreeze);
        tvTransferDate = findViewById(R.id.tvTransferDate);
        tvFromDistributor = findViewById(R.id.tvFromDistributor);
        tvToDistributor = findViewById(R.id.tvToDistributor);
        tvFromRetailerName = findViewById(R.id.tvFromRetailerName);
        tvToRetailerName = findViewById(R.id.tvToRetailerName);
        tvRemarks = findViewById(R.id.tvRemarks);
        llForDeepFreeze = findViewById(R.id.llForDeepFreeze);
        tiedtSerialNo = findViewById(R.id.tiedtSerialNo);
        tiedtFridgeCompanyName = findViewById(R.id.tiedtFridgeCompanyName);
        tiedtFridgeType = findViewById(R.id.tiedtFridgeType);
        llFreezeItemList = findViewById(R.id.llFreezeItemList);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvUOM = findViewById(R.id.tvUOM);
        tvTransferQty = findViewById(R.id.tvTransferQty);
        rvItemList = findViewById(R.id.rvItemList);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getGsbAndDeepFreezeDetailsApiCall(String id) {
        DialogUtils.showProgressDialogNotCancelable(GsbAndDeepFreezeViewTransferEntryActivity.this, "");
        ApiImplementer.getFreezeTransferEntryDetailsApiImplementer(appVersion, androidId, deviceId, userId, compId, id, new Callback<ViewFreezeTransferEntryPojo>() {
            @Override
            public void onResponse(Call<ViewFreezeTransferEntryPojo> call, Response<ViewFreezeTransferEntryPojo> response) {
                DialogUtils.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null && response.body().getRecords() != null &&
                        response.body().getRecords().getMain().size() > 0) {
                    setData(response.body().getRecords().getMain().get(0));
                    if (response.body().getRecords().getItemDetails() != null &&
                            response.body().getRecords().getItemDetails().size() > 0) {
                        rvItemList.setAdapter(new ViewFreezeListItemAdapter(GsbAndDeepFreezeViewTransferEntryActivity.this,
                                (ArrayList<ViewFreezeTransferEntryPojo.Records.ItemDetail>) response.body().getRecords().getItemDetails()));
                    }
                } else {
                    Toast.makeText(GsbAndDeepFreezeViewTransferEntryActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewFreezeTransferEntryPojo> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                Toast.makeText(GsbAndDeepFreezeViewTransferEntryActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(ViewFreezeTransferEntryPojo.Records.Main record) {

        if (record.getGsbTransferType() != null && !record.getGsbTransferType().isEmpty()) {
            if (record.getGsbTransferType().equalsIgnoreCase("GSB")) {
                rbtnGsb.setChecked(true);
                llForDeepFreeze.setVisibility(View.GONE);
            } else {
                rbtnDeepFreeze.setChecked(true);
                llForDeepFreeze.setVisibility(View.VISIBLE);
            }
        }

        if (record.getGsbTransferDate() != null && !record.getGsbTransferDate().isEmpty()) {
            tvTransferDate.setText(record.getGsbTransferDate() + "");
        }

        if (record.getFromDistributor() != null && !record.getFromDistributor().isEmpty()) {
            tvFromDistributor.setText(record.getFromDistributor() + "");
        }

        if (record.getToDistributor() != null && !record.getToDistributor().isEmpty()) {
            tvToDistributor.setText(record.getToDistributor() + "");
        }

        if (record.getFromRetailer() != null && !record.getFromRetailer().isEmpty()) {
            tvFromRetailerName.setText(record.getFromRetailer() + "");
        }

        if (record.getToRetailer() != null && !record.getToRetailer().isEmpty()) {
            tvToRetailerName.setText(record.getToRetailer() + "");
        }

        if (record.getGsbRemarks() != null && !record.getGsbRemarks().isEmpty()) {
            tvRemarks.setText(record.getGsbRemarks() + "");
        }

        if (record.getDftSerialNo() != null && !record.getDftSerialNo().isEmpty()) {
            tiedtSerialNo.setText(record.getDftSerialNo() + "");
        }

        if (record.getDftFridgeCompName() != null && !record.getDftFridgeCompName().isEmpty()) {
            tiedtFridgeCompanyName.setText(record.getDftFridgeCompName() + "");
        }

        if (record.getFridgeType() != null && !record.getFridgeType().isEmpty()) {
            tiedtFridgeType.setText(record.getFridgeType() + "");
        }

    }

}