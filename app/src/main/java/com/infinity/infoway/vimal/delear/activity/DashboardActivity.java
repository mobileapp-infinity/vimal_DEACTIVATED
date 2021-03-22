package com.infinity.infoway.vimal.delear.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Login;
import com.infinity.infoway.vimal.add__news_or_notification.activity.AddNewsOrNotificationActivity;
import com.infinity.infoway.vimal.add__news_or_notification.activity.ViewNewsOrNotificationListActivity;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.RoutePlanningActivity;
import com.infinity.infoway.vimal.delear.activity.Complaint.ComplainFormActivity;
import com.infinity.infoway.vimal.delear.activity.CreditNote.CreditNoteActivity;
import com.infinity.infoway.vimal.delear.activity.DebittNote.DebitNoteActivity;
import com.infinity.infoway.vimal.delear.activity.FeedBack.FeedbackFormActivity;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.OrderPlaceToCompanyActivity;
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.PerFormInvoiceAndLedgerActivity;
import com.infinity.infoway.vimal.delear.activity.PromotionalRequirement.PromoitonalRequirementActivity;
import com.infinity.infoway.vimal.delear.activity.RetailerOrderSummary.RetailerOrderSummaryActiivty;
import com.infinity.infoway.vimal.delear.activity.SalesAndStockDetails.SalesAndStockDetailsActivity;
import com.infinity.infoway.vimal.delear.activity.UpdateCallList.UpdateCallListActivity;
import com.infinity.infoway.vimal.delear.activity.VehicleDispatchUpdate.VehicleDispatchUpdateActivity;
import com.infinity.infoway.vimal.delear.activity.add_schedule.activity.ScheduleActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/*import com.infinity.infoway.davat.delear.activity.test.activity.AddScheduleActivity;*/

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    LinearLayout llFeedbackForm, llComplainForm, llOrderPlaceToCompany, llPerformInvoiceAndLedger, llVehicleDispatchUpdate, llUpdateCallList, llRetailOrderBooking, llRetailOrderSummary, llSalesAndStockDetails, llPromotionalRequirements, llCreditNote, llDebitNote;
    RelativeLayout rvNotificaitonContainerDealer;
    TextView tvFeedBackForm, tvComplainForm, tv_perform_invoice_and_ledger, tv_vehicle_dispatch_update, tv_sales_and_stock_details, tv_promoitonal;
    TextView tvNotificationCounter;
    ImageView ivNotificationBackButtonDealer;
    AppBarLayout aapbarDelear;
    SharedPref sharedPref;
    androidx.appcompat.widget.Toolbar toolbar;
    String titlee = "";
    TextView tv_retailer_order_summary;
    TextView tv_order_place_to_company;
    private TextView txt_user_name, txt_app_version;
    private DrawerLayout drawer;
    private SweetAlertDialog dialogSuccess;
    private TextView tv_update_call_list;
    LinearLayout llRetailerManagementForRetailer;
    private TextView tvDebitNote, tvCreditNote;

    //remish
    private LinearLayout llTest, llRoutePlanning;

    private LinearLayout llSendNotification;
    private LinearLayout llViewNewsOrNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!TextUtils.isEmpty(sharedPref.getCompanyName())) {
            titlee = sharedPref.getCompanyName();
        }


        this.setTitle(titlee);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        txt_app_version = navigationView.getHeaderView(0).findViewById(R.id.txt_app_version);

        if (sharedPref.getAppVersionCode() > 0) {
            txt_app_version.setText("App Version : " + String.valueOf(sharedPref.getAppVersionCode()) + " (" + sharedPref.getAppVerName() + " )");
        }

        txt_user_name = navigationView.getHeaderView(0).findViewById(R.id.txt_user_name);
        if (!TextUtils.isEmpty(sharedPref.getUserName())) {
            txt_user_name.setText(sharedPref.getUserName());
        }
    }


    private void initView() {

        //remish
        llTest = findViewById(R.id.llTest);
        llRoutePlanning = findViewById(R.id.llRoutePlanning);

        llTest.setOnClickListener(this);
        llRoutePlanning.setOnClickListener(this);

        /** Linear Layout**/
        sharedPref = new SharedPref(DashboardActivity.this);
        llOrderPlaceToCompany = (LinearLayout) findViewById(R.id.llOrderPlaceToCompany);
        llPerformInvoiceAndLedger = (LinearLayout) findViewById(R.id.llPerformInvoiceAndLedger);
        llVehicleDispatchUpdate = (LinearLayout) findViewById(R.id.llVehicleDispatchUpdate);
        llUpdateCallList = (LinearLayout) findViewById(R.id.llUpdateCallList);
        llRetailOrderBooking = (LinearLayout) findViewById(R.id.llRetailOrderBooking);
        llRetailOrderSummary = (LinearLayout) findViewById(R.id.llRetailOrderSummary);
        llSalesAndStockDetails = (LinearLayout) findViewById(R.id.llSalesAndStockDetails);
        llPromotionalRequirements = (LinearLayout) findViewById(R.id.llPromotionalRequirements);
        llFeedbackForm = (LinearLayout) findViewById(R.id.llFeedbackForm);
        llComplainForm = (LinearLayout) findViewById(R.id.llComplainForm);
        llCreditNote = (LinearLayout) findViewById(R.id.llCreditNote);
        llDebitNote = (LinearLayout) findViewById(R.id.llDebitNote);

        llOrderPlaceToCompany.setOnClickListener(this);
        llPerformInvoiceAndLedger.setOnClickListener(this);
        llVehicleDispatchUpdate.setOnClickListener(this);
        llUpdateCallList.setOnClickListener(this);
        llRetailOrderBooking.setOnClickListener(this);
        llRetailOrderSummary.setOnClickListener(this);
        llSalesAndStockDetails.setOnClickListener(this);
        llPromotionalRequirements.setOnClickListener(this);
        llFeedbackForm.setOnClickListener(this);
        llComplainForm.setOnClickListener(this);
        llCreditNote.setOnClickListener(this);
        llDebitNote.setOnClickListener(this);
        llSendNotification = findViewById(R.id.llSendNotification);
        llSendNotification.setOnClickListener(this);
        llViewNewsOrNotification = findViewById(R.id.llViewNewsOrNotification);
        llViewNewsOrNotification.setOnClickListener(this);


        /**TextView**/
        tvFeedBackForm = (TextView) findViewById(R.id.tvFeedBackForm);
        tvComplainForm = (TextView) findViewById(R.id.tvComplainForm);
        tvNotificationCounter = (TextView) findViewById(R.id.tvNotificationCounter);
        tv_perform_invoice_and_ledger = (TextView) findViewById(R.id.tv_perform_invoice_and_ledger);
        tv_vehicle_dispatch_update = (TextView) findViewById(R.id.tv_vehicle_dispatch_update);
        tv_vehicle_dispatch_update = (TextView) findViewById(R.id.tv_vehicle_dispatch_update);
        tv_sales_and_stock_details = (TextView) findViewById(R.id.tv_sales_and_stock_details);
        tv_promoitonal = (TextView) findViewById(R.id.tv_promoitonal);
        tv_retailer_order_summary = (TextView) findViewById(R.id.tv_retailer_order_summary);
        tv_order_place_to_company = (TextView) findViewById(R.id.tv_order_place_to_company);
        tv_update_call_list = (TextView) findViewById(R.id.tv_update_call_list);
        llRetailerManagementForRetailer = findViewById(R.id.llRetailerManagementForRetailer);
        tvCreditNote = (TextView) findViewById(R.id.tv_credit_note);
        tvDebitNote = (TextView) findViewById(R.id.tv_debit_note);
        llRetailerManagementForRetailer.setOnClickListener(this);

        /**Relative Layout**/
        // rvNotificaitonContainerDealer = (RelativeLayout) findViewById(R.id.rvNotificaitonContainerDealer);

        /**Image View**/
        // ivNotificationBackButtonDealer = (ImageView)findViewById(R.id.ivNotificationBackButtonDealer);


        // aapbarDelear = (AppBarLayout)findViewById(R.id.aapbarDelear);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.llTest) {

            Intent order_place_to_company_intent = new Intent(DashboardActivity.this, ScheduleActivity.class);
            order_place_to_company_intent.putExtra("title_screen", "Add Schedule");
            startActivity(order_place_to_company_intent);

        } else if (v.getId() == R.id.llOrderPlaceToCompany) {


            Intent order_place_to_company_intent = new Intent(DashboardActivity.this, OrderPlaceToCompanyActivity.class);
            order_place_to_company_intent.putExtra("title_screen", tv_order_place_to_company.getText().toString().trim());
            order_place_to_company_intent.putExtra("isOrderPlace", true);
            startActivity(order_place_to_company_intent);


        } else if (v.getId() == R.id.llPerformInvoiceAndLedger) {

            Intent perfom_intent = new Intent(DashboardActivity.this, PerFormInvoiceAndLedgerActivity.class);
            perfom_intent.putExtra("title_screen", tv_perform_invoice_and_ledger.getText().toString().trim());
            startActivity(perfom_intent);

        } else if (v.getId() == R.id.llVehicleDispatchUpdate) {

            Intent perfom_intent = new Intent(DashboardActivity.this, VehicleDispatchUpdateActivity.class);
            perfom_intent.putExtra("title_screen", tv_vehicle_dispatch_update.getText().toString().trim());
            startActivity(perfom_intent);

        } else if (v.getId() == R.id.llUpdateCallList) {

            Intent update_call_list_intent = new Intent(DashboardActivity.this, UpdateCallListActivity.class);
            update_call_list_intent.putExtra("title_screen", tv_update_call_list.getText().toString().trim());
            startActivity(update_call_list_intent);


        } else if (v.getId() == R.id.llRetailOrderBooking) {
            Intent retailorderBookingIntetnt = new Intent(DashboardActivity.this, OrderPlaceToCompanyActivity.class);
            retailorderBookingIntetnt.putExtra("title_screen", "Retailer Order Booking");
            startActivity(retailorderBookingIntetnt);

        } else if (v.getId() == R.id.llRetailOrderSummary) {

            Intent retailer_intent = new Intent(DashboardActivity.this, RetailerOrderSummaryActiivty.class);
            retailer_intent.putExtra("title_screen", tv_retailer_order_summary.getText().toString().trim());
            startActivity(retailer_intent);

        } else if (v.getId() == R.id.llSalesAndStockDetails) {

            Intent sales_and_stock_intent = new Intent(DashboardActivity.this, SalesAndStockDetailsActivity.class);
            sales_and_stock_intent.putExtra("title_screen", tv_sales_and_stock_details.getText().toString().trim());
            startActivity(sales_and_stock_intent);


        } else if (v.getId() == R.id.llPromotionalRequirements) {

            Intent sales_and_stock_intent = new Intent(DashboardActivity.this, PromoitonalRequirementActivity.class);
            sales_and_stock_intent.putExtra("title_screen", tv_promoitonal.getText().toString().trim());
            startActivity(sales_and_stock_intent);


        } else if (v.getId() == R.id.llFeedbackForm) {

            Intent feedback_intent = new Intent(DashboardActivity.this, FeedbackFormActivity.class);
            feedback_intent.putExtra("title_screen", tvFeedBackForm.getText().toString().trim());
            startActivity(feedback_intent);

        } else if (v.getId() == R.id.llComplainForm) {
            Intent complain_intent = new Intent(DashboardActivity.this, ComplainFormActivity.class);
            complain_intent.putExtra("title_screen", tvComplainForm.getText().toString().trim());
            startActivity(complain_intent);

        } else if (v.getId() == R.id.llRetailerManagementForRetailer) {
            Intent complain_intent = new Intent(DashboardActivity.this, RetailerManagementForRetailerActivity.class);
            complain_intent.putExtra("title_screen", "Retailer Management");
            startActivity(complain_intent);

        } else if (v.getId() == R.id.llCreditNote) {
            Intent credit_note = new Intent(DashboardActivity.this, CreditNoteActivity.class);
            credit_note.putExtra("title_screen", tvCreditNote.getText().toString().trim());
            startActivity(credit_note);
        } else if (v.getId() == R.id.llDebitNote) {
            Intent debit_note = new Intent(DashboardActivity.this, DebitNoteActivity.class);
            debit_note.putExtra("title_screen", tvDebitNote.getText().toString().trim());
            startActivity(debit_note);

        } else if (v.getId() == R.id.llRoutePlanning) {
            Intent debit_note = new Intent(DashboardActivity.this, RoutePlanningActivity.class);
            debit_note.putExtra("title_screen", "Route Plannig");
            startActivity(debit_note);
        } else if (v.getId() == R.id.llSendNotification) {
            Intent debit_note = new Intent(DashboardActivity.this, AddNewsOrNotificationActivity.class);
            debit_note.putExtra("title_screen", "Send Notification");
            startActivity(debit_note);
        } else if (v.getId() == R.id.llViewNewsOrNotification) {
            Intent debit_note = new Intent(DashboardActivity.this, ViewNewsOrNotificationListActivity.class);
            debit_note.putExtra("title_screen", "View News or Notification");
            startActivity(debit_note);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity__home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            try {
                dialogSuccess = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.WARNING_TYPE);
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

                                        sharedPref.setUserPunchInDate("");
                                        sharedPref.setUserPunchOutDate("");
                                        sharedPref.setCompanyName("");
                                        sharedPref.setCompanyId("0");
                                        sharedPref.setBranchId("0");
                                        sharedPref.setLastUpdatedSyncCityDate("");
                                        sharedPref.setLastUpdatedSyncRetailerDate("");


                                        Intent intent = new Intent(DashboardActivity.this, Activity_Login.class);
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
                dialogSuccess = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.WARNING_TYPE);
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
                dialogSuccess = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.SUCCESS_TYPE);
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_order_place) {
            llOrderPlaceToCompany.performClick();
        } else if (id == R.id.nav_invoice_ledger) {
            llPerformInvoiceAndLedger.performClick();
        } else if (id == R.id.nav_vehicle_dispatch_update) {
            llVehicleDispatchUpdate.performClick();
        } else if (id == R.id.nav_update_call_list) {
            llUpdateCallList.performClick();
        } else if (id == R.id.nav_retailer_order_booking) {
            llRetailOrderBooking.performClick();
        } else if (id == R.id.nav_retailer_order_summary) {
            llRetailOrderSummary.performClick();
        } else if (id == R.id.nav_salses_stock) {
            llSalesAndStockDetails.performClick();
        } else if (id == R.id.nav_promo_require) {
            llPromotionalRequirements.performClick();
        } else if (id == R.id.nav_feedback_form) {
            llFeedbackForm.performClick();
        } else if (id == R.id.nav_complaint_form) {
            llComplainForm.performClick();
        } else if (id == R.id.nav_retailer_management) {
            llRetailerManagementForRetailer.performClick();
        } else if (id == R.id.nav_credit_note) {
            llCreditNote.performClick();
        } else if (id == R.id.nav_debit_note) {
            llDebitNote.performClick();
        } else if (id == R.id.nav_route_planning) {

            llRoutePlanning.performClick();
        } else if (id == R.id.nav_schedules) {
            llTest.performClick();
        } else if (id == R.id.nav_send_notification) {
            llSendNotification.performClick();
        } else if (id == R.id.nav_view_news_or_notification) {
            llViewNewsOrNotification.performClick();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
