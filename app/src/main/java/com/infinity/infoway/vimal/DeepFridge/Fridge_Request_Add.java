package com.infinity.infoway.vimal.DeepFridge;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.DeepFridge.Pojo.GetBankNamesPOJO;
import com.infinity.infoway.vimal.DeepFridge.Pojo.GetCity_List_POJO;
import com.infinity.infoway.vimal.DeepFridge.Pojo.GetState_List_POJO;
import com.infinity.infoway.vimal.DeepFridge.Pojo.Get_all_item_list_POJO;
import com.infinity.infoway.vimal.DeepFridge.Pojo.Get_distributor_fridge_typePojo;
import com.infinity.infoway.vimal.DeepFridge.Pojo.Get_fridge_typePojo;
import com.infinity.infoway.vimal.DeepFridge.Pojo.Get_retailer_by_distributer_idPOJO;
import com.infinity.infoway.vimal.DeepFridge.Pojo.Save_Fridge_POJO;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add_news_or_notification.activity.AddNewsOrNotificationActivity;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.SaveNewsOrNotificationPojo;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllEmployeeByDesignationPojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Distributor_and_its_Retailer_detail_Pojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Fridge_Request_Add extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private ImageView iv_add;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    private Button tv_pg_1;
    private Button tv_pg_2;
    private Button tv_pg_3;
    private Button tv_pg_4;
    private LinearLayout ll_btn_header;
    private EditText ed_ref_no;
    private TextView ed_date;
    private EditText ed_approx_sale;
    private SearchableSpinner sp_Distributor_Firm_Name;
    private SearchableSpinner sp_Distributor_City_Name;
    private SearchableSpinner sp_Sales_person;
    private SearchableSpinner sp_Retailer_Firm_Name;
    private EditText ed_Mobile_No;
    private EditText ed_address_1;
    private EditText ed_retailer_pincode;
    private EditText ed_address_2;
    private EditText ed_address_3;
    private EditText ed_retailer_city;
    private EditText ed_retailer_state;
    private EditText ed_owner_Name;
    private EditText ed_owner_Mobile_No;
    private EditText ed_owner_address_1;
    private EditText ed_owner_address_2;
    private EditText ed_owner_address_3;
    private SearchableSpinner sp_owner_city;
    private SearchableSpinner sp_owner_state;
    private EditText ed_owner_pincode;
    //   private EditText ed_owner_mobile_no;
    private SearchableSpinner sp_Distributor_Fridge_Type;
    private SearchableSpinner sp_item_name;
    private SearchableSpinner sp_fridge_type;
    private EditText ed_Fridge_Company_Name;
    private EditText ed_item_qty;
    private EditText ed_Coupon_From_No;
    private EditText ed_Coupon_To_No;
    private EditText ed_Total_Coupon;
    private SearchableSpinner sp_payment_mode;
    private EditText ed_bank_dd_no;
    private TextView ed_bank_DD_Date;
    private LinearLayout lin_bank_dd;
    private SearchableSpinner sp_bank_cheque_account;
    private EditText ed_bank_Cheque_No;
    //  private EditText ed_bank_Cheque_Date;
    private TextView ed_bank_Cheque_Date;
    private LinearLayout lin_bank_cheque;
    private EditText ed_Deposite;
    private EditText ed_Service_Charge;
    private EditText ed_Other_Charge;
    private EditText ed_Total;
    private TextView ed_Retailer_Photo;
    private TextView ed_Retailer_Signature;
    private EditText ed_Remarks;
    View pg_two, pg_one, pg_three, pg_four;
    ArrayList<String> list_payment_modes = new ArrayList<>();
    private CustomButtonView tv_submit;
    private CustomButtonView tv_cancel;
    private LinearLayout ll_btn;
    int SELECTED_DIST_CUST_ID = 0;
    int SELECTED_DIST_CITY_ID = 0;
    int SELECTED_SALES_PERSON_ID = 0;
    String SELECTED_SALES_PER_CON_NO = "";
    int SELECTED_RETAILER_ID = 0;
    int SELECTED_RETAILER_CITY = 0;
    int SELECTED_RETAILER_STATE = 0;
    //    int SELECTED_CITY_ID = 0;
//    int SELECTED_STATE_ID = 0;
    int SELECTED_DIS_FIRDGE_TYPE = 0;
    int SELECTED_ITEM_ID = 0;
    int SELECTED_FRIDGE_TYPE = 0;
    int SELECTED_PAY_MODE = 0;
    int SELECTED_BANK_ID = 0;
    int SELECTED_OWN_STA_ID = 0;
    int SELECTED_OWN_CIT_ID = 0;
    // String SELECTED_SALES_PER_CON_NO = "";
    String SELECTED_RETAILER_NAME = "";

    //String SELECTED_RET_MOB_NO = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.fridge_request_add);
        initViews();
        ed_Deposite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ed_Total.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculate_total();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        ed_Service_Charge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ed_Total.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ed_Total.setText("");
