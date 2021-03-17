package com.infinity.infoway.vimal.fragment;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Vimal_Suspecting_Entry;
import com.infinity.infoway.vimal.activity.RelativeRadioGroup;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.SuspectingReport;
import com.infinity.infoway.vimal.api.response.SuspendingReportPojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Suspending_Extra extends Fragment implements View.OnClickListener {
    private View view;

    private EditText ed_godown_address;
    private EditText ed_height;
    private EditText ed_width;
    private EditText ed_SQfeet;
    private RadioButton rb_OwnerShip;
    private RadioButton rb_Rent;
    private RadioGroup rg_Possession_Type;
    private RadioButton rb_Distributorship;
    private RadioButton rb_SuperStockiest;
    private RadioButton rb_Retailer;
    private RelativeRadioGroup rg_Inquiry_Type;
    private EditText ed_current_company;
    private EditText ed_exp_year;
    private EditText ed_exp_mnth;
    private RadioButton rb_gst_Y;
    private RadioButton rb_gst_N;
    private RadioGroup rg_gst;
    private RadioButton rb_Computer;
    private RadioButton rb_Manual;
    private RadioGroup rg_billing;
    private EditText ed_Salesmen;
    private EditText ed_worker;
    private EditText ed_Driver;
    private EditText ed_VehicleDetails;
    private EditText ed_NoOfOutlet;
    private EditText ed_AreaCoverage;
    private EditText ed_ReferenceFrom;
    private EditText ed_Remarks;
    private EditText ed_WhyAreYouInterestInVimal;
    private Button btn_prev;
    private Button btn_submit;
    ScrollView scroll_view;
    SuspectingReport.RECORDSBean dataModel;
    Bundle bundle;
    private ConnectionDetector cd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.suspecting_basic_deatils_extra, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);
        initView(view);
        return view;
    }

    private void initView(View view) {
        bundle = getArguments();
        cd = new ConnectionDetector(getActivity());
        getSharedPref = new SharedPref(getActivity());
        apiService = ApiClient.getClient().create(ApiInterface.class);
        ed_godown_address = (EditText) view.findViewById(R.id.ed_godown_address);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        ed_height = (EditText) view.findViewById(R.id.ed_height);
        ed_width = (EditText) view.findViewById(R.id.ed_width);
        ed_SQfeet = (EditText) view.findViewById(R.id.ed_SQfeet);
        rb_OwnerShip = (RadioButton) view.findViewById(R.id.rb_OwnerShip);
        rb_Rent = (RadioButton) view.findViewById(R.id.rb_Rent);
        rg_Possession_Type = (RadioGroup) view.findViewById(R.id.rg_Possession_Type);
        rb_Distributorship = (RadioButton) view.findViewById(R.id.rb_Distributorship);
        rb_SuperStockiest = (RadioButton) view.findViewById(R.id.rb_SuperStockiest);
        rb_Retailer = (RadioButton) view.findViewById(R.id.rb_Retailer);
        rg_Inquiry_Type = (RelativeRadioGroup) view.findViewById(R.id.rg_Inquiry_Type);
        ed_current_company = (EditText) view.findViewById(R.id.ed_current_company);
        ed_exp_year = (EditText) view.findViewById(R.id.ed_exp_year);

        ed_exp_mnth = (EditText) view.findViewById(R.id.ed_exp_mnth);

        rb_gst_Y = (RadioButton) view.findViewById(R.id.rb_gst_Y);
        rb_gst_N = (RadioButton) view.findViewById(R.id.rb_gst_N);
        rg_gst = (RadioGroup) view.findViewById(R.id.rg_gst);
        rb_Computer = (RadioButton) view.findViewById(R.id.rb_Computer);
        rb_Manual = (RadioButton) view.findViewById(R.id.rb_Manual);
        rg_billing = (RadioGroup) view.findViewById(R.id.rg_billing);
        ed_Salesmen = (EditText) view.findViewById(R.id.ed_Salesmen);
        ed_worker = (EditText) view.findViewById(R.id.ed_worker);
        ed_Driver = (EditText) view.findViewById(R.id.ed_Driver);
        ed_VehicleDetails = (EditText) view.findViewById(R.id.ed_VehicleDetails);
        ed_NoOfOutlet = (EditText) view.findViewById(R.id.ed_NoOfOutlet);
        ed_AreaCoverage = (EditText) view.findViewById(R.id.ed_AreaCoverage);
        ed_ReferenceFrom = (EditText) view.findViewById(R.id.ed_ReferenceFrom);
        ed_Remarks = (EditText) view.findViewById(R.id.ed_Remarks);
        ed_WhyAreYouInterestInVimal = (EditText) view.findViewById(R.id.ed_WhyAreYouInterestInVimal);
        btn_prev = (Button) view.findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(this);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
//        rg_gst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                // str_selected_gst_Id = i + "";
//
//                if (i == R.id.rb_gst_Y) {
//                    str_selected_gst_Id = 1 + "";
//                } else {
//                    str_selected_gst_Id = 0 + "";
//                }
//                System.out.println("selected str_selected_gst_Id ID!!!!!!!!!!!  " + str_selected_gst_Id + "");
//            }
//        });
//        rg_Possession_Type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                // str_selected_rg_Possession_Type_Id = i + "";
//
//                if (i == R.id.rb_OwnerShip) {
//                    str_selected_rg_Possession_Type_Id = 1 + "";
//                } else if (i == R.id.rb_Rent) {
//                    str_selected_rg_Possession_Type_Id = 2 + "";
//                } else {
//                    str_selected_rg_Possession_Type_Id = 3 + "";
//                }
//
//                System.out.println("rg_Possession_Type ID!!!!!!!!!!!  " + str_selected_rg_Possession_Type_Id + "");
//            }
//        });
        try {
            dataModel = (SuspectingReport.RECORDSBean) getArguments().getSerializable("data");
        } catch (Exception E) {
            System.out.println("error in to data!!!!!!!!!! ");
        }
        // if (bundle == null || dataModel == null) {

        ed_exp_year.setOnClickListener(this);
        ed_exp_mnth.setOnClickListener(this);

        rg_Inquiry_Type.setOnCheckedChangeListener(new RelativeRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RelativeRadioGroup group, int i) {
                if (i == R.id.rb_Distributorship) {
                    str_rg_Inquiry_Type_ID = 1 + "";
                } else if (i == R.id.rb_SuperStockiest) {
                    str_rg_Inquiry_Type_ID = 2 + "";
                } else if (i == R.id.rb_Retailer) {
                    str_rg_Inquiry_Type_ID = 3 + "";
                } else if (i == R.id.rb_Inquiry_Type_none) {
                    str_rg_Inquiry_Type_ID = 0 + "";
                }
                System.out.println("selected rg_Inquiry_Type ID!!!!!!!!!!!  " + str_rg_Inquiry_Type_ID + "");
            }
        });


        //if (dataModel != null) {
        rg_gst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // str_selected_gst_Id = i + "";

