package com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.api.response.Distributor_Pojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.adapter.FreezeItemListAdapter;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.GetFreezeTypePojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.GetItemDetailsByRetailerIdPojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.GetRetailerByDistributorIdPojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.InsertGsbAndTransferEntryReqPojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.InsertOrUpdateFreezeDetailsPojo;
import com.infinity.infoway.vimal.util.common.DialogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGsbAndDeepFreezeTransferEntryActivity extends AppCompatActivity implements View.OnClickListener,
        FreezeItemListAdapter.IOnFreezeItemChecked {

    private static final String FOR_GSB_VALUE = "1";
    private static final String FOR_DEEP_FREEZE_VALUE = "2";

    private SharedPref mySharedPrefereces;
    private RadioGroup rGroupGsbAndDeepFreeze;
    private TextInputEditText tvTransferDate;
    private TextInputLayout tilFromDistributor;
    private AutoCompleteTextView actvFromDistributor;
    private TextInputLayout tilToDistributor;
    private AutoCompleteTextView actvToDistributor;
    private TextInputLayout tilFromRetailerName;
    private AutoCompleteTextView actvFromRetailerName;
    private TextInputLayout tilToRetailerName;
    private AutoCompleteTextView actvToRetailerName;
    private TextInputEditText tvRemarks;
    private LinearLayout llForDeepFreeze;
    private TextInputEditText tiedtSerialNo;
    private TextInputEditText tedtFridgeCompanyName;
    private TextInputLayout tilFridgeType;
    private AutoCompleteTextView actvFridgeType;
    private RecyclerView rvItemList;
    private MaterialButton btnCancel;
    private MaterialButton btnSubmit;
    private AppCompatImageView ivBack;
    private Calendar transferDateCalender = Calendar.getInstance();
    private String myDateFormat = "dd-MM-yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myDateFormat, Locale.US);
    private int selectedFromDistributor = -1;
    private int selectedToDistributor = -1;
    private int selectedFromRetailerName = -1;
    private int selectedToRetailerName = -1;
    private int selectedFreezeType = -1;
    private LinearLayout llFreezeItemList;

    private String appVersion;
    private String androidId;
    private String deviceId;
    private String userId;
    private String compId;

    private ArrayList<String> distributorArrayList;
    private HashMap<String, String> distributorNameAndIdHaspMap;

    private ArrayList<String> fromRetailerArrayList;
    private HashMap<String, String> fromRetailerNameAndIdHaspMap;

    private ArrayList<String> toRetailerArrayList;
    private HashMap<String, String> toRetailerNameAndIdHaspMap;

    private ArrayList<String> freezeTypeArrayList;
    private HashMap<String, String> freezeTypeAndIdHaspMap;

    private HashMap<String, GetItemDetailsByRetailerIdPojo.Record> selectedItems = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gsb_and_deep_freeze_transfer_entry);
        initView();
        getAllDistributorApiCall(true, true);
        getAllFreezeTypeApiCall();
    }


    private void initView() {
        mySharedPrefereces = new SharedPref(AddGsbAndDeepFreezeTransferEntryActivity.this);

        appVersion = String.valueOf(mySharedPrefereces.getAppVersionCode());
        androidId = mySharedPrefereces.getAppAndroidId();
        deviceId = String.valueOf(mySharedPrefereces.getRegisteredId());
        userId = mySharedPrefereces.getRegisteredUserId();
        compId = mySharedPrefereces.getCompanyId();

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        rGroupGsbAndDeepFreeze = findViewById(R.id.rGroupGsbAndDeepFreeze);
        tvTransferDate = findViewById(R.id.tvTransferDate);
        tvTransferDate.setOnClickListener(this);
        tilFromDistributor = findViewById(R.id.tilFromDistributor);
        actvFromDistributor = findViewById(R.id.actvFromDistributor);
        tilToDistributor = findViewById(R.id.tilToDistributor);
        actvToDistributor = findViewById(R.id.actvToDistributor);
        tilFromRetailerName = findViewById(R.id.tilFromRetailerName);
        actvFromRetailerName = findViewById(R.id.actvFromRetailerName);
        tilToRetailerName = findViewById(R.id.tilToRetailerName);
        actvToRetailerName = findViewById(R.id.actvToRetailerName);
        tvRemarks = findViewById(R.id.tvRemarks);
        llForDeepFreeze = findViewById(R.id.llForDeepFreeze);
        tiedtSerialNo = findViewById(R.id.tiedtSerialNo);
        tedtFridgeCompanyName = findViewById(R.id.tiedtFridgeCompanyName);
        tilFridgeType = findViewById(R.id.tilFridgeType);
        actvFridgeType = findViewById(R.id.actvFridgeType);
        rvItemList = findViewById(R.id.rvItemList);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        llFreezeItemList = findViewById(R.id.llFreezeItemList);
        Date c = Calendar.getInstance().getTime();
        tvTransferDate.setText(simpleDateFormat.format(c));

        rGroupGsbAndDeepFreeze.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnGsb) {
                    llForDeepFreeze.setVisibility(View.GONE);
                } else {
                    llForDeepFreeze.setVisibility(View.VISIBLE);
                }
            }
        });

        actvFromDistributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFromDistributor = position;
                tilFromDistributor.setError("");
                String distributorId = distributorNameAndIdHaspMap.get(distributorArrayList.get(position).trim());
                getFromRetailerByDistributorIdApiCall(true, true, distributorId);
            }
        });

        actvToDistributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedToDistributor = position;
                tilToDistributor.setError("");
                String distributorId = distributorNameAndIdHaspMap.get(distributorArrayList.get(position).trim());
                getToRetailerByDistributorIdApiCall(true, true, distributorId);
            }
        });

        actvFromRetailerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFromRetailerName = position;
                tilFromRetailerName.setError("");
                String retailerId = fromRetailerNameAndIdHaspMap.get(fromRetailerArrayList.get(position).trim());
                String type_flag = rGroupGsbAndDeepFreeze.getCheckedRadioButtonId() == R.id.rbtnGsb ? "1" : "2";
                getItemDetailsByRetailerIdApiCall(true, true, retailerId,type_flag);
            }
        });

        actvToRetailerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedToRetailerName = position;
                tilToRetailerName.setError("");
            }
        });

        actvFridgeType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFreezeType = position;
            }
        });
    }

    private boolean isValid() {
        if (selectedFromDistributor == -1) {
            tilFromDistributor.setError("Select From Distributor");
            return false;
        } else if (selectedToDistributor == -1) {
            tilFromDistributor.setError("");
            tilToDistributor.setError("Select To Distributor");
            return false;
        } else if (selectedFromRetailerName == -1) {
            tilFromDistributor.setError("");
            tilToDistributor.setError("");
            tilFromRetailerName.setError("Select From Retailer Name");
            return false;
        } else if (selectedToRetailerName == -1) {
            tilFromDistributor.setError("");
            tilToDistributor.setError("");
            tilFromRetailerName.setError("");
            tilToRetailerName.setError("Select To Retailer Name");
            return false;
        } else {
            tilFromDistributor.setError("");
            tilToDistributor.setError("");
            tilFromRetailerName.setError("");
            tilToRetailerName.setError("");
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack || v.getId() == R.id.btnCancel) {
            onBackPressed();
        } else if (v.getId() == R.id.btnSubmit) {
            if (isValid()) {
                if (selectedItems != null && selectedItems.size() > 0) {
                    String transfer_type = rGroupGsbAndDeepFreeze.getCheckedRadioButtonId() == R.id.rbtnGsb ? "0" : "1";
                    String transfer_dnt = tvTransferDate.getText().toString().trim();
                    String from_distributor_id = distributorNameAndIdHaspMap.get(distributorArrayList.get(selectedFromDistributor).trim()) + "";
                    String to_distributor_id = distributorNameAndIdHaspMap.get(distributorArrayList.get(selectedToDistributor).trim()) + "";
                    String from_retailer_id = fromRetailerNameAndIdHaspMap.get(fromRetailerArrayList.get(selectedFromRetailerName).trim()) + "";
                    String to_retailer_id = toRetailerNameAndIdHaspMap.get(toRetailerArrayList.get(selectedToRetailerName).trim()) + "";
                    String remarks = tvRemarks.getText().toString().trim();
                    String item_detail = "[]";
                    String id = "0";
                    String serial_no = tiedtSerialNo.getText().toString().trim();
                    String fridge_type = selectedFreezeType == -1 ? "" : freezeTypeArrayList.get(selectedFreezeType).trim() + "";
                    String fridge_comp_name = tedtFridgeCompanyName.getText().toString().trim() + "";

                    try {
                        JSONArray itemDetailsJsonArray = new JSONArray();
                        JSONObject itemJson;

                        for (String key : selectedItems.keySet()) {
                            GetItemDetailsByRetailerIdPojo.Record record = selectedItems.get(key);
                            if (record.isChecked() && record.getEnteredTransferQty() != 0) {
                                itemJson = new JSONObject();
                                if (record.getId() != null) {
                                    itemJson.put("item_id", record.getId());
                                }
                                if (record.getTransferQty() != null) {
                                    itemJson.put("actual_qty", Double.parseDouble(record.getHrdQty().toString()));
                                }
                                itemJson.put("transfer_qty", Double.parseDouble(record.getEnteredTransferQty() + ""));
                                itemDetailsJsonArray.put(itemJson);
                            } else {
                                Toast.makeText(this, "Please enter transfer quantity in " + record.getItmName(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        item_detail = itemDetailsJsonArray.toString();
                        insertGsbAndFreezeTransferEntryApiCall(transfer_type, transfer_dnt, from_distributor_id, to_distributor_id, from_retailer_id,
                                to_retailer_id, remarks, item_detail, id, serial_no, fridge_type, fridge_comp_name);
                    } catch (Exception exception) {
                        DialogUtils.hideProgressDialog();
                        Toast.makeText(this, "Exception to insert transfer entry!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please select items!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v.getId() == R.id.tvTransferDate) {
            showTransferDateDialog();
        }
    }

    private void showTransferDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddGsbAndDeepFreezeTransferEntryActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        try {
                            transferDateCalender.set(Calendar.YEAR, year);
                            transferDateCalender.set(Calendar.MONTH, monthOfYear);
                            transferDateCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            Date fromDate = null;
                            fromDate = simpleDateFormat.parse(simpleDateFormat.format(transferDateCalender.getTime()));
                            String selectedDeliveryDate = simpleDateFormat.format(fromDate);
                            tvTransferDate.setText(selectedDeliveryDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, transferDateCalender.get(Calendar.YEAR), transferDateCalender.get(Calendar.MONTH), transferDateCalender.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCheckOrUnChecked(boolean isChecked, GetItemDetailsByRetailerIdPojo.Record record) {
        if (isChecked) {
            selectedItems.put(record.getId() + "", record);
        } else {
            selectedItems.remove(record.getId() + "");
        }
    }

    private void getAllDistributorApiCall(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            DialogUtils.showProgressDialogNotCancelable(AddGsbAndDeepFreezeTransferEntryActivity.this, "");
        }
        ApiImplementer.getAllDistributorApiImplementer(appVersion, androidId, deviceId, userId, compId, new Callback<Distributor_Pojo>() {
            @Override
            public void onResponse(Call<Distributor_Pojo> call, Response<Distributor_Pojo> response) {
                if (isPdHide) {
                    DialogUtils.hideProgressDialog();
                }
                if (response.isSuccessful() && response.body() != null && response.body().getRECORDS() != null &&
                        response.body().getRECORDS().size() > 0) {
                    distributorArrayList = new ArrayList<>();
                    distributorNameAndIdHaspMap = new HashMap<>();
                    for (int i = 0; i < response.body().getRECORDS().size(); i++) {
                        if (response.body().getRECORDS().get(i).getCus_name() != null &&
                                !response.body().getRECORDS().get(i).getCus_name().isEmpty()) {
                            String disName = response.body().getRECORDS().get(i).getCus_name().trim();
                            String disId = response.body().getRECORDS().get(i).getId() + "";
                            distributorArrayList.add(disName);
                            distributorNameAndIdHaspMap.put(disName, disId);
                        }
                    }
                    ArrayAdapter<String> distributorAdapter = new ArrayAdapter<String>(AddGsbAndDeepFreezeTransferEntryActivity.this, android.R.layout.select_dialog_item, distributorArrayList);
                    actvFromDistributor.setAdapter(distributorAdapter);
                    actvToDistributor.setAdapter(distributorAdapter);
                } else {
                    if (!isPdHide) {
                        DialogUtils.hideProgressDialog();
                    }
                    Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Distributor not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Distributor_Pojo> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Failed to get distributor list!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFromRetailerByDistributorIdApiCall(boolean isPdShow, boolean isPdHide, String distributorId) {
        if (isPdShow) {
            DialogUtils.showProgressDialogNotCancelable(AddGsbAndDeepFreezeTransferEntryActivity.this, "");
        }
        ApiImplementer.getRetailerByDistributorIdApiImplementer(appVersion, androidId, deviceId, userId, compId, "0", distributorId, new Callback<GetRetailerByDistributorIdPojo>() {
            @Override
            public void onResponse(Call<GetRetailerByDistributorIdPojo> call, Response<GetRetailerByDistributorIdPojo> response) {
                if (isPdHide) {
                    DialogUtils.hideProgressDialog();
                }
                if (response.isSuccessful() && response.body() != null && response.body().getRecords() != null &&
                        response.body().getRecords().size() > 0) {
                    fromRetailerArrayList = new ArrayList<>();
                    fromRetailerNameAndIdHaspMap = new HashMap<>();
                    for (int i = 0; i < response.body().getRecords().size(); i++) {
                        if (response.body().getRecords().get(i).getCusName() != null &&
                                !response.body().getRecords().get(i).getCusName().isEmpty()) {
                            String retailerName = response.body().getRecords().get(i).getCusName().trim();
                            String retailerId = response.body().getRecords().get(i).getCusId() + "";
                            fromRetailerArrayList.add(retailerName);
                            fromRetailerNameAndIdHaspMap.put(retailerName, retailerId);
                        }
                    }
                    ArrayAdapter<String> fromRetailerAdapter = new ArrayAdapter<String>(AddGsbAndDeepFreezeTransferEntryActivity.this, android.R.layout.select_dialog_item, fromRetailerArrayList);
                    actvFromRetailerName.setAdapter(fromRetailerAdapter);
                } else {
                    selectedFromRetailerName = -1;
                    fromRetailerArrayList = new ArrayList<>();
                    fromRetailerNameAndIdHaspMap = new HashMap<>();
                    ArrayAdapter<String> fromRetailerAdapter = new ArrayAdapter<String>(AddGsbAndDeepFreezeTransferEntryActivity.this, android.R.layout.select_dialog_item, fromRetailerArrayList);
                    actvFromRetailerName.setAdapter(fromRetailerAdapter);
                    if (!isPdHide) {
                        DialogUtils.hideProgressDialog();
                    }
                    Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Retailer not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetRetailerByDistributorIdPojo> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                selectedFromRetailerName = -1;
                Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Failed to get retailer list!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getToRetailerByDistributorIdApiCall(boolean isPdShow, boolean isPdHide, String distributorId) {
        if (isPdShow) {
            DialogUtils.showProgressDialogNotCancelable(AddGsbAndDeepFreezeTransferEntryActivity.this, "");
        }
        ApiImplementer.getRetailerByDistributorIdApiImplementer(appVersion, androidId, deviceId, userId, compId, "0", distributorId, new Callback<GetRetailerByDistributorIdPojo>() {
            @Override
            public void onResponse(Call<GetRetailerByDistributorIdPojo> call, Response<GetRetailerByDistributorIdPojo> response) {
                if (isPdHide) {
                    DialogUtils.hideProgressDialog();
                }
                if (response.isSuccessful() && response.body() != null && response.body().getRecords() != null &&
                        response.body().getRecords().size() > 0) {
                    toRetailerArrayList = new ArrayList<>();
                    toRetailerNameAndIdHaspMap = new HashMap<>();
                    for (int i = 0; i < response.body().getRecords().size(); i++) {
                        if (response.body().getRecords().get(i).getCusName() != null &&
                                !response.body().getRecords().get(i).getCusName().isEmpty()) {
                            String retailerName = response.body().getRecords().get(i).getCusName().trim();
                            String retailerId = response.body().getRecords().get(i).getCusId() + "";
                            toRetailerArrayList.add(retailerName);
                            toRetailerNameAndIdHaspMap.put(retailerName, retailerId);
                        }
                    }
                    ArrayAdapter<String> toRetailerAdapter = new ArrayAdapter<String>(AddGsbAndDeepFreezeTransferEntryActivity.this, android.R.layout.select_dialog_item, toRetailerArrayList);
                    actvToRetailerName.setAdapter(toRetailerAdapter);
                } else {
                    selectedToRetailerName = -1;
                    toRetailerArrayList = new ArrayList<>();
                    toRetailerNameAndIdHaspMap = new HashMap<>();
                    ArrayAdapter<String> toRetailerAdapter = new ArrayAdapter<String>(AddGsbAndDeepFreezeTransferEntryActivity.this, android.R.layout.select_dialog_item, toRetailerArrayList);
                    actvToRetailerName.setAdapter(toRetailerAdapter);
                    if (!isPdHide) {
                        DialogUtils.hideProgressDialog();
                    }
                    Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Retailer not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetRetailerByDistributorIdPojo> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                selectedToRetailerName = -1;
                Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Failed to get retailer list!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllFreezeTypeApiCall() {
        ApiImplementer.getFreezeTypeApiImplementer(appVersion, androidId, deviceId, userId, compId, new Callback<GetFreezeTypePojo>() {
            @Override
            public void onResponse(Call<GetFreezeTypePojo> call, Response<GetFreezeTypePojo> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getRecords() != null &&
                        response.body().getRecords().size() > 0) {
                    freezeTypeArrayList = new ArrayList<>();
                    freezeTypeAndIdHaspMap = new HashMap<>();
                    for (int i = 0; i < response.body().getRecords().size(); i++) {
                        if (response.body().getRecords().get(i).getEbdName() != null &&
                                !response.body().getRecords().get(i).getEbdName().isEmpty()) {
                            String freezeType = response.body().getRecords().get(i).getEbdName().trim();
                            String freezeTypeId = response.body().getRecords().get(i).getId() + "";
                            freezeTypeArrayList.add(freezeType);
                            freezeTypeAndIdHaspMap.put(freezeType, freezeTypeId);
                        }
                    }
                    ArrayAdapter<String> freezeTypeAdapter = new ArrayAdapter<String>(AddGsbAndDeepFreezeTransferEntryActivity.this, android.R.layout.select_dialog_item, freezeTypeArrayList);
                    actvFridgeType.setAdapter(freezeTypeAdapter);
                } else {
                    Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Freeze type not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFreezeTypePojo> call, Throwable t) {
                Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Failed to get freeze type!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getItemDetailsByRetailerIdApiCall(boolean isPdShow, boolean isPdHide, String retailerId,String type_flag) {
        if (isPdShow) {
            DialogUtils.showProgressDialogNotCancelable(AddGsbAndDeepFreezeTransferEntryActivity.this, "");
        }
        llFreezeItemList.setVisibility(View.GONE);
        ApiImplementer.getItemDetailsByRetailerIdApiImplementer(appVersion, androidId, deviceId, userId, compId, retailerId,type_flag, new Callback<GetItemDetailsByRetailerIdPojo>() {
            @Override
            public void onResponse(Call<GetItemDetailsByRetailerIdPojo> call, Response<GetItemDetailsByRetailerIdPojo> response) {
                if (isPdHide) {
                    DialogUtils.hideProgressDialog();
                }
                if (response.isSuccessful() && response.body() != null && response.body().getRecords() != null &&
                        response.body().getRecords().size() > 0) {
                    llFreezeItemList.setVisibility(View.VISIBLE);
                    rvItemList.setAdapter(new FreezeItemListAdapter(AddGsbAndDeepFreezeTransferEntryActivity.this, (ArrayList<GetItemDetailsByRetailerIdPojo.Record>) response.body().getRecords()));
                } else {
                    llFreezeItemList.setVisibility(View.GONE);
                    if (!isPdHide) {
                        DialogUtils.hideProgressDialog();
                    }
                    Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Items not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetItemDetailsByRetailerIdPojo> call, Throwable t) {
                llFreezeItemList.setVisibility(View.GONE);
                DialogUtils.hideProgressDialog();
                Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Failed to get item list!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertGsbAndFreezeTransferEntryApiCall(String transfer_type, String transfer_dnt, String from_distributor_id, String to_distributor_id,
                                                        String from_retailer_id, String to_retailer_id, String remarks, String item_detail, String id,
                                                        String serial_no, String fridge_type, String fridge_comp_name) {

        InsertGsbAndTransferEntryReqPojo insertGsbAndTransferEntryReqPojo = new InsertGsbAndTransferEntryReqPojo(appVersion, androidId, deviceId, userId,
                Config.ACCESS_KEY, compId, transfer_type, transfer_dnt, from_distributor_id, to_distributor_id, from_retailer_id, to_retailer_id,
                remarks, item_detail, id, serial_no, fridge_type, fridge_comp_name);

        DialogUtils.showProgressDialogNotCancelable(AddGsbAndDeepFreezeTransferEntryActivity.this, "");
        ApiImplementer.insertOrUpdateDeepFreezeTransferApiImplementer(insertGsbAndTransferEntryReqPojo, new Callback<InsertOrUpdateFreezeDetailsPojo>() {
            @Override
            public void onResponse(Call<InsertOrUpdateFreezeDetailsPojo> call, Response<InsertOrUpdateFreezeDetailsPojo> response) {
                DialogUtils.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null && response.body().getId() != null) {
                    if (response.body().getMessage() != null && !response.body().getMessage().isEmpty()) {
                        Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Something went wrong,Please try again later!" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertOrUpdateFreezeDetailsPojo> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                Toast.makeText(AddGsbAndDeepFreezeTransferEntryActivity.this, "Failed to insert transfer entry!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}