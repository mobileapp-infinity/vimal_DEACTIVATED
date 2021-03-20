//package com.infinity.infoway.vimal.fragment;
//
//import android.app.DatePickerDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import com.google.android.material.snackbar.Snackbar;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//
//import com.infinity.infoway.vimal.R;
//import com.infinity.infoway.vimal.adapter.AttendanceListAdapter;
//import com.infinity.infoway.vimal.api.ApiClient;
//import com.infinity.infoway.vimal.api.ApiInterface;
//import com.infinity.infoway.vimal.api.response.GetAttendanceResponse;
//import com.infinity.infoway.vimal.config.Config;
//import com.infinity.infoway.vimal.database.SharedPref;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class ViewAttendance extends Fragment implements View.OnClickListener {
//    private Context context;
//    private RecyclerView rvViewAttendanceList;
//    private AttendanceListAdapter expenseListAdapter;
//    private List<GetAttendanceResponse.RECORD> viewAttendanceList;
//
//
//    private EditText etViewAttendanceFromDate,etViewAttendanceToDate;
//    private Button btnLoadAttendance;
//
//    private SimpleDateFormat sdf_full,serverDateFormat;
//    private Date today,selectedToDate,selectedFromDate;
//    private String selectedToDateString,selectedFromDateString;
//
//    private ProgressDialog progDialog;
//    private ApiInterface apiService;
//    private SharedPref getSharedPref;
//
//    private OnFragmentAttendanceInteractionListener mListener;
//    private CoordinatorLayout layout_main_view_attendance;
//
//    private Snackbar addAttendanceSnackBar;
//
//    public ViewAttendance() {
//        // Required empty public constructor
//    }
//
//
//    // TODO: Rename and change types and number of parameters
//    public static ViewAttendance newInstance() {
//        ViewAttendance fragment = new ViewAttendance();
//
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.fragment_view_attendance_temp, container, false);
//        initControls(view);
//        return view;
//    }
//
//    private void initControls(View view){
//
//        context=getActivity();
//
//        apiService = ApiClient.getClient().create(ApiInterface.class);
//        getSharedPref = new SharedPref(context);
//
//        layout_main_view_attendance=view.findViewById(R.id.layout_main_view_attendance);
//
//
//        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
//        serverDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//
//        etViewAttendanceFromDate=view.findViewById(R.id.etViewAttendanceFromDate);
//        etViewAttendanceToDate=view.findViewById(R.id.etViewAttendanceToDate);
//        btnLoadAttendance=view.findViewById(R.id.btnLoadAttendance);
//
//        viewAttendanceList=new ArrayList<>();
//
//
//        rvViewAttendanceList=view.findViewById(R.id.rvViewAttendanceList);
//
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//        rvViewAttendanceList.setLayoutManager(layoutManager);
//        rvViewAttendanceList.setItemAnimator(new DefaultItemAnimator());
//
//
//
//        etViewAttendanceFromDate.setOnClickListener(this);
//        etViewAttendanceToDate.setOnClickListener(this);
//        btnLoadAttendance.setOnClickListener(this);
//
//        try{
//            today=new Date();
//            etViewAttendanceFromDate.setText(sdf_full.format(today));
//            etViewAttendanceToDate.setText(sdf_full.format(today));
//           // btnLoadAttendance.performClick();
//        }catch (Exception ex){}
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.etViewAttendanceFromDate:
//                onFromDateButtonClicked();
//                break;
//            case R.id.etViewAttendanceToDate:
//                onToDateButtonClicked();
//                break;
//            case R.id.btnLoadAttendance:
//                onFetchExpenseClicked();
//                break;
//        }
//    }
//
//    private void onFetchExpenseClicked(){
//
//        if (progDialog != null && progDialog.isShowing()) {
//            progDialog.dismiss();
//        }
//
//        try {
//            progDialog = new ProgressDialog(context);
//            progDialog.setIndeterminate(true);
//            progDialog.setMessage(getResources().getString(R.string.processing_request));
//            progDialog.setCancelable(false);
//            progDialog.show();
//        } catch (Exception ex) { }
//
//        String fromDate="",toDate="";
//
//        try{
//            fromDate=serverDateFormat.format(sdf_full.parse(etViewAttendanceFromDate.getText().toString().trim()));
//            toDate=serverDateFormat.format(sdf_full.parse(etViewAttendanceToDate.getText().toString().trim()));
//
//
//        }catch (Exception ex){}
//
//
//        Call<GetAttendanceResponse> call = apiService.Get_Attendance_List(
//                String.valueOf(getSharedPref.getAppVersionCode()),
//                getSharedPref.getAppAndroidId(),
//                String.valueOf(getSharedPref.getRegisteredId()),
//                String.valueOf(getSharedPref.getRegisteredUserId()),
//                Config.ACCESS_KEY,
//                getSharedPref.getCompanyId(),
//                getSharedPref.getBranchId(),
//                fromDate,
//                toDate
//                );
//
//        call.enqueue(new Callback<GetAttendanceResponse>() {
//            @Override
//            public void onResponse(Call<GetAttendanceResponse> call, Response<GetAttendanceResponse> response) {
//                if(progDialog!=null && progDialog.isShowing()){
//                    progDialog.dismiss();
//                }
//
//                if((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
//
//                    if(response.body().getTOTAL()>0){
//                        viewAttendanceList=response.body().getRECORDS();
//                        expenseListAdapter=new AttendanceListAdapter(context, viewAttendanceList, new AttendanceListAdapter.OnItemClickListner() {
//                            @Override
//                            public void onItemClicked(int position, GetAttendanceResponse.RECORD viewExpensesModel) {
//
//                            }
//                        });
//
//                        rvViewAttendanceList.setAdapter(expenseListAdapter);
//                    }else{
//                        displaySnackBar("No data found !!!");
//                    }
//
//                }else{
//                    displaySnackBar("Something went wrong,try again !!!");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAttendanceResponse> call, Throwable t) {
//                if(progDialog!=null && progDialog.isShowing()){
//                    progDialog.dismiss();
//                }
//
//                displaySnackBar("Something went wrong,try again !!!");
//            }
//        });
//
//       /* for (int i = 0; i < 7; i++) {
//            AttendanceListModel data=new AttendanceListModel();
//            data.setDate(expName[i%2]);
//            data.setPunchInTime(expDate[i%2]);
//            data.setPunchOutTime(expAmount[i%2]);
//
//            viewAttendanceList.add(data);
//        }
//        expenseListAdapter.notifyDataSetChanged();*/
//    }
//
//
//    /**
//     * ToDate button clicked
//     */
//
//    public void onToDateButtonClicked()
//    {
//        int mYear = 0, mMonth = 0, mDay = 0;
//        final Calendar c = Calendar.getInstance();
//        if(selectedToDate==null) {
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//        }else
//        {
//            c.setTimeInMillis(selectedToDate.getTime());
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//        }
//        DatePickerDialog dialog = new DatePickerDialog(context,R.style.DateDialogTheme ,new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker , int year,
//                                  int monthOfYear, int dayOfMonth) {
//                try {
//                    StringBuilder theDate = new StringBuilder()
//                            .append(dayOfMonth).append("-")
//                            .append(monthOfYear + 1).append("-")
//                            .append(year);
//
//                    try {
//
//                        selectedToDate = sdf_full.parse(theDate.toString());
//                    } catch (Exception ex) {
//                    }
//
//                    selectedToDateString = sdf_full.format(selectedToDate);
//                    etViewAttendanceToDate.setText(selectedToDateString);
//                } catch (Exception ex) {
//                }
//
//            }
//        }, mYear, mMonth, mDay);
//        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
//        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
//        dialog.show();
//    }
//
//    /**
//     * from button clicked this method called
//     */
//
//    public void onFromDateButtonClicked()
//    {
//        int mYear = 0, mMonth = 0, mDay = 0;
//        final Calendar c = Calendar.getInstance();
//        if(selectedFromDate==null) {
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//        }else
//        {
//            c.setTimeInMillis(selectedFromDate.getTime());
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//        }
//        DatePickerDialog dialog = new DatePickerDialog(context,R.style.DateDialogTheme ,new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker , int year,
//                                  int monthOfYear, int dayOfMonth) {
//                try {
//                    StringBuilder theDate = new StringBuilder()
//                            .append(dayOfMonth).append("-")
//                            .append(monthOfYear + 1).append("-")
//                            .append(year);
//
//                    try {
//
//                        selectedFromDate = sdf_full.parse(theDate.toString());
//                    } catch (Exception ex) {
//                    }
//
//                    selectedFromDateString = sdf_full.format(selectedFromDate);
//                    etViewAttendanceFromDate.setText(selectedFromDateString);
//                } catch (Exception ex) {
//                }
//
//            }
//        }, mYear, mMonth, mDay);
//        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
//        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
//        dialog.show();
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentAttendanceInteractionListener) {
//            mListener = (OnFragmentAttendanceInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentAttendanceInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentAttendanceInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//    private void displaySnackBar(String message) {
//        try {
//            if (addAttendanceSnackBar != null && addAttendanceSnackBar.isShown()) {
//                addAttendanceSnackBar.dismiss();
//            }
//            addAttendanceSnackBar = Snackbar.make(layout_main_view_attendance, message, Snackbar.LENGTH_LONG);
//            addAttendanceSnackBar.show();
//        } catch (Exception ex) {
//        }
//    }
//}