//                if (i == R.id.rb_gst_Y) {
//                    str_selected_gst_Id = 1 + "";
//                } else {
//                    str_selected_gst_Id = 0 + "";
//                }
                if (i == R.id.rb_gst_Y) {
                    str_selected_gst_Id = 1 + "";
                } else {
                    str_selected_gst_Id = 2 + "";
                }
                System.out.println("selected str_selected_gst_Id ID!!!!!!!!!!!  " + str_selected_gst_Id + "");
            }
        });
        rg_billing.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // str_selected_gst_Id = i + "";

                if (i == R.id.rb_Computer) {
                    str_Billing_Id = 1 + "";
                } else {
                    str_Billing_Id = 2 + "";//manual
                }
                System.out.println("selected str_selected_gst_Id ID!!!!!!!!!!!  " + str_selected_gst_Id + "");
            }
        });
        rg_Possession_Type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // str_selected_rg_Possession_Type_Id = i + "";

                if (i == R.id.rb_OwnerShip) {
                    str_selected_rg_Possession_Type_Id = 1 + "";
                } else if (i == R.id.rb_Rent) {
                    str_selected_rg_Possession_Type_Id = 2 + "";
                }
//                    else {
//                        str_selected_rg_Possession_Type_Id = 3 + "";
//                    }

                System.out.println("rg_Possession_Type ID!!!!!!!!!!!  " + str_selected_rg_Possession_Type_Id + "");
            }
        });

        // } else {
        if (dataModel != null) {
            ed_Remarks.setText(dataModel.getSe_remarks() + "");
            ed_current_company.setText(dataModel.getSe_current_company() + "");
            ed_WhyAreYouInterestInVimal.setText(dataModel.getSe_interest() + "");
            ed_AreaCoverage.setText(dataModel.getSe_area_coverage() + "");
            ed_godown_address.setText(dataModel.getSe_godown_addr() + "");
            ed_height.setText(dataModel.getSe_godown_height() + "");
            ed_width.setText(dataModel.getSe_godown_width() + "");
            ed_SQfeet.setText(dataModel.getSe_godown_total_sq() + "");
            ed_exp_mnth.setText(dataModel.getSe_experience_month() + "");
            ed_exp_year.setText(dataModel.getSe_experience_year() + "");
            ed_VehicleDetails.setText(dataModel.getSs_vehicle_details() + "");
            ed_NoOfOutlet.setText(dataModel.getSe_no_of_outlet() + "");
            ed_ReferenceFrom.setText(dataModel.getSe_reference_from() + "");
            ed_Salesmen.setText(dataModel.getSe_salesmen() + "");
            ed_Driver.setText(dataModel.getSe_driver() + "");
            ed_worker.setText(dataModel.getSe_worker() + "");
//
            str_main_id = dataModel.getId() + "";

            String a = str_selected_gst_Id;
            try {
                a = dataModel.getSe_gst_no() + "";
            } catch (Exception e) {
                System.out.println("error in to gst!!!!! ");

            }
            if (a.contentEquals("1")) {
                rb_gst_Y.setChecked(true);
            } else {
                rb_gst_N.setChecked(true);
            }

            /** Inquiry Type 1 Distributorship 2 Super Stockiest 3 Retailer*/
            String Inquiry_Type_ID = str_rg_Inquiry_Type_ID;
            try {
                Inquiry_Type_ID = dataModel.getSe_inquiry_type() + "";
            } catch (Exception e) {
                System.out.println("error in to getSe_inquiry_type!!!!! ");

            }
            if (Inquiry_Type_ID.contentEquals("1")) {
                rb_Distributorship.setChecked(true);
            } else if (Inquiry_Type_ID.contentEquals("2")) {
                rb_SuperStockiest.setChecked(true);
            } else {  //(Inquiry_Type_ID.contentEquals("3")) {
                rb_Retailer.setChecked(true);
            }
//            else {
//                rb_Inquiry_Type_none.setChecked(true);
//            }


            /**
             * Possession Type 1 OwnerShip 2 Rent
             */
            String rg_Possession_Type_Id = str_selected_rg_Possession_Type_Id;
            try {
                rg_Possession_Type_Id = dataModel.getSe_possession_type() + "";
            } catch (Exception e) {
                System.out.println("error in to getSe_inquiry_type!!!!! ");

            }
            if (rg_Possession_Type_Id.contentEquals("1")) {
                rb_OwnerShip.setChecked(true);
            } else {
                rb_Rent.setChecked(true);
            }

            /**
             * Billing System  1 computer 2 manual
             */
            String Billing_Idd = str_Billing_Id;
            try {
                Billing_Idd = dataModel.getSe_billing_system() + "";
            } catch (Exception e) {
                System.out.println("error in to Billing_Idd!!!!! ");

            }
            if (Billing_Idd.contentEquals("1")) {
                rb_Computer.setChecked(true);
            } else {
                rb_Manual.setChecked(true);
            }


        }
