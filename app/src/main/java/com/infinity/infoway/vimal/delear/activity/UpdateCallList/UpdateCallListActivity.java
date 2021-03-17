package com.infinity.infoway.vimal.delear.activity.UpdateCallList;

import android.app.ProgressDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCallListActivity extends AppCompatActivity {


    /**
     * initial items
     **/

    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;
    private RecyclerView rvUpdateCallList;
    ConstraintLayout update_call_list_main;
    private String title_screen = "";

    /**
     * initial items
     **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_call_list);
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

        // Get_Retailer_Rout_Detail_Of_Login_Distributor();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Get_Retailer_Rout_Detail_Of_Login_Distributor();
    }

    /**
     * initializaiton of views
     **/
    LinearLayoutManager linearLayoutManager;

    private void initView() {

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(this);


        update_call_list_main = findViewById(R.id.update_call_list_main);


        rvUpdateCallList = findViewById(R.id.rvUpdateCallList);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvUpdateCallList.setLayoutManager(linearLayoutManager);


    }

    private Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo get_retailer_rout_detail_of_login_distributor_pojo;


    private void Get_Retailer_Rout_Detail_Of_Login_Distributor() {
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

        Call<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo> call = apiService.get_retailer_rout_detail_of_login_distributor(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo>() {
            @Override
            public void onResponse(Call<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo> call, Response<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo> response) {
                if (response.isSuccessful()) {
                    progDialog.dismiss();


                    get_retailer_rout_detail_of_login_distributor_pojo = response.body();

                    if (get_retailer_rout_detail_of_login_distributor_pojo != null && get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().size() > 0) {

                        rvUpdateCallList.setVisibility(View.VISIBLE);
                        UpdateCallListAdapter updateCallListAdapter = new UpdateCallListAdapter(UpdateCallListActivity.this, get_retailer_rout_detail_of_login_distributor_pojo);
                        rvUpdateCallList.setAdapter(updateCallListAdapter);
                        updateCallListAdapter.notifyDataSetChanged();

                    } else {
                        progDialog.dismiss();
                        rvUpdateCallList.setVisibility(View.GONE);
                        displaySnackBar(get_retailer_rout_detail_of_login_distributor_pojo.getMESSAGE() + "");

                    }


                }

            }

            @Override
            public void onFailure(Call<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo> call, Throwable t) {
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
            paymentSnackBar = Snackbar.make(update_call_list_main, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }
}