//                int temp = Integer.parseInt(ed_Deposite.getText().toString().trim()) +
//                        Integer.parseInt(ed_Service_Charge.getText().toString().trim()) +
//                        Integer.parseInt(ed_Other_Charge.getText().toString().trim());
//                ed_Total.setText(temp + "");
                calculate_total();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        ed_Other_Charge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ed_Total.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculate_total();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        ed_Coupon_From_No.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ed_Total_Coupon.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ed_Coupon_To_No.getText().toString().trim().length() > 0 && ed_Coupon_From_No.getText().toString().trim().length() > 0) {


                    if (Integer.parseInt(ed_Coupon_To_No.getText().toString().trim()) > Integer.parseInt(ed_Coupon_From_No.getText().toString().trim())) {
                        int temp = Integer.parseInt(ed_Coupon_To_No.getText().toString().trim()) - Integer.parseInt(ed_Coupon_From_No.getText().toString().trim());
                        ed_Total_Coupon.setText(temp + "");
                    }
                }
            }
        });
        ed_Coupon_To_No.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ed_Total_Coupon.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // if (ed_Coupon_From_No.getText().toString().trim().length() > 0) {
                if (ed_Coupon_To_No.getText().toString().trim().length() > 0 && ed_Coupon_From_No.getText().toString().trim().length() > 0) {

                    if (Integer.parseInt(ed_Coupon_To_No.getText().toString().trim()) > Integer.parseInt(ed_Coupon_From_No.getText().toString().trim())) {
                        int temp = Integer.parseInt(ed_Coupon_To_No.getText().toString().trim()) - Integer.parseInt(ed_Coupon_From_No.getText().toString().trim());
                        ed_Total_Coupon.setText(temp + "");
                    }
                }
            }
        });
        sp_bank_cheque_account.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    SELECTED_BANK_ID = getBankNamesPOJO.RECORDS.get(position - 1).id;
                } else {
                    SELECTED_BANK_ID = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_Retailer_Firm_Name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    try {
                        SELECTED_RETAILER_ID = get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).cus_id;
                        SELECTED_RETAILER_NAME = get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).cus_name;
                        ed_address_1.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).address1);
                        ed_address_2.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).cus_add1);
                        ed_address_3.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).cus_add2);
                        ed_retailer_pincode.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).cus_pincode + "");
                        ed_retailer_city.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).city);
                        SELECTED_RETAILER_CITY = get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).city_id;
                        SELECTED_RETAILER_STATE = get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).state_id;
                        ed_retailer_state.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).state_name);
                        ed_Mobile_No.setText(get_retailer_by_distributer_idPOJO.RECORDS.get(position - 1).cus_mobile_no);
                    } catch (Exception e) {
                        SELECTED_RETAILER_ID = 0;
                        SELECTED_RETAILER_NAME = "";
                        ed_address_1.setText("");
                        ed_address_2.setText("");
                        ed_address_3.setText("");
                        ed_retailer_city.setText("");
                        ed_retailer_state.setText("");
                        ed_Mobile_No.setText("");
                        ed_retailer_pincode.setText("");
                        SELECTED_RETAILER_CITY = 0;
                        SELECTED_RETAILER_STATE = 0;
                    }
                } else {
                    SELECTED_RETAILER_ID = 0;
                    SELECTED_RETAILER_NAME = "";
                    ed_address_1.setText("");
                    ed_address_2.setText("");
                    ed_address_3.setText("");
                    ed_retailer_city.setText("");
                    ed_retailer_state.setText("");
                    ed_Mobile_No.setText("");
                    ed_retailer_pincode.setText("");
                    SELECTED_RETAILER_CITY = 0;
                    SELECTED_RETAILER_STATE = 0;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        list_payment_modes = new ArrayList<>();
        list_payment_modes.add("Select Payment Mode");
        list_payment_modes.add("Cheque");
        list_payment_modes.add("Cash");
        list_payment_modes.add("DD");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                        list_payment_modes);
        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
        sp_payment_mode.setTitle("Select Payment Mode");

        sp_payment_mode.setAdapter(adapter);
        //pp    sp_payment_mode.setSelection(3);
        sp_payment_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    SELECTED_PAY_MODE = position;
                    if (position == 1)//cheque
                    {
                        lin_bank_cheque.setVisibility(View.VISIBLE);
                        lin_bank_dd.setVisibility(View.GONE);
                        //lin.setVisibility(View.VISIBLE);
                    } else if (position == 2)//cash
                    {
                        lin_bank_cheque.setVisibility(View.GONE);
                        lin_bank_dd.setVisibility(View.GONE);
                        SELECTED_BANK_ID = 0;
                        // selected
                        //lin.setVisibility(View.VISIBLE);
                    } else if (position == 3)//dd
                    {
                        lin_bank_cheque.setVisibility(View.GONE);
                        lin_bank_dd.setVisibility(View.VISIBLE);
                        SELECTED_BANK_ID = 0;

                        //lin.setVisibility(View.VISIBLE);
                    }
                } else {//nothing selected
                    SELECTED_PAY_MODE = 0;
                    lin_bank_cheque.setVisibility(View.GONE);
                    lin_bank_dd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SELECTED_PAY_MODE = 0;

            }
        });
        sp_Sales_person.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    // SELECTED_DIS_FIRDGE_TYPE = get_distributor_fridge_typePojo.RECORDS.get(position - 1).id; //30-06-2021 pragna as kartik told to pass edb id
                    SELECTED_SALES_PERSON_ID = getAllEmployeeByDesignationPojo.getRECORDS().get(position-1).getId();
                    SELECTED_SALES_PER_CON_NO = getAllEmployeeByDesignationPojo.getRECORDS().get(position-1).getEmpContactNo()+"";
                } else {
                    SELECTED_SALES_PERSON_ID = 0;
                    SELECTED_SALES_PER_CON_NO ="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_Distributor_Fridge_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    // SELECTED_DIS_FIRDGE_TYPE = get_distributor_fridge_typePojo.RECORDS.get(position - 1).id; //30-06-2021 pragna as kartik told to pass edb id
                    SELECTED_DIS_FIRDGE_TYPE = get_distributor_fridge_typePojo.RECORDS.get(position - 1).ebd_value;
                } else {
                    SELECTED_DIS_FIRDGE_TYPE = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_fridge_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    // SELECTED_DIS_FIRDGE_TYPE = get_distributor_fridge_typePojo.RECORDS.get(position - 1).id; //30-06-2021 pragna as kartik told to pass edb id
                    SELECTED_FRIDGE_TYPE = get_fridge_typePojo.RECORDS.get(position - 1).ebd_value;
                } else {
                    SELECTED_FRIDGE_TYPE = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getSharedPref = new SharedPref(Fridge_Request_Add.this);
        queue = Volley.newRequestQueue(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_act.setText("Fridge Request");
        tv_pg_1.setOnClickListener(this);
        tv_pg_2.setOnClickListener(this);
        tv_pg_3.setOnClickListener(this);
        tv_pg_4.setOnClickListener(this);
        ed_date.setOnClickListener(this);
        ed_Retailer_Photo.setOnClickListener(this);
        ed_Retailer_Signature.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        GetFridge_Request_Master();
        Get_fridge_type();
        Get_Distributor_and_its_Retailer_detail();
        Get_All_employee_By_Designation();
        Get_all_item_list("%");
        Get_City();
        Get_Bank_Names();
        //  Get_State_Country_By_CityId();
        sp_owner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    SELECTED_OWN_CIT_ID = getCity_list_pojo.RECORDS.get(position - 1).id;
                    Get_State_Country_By_CityId(getCity_list_pojo.RECORDS.get(position - 1).id);
                } else {
                    sp_owner_state.setSelection(0);
                    SELECTED_OWN_STA_ID = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //   sp_owner_state.setSelection(0);

            }
        });
        sp_owner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    SELECTED_OWN_STA_ID = getState_list_pojo.RECORDS.get(position - 1).STATE_ID;
                } else {
                    //   sp_owner_state.setSelection(0);
                    SELECTED_OWN_STA_ID = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //   sp_owner_state.setSelection(0);

            }
        });
        sp_item_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    SELECTED_ITEM_ID = get_all_item_list_pojo.RECORDS.get(position - 1).item_id;
                } else {
                    SELECTED_ITEM_ID = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SELECTED_ITEM_ID = 0;

            }
        });

        sp_Distributor_Firm_Name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    //     SELECTED_
                    SELECTED_DIST_CUST_ID = list_distributor_ID.get(position);
                    sp_Distributor_City_Name.setSelection(position);
                    ed_address_1.setText("");
                    ed_address_2.setText("");
                    ed_address_3.setText("");
                    ed_retailer_city.setText("");
                    ed_retailer_state.setText("");
                    sp_Retailer_Firm_Name.setSelection(0);
                    Get_retailer_by_distributer_id(list_distributor_ID.get(position));
                } else {
                    SELECTED_DIST_CUST_ID = 0;
                    sp_Distributor_City_Name.setSelection(0);
                    // sp_dis
                    ed_address_1.setText("");
                    ed_address_2.setText("");
                    ed_address_3.setText("");
                    ed_retailer_city.setText("");
                    ed_retailer_state.setText("");
                    sp_Retailer_Firm_Name.setSelection(0);
                    //  Get_retailer_by_distributer_id(0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sp_Distributor_City_Name.setSelection(0);
            }
        });
        sp_Distributor_City_Name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SELECTED_DIST_CITY_ID = 0;
                if (position > 0) {
                    if (get_distributor_and_its_retailer_detail_pojo != null) {
                        SELECTED_DIST_CITY_ID = get_distributor_and_its_retailer_detail_pojo.getRECORDS().get(position - 1).getCusCitId();
                    }
//                else{
//                    SELECTED_DIST_CITY_ID =0;
//                }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void calculate_total() {
        ed_Total.setText("");
        double deposite = 0.0;
        double service = 0.0;
        double other = 0.0;
        try {
            deposite = Double.parseDouble(ed_Deposite.getText().toString().trim() + "");

        } catch (Exception e) {
            deposite = 0.0;
        }
        try {
            other = Double.parseDouble(ed_Other_Charge.getText().toString().trim() + "");

        } catch (Exception e) {
            other = 0.0;
        }
        try {
            service = Double.parseDouble(ed_Service_Charge.getText().toString().trim() + "");

        } catch (Exception e) {
            service = 0.0;
        }

        double temp = deposite + service + other;
        ed_Total.setText(temp + "");
    }

    private void initViews() {
        iv_back = findViewById(R.id.iv_back);
        pg_one = findViewById(R.id.pg_one);
        pg_three = findViewById(R.id.pg_three);
        ed_retailer_pincode = findViewById(R.id.ed_retailer_pincode);
        pg_two = findViewById(R.id.pg_two);
        pg_four = findViewById(R.id.pg_four);
        txt_act = findViewById(R.id.txt_act);
        iv_info = findViewById(R.id.iv_info);
        iv_profile = findViewById(R.id.iv_profile);
        iv_add = findViewById(R.id.iv_add);
        rel = findViewById(R.id.rel);
        toolbar_act = findViewById(R.id.toolbar_act);
        toolbarContainer = findViewById(R.id.toolbarContainer);
        tv_pg_1 = findViewById(R.id.tv_pg_1);
        tv_pg_2 = findViewById(R.id.tv_pg_2);
        tv_pg_3 = findViewById(R.id.tv_pg_3);
        tv_pg_4 = findViewById(R.id.tv_pg_4);
        ll_btn_header = findViewById(R.id.ll_btn_header);
        ed_ref_no = findViewById(R.id.ed_ref_no);
        ed_item_qty = findViewById(R.id.ed_item_qty);
        ed_date = findViewById(R.id.ed_date);
        ed_approx_sale = findViewById(R.id.ed_approx_sale);
        sp_Distributor_Firm_Name = findViewById(R.id.sp_Distributor_Firm_Name);
        sp_Distributor_City_Name = findViewById(R.id.sp_Distributor_City_Name);
        sp_Sales_person = findViewById(R.id.sp_Sales_person);
        sp_Retailer_Firm_Name = findViewById(R.id.sp_Retailer_Firm_Name);
        ed_Mobile_No = findViewById(R.id.ed_Mobile_No);
        ed_address_1 = findViewById(R.id.ed_address_1);
        ed_address_2 = findViewById(R.id.ed_address_2);
        ed_address_3 = findViewById(R.id.ed_address_3);
        ed_retailer_city = findViewById(R.id.ed_retailer_city);
        ed_retailer_state = findViewById(R.id.ed_retailer_state);
        ed_owner_Name = findViewById(R.id.ed_owner_Name);
        ed_owner_Mobile_No = findViewById(R.id.ed_owner_Mobile_No);
        ed_owner_address_1 = findViewById(R.id.ed_owner_address_1);
        ed_owner_address_2 = findViewById(R.id.ed_owner_address_2);
        ed_owner_address_3 = findViewById(R.id.ed_owner_address_3);
        //  ed_owner_mobile_no = findViewById(R.id.ed_owner_mobile_no);
        sp_owner_city = findViewById(R.id.sp_owner_city);
        sp_owner_state = findViewById(R.id.sp_owner_state);
        ed_owner_pincode = findViewById(R.id.ed_owner_pincode);
        sp_Distributor_Fridge_Type = findViewById(R.id.sp_Distributor_Fridge_Type);
        sp_item_name = findViewById(R.id.sp_item_name);
        sp_fridge_type = findViewById(R.id.sp_fridge_type);
        ed_Fridge_Company_Name = findViewById(R.id.ed_Fridge_Company_Name);
        ed_Coupon_From_No = findViewById(R.id.ed_Coupon_From_No);
        ed_Coupon_To_No = findViewById(R.id.ed_Coupon_To_No);
        ed_Total_Coupon = findViewById(R.id.ed_Total_Coupon);
        sp_payment_mode = findViewById(R.id.sp_payment_mode);
        ed_bank_dd_no = findViewById(R.id.ed_bank_dd_no);
        ed_bank_DD_Date = findViewById(R.id.ed_bank_DD_Date);
        ed_bank_DD_Date.setOnClickListener(this);
        lin_bank_dd = findViewById(R.id.lin_bank_dd);
        sp_bank_cheque_account = findViewById(R.id.sp_bank_cheque_account);
        ed_bank_Cheque_No = findViewById(R.id.ed_bank_Cheque_No);
        ed_bank_Cheque_Date = findViewById(R.id.ed_bank_Cheque_Date);
        ed_bank_Cheque_Date.setOnClickListener(this);
        lin_bank_cheque = findViewById(R.id.lin_bank_cheque);
        ed_Deposite = findViewById(R.id.ed_Deposite);
        ed_Service_Charge = findViewById(R.id.ed_Service_Charge);
        ed_Other_Charge = findViewById(R.id.ed_Other_Charge);
        ed_Total = findViewById(R.id.ed_Total);
        ed_Retailer_Photo = findViewById(R.id.ed_Retailer_Photo);
        ed_Retailer_Signature = findViewById(R.id.ed_Retailer_Signature);
        ed_Remarks = findViewById(R.id.ed_Remarks);
        tv_submit = findViewById(R.id.tv_submit);
        tv_cancel = findViewById(R.id.tv_cancel);
        ll_btn = findViewById(R.id.ll_btn);
    }

    File SELECTED_FILE_RETAILER_PHOTO = null;
    File SELECTED_FILE_RETAILER_SIGNATURE_PHOTO = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            if (files != null) {
                for (int i = 0; i < files.size(); i++) {
                    System.out.println("file name " + files.get(i).getName() + "");
                    System.out.println("file path " + files.get(i).getUri() + "");
                    System.out.println("file path " + files.get(i).getPath() + "");

                    SELECTED_FILE_RETAILER_PHOTO = new File(files.get(i).getPath() + "");
                    ed_Retailer_Photo.setText(SELECTED_FILE_RETAILER_PHOTO.getName() + "");
                    RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), SELECTED_FILE_RETAILER_PHOTO);