//            ed_Remarks.setFocusable(false);
//            ed_current_company.setFocusable(false);
//            ed_WhyAreYouInterestInDavat.setFocusable(false);
//            ed_AreaCoverage.setFocusable(false);
//            ed_godown_address.setFocusable(false);
//            ed_height.setFocusable(false);
//            ed_width.setFocusable(false);
//            ed_SQfeet.setFocusable(false);
//            ed_exp_mnth.setFocusable(false);
//            ed_exp_year.setFocusable(false);


        //}


    }

    String str_ed_godown_address = "";
    String str_selected_gst_Id = "2";
    /**
     * GST No. 1 yes 2 no
     */
    String str_Billing_Id = "1";
    String str_main_id = "0";
    /**
     * Billing System  1 computer 2 manual
     */
    String str_rg_Inquiry_Type_ID = "1";
    /**
     * Inquiry Type 1 Distributorship 2 Super Stockiest 3 Retailer
     */
    String str_selected_rg_Possession_Type_Id = "1";
    /**
     * Possession Type 1 OwnerShip 2 Rent
     */
    private SweetAlertDialog dialogSuccess;

    private ProgressDialog progDialog;
    private ApiInterface apiService;
    private SharedPref getSharedPref;
    private Snackbar addOrderSnackBar;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ed_exp_year:
                break;
            case R.id.ed_exp_mnth:
                break;
            case R.id.btn_prev:
                Vimal_Suspecting_Entry.mViewPager.setCurrentItem(0);
                break;
            case R.id.btn_submit:

                str_ed_godown_address = ed_godown_address.getText().toString().trim();

                Suspending_Basic basic = new Suspending_Basic();
                System.out.println("!!!!!!!!!!!! " + basic.validation_for_FirstPg() + "");
                if (basic.validation_for_FirstPg()) {
                    if (!str_ed_godown_address.contentEquals("")) {

                        if (cd != null && cd.isConnectingToInternet()) {
                            do_send_data_To_Server();
                        } else {
                            displaySnackBar(getResources().getString(R.string.title_no_internet));
                        }
                    } else {
                        displaySnackBar("Please enter valid Godown Address");
                    }

                } else {
                    dialogSuccess = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                    dialogSuccess.setTitleText(getString(R.string.sorder_oops));
                    dialogSuccess.setContentText("Please Fill Required Field Properly !");
                    dialogSuccess.setCancelable(false);
                    dialogSuccess.show();
                    dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSuccess.dismissWithAnimation();
                            dialogSuccess.cancel();

                            Vimal_Suspecting_Entry.mViewPager.setCurrentItem(0);
                        }
                    });
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

    private void do_send_data_To_Server() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(getActivity());
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        String req_app_version = String.valueOf(getSharedPref.getAppVersionCode());
        String req_android_id = getSharedPref.getAppAndroidId() + "";
        String req_device_id = String.valueOf(getSharedPref.getRegisteredId() + "");
        String req_user_id = String.valueOf(getSharedPref.getRegisteredUserId() + "");
        String req_key = Config.ACCESS_KEY;
        String req_company_id = getSharedPref.getCompanyId() + "";
