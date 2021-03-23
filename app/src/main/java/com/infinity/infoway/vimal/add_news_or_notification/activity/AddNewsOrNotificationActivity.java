package com.infinity.infoway.vimal.add_news_or_notification.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add_news_or_notification.NewsOrNotificationImplementer;
import com.infinity.infoway.vimal.add_news_or_notification.adapter.DeprtmentListAdapter;
import com.infinity.infoway.vimal.add_news_or_notification.adapter.DesignationListAdapter;
import com.infinity.infoway.vimal.add_news_or_notification.adapter.UserListAdapter;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.DepartmentListPojo;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.DesignationListPojo;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.SaveNewsOrNotificationPojo;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.UserListPojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomEditText;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.infinity.infoway.vimal.kich_expense.Expense.Expense_Save_U.REQUEST_CODE_FOR_UPLOAD_DOC;

public class AddNewsOrNotificationActivity extends AppCompatActivity implements View.OnClickListener,
        UserListAdapter.IUser, DeprtmentListAdapter.IDepartment, DesignationListAdapter.IDesignation {

    private static final String SELECT_BROADCAST_TYPE = "Select Broadcast Type";


    private ArrayList<String> broadcastTypeArrayList = new ArrayList<>();
    private HashMap<String, String> broadcastTypeAndIdHashMap = new HashMap<>();
    private ImageView iv_back;
    private AppCompatTextView tv_title;
    private SharedPref getSharedPref;

    private SearchableSpinner spSelectBroadcastType;
    private MaterialCheckBox cbIsHighlyImportant;
    private CustomTextView tvChooseFile;
    private CustomTextView tvDestroyDate;
    private CustomEditText edtDescription;
    private MaterialCheckBox cbIsSendToAll;
    private LinearLayout llSendToAll;
    private RadioGroup rbtnGroup;
    private RecyclerView rvSelectUser;
    private RecyclerView rvSelectDepartment;
    private RecyclerView rvSelectDesignation;
    private CustomButtonView tvSubmit;
    private CustomButtonView tvCancel;
    private String selectedUserName = "";
    private String selectedDepartmentName = "";
    private String selectedDesignationName = "";

    private boolean isSelectUserHasData = false;
    private boolean isDepartmentHasData = false;
    private boolean isDesignationHasData = false;

    private RadioButton rbtnUser;
    private RadioButton rbtnDepartment;
    private RadioButton rbtnDesignation;

    private LinearLayout llSelectUser;
    private LinearLayout llDepartment;
    private LinearLayout llSelectDesignation;
    private Calendar myCalendar = Calendar.getInstance();
    private MultipartBody.Part fileToUpload = null;


    final DatePickerDialog.OnDateSetListener from_date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            tvDestroyDate.setText(sdf.format(myCalendar.getTime()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news_or_notification);
        initView();
        getUserList();
        getDepartmentList();
        getDesignationList();
    }

    private void initView() {
        getSharedPref = new SharedPref(AddNewsOrNotificationActivity.this);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("Add News/Notification");
        rbtnUser = findViewById(R.id.rbtnUser);
        rbtnDepartment = findViewById(R.id.rbtnDepartment);
        rbtnDesignation = findViewById(R.id.rbtnDesignation);
        llSelectUser = findViewById(R.id.llSelectUser);
        llDepartment = findViewById(R.id.llDepartment);
        llSelectDesignation = findViewById(R.id.llSelectDesignation);

        spSelectBroadcastType = findViewById(R.id.spSelectBroadcastType);
        cbIsHighlyImportant = findViewById(R.id.cbIsHighlyImportant);
        tvChooseFile = findViewById(R.id.tvChooseFile);
        tvChooseFile.setOnClickListener(this);
        tvDestroyDate = findViewById(R.id.tvDestroyDate);
        tvDestroyDate.setOnClickListener(this);
        edtDescription = findViewById(R.id.edtDescription);
        cbIsSendToAll = findViewById(R.id.cbIsSendToAll);
        llSendToAll = findViewById(R.id.llSendToAll);
        rbtnGroup = findViewById(R.id.rbtnGroup);
        rvSelectUser = findViewById(R.id.rvSelectUser);
        rvSelectDepartment = findViewById(R.id.rvSelectDepartment);
        rvSelectDesignation = findViewById(R.id.rvSelectDesignation);
        tvSubmit = findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(this);
        tvCancel = findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);

        broadcastTypeArrayList.add(SELECT_BROADCAST_TYPE);
        broadcastTypeArrayList.add("News");
        broadcastTypeAndIdHashMap.put("News", "1");
        broadcastTypeArrayList.add("Message");
        broadcastTypeAndIdHashMap.put("Message", "2");
        broadcastTypeArrayList.add("Instruction");
        broadcastTypeAndIdHashMap.put("Instruction", "3");

        ArrayAdapter<String> broadcastTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, broadcastTypeArrayList);
        broadcastTypeAdapter.setDropDownViewResource(R.layout.spinner_common_layout);
        spSelectBroadcastType.setTitle(SELECT_BROADCAST_TYPE);
        spSelectBroadcastType.setAdapter(broadcastTypeAdapter);

        cbIsSendToAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llSendToAll.setVisibility(View.GONE);
                } else {
                    llSendToAll.setVisibility(View.VISIBLE);
                }
            }
        });

        rbtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnUser) {
                    if (isSelectUserHasData) {
                        llSelectUser.setVisibility(View.VISIBLE);
                    }
                    llDepartment.setVisibility(View.GONE);
                    llSelectDesignation.setVisibility(View.GONE);
                } else if (checkedId == R.id.rbtnDepartment) {
                    if (isDepartmentHasData) {
                        llDepartment.setVisibility(View.VISIBLE);
                    }
                    llSelectUser.setVisibility(View.GONE);
                    llSelectDesignation.setVisibility(View.GONE);
                } else if (checkedId == R.id.rbtnDesignation) {
                    if (isDesignationHasData) {
                        llSelectDesignation.setVisibility(View.VISIBLE);
                    }
                    llDepartment.setVisibility(View.GONE);
                    llSelectUser.setVisibility(View.GONE);
                }
            }
        });


    }

    private boolean isValid() {
        if (spSelectBroadcastType.getSelectedItemPosition() == -1 || spSelectBroadcastType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select broadcast type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtDescription.getText().toString())) {
            Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!cbIsSendToAll.isChecked()) {
            if (rbtnGroup.getCheckedRadioButtonId() == R.id.rbtnUser && isSelectUserHasData && selectedUserName.isEmpty()) {
                Toast.makeText(this, "Please select User", Toast.LENGTH_SHORT).show();
                return false;
            } else if (rbtnGroup.getCheckedRadioButtonId() == R.id.rbtnDepartment && isDepartmentHasData && selectedDepartmentName.isEmpty()) {
                Toast.makeText(this, "Please select Department", Toast.LENGTH_SHORT).show();
                return false;
            } else if (rbtnGroup.getCheckedRadioButtonId() == R.id.rbtnDesignation && isDesignationHasData && selectedDesignationName.isEmpty()) {
                Toast.makeText(this, "Please select Designation", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        } else if (v.getId() == R.id.tvSubmit) {
            if (isValid()) {
                String app_version = String.valueOf(getSharedPref.getAppVersionCode());
                String android_id = getSharedPref.getAppAndroidId();
                String device_id = String.valueOf(getSharedPref.getRegisteredId());
                String user_id = getSharedPref.getRegisteredUserId();
                String key = Config.ACCESS_KEY;
                String comp_id = String.valueOf(getSharedPref.getCompanyId());
                String news_content = edtDescription.getText().toString();
                int news_type = Integer.parseInt(broadcastTypeAndIdHashMap.get(broadcastTypeArrayList.get(spSelectBroadcastType.getSelectedItemPosition())).trim());
                boolean news_high_imp = cbIsHighlyImportant.isChecked();
                boolean check_all = cbIsSendToAll.isChecked();
                int user_type = 0;
                String names = "";
                String destory_date = null;

                if (!cbIsSendToAll.isChecked()) {
                    if (rbtnGroup.getCheckedRadioButtonId() == R.id.rbtnUser) {
                        user_type = 1;
                        names = selectedUserName;
                    } else if (rbtnGroup.getCheckedRadioButtonId() == R.id.rbtnDepartment) {
                        user_type = 2;
                        names = selectedDepartmentName;
                    } else if (rbtnGroup.getCheckedRadioButtonId() == R.id.rbtnDesignation) {
                        user_type = 3;
                        names = selectedDesignationName;
                    }
                } else {
                    user_type = 0;
                    names = "";
                }

                if (!TextUtils.isEmpty(tvDestroyDate.getText().toString())) {
                    destory_date = tvDestroyDate.getText().toString();
                }

                RequestBody app_version_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(app_version));
                RequestBody android_id_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(android_id));
                RequestBody device_id_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(device_id));
                RequestBody user_id_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(user_id));
                RequestBody key_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(key));
                RequestBody comp_id_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(comp_id));
                RequestBody news_content_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(news_content));
                RequestBody news_type_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(news_type));
                RequestBody news_high_imp_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(news_high_imp));
                RequestBody check_all_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(check_all));
                RequestBody user_type_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(user_type));
                RequestBody names_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(names));
                RequestBody destory_date_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(destory_date));

                saveNewsOrNotificationApiCall(
                        app_version_req,
                        android_id_req,
                        device_id_req,
                        user_id_req,
                        key_req,
                        comp_id_req,
                        news_content_req,
                        news_type_req,
                        news_high_imp_req,
                        check_all_req,
                        user_type_req,
                        names_req,
                        fileToUpload,
                        destory_date_req);


            }
        } else if (v.getId() == R.id.tvCancel) {
            onBackPressed();
        } else if (v.getId() == R.id.tvDestroyDate) {
            DatePickerDialog datePickerDialog_from = new DatePickerDialog(AddNewsOrNotificationActivity.this, from_date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog_from.getDatePicker();//.setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog_from.show();
        } else if (v.getId() == R.id.tvChooseFile) {
            Intent intent = new Intent(AddNewsOrNotificationActivity.this,
                    com.jaiselrahman.filepicker.activity.FilePickerActivity.class);
            intent.putExtra(com.jaiselrahman.filepicker.activity.FilePickerActivity.CONFIGS,
                    new Configurations.Builder()
                            .setCheckPermission(true)
                            .setShowImages(true)
                            .setShowAudios(false)
                            .setShowVideos(false)
                            .enableImageCapture(false)
                            .setMaxSelection(1)
                            .setSkipZeroSizeFiles(true)
                            .build());
            startActivityForResult(intent, REQUEST_CODE_FOR_UPLOAD_DOC);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getUserList() {
        rbtnUser.setVisibility(View.GONE);
        NewsOrNotificationImplementer.getUserListApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), String.valueOf(getSharedPref.getCompanyId()), "1", "", new Callback<UserListPojo>() {
                    @Override
                    public void onResponse(Call<UserListPojo> call, Response<UserListPojo> response) {
                        try {
                            if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                                isSelectUserHasData = true;
                                rbtnUser.setVisibility(View.VISIBLE);
                                rvSelectUser.setAdapter(new UserListAdapter(AddNewsOrNotificationActivity.this, (ArrayList<UserListPojo.RECORD>) response.body().getRECORDS()));
                            } else {
                                isSelectUserHasData = false;
                                rbtnUser.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            isSelectUserHasData = false;
                            rbtnUser.setVisibility(View.GONE);
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserListPojo> call, Throwable t) {
                        isSelectUserHasData = false;
                        rbtnUser.setVisibility(View.GONE);
                        Toast.makeText(AddNewsOrNotificationActivity.this, "Request Failed:- " + t.getCause().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDepartmentList() {
        rbtnDepartment.setVisibility(View.GONE);
        NewsOrNotificationImplementer.getDepartmentListApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), String.valueOf(getSharedPref.getCompanyId()), "2", "", new Callback<DepartmentListPojo>() {
                    @Override
                    public void onResponse(Call<DepartmentListPojo> call, Response<DepartmentListPojo> response) {
                        try {
                            if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                                isDepartmentHasData = true;
                                rbtnDepartment.setVisibility(View.VISIBLE);
                                rvSelectDepartment.setAdapter(new DeprtmentListAdapter(AddNewsOrNotificationActivity.this, (ArrayList<DepartmentListPojo.RECORD>) response.body().getRECORDS()));
                            } else {
                                isDepartmentHasData = false;
                                rbtnDepartment.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            isDepartmentHasData = false;
                            rbtnDepartment.setVisibility(View.GONE);
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<DepartmentListPojo> call, Throwable t) {
                        isDepartmentHasData = false;
                        rbtnDepartment.setVisibility(View.GONE);
                        Toast.makeText(AddNewsOrNotificationActivity.this, "Request Failed:- " + t.getCause().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDesignationList() {
        rbtnDesignation.setVisibility(View.GONE);
        NewsOrNotificationImplementer.getDesignationListApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), String.valueOf(getSharedPref.getCompanyId()), "3", "", new Callback<DesignationListPojo>() {
                    @Override
                    public void onResponse(Call<DesignationListPojo> call, Response<DesignationListPojo> response) {
                        try {
                            if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                                isDesignationHasData = true;
                                rbtnDesignation.setVisibility(View.VISIBLE);
                                rvSelectDesignation.setAdapter(new DesignationListAdapter(AddNewsOrNotificationActivity.this, (ArrayList<DesignationListPojo.RECORD>) response.body().getRECORDS()));
                            } else {
                                isDesignationHasData = false;
                                rbtnDesignation.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            isDesignationHasData = false;
                            rbtnDesignation.setVisibility(View.GONE);
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<DesignationListPojo> call, Throwable t) {
                        isDesignationHasData = false;
                        rbtnDesignation.setVisibility(View.GONE);
                        Toast.makeText(AddNewsOrNotificationActivity.this, "Request Failed:- " + t.getCause().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onUserChecked(ArrayList<UserListPojo.RECORD> recordArrayList) {
        selectedUserName = "";
        for (int i = 0; i < recordArrayList.size(); i++) {
            if (recordArrayList.get(i).isChecked()) {
                selectedUserName = selectedUserName + "," + recordArrayList.get(i).getUsrmName() + "";
            }
        }
        selectedUserName = selectedUserName.replaceFirst(",", "");
    }

    @Override
    public void onDepartmentChecked(ArrayList<DepartmentListPojo.RECORD> recordArrayList) {
        selectedDepartmentName = "";
        for (int i = 0; i < recordArrayList.size(); i++) {
            if (recordArrayList.get(i).isChecked()) {
                selectedDepartmentName = selectedDepartmentName + "," + recordArrayList.get(i).getDepName() + "";
            }
        }
        selectedDepartmentName = selectedDepartmentName.replaceFirst(",", "");
    }

    @Override
    public void onDesignationChecked(ArrayList<DesignationListPojo.RECORD> recordArrayList) {
        selectedDesignationName = "";
        for (int i = 0; i < recordArrayList.size(); i++) {
            if (recordArrayList.get(i).isChecked()) {
                selectedDesignationName = selectedDesignationName + "," + recordArrayList.get(i).getDesName() + "";
            }
        }
        selectedDesignationName = selectedDesignationName.replaceFirst(",", "");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FOR_UPLOAD_DOC && resultCode == RESULT_OK) {
            try {
                ArrayList<MediaFile> files = data.getParcelableArrayListExtra(com.jaiselrahman.filepicker.activity.FilePickerActivity.MEDIA_FILES);
                if (data != null && files != null && files.size() == 1) {

                    String singleDynamicFilePath = files.get(0).getPath().trim();
                    File singleDynamicFile = new File(singleDynamicFilePath);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application*//*"), singleDynamicFile);
                    fileToUpload = MultipartBody.Part.createFormData("file", singleDynamicFile.getName(), requestBody);
                    tvChooseFile.setText(singleDynamicFile.getName());

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveNewsOrNotificationApiCall(RequestBody app_version,
                                               RequestBody android_id,
                                               RequestBody device_id,
                                               RequestBody user_id,
                                               RequestBody key,
                                               RequestBody comp_id,
                                               RequestBody news_content,
                                               RequestBody news_type,
                                               RequestBody news_high_imp,
                                               RequestBody check_all,
                                               RequestBody user_type,
                                               RequestBody names,
                                               MultipartBody.Part file,
                                               RequestBody destory_date) {
        DialogUtils.showProgressDialogNotCancelable(AddNewsOrNotificationActivity.this, "");
        NewsOrNotificationImplementer.saveNewsAndNotificationApiImplementer(app_version, android_id, device_id, user_id, key
                , comp_id, news_content, news_type, news_high_imp, check_all, user_type, names, file, destory_date, new Callback<SaveNewsOrNotificationPojo>() {
                    @Override
                    public void onResponse(Call<SaveNewsOrNotificationPojo> call, Response<SaveNewsOrNotificationPojo> response) {
                        DialogUtils.hideProgressDialog();
                        try {
                            if (response.isSuccessful() && response.body() != null && response.body().getFLAG() == 1) {
                                Toast.makeText(AddNewsOrNotificationActivity.this, "" + response.body().getMESSAGE(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddNewsOrNotificationActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<SaveNewsOrNotificationPojo> call, Throwable t) {
                        DialogUtils.hideProgressDialog();
                        Toast.makeText(AddNewsOrNotificationActivity.this, "Request Failed:- " + t.getCause().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}