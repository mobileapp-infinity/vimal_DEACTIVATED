package com.infinity.infoway.vimal.Advertisement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.DeepFridge.Fridge_Listing;
import com.infinity.infoway.vimal.DeepFridge.Fridge_Request_Add;
import com.infinity.infoway.vimal.DeepFridge.Pojo.Get_retailer_by_distributer_idPOJO;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.Distributor_Pojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;

import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomEditText;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adv_Save extends AppCompatActivity implements View.OnClickListener {
    private RadioButton rb_new;
    private RadioButton rb_existing;
    private RadioGroup rg;
    private SearchableSpinner spdistributor_name;
    private SearchableSpinner spvendor_name;
    private SearchableSpinner spretailer_name;
    private CustomEditText ed_hording_print_name;
    private CustomEditText ed_address_1;
    private CustomEditText ed_address_2;
    private SearchableSpinner sp_city;
    private SearchableSpinner sp_state;
    private SearchableSpinner sp_country;
    private CustomEditText ed_pin_code;
    private CustomEditText et_from_date;
    private CustomEditText et_todate;
    private CustomEditText et_photo;
    private TextInputLayout et_input_complaint_photo;
    private CustomEditText et_contact_no;
    private CustomEditText et_dealer_start_date;
    private CustomEditText et_dealer_ytd;
    private CustomEditText et_last_date;
    private CustomEditText et_gsb_given_amt;
    private RadioButton rb_OwnBase;
    private RadioButton rb_T_Base;
    private RadioButton rb_DepositBase;
    private RadioButton rb_FOCBase;
    private RadioGroup rg_category;
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private ImageView iv_add;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    private CustomButtonView tv_submit;
    private CustomButtonView tv_cancel;
    private LinearLayout ll_btn;
    private CustomBoldTextView tv_shop_name;
    private CustomTextView tv_empty;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adv_save_hoarding_request);
        getSharedPref = new SharedPref(Adv_Save.this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        queue = Volley.newRequestQueue(this);
        initViews();
        txt_act.setText("Advertisement");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Get_All_Distributor();
        spdistributor_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if( i>0) {
                    Get_retailer_by_distributer_id(listDistributorResponse.get(i-1).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spretailer_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0)
                {
                    ed_address_1.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(i).cus_add1+"");
                    ed_address_2.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(i).cus_add2+"");
                    ed_pin_code.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(i).cus_pincode+"");
                    et_contact_no.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(i).cus_mobile_no+"");
                    //    ed_address_2.setText("");
                }
                else{
                    ed_address_1.setText("");
                    ed_address_2.setText("");
                    ed_pin_code.setText("");
                    et_contact_no.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    File photo = null;
    private MultipartBody.Part photo_fileToUpload = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data); if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            if (files != null) {
                for (int i = 0; i < files.size(); i++) {
                    System.out.println("file name " + files.get(i).getName() + "");
                    System.out.println("file path " + files.get(i).getUri() + "");
                    System.out.println("file path " + files.get(i).getPath() + "");

                    photo = new File(files.get(i).getPath() + "");
                    et_photo.setText(photo.getName() + "");
                    RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), photo);