//
//            //fileToUpload = MultipartBody.Part.createFormData("file[0]", SELECTED_FILE.getName(), mFile);
                    retailer_photo_fileToUpload = MultipartBody.Part.createFormData("file", SELECTED_FILE_RETAILER_PHOTO.getName(), mFile);
                }
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            if (files != null) {
                for (int i = 0; i < files.size(); i++) {
                    System.out.println("file name " + files.get(i).getName() + "");
                    System.out.println("file path " + files.get(i).getUri() + "");
                    System.out.println("file path " + files.get(i).getPath() + "");

                    SELECTED_FILE_RETAILER_SIGNATURE_PHOTO = new File(files.get(i).getPath() + "");
                    ed_Retailer_Signature.setText(SELECTED_FILE_RETAILER_SIGNATURE_PHOTO.getName() + "");
                    RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), SELECTED_FILE_RETAILER_SIGNATURE_PHOTO);
//
//            //fileToUpload = MultipartBody.Part.createFormData("file[0]", SELECTED_FILE.getName(), mFile);
                    retailer_photo_signature_fileToUpload = MultipartBody.Part.createFormData("sign", SELECTED_FILE_RETAILER_SIGNATURE_PHOTO.getName(), mFile);
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

    private MultipartBody.Part retailer_photo_fileToUpload = null;
    private MultipartBody.Part retailer_photo_signature_fileToUpload = null;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                Save_fridge_request();
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.ed_Retailer_Photo:
                file_select(1);
                break;
            case R.id.ed_Retailer_Signature:
                file_select(2);
                break;
            case R.id.ed_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(Fridge_Request_Add.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
            case R.id.ed_bank_Cheque_Date:
                DatePickerDialog datePickerDialog_for_cheque_date = new DatePickerDialog(Fridge_Request_Add.this, date1_cheque_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog_for_cheque_date.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog_for_cheque_date.show();
                break;
            case R.id.ed_bank_DD_Date:
                DatePickerDialog datePickerDialog_for_dd = new DatePickerDialog(Fridge_Request_Add.this, date1_dd_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog_for_dd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog_for_dd.show();
                break;
            case R.id.tv_pg_1:
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_3.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_4.setBackgroundColor(getResources().getColor(R.color.redlight));

                pg_two.setVisibility(View.GONE);
                pg_four.setVisibility(View.GONE);
                pg_three.setVisibility(View.GONE);
                pg_one.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pg_2:
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_3.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_4.setBackgroundColor(getResources().getColor(R.color.redlight));


                pg_one.setVisibility(View.GONE);
                pg_four.setVisibility(View.GONE);
                pg_three.setVisibility(View.GONE);
                pg_two.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pg_3:

                tv_pg_3.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_4.setBackgroundColor(getResources().getColor(R.color.redlight));


                pg_one.setVisibility(View.GONE);
                pg_four.setVisibility(View.GONE);
                pg_three.setVisibility(View.VISIBLE);
                pg_two.setVisibility(View.GONE);
                break;
            case R.id.tv_pg_4:
                tv_pg_4.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_3.setBackgroundColor(getResources().getColor(R.color.redlight));
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.redlight));

                pg_one.setVisibility(View.GONE);
                pg_four.setVisibility(View.VISIBLE);
                pg_three.setVisibility(View.GONE);
                pg_two.setVisibility(View.GONE);
                break;
        }
    }

    private void Save_fridge_request() {
        System.out.println("Save_fridge_request!!!");
        //validation methd for validate fomr
//      if(validate_form()) {
//          save_fridge_request();
//      }
        if (validate_pg_1()) {
            if (validate_pg_2()) {

                if (validate_pg_3()) {
                    if (sp_payment_mode.getSelectedItemPosition() == 1) {//cheque
                        ed_bank_dd_no.setText("");
                        ed_bank_DD_Date.setText("");
                        // selected_ban
                    }
                    if (sp_payment_mode.getSelectedItemPosition() == 2) {//cash
                        ed_bank_dd_no.setText("");
                        ed_bank_DD_Date.setText("");
                        SELECTED_BANK_ID = 0;
                        ed_bank_Cheque_No.setText("");
                        ed_bank_Cheque_Date.setText("");
                    }
                    if (sp_payment_mode.getSelectedItemPosition() == 3) {//DD
                        SELECTED_BANK_ID = 0;
                        ed_bank_Cheque_No.setText("");
                        ed_bank_Cheque_Date.setText("");
                    }

                    System.out.println("validation done");
                    save_fridge_request();
                } else {
                    System.out.println("validation not done");
                }
                // save_fridge_request();
            } else {
                System.out.println("something is not ok in pg 2!!!!!!!");
            }
        } else {
            System.out.println("something is not ok in pg 1!!!!!!!");
        }
    }


    private boolean validate_pg_3() {
        int to_coupon = 0;
        int from_coupon = 0;
        if (ed_Coupon_From_No.getText().toString().trim().length() > 0) {
            from_coupon = Integer.parseInt(ed_Coupon_From_No.getText().toString().trim() + "");
        }
        if (ed_Coupon_To_No.getText().toString().trim().length() > 0) {
            to_coupon = Integer.parseInt(ed_Coupon_To_No.getText().toString().trim() + "");
        }
        boolean flag = true;
//        if (validate_pg_1()) {
//
//        }
        if (SELECTED_ITEM_ID == 0) {
            flag = false;
            tv_pg_3.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Select Item  ");
        } else if (ed_item_qty.getText().toString().trim().length() == 0) {
            flag = false;
            tv_pg_3.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter Item Qty.  ");
        }
        //10-06-21 for coupon form number
        else if (to_coupon < from_coupon) {
            flag = false;
            tv_pg_3.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter Proper from and to coupon  ");
        }
        //bank validation
        else if (sp_payment_mode.getSelectedItemPosition() == 0) {//if no mode selected
            flag = false;
            tv_pg_3.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please select payment mode ");
        } else if (sp_payment_mode.getSelectedItemPosition() == 1) {//cheque
            if (sp_bank_cheque_account.getSelectedItemPosition() == 0) {
                // if (SELECTED_BANK_ID == 0) {
                flag = false;
                tv_pg_3.performClick();

                DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please select bank account ");
            } else if (ed_bank_Cheque_No.getText().toString().trim().length() <= 0) {
                flag = false;
                tv_pg_3.performClick();

                DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter cheque no. ");
            } else if (ed_bank_Cheque_Date.getText().toString().trim().length() <= 0) {
                flag = false;
                tv_pg_3.performClick();

                DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter cheque date ");
            }
        } else if (sp_payment_mode.getSelectedItemPosition() == 3) {//DD
            //ed_ref_no.getText().toString().trim().length() <= 0
            if (ed_bank_dd_no.getText().toString().trim().length() <= 0) {
                flag = false;
                tv_pg_3.performClick();
                DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter DD no. ");
            } else if (ed_bank_DD_Date.getText().toString().trim().length() == 0) {
                flag = false;
                tv_pg_3.performClick();
                System.out.println("dd no" + ed_bank_dd_no.getText() + "");
                DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter DD date ");
            }
        }//bank validation over
        return flag;
    }

    private boolean validate_pg_2() {

        boolean flag = true;
        if (ed_owner_Name.getText().toString().trim().length() <= 0) {
            flag = false;
            tv_pg_2.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter Owner Name ");
        } else if (ed_owner_address_1.getText().toString().trim().length() <= 0) {
            flag = false;
            tv_pg_2.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter Address-1 ");
        } else if (SELECTED_OWN_CIT_ID <= 0) {
            flag = false;
            tv_pg_2.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Select City ");
        } else if (SELECTED_OWN_STA_ID <= 0) {
            flag = false;
            tv_pg_2.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Select State ");
        } else if (ed_owner_pincode.getText().toString().trim().length() <= 0) {
            flag = false;
            tv_pg_2.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Enter Pin Code ");
        }
        return flag;
    }

    private boolean validate_pg_1() {
        boolean flag = true;
        if (ed_ref_no.getText().toString().trim().length() <= 0) {
            flag = false;
            tv_pg_1.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter Ref. No. ");
        } else if (ed_date.getText().toString().trim().length() <= 0) {
            flag = false;
            tv_pg_1.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Enter Date ");
        } else if (SELECTED_DIST_CUST_ID == 0) {
            flag = false;
            tv_pg_1.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Select Distributor Firm Name ");
        } else if (SELECTED_DIST_CITY_ID == 0) {//SELECTED_DIST_CITY_ID
            flag = false;
            tv_pg_1.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Select Distributor Firm City ");
        } else if (SELECTED_RETAILER_ID == 0) {
            flag = false;
            tv_pg_1.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Select Retailer Firm Name ");
        } else if (ed_address_1.getText().toString().trim().length() <= 0) {
            flag = false;
            tv_pg_1.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter Address-1 ");
        } else if (SELECTED_RETAILER_CITY == 0) {
            flag = false;
            tv_pg_1.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Select City ");
        } else if (SELECTED_RETAILER_STATE == 0) {
            flag = false;
            tv_pg_1.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please Select State ");
        } else if (ed_retailer_pincode.getText().toString().trim().length() <= 0) {
            flag = false;
            tv_pg_1.performClick();
            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter Pin code  ");
        }
//
//        else if (ed_owner_address_1.getText().toString().trim().length() <= 0) {
//            flag = false;
//            tv_pg_1.performClick();
//            DialogUtils.Show_Toast(Fridge_Request_Add.this, "Please enter Address-1 ");
//        }
        return flag;
    }

    SharedPref getSharedPref;
    Get_distributor_fridge_typePojo get_distributor_fridge_typePojo;
    ArrayList<String> list_Distributor_Fridge_Type = new ArrayList<>();
    RequestQueue queue;
    String CURRENT_DATE = "";
    Calendar myCalendar = Calendar.getInstance();
    final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();


        }

    };
    final DatePickerDialog.OnDateSetListener date1_dd_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel_dd_date();


        }

    };
    final DatePickerDialog.OnDateSetListener date1_cheque_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel_cheque_date();


        }

    };

    private void updateLabel_cheque_date() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_bank_Cheque_Date.setText(sdf.format(myCalendar.getTime()));
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time updateLabel_cheque_date=> " + c);
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_date.setText(sdf.format(myCalendar.getTime()));
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);


        //   SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = sdf.format(c);
