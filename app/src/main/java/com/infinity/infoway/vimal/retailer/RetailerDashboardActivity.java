package com.infinity.infoway.vimal.retailer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Login;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.OrderPlaceToCompanyActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RetailerDashboardActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout llRetailerPlaceOrer;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_dashboard);
        initView();
    }



    private void initView(){
        llRetailerPlaceOrer = findViewById(R.id.llRetailerPlaceOrer);
        llRetailerPlaceOrer.setOnClickListener(this);
        sharedPref = new SharedPref(RetailerDashboardActivity.this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.llRetailerPlaceOrer){

            Intent retailerPlaceOrder = new Intent(RetailerDashboardActivity.this, OrderPlaceToCompanyActivity.class);
            startActivity(retailerPlaceOrder);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity__home, menu);
        return true;
    }

    private SweetAlertDialog dialogSuccess;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            try {
                dialogSuccess = new SweetAlertDialog(RetailerDashboardActivity.this, SweetAlertDialog.WARNING_TYPE);
                dialogSuccess.setContentText(getResources().getString(R.string.are_sure_want_logout));
                dialogSuccess.setCancelable(true);
                dialogSuccess.show();

                dialogSuccess.setConfirmText(getResources().getString(R.string.title_yes));

                dialogSuccess.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.setCancelable(false);
                        sDialog
                                .setContentText(getResources().getString(R.string.title_logout_from_app))
                                .setConfirmText(getResources().getString(R.string.title_ok))

                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialogSuccess.dismissWithAnimation();
                                        dialogSuccess.cancel();
                                        sharedPref.setIsLogin(false);
                                        sharedPref.setLoginUserName("");
                                        sharedPref.setLoginLoginUserPassword("");
                                        sharedPref.setLoginCustomerId("");
                                        sharedPref.setIsRetailer(false);

                                        sharedPref.setUserPunchInDate("");
                                        sharedPref.setUserPunchOutDate("");
                                        sharedPref.setCompanyName("");
                                        sharedPref.setCompanyId("");
                                        sharedPref.setBranchId("0");
                                        sharedPref.setLastUpdatedSyncCityDate("");
                                        sharedPref.setLastUpdatedSyncRetailerDate("");


                                        Intent intent = new Intent(RetailerDashboardActivity.this, Activity_Login.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sDialog.showCancelButton(false);

                    }
                });

                dialogSuccess.setCancelText(getResources().getString(R.string.title_no));
                dialogSuccess.showCancelButton(true);

            } catch (Exception ignored) {
            }

        } else if (id == R.id.action_exit) {
            try {
                dialogSuccess = new SweetAlertDialog(RetailerDashboardActivity.this, SweetAlertDialog.WARNING_TYPE);
                dialogSuccess.setContentText(getResources().getString(R.string.are_sure_want_exit));
                dialogSuccess.setCancelable(true);
                dialogSuccess.show();

                dialogSuccess.setConfirmText(getResources().getString(R.string.title_yes));

                dialogSuccess.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.setCancelable(false);
                        sDialog
                                .setContentText(getResources().getString(R.string.title_exit_from_app))
                                .setConfirmText(getResources().getString(R.string.title_ok))

                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialogSuccess.dismissWithAnimation();
                                        dialogSuccess.cancel();
                                        finish();
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sDialog.showCancelButton(false);

                    }
                });

                dialogSuccess.setCancelText(getResources().getString(R.string.title_no));
                dialogSuccess.showCancelButton(true);

            } catch (Exception ignored) {
            }
            return true;
        } else if (id == R.id.action_update) {
            try {
                dialogSuccess = new SweetAlertDialog(RetailerDashboardActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                dialogSuccess.setContentText(getResources().getString(R.string.you_are_using_latest));
                dialogSuccess.setCancelable(true);
                dialogSuccess.show();

                Button confirm_button = dialogSuccess.findViewById(R.id.confirm_button);
                confirm_button.setText(R.string.title_ok);

                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSuccess.dismissWithAnimation();
                        dialogSuccess.cancel();
                    }
                });
            } catch (Exception ignored) {
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}