//        String req_emp_id = getSharedPref.getEMP_ID() + "";
        String req_emp_id = Suspending_Basic.selected_EMP_NAME_ID + "";
        String req_date = Suspending_Basic.et_date.getText().toString().trim() + "";
        String req_agency_name = Suspending_Basic.ed_agency_name.getText().toString().trim() + "";
        String req_owner_name = Suspending_Basic.ed_owner_name.getText().toString().trim() + "";
        String req_mobile_no = Suspending_Basic.ed_mobile_no.getText().toString().trim() + "";
        String req_area = Suspending_Basic.ed_area.getText().toString().trim() + "";
        String req_cit_id = Suspending_Basic.selectedCityId + "";
        String req_state_id = Suspending_Basic.selected_state_Id + "";
        String req_godown_add = ed_godown_address.getText().toString().trim() + "";
        String req_possession = str_selected_rg_Possession_Type_Id.toString().trim();
        String req_Inquiry = str_rg_Inquiry_Type_ID + "";
        String req_current_company = ed_current_company.getText().toString().trim() + "";
        String req_gst_no = str_selected_gst_Id + "";
        String req_bill_sys = str_Billing_Id + "";
        String req_main_id = str_main_id + "";


        String req_tal_id = Suspending_Basic.selected_taluka_ID + "";
        String req_dis_id = Suspending_Basic.selected_district_ID + "";
        String req_go_height = ed_height.getText().toString().trim() + "";
        String req_go_width = ed_width.getText().toString().trim() + "";
        String req_go_feet = ed_SQfeet.getText().toString().trim() + "";
        String req_year = ed_exp_year.getText().toString().trim() + "";
        String req_month = ed_exp_mnth.getText().toString().trim() + "";
        String req_salesman = ed_Salesmen.getText().toString().trim() + "";
        String req_Worker = ed_worker.getText().toString().trim() + "";
        String req_Driver = ed_Driver.getText().toString().trim() + "";
        String req_vehicle_detail = ed_VehicleDetails.getText().toString().trim() + "";
        String req_no_of_out = ed_NoOfOutlet.getText().toString().trim() + "";
        String req_area_cov = ed_AreaCoverage.getText().toString().trim() + "";
        String req_form = ed_ReferenceFrom.getText().toString().trim() + "";
        String req_remark = ed_Remarks.getText().toString().trim() + "";
        String req_a_y_d = ed_WhyAreYouInterestInVimal.getText().toString().trim() + "";


        Call<SuspendingReportPojo> call = apiService.Add_edit_Suspecting_Report(
                req_app_version,
                req_android_id,
                req_device_id,
                req_user_id,
                req_key,
                req_company_id,
                req_date,
                req_emp_id,
                req_agency_name,
                req_owner_name,
                req_mobile_no,
                req_area,
                req_cit_id,
                req_state_id,
                req_godown_add,
                req_possession,
                req_Inquiry,
                req_current_company,
                req_gst_no,
                req_bill_sys,
                req_main_id,
                req_tal_id,
                req_dis_id,
                req_go_height,
                req_go_width,
                req_go_feet,
                req_year,
                req_month,
                req_salesman,
                req_Worker,
                req_Driver,
                req_vehicle_detail,
                req_no_of_out,
                req_area_cov,
                req_form,
                req_remark,
                req_a_y_d);
        call.enqueue(new Callback<SuspendingReportPojo>() {
            @Override
            public void onResponse(Call<SuspendingReportPojo> call, Response<SuspendingReportPojo> response) {
                System.out.println("this is response!!!!!!!!!!!!!!" + call.request() + "");
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }

                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getFlag() > 0) {
                        System.out.println("this is if!!!!!!!!!!!!!!");
                        //  displaySnackBar(response.body().getMESSAGE() + "");
                        // getActivity().finish();
                        try {
//                            if (response.body().getFLG() == 1) {
                            dialogSuccess = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                            dialogSuccess.setTitleText(getString(R.string.sorder_good_job));
                            dialogSuccess.setContentText(response.body().getMESSAGE() + "");
                            dialogSuccess.setCancelable(false);
                            dialogSuccess.show();
                            dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogSuccess.dismissWithAnimation();
                                    dialogSuccess.cancel();
                                    getActivity().finish();
                                }
                            });


                        } catch (Exception ex) {
                        }
                    } else {
                        System.out.println("this is last else!!!!!!!!!!!!!!!");
                        displaySnackBar(response.body().getMESSAGE() + "");
                    }
                }
            }

            @Override
            public void onFailure(Call<SuspendingReportPojo> call, Throwable t) {
                System.out.println("on failure!!!!!!!!!!!!!!!!!!!  ");

                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                        t.printStackTrace();
                        displaySnackBar(getActivity().getString(R.string.something_went_wrong) + "");

                    }
                } catch (Exception ex) {
                }
            }
        });

    }

}
