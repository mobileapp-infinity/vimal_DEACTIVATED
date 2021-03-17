package com.infinity.infoway.vimal.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.Insert_Retailer_And_Call_Visit_Response;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.model.RetailerVisitCallModel;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Sync_Data extends AppCompatActivity {

    private TextView txt_retailer_visit_call_sync_count,txt_retailer_visit_call_sync_detail,txt_retailer_new_call_sync_count,txt_retailer_regular_call_sync_count,txt_distributor_visit_call_sync_count,txt_distributor_regular_call_sync_count;
    private RelativeLayout rel_call_sync,rel_retailer_new_call,rel_retailer_regular_call_sync,rel_distributor_visit_call,rel_distributor_regular_call;
    private SwitchCompat switch_retailer_visit_call_sync,switch_retailer_new_call_sync,switch_retailer_regular_call_sync,switch_distributor_visit_call_sync,switch_distributor_regular_call_sync;
    private DBConnector dbConnector;
    private SharedPref getSharedPref;
    private ApiInterface apiService;
    private ConnectionDetector cd;
    private MultipartBody.Part fileToUploadPassport;
    private SweetAlertDialog dialogSuccess;
    private Snackbar paymentSnackBar;
    private LinearLayout linear_sync_data;
    private View view_retailer_visit_call,view_retailer_new_call,view_retailer_regular_call,view_distributor_visit_call,view_distributor_regular_call;

    private int visitLoopCounter=0;
    private ProgressDialog progDialog;

    private List<RetailerVisitCallModel> listRetailerVisitCall;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);
        Activity_Home.    FLAG_4_BACK_START_PG_AGAIN=true;
        this.setTitle(getResources().getString((R.string.title_sync_offline_data)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initControls();

    }

    private void initControls(){


        apiService = ApiClient.getClient().create(ApiInterface.class);
        cd = new ConnectionDetector(Activity_Sync_Data.this);

        dbConnector=new DBConnector(Activity_Sync_Data.this);
        getSharedPref=new SharedPref(Activity_Sync_Data.this);



        view_retailer_visit_call=findViewById(R.id.view_retailer_visit_call);
        txt_retailer_visit_call_sync_count=findViewById(R.id.txt_retailer_visit_call_sync_count);
        txt_retailer_visit_call_sync_detail=findViewById(R.id.txt_retailer_visit_call_sync_detail);
        switch_retailer_visit_call_sync=findViewById(R.id.switch_retailer_visit_call_sync);
        rel_call_sync=findViewById(R.id.rel_call_sync);


        rel_retailer_new_call=findViewById(R.id.rel_retailer_new_call);
        txt_retailer_new_call_sync_count=findViewById(R.id.txt_retailer_new_call_sync_count);
        switch_retailer_new_call_sync=findViewById(R.id.switch_retailer_new_call_sync);
        view_retailer_new_call=findViewById(R.id.view_retailer_new_call);

        rel_retailer_regular_call_sync=findViewById(R.id.rel_retailer_regular_call_sync);
        txt_retailer_regular_call_sync_count=findViewById(R.id.txt_retailer_regular_call_sync_count);
        switch_retailer_regular_call_sync=findViewById(R.id.switch_retailer_regular_call_sync);
        view_retailer_regular_call=findViewById(R.id.view_retailer_regular_call);

        rel_distributor_visit_call=findViewById(R.id.rel_distributor_visit_call);
        txt_distributor_visit_call_sync_count=findViewById(R.id.txt_distributor_visit_call_sync_count);
        switch_distributor_visit_call_sync=findViewById(R.id.switch_distributor_visit_call_sync);
        view_distributor_visit_call=findViewById(R.id.view_distributor_visit_call);

        rel_distributor_regular_call=findViewById(R.id.rel_distributor_regular_call);
        txt_distributor_regular_call_sync_count=findViewById(R.id.txt_distributor_regular_call_sync_count);
        switch_distributor_regular_call_sync=findViewById(R.id.switch_distributor_regular_call_sync);
        view_distributor_regular_call=findViewById(R.id.view_distributor_regular_call);

        linear_sync_data=findViewById(R.id.linear_sync_data);

        // Retailer Visit Call
        listRetailerVisitCall=new ArrayList<>();
        listRetailerVisitCall=dbConnector.getRetailerVisitCall();
        if(listRetailerVisitCall!=null && listRetailerVisitCall.size()>0){
            rel_call_sync.setVisibility(View.VISIBLE);
            view_retailer_visit_call.setVisibility(View.VISIBLE);
            txt_retailer_visit_call_sync_count.setText(listRetailerVisitCall.size()+" Retailer Visit Calls pending from Sync");
        }else{
            rel_call_sync.setVisibility(View.GONE);
            displaySnackBar("No data available !!!");
        }

        switch_retailer_visit_call_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet()){
                    if(switch_retailer_visit_call_sync.isChecked()){
                        if(listRetailerVisitCall!=null && listRetailerVisitCall.size()>0){
                            progDisp();
                            retailerVisitCallApiCall();
                        }
                    }
                }else{
                    dispNetworkError();
                    switch_retailer_visit_call_sync.setChecked(false);
                }

            }
        });


    }

    private void retailerVisitCallApiCall(){

        final RetailerVisitCallModel data=listRetailerVisitCall.get(0);

        RequestBody req_app_version = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppVersionCode()));
        RequestBody req_android_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getAppAndroidId());
        RequestBody req_device_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredId()));
        RequestBody req_user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredUserId()));
        RequestBody req_key = RequestBody.create(MediaType.parse("text/plain"), Config.ACCESS_KEY);
        RequestBody req_company_id = RequestBody.create(MediaType.parse("text/plain"),getSharedPref.getCompanyId());
        RequestBody req_branch_id = RequestBody.create(MediaType.parse("text/plain"),getSharedPref.getBranchId());
        RequestBody req_call_type = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_CALL_TYPE());
        RequestBody req_retailer_id = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody req_loc_address = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_LOC_ADDRESS());
        RequestBody req_loc_lat = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_LATITUDE());
        RequestBody req_loc_lng = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_LONGITUDE());
        RequestBody req_gps_flag = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_GPS_FLAG());
        RequestBody req_loc_accuracy = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(data.getRETAILER_VISIT_LOC_ACCURACY()));
        RequestBody req_remarks = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_SUGGESTIONS());

        RequestBody req_shop_name=null,req_con_person=null,req_mb=null,req_city_id=null,req_addr1=null,req_addr2=null;


        req_shop_name = RequestBody.create(MediaType.parse("text/plain"),data.getRETAILER_VISIT_SHOP_NAME());
        req_con_person = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_PERSON_NMAE());
        req_mb = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_MOBILE());
        req_city_id = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_CITY_ID());
        req_addr1 = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_ADDRESS1());
        req_addr2 = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_ADDRESS2());
