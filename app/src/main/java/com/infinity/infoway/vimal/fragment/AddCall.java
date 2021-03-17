package com.infinity.infoway.vimal.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.infinity.infoway.vimal.BuildConfig;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.Insert_Retailer_And_Call_Visit_Response;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.model.GPSMasterBean;
import com.infinity.infoway.vimal.model.RetailerVisitCallModel;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCall extends Fragment implements View.OnClickListener {

    private SweetAlertDialog dialogSuccess;

    private FusedLocationProviderClient mFusedLocationClient;
    private ApiInterface apiService;
    private ConnectionDetector cd;
    private double curLatitude = 0.0, curLongitude = 0.0, locAccuracy = 0.0;
    private int gpsFlag = 0;
    private String LocationAddress = "";
    private LocationManager manager;
    private boolean statusOfGPS;

    private ProgressDialog progDialog;
    private SharedPref getSharedPref;

    private LinearLayout linear_call_new, linear_regular_call, linear_annexure_send_upload;
    private Dialog bottomSheetDialog;
    private Button btnConfirmAddExpense, btnCancelAddExpense;
    private TextInputLayout txt_input_retailer, txt_input_select_retailer_regular, txt_inout_distributor, txt_input_price_list;
    private ScrollView scroll_view_add_call;
    private Snackbar paymentSnackBar;
    private Bitmap selectedBitmap;
    private byte[] imageInByte = null;
    private Dialog fullScreenDialog;
    private String imageNameDisplay="";
    private Context context;
    private RadioGroup rg_call;
    private RadioButton rb_new_visit, rb_new_call, rb_returning_call;
    private EditText et_retailer, et_name, et_mobile, et_next_followupdate, et_select_city, et_state, et_country, et_address1, et_address2, et_select_distributor, et_select_price_list, et_remarks_new_call;
    private ImageView imgAnnexureSend, imgAnnexureSendDelete, imgAnnexureSendUpload;
    private TextView txt_annexure_file_name;
    private Button btn_add_retailer;
    private String selectedCityId, selectedCityName;
    private String selectedCityIdRegular, selectedCityNameRegular;
    private String selectedDistributorID, selectedDistributorName;
    private String selectedDistributorIDRegular, selectedDistributorNameRegular;
    private String selectedRetailerIDRegular, selectedRetailerNameRegular;
    private RequestBody filenamePassport;
    private MultipartBody.Part fileToUploadPassport;
    private String mCurrentPhotoPath = null;
    private Uri mCapturedImageURI = null;
    private static final int CAMERA_REQUEST = 1001;
    private File photoFile = null;
    // Storage for camera image URI components
    private final static String CAPTURED_PHOTO_PATH_KEY = "mCurrentPhotoPath";
    private final static String CAPTURED_PHOTO_URI_KEY = "mCapturedImageURI";

    private String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private boolean is_call = false;

    // Regular call
    private EditText et_select_retailer_regular, et_mobile_regular, et_name_regular, et_select_city_regular, et_state_regular, et_country_regular, et_address1_regular, et_address2_regular, et_select_distributor_regular;
    private TextView txt_title_photo_upload_regular, txt_annexure_file_name_regular;
    private ImageView imgAnnexureSend_regular, imgAnnexureSendDelete_regular, imgAnnexureSendUpload_regular;
    private Button btn_add_retailer_regular;

    private Canvas canvas;
    private DBConnector dbConnector;
    private Intent batteryStatus;
    private IntentFilter ifilter;
    private Date today;
    private SimpleDateFormat sdf;
    private TextInputLayout txt_input_next_folow_up_date;
    private String resPvsdID;
    private Date selectedFromDate;
    private SimpleDateFormat sdf_full, serverDateFormat;
    private String selectedFromDateString;

    private String retailerPriceListId, retailerPriceListName;
    private AlertDialog alertDialog;

    public static AddCall newInstance() {
        AddCall fragment = new AddCall();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_call, container, false);

        is_call = getArguments().getBoolean("is_call");

        initControls(view);
        return view;
    }


    private void initControls(View view) {

        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dbConnector = new DBConnector(context);
        getSharedPref = new SharedPref(context);
        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        if(getSharedPref!=null) {
            getSharedPref.SET_SHOULD_CALL_API(true);
        }
        batteryStatus = context.registerReceiver(null, ifilter);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        cd = new ConnectionDetector(context);

        scroll_view_add_call = view.findViewById(R.id.scroll_view_add_call);

        linear_call_new = view.findViewById(R.id.linear_call_new);
        linear_regular_call = view.findViewById(R.id.linear_regular_call);
        linear_annexure_send_upload = view.findViewById(R.id.linear_annexure_send_upload);

        rg_call = view.findViewById(R.id.rg_call);
        rb_new_visit = view.findViewById(R.id.rb_new_visit);
        rb_new_call = view.findViewById(R.id.rb_new_call);
        rb_returning_call = view.findViewById(R.id.rb_returning_call);
        btn_add_retailer = view.findViewById(R.id.btn_add_retailer);

        txt_input_next_folow_up_date = view.findViewById(R.id.txt_input_next_folow_up_date);

        if (!is_call) {
            //linear_annexure_send_upload.setVisibility(View.GONE);
            rg_call.setVisibility(View.GONE);
            linear_call_new.setVisibility(View.VISIBLE);
            linear_regular_call.setVisibility(View.GONE);
            btn_add_retailer.setText("Add Retailer");
            txt_input_next_folow_up_date.setVisibility(View.GONE);
        } else {
            linear_annexure_send_upload.setVisibility(View.VISIBLE);
            rg_call.setVisibility(View.VISIBLE);
            linear_call_new.setVisibility(View.GONE);
            linear_regular_call.setVisibility(View.GONE);

        }

        txt_input_retailer = view.findViewById(R.id.txt_input_retailer);

        et_retailer = view.findViewById(R.id.et_retailer);
        et_name = view.findViewById(R.id.et_name);
        et_mobile = view.findViewById(R.id.et_mobile);
        et_next_followupdate = view.findViewById(R.id.et_next_followupdate);
        et_select_city = view.findViewById(R.id.et_select_city);
        et_state = view.findViewById(R.id.et_state);
        et_country = view.findViewById(R.id.et_country);
        et_address1 = view.findViewById(R.id.et_address1);
        et_address2 = view.findViewById(R.id.et_address2);
        et_select_distributor = view.findViewById(R.id.et_select_distributor);
        txt_input_price_list = view.findViewById(R.id.txt_input_price_list);
        et_select_price_list = view.findViewById(R.id.et_select_price_list);
        txt_inout_distributor = view.findViewById(R.id.txt_inout_distributor);
        et_remarks_new_call = view.findViewById(R.id.et_remarks_new_call);

        imgAnnexureSend = view.findViewById(R.id.imgAnnexureSend);
        imgAnnexureSendDelete = view.findViewById(R.id.imgAnnexureSendDelete);
        imgAnnexureSendUpload = view.findViewById(R.id.imgAnnexureSendUpload);

        txt_annexure_file_name = view.findViewById(R.id.txt_annexure_file_name);

        // Regular call

        txt_input_select_retailer_regular = view.findViewById(R.id.txt_input_select_retailer_regular);
        et_select_retailer_regular = view.findViewById(R.id.et_select_retailer_regular);
        et_name_regular = view.findViewById(R.id.et_name_regular);
        et_mobile_regular = view.findViewById(R.id.et_mobile_regular);
        et_select_city_regular = view.findViewById(R.id.et_select_city_regular);
        et_state_regular = view.findViewById(R.id.et_state_regular);
        et_country_regular = view.findViewById(R.id.et_country_regular);
        et_address1_regular = view.findViewById(R.id.et_address1_regular);
        et_address2_regular = view.findViewById(R.id.et_address2_regular);
        et_select_distributor_regular = view.findViewById(R.id.et_select_distributor_regular);

        txt_title_photo_upload_regular = view.findViewById(R.id.txt_title_photo_upload_regular);
        txt_annexure_file_name_regular = view.findViewById(R.id.txt_annexure_file_name_regular);

        imgAnnexureSend_regular = view.findViewById(R.id.imgAnnexureSend_regular);
        imgAnnexureSendDelete_regular = view.findViewById(R.id.imgAnnexureSendDelete_regular);
        imgAnnexureSendUpload_regular = view.findViewById(R.id.imgAnnexureSendUpload_regular);

        btn_add_retailer_regular = view.findViewById(R.id.btn_add_retailer_regular);


        imgAnnexureSendUpload_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPassPortPhotoTpload();
            }
        });

        imgAnnexureSend_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedBitmap != null) {
                    fullScreenImageDisplay();
                } else {
                    displaySnackBar("Please capture image !!!");
                }
            }
        });

        imgAnnexureSendDelete_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedBitmap = null;
                txt_annexure_file_name_regular.setText("");
            }
        });


        imgAnnexureSendUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPassPortPhotoTpload();
            }
        });

        imgAnnexureSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedBitmap != null) {
                    fullScreenImageDisplay();
                } else {
                    displaySnackBar("Please capture image !!!");
                }
            }
        });

        imgAnnexureSendDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedBitmap = null;
                txt_annexure_file_name.setText("");
            }
        });

        rg_call.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_new_visit) {
                    txt_input_next_folow_up_date.setVisibility(View.VISIBLE);
                    linear_regular_call.setVisibility(View.GONE);
                    linear_call_new.setVisibility(View.VISIBLE);
                    et_select_distributor.setVisibility(View.GONE);
                    txt_input_price_list.setVisibility(View.GONE);
                    txt_inout_distributor.setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_new_call) {
                    txt_input_next_folow_up_date.setVisibility(View.GONE);
                    et_select_distributor.setVisibility(View.VISIBLE);
                    linear_regular_call.setVisibility(View.GONE);
                    linear_call_new.setVisibility(View.VISIBLE);
                    txt_input_price_list.setVisibility(View.VISIBLE);
                    txt_inout_distributor.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rb_returning_call) {
                    txt_input_next_folow_up_date.setVisibility(View.GONE);
                    linear_call_new.setVisibility(View.GONE);
                    et_select_distributor.setVisibility(View.GONE);
                    linear_regular_call.setVisibility(View.VISIBLE);
                    txt_input_price_list.setVisibility(View.GONE);
                    txt_inout_distributor.setVisibility(View.VISIBLE);
                } else {
                    linear_call_new.setVisibility(View.GONE);
                    txt_input_next_folow_up_date.setVisibility(View.GONE);
                    linear_regular_call.setVisibility(View.GONE);
                    et_select_distributor.setVisibility(View.GONE);
                    txt_input_price_list.setVisibility(View.GONE);
                    txt_inout_distributor.setVisibility(View.GONE);
                }
            }
        });

        et_select_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Select_City.class);
                intent.putExtra("isFromCitySelect", 0);
                startActivityForResult(intent, 100);
            }
        });

        btn_add_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (is_call) {
                    if (rg_call.getCheckedRadioButtonId() == R.id.rb_new_call) {
                        if (TextUtils.isEmpty(et_retailer.getText().toString().trim())) {
                            displaySnackBar("Enter valid shop name !!!");
                        } else if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                            displaySnackBar("Enter valid contact person name !!!");
                        } else if (TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
                            displaySnackBar("Enter valid mobile no !!!");
                        } else if (TextUtils.isEmpty(et_select_city.getText().toString().trim())) {
                            displaySnackBar("Select valid city !!!");
                        } else if (TextUtils.isEmpty(et_select_distributor.getText().toString().trim())) {
                            displaySnackBar("Select valid distributor !!!");
                        }
                        else if (TextUtils.isEmpty(et_select_price_list.getText().toString().trim())) {
                            displaySnackBar("Select valid price list !!!");
                        }




                        else {
                            if (cd != null && cd.isConnectingToInternet()) {
                                openBottomSheetSialog();
                            } else {
                                if (imageInByte == null) {
                                    displaySnackBar("Try again camera image captured !!!");
                                } else {
                                    openBottomSheetSialog();
                                }

                            }
                        }
                    } else if (rg_call.getCheckedRadioButtonId() == R.id.rb_new_visit) {
                        if (TextUtils.isEmpty(et_retailer.getText().toString().trim())) {
                            displaySnackBar("Enter valid shop name !!!");
                        } else if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                            displaySnackBar("Enter valid contact person name !!!");
                        } else if (TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
                            displaySnackBar("Enter valid mobile no !!!");
                        } else if (TextUtils.isEmpty(et_select_city.getText().toString().trim())) {
                            displaySnackBar("Select valid city !!!");
                        } else if (TextUtils.isEmpty(et_next_followupdate.getText().toString().trim())) {
                            displaySnackBar("Select valid followup date !!!");
                        }
                        else if (imageNameDisplay.toString()==null||TextUtils.isEmpty(imageNameDisplay.toString().trim())) {
                            displaySnackBar("Select Photo to upload !!!");
                        }




                        else {
                            if (cd != null && cd.isConnectingToInternet()) {
                                openBottomSheetSialog();
                            } else {
                                if (imageInByte == null) {
                                    displaySnackBar("Try again camera image captured !!!");
                                } else {
                                    openBottomSheetSialog();
                                }
                            }
                        }
                    }
                } else {
                    if (TextUtils.isEmpty(et_retailer.getText().toString().trim())) {
                        displaySnackBar("Enter valid shop name !!!");
                    } else if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                        displaySnackBar("Enter valid contact person name !!!");
                    } else if (TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
                        displaySnackBar("Enter valid mobile no !!!");
                    } else if (TextUtils.isEmpty(et_select_city.getText().toString().trim())) {
                        displaySnackBar("Select valid city !!!");
                    } else if (TextUtils.isEmpty(et_select_distributor.getText().toString().trim())) {
                        displaySnackBar("Select valid distributor !!!");
                    } else if (TextUtils.isEmpty(et_select_price_list.getText().toString().trim())) {
                        displaySnackBar("Select valid price list !!!");
                    } else {
                        if (cd != null && cd.isConnectingToInternet()) {
                            openBottomSheetSialog();
                        } else {
                            if (imageInByte == null) {
                                displaySnackBar("Try again camera image captured !!!");
                            } else {
                                openBottomSheetSialog();
                            }
                        }
                    }
                }
            }
        });

        btn_add_retailer_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_select_distributor_regular.getText().toString().trim())) {
                    displaySnackBar("Select valid distributor !!!");
                } else if (TextUtils.isEmpty(et_select_retailer_regular.getText().toString().trim())) {
                    displaySnackBar("Select valid retailer name !!!");
                } else {
                    if (cd != null && cd.isConnectingToInternet()) {
                        openBottomSheetSialog();
                    } else {
                        if (imageInByte == null) {
                            displaySnackBar("Try again camera image captured !!!");
                        } else {
                            openBottomSheetSialog();
                        }
                    }
                }
            }
        });

        et_select_distributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Select_City.class);
                intent.putExtra("isFromCitySelect", 1);
                startActivityForResult(intent, 200);
            }
        });

        et_select_distributor_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Select_City.class);
                intent.putExtra("isFromCitySelect", 1);
                startActivityForResult(intent, 400);
            }
        });

        et_select_price_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Select_City.class);
                intent.putExtra("isFromCitySelect", 6);
                startActivityForResult(intent, 700);
            }
        });

        et_select_retailer_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Select_City.class);
                intent.putExtra("isFromCitySelect", 2);
                intent.putExtra("selected_group_id", selectedDistributorIDRegular);
                startActivityForResult(intent, 300);
            }
        });

        et_mobile_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_mobile_regular.getText().toString().trim())) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + et_mobile_regular.getText().toString().trim()));
                        startActivity(callIntent);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        et_next_followupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFromDateButtonClicked();
            }
        });

    }

    private void imgPassPortPhotoTpload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(context, RunTimePerMissions)) {
                ActivityCompat.requestPermissions(getActivity(), RunTimePerMissions, 101);
            } else {
                openCameraDialog(1);
            }
        } else {
            openCameraDialog(1);
        }

        getLastLocation();
    }

    private void dispNetworkError() {
        displaySnackBar(context.getResources().getString(R.string.title_no_internet));
    }


    private void openCameraDialog(final int imageUploadFlag) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go

            String timeStamp = "INFINITY_VISIT_";
            try {
                timeStamp += new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            } catch (Exception ex) {
                timeStamp += String.valueOf(System.currentTimeMillis());
            }

            try {
                photoFile = new File(getActivity().getExternalCacheDir(),
                        timeStamp + ".jpg");
            } catch (Exception ex) {
            }

           /* try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }*/
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                if (photoURI != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                } else {
                    displaySnackBar("Unable to load camera , try again !!!");
                }

            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            try {

//                Uri uri;
//
//                if (data.getData() == null) {
//                    photoUploadBitmap = (Bitmap) data.getExtras().get("data");
//                } else {
//                     photoUploadBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
//                }
//
//                uri=getImageUri(context,photoUploadBitmap,"Upload Documents");
//
//                file = new File(getAbsolutePath(uri));

               /* if ((photoFile.length() / 1024) > 2048) {
                    //String selectedImagePath = getAbsolutePath(data.getData());
                    // decodeFile(selectedImagePath);
                    photoFile = saveBitmapToFile(photoFile);
                }*/

                photoFile = saveBitmapToFile(photoFile);


                RequestBody mFile = RequestBody.create(MediaType.parse("image*//*"), photoFile);

                fileToUploadPassport = MultipartBody.Part.createFormData("file", photoFile.getName(), mFile);


                //filenamePassport = RequestBody.create(MediaType.parse("text/plain"), photoFile.getName());

                if (photoFile != null && !TextUtils.isEmpty(photoFile.getName())) {
                    imageNameDisplay = photoFile.getName();
                    txt_annexure_file_name_regular.setText(imageNameDisplay);
                    imgAnnexureSendDelete_regular.setVisibility(View.VISIBLE);
                    txt_annexure_file_name.setText(imageNameDisplay);
                    imgAnnexureSendDelete.setVisibility(View.VISIBLE);
                } else {
                    imageNameDisplay = "";
                }

              /*  try{
                    Bitmap bmp = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    imgAnnexureSend.setImageBitmap(bmp);
                }catch (Exception ex){
                    ex.printStackTrace();
                }*/


                //imgPassPortPhotoUploadApiCall();

            } catch (Exception ex) {
                Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 100) {
            if (data.hasExtra("CityId")) {
                if (rg_call.getCheckedRadioButtonId() == R.id.rb_returning_call) {
                    selectedCityNameRegular = data.getExtras().getString("CityName");
                    if (!TextUtils.isEmpty(data.getExtras().getString("CityId"))) {
                        selectedCityIdRegular = data.getExtras().getString("CityId");
                    }
                    if (!TextUtils.isEmpty(selectedCityNameRegular)) {
                        et_select_city_regular.setText(selectedCityNameRegular);
                    }

                } else {
                    selectedCityName = data.getExtras().getString("CityName");
                    if (!TextUtils.isEmpty(data.getExtras().getString("CityId"))) {
                        selectedCityId = data.getExtras().getString("CityId");
                    }
                    if (!TextUtils.isEmpty(selectedCityName)) {
                        et_select_city.setText(selectedCityName);
                    }

                    if (!TextUtils.isEmpty(data.getExtras().getString("StateName"))) {
                        et_state.setText(data.getExtras().getString("StateName"));
                    }
                    if (!TextUtils.isEmpty(data.getExtras().getString("CountryName"))) {
                        et_country.setText(data.getExtras().getString("CountryName"));
                    }

                    // getStateCountryApiCall(selectedCityId);
                }

            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (mCurrentPhotoPath != null) {
            savedInstanceState.putString(CAPTURED_PHOTO_PATH_KEY, mCurrentPhotoPath);
        }
        if (mCapturedImageURI != null) {
            savedInstanceState.putString(CAPTURED_PHOTO_URI_KEY, mCapturedImageURI.toString());
        }
        super.onSaveInstanceState(savedInstanceState);
    }


    //checking has runtime permmition or not
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 4;
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

            selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);


            Bitmap tempBitmap = ProcessingBitmap(selectedBitmap);

            if (tempBitmap != null) {
                selectedBitmap = tempBitmap;
            }

            if (!cd.isConnectingToInternet()) {
                imageInByte = null;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                imageInByte = stream.toByteArray();
            } else {
                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            }

            return file;
        } catch (Exception e) {
            return file;
        }
    }

    private Bitmap ProcessingBitmap(Bitmap bm1) {
        String captionString = "";
        try {
            captionString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        } catch (Exception ex) {
        }

        canvas = null;
        Bitmap newBitmap = null;
        try {
            Bitmap.Config config = bm1.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }
            newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
            canvas = new Canvas(newBitmap);
            canvas.drawBitmap(bm1, 0, 0, null);
            Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintText.setColor(Color.RED);
            paintText.setTextSize(50);
            paintText.setTextAlign(Paint.Align.RIGHT);
            paintText.setStyle(Paint.Style.FILL);
            // paintText.setShadowLayer(10f, 10f, 10f, Color.RED);
            Rect textRect = new Rect();
            paintText.getTextBounds(captionString, 0, captionString.length(), textRect);
           /* if(textRect.width() >= (canvas.getWidth() - 4))
                paintText.setTextSize(7);*/
            //int xPos = (canvas.getWidth() / 2) - 2;
            //int yPos = (int) ((canvas.getHeight() / 2) - ((paintText.descent() + paintText.ascent()) / 2));
            canvas.drawText(captionString, canvas.getWidth() - captionString.length(), canvas.getHeight() - 20, paintText);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newBitmap;
    }


    private void fullScreenImageDisplay() {

        if (fullScreenDialog != null && fullScreenDialog.isShowing()) {
            fullScreenDialog.dismiss();
        }

        try {
            fullScreenDialog = new Dialog(context);
            fullScreenDialog.setContentView(R.layout.layout_dialog_full_screen_image);
            fullScreenDialog.setCancelable(false);

            ImageView imgViewDisplay = fullScreenDialog.findViewById(R.id.imgViewDisplay);

            imgViewDisplay.setImageBitmap(selectedBitmap);

            ImageButton imgBtnClose = fullScreenDialog.findViewById(R.id.imgBtnClose);
            imgBtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fullScreenDialog.dismiss();
                }
            });

            fullScreenDialog.show();

        } catch (Exception ex) {
        }


    }

    private void storeImage(Bitmap mBitmap, String path) {
        OutputStream fOut = null;
        File file = new File(path);
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(scroll_view_add_call, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }


    private void openBottomSheetSialog() {
        try {
            bottomSheetDialog = new BottomSheetDialog(getActivity());
            bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet_double_confirmation);
            TextView txtBottomSheetTitle = bottomSheetDialog.findViewById(R.id.txtBottomSheetTitle);
            if (is_call) {
                txtBottomSheetTitle.setText(R.string.title_confirm_add_call);
            }

            btnConfirmAddExpense = bottomSheetDialog.findViewById(R.id.btnConfirmRegister);
            btnCancelAddExpense = bottomSheetDialog.findViewById(R.id.btnCancelRegistration);
            btnConfirmAddExpense.setOnClickListener(this);
            btnCancelAddExpense.setOnClickListener(this);
            bottomSheetDialog.setCancelable(true);

            if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                bottomSheetDialog.dismiss();
            }

            bottomSheetDialog.show();
        } catch (Exception ex) {
        }
    }
    private long lastClickTime = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnConfirmRegister:
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
                if (SystemClock.elapsedRealtime() - lastClickTime < Config.TIME_TILL_DISABLE_BTN) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();
                Insert_New_And_Regular_Call_Visit_DetailsApiCall("0", "");

                break;
            case R.id.btnCancelRegistration:
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
                break;
        }
    }


    private void Insert_New_And_Regular_Call_Visit_DetailsApiCall(String resRetailerId, final String resMessage) {

        String callType = "";
        String remarks = "";
        String nextFollowupDate = "";
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
        if (is_call) {
            if (rg_call.getCheckedRadioButtonId() == R.id.rb_returning_call) {
                callType = "2";
                System.out.println("call type 2 rrrrrrrrr");
            } else if (rg_call.getCheckedRadioButtonId() == R.id.rb_new_call) {
                callType = "1";
                if (!TextUtils.isEmpty(et_remarks_new_call.getText().toString().trim())) {
                    remarks = et_remarks_new_call.getText().toString().trim();
                    System.out.println("call type 1 ifrrrrrrrrrrr");
                }

            } else if (rg_call.getCheckedRadioButtonId() == R.id.rb_new_visit) {
                callType = "7";
                if (!TextUtils.isEmpty(et_remarks_new_call.getText().toString().trim())) {
                    remarks = et_remarks_new_call.getText().toString().trim();
                    System.out.println("call type 7 ifrrrrrrr");
                }
                if (!TextUtils.isEmpty(selectedFromDateString)) {
                    nextFollowupDate = selectedFromDateString;
                    System.out.println("call type elserrrrrrrr ");
                }
            }
        } else {
            callType = "3";
            System.out.println("call type 3rrrrrrrrrr");
        }

        try {
            progDialog = new ProgressDialog(context);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        RequestBody req_app_version = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppVersionCode()));
        RequestBody req_android_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getAppAndroidId());
        RequestBody req_device_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredId()));
        RequestBody req_user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredUserId()));
        RequestBody req_key = RequestBody.create(MediaType.parse("text/plain"), Config.ACCESS_KEY);
        RequestBody req_company_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getCompanyId());
        RequestBody req_branch_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getBranchId());
        RequestBody req_call_type = RequestBody.create(MediaType.parse("text/plain"), callType);
        RequestBody req_retailer_id = RequestBody.create(MediaType.parse("text/plain"), resRetailerId);
        RequestBody req_loc_address = RequestBody.create(MediaType.parse("text/plain"), LocationAddress);
        RequestBody req_loc_lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(curLatitude));
        RequestBody req_loc_lng = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(curLongitude));
        RequestBody req_gps_flag = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(isGPSON()));
        RequestBody req_loc_accuracy = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(locAccuracy));
        RequestBody req_remarks = RequestBody.create(MediaType.parse("text/plain"), remarks);

        RequestBody req_shop_name = null, req_con_person = null, req_mb = null, req_city_id = null, req_addr1 = null, req_addr2 = null;
        System.out.println("this is remar111k:::: "+remarks+"");

        if ((!TextUtils.isEmpty(resRetailerId)) && (resRetailerId.equalsIgnoreCase("0"))) {
            req_shop_name = RequestBody.create(MediaType.parse("text/plain"), et_retailer.getText().toString().trim());
            req_con_person = RequestBody.create(MediaType.parse("text/plain"), et_name.getText().toString().trim());
            req_mb = RequestBody.create(MediaType.parse("text/plain"), et_mobile.getText().toString().trim());
            req_city_id = RequestBody.create(MediaType.parse("text/plain"), selectedCityId);
            req_addr1 = RequestBody.create(MediaType.parse("text/plain"), et_address1.getText().toString().trim());
            req_addr2 = RequestBody.create(MediaType.parse("text/plain"), et_address2.getText().toString().trim());
        } else {
            req_shop_name = RequestBody.create(MediaType.parse("text/plain"), "");
            req_con_person = RequestBody.create(MediaType.parse("text/plain"), "");
            req_mb = RequestBody.create(MediaType.parse("text/plain"), "");
            req_city_id = RequestBody.create(MediaType.parse("text/plain"), "0");
            req_addr1 = RequestBody.create(MediaType.parse("text/plain"), "");
            req_addr2 = RequestBody.create(MediaType.parse("text/plain"), "");
        }

        RequestBody req_next_followup_date = RequestBody.create(MediaType.parse("text/plain"), nextFollowupDate);
        RequestBody req_purpose = RequestBody.create(MediaType.parse("text/plain"),  "");


        if (!cd.isConnectingToInternet()) {
            if (callType.equalsIgnoreCase("7")) {


                // Visit Call
                System.out.println("this is visit code!!!!!!!!!!!!");
                RetailerVisitCallModel data = new RetailerVisitCallModel();
                data.setRETAILER_VISIT_CALL_TYPE(callType);
                data.setRETAILER_VISIT_SHOP_NAME(et_retailer.getText().toString().trim());
                data.setRETAILER_VISIT_PERSON_NMAE(et_name.getText().toString().trim());
                data.setRETAILER_VISIT_MOBILE(et_mobile.getText().toString().trim());
                data.setRETAILER_VISIT_NEXT_FOLOWUP_DATE(nextFollowupDate);
                data.setRETAILER_VISIT_CITY_ID(selectedCityId);
                data.setRETAILER_VISIT_ADDRESS1(et_address1.getText().toString().trim());
                data.setRETAILER_VISIT_ADDRESS2(et_address2.getText().toString().trim());
                data.setRETAILER_VISIT_SUGGESTIONS(remarks);
                data.setRETAILER_VISIT_PHOTO(imageInByte);
                data.setRETAILER_VISIT_LATITUDE(String.valueOf(curLatitude));
                data.setRETAILER_VISIT_LONGITUDE(String.valueOf(curLongitude));
                data.setRETAILER_VISIT_LOC_ADDRESS(LocationAddress);
                data.setRETAILER_VISIT_GPS_FLAG(String.valueOf(isGPSON()));
                data.setRETAILER_VISIT_LOC_ACCURACY(String.valueOf(locAccuracy) + "");

                int AutoId = dbConnector.getRetailerVisitLastRecordAutoId();

                boolean flag = dbConnector.addRetailerVisitCall(data, AutoId + 1);


                if (flag) {
                    displaySnackBar("Call details saved successfully ");
                    emptyData();
                    getActivity().finish();


                } else if (imageInByte == null) {
                    displaySnackBar("Try again camera image captured !!!");
                } else {
                    displaySnackBar("Failed to save data , try again !!!");
                }

                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }

                try {
                    addLocationDataEvent();
                } catch (Exception ex) {
                }

            }
        } else {
            System.out.println("this is remar22222222222222k:::: "+remarks+"");
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
            Log.e("dataRequest", "" + call.toString());
            Log.e("dataRequest", "" + call.toString());
            call.enqueue(new Callback<Insert_Retailer_And_Call_Visit_Response>() {
                @Override
                public void onResponse(Call<Insert_Retailer_And_Call_Visit_Response> call, final Response<Insert_Retailer_And_Call_Visit_Response> response) {

                    try {
                        if (progDialog != null && progDialog.isShowing()) {
                            progDialog.dismiss();
                        }
                    } catch (Exception ex) {
                    }


                    if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                        try {
                            System.out.println("this is response "+response+"");
                            if (response.body().getFLAG() == 1) {
                                dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                dialogSuccess.setTitleText(getString(R.string.sorder_good_job));

                                if (!is_call && !TextUtils.isEmpty(resMessage)) {
                                    dialogSuccess.setContentText(resMessage);
                                } else {
                                    dialogSuccess.setContentText(response.body().getMSG());
                                }
                            } else {
                                dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                                dialogSuccess.setTitleText(getString(R.string.sorder_oops));

                                dialogSuccess.setContentText(response.body().getMSG());
                            }

                            dialogSuccess.setCancelable(false);
                            dialogSuccess.show();
                            dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogSuccess.dismissWithAnimation();
                                    dialogSuccess.cancel();
                                    if (response.body().getFLAG() == 1) {
                                        emptyData();
                                        getActivity().finish();

                                    } else {
                                        displaySnackBar("Something went wrong,try again !!!");
                                    }

                                }
                            });

                        } catch (Exception ex) {
                        }


                    } else {
                        // Log.e("Error in res",response.message());
                        System.out.println("wrong!!!! "+response+"");
                        displaySnackBar("Something went wrong,try again !!!");
                    }

                }

                @Override
                public void onFailure(Call<Insert_Retailer_And_Call_Visit_Response> call, Throwable t) {
                    try {
                        if (progDialog != null && progDialog.isShowing()) {
                            progDialog.dismiss();
                        }
                    } catch (Exception ex) {
                    }

                    //Log.e("Error in 111",t.getMessage());
                }
            });
        }


    }

    private void emptyData() {
        imageInByte = null;
        if (is_call) {
            if (rg_call.getCheckedRadioButtonId() == R.id.rb_returning_call) {
                et_select_retailer_regular.setText("");
                et_name_regular.setText("");
                et_mobile_regular.setText("");
                et_select_city_regular.setText("");
                et_state_regular.setText("");
                et_country_regular.setText("");
                et_address1_regular.setText("");
                et_address2_regular.setText("");
                selectedCityIdRegular = "";
                selectedCityNameRegular = "";
                selectedDistributorIDRegular = "";
                selectedDistributorNameRegular = "";
                txt_annexure_file_name_regular.setText("");
                txt_annexure_file_name.setText("");
                selectedBitmap = null;
                photoFile = null;
                fileToUploadPassport = null;
                imgAnnexureSendDelete_regular.setVisibility(View.GONE);
                imgAnnexureSendDelete.setVisibility(View.GONE);

                mCurrentPhotoPath = "";
                mCapturedImageURI = null;

                et_select_distributor.setText("");
                et_select_distributor_regular.setText("");
                et_next_followupdate.setText("");
                et_remarks_new_call.setText("");
                et_select_price_list.setText("");
            } else {
                et_retailer.setText("");
                et_name.setText("");
                et_mobile.setText("");
                et_select_city.setText("");
                et_state.setText("");
                et_country.setText("");
                et_address1.setText("");
                et_address2.setText("");
                selectedCityId = "";
                selectedCityName = "";
                selectedDistributorID = "";
                selectedDistributorName = "";
                txt_annexure_file_name.setText("");
                txt_annexure_file_name_regular.setText("");
                selectedBitmap = null;
                photoFile = null;
                mCurrentPhotoPath = "";
                mCapturedImageURI = null;
                fileToUploadPassport = null;
                imgAnnexureSendDelete.setVisibility(View.GONE);
                imgAnnexureSendDelete_regular.setVisibility(View.GONE);
                et_select_distributor.setText("");
                et_select_distributor_regular.setText("");
                et_next_followupdate.setText("");
                et_remarks_new_call.setText("");
                et_select_price_list.setText("");
            }
            rg_call.clearCheck();
        } else {
            et_retailer.setText("");
            et_name.setText("");
            et_mobile.setText("");
            et_select_city.setText("");
            et_state.setText("");
            et_country.setText("");
            et_address1.setText("");
            et_address2.setText("");
            selectedCityId = "";
            selectedCityName = "";
            selectedDistributorID = "";
            selectedDistributorName = "";
            txt_annexure_file_name.setText("");
            txt_annexure_file_name_regular.setText("");
            photoFile = null;
            selectedBitmap = null;
            mCurrentPhotoPath = "";
            mCapturedImageURI = null;
            fileToUploadPassport = null;
            imgAnnexureSendDelete.setVisibility(View.GONE);
            et_select_distributor.setText("");
            et_select_distributor_regular.setText("");
            et_next_followupdate.setText("");
            et_remarks_new_call.setText("");
            et_select_price_list.setText("");
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    if (location.getLatitude() > 0) {
                        curLatitude = location.getLatitude();
                        curLongitude = location.getLongitude();
                        locAccuracy = location.getAccuracy();
                        GetAddress(location.getLatitude(), location.getLongitude());
                    }

                }
            }
        });
    }

    public String GetAddress(double lat, double lng) {

        LocationAddress = "";

        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 5);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
               /* sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());*/

                LocationAddress = sb.toString();
                if (LocationAddress != null && LocationAddress.length() > 0 && LocationAddress.contains("null") || LocationAddress.contains("Null") || LocationAddress.contains("NULL")) {
                    if (LocationAddress.contains("null")) {
                        LocationAddress = LocationAddress.replace("null", "");
                    }
                    if (LocationAddress.contains("Null")) {
                        LocationAddress = LocationAddress.replace("Null", "");
                    }
                    if (LocationAddress.contains("NULL")) {
                        LocationAddress = LocationAddress.replace("NULL", "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LocationAddress;
    }

    private int isGPSON() {
        try {
            manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (statusOfGPS) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // getLastLocation();
    }


    private void addLocationDataEvent() {
        int minutes = 2;
        int MIN_FOR_LOCAL_STORE = 2;
        int MINIT_API_INTERVAL_IME_FROM_API = 2;
        try {
            MINIT_API_INTERVAL_IME_FROM_API = getSharedPref.getAPP_LOCATION_INTERVAL_TIME();


            // today = new Date();
            /*https://stackoverflow.com/questions/21285161/android-difference-between-two-dates*/
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());
            String getLAST_DB_DATA_STORE_TIME = getSharedPref.getLAST_DB_DATA_STORE_TIME();
            System.out.println("THIS CURRENT TIME >>>>>>>>>>>>>>>>>>>>>    " + currentDateandTime + "");
            System.out.println("THIS WAS LAST TIME >>>>>>>>>>>>>>>>>>>>>    " + getLAST_DB_DATA_STORE_TIME + "");

            Date OLD = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(getLAST_DB_DATA_STORE_TIME);
            Date CURRENT = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(currentDateandTime);
            long diff = CURRENT.getTime() - OLD.getTime();
            int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            int hours = (int) (diff / (1000 * 60 * 60));
            MIN_FOR_LOCAL_STORE = (int) (diff / (1000 * 60));
            // SEC_FOR_LOCAL_STORE = (int) (diff / (1000));

        } catch (Exception e) {

        }
        if (MIN_FOR_LOCAL_STORE >= MINIT_API_INTERVAL_IME_FROM_API) {
            if (dbConnector != null) {

                batteryStatus = context.registerReceiver(null, ifilter);

                today = new Date();
                GPSMasterBean data = new GPSMasterBean();

                data.setGPS_Location_Name("unspecified");
                data.setGPS_Latitude(String.valueOf(curLatitude));
                data.setGPS_Longitude(String.valueOf(curLongitude));

                data.setGPS_Address(LocationAddress);

                try {
                    data.setGPS_Battery_Percentage(String.valueOf(calculateBatteryPercentage()));
                } catch (Exception ex) {
                }

                if (cd != null) {
                    data.setGPS_Internet_Status(cd.isConnectingToInternet() ? "1" : "0");
                } else {
                    data.setGPS_Internet_Status("0");
                }

                data.setGPS_Status(String.valueOf(isGPSON()));

                try {
                    data.setGPS_DateTime(sdf.format(today));
                } catch (Exception ex) {
                }

                try {
                    data.setGPS_Accuracy(String.valueOf(locAccuracy));
                } catch (Exception ex) {
                }

                if (curLatitude > 0 && (!TextUtils.isEmpty(getSharedPref.getPreviousLat())) && !getSharedPref.getPreviousLat().equalsIgnoreCase("0.0")) {

                    Location priviusLocation = new Location("");//provider name is unnecessary
                    priviusLocation.setLatitude(Double.parseDouble(getSharedPref.getPreviousLat()));//your coords of course
                    priviusLocation.setLongitude(Double.parseDouble(getSharedPref.getPreviousLong()));

                    Location currentLocation = new Location("");
                    currentLocation.setLatitude(curLatitude);
                    currentLocation.setLongitude(curLongitude);

                    double distanceInMeters = currentLocation.distanceTo(priviusLocation);

//                if (distanceInMeters > getSharedPref.getDefaultDistance()) {
//                if (distanceInMeters > getSharedPref.getDefaultDistance() && locAccuracy < 100) {
                    if (distanceInMeters > getSharedPref.getDefaultDistance() && (locAccuracy < Config.ACCURACY || distanceInMeters >= Config.MIN_KM_FOR_ACCURACY)) {
//                    if (curLatitude > 0) {
//                    if (curLatitude > 0 && locAccuracy < 100) {
                        if (curLatitude > 0 && (locAccuracy < Config.ACCURACY || distanceInMeters >= Config.MIN_KM_FOR_ACCURACY)) {
                            getSharedPref.setPreviousLocation(String.valueOf(curLatitude), String.valueOf(curLongitude));
                        }
                        data.setGPS_Km_Travelled(String.format("%.2f", distanceInMeters / 1000));
                        data.setGPS_Is_Loc_Changed("1");
                    } else {
                        data.setGPS_Km_Travelled("0.0");
                        data.setGPS_Is_Loc_Changed("0");
                    }
                } else {
                    data.setGPS_Km_Travelled("0.0");
                    data.setGPS_Is_Loc_Changed("0");
//                double distanceInMeters = currentLocation.distanceTo(priviusLocation);
//                if (curLatitude > 0) {
//                if (curLatitude > 0 && locAccuracy < 100) {
                    if (curLatitude > 0 && locAccuracy < Config.ACCURACY) {
                        getSharedPref.setPreviousLocation(String.valueOf(curLatitude), String.valueOf(curLongitude));
                    }
                }

//                Toast.makeText(context, "old=="+getSharedPref.getlat(), Toast.LENGTH_LONG).show();
                /*16-aug Pragna*/
           /* if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                    (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                    Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
                dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " ADD CALL 1 ");

            } else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
                dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " ADD CALL 2 ");
            }*/
          /*  if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                    (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                    Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {*/


                //  dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " ADD CALL 1 ");

          /*  } else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
                dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " ADD CALL 2 ");
            }*/


                dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " LOCATION ADD CALL ");
                //}
            }
        }
        else {

            System.out.println("WILL NOT ALLOW USER TO SAVE DATA1111111111111111 !!!!!!!!!!!!!!!!!!!!!!!!!!");
        }


    }

    private int calculateBatteryPercentage() {

        boolean present = batteryStatus.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
        int battery_percentage = 0;
        if (present) {
            battery_percentage = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        }
        return battery_percentage;
    }

    public void onFromDateButtonClicked() {
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
        DatePickerDialog dialog = new DatePickerDialog(context, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
                    et_next_followupdate.setText(sdf_full.format(selectedFromDate));

                } catch (Exception ex) {
                    //Log.e("Error",""+ex.getMessage());
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.getDatePicker().setMinDate(result.getTime());
        dialog.show();
    }


}




