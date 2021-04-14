package com.infinity.infoway.vimal.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.activity.Vimal_Suspecting_Entry;
import com.infinity.infoway.vimal.activity.Vimal_Suspecting_View_Edit;
import com.infinity.infoway.vimal.api.response.SuspectingReport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Suspending_Basic extends Fragment implements View.OnClickListener {
    private SimpleDateFormat sdf_full, serverDateFormat;
    String selectedFromDateString;

    View view;
    public static EditText et_date;

    public static EditText ed_executive_person;
    public static EditText ed_agency_name;
    public static EditText ed_owner_name;
    public static EditText ed_mobile_no;
    public static EditText ed_area;
    public static EditText ed_city;
    public static EditText ed_taluka;
    public static EditText ed_district, ed_distributor;
    public static EditText ed_state;
    public static EditText ed_pincode;
    public static Button mBtnNxt;
    public static String selected_date = "";
    public static String selected_district_ID = "";
    public static String selected_taluka_ID = "";
    public static String selected_state_Id = "";
    public static String selected_distributor = "";
    public static String selected_EMP_NAME_ID = "";
    public static String selected_EMP_NAME = "";
    public static String selected_distributor_ID = "";

    //    public static String selected_city = "";
    String str_area = "";
    String str_mobile_no = "";
    String str_owner_name = "";
    String str_agency_name = "";
    String str_executive_person = "";
    ScrollView scroll_view;
    SuspectingReport.RECORDSBean dataModel;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.suspecting_basic_deatils, container, false);
        bundle = getArguments();
