package com.infinity.infoway.vimal.delear.activity.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.Complaint.Insert_RoutWise_Complaint_Pojo;
import com.infinity.infoway.vimal.util.common.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Complaint extends Fragment implements View.OnClickListener {


    public Complaint() {
        // Required empty public constructor
    }


    private static final int GALLERY_REQUEST = 1;
    TextInputLayout tv_input_retailer, tv_input_shop_name, tv_input_mobile, tv_input_area, tv_input_village, tv_input_city, tv_input_district, et_input_complain_for;
    EditText et_select_retailer, et_shop_name, et_mobile, et_area, et_village, et_city, et_district, et_complain_for, et_complaint_photo, et_complain_details;
    Button btn_submit_complain, btn_upload_photo_and_video;


    ImageView complaint_image_close;
    ProgressDialog progDialog;
    ApiInterface apiInterface;
    ConstraintLayout main_Complaint;

    private SharedPref getSharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        initView(view);
        return view;
    }


    private Insert_RoutWise_Complaint_Pojo insert_routWise_complaint_pojo;

    private void insert_RoutWise_Complaint() {


        /** Getting all text fields value to final submit**/

        String SHOP_NAME = et_shop_name.getText().toString().trim();
        String RETAILER_NAME = et_select_retailer.getText().toString().trim();
        String MOBILE_NO = et_mobile.getText().toString().trim();
        String AREA_NAME = et_area.getText().toString().trim();
        String VILLAGE_NAME = et_village.getText().toString().trim();
        String CITY_NAME = et_city.getText().toString().trim();
        String DISTRICT_NAME = et_district.getText().toString().trim();
        String COMPLAINTFor = et_complain_for.getText().toString().trim();
        String COMPLAINT = et_complain_details.getText().toString().trim();


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

        RequestBody AppVersionCode = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppVersionCode()));
        RequestBody AppAndroidId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppAndroidId()));

