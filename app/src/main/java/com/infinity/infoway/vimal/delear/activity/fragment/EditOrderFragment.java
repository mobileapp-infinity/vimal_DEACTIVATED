package com.infinity.infoway.vimal.delear.activity.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.api.response.City_State_Taluka_CountryPojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.EditOrderAdapter;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_All_employee_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Distributor_and_its_Retailer_detail_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Sale_Order_Consignee_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Sales_Order_List_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Size_Flavour_Wise_All_Items_Detail_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.InsertRespectiveResponsePojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.InsertRespectiveSalesOrderReqModel;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.ItemCategoryAdapter;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.ItemCategoryPojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.ItemDetailsJasonReqModel;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.ItemDetailsPojo;
import com.infinity.infoway.vimal.delear.util.CommonUtils;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditOrderFragment extends Fragment implements View.OnClickListener {

    private Activity context;
    private SharedPref getSharedPref;
    private ProgressDialog progDialog;
    private ArrayList<String> customerIdArrayList = new ArrayList<>();
    private SearchableSpinner spCustomerEdit;
    private EditText edtDeliveryDateEdit;
    private SearchableSpinner spOrderEdit;
    private RecyclerView rvEditOrderList;
    private LinearLayout llOrderList;
    private ArrayList<String> customerNameArrayList;
    private HashMap<String, Get_Distributor_and_its_Retailer_detail_Pojo.RECORD> customerRecordHashMap;
    private ArrayList<String> orderArrayList;
    private HashMap<String, Get_Sales_Order_List_Pojo.RECORD> orderHashMap;
    private Calendar my_calendar = Calendar.getInstance();
    private EditOrderAdapter editOrderAdapter;
    private ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModelArrayListFinal;
    private Button btnSubmitEditedOrder;
    private ArrayList<String> allEmployeeArrayList;
    private HashMap<String, Get_All_employee_Pojo.RECORDSBean> getAllEmployeeRecordArrayListHashMap;
    private ArrayList<String> salesOrderConsigneeArrayList;
    private HashMap<String, Get_Sale_Order_Consignee_Pojo.RECORD> salesOrderConsigneeHashMap;
    private ArrayList<String> cityAndStateArrayList;
    private HashMap<String, City_State_Taluka_CountryPojo.RECORDSBean> cityAndStateHashMap;

    final DatePickerDialog.OnDateSetListener to_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            my_calendar.set(Calendar.YEAR, year);
            my_calendar.set(Calendar.MONTH, monthOfYear);
            my_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            edtDeliveryDateEdit.setText(sdf.format(my_calendar.getTime()));

            if (spCustomerEdit.getSelectedItemPosition() > 0 &&
                    !TextUtils.isEmpty(edtDeliveryDateEdit.getText().toString())) {
                Get_Distributor_and_its_Retailer_detail_Pojo.RECORD record =
                        customerRecordHashMap.get(customerNameArrayList.get(spCustomerEdit.getSelectedItemPosition()).trim());
                getSalesOrderListOnCustomerDate(true, true, record.getId(),
                        edtDeliveryDateEdit.getText().toString());
            }
        }
    };


    //For Dialog
    AlertDialog productInfoDialog;
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
    //    private Button btnSubmitOrderForDialog;
    private AppCompatButton btn_submit_order, btn_cancel_order;
    private LinearLayout llDelAddressEditOrder;
