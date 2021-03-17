package com.infinity.infoway.vimal.delear.activity.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.FeedBack.Insert_RoutWise_FeedBack_Pojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment implements View.OnClickListener {


    private TextInputLayout tv_input_retailer_name, tv_input_shop_name, tv_input_mobile, tv_input_area, tv_input_village, tv_input_city, tv_input_district, tv_input_feedback,tv_photo_or_video;
    private EditText et_select_retailer_name, et_feedback, et_shop_name, et_mobile, et_area, et_village, et_city, et_district,et_feedback_attachment;
    Button btn_submit_feedback;


    /**
     * initial items
     **/

    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;

    /**
     * initial items
     **/


    ConstraintLayout main_feed_back;

    public FeedbackFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        initView(view);

        return view;
    }


    private void initView(View view) {

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(getActivity());
        /**Textinput Layout**/
        tv_input_retailer_name = (TextInputLayout) view.findViewById(R.id.tv_input_retailer_name);
        tv_input_shop_name = view.findViewById(R.id.tv_input_shop_name);
        tv_input_mobile = view.findViewById(R.id.tv_input_mobile);
        tv_input_area = view.findViewById(R.id.tv_input_area);
        tv_input_village = view.findViewById(R.id.tv_input_village);
        tv_input_city = view.findViewById(R.id.tv_input_city);
        tv_input_district = view.findViewById(R.id.tv_input_district);
        tv_input_feedback = view.findViewById(R.id.tv_input_feedback);
        et_feedback_attachment = view.findViewById(R.id.et_feedback_attachment);
        main_feed_back = view.findViewById(R.id.main_feed_back);
        /**Editext**/
        et_select_retailer_name = (EditText) view.findViewById(R.id.et_select_retailer_name);
        et_select_retailer_name.setOnClickListener(this);
        et_shop_name = (EditText) view.findViewById(R.id.et_shop_name);
        et_mobile = (EditText) view.findViewById(R.id.et_mobile);
        et_area = (EditText) view.findViewById(R.id.et_area);
        et_village = (EditText) view.findViewById(R.id.et_village);
        et_city = (EditText) view.findViewById(R.id.et_city);
        et_district = (EditText) view.findViewById(R.id.et_district);
        et_feedback = (EditText) view.findViewById(R.id.et_feedback);

        /** Button **/
        btn_submit_feedback = (Button) view.findViewById(R.id.btn_submit_feedback);
        btn_submit_feedback.setOnClickListener(this);
        et_feedback_attachment.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit_feedback) {
            if (et_select_retailer_name.getText().toString().contentEquals("")) {
                Toast.makeText(getActivity(), "Please Select Retailer Name", Toast.LENGTH_LONG).show();

            } else if (et_feedback.getText().toString().contentEquals("")) {

                Toast.makeText(getActivity(), "Please Enter Valid Feedback", Toast.LENGTH_LONG).show();
            } else {

                System.out.println("validated==== inserting users feedback");
                insert_RoutWise_FeedBack();

            }

        } else if (v.getId() == R.id.et_select_retailer_name) {
            Intent intent = new Intent(getActivity(), Activity_Select_City.class);
            intent.putExtra("isFromCitySelect", 9899);
            startActivityForResult(intent, 9888);

        }else if (v.getId() == R.id.et_feedback_attachment){

        }
    }



    private Insert_RoutWise_FeedBack_Pojo insert_routWise_feedBack_pojo;

    private void insert_RoutWise_FeedBack() {

        /** Getting all text fields value to final submit**/

        String SHOP_NAME = et_shop_name.getText().toString().trim();
        String RETAILER_NAME = et_select_retailer_name.getText().toString().trim();
        String MOBILE_NO = et_mobile.getText().toString().trim();
        String AREA_NAME = et_area.getText().toString().trim();
        String VILLAGE_NAME = et_village.getText().toString().trim();
        String CITY_NAME = et_city.getText().toString().trim();
        String DISTRICT_NAME = et_district.getText().toString().trim();
        String Feedback = et_feedback.getText().toString().trim();


        /** Getting all text fields value to final submit**/


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

        Call<Insert_RoutWise_FeedBack_Pojo> call = apiService.insert_routWise_feedBack(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,

                getSharedPref.getCompanyId() + "",
                RETAILER_NAME,
                SHOP_NAME, MOBILE_NO,
                AREA_NAME, VILLAGE_NAME, CITY_NAME,
                DISTRICT_NAME, Feedback


        );

        call.enqueue(new Callback<Insert_RoutWise_FeedBack_Pojo>() {
            @Override
            public void onResponse(Call<Insert_RoutWise_FeedBack_Pojo> call, Response<Insert_RoutWise_FeedBack_Pojo> response) {
                if (response.isSuccessful()) {
                    progDialog.dismiss();
                    insert_routWise_feedBack_pojo = response.body();
                    if (insert_routWise_feedBack_pojo != null) {

                        displaySnackBar(insert_routWise_feedBack_pojo.getMESSAGE());
                        et_select_retailer_name.setText("");
                        et_shop_name.setText("");
                        et_mobile.setText("");
                        et_area.setText("");
                        et_village.setText("");
                        et_city.setText("");
                        et_district.setText("");
                        et_feedback.setText("");

                    }


                }

            }

            @Override
            public void onFailure(Call<Insert_RoutWise_FeedBack_Pojo> call, Throwable t) {
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
            paymentSnackBar = Snackbar.make(main_feed_back, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9888) {
            if (data.hasExtra("Cus_Name")) {
                et_select_retailer_name.setText(data.getExtras().getString("Cus_Name"));

                if (data.getExtras().getString("Shop_Name") == null) {
                    et_shop_name.setText("-");
                } else {
                    et_shop_name.setText(data.getExtras().getString("Shop_Name"));
                }

                if (data.getExtras().getString("Mobile_No") == null) {
                    et_mobile.setText("-");
                } else {
                    et_mobile.setText(data.getExtras().getString("Mobile_No"));
                }

                if (data.getExtras().getString("Area_Name") == null) {
                    et_area.setText("-");
                } else {
                    et_area.setText(data.getExtras().getString("Area_Name"));
                }

                if (data.getExtras().getString("City_Name") == null) {
                    et_city.setText("-");
                } else {
                    et_city.setText(data.getExtras().getString("City_Name"));
                }

                if (data.getExtras().getString("District_Name") == null) {
                    et_district.setText("-");
                } else {
                    et_district.setText(data.getExtras().getString("District_Name"));
                }


                // et_select_retailer_name.setText(data.getExtras().getString("Cus_Name"));
                // et_select_retailer_name.setText(data.getExtras().getString("Cus_Name"));

            }


        }
    }
}