//        etSaveExpensesAmount.setText("");
//        etSaveExpensesDiscription.setText("");
//        etSaveExpensesKm.setText("");
//        etSaveExpensesSelectDocument.setText("");
//        CURRENT_DATE = formattedDate;
//        System.out.println("current dat ::: " + CURRENT_DATE + "");
//        fetch_expense_names(sdf.format(myCalendar.getTime()) + "");
    }

    private void updateLabel_dd_date() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_bank_DD_Date.setText(sdf.format(myCalendar.getTime()));
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);


        //   SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = sdf.format(c);
//        etSaveExpensesAmount.setText("");
//        etSaveExpensesDiscription.setText("");
//        etSaveExpensesKm.setText("");
//        etSaveExpensesSelectDocument.setText("");
//        CURRENT_DATE = formattedDate;
//        System.out.println("current dat ::: " + CURRENT_DATE + "");
//        fetch_expense_names(sdf.format(myCalendar.getTime()) + "");
    }

    private void GetFridge_Request_Master() {
        //  DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_distributor_fridge_type + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";// + "&status=" + status;
        list_Distributor_Fridge_Type = new ArrayList<>();
        list_Distributor_Fridge_Type.add("Distributor Fridge Type");

        url = url.replace(" ", "%20");
        System.out.println("Get_distributor_fridge_type URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                // DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                // getFridge_request_masterPojo = new GetFridge_Request_MasterPojo();
                get_distributor_fridge_typePojo = gson.fromJson(response, Get_distributor_fridge_typePojo.class);
                if (get_distributor_fridge_typePojo != null &&
                        get_distributor_fridge_typePojo.RECORDS != null) {
                    if (get_distributor_fridge_typePojo.RECORDS.size() > 0) {
                        for (int i = 0; i < get_distributor_fridge_typePojo.RECORDS.size(); i++) {
                            if (get_distributor_fridge_typePojo.RECORDS.get(i).is_visible_to_user) {
                                list_Distributor_Fridge_Type.add(get_distributor_fridge_typePojo.RECORDS.get(i).ebd_name);
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_Distributor_Fridge_Type);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_Distributor_Fridge_Type.setTitle("Distributor Fridge Type");

                        sp_Distributor_Fridge_Type.setAdapter(adapter);
                    } else {
                        DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    Get_fridge_typePojo get_fridge_typePojo;
    ArrayList<String> list_Fridge_Type = new ArrayList<>();

    private void Get_fridge_type() {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_fridge_type + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";// + "&status=" + status;
        list_Fridge_Type = new ArrayList<>();
        list_Fridge_Type.add("Select Fridge Type");
        url = url.replace(" ", "%20");
        System.out.println("Get_fridge_type URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                //  DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                // getFridge_request_masterPojo = new GetFridge_Request_MasterPojo();
                get_fridge_typePojo = gson.fromJson(response, Get_fridge_typePojo.class);
                if (get_fridge_typePojo != null &&
                        get_fridge_typePojo.RECORDS != null) {
                    if (get_fridge_typePojo.RECORDS.size() > 0) {
                        for (int i = 0; i < get_fridge_typePojo.RECORDS.size(); i++) {
                            if (get_fridge_typePojo.RECORDS.get(i).is_visible_to_user) {
                                list_Fridge_Type.add(get_fridge_typePojo.RECORDS.get(i).ebd_name);
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_Fridge_Type);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_fridge_type.setTitle("Select Fridge Type");

                        sp_fridge_type.setAdapter(adapter);
                    } else {
                        DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    Get_retailer_by_distributer_idPOJO get_retailer_by_distributer_idPOJO;
    ArrayList<String> list_retailer = new ArrayList<>();

    private void Get_retailer_by_distributer_id(int distributer_id) {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_retailer_by_distributer_id + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&distributer_id=" + distributer_id + "&branch_id=" + getSharedPref.getBranchId() + "";// + "&status=" + status;
        // list_Fridge_Type = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_retailer_by_distributer_id URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                //  DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                list_retailer = new ArrayList<>();
                list_retailer.add("Select Retailer Firm Name");
                // get_retailer_by_distributer_idPOJO = new Get_retailer_by_distributer_idPOJO();
                get_retailer_by_distributer_idPOJO = gson.fromJson(response, Get_retailer_by_distributer_idPOJO.class);
                if (get_retailer_by_distributer_idPOJO != null &&
                        get_retailer_by_distributer_idPOJO.RECORDS != null) {
                    if (get_retailer_by_distributer_idPOJO.RECORDS.size() > 0) {
                        for (int i = 0; i < get_retailer_by_distributer_idPOJO.RECORDS.size(); i++) {
                            // if (get_fridge_typePojo.RECORDS.get(i).is_visible_to_user) {
                            list_retailer.add(get_retailer_by_distributer_idPOJO.RECORDS.get(i).cus_name);
                            //  }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_retailer);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_Retailer_Firm_Name.setTitle("Select Retailer Firm Name");

                        sp_Retailer_Firm_Name.setAdapter(adapter);

                    } else {
                        DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    ArrayList<String> list_items = new ArrayList<>();
    Get_all_item_list_POJO get_all_item_list_pojo;

    private void Get_all_item_list(String prefixText) {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_all_item_list + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&prefixText=" + prefixText + "&branch_id=" + getSharedPref.getBranchId() + "";// + "&status=" + status;
        list_items = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_all_item_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                //  DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                list_items = new ArrayList<>();
                list_items.add("Select Item");
                // get_all_item_list_pojo = new Get_all_item_list_POJO();
                get_all_item_list_pojo = gson.fromJson(response, Get_all_item_list_POJO.class);
                if (get_all_item_list_pojo != null &&
                        get_all_item_list_pojo.RECORDS != null) {
                    if (get_all_item_list_pojo.RECORDS.size() > 0) {
                        for (int i = 0; i < get_all_item_list_pojo.RECORDS.size(); i++) {
                            // if (get_fridge_typePojo.RECORDS.get(i).is_visible_to_user) {
                            list_items.add(get_all_item_list_pojo.RECORDS.get(i).item_name + "");
                            //  }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_items);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_item_name.setTitle("Select Item");

                        sp_item_name.setAdapter(adapter);
                        //pppp  sp_item_name.setSelection(1);
                    } else {
                        DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    private ArrayList<String> list_distributor_name;
    private ArrayList<Integer> list_distributor_ID;
    private ArrayList<String> list_distributor_city_name;
    private ArrayList<String> customerIdArrayList = new ArrayList<>();
    private Get_Distributor_and_its_Retailer_detail_Pojo get_distributor_and_its_retailer_detail_pojo;

    private void Get_Distributor_and_its_Retailer_detail() {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_Distributor_and_its_Retailer_detail + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";// + "&status=" + status;
        // list_Fridge_Type = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_Distributor_and_its_Retailer_detail URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                //  DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                list_distributor_name = new ArrayList<>();
                list_distributor_city_name = new ArrayList<>();
                list_distributor_ID = new ArrayList<>();
                list_distributor_name.add("Select Distributor Firm name");
                list_distributor_ID.add(0);
                list_distributor_city_name.add("Select Distributor City name");
                // getFridge_request_masterPojo = new GetFridge_Request_MasterPojo();
                get_distributor_and_its_retailer_detail_pojo = gson.fromJson(response, Get_Distributor_and_its_Retailer_detail_Pojo.class);
                if (get_distributor_and_its_retailer_detail_pojo != null &&
                        get_distributor_and_its_retailer_detail_pojo.getRECORDS() != null) {
                    if (get_distributor_and_its_retailer_detail_pojo.getRECORDS().size() > 0) {
                        for (int i = 0; i < get_distributor_and_its_retailer_detail_pojo.getRECORDS().size(); i++) {
                            // if (get_distributor_and_its_retailer_detail_pojo.getRECORDS().get(i)) {
                            list_distributor_name.add(get_distributor_and_its_retailer_detail_pojo.getRECORDS().get(i).getCusName() + "");
                            list_distributor_ID.add(get_distributor_and_its_retailer_detail_pojo.getRECORDS().get(i).getId());
                            list_distributor_city_name.add(get_distributor_and_its_retailer_detail_pojo.getRECORDS().get(i).getCityName() + "");
                            //}
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_distributor_name);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_Distributor_Firm_Name.setTitle("Select Distributor Firm name");
                        sp_Distributor_Firm_Name.setAdapter(adapter);
                        //pppppppppppp    sp_Distributor_Firm_Name.setSelection(1);//comment this line
                        ArrayAdapter<String> adapter_city = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_distributor_city_name);
                        adapter_city.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_Distributor_City_Name.setTitle("Select City");
                        sp_Distributor_City_Name.setEnabled(false);
                        sp_Distributor_City_Name.setAdapter(adapter_city);
                    } else {
                        DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);

                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    GetAllEmployeeByDesignationPojo getAllEmployeeByDesignationPojo = new GetAllEmployeeByDesignationPojo();
    ArrayList<String> list_sales_persons = new ArrayList<>();

    private void Get_All_employee_By_Designation() {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_All_employee_By_Designation + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&des_key=" + "sales_person";// + "&status=" + status;
        //  list_Fridge_Type = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_All_employee_By_Designation URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                //  DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                list_sales_persons = new ArrayList<>();
                list_sales_persons.add("Select Sales Person");
                // getFridge_request_masterPojo = new GetFridge_Request_MasterPojo();
                getAllEmployeeByDesignationPojo = gson.fromJson(response, GetAllEmployeeByDesignationPojo.class);
                if (getAllEmployeeByDesignationPojo != null &&
                        getAllEmployeeByDesignationPojo.getRECORDS() != null) {
                    if (getAllEmployeeByDesignationPojo.getRECORDS().size() > 0) {
                        for (int i = 0; i < getAllEmployeeByDesignationPojo.getRECORDS().size(); i++) {
                            // if (getAllEmployeeByDesignationPojo.getRECORDS().get(i).is_visible_to_user) {
                          //  list_sales_persons.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getEmpFirstName() + "");
                            list_sales_persons.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getEmpUserName() + "");
                            // }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_sales_persons);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_Sales_person.setTitle("Select Sales Person");
                        sp_Sales_person.setAdapter(adapter);
                    } else {
                        DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    ArrayList<String> list_all_city = new ArrayList<>();
    ArrayList<String> list_state = new ArrayList<>();
    GetCity_List_POJO getCity_list_pojo;

    private void Get_City() {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_City + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";//; "&des_key=" + "sales_person";// + "&status=" + status;
        //  list_Fridge_Type = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_City URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                //  DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                list_all_city = new ArrayList<>();
                list_all_city.add("Select City");
                // getCity_list_pojo = new GetFridge_Request_MasterPojo();
                getCity_list_pojo = gson.fromJson(response, GetCity_List_POJO.class);
                if (getCity_list_pojo != null &&
                        getCity_list_pojo.RECORDS != null) {
                    if (getCity_list_pojo.RECORDS.size() > 0) {
                        for (int i = 0; i < getCity_list_pojo.RECORDS.size(); i++) {
                            // if (getAllEmployeeByDesignationPojo.getRECORDS().get(i).is_visible_to_user) {
                            list_all_city.add(getCity_list_pojo.RECORDS.get(i).cit_name + "");
                            // }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_all_city);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_owner_city.setTitle("Select City");
                        sp_owner_city.setAdapter(adapter);
                        //ppppp         sp_owner_city.setSelection(1);
                    } else {
                        DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    ArrayList<String> list_bank_names = new ArrayList<>();
    GetBankNamesPOJO getBankNamesPOJO;

    private void Get_Bank_Names() {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_Bank_Names + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";//; "&des_key=" + "sales_person";// + "&status=" + status;
        //  list_Fridge_Type = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_Bank_Names URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                //  DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                list_bank_names = new ArrayList<>();
                list_bank_names.add("Select Bank");
                // getCity_list_pojo = new GetFridge_Request_MasterPojo();
                getBankNamesPOJO = gson.fromJson(response, GetBankNamesPOJO.class);
                if (getBankNamesPOJO != null &&
                        getBankNamesPOJO.RECORDS != null) {
                    if (getBankNamesPOJO.RECORDS.size() > 0) {
                        for (int i = 0; i < getBankNamesPOJO.RECORDS.size(); i++) {
                            // if (getAllEmployeeByDesignationPojo.getRECORDS().get(i).is_visible_to_user) {
                            list_bank_names.add(getBankNamesPOJO.RECORDS.get(i).pbm_name + "");
                            // }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_bank_names);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_bank_cheque_account.setTitle("Select Bank");
                        sp_bank_cheque_account.setAdapter(adapter);
                        //    sp_bank_cheque_account.setSelection(1);
                    } else {
                        DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    GetState_List_POJO getState_list_pojo;

    private void Get_State_Country_By_CityId(int cityID) {
        // DialogUtils.showProgressDialog(Fridge_Request_Add.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_State_Country_By_CityId + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&city_id=" + cityID + "";//; "&des_key=" + "sales_person";// + "&status=" + status;
        //  list_Fridge_Type = new ArrayList<>();

        url = url.replace(" ", "%20");
        System.out.println("Get_State_Country_By_CityId URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                //  DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                list_state = new ArrayList<>();
                list_state.add("Select State");
                // getFridge_request_masterPojo = new GetFridge_Request_MasterPojo();
                getState_list_pojo = gson.fromJson(response, GetState_List_POJO.class);
                if (getState_list_pojo != null &&
                        getState_list_pojo.RECORDS != null) {
                    if (getState_list_pojo.RECORDS.size() > 0) {
                        for (int i = 0; i < getState_list_pojo.RECORDS.size(); i++) {
                            // if (getAllEmployeeByDesignationPojo.getRECORDS().get(i).is_visible_to_user) {
                            list_state.add(getState_list_pojo.RECORDS.get(i).STATE_NAME + "");
                            // }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                                        list_state);
                        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
                        sp_owner_state.setTitle("Select State");
                        sp_owner_state.setAdapter(adapter);
                    } else {
                        DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.no_data_available));
                        //  lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Request_Add.this, getResources().getString(R.string.something_went_wrong));
                    //  lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }

    private void save_fridge_request() {
        String app_version = String.valueOf(getSharedPref.getAppVersionCode());
        String android_id = getSharedPref.getAppAndroidId();
        String device_id = String.valueOf(getSharedPref.getRegisteredId());
        String user_id = getSharedPref.getRegisteredUserId();
        String key = Config.ACCESS_KEY;
        String comp_id = getSharedPref.getCompanyId() + "";
        //RequestBody app_version_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(app_version));
        RequestBody app_version_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(app_version));
        RequestBody android_id_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(android_id));
        RequestBody device_id_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(device_id));
        RequestBody user_id_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(user_id));
        RequestBody key_req = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(key));
        RequestBody comp_id_req = RequestBody.create(MediaType.parse("text/plain"), comp_id + "");
        RequestBody ref_no = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_ref_no.getText().toString().trim() + ""));
        System.out.println("ref no@@ : " + ed_ref_no.getText().toString().trim() + "");
        RequestBody sr_no = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("0"));
        double sales = 0;
        if (ed_approx_sale.getText().toString().trim().length() > 0) {
            sales = Double.parseDouble(ed_approx_sale.getText().toString());
        }
        RequestBody apprpox_sales = RequestBody.create(MediaType.parse("text/plain"), sales + "");
        String dt = ed_date.getText().toString();
        System.out.println("datae@@@  " + dt + "");
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(dt));

        RequestBody dist_cust_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_DIST_CUST_ID + "");
        RequestBody dist_city_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_DIST_CITY_ID + "");
        RequestBody sales_person_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_SALES_PERSON_ID + "");
        RequestBody sales_per_con_no = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_SALES_PER_CON_NO + ""));
        RequestBody retailer_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_RETAILER_ID + "");
        RequestBody retailer_name = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_RETAILER_NAME));
        RequestBody ret_mob_no = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_Mobile_No.getText().toString() + ""));
        System.out.println("ret_mob_no no @@@@: " + ed_Mobile_No.getText().toString().trim() + "");
        RequestBody add1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_address_1.getText().toString().trim() + ""));
        System.out.println("ret_mob_no no @@@@: " + ed_address_1.getText().toString().trim() + "");
        System.out.println("ed_address_2 no @@@@: " + ed_address_2.getText().toString().trim() + "");
        System.out.println("ed_address_3 no @@@@: " + ed_address_3.getText().toString().trim() + "");
        RequestBody add2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_address_2.getText().toString().trim() + ""));
        RequestBody add3 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_address_3.getText().toString().trim() + ""));
        //RequestBody city_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_RETAILER_CITY));
        RequestBody sta_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_RETAILER_STATE + "");
        RequestBody city_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_RETAILER_CITY + "");

        RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_retailer_pincode.getText().toString().trim() + ""));
        System.out.println("pincode no @@@@: " + ed_retailer_pincode.getText().toString().trim() + "");
        //==page 2
        RequestBody owner_name = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_owner_Name.getText().toString().trim() + ""));
        System.out.println("owner_name no @@@@: " + ed_owner_Name.getText().toString().trim() + "");

        RequestBody owner_mob_no = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_owner_Mobile_No.getText().toString().trim() + ""));
        System.out.println("ed_owner_Mobile_No no @@@@: " + ed_owner_Mobile_No.getText().toString().trim() + "");

        RequestBody own_add1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_owner_address_1.getText().toString().trim() + ""));
        System.out.println("ed_owner_address_1 no @@@@: " + ed_owner_address_1.getText().toString().trim() + "");
        System.out.println("ed_owner_address_2 no @@@@: " + ed_owner_address_2.getText().toString().trim() + "");
        System.out.println("ed_owner_address_3 no @@@@: " + ed_owner_address_3.getText().toString().trim() + "");

        RequestBody own_add2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_owner_address_2.getText().toString().trim() + ""));
        RequestBody own_add3 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_owner_address_3.getText().toString().trim() + ""));
        //  RequestBody own_cit_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_OWN_CIT_ID));
        //  RequestBody own_sta_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_OWN_STA_ID));
        RequestBody own_sta_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_OWN_STA_ID + "");
        RequestBody own_cit_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_OWN_CIT_ID + "");

        RequestBody own_pincode = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_owner_pincode.getText().toString().trim() + ""));
        System.out.println("ed_owner_pincode no @@@@: " + ed_owner_pincode.getText().toString().trim() + "");

