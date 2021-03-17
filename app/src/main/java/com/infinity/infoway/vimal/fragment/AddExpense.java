package com.infinity.infoway.vimal.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.github.simonpercic.oklog.android.BuildConfig;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.adapter.ExnpenseListNameAdapter;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.AddExpenseResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.model.ExpenceListModel;
import com.infinity.infoway.vimal.util.common.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpense extends Fragment implements View.OnClickListener {

    // TODO: Rename and change types of parameters
    private Context context;
    private Dialog takeImgDialog;
    private int imageUploadFlagData = 1;
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private Bitmap bitmap;
    private String imgString;
    private MultipartBody.Part fileToUploadPassport;
    private EditText etSaveExpensesDiscription, etSaveExpensesAmount, etSaveExpensesDate;
    private Date selectedDate;
    private String selectedDateString = "";
    Date today;
    SimpleDateFormat sdf_full;
    SimpleDateFormat serverDateFormat;
    private List<ExpenceListModel> expenceListModelList;
    private Button btnSaveExpensesSubmit;
    private EditText etSaveExpensesSelectDocument, et_drop_down_exp_name;
    private Calendar calendar;

    private SharedPref getSharedPref;

    private Snackbar addExpenseSnackBar;
    private int[] resExpenseTypeId;
    private String[] resExpenseTypeName;
    private FrameLayout frm_add_expense;

    private SweetAlertDialog dialogSuccess;
    private int resAddExpenseId = 0;
    private String resAddExpenseMessage = "";

    long fileSize = 0;
    private File photoFile = null;
    private String mCurrentPhotoPath = null;
    private Bitmap orientedBitmap = null;
    private String currentDate;
    private Dialog bottomSheetDialog;

    private Button btnConfirmAddExpense, btnCancelAddExpense;

    private ExnpenseListNameAdapter exnpenseListNameAdapter;
    private int selectedPosition;
    private ProgressDialog progDialog;
    private ApiInterface apiService;
    private String resExpenseId, resExpenseName, resExpenseMaxAmount;

    public AddExpense() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddExpense newInstance() {
        AddExpense fragment = new AddExpense();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        initControls(view);
        return view;
    }

    private void initControls(View view) {


        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(context);

        frm_add_expense = view.findViewById(R.id.frm_add_expense);

        et_drop_down_exp_name = view.findViewById(R.id.et_drop_down_exp_name);

        etSaveExpensesDiscription = view.findViewById(R.id.etSaveExpensesDiscription);
        etSaveExpensesAmount = view.findViewById(R.id.etSaveExpensesAmount);
        etSaveExpensesDate = view.findViewById(R.id.etSaveExpensesDate);
        btnSaveExpensesSubmit = view.findViewById(R.id.btnSaveExpensesSubmit);
        etSaveExpensesSelectDocument = view.findViewById(R.id.etSaveExpensesSelectDocument);


        etSaveExpensesDate.setOnClickListener(this);
        btnSaveExpensesSubmit.setOnClickListener(this);
        etSaveExpensesSelectDocument.setOnClickListener(this);
        et_drop_down_exp_name.setOnClickListener(this);

        expenceListModelList = new ArrayList<>();

        today = new Date();
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etSaveExpensesDate.setText(String.valueOf(sdf_full.format(today)));
        selectedDate = today;
        try {
            currentDate = serverDateFormat.format(today);
            selectedDateString = serverDateFormat.format(today);
        } catch (Exception ex) {
        }
        calendar = Calendar.getInstance();

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSaveExpensesSubmit:
                onSubmitButtonClicked();
                break;
            case R.id.etSaveExpensesSelectDocument:
                openCameraDialog();
                break;
            case R.id.etSaveExpensesDate:
                onDateButtonClicked();
                break;
            case R.id.btnConfirmRegister:
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
                addExpenseApiCall();
               /* if (imgString != null && imgString.trim().length() > 0) {
                    //with  image
                    addExpenseApiCall(expenceListModel.getId(), expenceListModel.getEm_expense_name(), etSaveExpensesDiscription.getText().toString().trim(),
                            etSaveExpensesAmount.getText().toString().trim(), serverDateFormat.format(selectedDate), select_document, imgString, fileSize
                    );

                } else {
                    //with out image
                    addExpenseApiCall(expenceListModel.getId(), expenceListModel.getEm_expense_name(), etSaveExpensesDiscription.getText().toString().trim(),
                            etSaveExpensesAmount.getText().toString().trim(), serverDateFormat.format(selectedDate), "", "", 0
                    );
                }*/
                break;
            case R.id.btnCancelRegistration:
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
                break;
            case R.id.et_drop_down_exp_name:
                Intent intent = new Intent(context, Activity_Select_City.class);
                intent.putExtra("isFromCitySelect", 5);
                startActivityForResult(intent, 600);
                break;

        }
    }

    private void openCameraDialog() {
        try {
            bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setTitle(getResources().getString(R.string.title_capture_image));
            bottomSheetDialog.setContentView(R.layout.layout_dialog_capture_image);
            bottomSheetDialog.setCancelable(true);

            TextView tvTakePhoto = bottomSheetDialog.findViewById(R.id.tv_take_photo);
            TextView tvChoosePhoto = bottomSheetDialog.findViewById(R.id.tv_choose_photo);
            TextView tvCancel = bottomSheetDialog.findViewById(R.id.tv_cancel);

            tvTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    }
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Ensure that there's a camera activity to handle the intent
                    if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                        // Create the File where the photo should go

                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(context,
                                    BuildConfig.APPLICATION_ID + ".provider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                        }
                    }


                }
            });

            tvChoosePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    }

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, GALLERY_REQUEST);

                }
            });

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    }
                }
            });
            bottomSheetDialog.show();
        } catch (Exception ex) {
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            try {

                // fileSize = getFileSizeInKB(photoFile);
                photoFile = saveBitmapToFile(photoFile);

                RequestBody mFile = RequestBody.create(MediaType.parse("image*//*"), photoFile);

                fileToUploadPassport = MultipartBody.Part.createFormData("file", photoFile.getName(), mFile);

                if (photoFile != null && !TextUtils.isEmpty(photoFile.getName())) {
                    etSaveExpensesSelectDocument.setText(photoFile.getName());
                }
                //imgString = encodeImage(orientedBitmap);


            } catch (Exception ex) {
                Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {

            try {

                Uri uri;

                if (data.getData() == null) {
                    uri = (Uri) data.getExtras().get("data");
                } else {
                    uri = data.getData();
                }

                String fileUrl = FileUtils.getPath(context, uri);

                File file = new File(fileUrl);

                if ((file.length() / 1024) > 2048) {
                    String file_extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
                    if ((!TextUtils.isEmpty(file_extension)) && (!file_extension.equalsIgnoreCase("pdf"))) {
                        file = saveBitmapToFile(file);
                    }
                }
                etSaveExpensesSelectDocument.setText(file.getName());
                RequestBody mFile = RequestBody.create(MediaType.parse("image*//*"), file);

                fileToUploadPassport = MultipartBody.Part.createFormData("file", file.getName(), mFile);


            } catch (Exception e) {
                e.printStackTrace();
            }


           /* try {

                Uri uri;
                orientedBitmap = null;

                if (data.getData() == null) {
                    uri = (Uri) data.getExtras().get("data");
                    //orientedBitmap = (Bitmap) data.getExtras().get("data");
                } else {
                    uri = data.getData();
                    //orientedBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                }

                File file = new File(getAbsolutePath(uri));
                //fileSize = getFileSizeInKB(file);

                if (fileSize > 500) {
                    String selectedImagePath = getAbsolutePath(uri);
                    final Bitmap selectedImage = decodeFile(selectedImagePath);
                    orientedBitmap = ExifUtil.rotateBitmap(selectedImagePath, selectedImage);
                }

                etSaveExpensesSelectDocument.setText(file.getName());

                imgString = encodeImage(orientedBitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }*/
        } else if (requestCode == 600) {
            if (data.hasExtra("ExpenseId")) {
                resExpenseId = data.getExtras().getString("ExpenseId");
                if (!TextUtils.isEmpty(resExpenseId)) {
                    resExpenseName = data.getExtras().getString("ExpenseName");
                    resExpenseMaxAmount = data.getExtras().getString("ExpenseMaxAmount");
                    et_drop_down_exp_name.setText(resExpenseName);
                }
            }
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = ((Activity) context).managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public Bitmap decodeFile(String path) {

        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 500;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }


    //date picker dialog show
    public void onDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if (selectedDate == null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            c.setTimeInMillis(selectedDate.getTime());
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

                        selectedDate = sdf_full.parse(theDate.toString());
                    } catch (Exception ex) {
                    }

                    selectedDateString = serverDateFormat.format(selectedDate);
                    etSaveExpensesDate.setText(sdf_full.format(selectedDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    public void onSubmitButtonClicked() {

        if (TextUtils.isEmpty(resExpenseName)) {
            displaySnackBar(getResources().getString(R.string.select_expenses_name));
        } else if (TextUtils.isEmpty(etSaveExpensesDiscription.getText().toString().trim())) {
            displaySnackBar(getResources().getString(R.string.please_enterdescriptition));
        } else if (TextUtils.isEmpty(etSaveExpensesAmount.getText().toString().trim())) {
            displaySnackBar(getResources().getString(R.string.please_enter_amount));
        } else if (TextUtils.isEmpty(etSaveExpensesDate.getText().toString().trim())) {
            displaySnackBar(getResources().getString(R.string.please_select_date));
        } else {
            openBottomSheetSialog();
        }
    }


    private void displaySnackBar(String message) {
        try {
            if (addExpenseSnackBar != null && addExpenseSnackBar.isShown()) {
                addExpenseSnackBar.dismiss();
            }
            addExpenseSnackBar = Snackbar.make(frm_add_expense, message, Snackbar.LENGTH_LONG);
            addExpenseSnackBar.show();
        } catch (Exception ex) {
        }
    }

    public long getFileSizeInKB(File file) {
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        return fileSizeInKB;
    }

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

    private void openBottomSheetSialog() {
        try {
            bottomSheetDialog = new BottomSheetDialog(getActivity());
            bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet_double_confirmation);
            TextView txtBottomSheetTitle = bottomSheetDialog.findViewById(R.id.txtBottomSheetTitle);
            txtBottomSheetTitle.setText(R.string.title_exp_double_confirm);
            btnConfirmAddExpense = bottomSheetDialog.findViewById(R.id.btnConfirmRegister);
            btnCancelAddExpense = bottomSheetDialog.findViewById(R.id.btnCancelRegistration);
            btnConfirmAddExpense.setOnClickListener(this);
            btnCancelAddExpense.setOnClickListener(this);
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.show();
        } catch (Exception ex) {
        }
    }

    private void addExpenseApiCall() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
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
        RequestBody req_company_id = RequestBody.create(MediaType.parse("text/plain"),getSharedPref.getCompanyId());
        RequestBody req_branch_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getBranchId());
        RequestBody req_exp_id = RequestBody.create(MediaType.parse("text/plain"), resExpenseId);
        RequestBody req_exp_name = RequestBody.create(MediaType.parse("text/plain"), resExpenseName);
        RequestBody req_exp_amount = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesAmount.getText().toString().trim());
        RequestBody req_exp_date = RequestBody.create(MediaType.parse("text/plain"), selectedDateString);
        RequestBody req_exp_description = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesDiscription.getText().toString());

        Call<AddExpenseResponse> call = apiService.add_expense(
                req_app_version,
                req_android_id,
                req_device_id,
                req_user_id,
                req_key,
                req_company_id,
                req_branch_id,
                req_exp_id,
                req_exp_name,
                req_exp_amount,
                req_exp_date,
                req_exp_description,
                fileToUploadPassport
        );

        call.enqueue(new Callback<AddExpenseResponse>() {
            @Override
            public void onResponse(Call<AddExpenseResponse> call, final Response<AddExpenseResponse> response) {
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }

                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                    try {
                        if (response.body().getFLAG() > 0) {
                            dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                            dialogSuccess.setTitleText(getString(R.string.sorder_good_job));
                        } else {
                            dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                            dialogSuccess.setTitleText(getString(R.string.sorder_oops));
                        }


                        dialogSuccess.setContentText(response.body().getMESSAGE());


                        dialogSuccess.setCancelable(false);
                        dialogSuccess.show();
                        dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogSuccess.dismissWithAnimation();
                                dialogSuccess.cancel();
                                if (response.body().getFLAG() > 0) {
                                   et_drop_down_exp_name.setText("");
                                   etSaveExpensesAmount.setText("");
                                   etSaveExpensesDiscription.setText("");
                                   etSaveExpensesSelectDocument.setText("");
                                   etSaveExpensesDate.setText("");
                                   resExpenseId="";
                                   resExpenseName="";
                                    fileToUploadPassport=null;
                                    photoFile=null;
                                    orientedBitmap=null;
                                }

                            }
                        });
                    } catch (Exception ignored) {
                    }

                }else{
                    displaySnackBar("Something went wrong,try again !!!");
                }

            }

            @Override
            public void onFailure(Call<AddExpenseResponse> call, Throwable t) {
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }

                displaySnackBar("Something went wrong,try again !!!");

            }
        });

    }

}