//
//            //fileToUpload = MultipartBody.Part.createFormData("file[0]", SELECTED_FILE.getName(), mFile);
                    photo_fileToUpload = MultipartBody.Part.createFormData("file", photo.getName(), mFile);
                }
            }
        }

    }

    void file_select(int request_code) {
        Intent intent = new Intent(this, FilePickerActivity.class);
        //   startActivityForResult(intent, 1);

        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .setShowAudios(false)
                .setShowVideos(false)
                .setShowFiles(false)
                .enableImageCapture(false)
                .setMaxSelection(1)

                .setSkipZeroSizeFiles(true)
                .build());
        startActivityForResult(intent, request_code);
    }
    private void initViews() {
        rb_new = findViewById(R.id.rb_new);
        rb_existing = findViewById(R.id.rb_existing);
        rg = findViewById(R.id.rg);
        spdistributor_name = findViewById(R.id.spdistributor_name);
        spvendor_name = findViewById(R.id.spvendor_name);
        spretailer_name = findViewById(R.id.spretailer_name);
        ed_hording_print_name = findViewById(R.id.ed_hording_print_name);
        ed_address_1 = findViewById(R.id.ed_address_1);
        ed_address_2 = findViewById(R.id.ed_address_2);
        sp_city = findViewById(R.id.sp_city);
        sp_state = findViewById(R.id.sp_state);
        sp_country = findViewById(R.id.sp_country);
        ed_pin_code = findViewById(R.id.ed_pin_code);
        et_from_date = findViewById(R.id.et_from_date);
        et_todate = findViewById(R.id.et_todate);
        et_photo = findViewById(R.id.et_photo);
        et_input_complaint_photo = findViewById(R.id.et_input_complaint_photo);
        et_contact_no = findViewById(R.id.et_contact_no);
        et_dealer_start_date = findViewById(R.id.et_dealer_start_date);
        et_dealer_ytd = findViewById(R.id.et_dealer_ytd);
        et_last_date = findViewById(R.id.et_last_date);
        et_gsb_given_amt = findViewById(R.id.et_gsb_given_amt);
        rb_OwnBase = findViewById(R.id.rb_OwnBase);
        rb_T_Base = findViewById(R.id.rb_T_Base);
        rb_DepositBase = findViewById(R.id.rb_DepositBase);
        rb_FOCBase = findViewById(R.id.rb_FOCBase);
        rg_category = findViewById(R.id.rg_category);
        iv_back = findViewById(R.id.iv_back);
        txt_act = findViewById(R.id.txt_act);
        iv_info = findViewById(R.id.iv_info);
        iv_profile = findViewById(R.id.iv_profile);
        iv_add = findViewById(R.id.iv_add);
        rel = findViewById(R.id.rel);
        toolbar_act = findViewById(R.id.toolbar_act);
        toolbarContainer = findViewById(R.id.toolbarContainer);
        tv_submit = findViewById(R.id.tv_submit);
        tv_cancel = findViewById(R.id.tv_cancel);
        ll_btn = findViewById(R.id.ll_btn);
        tv_shop_name = findViewById(R.id.tv_shop_name);
        tv_empty = findViewById(R.id.tv_empty);
        lv = findViewById(R.id.lv);
        et_photo.setOnClickListener(this);
    }

    private ProgressDialog progDialog;
    private ApiInterface apiService;
    private SharedPref getSharedPref;
    private List<Distributor_Pojo.RECORDSBean> listDistributorResponse;
    ArrayList<String> list_distributor = new ArrayList<>();
    private void Get_All_Distributor() {


        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
        list_distributor = new ArrayList<>();
        list_distributor.add("Select Distributor");
        try {
            progDialog = new ProgressDialog(Adv_Save.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }





        Call<Distributor_Pojo> call = apiService.Get_All_Distributor(String.valueOf(getSharedPref.getAppVersionCode()+""), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), Config.ACCESS_KEY, String.valueOf(getSharedPref.getRegisteredUserId()), getSharedPref.getCompanyId() + "");
        call.enqueue(new Callback<Distributor_Pojo>() {
            @Override
            public void onResponse(Call<Distributor_Pojo> call, Response<Distributor_Pojo> response) {
                System.out.println("Response!!!!!!!!! " + call.request() + "");
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }
                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {

                        listDistributorResponse = response.body().getRECORDS();
                        int logged_in_distributor_position = 0;
                        if (listDistributorResponse != null && listDistributorResponse.size() > 0) {
                            String Cus_Id = getSharedPref.getLoginCustomerId();

                            for(int i= 0;i<listDistributorResponse.size();i++)
                            {
                                list_distributor.add(listDistributorResponse.get(i).getCus_name()+"");
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                            list_distributor);
                            adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                            //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                            spdistributor_name.setTitle("Select Distributor");

                            spdistributor_name.setAdapter(adapter);



                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<Distributor_Pojo> call, Throwable t) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }
            }
        });
    }
    Get_retailer_by_distributer_idPOJO get_retailer_by_distributer_idPOJO;
    ArrayList<String> list_retailer = new ArrayList<>();
    RequestQueue queue;
    private void Get_retailer_by_distributer_id(int distributer_id) {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_retailer_by_distributer_id + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&distributer_id=" + distributer_id + "&branch_id=" + getSharedPref.getBranchId() + "";// + "&status=" + status;
        // list_Fridge_Type = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_retailer_by_distributer_id URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                //  DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                list_retailer = new ArrayList<>();
                list_retailer.add("Select Retailer Name");
                // get_retailer_by_distributer_idPOJO = new Get_retailer_by_distributer_idPOJO();
                get_retailer_by_distributer_idPOJO = gson.fromJson(response, Get_retailer_by_distributer_idPOJO.class);
                if (get_retailer_by_distributer_idPOJO != null &&
                        get_retailer_by_distributer_idPOJO.RECORDS != null) {
                    if (get_retailer_by_distributer_idPOJO.RECORDS.size() > 0) {
                        for (int i = 0; i < get_retailer_by_distributer_idPOJO.RECORDS.size(); i++) {
                            // if (get_fridge_typePojo.RECORDS.get(i).is_visible_to_user) {
                            list_retailer.add(get_retailer_by_distributer_idPOJO.RECORDS.get(i).cus_name+"");
                            //  }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_retailer);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        spretailer_name.setTitle("Select Retailer Name");

                        spretailer_name.setAdapter(adapter);




                    } else {
                        DialogUtils.Show_Toast(Adv_Save.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Adv_Save.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_photo:
                file_select(1);

                break;
        }
    }
}