//        RequestBody dis_firdge_type = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(app_version));
        RequestBody dis_firdge_type = RequestBody.create(MediaType.parse("text/plain"), SELECTED_DIS_FIRDGE_TYPE + "");

        System.out.println("ed_Coupon_From_No no @@@@: " + ed_Coupon_From_No.getText().toString().trim() + "");
        System.out.println("ed_Coupon_To_No no @@@@: " + ed_Coupon_To_No.getText().toString().trim() + "");
        System.out.println("ed_Total_Coupon no @@@@: " + ed_Total_Coupon.getText().toString().trim() + "");

        int to_no = 0;
        if (ed_Coupon_To_No.getText().toString().trim().length() > 0) {
            to_no = Integer.parseInt(ed_Coupon_To_No.getText().toString());
        }
        int from_no = 0;
        if (ed_Coupon_From_No.getText().toString().trim().length() > 0) {
            from_no = Integer.parseInt(ed_Coupon_From_No.getText().toString());
        }
        int from_to_no_tot = 0;
        if (ed_Total_Coupon.getText().toString().trim().length() > 0) {
            from_to_no_tot = Integer.parseInt(ed_Total_Coupon.getText().toString());
        }

        RequestBody coupn_from_no = RequestBody.create(MediaType.parse("text/plain"), from_no + "");
        RequestBody coupn_to_no = RequestBody.create(MediaType.parse("text/plain"), to_no + "");

        RequestBody coupn_total = RequestBody.create(MediaType.parse("text/plain"), from_to_no_tot + "");