//        RequestBody req_purpose = RequestBody.create(MediaType.parse("text/plain"),  "TEST");
        RequestBody req_purpose = RequestBody.create(MediaType.parse("text/plain"),  "");
        RequestBody req_next_followup_date = RequestBody.create(MediaType.parse("text/plain"), data.getRETAILER_VISIT_NEXT_FOLOWUP_DATE());


        try{
            File file=null;

            try {


                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                file = File.createTempFile(
                        String.valueOf(new Date().getTime()),  /* prefix */
                        ".png",         /* suffix */
                        storageDir      /* directory */
                );

                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data.getRETAILER_VISIT_PHOTO());
                fos.close();
            } catch (Exception e) {
                //Log.e(TAG, e.getMessage());
            }


           /* ByteArrayInputStream bis = new ByteArrayInputStream(data.getDISTRIBUTOR_REGULAR_PHOTO());
            ObjectInputStream ois = new ObjectInputStream(bis);
            File fileFromBytes = (File) ois.readObject();
            bis.close();
            ois.close();*/

            RequestBody mFile = RequestBody.create(MediaType.parse("image*//*"), file);

            fileToUploadPassport = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        }catch (Exception ex){}


        Call<Insert_Retailer_And_Call_Visit_Response> call = apiService.Insert_New_And_Regular_Call_Visit_Details(req_app_version,
                req_android_id,
                req_device_id,
                req_user_id,
                req_key,
                req_company_id,
                req_branch_id,
                req_call_type,
                req_retailer_id,
                req_loc_address,
                req_loc_lat,
                req_loc_lng,
                req_gps_flag,
                req_loc_accuracy,
                fileToUploadPassport,
                req_remarks,
                req_shop_name,
                req_con_person,
                req_mb,
                req_city_id,
                req_addr1,
                req_addr2,
                req_next_followup_date,req_purpose
        );

        call.enqueue(new Callback<Insert_Retailer_And_Call_Visit_Response>() {
            @Override
            public void onResponse(Call<Insert_Retailer_And_Call_Visit_Response> call, final Response<Insert_Retailer_And_Call_Visit_Response> response) {

                if((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                    if(response.body().getFLAG()==1){
                        int rowCount=dbConnector.deleteRetailerVisitCall(data.getRETAILER_VISIT_ID());
                        listRetailerVisitCall=dbConnector.getRetailerVisitCall();
                        if(listRetailerVisitCall!=null && listRetailerVisitCall.size()>0 && rowCount>0){
                            retailerVisitCallApiCall();
                        }else{
                            try{
                                dismissProg();
                                dialogSuccess = new SweetAlertDialog(Activity_Sync_Data.this, SweetAlertDialog.SUCCESS_TYPE);
                                dialogSuccess.setTitleText(getString(R.string.sorder_good_job));
                                dialogSuccess.setContentText(response.body().getMSG());

                                dialogSuccess.setCancelable(false);
                                dialogSuccess.show();

                                dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogSuccess.dismissWithAnimation();
                                        dialogSuccess.cancel();
                                        rel_call_sync.setVisibility(View.GONE);
                                        view_retailer_visit_call.setVisibility(View.GONE);
                                    }
                                });

                            }catch (Exception ex){
                                dismissProg();
                            }
                        }
                    }else{
                        try{
                            dismissProg();
                            dialogSuccess = new SweetAlertDialog(Activity_Sync_Data.this, SweetAlertDialog.ERROR_TYPE);
                            dialogSuccess.setTitleText(getString(R.string.sorder_oops));
                            dialogSuccess.setContentText(response.body().getMSG());

                            dialogSuccess.setCancelable(false);
                            dialogSuccess.show();

                            dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogSuccess.dismissWithAnimation();
                                    dialogSuccess.cancel();
                                }
                            });

                        }catch (Exception ex){
                            dismissProg();
                        }
                    }

                }else{
                    // Log.e("Error in res",response.message());
                    dismissProg();
                    displaySnackBar("Something went wrong,try again !!!");
                }

            }

            @Override
            public void onFailure(Call<Insert_Retailer_And_Call_Visit_Response> call, Throwable t) {
                dismissProg();

                //Log.e("Error in 111",t.getMessage());
            }
        });

    }

    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(linear_sync_data, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }

    private void progDisp(){
        try {
            progDialog = new ProgressDialog(Activity_Sync_Data.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request_call));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) { }
    }

    private void dismissProg(){
        try {
            if (progDialog != null && progDialog.isShowing()) {
                progDialog.dismiss();
            }
        } catch (Exception ex) { }
    }

    private void dispNetworkError(){
        displaySnackBar(getResources().getString(R.string.title_no_internet));
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
