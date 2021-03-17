package com.infinity.infoway.vimal.delear.activity.PromotionalRequirement;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.City_State_Taluka_CountryPojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.Complaint.Insert_RoutWise_Complaint_Pojo;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoitonalRequirementActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * initial items
     **/

    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;

    /**
     * initial items
     **/

    private TextInputLayout tv_input_request_by;
    private TextInputLayout tv_input_ship_to;
    private TextInputLayout tv_input_address1_promoitonal, tv_input_address2_promoitonal, tv_input_address3_promoitonal;
    private TextInputLayout tv_input_state;
    private TextInputLayout tv_input_city;
    private TextInputLayout tv_input_area;
    private TextInputLayout tv_input_pinCode;
    private TextInputLayout tv_input_city_from_retailer;


    private EditText et_request_by;
    private EditText et_remark;
    private EditText et_ship_to;
    private EditText et_address1_promoitonal;
    private EditText et_address2_promoitonal;
    private EditText et_address3_promoitonal;
    private EditText et_state;
    private EditText et_city;
    private EditText et_area;
    private EditText et_pincode;
    private EditText et_uom_with_stock;
    private EditText et_qty;
    private EditText et_with_stock_remarks;
    private EditText et_without_uom;
    private EditText et_without_qty;
    private EditText et_without_stock_remarks;
    private EditText et_city_from_retailer;
    private SearchableSpinner et_select_item, et_select_city, et_select_area;

    private View last_divider_city;
    private TextView btnAddWithStock, btnAddWithoutStock;
    private Button btn_submit_prmotional_req;


    SearchableSpinner spSearchCity, spSelectWithItemDetails, et_select_without_item;
    ConstraintLayout main_prom_req;
    static TextView tv_no_item_selected, tv_no_item_selected2;
    String title_screen = "";

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoitonal_requirement);

        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        Get_All_City_Taluka_District();
    }


    String SELECTED_WITH_STOCK_ITEM = "", SELECTED_WITH_STOCK_UOM = "";
    String SELECTED_WITH_OUT_STOCK_ITEM = "", SELECTED_WITH_OUT_STOCK_UOM = "";

    String STATE_NAME = "";

    private void initView() {

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(this);
        /**Textinput Layout**/
        tv_input_request_by = (TextInputLayout) findViewById(R.id.tv_input_request_by);
        queue = Volley.newRequestQueue(this);
        tv_input_ship_to = findViewById(R.id.tv_input_ship_to);
        tv_input_address1_promoitonal = findViewById(R.id.tv_input_address1_promoitonal);
        tv_input_address2_promoitonal = findViewById(R.id.tv_input_address2_promoitonal);
        tv_input_address3_promoitonal = findViewById(R.id.tv_input_address3_promoitonal);
        tv_input_state = findViewById(R.id.tv_input_state);
        tv_input_city = findViewById(R.id.tv_input_city);
        tv_input_area = findViewById(R.id.tv_input_area);
        tv_input_pinCode = findViewById(R.id.tv_input_pinCode);
        tv_input_city_from_retailer = findViewById(R.id.tv_input_city_from_retailer);
        tv_input_city_from_retailer.setOnClickListener(this);
        // main_feed_back = findViewById(R.id.main_feed_back);
        /**Editext**/
        et_request_by = (EditText) findViewById(R.id.et_request_by);
        if (getSharedPref.getUserName() != null) {
            et_request_by.setText(getSharedPref.getUserName() + "");
        }

        main_prom_req = findViewById(R.id.main_prom_req);
        et_ship_to = (EditText) findViewById(R.id.et_ship_to);
        et_ship_to.setOnClickListener(this);
        et_address1_promoitonal = (EditText) findViewById(R.id.et_address1_promoitonal);
        et_address2_promoitonal = (EditText) findViewById(R.id.et_address2_promoitonal);
        et_address3_promoitonal = (EditText) findViewById(R.id.et_address3_promoitonal);
        et_remark = (EditText) findViewById(R.id.et_remark);
        et_area = (EditText) findViewById(R.id.et_area);
        et_city_from_retailer = (EditText) findViewById(R.id.et_city_from_retailer);
        // et_village = (EditText) findViewById(R.id.et_village);
        et_select_city = findViewById(R.id.et_select_city);

        final LayoutInflater factory = getLayoutInflater();

        final View textEntryView = factory.inflate(R.layout.searchable_spinner_text_view, null);
        last_divider_city = findViewById(R.id.last_divider_city);
        tv_city = (TextView) textEntryView.findViewById(R.id.tv_city);
        btnAddWithStock = (TextView) findViewById(R.id.btnAddWithStock);
        btnAddWithStock.setOnClickListener(this);
        et_pincode = (EditText) findViewById(R.id.et_pincode);
        et_state = (EditText) findViewById(R.id.et_state);
        tv_no_item_selected = findViewById(R.id.tv_no_item_selected);
        et_uom_with_stock = (EditText) findViewById(R.id.et_uom);
        et_without_uom = (EditText) findViewById(R.id.et_without_uom);
        et_without_qty = (EditText) findViewById(R.id.et_without_qty);
        et_without_stock_remarks = (EditText) findViewById(R.id.et_without_stock_remarks);
        et_qty = (EditText) findViewById(R.id.et_qty);
        et_with_stock_remarks = (EditText) findViewById(R.id.et_with_stock_remarks);
        get_stockWise_promotional_item();

        et_select_item = findViewById(R.id.et_select_item);

        et_select_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SELECTED_WITH_STOCK_ITEM = with_stock_item_list.get(position);
                SELECTED_WITH_STOCK_UOM = with_stock_uom_list.get(position);
                if (position > 0) {
                    et_uom_with_stock.setText(SELECTED_WITH_STOCK_UOM);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SELECTED_WITH_STOCK_ITEM = "";
                SELECTED_WITH_STOCK_UOM = "";

            }
        });

        et_select_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    SELECTED_CITY_ID = city_list_id.get(position);
                    if (city_state_taluka_countryPojos != null && city_state_taluka_countryPojos.size() > 0) {
                        for (int i = 0; i < city_state_taluka_countryPojos.size(); i++) {
                            if (city_state_taluka_countryPojos.get(i).getCit_id() == Integer.parseInt(SELECTED_CITY_ID)) {
                                et_state.setText(city_state_taluka_countryPojos.get(i).getState_Name());
                                break;

                            }

                        }
                    }


                    get__area(SELECTED_CITY_ID);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SELECTED_CITY_ID = "";

            }
        });

        et_select_without_item = findViewById(R.id.et_select_without_item);
        et_select_without_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SELECTED_WITH_OUT_STOCK_ITEM = without_stock_item_list.get(position);
                SELECTED_WITH_OUT_STOCK_UOM = without_stock_uom_list.get(position);
                if (position > 0) {
                    et_without_uom.setText(SELECTED_WITH_OUT_STOCK_UOM);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SELECTED_WITH_OUT_STOCK_ITEM = "";
                SELECTED_WITH_OUT_STOCK_UOM = "";

            }
        });


        et_select_area = findViewById(R.id.et_select_area);
        et_select_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        /** Button **/
        btn_submit_prmotional_req = (Button) findViewById(R.id.btn_submit_prmotional_req);
        btnAddWithoutStock = (TextView) findViewById(R.id.btnAddWithoutStock);
        btnAddWithoutStock.setOnClickListener(this);
        btn_submit_prmotional_req.setOnClickListener(this);
        rvWithStockList = findViewById(R.id.rvWithStockList);
        tv_no_item_selected2 = findViewById(R.id.tv_no_item_selected2);
        rvWithoutStockList = findViewById(R.id.rvWithoutStockList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        rvWithStockList.setLayoutManager(linearLayoutManager);
        rvWithoutStockList.setLayoutManager(linearLayoutManager2);


    }


    private String SELECTED_CITY_ID = "";
    TextView tv_city;
    static RecyclerView rvWithStockList;
    ArrayList<WithStockItemPojo> withStockItemPojoArrayList = new ArrayList<>();
    ArrayList<WithStockItemPojo> with_out_StockItemPojoArrayList = new ArrayList<>();

    static RecyclerView rvWithoutStockList;
    WithStockItemAdapter lazyAdapter1, lazyAdapter2;

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_submit_prmotional_req) {
            if (et_ship_to.getText().toString().contentEquals("")) {
                Toast.makeText(this, "Please Select Retailer Name", Toast.LENGTH_LONG).show();

            } else if (et_address1_promoitonal.getText().toString().contentEquals("")) {

                Toast.makeText(PromoitonalRequirementActivity.this, "Please Enter Address 1", Toast.LENGTH_LONG).show();
            } else if (et_pincode.getText().toString().contentEquals("")) {
                Toast.makeText(PromoitonalRequirementActivity.this, "Please Enter Pincode", Toast.LENGTH_LONG).show();
            } else if (et_select_city.getSelectedItemPosition() == 0) {
                Toast.makeText(PromoitonalRequirementActivity.this, "Please Select City", Toast.LENGTH_LONG).show();
            } else if (withStockItemPojoArrayList.size() == 0) {

                Toast.makeText(this, "Please Add With Stock Item Details", Toast.LENGTH_LONG).show();

            } /*else if (with_out_StockItemPojoArrayList.size() == 0) {

                Toast.makeText(this, "Please Add Without Stock Item Details", Toast.LENGTH_LONG).show();
            } */ else {

                System.out.println("validated==== inserting promo requirement");
                insert_Promotional_Item_Request();

            }

        } else if (v.getId() == R.id.et_ship_to) {
            Intent intent = new Intent(this, Activity_Select_City.class);
            intent.putExtra("isFromCitySelect", 9899);
            startActivityForResult(intent, 9888);

        } else if (v.getId() == R.id.btnAddWithStock) {
            if (et_select_item.getSelectedItemPosition() == 0) {

                Toast.makeText(this, "Please Select With Stock Item Name", Toast.LENGTH_LONG).show();
            } else if (et_qty.getText().toString().contentEquals("")) {
                Toast.makeText(this, "Please Add Item Quantity", Toast.LENGTH_LONG).show();
            } /*else if (et_with_stock_remarks.getText().toString().contentEquals("")) {
                Toast.makeText(this, "Please Add Item Remarks", Toast.LENGTH_LONG).show();
            }*/ else {
                boolean is_duplicate = false;
                if (withStockItemPojoArrayList != null && withStockItemPojoArrayList.size() > 0) {
                    for (int it = 0; it < withStockItemPojoArrayList.size(); it++) {
                        if (withStockItemPojoArrayList.get(it).getItemName().compareToIgnoreCase(SELECTED_WITH_STOCK_ITEM) == 0) {
                            displaySnackBar("Duplicate Data");
                            is_duplicate = true;
                        }

                    }
                }


                if (!is_duplicate) {
                    withStockItemPojoArrayList.add(new WithStockItemPojo(et_select_item.getSelectedItem().toString() + "", SELECTED_WITH_STOCK_UOM + "", et_qty.getText().toString(), et_with_stock_remarks.getText().toString() + ""));
                    //= new Dealer_Sales_Order(SELECTED_CATEGORY_ID, "", SELECTED_PRODUCT_ID, "", ed_qty.getText().toString().trim() + "");
                    lazyAdapter1 = new WithStockItemAdapter(PromoitonalRequirementActivity.this, withStockItemPojoArrayList, true);

                    rvWithStockList.setAdapter(lazyAdapter1);
                    lazyAdapter1.notifyDataSetChanged();
                    et_qty.setText("");
                    et_uom_with_stock.setText("");
                    et_with_stock_remarks.setText("");
                    ArrayAdapter<String> with_stock_adapter = new ArrayAdapter<String>
                            (PromoitonalRequirementActivity.this, R.layout.searchable_spinner_text_view,
                                    with_stock_item_list);
                    with_stock_adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                    et_select_item.setAdapter(with_stock_adapter);
                    et_select_item.setTitle("Select Item");
                    et_select_item.setPositiveButton("Cancel");
                    rvWithStockList.setVisibility(View.VISIBLE);
                    tv_no_item_selected.setVisibility(View.GONE);


                }

            }


        } else if (v.getId() == R.id.btnAddWithoutStock) {

            if (et_select_without_item.getSelectedItemPosition() == 0) {

                Toast.makeText(this, "Please Select With Out Stock Item Name", Toast.LENGTH_LONG).show();
            } else if (et_without_qty.getText().toString().contentEquals("")) {
                Toast.makeText(this, "Please Add With Out Stock Item Quantity", Toast.LENGTH_LONG).show();
            } else if (et_without_stock_remarks.getText().toString().contentEquals("")) {
                Toast.makeText(this, "Please Add With Out Stock Item Remarks", Toast.LENGTH_LONG).show();
            } else {
                boolean is_duplicate = false;
                if (with_out_StockItemPojoArrayList != null && with_out_StockItemPojoArrayList.size() > 0) {
                    for (int it = 0; it < with_out_StockItemPojoArrayList.size(); it++) {
                        if (with_out_StockItemPojoArrayList.get(it).getItemName().compareToIgnoreCase(SELECTED_WITH_OUT_STOCK_ITEM) == 0) {
                            displaySnackBar("Duplicate Data");
                            is_duplicate = true;
                        }

                    }
                }


                if (!is_duplicate) {
                    with_out_StockItemPojoArrayList.add(new WithStockItemPojo(et_select_without_item.getSelectedItem().toString() + "", SELECTED_WITH_OUT_STOCK_UOM + "", et_without_qty.getText().toString(), et_without_stock_remarks.getText().toString() + ""));
                    //= new Dealer_Sales_Order(SELECTED_CATEGORY_ID, "", SELECTED_PRODUCT_ID, "", ed_qty.getText().toString().trim() + "");
                    lazyAdapter2 = new WithStockItemAdapter(PromoitonalRequirementActivity.this, with_out_StockItemPojoArrayList, false);

                    rvWithoutStockList.setAdapter(lazyAdapter2);
                    lazyAdapter2.notifyDataSetChanged();
                    et_without_qty.setText("");
                    et_without_uom.setText("");
                    et_without_stock_remarks.setText("");
                    ArrayAdapter<String> with_stock_adapter = new ArrayAdapter<String>
                            (PromoitonalRequirementActivity.this, R.layout.searchable_spinner_text_view,
                                    without_stock_item_list);
                    with_stock_adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                    et_select_without_item.setAdapter(with_stock_adapter);
                    et_select_without_item.setTitle("Select Item");
                    et_select_without_item.setPositiveButton("Cancel");
                    rvWithoutStockList.setVisibility(View.VISIBLE);
                    tv_no_item_selected2.setVisibility(View.GONE);


                }

            }


        }/*else if (v.getId() ==  R.id.tv_input_city_from_retailer){
            tv_input_city_from_retailer.setVisibility(View.GONE);
            et_select_city.setVisibility(View.VISIBLE);

        }*/


    }


    ArrayList<String> with_stock_item_list;
    ArrayList<String> with_stock_uom_list;
    private Get_StockWise_Promotional_Item_Pojo get_stockWise_promotional_item_pojo;

    /**
     * Flag Passed 1 for with stock
     **/
    private void get_stockWise_promotional_item() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_StockWise_Promotional_Item_Pojo> call = apiService.get_stockWise_promotional_item(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",

                Config.ACCESS_KEY,

                getSharedPref.getCompanyId() + "",
                "1"


        );


        call.enqueue(new Callback<Get_StockWise_Promotional_Item_Pojo>() {
            @Override
            public void onResponse(Call<Get_StockWise_Promotional_Item_Pojo> call, Response<Get_StockWise_Promotional_Item_Pojo> response) {
                if (response.isSuccessful()) {

                    progDialog.dismiss();

                    get_sAll_city();
                    get_stockWise_promotional_item_pojo = response.body();
                    if (get_stockWise_promotional_item_pojo != null) {
                        with_stock_item_list = new ArrayList<>();
                        with_stock_uom_list = new ArrayList<>();
                        with_stock_item_list.add("Select Item");
                        with_stock_uom_list.add("0");
                        for (int i = 0; i < get_stockWise_promotional_item_pojo.getRECORDS().size(); i++) {
                            with_stock_item_list.add(get_stockWise_promotional_item_pojo.getRECORDS().get(i).getItem_name() + "");
                            with_stock_uom_list.add(get_stockWise_promotional_item_pojo.getRECORDS().get(i).getUom_Name() + "");
                        }


                        ArrayAdapter<String> with_stock_adapter = new ArrayAdapter<String>
                                (PromoitonalRequirementActivity.this, R.layout.searchable_spinner_text_view,
                                        with_stock_item_list);
                        with_stock_adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        et_select_item.setAdapter(with_stock_adapter);
                        et_select_item.setTitle("Select Item");
                        et_select_item.setPositiveButton("Cancel");

                    }


                }

            }

            @Override
            public void onFailure(Call<Get_StockWise_Promotional_Item_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });

    }


    private Snackbar paymentSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(main_prom_req, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9888) {
            if (data.hasExtra("Cus_Name")) {
                et_ship_to.setText(data.getExtras().getString("Cus_Name"));

              /*  if (data.getExtras().getString("Shop_Name") == null) {
                    et_shop_name.setText("-");
                } else {
                    et_shop_name.setText(data.getExtras().getString("Shop_Name"));
                }

                if (data.getExtras().getString("Mobile_No") == null) {
                    et_mobile.setText("-");
                } else {
                    et_mobile.setText(data.getExtras().getString("Mobile_No"));
                }*/

                if (data.getExtras().getString("Area_Name") == null) {
                    // et_area.setText("-");

                } else {

                    int area_pos = 0;
                    String area = data.getExtras().getString("Area_Name");

                    if (area_list != null) {
                        for (int i = 0; i < area_list.size(); i++) {

                            if (area_list != null) {
                                if (area_list.contains(data.getExtras().getString("Area_Name"))) {

                                    area_pos = area_list.indexOf(area);


                                }
                            }


                        }
                        et_select_area.setSelection(area_pos);
                    } else {
                        et_select_area.setSelection(0);
                    }


                    // et_area.setText(data.getExtras().getString("Area_Name"));
                }

                // tv_input_city_from_retailer.setVisibility(View.VISIBLE);
                // et_select_city.setVisibility(View.GONE);
                // last_divider_city.setVisibility(View.GONE);

                if (data.getExtras().getString("City_Name") == null) {
                    // et_city_from_retailer.setText("-");
                } else {
                    int city_name = 0;
                    String city = data.getExtras().getString("City_Name");
                    for (int i = 0; i < city_list.size(); i++) {

                        if (city_list.contains(data.getExtras().getString("City_Name"))) {

                            city_name = city_list.indexOf(city);
                            break;


                        }


                    }
                    System.out.println("data from onac==" + data.getExtras().getString("City_Name"));
                    System.out.println("citypos" + city_name);
                    System.out.println("city=====" + city_list.get(city_name));
                    et_select_city.setSelection(city_name);


                    // et_city_from_retailer.setText(data.getExtras().getString("City_Name"));

                }

                if (data.getExtras().getString("Address1") == null) {
                    et_address1_promoitonal.setText("-");
                } else {
                    et_address1_promoitonal.setText(data.getExtras().getString("Address1"));
                }

                if (data.getExtras().getString("Address2") == null) {
                    et_address2_promoitonal.setText("-");
                } else {
                    et_address2_promoitonal.setText(data.getExtras().getString("Address2"));
                }

                if (data.getExtras().getString("Address3") == null) {
                    et_address3_promoitonal.setText("-");
                } else {
                    et_address3_promoitonal.setText(data.getExtras().getString("Address3"));
                }

                if (data.getExtras().getString("PinCode") == null) {
                    et_pincode.setText("-");
                } else {
                    et_pincode.setText(data.getExtras().getString("PinCode"));
                }

                if (data.getExtras().getString("State_Name") == null) {
                    et_state.setText("-");
                } else {
                    et_state.setText(data.getExtras().getString("State_Name"));
                }


                // et_select_retailer_name.setText(data.getExtras().getString("Cus_Name"));
                // et_select_retailer_name.setText(data.getExtras().getString("Cus_Name"));

            }


        }
    }


    ArrayList<String> city_list;
    ArrayList<String> city_list_id;
    private Get_All_City_Pojo get_all_city_pojo;

    /**
     * passed  0 as state id instruciton from back end
     * get_sAll_city api added by harsh 25-09-2020
     **/
    private void get_sAll_city() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_All_City_Pojo> call = apiService.get_all_city(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,

                getSharedPref.getCompanyId() + "",
                getSharedPref.getBranchId() + "",
                "0"


        );


        call.enqueue(new Callback<Get_All_City_Pojo>() {
            @Override
            public void onResponse(Call<Get_All_City_Pojo> call, Response<Get_All_City_Pojo> response) {
                if (response.isSuccessful()) {

                    progDialog.dismiss();

                    /**calling get_stockWithout_promotional_item with flag 2 to get Without stock item details  **/
                    get_stockWithout_promotional_item();
                    /**calling get_stockWithout_promotional_item with flag 2 to get Without stock item details  **/
                    get_all_city_pojo = response.body();
                    if (get_all_city_pojo != null) {

                        city_list = new ArrayList<>();
                        city_list_id = new ArrayList<>();

                        city_list.add("Select City");
                        city_list_id.add("-");

                        for (int i = 0; i < get_all_city_pojo.getRECORDS().size(); i++) {
                            city_list.add(get_all_city_pojo.getRECORDS().get(i).getNAME() + "");
                            city_list_id.add(get_all_city_pojo.getRECORDS().get(i).getID() + "");
                        }


                        ArrayAdapter<String> city_adapter = new ArrayAdapter<String>
                                (PromoitonalRequirementActivity.this, R.layout.searchable_spinner_text_view,
                                        city_list);
                        city_adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        et_select_city.setAdapter(city_adapter);
                        et_select_city.setTitle("City");
                        et_select_city.setPositiveButton("Cancel");
                    }


                }

            }

            @Override
            public void onFailure(Call<Get_All_City_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    /***Get_Area Api added by harsh on 25-09-2020**/


    private Get_Area_Pojo get_area_pojo;
    ArrayList<String> area_list;

    private void get__area(String cit_id) {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_Area_Pojo> call = apiService.Get_Area(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                Config.ACCESS_KEY,
                getSharedPref.getRegisteredUserId() + "",


                getSharedPref.getCompanyId() + "",
                cit_id


        );


        call.enqueue(new Callback<Get_Area_Pojo>() {
            @Override
            public void onResponse(Call<Get_Area_Pojo> call, Response<Get_Area_Pojo> response) {
                if (response.isSuccessful()) {
                    progDialog.dismiss();

                    get_area_pojo = response.body();
                    if (get_area_pojo != null) {

                        if (get_area_pojo.getRECORDS().size() > 0) {

                            area_list = new ArrayList<>();
                            area_list.add("Select Area");

                            for (int i = 0; i < get_area_pojo.getRECORDS().size(); i++) {
                                area_list.add(get_area_pojo.getRECORDS().get(i).getArea_name() + "");
                            }


                            ArrayAdapter<String> area_adapter = new ArrayAdapter<String>
                                    (PromoitonalRequirementActivity.this, R.layout.searchable_spinner_text_view,
                                            area_list);
                            area_adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                            et_select_area.setAdapter(area_adapter);
                            et_select_area.setTitle("Area");
                            et_select_area.setPositiveButton("Cancel");
                        } else {

                            if (area_list != null) {
                                area_list.clear();

                                area_list = new ArrayList<>();
                                area_list.add("Select Area");
                                ArrayAdapter<String> area_adapter = new ArrayAdapter<String>
                                        (PromoitonalRequirementActivity.this, R.layout.searchable_spinner_text_view,
                                                area_list);
                                area_adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                                et_select_area.setAdapter(area_adapter);
                                et_select_area.setTitle("Area");
                                et_select_area.setPositiveButton("Cancel");

                            } else {
                                area_list = new ArrayList<>();
                                area_list.add("Select Area");
                                et_select_area.setSelection(0);
                                progDialog.dismiss();
                            }

                            /*if (area_list.isEmpty()) {

                                area_list = new ArrayList<>();
                                area_list.add("Select Area");
                                et_select_area.setSelection(0);
                                progDialog.dismiss();


                            }*/

                        }


                    } else {

                    }


                }

            }

            @Override
            public void onFailure(Call<Get_Area_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });

    }

    ArrayList<String> without_stock_item_list;
    ArrayList<String> without_stock_uom_list;
    private Get_StockWise_Promotional_Item_Pojo get_without_stock_promotional_item_pojo;

    /**
     * Flage passed 2 to get without stock  items
     **/
    private void get_stockWithout_promotional_item() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_StockWise_Promotional_Item_Pojo> call = apiService.get_stockWise_promotional_item(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",

                Config.ACCESS_KEY,

                getSharedPref.getCompanyId() + "",
                "2"


        );


        call.enqueue(new Callback<Get_StockWise_Promotional_Item_Pojo>() {
            @Override
            public void onResponse(Call<Get_StockWise_Promotional_Item_Pojo> call, Response<Get_StockWise_Promotional_Item_Pojo> response) {
                if (response.isSuccessful()) {

                    progDialog.dismiss();


                    get_without_stock_promotional_item_pojo = response.body();
                    if (get_without_stock_promotional_item_pojo != null) {
                        without_stock_item_list = new ArrayList<>();
                        without_stock_uom_list = new ArrayList<>();
                        without_stock_item_list.add("Select Item");
                        without_stock_uom_list.add("0");
                        for (int i = 0; i < get_without_stock_promotional_item_pojo.getRECORDS().size(); i++) {
                            without_stock_item_list.add(get_without_stock_promotional_item_pojo.getRECORDS().get(i).getItem_name() + "");
                            without_stock_uom_list.add(get_without_stock_promotional_item_pojo.getRECORDS().get(i).getUom_Name() + "");
                        }


                        ArrayAdapter<String> with_out_stock_adapter = new ArrayAdapter<String>
                                (PromoitonalRequirementActivity.this, R.layout.searchable_spinner_text_view,
                                        without_stock_item_list);
                        with_out_stock_adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        et_select_without_item.setAdapter(with_out_stock_adapter);
                        et_select_without_item.setTitle("Select Item");
                        et_select_without_item.setPositiveButton("Cancel");

                        area_list = new ArrayList<>();
                        area_list.add("Select Area");
                        ArrayAdapter<String> area_adapter = new ArrayAdapter<String>
                                (PromoitonalRequirementActivity.this, R.layout.searchable_spinner_text_view,
                                        area_list);
                        area_adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                        et_select_area.setAdapter(area_adapter);
                        et_select_area.setTitle("Area");
                        et_select_area.setPositiveButton("Cancel");

                    }


                }

            }

            @Override
            public void onFailure(Call<Get_StockWise_Promotional_Item_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });

    }


    /**
     * Insert_Promotional_Item_Request added by harsh on 26-09-2020
     **/


    private Insert_RoutWise_Complaint_Pojo insert_routWise_complaint_pojo;

    private void insert_Promotional_Item_Request() {
        String area = "";

        if (et_select_area.getSelectedItemPosition() == 0) {
            area = "-";
        } else if (et_select_area.getSelectedItem() == null) {
            area = "-";
        } else {
            area = et_select_area.getSelectedItem().toString();
        }


        /**with stock========================*/

        JSONObject with_stock_with_all_item = new JSONObject();


        JSONArray item_array = new JSONArray();
        for (int i = 0; i < withStockItemPojoArrayList.size(); i++) {

            JSONObject withStock = new JSONObject();
            try {

                withStock.put("itm_name", withStockItemPojoArrayList.get(i).getItemName());
                withStock.put("Uom", withStockItemPojoArrayList.get(i).getUom());
                withStock.put("qty", withStockItemPojoArrayList.get(i).getQty());
                withStock.put("remarks", withStockItemPojoArrayList.get(i).getRemarks());

            } catch (JSONException e) {
                e.printStackTrace();

                System.out.println("error in json creation!!!!!");
            }

            item_array.put(withStock);

        }

        try {
            with_stock_with_all_item.put("with_Stock", item_array);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("final" + with_stock_with_all_item);


        /**with stock========================*/

        /**(2)without stock========================*/


        JSONObject with_out_stock_with_all_item = new JSONObject();
        JSONArray item_without_stock_array = new JSONArray();

        for (int i = 0; i < with_out_StockItemPojoArrayList.size(); i++) {

            JSONObject withoutStock = new JSONObject();
            try {

                withoutStock.put("itm_name", with_out_StockItemPojoArrayList.get(i).getItemName());
                withoutStock.put("Uom", with_out_StockItemPojoArrayList.get(i).getUom());
                withoutStock.put("qty", with_out_StockItemPojoArrayList.get(i).getQty());
                withoutStock.put("remarks", with_out_StockItemPojoArrayList.get(i).getRemarks());

            } catch (JSONException e) {
                e.printStackTrace();

                System.out.println("error in json creation!!!!!");
            }

            item_without_stock_array.put(withoutStock);

        }

        try {
            with_out_stock_with_all_item.put("with_out_Stock", item_without_stock_array);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("final_without" + with_out_stock_with_all_item);


        /**without stock==========================*/


        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }


        final Insert_Promotional_Item_Request datarequest = new Insert_Promotional_Item_Request(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY,
                getSharedPref.getCompanyId(), et_ship_to.getText().toString().trim(),
                et_address1_promoitonal.getText().toString().trim(),
                et_address2_promoitonal.getText().toString().trim(),
                et_address3_promoitonal.getText().toString().trim(),
                et_select_city.getSelectedItem().toString(),
                et_state.getText().toString().trim(),


                area,
                et_pincode.getText().toString().trim(),
                et_remark.getText().toString().trim(),
                item_array.toString(),
                item_without_stock_array.toString());

        Call<Insert_RoutWise_Complaint_Pojo> call = apiService.insert_promotional_item_request(datarequest);

        call.enqueue(new Callback<Insert_RoutWise_Complaint_Pojo>() {
            @Override
            public void onResponse(Call<Insert_RoutWise_Complaint_Pojo> call, Response<Insert_RoutWise_Complaint_Pojo> response) {
                if (response.isSuccessful()) {
                    insert_routWise_complaint_pojo = response.body();

                    progDialog.dismiss();
                    if (insert_routWise_complaint_pojo != null) {
                        displaySnackBar(insert_routWise_complaint_pojo.getMESSAGE() + "");
                        et_ship_to.setText("");
                        et_address1_promoitonal.setText("");
                        et_address2_promoitonal.setText("");
                        et_address3_promoitonal.setText("");
                        et_state.setText("");
                        et_pincode.setText("");
                        et_remark.setText("");
                        withStockItemPojoArrayList.clear();
                        with_out_StockItemPojoArrayList.clear();

                        if (lazyAdapter1 != null) {
                            lazyAdapter1.notifyDataSetChanged();
                        }

                        if (lazyAdapter2 != null) {
                            lazyAdapter2.notifyDataSetChanged();
                        }

                        tv_no_item_selected.setVisibility(View.VISIBLE);
                        tv_no_item_selected2.setVisibility(View.VISIBLE);
                        et_select_city.setSelection(0);
                        et_select_area.setSelection(0);


                    }


                }

            }

            @Override
            public void onFailure(Call<Insert_RoutWise_Complaint_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage() + "");


            }
        });

    }


    /**
     * Insert_Promotional_Item_Request added by harsh on 26-09-2020
     **/

    private ArrayList<String> state_list = new ArrayList<>();
    private ArrayList<City_State_Taluka_CountryPojo.RECORDSBean> city_state_taluka_countryPojos = new ArrayList<>();
    private City_State_Taluka_CountryPojo city_state_taluka_countryPojo;

    private void Get_All_City_Taluka_District() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<City_State_Taluka_CountryPojo> call = apiService.Get_All_City_Taluka_District(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",

                getSharedPref.getRegisteredUserId(),
                Config.ACCESS_KEY,

                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<City_State_Taluka_CountryPojo>() {
            @Override
            public void onResponse(Call<City_State_Taluka_CountryPojo> call, Response<City_State_Taluka_CountryPojo> response) {
                if (response.isSuccessful()) {


                    city_state_taluka_countryPojo = response.body();

                    if (city_state_taluka_countryPojo != null && city_state_taluka_countryPojo.getRECORDS().size() > 0) {

                        for (int i = 0; i < city_state_taluka_countryPojo.getRECORDS().size(); i++) {
                            state_list.add(city_state_taluka_countryPojo.getRECORDS().get(i).getState_Name());
                            city_state_taluka_countryPojos.add(city_state_taluka_countryPojo.getRECORDS().get(i));
                        }


                    }

                }

            }

            @Override
            public void onFailure(Call<City_State_Taluka_CountryPojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());
            }
        });


    }


}