//        RequestBody item_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_ITEM_ID));
        RequestBody item_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_ITEM_ID + "");

        double qty = 0;
        if (ed_item_qty.getText().toString().trim().length() > 0) {
            qty = Double.parseDouble(ed_item_qty.getText().toString());
        }
        RequestBody itm_qty = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(qty + ""));
        System.out.println("ed_item_qty no @@@@: " + ed_item_qty.getText().toString().trim() + "");

//        RequestBody fridge_type = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_FRIDGE_TYPE));
        RequestBody fridge_type = RequestBody.create(MediaType.parse("text/plain"), SELECTED_FRIDGE_TYPE + "");
        RequestBody company_name = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_Fridge_Company_Name.getText().toString().trim() + ""));
        System.out.println("ed_Fridge_Company_Name no @@@@: " + ed_Fridge_Company_Name.getText().toString().trim() + "");

//        RequestBody pay_mode = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_PAY_MODE));
//        RequestBody bank_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_BANK_ID ));

        RequestBody pay_mode = RequestBody.create(MediaType.parse("text/plain"), SELECTED_PAY_MODE + "");
        RequestBody bank_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_BANK_ID + "");
        RequestBody cheq_no = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_bank_Cheque_No.getText().toString().trim() + ""));
        System.out.println("ed_bank_Cheque_No no @@@@: " + ed_bank_Cheque_No.getText().toString().trim() + "");

        RequestBody check_dt = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_bank_Cheque_Date.getText().toString().trim() + ""));
        System.out.println("ed_bank_Cheque_Date no @@@@: " + ed_bank_Cheque_Date.getText().toString().trim() + "");

        // RequestBody acc_no = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("test acc_no"));
        RequestBody acc_no = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SELECTED_BANK_ID));
        double dd = 0;
        if (ed_bank_dd_no.getText().toString().trim().length() > 0) {
            dd = Double.parseDouble(ed_bank_dd_no.getText().toString());
        }
        RequestBody dd_no = RequestBody.create(MediaType.parse("text/plain"), dd + "");
        System.out.println("ed_bank_dd_no no @@@@: " + ed_bank_dd_no.getText().toString().trim() + "");

        RequestBody dd_dt = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_bank_DD_Date.getText().toString().trim() + ""));
        System.out.println("ed_bank_DD_Date no @@@@: " + ed_bank_DD_Date.getText().toString().trim() + "");
        double depo = 0.0;
        if (ed_Deposite.getText().toString().trim().length() > 0) {
            depo = Double.parseDouble(ed_Deposite.getText().toString());
        }
        double other = 0;
        if (ed_Other_Charge.getText().toString().trim().length() > 0) {
            other = Double.parseDouble(ed_Other_Charge.getText().toString());
        }
        double service = 0;
        if (ed_Service_Charge.getText().toString().trim().length() > 0) {
            service = Double.parseDouble(ed_Service_Charge.getText().toString());
        }
        double tot = 0.0;
        if (ed_Total.getText().toString().trim().length() > 0) {
            tot = Double.parseDouble(ed_Total.getText().toString());
        }

        System.out.println("depo@@@@ " + depo);
        System.out.println("other@@@@ " + other);
        System.out.println("service@@@@ " + service);
        System.out.println("depo@@@@ " + depo);
        System.out.println("tot@@@@ " + tot);