//        RequestBody Type_api = RequestBody.create(MediaType.parse("text/plain"), Type);
        RequestBody reg_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredId()));
        RequestBody reg_user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredUserId()));
        RequestBody req_key = RequestBody.create(MediaType.parse("text/plain"), Config.ACCESS_KEY);
        RequestBody req_company_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getCompanyId());
        RequestBody req_retailer_name = RequestBody.create(MediaType.parse("text/plain"), RETAILER_NAME);
        RequestBody req_shop_name = RequestBody.create(MediaType.parse("text/plain"), SHOP_NAME);
        RequestBody req_mobile_no = RequestBody.create(MediaType.parse("text/plain"), MOBILE_NO);
        RequestBody req_area_name = RequestBody.create(MediaType.parse("text/plain"), AREA_NAME);
        RequestBody req_village_name = RequestBody.create(MediaType.parse("text/plain"), VILLAGE_NAME);
        RequestBody req_city_name = RequestBody.create(MediaType.parse("text/plain"), CITY_NAME);
        RequestBody req_district_name = RequestBody.create(MediaType.parse("text/plain"), DISTRICT_NAME);
        RequestBody req_complain_for = RequestBody.create(MediaType.parse("text/plain"), COMPLAINTFor);
        RequestBody req_complaint_details = RequestBody.create(MediaType.parse("text/plain"), COMPLAINT);

        Call<Insert_RoutWise_Complaint_Pojo> call = apiInterface.insert_routWise_complaint(
                AppVersionCode,
                AppAndroidId,
                reg_id,
                reg_user_id,
                req_key,
                req_company_id,
                req_retailer_name,
                req_shop_name,
                req_mobile_no,
                req_area_name,
                req_village_name,
                req_city_name,
                req_district_name,
                req_complain_for,
                req_complaint_details,
                fileToUploadPassport);


       /* Call<Insert_RoutWise_Complaint_Pojo> call = apiInterface.insert_routWise_complaint(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,

                getSharedPref.getCompanyId() + "",
                RETAILER_NAME,
                SHOP_NAME, MOBILE_NO,
                AREA_NAME, VILLAGE_NAME, CITY_NAME,
                DISTRICT_NAME, COMPLAINTFor, COMPLAINT,
                fileToUploadPassport


        );*/


        call.enqueue(new Callback<Insert_RoutWise_Complaint_Pojo>() {
            @Override
            public void onResponse(Call<Insert_RoutWise_Complaint_Pojo> call, Response<Insert_RoutWise_Complaint_Pojo> response) {
                if (response.isSuccessful()) {
                    progDialog.dismiss();
                    insert_routWise_complaint_pojo = response.body();
                    if (insert_routWise_complaint_pojo != null) {

                        displaySnackBar(insert_routWise_complaint_pojo.getMESSAGE() + "");
                        et_select_retailer.setText("");
                        et_shop_name.setText("");
                        et_mobile.setText("");
                        et_area.setText("");
                        et_village.setText("");
                        et_city.setText("");
                        et_district.setText("");
                        et_complain_for.setText("");
                        et_complain_details.setText("");
                        et_complaint_photo.setText("");
                        complaint_image_close.setVisibility(View.GONE);


                    }


                }

            }

            @Override
            public void onFailure(Call<Insert_RoutWise_Complaint_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });


        /** Getting all text fields value to final submit**/


    }

    private Snackbar paymentSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(main_Complaint, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }

    private void initView(View view) {

        /**TextInput Layout**/
        tv_input_retailer = (TextInputLayout) view.findViewById(R.id.txt_input_retailer);
        tv_input_shop_name = (TextInputLayout) view.findViewById(R.id.tv_input_shop_name);
        tv_input_mobile = (TextInputLayout) view.findViewById(R.id.tv_input_mobile);
        tv_input_area = (TextInputLayout) view.findViewById(R.id.tv_input_area);
        tv_input_village = (TextInputLayout) view.findViewById(R.id.tv_input_village);
        tv_input_city = (TextInputLayout) view.findViewById(R.id.tv_input_city);
        tv_input_district = (TextInputLayout) view.findViewById(R.id.tv_input_district);
        et_input_complain_for = (TextInputLayout) view.findViewById(R.id.et_input_complain_for);

        getSharedPref = new SharedPref(getActivity());
        /**Button**/
        btn_submit_complain = (Button) view.findViewById(R.id.btn_submit_complain);
        btn_submit_complain.setOnClickListener(this);
        btn_upload_photo_and_video = (Button) view.findViewById(R.id.btn_upload_photo_and_video);
        btn_upload_photo_and_video.setOnClickListener(this);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        main_Complaint = view.findViewById(R.id.main_Complaint);

        /**Editext**/
        et_select_retailer = (EditText) view.findViewById(R.id.et_select_retailer);
        et_select_retailer.setOnClickListener(this);
        et_shop_name = (EditText) view.findViewById(R.id.et_shop_name);
        et_mobile = (EditText) view.findViewById(R.id.et_mobile);
        et_area = (EditText) view.findViewById(R.id.et_area);
        et_village = (EditText) view.findViewById(R.id.et_village);
        et_city = (EditText) view.findViewById(R.id.et_city);
        et_district = (EditText) view.findViewById(R.id.et_district);
        et_complain_for = (EditText) view.findViewById(R.id.et_complain_for);
        et_complaint_photo = (EditText) view.findViewById(R.id.et_complaint_photo);
        et_complaint_photo.setOnClickListener(this);
        et_complain_details = (EditText) view.findViewById(R.id.et_complain_details);

        complaint_image_close = view.findViewById(R.id.complaint_image_close);
        complaint_image_close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit_complain) {
            if (et_select_retailer.getText().toString().contentEquals("")) {
                Toast.makeText(getActivity(), "Please Select Retailer Name", Toast.LENGTH_LONG).show();

            } else if (et_complain_details.getText().toString().contentEquals("")) {

                Toast.makeText(getActivity(), "Please Enter Valid Complaint Details", Toast.LENGTH_LONG).show();
            } /*else if (et_complaint_photo.getText().toString().contentEquals("")) {

                Toast.makeText(getActivity(), "Please Select Complaint Photo", Toast.LENGTH_LONG).show();
            }*/ else {

                System.out.println("validated==== inserting users complaint");
                insert_RoutWise_Complaint();

            }

        } else if (v.getId() == R.id.et_select_retailer) {
            Intent intent = new Intent(getActivity(), Activity_Select_City.class);
            intent.putExtra("isFromCitySelect", 9899);
            startActivityForResult(intent, 9888);

        } else if (v.getId() == R.id.et_complaint_photo) {
            if (et_complaint_photo.getText().toString().contentEquals("")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, GALLERY_REQUEST);
            } else {

                final android.app.AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.complaint_photo_view, null, false);

                //complaint_image

                ImageView complaint_image = view.findViewById(R.id.complaint_image);
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                complaint_image.setImageBitmap(myBitmap);


                alertDialog.setView(view);
                alertDialog.show();
            }


        } else if (v.getId() == R.id.complaint_image_close) {
            et_complaint_photo.setText("");
            fileToUploadPassport = null;
            complaint_image_close.setVisibility(View.GONE);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9888) {
            if (data.hasExtra("Cus_Name")) {
                et_select_retailer.setText(data.getExtras().getString("Cus_Name"));

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


        } else if (requestCode == GALLERY_REQUEST) {

            try {

                Uri uri;

                if (data.getData() == null) {
                    uri = (Uri) data.getExtras().get("data");
                } else {
                    uri = data.getData();

                }

                String fileUrl = FileUtils.getPath(getActivity(), uri);

                file = new File(fileUrl);

                if ((file.length() / 1024) > 2048) {
                    String file_extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
                    if ((!TextUtils.isEmpty(file_extension)) && (!file_extension.equalsIgnoreCase("jpg"))) {
                        file = saveBitmapToFile(file);

                    }
                }

                System.out.println("size-------" + file.length());
                et_complaint_photo.setText(file.getName());
                complaint_image_close.setVisibility(View.VISIBLE);
                RequestBody mFile = RequestBody.create(MediaType.parse(IMG_JPEG), file);

                fileToUploadPassport = MultipartBody.Part.createFormData("Docfile", file.getName(), mFile);
                System.out.println(fileToUploadPassport);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static final String IMG_JPEG = "image/jpeg";
    File file;
    private MultipartBody.Part fileToUploadPassport = null;
    private Bitmap orientedBitmap = null;

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            orientedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            orientedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * test of stfalcon imageviewer
     **/

    List<Uri> selected_image = new ArrayList<>();
    /**test of stfalcon imageviewer**/


}