//        return super.onCreateView(inflater, container, savedInstanceState);
        initView(view);


        return view;
    }

    private void initView(View view) {
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


        et_date = (EditText) view.findViewById(R.id.et_date);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);

        ed_executive_person = (EditText) view.findViewById(R.id.ed_executive_person);
        ed_agency_name = (EditText) view.findViewById(R.id.ed_agency_name);

        ed_owner_name = (EditText) view.findViewById(R.id.ed_owner_name);
        ed_mobile_no = (EditText) view.findViewById(R.id.ed_mobile_no);
        ed_area = (EditText) view.findViewById(R.id.ed_area);
        ed_city = (EditText) view.findViewById(R.id.ed_city);
        ed_pincode = (EditText)view.findViewById(R.id.ed_pincode);
        ed_taluka = (EditText) view.findViewById(R.id.ed_taluka);

        ed_district = (EditText) view.findViewById(R.id.ed_district);
        ed_distributor = (EditText) view.findViewById(R.id.ed_distributor);

        ed_state = (EditText) view.findViewById(R.id.ed_state);

        mBtnNxt = (Button) view.findViewById(R.id.btn_nxt);
        mBtnNxt.setOnClickListener(this);
        System.out.println("this is null!!!!!!!!!!!!!!!");
        ed_agency_name.setOnClickListener(this);
        et_date.setOnClickListener(this);
        ed_city.setOnClickListener(this);
        ed_district.setOnClickListener(this);
        ed_state.setOnClickListener(this);
        ed_taluka.setOnClickListener(this);
        ed_distributor.setOnClickListener(this);
        ed_executive_person.setOnClickListener(this);
        if (bundle != null) {
            dataModel = (SuspectingReport.RECORDSBean) getArguments().getSerializable("data");
            if (dataModel != null) {
                Vimal_Suspecting_View_Edit.to_call_method = true;
                et_date.setText(dataModel.getDate() + "");
                ed_mobile_no.setText(dataModel.getSe_mobile_no() + "");
                ed_owner_name.setText(dataModel.getSe_owner_name() + "");
                ed_agency_name.setText(dataModel.getSe_agency_name() + "");
                ed_area.setText(dataModel.getSe_area() + "");
                ed_taluka.setText(dataModel.getTal_name() + "");
                selected_taluka_ID = dataModel.getSe_taluka_id() + "";

                selected_district_ID = dataModel.getSe_district_id() + "";
                ed_district.setText(dataModel.getDis_name() + "");

                selectedCityId = dataModel.getSe_city_id() + "";
                ed_city.setText(dataModel.getCity_name() + "");

                selected_EMP_NAME_ID = dataModel.getEmp_id() + "";
                ed_executive_person.setText(dataModel.getEmp_name() + "");

                selected_state_Id = dataModel.getSe_state_id() + "";
                ed_state.setText(dataModel.getSta_name() + "");
                //   ed_distributor.setText(dataModel.getdi() + "");

            } else {
//                System.out.println("this is null!!!!!!!!!!!!!!!");
//                ed_agency_name.setOnClickListener(this);
//                ed_city.setOnClickListener(this);
//                ed_district.setOnClickListener(this);
//                ed_state.setOnClickListener(this);
//                ed_taluka.setOnClickListener(this);
            }

        } else {
//            System.out.println("this is null!!!!!!!!!!!!!!!");
//            ed_agency_name.setOnClickListener(this);
//            ed_city.setOnClickListener(this);
//            ed_district.setOnClickListener(this);
//            ed_state.setOnClickListener(this);
//            ed_taluka.setOnClickListener(this);
        }

    }

    private Date selectedFromDate;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 512) {
            if (data.hasExtra("CityId")) {
              /*  if (rg_call.getCheckedRadioButtonId() == R.id.rb_returning_call) {
                    selectedCityNameRegular = data.getExtras().getString("CityName");
                    if (!TextUtils.isEmpty(data.getExtras().getString("CityId"))) {
                        selectedCityIdRegular = data.getExtras().getString("CityId");
                    }
                    if (!TextUtils.isEmpty(selectedCityNameRegular)) {
                        et_select_city_regular.setText(selectedCityNameRegular);
                    }

                } else {*/
                selectedCityName = data.getExtras().getString("CityName");
                if (!TextUtils.isEmpty(data.getExtras().getString("CityId"))) {
                    selectedCityId = data.getExtras().getString("CityId");
                }
                if (!TextUtils.isEmpty(selectedCityName)) {
                    ed_city.setText(selectedCityName);
                    ed_state.setText("");
                    ed_taluka.setText("");
                    ed_district.setText("");
                    selected_taluka_ID = "";
                }

                if (!TextUtils.isEmpty(data.getExtras().getString("StateName"))) {
                    ed_state.setText(data.getExtras().getString("StateName"));
                }
                if (!TextUtils.isEmpty(data.getExtras().getString("State_ID"))) {
                    selected_state_Id = data.getExtras().getString("State_ID");
                }
              //  System.out.println("this is state ID!!!!! "+selected_state_Id+"");
                if (!TextUtils.isEmpty(data.getExtras().getString("TalukaName"))) {
                    ed_taluka.setText(data.getExtras().getString("TalukaName"));
                }
                if (!TextUtils.isEmpty(data.getExtras().getString("TalukaID"))) {
                    selected_taluka_ID = data.getExtras().getString("TalukaID");
                }
                if (!TextUtils.isEmpty(data.getExtras().getString("DisID"))) {
                    selected_district_ID = data.getExtras().getString("DisID");
                    System.out.println("test!!!!!!!!!! data.getExtras().getString(\"DISNAME\") " + data.getExtras().getString("DISNAME") + "");
                    ed_district.setText(data.getExtras().getString("DISNAME"));
                }

                // getStateCountryApiCall(selectedCityId);
//                }

            }
        } else if (resultCode == 513) {
            if (data.hasExtra("ID")) {

                selected_distributor = data.getExtras().getString("CUS_NAME");
                selected_distributor_ID = data.getExtras().getString("ID");
                ed_distributor.setText(selected_distributor + "");
//

            }
        } else if (resultCode == 514) {
            if (data.hasExtra("ID")) {

                selected_EMP_NAME = data.getExtras().getString("EMP_NAME");
                selected_EMP_NAME_ID = data.getExtras().getString("ID");
                ed_executive_person.setText(selected_EMP_NAME + "");
//

            }
        }
    }

    public static boolean VALID_FORM_ONE = false;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            default:
                break;
            case R.id.et_date:
                int mYear = 0, mMonth = 0, mDay = 0;
                final Calendar c = Calendar.getInstance();
                Date result = c.getTime();
                if (selectedFromDate == null) {
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                } else {
                    c.setTimeInMillis(selectedFromDate.getTime());
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        try {
                            StringBuilder theDate = new StringBuilder()
                                    .append(dayOfMonth).append("-")
                                    .append(monthOfYear + 1).append("-")
                                    .append(year);


                            try {
                                selectedFromDate = sdf_full.parse(theDate.toString());

                            } catch (Exception ex) {
                            }

                            selectedFromDateString = serverDateFormat.format(selectedFromDate);
                            et_date.setText(sdf_full.format(selectedFromDate));

                        } catch (Exception ex) {
                            //Log.e("Error",""+ex.getMessage());
                        }
                    }
                }, mYear, mMonth, mDay);
                datePicker.show();


                break;
            case R.id.ed_agency_name:

                break;
            case R.id.ed_city:
                intent = new Intent(getActivity(), Activity_Select_City.class);