//        RequestBody deposite = RequestBody.create(MediaType.parse("text/plain"), ed_Deposite.getText() + "");
//        RequestBody other_chrg = RequestBody.create(MediaType.parse("text/plain"), ed_Other_Charge.getText() + "");
//        RequestBody service_chrg = RequestBody.create(MediaType.parse("text/plain"), ed_Service_Charge.getText() + "");
//        RequestBody total = RequestBody.create(MediaType.parse("text/plain"), ed_Total.getText() + "");


        RequestBody deposite = RequestBody.create(MediaType.parse("text/plain"), depo + "");
        RequestBody other_chrg = RequestBody.create(MediaType.parse("text/plain"), other + "");
        RequestBody service_chrg = RequestBody.create(MediaType.parse("text/plain"), service + "");
        RequestBody total = RequestBody.create(MediaType.parse("text/plain"), tot + "");



        RequestBody remarks = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ed_Remarks.getText().toString().trim() + ""));


        System.out.println("ed_Deposite no @@@@: " + ed_Deposite.getText().toString().trim() + "");
        System.out.println("ed_Other_Charge no @@@@: " + ed_Other_Charge.getText().toString().trim() + "");
        System.out.println("ed_Service_Charge no @@@@: " + ed_Service_Charge.getText().toString().trim() + "");
        System.out.println("ed_Total no @@@@: " + ed_Total.getText().toString().trim() + "");
        System.out.println("ed_Remarks no @@@@: " + ed_Remarks.getText().toString().trim() + "");
