package com.infinity.infoway.vimal.delear.activity.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.response.City_State_Taluka_CountryPojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.BoxOrderListForAdapter;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_All_employee_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Distributor_and_its_Retailer_detail_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Sale_Order_Consignee_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Size_Flavour_Wise_All_Items_Detail_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.InsertRespectiveResponsePojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.InsertRespectiveSalesOrderReqModel;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.ItemDetailsJasonReqModel;
import com.infinity.infoway.vimal.delear.util.CommonUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewOrderFragment extends Fragment implements View.OnClickListener {

    private Activity context;
    private SharedPref getSharedPref;
    private ProgressDialog progDialog;

    private SearchableSpinner spCustomer;
    private EditText edtDeliveryDate;
    private AppCompatCheckBox cbOtherDeliveryAddress;
    private SearchableSpinner spDelAddressTitle;
    private SearchableSpinner spCity;
    private EditText edtConsigneeName;
    private EditText edtAddress1;
    private EditText edtAddress2;
    private EditText edtAddress3;
    private EditText edtState;
    private EditText edtPANno;
    private EditText edtGSTIN;
    private EditText edtPinCode;
    private EditText edtContactPerson;
    private EditText edtContactNo;
    private LinearLayout llOrderDeliveryAddress;
//    private SearchableSpinner spSalesPerson;
    private EditText edtRemarks;
    private Button btnSubmitOrder;
    private ConstraintLayout clMainOrder;
    //    private SearchableSpinner spOrderList;
    private LinearLayout llBasicDetails;
    private LinearLayout llProductDetails;
    private AppCompatImageView ivBasicDetails;
    private AppCompatImageView ivProductDetails;
    private boolean basicDetailsToggle = false;
    private boolean productDetailsToggle = true;
    private RecyclerView rvProductList;
    private BoxOrderListForAdapter boxOrderListForAdapter;
    private CardView cvProductHeader;

    private ArrayList<String> customerNameArrayList;
    private ArrayList<String> customerIdArrayList = new ArrayList<>();
    private HashMap<String, Get_Distributor_and_its_Retailer_detail_Pojo.RECORD> distributorAndRetailerRecordHashMap;

    private ArrayList<String> allEmployeeArrayList;
    private HashMap<String, Get_All_employee_Pojo.RECORDSBean> getAllEmployeeRecordArrayListHashMap;

    private ArrayList<String> salesOrderConsigneeArrayList;
    private HashMap<String, Get_Sale_Order_Consignee_Pojo.RECORD> salesOrderConsigneeHashMap;

    private LinearLayout llAddressTitle;
//    private LinearLayout llSalesPerson;
    private LinearLayout llExpandedProductDetails, llExpandedBasicDetailsNewOrder;

//    private ArrayList<String> salesOrderArrayList;
//    private HashMap<String, Get_Sales_Order_List_Pojo.RECORD> salesOrderHashMap;

    private ArrayList<String> cityAndStateArrayList = new ArrayList<>();
    private HashMap<String, City_State_Taluka_CountryPojo.RECORDSBean> cityAndStateHashMap;

    private Calendar my_calendar = Calendar.getInstance();
    ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModelArrayListFinal;

    private CardView cvProductDetails;

    LinearLayout llTotal;
    public static EditText edtTotal;
    public static TextView edtTotalAmount, edtTotalWeight;

    final DatePickerDialog.OnDateSetListener deliveryDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            my_calendar.set(Calendar.YEAR, year);
            my_calendar.set(Calendar.MONTH, monthOfYear);
            my_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            edtDeliveryDate.setText(sdf.format(my_calendar.getTime()));
            if (spCustomer.getSelectedItemPosition() > 0 &&
                    !TextUtils.isEmpty(edtDeliveryDate.getText().toString())) {
                Get_Distributor_and_its_Retailer_detail_Pojo.RECORD record =
                        distributorAndRetailerRecordHashMap.get(customerNameArrayList.get(spCustomer.getSelectedItemPosition()).trim());
                getSizeFlavourWiseItemsApiCall(true, true,
                        edtDeliveryDate.getText().toString(), String.valueOf(record.getId()), "0");
            }
        }
    };

    public NewOrderFragment() {
        // Required empty public constructor
    }

    boolean isOrder = false;

    public NewOrderFragment(boolean isOrder) {
        this.isOrder = isOrder;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initView(view);
        getDiatributorAndRetailerNameApiCall(true, false);
        return view;
    }


    private void initView(View view) {
        getSharedPref = new SharedPref(context);
        progDialog = new ProgressDialog(context);
        rvProductList = view.findViewById(R.id.rvProductList);
        edtTotal = view.findViewById(R.id.edttotalBox);
        edtTotalAmount = view.findViewById(R.id.tvTotalAmount);
        edtTotalWeight = view.findViewById(R.id.tvtotalWeight);

        cvProductDetails = view.findViewById(R.id.cvProductDetails);
        llTotal = view.findViewById(R.id.llTotal);
        cvProductHeader = view.findViewById(R.id.cvProductHeader);
        llBasicDetails = view.findViewById(R.id.llBasicDetails);
        llProductDetails = view.findViewById(R.id.llProductDetails);
        ivBasicDetails = view.findViewById(R.id.ivBasicDetails);
        ivBasicDetails.setOnClickListener(this);
        ivProductDetails = view.findViewById(R.id.ivProductDetails);
        ivProductDetails.setOnClickListener(this);
        llExpandedBasicDetailsNewOrder = view.findViewById(R.id.llExpandedBasicDetailsNewOrder);
        llExpandedBasicDetailsNewOrder.setOnClickListener(this);
        llExpandedProductDetails = view.findViewById(R.id.llExpandedProductDetails);
        llExpandedProductDetails.setOnClickListener(this);


        llAddressTitle = view.findViewById(R.id.llAddressTitle);
//        llSalesPerson = view.findViewById(R.id.llSalesPerson);

        spCustomer = view.findViewById(R.id.spCustomer);
        spCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    edtTotal.setText("");
                    edtTotalAmount.setText("");
                    edtTotalWeight.setText("");
                    Get_Distributor_and_its_Retailer_detail_Pojo.RECORD record = distributorAndRetailerRecordHashMap.get(customerNameArrayList.get(position).trim());

                    if (!TextUtils.isEmpty(edtDeliveryDate.getText().toString())) {
                        getSaleOrderConsigneeApiCall(true, false, record.getId(),
                                edtDeliveryDate.getText().toString(), "0", true);
                    } else {
                        getSaleOrderConsigneeApiCall(true, true, record.getId(),
                                edtDeliveryDate.getText().toString(), "0", false);
                    }
                    String consigneeName = CommonUtils.checkIsEmptyOrNullCommon(record.getConsigneeName()) ? "" : record.getConsigneeName();
                    String add1 = CommonUtils.checkIsEmptyOrNullCommon(record.getAddress1()) ? "" : record.getAddress1();
                    String add2 = CommonUtils.checkIsEmptyOrNullCommon(record.getAddress2()) ? "" : record.getAddress2();
                    String add3 = CommonUtils.checkIsEmptyOrNullCommon(record.getAddress3()) ? "" : record.getAddress3().toString();
                    String cityName = CommonUtils.checkIsEmptyOrNullCommon(record.getCityName()) ? "" : record.getCityName();
                    String stateName = CommonUtils.checkIsEmptyOrNullCommon(record.getStateName()) ? "" : record.getStateName();
                    String panNo = CommonUtils.checkIsEmptyOrNullCommon(record.getPANNo()) ? " " : record.getPANNo().toString();
                    String GSTIN = CommonUtils.checkIsEmptyOrNullCommon(record.getGSTINNo()) ? "" : record.getGSTINNo().toString();
                    String pinCode = CommonUtils.checkIsEmptyOrNullCommon(record.getPinCode()) ? "" : record.getPinCode();
                    String contactPerson = CommonUtils.checkIsEmptyOrNullCommon(record.getContactPerson()) ? "" : record.getContactPerson();
                    String mobileNo = CommonUtils.checkIsEmptyOrNullCommon(record.getMobileNo()) ? "" : record.getMobileNo();

                    setData(consigneeName, add1, add2, add3, cityName,
                            stateName, panNo, GSTIN, pinCode,
                            contactPerson, mobileNo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtDeliveryDate = view.findViewById(R.id.edtDeliveryDate);
        edtDeliveryDate.setOnClickListener(this);
        spDelAddressTitle = view.findViewById(R.id.spDelAddressTitle);
        spDelAddressTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    edtConsigneeName.setEnabled(false);
                    edtAddress1.setEnabled(false);
                    edtAddress2.setEnabled(false);
                    edtAddress3.setEnabled(false);
                    edtPANno.setEnabled(false);
                    edtGSTIN.setEnabled(false);
                    edtPinCode.setEnabled(false);
                    edtContactPerson.setEnabled(false);
                    edtContactNo.setEnabled(false);

                    Get_Sale_Order_Consignee_Pojo.RECORD record = salesOrderConsigneeHashMap.get(salesOrderConsigneeArrayList.get(position));


                    String consigneeName = CommonUtils.checkIsEmptyOrNullCommon(record.getConsigneeName()) ? "" : record.getConsigneeName();
                    String add1 = CommonUtils.checkIsEmptyOrNullCommon(record.getAddress1()) ? "" : record.getAddress1();
                    String add2 = CommonUtils.checkIsEmptyOrNullCommon(record.getAddress2()) ? "" : record.getAddress2();
                    String add3 = CommonUtils.checkIsEmptyOrNullCommon(record.getAddress3()) ? "" : record.getAddress3().toString();
                    String cityName = CommonUtils.checkIsEmptyOrNullCommon(record.getCityName()) ? "" : record.getCityName();
                    String stateName = CommonUtils.checkIsEmptyOrNullCommon(record.getStateName()) ? "" : record.getStateName();
                    String panNo = CommonUtils.checkIsEmptyOrNullCommon(record.getPANNo()) ? " " : record.getPANNo().toString();
                    String GSTIN = CommonUtils.checkIsEmptyOrNullCommon(record.getGSTINNo()) ? "" : record.getGSTINNo().toString();
                    String pinCode = CommonUtils.checkIsEmptyOrNullCommon(record.getPinCode()) ? "" : record.getPinCode();
                    String contactPerson = CommonUtils.checkIsEmptyOrNullCommon(record.getContactPerson()) ? "" : record.getContactPerson();
                    String mobileNo = CommonUtils.checkIsEmptyOrNullCommon(record.getMobileNo()) ? "" : record.getMobileNo();

                    setData(consigneeName, add1, add2, add3, cityName,
                            stateName, panNo, GSTIN, pinCode,
                            contactPerson, mobileNo);


                } else {
                    edtConsigneeName.setEnabled(true);
                    edtAddress1.setEnabled(true);
                    edtAddress2.setEnabled(true);
                    edtAddress3.setEnabled(true);
                    edtPANno.setEnabled(true);
                    edtGSTIN.setEnabled(true);
                    edtPinCode.setEnabled(true);
                    edtContactPerson.setEnabled(true);
                    edtContactNo.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtConsigneeName = view.findViewById(R.id.edtConsigneeName);
        edtAddress1 = view.findViewById(R.id.edtAddress1);
        edtAddress2 = view.findViewById(R.id.edtAddress2);
        edtAddress3 = view.findViewById(R.id.edtAddress3);
        edtState = view.findViewById(R.id.edtState);
        edtPANno = view.findViewById(R.id.edtPANno);
        edtGSTIN = view.findViewById(R.id.edtGSTIN);
        edtPinCode = view.findViewById(R.id.edtPinCode);
        edtContactPerson = view.findViewById(R.id.edtContactPerson);
        edtContactNo = view.findViewById(R.id.edtContactNo);

        llOrderDeliveryAddress = view.findViewById(R.id.llOrderDeliveryAddress);
//        spSalesPerson = view.findViewById(R.id.spSalesPerson);
        edtRemarks = view.findViewById(R.id.edtRemarks);
        btnSubmitOrder = view.findViewById(R.id.btnSubmitOrder);
        btnSubmitOrder.setOnClickListener(this);
        clMainOrder = view.findViewById(R.id.clMainOrder);

//        spOrderList = view.findViewById(R.id.spOrderList);

        cbOtherDeliveryAddress = view.findViewById(R.id.cbOtherDeliveryAddress);
        cbOtherDeliveryAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llOrderDeliveryAddress.setVisibility(View.VISIBLE);
                } else {
                    llOrderDeliveryAddress.setVisibility(View.GONE);
                }
            }
        });
        spCity = view.findViewById(R.id.spCity);
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String cityName = cityAndStateArrayList.get(spCity.getSelectedItemPosition());
                    City_State_Taluka_CountryPojo.RECORDSBean recordsBean = cityAndStateHashMap.get(cityName);
                    if (!CommonUtils.checkIsEmptyOrNullCommon(recordsBean.getState_Name())) {
                        edtState.setText(recordsBean.getState_Name());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isValid() {
        boolean isValid = true;
        if (spCustomer.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please select customer", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (TextUtils.isEmpty(edtDeliveryDate.getText().toString())) {
            Toast.makeText(context, "Please select delivery date", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (cbOtherDeliveryAddress.isChecked() &&
                TextUtils.isEmpty(edtConsigneeName.getText().toString())) {
            Toast.makeText(context, "Please enter consignee name", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    private void setData(String consigneeName, String address1, String address2, String address3,
                         String city, String state, String panNo, String GSTIN,
                         String pinCode, String contactPerson, String contactNo) {
        if (!CommonUtils.checkIsEmptyOrNullCommon(consigneeName)) {
            edtConsigneeName.setText(consigneeName);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(address1)) {
            edtAddress1.setText(address1);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(address2)) {
            edtAddress2.setText(address2);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(address3)) {
            edtAddress3.setText(address3);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(city)) {
            int cityPosition = cityAndStateArrayList.indexOf(city.trim());
            spCity.setSelection(cityPosition);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(state)) {
            edtState.setText(state);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(panNo)) {
            edtPANno.setText(panNo);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(GSTIN)) {
            edtGSTIN.setText(GSTIN);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(pinCode)) {
            edtPinCode.setText(pinCode);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(contactPerson)) {
            edtContactPerson.setText(contactPerson);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(contactNo)) {
            edtContactNo.setText(contactNo);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmitOrder) {
            if (isValid()) {

                Get_Distributor_and_its_Retailer_detail_Pojo.RECORD record =
                        distributorAndRetailerRecordHashMap.get(customerNameArrayList.get(spCustomer.getSelectedItemPosition()).trim());

                String app_version = String.valueOf(getSharedPref.getAppVersionCode());
                String android_id = getSharedPref.getAppAndroidId();
                String device_id = String.valueOf(getSharedPref.getRegisteredId());
                String user_id = getSharedPref.getRegisteredUserId();
                String key = Config.ACCESS_KEY;
                String comp_id = getSharedPref.getCompanyId();
                String cus_name = customerNameArrayList.get(spCustomer.getSelectedItemPosition());
                String delivery_date = edtDeliveryDate.getText().toString();
                String som_id = "0";
                String cus_id = CommonUtils.checkIsEmptyOrNullCommon(record.getId()) ? "" : record.getId().toString();
                String ord_to_ref_id = CommonUtils.checkIsEmptyOrNullCommon(record.getOrderToRefId()) ? "" : record.getOrderToRefId().toString();
                String chk_other_del = cbOtherDeliveryAddress.isChecked() ? "0" : "1";

                String area_name = "";
                String full_address = "";
                String del_add_title = "";
                String del_cus_name = "";
                String del_address1 = "";
                String del_address2 = "";
                String del_address3 = "";
                String del_cit_name = "";
                String del_state_name = "";
                String del_area_name = "";
                String del_pan_no = "";
                String del_contact_person = "";
                String del_contact_no = "";
                String del_GSTIN = "";
                String del_pincode = "";


                if (cbOtherDeliveryAddress.isChecked()) {
                    area_name = CommonUtils.checkIsEmptyOrNullCommon(record.getAreaName()) ? "" : record.getAreaName().toString();
                    full_address = CommonUtils.checkIsEmptyOrNullCommon(record.getFullAddress()) ? "" : record.getFullAddress();
                    del_add_title = salesOrderConsigneeArrayList != null ? salesOrderConsigneeArrayList.get(spDelAddressTitle.getSelectedItemPosition()) : "";
                    del_cus_name = edtConsigneeName.getText().toString();
                    del_address1 = edtAddress1.getText().toString();
                    del_address2 = edtAddress2.getText().toString();
                    del_address3 = edtAddress3.getText().toString();
                    del_cit_name = spCity.getSelectedItemPosition() == 0 ? "" : cityAndStateArrayList.get(spCity.getSelectedItemPosition());
                    del_state_name = edtState.getText().toString();
                    del_area_name = CommonUtils.checkIsEmptyOrNullCommon(record.getAreaName()) ? "" : record.getFullAddress();
                    del_pan_no = edtPANno.getText().toString();
                    del_contact_person = edtContactPerson.getText().toString();
                    del_contact_no = edtContactNo.getText().toString();
                    del_GSTIN = edtGSTIN.getText().toString();
                    del_pincode = edtPinCode.getText().toString();
                }

//                String sales_person_name = "";
//                if (allEmployeeArrayList != null) {
//                    sales_person_name = spSalesPerson.getSelectedItemPosition() == 0 ? "" : allEmployeeArrayList.get(spSalesPerson.getSelectedItemPosition());
//                }
                String remarks = edtRemarks.getText().toString();

                String productInfo = "";

                JSONArray jsonArray = new JSONArray();

                if (itemDetailsJasonReqModelArrayListFinal != null &&
                        itemDetailsJasonReqModelArrayListFinal.size() > 0) {
                    for (int i = 0; i < itemDetailsJasonReqModelArrayListFinal.size(); i++) {
                        if (itemDetailsJasonReqModelArrayListFinal.get(i).isEdited()) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                productInfo += "Product Name:- " + itemDetailsJasonReqModelArrayListFinal.get(i).getItem_name() + "\n" +
                                        "Quantity:- " + itemDetailsJasonReqModelArrayListFinal.get(i).getQty() + "\n" +
                                        "Price:- " + itemDetailsJasonReqModelArrayListFinal.get(i).getPrice() + "\n" +
                                        "Flavour:- " + itemDetailsJasonReqModelArrayListFinal.get(i).getFlavour() + "\n\n";

                                jsonObject.put("item_id", itemDetailsJasonReqModelArrayListFinal.get(i).getItem_id());
                                jsonObject.put("item_name", itemDetailsJasonReqModelArrayListFinal.get(i).getItem_name());
                                jsonObject.put("qty", itemDetailsJasonReqModelArrayListFinal.get(i).getQty());
                                jsonObject.put("disc_amt", itemDetailsJasonReqModelArrayListFinal.get(i).getDisc_amt());
                                jsonObject.put("disc_per", itemDetailsJasonReqModelArrayListFinal.get(i).getDisc_per());
                                jsonObject.put("basic_price", itemDetailsJasonReqModelArrayListFinal.get(i).getBasic_price());
                                jsonObject.put("price", itemDetailsJasonReqModelArrayListFinal.get(i).getPrice());
                                jsonObject.put("st_uom_id", itemDetailsJasonReqModelArrayListFinal.get(i).getSt_uom_id());
                                jsonObject.put("hsn_code", itemDetailsJasonReqModelArrayListFinal.get(i).getHsn_code());
                                jsonObject.put("gst_type", itemDetailsJasonReqModelArrayListFinal.get(i).getGst_type());
                                jsonObject.put("gst_per", itemDetailsJasonReqModelArrayListFinal.get(i).getGst_per());
                                jsonObject.put("cess_per", itemDetailsJasonReqModelArrayListFinal.get(i).getCess_per());
                                jsonObject.put("flavour", itemDetailsJasonReqModelArrayListFinal.get(i).getFlavour());
                                jsonObject.put("size", itemDetailsJasonReqModelArrayListFinal.get(i).getSize());
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }


                InsertRespectiveSalesOrderReqModel insertRespectiveSalesOrderReqModel = new InsertRespectiveSalesOrderReqModel();
                insertRespectiveSalesOrderReqModel.setApp_version(app_version);
                insertRespectiveSalesOrderReqModel.setAndroid_id(android_id);
                insertRespectiveSalesOrderReqModel.setDevice_id(device_id);
                insertRespectiveSalesOrderReqModel.setUser_id(user_id);
                insertRespectiveSalesOrderReqModel.setKey(key);
                insertRespectiveSalesOrderReqModel.setComp_id(comp_id);
                insertRespectiveSalesOrderReqModel.setCus_name(cus_name);
                insertRespectiveSalesOrderReqModel.setDelivery_date(delivery_date);
                insertRespectiveSalesOrderReqModel.setSom_id(som_id);
                insertRespectiveSalesOrderReqModel.setCus_id(cus_id);
                insertRespectiveSalesOrderReqModel.setOrd_to_ref_id(ord_to_ref_id);
                insertRespectiveSalesOrderReqModel.setArea_name(area_name);
                insertRespectiveSalesOrderReqModel.setFull_address(full_address);
                insertRespectiveSalesOrderReqModel.setChk_other_del(chk_other_del);
                insertRespectiveSalesOrderReqModel.setDel_cus_name(del_cus_name);
                insertRespectiveSalesOrderReqModel.setDel_add_title(del_add_title);
                insertRespectiveSalesOrderReqModel.setDel_address1(del_address1);
                insertRespectiveSalesOrderReqModel.setDel_address2(del_address2);
                insertRespectiveSalesOrderReqModel.setDel_address3(del_address3);
                insertRespectiveSalesOrderReqModel.setDel_cit_name(del_cit_name);
                insertRespectiveSalesOrderReqModel.setDel_state_name(del_state_name);
                insertRespectiveSalesOrderReqModel.setDel_area_name(del_area_name);
                insertRespectiveSalesOrderReqModel.setDel_pan_no(del_pan_no);
                insertRespectiveSalesOrderReqModel.setDel_contact_person(del_contact_person);
                insertRespectiveSalesOrderReqModel.setDel_contact_no(del_contact_no);
                insertRespectiveSalesOrderReqModel.setDel_GSTIN(del_GSTIN);
                insertRespectiveSalesOrderReqModel.setDel_pincode(del_pincode);
                insertRespectiveSalesOrderReqModel.setSales_person_name("");//sales persion field remove by remish as per discussion with darmeshbhai
                insertRespectiveSalesOrderReqModel.setRemarks(remarks);
                insertRespectiveSalesOrderReqModel.setItem_detail_json(jsonArray.toString());

                insertUpdateRespectiveSalesOrder(insertRespectiveSalesOrderReqModel, productInfo);
            }
        } else if (v.getId() == R.id.edtDeliveryDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, deliveryDatePicker, my_calendar
                    .get(Calendar.YEAR), my_calendar.get(Calendar.MONTH),
                    my_calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.show();
        } else if (v.getId() == R.id.ivBasicDetails ||
                v.getId() == R.id.llExpandedBasicDetailsNewOrder) {
            if (basicDetailsToggle) {
                basicDetailsToggle = false;
                ivBasicDetails.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_up));
                llBasicDetails.setVisibility(View.VISIBLE);
            } else {
                basicDetailsToggle = true;
                ivBasicDetails.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_down));
                llBasicDetails.setVisibility(View.GONE);
            }

        } else if (v.getId() == R.id.ivProductDetails ||
                v.getId() == R.id.llExpandedProductDetails) {
            if (productDetailsToggle) {
                productDetailsToggle = false;
                ivProductDetails.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_up));
                llProductDetails.setVisibility(View.VISIBLE);
                llTotal.setVisibility(View.VISIBLE);
            } else {
                productDetailsToggle = true;
                ivProductDetails.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_down));
                llProductDetails.setVisibility(View.GONE);
                llTotal.setVisibility(View.GONE);
            }
        }
    }


    private void getDiatributorAndRetailerNameApiCall(boolean isPdShow, final boolean isPdHide) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiClient.getDistributorAndRetailerDetailsImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(),
                Config.ACCESS_KEY, getSharedPref.getCompanyId(), new Callback<Get_Distributor_and_its_Retailer_detail_Pojo>() {
                    @Override
                    public void onResponse(Call<Get_Distributor_and_its_Retailer_detail_Pojo> call, Response<Get_Distributor_and_its_Retailer_detail_Pojo> response) {
                        if (isPdHide) {
                            hideProgressDialog();
                        }
                        try {
                            Get_Distributor_and_its_Retailer_detail_Pojo get_distributor_and_its_retailer_detail_pojo = response.body();
                            if (response.isSuccessful() && get_distributor_and_its_retailer_detail_pojo != null &&
                                    get_distributor_and_its_retailer_detail_pojo.getRECORDS().size() > 0) {
                                customerNameArrayList = new ArrayList<>();
                                customerIdArrayList = new ArrayList<>();
                                customerNameArrayList.add(0, "Select Customer");
                                customerIdArrayList.add(0, "0");
                                distributorAndRetailerRecordHashMap = new HashMap<>();
                                for (int i = 0; i < get_distributor_and_its_retailer_detail_pojo.getRECORDS().size(); i++) {
                                    Get_Distributor_and_its_Retailer_detail_Pojo.RECORD record = get_distributor_and_its_retailer_detail_pojo.getRECORDS().get(i);
                                    if (!TextUtils.isEmpty(record.getCusName()) &&
                                            record.getId() != null) {
                                        customerNameArrayList.add(record.getCusName().trim());
                                        customerIdArrayList.add(record.getId().toString().trim());
                                        distributorAndRetailerRecordHashMap.put(record.getCusName(), record);
                                    }
                                }
                                ArrayAdapter<String> customerNameAdapter = new ArrayAdapter<String>
                                        (context, R.layout.searchable_spinner_text_view,
                                                customerNameArrayList);
                                customerNameAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                                spCustomer.setAdapter(customerNameAdapter);
                                spCustomer.setTitle("Select Customer");
                                spCustomer.setPositiveButton("Cancel");
                                getAllCityOrTalukaApiCall(false, true);
//                                getAllSalesPersonApiCall(false, false);
                            } else {
                                Toast.makeText(context, "Customer Not Found!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            hideProgressDialog();
                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Get_Distributor_and_its_Retailer_detail_Pojo> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void getAllSalesPersonApiCall(boolean isPdShow, final boolean isPdHide) {
//        if (isPdShow) {
//            showProgressDialog();
//        }
//        ApiClient.getAllSalespersonImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
//                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(),
//                Config.ACCESS_KEY, getSharedPref.getCompanyId(), new Callback<Get_All_employee_Pojo>() {
//                    @Override
//                    public void onResponse(Call<Get_All_employee_Pojo> call, Response<Get_All_employee_Pojo> response) {
//                        if (isPdHide) {
//                            hideProgressDialog();
//                        }
//                        try {
//                            Get_All_employee_Pojo get_all_employee_pojo = response.body();
//                            if (response.isSuccessful() &&
//                                    get_all_employee_pojo != null && get_all_employee_pojo.getRECORDS().size() > 0) {
//                                allEmployeeArrayList = new ArrayList<>();
//                                allEmployeeArrayList.add(0, "Select sales person");
////                                llSalesPerson.setVisibility(View.VISIBLE);
//                                getAllEmployeeRecordArrayListHashMap = new HashMap<>();
//                                for (int i = 0; i < get_all_employee_pojo.getRECORDS().size(); i++) {
//                                    Get_All_employee_Pojo.RECORDSBean recordsBean = get_all_employee_pojo.getRECORDS().get(i);
//                                    allEmployeeArrayList.add(recordsBean.getEmp_name().trim());
//                                    getAllEmployeeRecordArrayListHashMap.put(recordsBean.getEmp_name(), recordsBean);
//                                }
//                                ArrayAdapter<String> employeeNameAdapter = new ArrayAdapter<String>
//                                        (context, R.layout.searchable_spinner_text_view,
//                                                allEmployeeArrayList);
//                                employeeNameAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
//                                spSalesPerson.setAdapter(employeeNameAdapter);
//                                spSalesPerson.setTitle("Select sales person");
//                                spSalesPerson.setPositiveButton("Cancel");
//
//                            } else {
////                                llSalesPerson.setVisibility(View.GONE);
//                                hideProgressDialog();
//                            }
//                        } catch (Exception ex) {
////                            llSalesPerson.setVisibility(View.GONE);
//                            hideProgressDialog();
//                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Get_All_employee_Pojo> call, Throwable t) {
////                        llSalesPerson.setVisibility(View.GONE);
//                        hideProgressDialog();
//                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    private void getSaleOrderConsigneeApiCall(boolean isPdShow, final boolean isPdHide,
                                              final int cus_id, final String del_date,
                                              final String som_id,
                                              final boolean isFlavourSizeApiCall) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiClient.getSaleOrderConsigneeImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(),
                Config.ACCESS_KEY, getSharedPref.getCompanyId(), String.valueOf(cus_id), new Callback<Get_Sale_Order_Consignee_Pojo>() {
                    @Override
                    public void onResponse(Call<Get_Sale_Order_Consignee_Pojo> call, Response<Get_Sale_Order_Consignee_Pojo> response) {
                        if (isPdHide) {
                            hideProgressDialog();
                        }

                        try {
                            Get_Sale_Order_Consignee_Pojo get_sale_order_consignee_pojo = response.body();
                            if (response.isSuccessful() && get_sale_order_consignee_pojo != null &&
                                    get_sale_order_consignee_pojo.getRECORDS().size() > 0) {
                                salesOrderConsigneeArrayList = new ArrayList<>();
                                salesOrderConsigneeArrayList.add(0, "Select address title");
                                salesOrderConsigneeHashMap = new HashMap<>();
                                llAddressTitle.setVisibility(View.VISIBLE);
                                for (int i = 0; i < get_sale_order_consignee_pojo.getRECORDS().size(); i++) {
                                    Get_Sale_Order_Consignee_Pojo.RECORD record = get_sale_order_consignee_pojo.getRECORDS().get(i);
                                    salesOrderConsigneeArrayList.add(record.getConsigneeName().trim());
                                    salesOrderConsigneeHashMap.put(record.getConsigneeName().trim(), record);
                                }
                                ArrayAdapter<String> consigneeNameAdapter = new ArrayAdapter<String>
                                        (context, R.layout.searchable_spinner_text_view,
                                                salesOrderConsigneeArrayList);
                                consigneeNameAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                                spDelAddressTitle.setAdapter(consigneeNameAdapter);
                                spDelAddressTitle.setTitle("Select address title");
                                spDelAddressTitle.setPositiveButton("Cancel");
                                if (isFlavourSizeApiCall) {
                                    getSizeFlavourWiseItemsApiCall(false, true, del_date, cus_id + "", som_id);
                                }
//                                getSalesOrderListOnCustomerDate(false, true, cus_id, date);
                            } else {
                                llAddressTitle.setVisibility(View.GONE);
                                if (isFlavourSizeApiCall) {
                                    getSizeFlavourWiseItemsApiCall(false, true, del_date, cus_id + "", som_id);
                                }
                            }
                        } catch (Exception ex) {
                            llAddressTitle.setVisibility(View.GONE);
                            hideProgressDialog();
                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Get_Sale_Order_Consignee_Pojo> call, Throwable t) {
                        hideProgressDialog();
                        llAddressTitle.setVisibility(View.GONE);
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void getSalesOrderListOnCustomerDate(boolean isPdShow, final boolean isPdHide, int cus_id, String date) {
//        if (isPdShow) {
//            showProgressDialog();
//        }
//        ApiClient.getSalesOrderListOnCustomerDateImplementer(getSharedPref.getAppVersionCode(),
//                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(),
//                Config.ACCESS_KEY, getSharedPref.getCompanyId(), String.valueOf(cus_id), date, new Callback<Get_Sales_Order_List_Pojo>() {
//                    @Override
//                    public void onResponse(Call<Get_Sales_Order_List_Pojo> call, Response<Get_Sales_Order_List_Pojo> response) {
//                        if (isPdHide) {
//                            hideProgressDialog();
//                        }
//                        try {
//                            Get_Sales_Order_List_Pojo get_sales_order_list_pojo = response.body();
//                            if (response.isSuccessful() &&
//                                    get_sales_order_list_pojo != null &&
//                                    get_sales_order_list_pojo.getRECORDS().size() > 0) {
//                                salesOrderArrayList = new ArrayList<>();
//                                salesOrderHashMap = new HashMap<>();
//                                for (int i = 0; i < get_sales_order_list_pojo.getRECORDS().size();i++){
//                                    Get_Sales_Order_List_Pojo.RECORD record = get_sales_order_list_pojo.getRECORDS().get(i);
//                                    salesOrderArrayList.add(record.getSomNo());
//                                    salesOrderHashMap.put(record.getSomNo(),record);
//                                }
//                                ArrayAdapter<String> salesOrderAdapter = new ArrayAdapter<String>
//                                        (context, R.layout.searchable_spinner_text_view,
//                                                salesOrderArrayList);
//                                salesOrderAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
//                                spOrderList.setAdapter(salesOrderAdapter);
//                                spOrderList.setTitle("Select sales order");
//                                spOrderList.setPositiveButton("Cancel");
//                            } else {
//                                Toast.makeText(context, "No Record Found!", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception ex) {
//                            hideProgressDialog();
//                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Get_Sales_Order_List_Pojo> call, Throwable t) {
//                        hideProgressDialog();
//                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    private void getAllCityOrTalukaApiCall(boolean isPdShow, final boolean isPdHide) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiClient.getAllCityTalukaImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(),
                Config.ACCESS_KEY, getSharedPref.getCompanyId(), new Callback<City_State_Taluka_CountryPojo>() {
                    @Override
                    public void onResponse(Call<City_State_Taluka_CountryPojo> call, Response<City_State_Taluka_CountryPojo> response) {
                        if (isPdHide) {
                            hideProgressDialog();
                        }
                        try {
                            City_State_Taluka_CountryPojo city_state_taluka_countryPojo = response.body();
                            if (response.isSuccessful() &&
                                    city_state_taluka_countryPojo != null &&
                                    city_state_taluka_countryPojo.getRECORDS().size() > 0) {
                                cityAndStateArrayList = new ArrayList<>();
                                cityAndStateHashMap = new HashMap<>();
                                cityAndStateArrayList.add("Select City");
                                for (int i = 0; i < city_state_taluka_countryPojo.getRECORDS().size(); i++) {
                                    City_State_Taluka_CountryPojo.RECORDSBean recordsBean = city_state_taluka_countryPojo.getRECORDS().get(i);
                                    cityAndStateArrayList.add(recordsBean.getCity_Name().trim());
                                    cityAndStateHashMap.put(recordsBean.getCity_Name().trim(), recordsBean);
                                }
                                ArrayAdapter<String> cityOrState = new ArrayAdapter<String>
                                        (context, R.layout.searchable_spinner_text_view,
                                                cityAndStateArrayList);
                                cityOrState.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                                spCity.setAdapter(cityOrState);
                                spCity.setTitle("Select City");
                                spCity.setPositiveButton("Cancel");
                                // if (isOrder) {

                                System.out.println(customerIdArrayList);

                                String loggedInCusId = getSharedPref.getLoginCustomerId() + "";

                                for (int i = 0; i < customerIdArrayList.size(); i++) {
                                    if (customerIdArrayList.get(i).equals(loggedInCusId)) {
                                        System.out.println("name----------------------------------------" + customerIdArrayList.get(i));
                                        spCustomer.setSelection(i);

                                        break;
                                    }

                                    //

                                }


                                // }

                            } else {
                                hideProgressDialog();
                            }

                        } catch (Exception ex) {
                            hideProgressDialog();
                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<City_State_Taluka_CountryPojo> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void getSizeFlavourWiseItemsApiCall(boolean isPdShow, final boolean isPdHide,
                                                String del_date, String cus_id, String som_id) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiClient.getSizeFlavourWiseAllItemsDetailsImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(),
                Config.ACCESS_KEY, getSharedPref.getCompanyId(), del_date, cus_id, som_id, new Callback<Get_Size_Flavour_Wise_All_Items_Detail_Pojo>() {
                    @Override
                    public void onResponse(Call<Get_Size_Flavour_Wise_All_Items_Detail_Pojo> call, Response<Get_Size_Flavour_Wise_All_Items_Detail_Pojo> response) {
                        if (isPdHide) {
                            hideProgressDialog();
                        }
                        ArrayList<Get_Size_Flavour_Wise_All_Items_Detail_Pojo.RECORD> flavourWiseAllItems = new ArrayList<>();
                        itemDetailsJasonReqModelArrayListFinal = new ArrayList<>();
                        try {
                            Get_Size_Flavour_Wise_All_Items_Detail_Pojo get_size_flavour_wise_all_items_detail_pojo = response.body();
                            if (response.isSuccessful() &&
                                    get_size_flavour_wise_all_items_detail_pojo != null &&
                                    get_size_flavour_wise_all_items_detail_pojo.getRECORDS().size() > 0) {
                                cvProductDetails.setVisibility(View.VISIBLE);
                                flavourWiseAllItems = (ArrayList<Get_Size_Flavour_Wise_All_Items_Detail_Pojo.RECORD>) get_size_flavour_wise_all_items_detail_pojo.getRECORDS();
                                cvProductHeader.setVisibility(View.VISIBLE);
                                for (int i = 0; i < get_size_flavour_wise_all_items_detail_pojo.getRECORDS().size(); i++) {
                                    ItemDetailsJasonReqModel itemDetailsJasonReqModel = new ItemDetailsJasonReqModel();
                                    Get_Size_Flavour_Wise_All_Items_Detail_Pojo.RECORD record = get_size_flavour_wise_all_items_detail_pojo.getRECORDS().get(i);

                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getFlavour())) {
                                        itemDetailsJasonReqModel.setFlavour(record.getFlavour());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getSize())) {
                                        itemDetailsJasonReqModel.setSize(record.getSize());
                                    }

                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getItemId())) {
                                        itemDetailsJasonReqModel.setItem_id(record.getItemId().toString());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getItemName())) {
                                        itemDetailsJasonReqModel.setItem_name(record.getItemName());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getQty())) {
                                        itemDetailsJasonReqModel.setQty(record.getQty().toString());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getDiscAmt())) {
                                        itemDetailsJasonReqModel.setDisc_amt(record.getDiscAmt());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getDiscPer())) {
                                        itemDetailsJasonReqModel.setDisc_per(record.getDiscPer());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getBasicPrice())) {
                                        itemDetailsJasonReqModel.setBasic_price(record.getBasicPrice());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getPrice())) {
                                        itemDetailsJasonReqModel.setPrice(record.getPrice().toString());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getStUomId())) {
                                        itemDetailsJasonReqModel.setSt_uom_id(record.getStUomId().toString());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getHsnCode())) {
                                        itemDetailsJasonReqModel.setHsn_code(record.getHsnCode());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getGstType())) {
                                        itemDetailsJasonReqModel.setGst_type(record.getGstType().toString());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getGstPer())) {
                                        itemDetailsJasonReqModel.setGst_per(record.getGstPer().toString());
                                    }
                                    if (!CommonUtils.checkIsEmptyOrNullCommon(record.getCessPer())) {
                                        itemDetailsJasonReqModel.setCess_per(record.getCessPer().toString());
                                    }
                                    itemDetailsJasonReqModelArrayListFinal.add(itemDetailsJasonReqModel);
                                }


                                boxOrderListForAdapter = new BoxOrderListForAdapter(context, flavourWiseAllItems,
                                        itemDetailsJasonReqModelArrayListFinal, new BoxOrderListForAdapter.ITotalProduct() {
                                    @Override
                                    public void getTotalOrder(ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModels) {
                                        itemDetailsJasonReqModelArrayListFinal = itemDetailsJasonReqModels;
                                    }
                                });
                                rvProductList.setAdapter(boxOrderListForAdapter);

                            } else {
                                cvProductDetails.setVisibility(View.GONE);
                                hideProgressDialog();
                                cvProductHeader.setVisibility(View.GONE);
                                llProductDetails.setVisibility(View.GONE);
                                llTotal.setVisibility(View.GONE);

                                Toast.makeText(context, "Product Not Found!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            cvProductDetails.setVisibility(View.GONE);
                            cvProductHeader.setVisibility(View.GONE);
                            llProductDetails.setVisibility(View.GONE);
                            llTotal.setVisibility(View.GONE);
                            hideProgressDialog();
                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Get_Size_Flavour_Wise_All_Items_Detail_Pojo> call, Throwable t) {
                        hideProgressDialog();
                        cvProductDetails.setVisibility(View.GONE);
                        cvProductHeader.setVisibility(View.GONE);
                        llProductDetails.setVisibility(View.GONE);
                        llTotal.setVisibility(View.GONE);
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showProgressDialog() {
        if (!((Activity) context).isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (!((Activity) context).isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }

    private void insertUpdateRespectiveSalesOrder(final InsertRespectiveSalesOrderReqModel insertRespectiveSalesOrderReqModel,
                                                  final String productInfo) {
        showProgressDialog();
        ApiClient.insertUpdaterespecticeSalesOrderImplementer(insertRespectiveSalesOrderReqModel, new Callback<InsertRespectiveResponsePojo>() {
            @Override
            public void onResponse(Call<InsertRespectiveResponsePojo> call, Response<InsertRespectiveResponsePojo> response) {
                hideProgressDialog();
                try {
                    InsertRespectiveResponsePojo insertRespectiveResponsePojo = response.body();
                    if (response.isSuccessful() &&
                            insertRespectiveResponsePojo != null) {
                        Toast.makeText(context, insertRespectiveResponsePojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        System.out.println(response);
                        openPdfFromBase64(insertRespectiveResponsePojo.getDocumentFile(), insertRespectiveSalesOrderReqModel.getCus_name(), insertRespectiveSalesOrderReqModel.getDelivery_date());
                        /*generatePDFOfRetailerManagement(
                                insertRespectiveSalesOrderReqModel.getCus_name(),
                                insertRespectiveSalesOrderReqModel.getDelivery_date(),
                                insertRespectiveSalesOrderReqModel.getDel_add_title(),
                                insertRespectiveSalesOrderReqModel.getDel_cus_name(),
                                insertRespectiveSalesOrderReqModel.getDel_address1(),
                                insertRespectiveSalesOrderReqModel.getDel_address2(),
                                insertRespectiveSalesOrderReqModel.getDel_address3(),
                                insertRespectiveSalesOrderReqModel.getDel_cit_name(),
                                insertRespectiveSalesOrderReqModel.getDel_state_name(),
                                insertRespectiveSalesOrderReqModel.getDel_pan_no(),
                                insertRespectiveSalesOrderReqModel.getDel_GSTIN(),
                                insertRespectiveSalesOrderReqModel.getDel_pincode(),
                                insertRespectiveSalesOrderReqModel.getDel_contact_person(),
                                insertRespectiveSalesOrderReqModel.getDel_contact_no(),
                                insertRespectiveSalesOrderReqModel.getSales_person_name(),
                                insertRespectiveSalesOrderReqModel.getRemarks(),
                                productInfo);*/


                    } else {
                        Toast.makeText(context, "Some thing went wrong please try again later", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertRespectiveResponsePojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void generatePDFOfRetailerManagement(String customer, String deliveryDate,
                                                 String delAddressTitle, String consigneeName, String ad1,
                                                 String ad2, String ad3, String cityName, String stateName,
                                                 String panNo, String GSTIN, String pinCode, String contactPerson,
                                                 String contactNo, String salesPerson, String remarks,
                                                 String products) {

        String isOtherDeliveryDate = cbOtherDeliveryAddress.isChecked() ? "Yes" : "No";

        String retailerManagementPDFString = "Customer:- " + customer + "\n\n" + "Delivery Date:- " + deliveryDate;

        if (cbOtherDeliveryAddress.isChecked()) {
            retailerManagementPDFString += "\n\n" +
                    "Other Delivery Address:- " + isOtherDeliveryDate + "\n\n" + "Del. Address Title:- " + delAddressTitle + "\n\n" +
                    "Consignee Name:- " + consigneeName + "\n\n" + "Address1:- " + ad1 + "\n\n" + "Address2:- " + ad2 + "\n\n" +
                    "Address3:- " + ad3 + "\n\n" + "City Name:- " + cityName + "\n\n" + "State Name:- " + stateName + "\n\n" +
                    "PAN No.:- " + panNo + "\n\n" + "GSTIN:- " + GSTIN + "\n\n" + "Pin Code:- " + pinCode + "\n\n" +
                    "Contact Person:- " + contactPerson + "\n\n" + "Contact No.:- " + contactNo + "\n\n";
        }
        retailerManagementPDFString += "Sales Person:- " + salesPerson + "\n\n" +
                "Remarks:- " + remarks + "\n\n";


        Document document;

        String extension = ".pdf";

        try {

            document = new Document();
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/OrderPlaceToCompany/");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String file_name = customer + "_" + deliveryDate + "_" + System.currentTimeMillis();
            File file = new File(folder, file_name + ".pdf");
            file.createNewFile();

            FontSelector selector = new FontSelector();
            Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
            f1.setColor(BaseColor.BLUE);
            selector.addFont(f1);

            FontSelector selector1 = new FontSelector();
            Font f2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
            f2.setColor(BaseColor.BLUE);
            selector1.addFont(f2);

            Phrase ph1 = selector.process(retailerManagementPDFString);
            Phrase ph2 = selector1.process(products);

            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph(ph1));
            document.add(new Paragraph(ph2));

            //open file/////
            File file1 = new File(file.getAbsolutePath());
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file1);

            getActivity().grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent intent = null;
            if (extension.compareToIgnoreCase(".pdf") == 0 || extension.compareToIgnoreCase("pdf") == 0) {
                target.setDataAndType(uri, "application/pdf");
            }
            intent = Intent.createChooser(target, "Open File");
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "No Apps can performs This action", Toast.LENGTH_SHORT).show();
            }
            document.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * added on 26-10-2020 by harsh
     **/
    private void openPdfFromBase64(String baser64String, String customer, String deliveryDate) {


        String extension = ".pdf";

        try {


            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/OrderPlaceToCompany/");
            if (!folder.exists()) {
                folder.mkdirs();
            }


            String file_name = customer + "_" + deliveryDate + "_" + System.currentTimeMillis();
            File file = new File(folder, file_name + ".pdf");
            file.createNewFile();
            FileOutputStream output = new FileOutputStream(file);
            // extension = file.toString().substring(file.toString().lastIndexOf("."), file.toString().length());
            System.out.println("hi" + extension);
            byte[] pdfAsBytes = Base64.decode(baser64String, Base64.NO_WRAP);
            output.write(pdfAsBytes);
            output.flush();
            // closing streams
            output.close();//

            //open file/////
            File file1 = new File(file.getAbsolutePath());
            System.out.println(file1);
            Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", file1);

            getActivity().grantUriPermission(getActivity().getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent intent = null;
            if (extension.compareToIgnoreCase(".pdf") == 0 || extension.compareToIgnoreCase("pdf") == 0) {
                target.setDataAndType(uri, "application/pdf");
            }
            intent = Intent.createChooser(target, "Open File");
            try {
                getActivity().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // DialogUtils.Show_Toast(ctx, "No Apps can performs This action");
            }


            //open file/////


//                            final File dwldsPath = new File("testing_pdf_file" + ".pdf");
//                            String filePath = PerFormInvoiceAndLedgerActivity.this.getFilesDir().getPath().toString() + dwldsPath;
//
//                            File f = new File(filePath);
//                            byte[] pdfAsBytes = Base64.decode(baser64String, Base64.NO_WRAP);
//                            FileOutputStream os;
//                            os = new FileOutputStream(dwldsPath, false);
//                            os.write(pdfAsBytes);
//                            os.flush();
//                            os.close();

//                             FileOutputStream fos = new FileOutputStream(dwldsPath);
//                             fos.write(Base64.decode(base64String, Base64.NO_WRAP));
//                             fos.close();//try karo run kari n
            //exception awe che read only filesystem and file save nathi thati


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Toast.makeText(getActivity(), "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