//    private LinearLayout llSalesPersonEditDialog;

    AlertDialog.Builder dialogBuilder;
    LayoutInflater layoutInflaterProductInfo;
    View dialogViewProductInfo;

    LinearLayout llOrderSpinner;

    private String som_id_for_edit_product = "";

    private String salesPerson = "";
    private String remarks = "";


    public EditOrderFragment() {
    }

    private RecyclerView rvItemCategory;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
        dialogBuilder = new AlertDialog.Builder(context);
        layoutInflaterProductInfo = LayoutInflater.from(context);
        dialogViewProductInfo =
                layoutInflaterProductInfo.inflate(R.layout.layout_for_edit_order, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_place_to_company_report, container, false);

        initView(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getItemCategoryKey();

        }
    }

    private void initView(View view) {

        rvItemCategory = view.findViewById(R.id.rvItemCategory);
        rvItemCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        getSharedPref = new SharedPref(context);
        progDialog = new ProgressDialog(context);
        llOrderList = view.findViewById(R.id.llOrderList);
        btnSubmitEditedOrder = view.findViewById(R.id.btnSubmitEditedOrder);
        btnSubmitEditedOrder.setOnClickListener(this);
        llOrderSpinner = view.findViewById(R.id.llOrderSpinner);
        spCustomerEdit = view.findViewById(R.id.spCustomerEdit);
        spCustomerEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Get_Distributor_and_its_Retailer_detail_Pojo.RECORD record = customerRecordHashMap.get(customerNameArrayList.get(position).trim());
                    customerID = record.getId() + "";
                    if (!TextUtils.isEmpty(edtDeliveryDateEdit.getText().toString())) {


                        getSalesOrderListOnCustomerDate(true, true, record.getId(),
                                edtDeliveryDateEdit.getText().toString());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edtDeliveryDateEdit = view.findViewById(R.id.edtDeliveryDateEdit);
        edtDeliveryDateEdit.setOnClickListener(this);
        spOrderEdit = view.findViewById(R.id.spOrderEdit);
        spOrderEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Get_Sales_Order_List_Pojo.RECORD record = orderHashMap.get(orderArrayList.get(position));
                    som_id_for_edit_product = record.getSomId().toString();
                   /* getSizeFlavourWiseItemsApiCall(true, true, edtDeliveryDateEdit.getText().toString(),
                            record.getCusId().toString(), record.getSomId().toString());*/

                    Get_All_Items_Detail_For_Sales_Order(true,true,edtDeliveryDateEdit.getText().toString(),customerID,record.getSomId().toString(),selectedCategoryId);

                    String consigneeName = CommonUtils.checkIsEmptyOrNullCommon(record.getDelCusName()) ? "" : record.getDelCusName();
                    String add1 = CommonUtils.checkIsEmptyOrNullCommon(record.getDelAddress1()) ? "" : record.getDelAddress1();
                    String add2 = CommonUtils.checkIsEmptyOrNullCommon(record.getDelAddress2()) ? "" : record.getDelAddress2();
                    String add3 = CommonUtils.checkIsEmptyOrNullCommon(record.getDelAddress3()) ? "" : record.getDelAddress3().toString();
                    String cityName = CommonUtils.checkIsEmptyOrNullCommon(record.getDelCitName()) ? "" : record.getDelCitName();
                    String stateName = CommonUtils.checkIsEmptyOrNullCommon(record.getDelStateName()) ? "" : record.getDelStateName();
                    String panNo = CommonUtils.checkIsEmptyOrNullCommon(record.getDelPanNo()) ? " " : record.getDelPanNo().toString();
                    String GSTIN = CommonUtils.checkIsEmptyOrNullCommon(record.getDelGSTIN()) ? "" : record.getDelGSTIN().toString();
                    String pinCode = CommonUtils.checkIsEmptyOrNullCommon(record.getDelPincode()) ? "" : record.getDelPincode().toString();
                    String contactPerson = CommonUtils.checkIsEmptyOrNullCommon(record.getDelContactPerson()) ? "" : record.getDelContactPerson().toString();
                    String mobileNo = CommonUtils.checkIsEmptyOrNullCommon(record.getDelContactNo()) ? "" : record.getDelContactNo().toString();

                    salesPerson = CommonUtils.checkIsEmptyOrNullCommon(record.getSalesPersonName()) ? "" : record.getSalesPersonName().toString();
                    remarks = CommonUtils.checkIsEmptyOrNullCommon(record.getRemarks()) ? "" : record.getRemarks();

                    setData(consigneeName, add1, add2, add3, cityName,
                            stateName, panNo, GSTIN, pinCode,
                            contactPerson, mobileNo, true);
                } else {
                    salesPerson = "";
                    remarks = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rvEditOrderList = view.findViewById(R.id.rvEditOrderList);
        initViewForDialog();
    }

    private void initViewForDialog() {


//        llSalesPersonEditDialog = dialogViewProductInfo.findViewById(R.id.llSalesPersonEditDialog);
        llDelAddressEditOrder = dialogViewProductInfo.findViewById(R.id.llDelAddressEditOrder);

        spDelAddressTitle = dialogViewProductInfo.findViewById(R.id.spDelAddressTitle);
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
                            contactPerson, mobileNo, false);

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
        edtConsigneeName = dialogViewProductInfo.findViewById(R.id.edtConsigneeName);
        edtAddress1 = dialogViewProductInfo.findViewById(R.id.edtAddress1);
        edtAddress2 = dialogViewProductInfo.findViewById(R.id.edtAddress2);
        edtAddress3 = dialogViewProductInfo.findViewById(R.id.edtAddress3);
        edtState = dialogViewProductInfo.findViewById(R.id.edtState);
        edtPANno = dialogViewProductInfo.findViewById(R.id.edtPANno);
        edtGSTIN = dialogViewProductInfo.findViewById(R.id.edtGSTIN);
        edtPinCode = dialogViewProductInfo.findViewById(R.id.edtPinCode);
        edtContactPerson = dialogViewProductInfo.findViewById(R.id.edtContactPerson);
        edtContactNo = dialogViewProductInfo.findViewById(R.id.edtContactNo);

        llOrderDeliveryAddress = dialogViewProductInfo.findViewById(R.id.llOrderDeliveryAddress);
//        spSalesPerson = dialogViewProductInfo.findViewById(R.id.spSalesPerson);
        edtRemarks = dialogViewProductInfo.findViewById(R.id.edtRemarks);
        btn_submit_order = dialogViewProductInfo.findViewById(R.id.btn_submit_order);
        btn_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!CommonUtils.checkIsEmptyOrNullCommon(som_id_for_edit_product) &&
                        Integer.parseInt(som_id_for_edit_product) > 0) {
                    Get_Distributor_and_its_Retailer_detail_Pojo.RECORD record =
                            customerRecordHashMap.get(customerNameArrayList.get(spCustomerEdit.getSelectedItemPosition()).trim());

                    String app_version = String.valueOf(getSharedPref.getAppVersionCode());
                    String android_id = getSharedPref.getAppAndroidId();
                    String device_id = String.valueOf(getSharedPref.getRegisteredId());
                    String user_id = getSharedPref.getRegisteredUserId();
                    String key = Config.ACCESS_KEY;
                    String comp_id = getSharedPref.getCompanyId();
                    String cus_name = customerNameArrayList.get(spCustomerEdit.getSelectedItemPosition());
                    String delivery_date = edtDeliveryDateEdit.getText().toString();
                    String som_id = som_id_for_edit_product;
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

//                    String sales_person_name = "";
//                    if (allEmployeeArrayList != null &&
//                            allEmployeeArrayList.size() > 0) {
//                        sales_person_name = spSalesPerson.getSelectedItemPosition() == 0 ? "" : allEmployeeArrayList.get(spSalesPerson.getSelectedItemPosition());
//                    }
                    String remarks = edtRemarks.getText().toString();

                    JSONArray jsonArray = new JSONArray();

                    if (itemDetailsJasonReqModelArrayListFinal != null &&
                            itemDetailsJasonReqModelArrayListFinal.size() > 0) {

                        for (int i = 0; i < itemDetailsJasonReqModelArrayListFinal.size(); i++) {
                            if (itemDetailsJasonReqModelArrayListFinal.get(i).isEdited()) {
                                JSONObject jsonObject = new JSONObject();
                                try {
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

                    insertUpdateRespectiveSalesOrder(insertRespectiveSalesOrderReqModel);
                } else {
                    Toast.makeText(context, "Order Id Not Found", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_cancel_order = dialogViewProductInfo.findViewById(R.id.btn_cancel_order);
        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productInfoDialog != null) {
                    productInfoDialog.dismiss();
                }
            }
        });


        cbOtherDeliveryAddress = dialogViewProductInfo.findViewById(R.id.cbOtherDeliveryAddress);
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
        spCity = dialogViewProductInfo.findViewById(R.id.spCity);
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

    private void setData(String consigneeName, String address1, String address2, String address3,
                         String city, String state, String panNo, String GSTIN,
                         String pinCode, String contactPerson, String contactNo,
                         boolean isEdit) {
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

        if (isEdit && !CommonUtils.checkIsEmptyOrNullCommon(city)) {
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

        if (!CommonUtils.checkIsEmptyOrNullCommon(remarks)) {
            edtRemarks.setText(remarks);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edtDeliveryDateEdit) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, to_date, my_calendar
                    .get(Calendar.YEAR), my_calendar.get(Calendar.MONTH),
                    my_calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.show();
        } else if (v.getId() == R.id.btnSubmitEditedOrder) {
            if (isValid()) {

                if (dialogViewProductInfo.getParent() != null) {
                    ((ViewGroup) dialogViewProductInfo.getParent()).removeView(dialogViewProductInfo); // <- fix
                }
                dialogBuilder.setView(dialogViewProductInfo);
                productInfoDialog = dialogBuilder.create();
//                productInfoDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                productInfoDialog.show();
                Get_Distributor_and_its_Retailer_detail_Pojo.RECORD record = customerRecordHashMap.get(customerNameArrayList.get(spCustomerEdit.getSelectedItemPosition()).trim());
                getSaleOrderConsigneeApiCall(true, true, record.getId());
            }
        }
    }

    private boolean isValid() {
        boolean isValid = true;
        if (spCustomerEdit.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please select customer", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (CommonUtils.checkIsEmptyOrNullCommon(edtDeliveryDateEdit.getText().toString())) {
            Toast.makeText(context, "Please enter delivery date", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (spOrderEdit.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please select order", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
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
                                customerRecordHashMap = new HashMap<>();
                                for (int i = 0; i < get_distributor_and_its_retailer_detail_pojo.getRECORDS().size(); i++) {
                                    Get_Distributor_and_its_Retailer_detail_Pojo.RECORD record = get_distributor_and_its_retailer_detail_pojo.getRECORDS().get(i);
                                    if (!TextUtils.isEmpty(record.getCusName()) &&
                                            record.getId() != null) {
                                        customerNameArrayList.add(record.getCusName().trim());
                                        customerIdArrayList.add(record.getId().toString().trim());
                                        customerRecordHashMap.put(record.getCusName(), record);
                                    }
                                }
                                ArrayAdapter<String> customerNameAdapter = new ArrayAdapter<String>
                                        (context, R.layout.searchable_spinner_text_view,
                                                customerNameArrayList);
                                customerNameAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                                spCustomerEdit.setAdapter(customerNameAdapter);
                                spCustomerEdit.setTitle("Select Customer");
                                spCustomerEdit.setPositiveButton("Cancel");

                                String loggedInCusId = getSharedPref.getLoginCustomerId() + "";

                                for (int i = 0; i < customerIdArrayList.size(); i++) {
                                    if (customerIdArrayList.get(i).equals(loggedInCusId)) {
                                        System.out.println("name----------------------------------------" + customerIdArrayList.get(i));
                                        spCustomerEdit.setSelection(i);

                                        break;
                                    }

                                    //

                                }
                                getAllCityOrTalukaApiCall(false, true);
//                                getAllSalesPersonApiCall(false, false);
                            } else {
                                hideProgressDialog();
                                Toast.makeText(context, "Customers Not Found!", Toast.LENGTH_SHORT).show();
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


    private void getSalesOrderListOnCustomerDate(boolean isPdShow, final boolean isPdHide, int cus_id, String date) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiClient.getSalesOrderListOnCustomerDateImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(),
                Config.ACCESS_KEY, getSharedPref.getCompanyId(), String.valueOf(cus_id), date, new Callback<Get_Sales_Order_List_Pojo>() {
                    @Override
                    public void onResponse(Call<Get_Sales_Order_List_Pojo> call, Response<Get_Sales_Order_List_Pojo> response) {
                        if (isPdHide) {
                            hideProgressDialog();
                        }
                        try {
                            Get_Sales_Order_List_Pojo get_sales_order_list_pojo = response.body();
                            if (response.isSuccessful() &&
                                    get_sales_order_list_pojo != null &&
                                    get_sales_order_list_pojo.getRECORDS().size() > 0) {
                                llOrderSpinner.setVisibility(View.VISIBLE);
                                orderArrayList = new ArrayList<>();
                                orderHashMap = new HashMap<>();
                                orderArrayList.add(0, "Select sales order");
                                for (int i = 0; i < get_sales_order_list_pojo.getRECORDS().size(); i++) {
                                    Get_Sales_Order_List_Pojo.RECORD record = get_sales_order_list_pojo.getRECORDS().get(i);
                                    orderArrayList.add(record.getSomNo());
                                    orderHashMap.put(record.getSomNo(), record);
                                }
                                ArrayAdapter<String> salesOrderAdapter = new ArrayAdapter<String>
                                        (context, R.layout.searchable_spinner_text_view,
                                                orderArrayList);
                                salesOrderAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                                spOrderEdit.setAdapter(salesOrderAdapter);
                                spOrderEdit.setTitle("Select sales order");
                                spOrderEdit.setPositiveButton("Cancel");
                            } else {
                                llOrderList.setVisibility(View.GONE);
                                llOrderSpinner.setVisibility(View.GONE);
                                Toast.makeText(context, "Orders Not Found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            llOrderList.setVisibility(View.GONE);
                            llOrderSpinner.setVisibility(View.GONE);
                            hideProgressDialog();
                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Get_Sales_Order_List_Pojo> call, Throwable t) {
                        llOrderSpinner.setVisibility(View.GONE);
                        llOrderList.setVisibility(View.GONE);
                        hideProgressDialog();
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


  /*  private void getSizeFlavourWiseItemsApiCall(boolean isPdShow, final boolean isPdHide,
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
                        try {
                            Get_Size_Flavour_Wise_All_Items_Detail_Pojo get_size_flavour_wise_all_items_detail_pojo = response.body();
                            itemDetailsJasonReqModelArrayListFinal = new ArrayList<>();
                            if (response.isSuccessful() &&
                                    get_size_flavour_wise_all_items_detail_pojo != null &&
                                    get_size_flavour_wise_all_items_detail_pojo.getRECORDS().size() > 0) {
                                llOrderList.setVisibility(View.VISIBLE);
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
                                editOrderAdapter = new EditOrderAdapter(context,
                                        (ArrayList<Get_Size_Flavour_Wise_All_Items_Detail_Pojo.RECORD>) get_size_flavour_wise_all_items_detail_pojo.getRECORDS(),
                                        itemDetailsJasonReqModelArrayListFinal,
                                        new EditOrderAdapter.IEditOrder() {
                                            @Override
                                            public void editOrderList(ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModelArrayListFinalEdited) {
                                                itemDetailsJasonReqModelArrayListFinal = itemDetailsJasonReqModelArrayListFinalEdited;
                                            }
                                        });
                                rvEditOrderList.setAdapter(editOrderAdapter);

                            } else {
                                llOrderList.setVisibility(View.GONE);
                                hideProgressDialog();
                                Toast.makeText(context, "Products Not Found!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            llOrderList.setVisibility(View.GONE);
                            hideProgressDialog();
                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Get_Size_Flavour_Wise_All_Items_Detail_Pojo> call, Throwable t) {
                        hideProgressDialog();
                        llOrderList.setVisibility(View.GONE);
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

    private void getSaleOrderConsigneeApiCall(boolean isPdShow, final boolean isPdHide,
                                              final int cus_id) {
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
                                llDelAddressEditOrder.setVisibility(View.VISIBLE);
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

                            } else {
                                llDelAddressEditOrder.setVisibility(View.GONE);
                                hideProgressDialog();
                            }
                        } catch (Exception ex) {
                            llDelAddressEditOrder.setVisibility(View.GONE);
                            hideProgressDialog();
                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Get_Sale_Order_Consignee_Pojo> call, Throwable t) {
                        hideProgressDialog();
                        llDelAddressEditOrder.setVisibility(View.GONE);
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

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
//                                getAllEmployeeRecordArrayListHashMap = new HashMap<>();
////                                llSalesPersonEditDialog.setVisibility(View.VISIBLE);
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
//                                if (!CommonUtils.checkIsEmptyOrNullCommon(salesPerson)) {
//                                    int pos = allEmployeeArrayList.indexOf(salesPerson.trim());
//                                    spSalesPerson.setSelection(pos);
//                                }
//
//
//                                getAllCityOrTalukaApiCall(false, true);
//                            } else {
//                                llSalesPersonEditDialog.setVisibility(View.GONE);
//                                hideProgressDialog();
//                            }
//                        } catch (Exception ex) {
//                            llSalesPersonEditDialog.setVisibility(View.GONE);
//                            hideProgressDialog();
//                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Get_All_employee_Pojo> call, Throwable t) {
//                        llSalesPersonEditDialog.setVisibility(View.GONE);
//                        hideProgressDialog();
//                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }


    private void insertUpdateRespectiveSalesOrder(InsertRespectiveSalesOrderReqModel insertRespectiveSalesOrderReqModel) {
        showProgressDialog();
        ApiClient.insertUpdaterespecticeSalesOrderImplementer(insertRespectiveSalesOrderReqModel, new Callback<InsertRespectiveResponsePojo>() {
            @Override
            public void onResponse(Call<InsertRespectiveResponsePojo> call, Response<InsertRespectiveResponsePojo> response) {
                hideProgressDialog();
                try {
                    if (productInfoDialog != null) {
                        productInfoDialog.dismiss();
                    }
                    InsertRespectiveResponsePojo insertRespectiveResponsePojo = response.body();
                    if (response.isSuccessful() &&
                            insertRespectiveResponsePojo != null) {
                        Toast.makeText(context, insertRespectiveResponsePojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
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
                if (productInfoDialog != null) {
                    productInfoDialog.dismiss();
                }
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


    private void Get_All_Items_Detail_For_Sales_Order(boolean isPdShow, final boolean isPdHide,
                                                String del_date, String cus_id, String som_id,String selectedCaegoryId) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiImplementer.getAllItemsDetailForSalesOrderImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(),
                Config.ACCESS_KEY, getSharedPref.getCompanyId(), del_date, cus_id, som_id, selectedCaegoryId,new Callback<ItemDetailsPojo>() {
                    @Override
                    public void onResponse(Call<ItemDetailsPojo> call, Response<ItemDetailsPojo> response) {
                        if (isPdHide) {
                            hideProgressDialog();
                        }
                        try {
                            //Get_Size_Flavour_Wise_All_Items_Detail_Pojo get_size_flavour_wise_all_items_detail_pojo = response.body();
                            ArrayList<ItemDetailsPojo.Record> allItems = new ArrayList<>();
                            ItemDetailsPojo itemDetailsPojo =response.body();
                            itemDetailsJasonReqModelArrayListFinal = new ArrayList<>();
                            if (response.isSuccessful() &&
                                    itemDetailsPojo != null &&
                                    itemDetailsPojo.getRecords().size() > 0) {
                                llOrderList.setVisibility(View.VISIBLE);
                                for (int i = 0; i < itemDetailsPojo.getRecords().size(); i++) {
                                    ItemDetailsJasonReqModel itemDetailsJasonReqModel = new ItemDetailsJasonReqModel();
                                    ItemDetailsPojo.Record record = itemDetailsPojo.getRecords().get(i);

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
                                editOrderAdapter = new EditOrderAdapter(context,
                                        (ArrayList<ItemDetailsPojo.Record>) itemDetailsPojo.getRecords(),
                                        itemDetailsJasonReqModelArrayListFinal,
                                        new EditOrderAdapter.IEditOrder() {
                                            @Override
                                            public void editOrderList(ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModelArrayListFinalEdited) {
                                                itemDetailsJasonReqModelArrayListFinal = itemDetailsJasonReqModelArrayListFinalEdited;
                                            }
                                        });
                                rvEditOrderList.setAdapter(editOrderAdapter);

                            } else {
                                llOrderList.setVisibility(View.GONE);
                                hideProgressDialog();
                                Toast.makeText(context, "Products Not Found!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            llOrderList.setVisibility(View.GONE);
                            hideProgressDialog();
                            Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ItemDetailsPojo> call, Throwable t) {
                        hideProgressDialog();
                        llOrderList.setVisibility(View.GONE);
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    String customerID,somID;

    private int selectedPostion;
    private String selectedCategoryId;
    private void getItemCategoryKey() {


        ApiImplementer.GetItemCategoryKeyImplementer(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY, getSharedPref.getCompanyId(), new Callback<ItemCategoryPojo>() {
            @Override
            public void onResponse(Call<ItemCategoryPojo> call, Response<ItemCategoryPojo> response) {

                getDiatributorAndRetailerNameApiCall(true, false);

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        ItemCategoryPojo itemCategoryPojo = response.body();

                        if (itemCategoryPojo != null && itemCategoryPojo.getRecords().size() > 0) {

                            ItemCategoryAdapter itemCategoryAdapter = new ItemCategoryAdapter(getActivity(), itemCategoryPojo, new ItemCategoryAdapter.IOnRadioButtonChanged() {
                                @Override
                                public void OnCheckedChaged(int position, ItemCategoryPojo itemCategoryPojo) {
                                    selectedPostion = position;
                                    System.out.println(itemCategoryPojo);
                                    selectedCategoryId = itemCategoryPojo.getRecords().get(position).getParentValue() + "";
                                    if (!edtDeliveryDateEdit.getText().toString().equals("") && !customerID.equals("") && !som_id_for_edit_product.equals("")){
                                        Get_All_Items_Detail_For_Sales_Order(true,true,selectedCategoryId,customerID,som_id_for_edit_product,edtDeliveryDateEdit.getText().toString());
                                        getSalesOrderListOnCustomerDate(true, true, Integer.parseInt(customerID),
                                                edtDeliveryDateEdit.getText().toString());
                                    }


                                }
                            });
                            rvItemCategory.setAdapter(itemCategoryAdapter);

                        } else {
                            Toast.makeText(getActivity(), itemCategoryPojo.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemCategoryPojo> call, Throwable t) {

            }
        });


    }

}