//
//        FridgeSAveImplementer.Save_fridge_requestApiImplementer(app_version_req, android_id_req, device_id_req, user_id_req, key_req, comp_id_req, ref_no, sr_no, apprpox_sales, date, dist_cust_id, dist_city_id, sales_person_id, sales_per_con_no, retailer_id, retailer_name, ret_mob_no, add1, add2, add3, city_id, sta_id, pincode, owner_name, owner_mob_no, own_add1, own_add2, own_add3, own_cit_id, own_sta_id, own_pincode, owner_name, dis_firdge_type, coupn_from_no, coupn_to_no, coupn_total, item_id, itm_qty, fridge_type, company_name, pay_mode, bank_id, cheq_no, check_dt, acc_no, dd_no, dd_dt, deposite, other_chrg, service_chrg, total, remarks, retailer_photo_fileToUpload, retailer_photo_signature_fileToUpload

        FridgeSAveImplementer.Save_fridge_requestApiImplementer(app_version_req, android_id_req, device_id_req, user_id_req, key_req, comp_id_req, ref_no, sr_no, apprpox_sales, date, dist_cust_id, dist_city_id, sales_person_id, sales_per_con_no, retailer_id, retailer_name, ret_mob_no, add1, add2, add3, city_id, sta_id, pincode, owner_name, owner_mob_no, own_add1, own_add2, own_add3, own_cit_id, own_sta_id, own_pincode, dis_firdge_type, coupn_from_no, coupn_to_no, coupn_total, item_id, itm_qty, fridge_type, company_name, pay_mode, bank_id, cheq_no, check_dt, acc_no, dd_no, dd_dt, deposite, other_chrg, service_chrg, total, remarks, retailer_photo_fileToUpload, retailer_photo_signature_fileToUpload, new Callback<Save_Fridge_POJO>() {
            @Override
            public void onResponse(Call<Save_Fridge_POJO> call, retrofit2.Response<Save_Fridge_POJO> response) {
                DialogUtils.hideProgressDialog();
                try {
                    if (response.isSuccessful() && response.body() != null && response.body().FLAG == 1) {
                        Toast.makeText(Fridge_Request_Add.this, "" + response.body().MESSAGE, Toast.LENGTH_SHORT).show();
                         finish();
                    } else {

                        Toast.makeText(Fridge_Request_Add.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                        System.out.println("response!!!!!!! " + response.message() + "");
                        System.out.println("response!!!!!!! " + response.code() + "");
                        System.out.println("response!!!!!!! " + response.raw() + "");
                        System.out.println("response!!!!!!! " + response.toString() + "");
                        System.out.println("response!!!!!!! " + response.errorBody() + "");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Save_Fridge_POJO> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                Toast.makeText(Fridge_Request_Add.this, "Request Failed:- " + t.getCause().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