//                intent.putExtra("isFromCitySelect", 0);
                intent.putExtra("isFromCitySelect", 5120);
                startActivityForResult(intent, 512);
                break;
           /* case R.id.ed_distributor:
                intent = new Intent(getActivity(), Activity_Select_City.class);
//                intent.putExtra("isFromCitySelect", 0);
                intent.putExtra("isFromCitySelect", 5130);
                startActivityForResult(intent, 513);
                break;*/
            case R.id.ed_executive_person://executive person==emp
                intent = new Intent(getActivity(), Activity_Select_City.class);
//                //intent.putExtra("isFromCitySelect", 0);
                intent.putExtra("isFromCitySelect", 5140);
                startActivityForResult(intent, 514);
                break;


            case R.id.ed_taluka:
                break;
            case R.id.ed_district:

                break;
            case R.id.ed_state:

                break;
            case R.id.btn_nxt:
                if (validation_for_FirstPg()) {
                    Vimal_Suspecting_Entry.mViewPager.setCurrentItem(1);
                }
                break;
        }
    }

    private Snackbar paymentSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(scroll_view, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }

    public static String selectedCityId, selectedCityName;

    public boolean validation_for_FirstPg() {
        boolean flag = true;
        str_agency_name = ed_agency_name.getText().toString().trim();
        selected_date = et_date.getText().toString().trim();
        str_area = ed_area.getText().toString().trim();
        str_executive_person = ed_executive_person.getText().toString().trim();
        str_mobile_no = ed_mobile_no.getText().toString().trim();
        str_owner_name = ed_owner_name.getText().toString().trim();
        str_owner_name = ed_owner_name.getText().toString().trim();
        if (TextUtils.isEmpty(selected_date)) {
            displaySnackBar("Enter valid Date !!!");
            flag = false;
        } else if (TextUtils.isEmpty(str_executive_person)) {
            displaySnackBar("Enter valid executive person !!!");
            flag = false;
        } else if (TextUtils.isEmpty(str_agency_name)) {
            displaySnackBar("Enter valid Agency name !!!");
            flag = false;
        } else if (TextUtils.isEmpty(str_owner_name)) {
            displaySnackBar("Enter valid Owner name !!!");
            flag = false;
        } else if (TextUtils.isEmpty(str_mobile_no) || !isValidPhoneNumber(str_mobile_no)) {

//            displaySnackBar("Enter valid Mobile No. !!!");
//            if(!isValidPhoneNumber(str_mobile_no) )
//            {
            flag = false;
            displaySnackBar("Enter valid Mobile No. !!!");
            System.out.println("hjerthjrhtjrhj hj!!!!!!!!!!!!!");
            // }
            //  flag = false;
        } else if (TextUtils.isEmpty(str_area)) {
            displaySnackBar("Enter valid Area !!!");
            flag = false;
        } else if (TextUtils.isEmpty(selectedCityId + "")) {
            displaySnackBar("Enter valid City !!!");
            flag = false;
        } else if (TextUtils.isEmpty(selected_state_Id + "")) {
            displaySnackBar("Enter valid State !!!");
            flag = false;
        }
        return flag;
    }

    /**
     * Validation of Phone Number
     */
    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || target.length() < 6 || target.length() > 10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }

    }
}
