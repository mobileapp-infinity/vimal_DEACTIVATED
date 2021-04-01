package com.infinity.infoway.vimal.delear.activity.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.FeedBack.Insert_RoutWise_FeedBack_Pojo;
import com.infinity.infoway.vimal.util.common.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment implements View.OnClickListener {


    private static final int REQ_FFEDBACK_CODE = 856;
    private TextInputLayout tv_input_retailer_name, tv_input_shop_name, tv_input_mobile, tv_input_area, tv_input_village, tv_input_city, tv_input_district, tv_input_feedback, tv_photo_or_video;
    private EditText et_select_retailer_name, et_feedback, et_shop_name, et_mobile, et_area, et_village, et_city, et_district, et_feedback_attachment;
    Button btn_submit_feedback;


    /**
     * initial items
     **/

    private ImageView feedback_image_close;
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
        feedback_image_close = view.findViewById(R.id.feedback_image_close);
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
        feedback_image_close.setOnClickListener(this);


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

                try {
                    insert_RoutWise_FeedBack();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        } else if (v.getId() == R.id.et_select_retailer_name) {
            Intent intent = new Intent(getActivity(), Activity_Select_City.class);
            intent.putExtra("isFromCitySelect", 9899);
            startActivityForResult(intent, 9888);

        } else if (v.getId() == R.id.et_feedback_attachment) {
            browseDocuments();


        } else if (v.getId() == R.id.feedback_image_close) {
            feedback_image_close.setVisibility(View.GONE);
            et_feedback_attachment.setText("");
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

      /*  Call<Insert_RoutWise_FeedBack_Pojo> call = apiService.insert_routWise_feedBack(
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


        );*/

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
        RequestBody req_feedback = RequestBody.create(MediaType.parse("text/plain"), Feedback);
        // RequestBody req_complaint_details = RequestBody.create(MediaType.parse("text/plain"), COMPLAINT);

        Call<Insert_RoutWise_FeedBack_Pojo> call = apiService.insert_routWise_feedBack(
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
                req_feedback,

                file);

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
                        et_feedback_attachment.setText("");
                        file = null;

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

    private MultipartBody.Part file = null;

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


        } else if (requestCode == REQ_FFEDBACK_CODE) {


            try {

                Uri uri;

                if (data.getData() == null) {
                    uri = (Uri) data.getExtras().get("data");
                } else {
                    uri = data.getData();

                }

                String fileUrl = FileUtils.getPath(getActivity(), uri);

                File file = new File(fileUrl);

                //  if ((file.length() / 1024) > 2048) {
                String file_extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
               /* if ((!TextUtils.isEmpty(file_extension)) && (file_extension.equalsIgnoreCase("jpeg"))) {
                    file = saveBitmapToFile(file);

                } else if ((!TextUtils.isEmpty(file_extension)) && (!file_extension.equalsIgnoreCase("mp3"))) {
                    et_feedback_attachment.setText(file.getName());
                }*/
                RequestBody mFile = null;
                if (file_extension.equals("jpeg")) {
                    mFile = RequestBody.create(MediaType.parse(IMG_JPEG), file);
                    et_feedback_attachment.setText(file.getName());
                    feedback_image_close.setVisibility(View.VISIBLE);
                }
                if (file_extension.equals("jpg")) {
                    mFile = RequestBody.create(MediaType.parse(IMG_JPEG), file);
                    et_feedback_attachment.setText(file.getName());
                    feedback_image_close.setVisibility(View.VISIBLE);
                }
                if (file_extension.equals("mp3")) {
                    mFile = RequestBody.create(MediaType.parse(audio), file);
                    et_feedback_attachment.setText(file.getName());
                    feedback_image_close.setVisibility(View.VISIBLE);
                }

                if (file_extension.equals("mp4")) {
                    //if (file.get)
                    int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
                    et_feedback_attachment.setText(file.getName());
                    feedback_image_close.setVisibility(View.VISIBLE);
                    if (file_size < 8000) {
                        mFile = RequestBody.create(MediaType.parse(video), file);
                        et_feedback_attachment.setText(file.getName());
                        feedback_image_close.setVisibility(View.VISIBLE);
                    } else {
                        mFile = null;
                        Toast.makeText(getActivity(), "Please Select Fiel Less Than 8 mb", Toast.LENGTH_SHORT).show();
                        et_feedback_attachment.setText("");
                        feedback_image_close.setVisibility(View.GONE);
                    }

                }

                //  }

                System.out.println("size-------" + file.length());



                // RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), file);

                this.file = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                System.out.println(file);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /*private String _getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }*/
    private static final String IMG_JPEG = "image/jpeg";
    private static final String audio = "audio/*";
    private static final String video = "video/mp4*";


    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(getActivity(), uri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column);
        cursor.close();
        return result;
    }

    void openFile(Context context, File url) throws IOException {
        // Create URI
        File file = url;
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void browseDocuments() {
/*

        String[] mimeTypes =
                {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/audio",
                        "application/image",
                        "application/zip"};
*/

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);


        intent.setType("*/*");
        String[] mimetypes = {"image/*", "video/*", "audio/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);


        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), REQ_FFEDBACK_CODE);

    }

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

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}