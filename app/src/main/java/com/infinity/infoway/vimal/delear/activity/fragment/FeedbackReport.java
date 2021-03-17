package com.infinity.infoway.vimal.delear.activity.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.FeedBack.FeedbackReportAdapter;
import com.infinity.infoway.vimal.delear.activity.FeedBack.Get_Sale_RoutWise_Feedback_Report_Pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedbackReport.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * <p>
 * create an instance of this fragment.
 */
public class FeedbackReport extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FeedbackReport() {
        // Required empty public constructor
    }


    /**
     * initial items
     **/

    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;

    /**
     * initial items
     **/


    /**
     * From-Date To-date
     **/
    EditText etVehicleDispatchFromDate, eVehicleDispatchToDate;
    private Date today, selectedToDate, selectedFromDate;
    private String selectedToDateString, selectedFromDateString;
    private SimpleDateFormat sdf_full, serverDateFormat;

    /**
     * From-Date To-date
     **/


    SwipeRefreshLayout swipeonFeedback;
    EditText et_feedback_report_from_date;
    EditText et_feedback_report_to_date;

    RecyclerView rvFeedback;
    Button btnFeedBackReport;
    LinearLayoutManager linearLayoutManager;
    ConstraintLayout feedback_report;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_report, container, false);
        initView(view);


        return view;
    }


    /**
     * initializaiton of views
     **/
    private void initView(View view) {

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(getActivity());
       /* swipeonFeedback = view.findViewById(R.id.swipeonFeedback);
        swipeonFeedback.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_Sale_RoutWise_Feedback_Report();
                swipeonFeedback.setRefreshing(false);
            }
        });*/

        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        feedback_report = view.findViewById(R.id.feedback_report);
        et_feedback_report_from_date = view.findViewById(R.id.et_feedbck_report_From_Date);
        et_feedback_report_to_date = view.findViewById(R.id.et_feedback_report_to_date);
        btnFeedBackReport = view.findViewById(R.id.btnFeedBackReport);

        et_feedback_report_from_date.setOnClickListener(this);
        et_feedback_report_to_date.setOnClickListener(this);
        btnFeedBackReport.setOnClickListener(this);

        rvFeedback = view.findViewById(R.id.rvFeedback);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFeedback.setLayoutManager(linearLayoutManager);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * On Click Listner
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.et_feedbck_report_From_Date) {

            onFromDateButtonClicked();


        } else if (v.getId() == R.id.et_feedback_report_to_date) {

            onToDateButtonClicked();

        } else if (v.getId() == R.id.btnFeedBackReport) {

            if (et_feedback_report_from_date.getText().toString().contentEquals("")) {
                Toast.makeText(getActivity(), "Please Select From-date", Toast.LENGTH_LONG).show();
            } else if (et_feedback_report_to_date.getText().toString().contentEquals("")) {
                Toast.makeText(getActivity(), "Please Select To-date", Toast.LENGTH_LONG).show();
            } else {


                if (compareDates(et_feedback_report_from_date.getText().toString().trim(), et_feedback_report_to_date.getText().toString().trim())) {
                    System.out.println("Validated=====================");
                    get_Sale_RoutWise_Feedback_Report();
                } else {
                    Toast.makeText(getActivity(), "From-Date Can Not Be Longer Than To-Date ", Toast.LENGTH_LONG).show();
                }


            }


        }


    }


    private Get_Sale_RoutWise_Feedback_Report_Pojo get_sale_routWise_feedback_report_pojo;

    private DividerItemDecoration dividerItemDecoration;

    private void get_Sale_RoutWise_Feedback_Report() {


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

        Call<Get_Sale_RoutWise_Feedback_Report_Pojo> call = apiService.get_sale_routWise_feedback_report(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,

                selectedFromDateString,
                selectedToDateString,
                getSharedPref.getCompanyId() + ""


        );


        call.enqueue(new Callback<Get_Sale_RoutWise_Feedback_Report_Pojo>() {
            @Override
            public void onResponse(Call<Get_Sale_RoutWise_Feedback_Report_Pojo> call, Response<Get_Sale_RoutWise_Feedback_Report_Pojo> response) {

                if (response.isSuccessful()) {
                    progDialog.dismiss();
                    get_sale_routWise_feedback_report_pojo = response.body();
                    if (get_sale_routWise_feedback_report_pojo != null && get_sale_routWise_feedback_report_pojo.getRECORDS().size() > 0) {

                        rvFeedback.setVisibility(View.VISIBLE);

                      /*  dividerItemDecoration = new DividerItemDecoration(rvFeedback.getContext(),
                                linearLayoutManager.getOrientation());
                        dividerItemDecoration.setOrientation(DividerItemDecoration.VERTICAL);
                        rvFeedback.addItemDecoration(dividerItemDecoration);*/
                        FeedbackReportAdapter feedbackReportAdapter = new FeedbackReportAdapter(getActivity(), get_sale_routWise_feedback_report_pojo);
                        rvFeedback.setAdapter(feedbackReportAdapter);
                        feedbackReportAdapter.notifyDataSetChanged();


                    } else {

                        progDialog.dismiss();
                        rvFeedback.setVisibility(View.GONE);
                        displaySnackBar(get_sale_routWise_feedback_report_pojo.getMESSAGE() + "");
                    }


                }


            }

            @Override
            public void onFailure(Call<Get_Sale_RoutWise_Feedback_Report_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });


    }


    public static boolean compareDates(String d1, String d2) {
        try {
            // If you already have date objects then skip 1

            //1
            // Create 2 dates starts
            String myFormat1 = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat1, Locale.US);
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            System.out.println("Date1" + sdf.format(date1));
            System.out.println("Date2" + sdf.format(date2));
            System.out.println();

            // Create 2 dates ends
            //1

            // Date object is having 3 methods namely after,before and equals for comparing
            // after() will return true if and only if date1 is after date 2
            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                return false;
            }
            // before() will return true if and only if date1 is before date2
            if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
                return true;
            }

            //equals() returns true if both the dates are equal
            if (date1.equals(date2)) {
                System.out.println("Date1 is equal Date2");
                return true;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Snackbar paymentSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(feedback_report, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    /**
     * from-date Selector
     **/
    public void onFromDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
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
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
                    et_feedback_report_from_date.setText(sdf_full.format(selectedFromDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    /**
     * To-date Selector
     **/
    public void onToDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if (selectedToDate == null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            c.setTimeInMillis(selectedToDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year,
                                  int monthOfYear, int dayOfMonth) {
                try {
                    StringBuilder theDate = new StringBuilder()
                            .append(dayOfMonth).append("-")
                            .append(monthOfYear + 1).append("-")
                            .append(year);

                    try {
                        selectedToDate = sdf_full.parse(theDate.toString());
                    } catch (Exception ex) {
                    }

                    selectedToDateString = serverDateFormat.format(selectedToDate);
                    et_feedback_report_to_date.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }


}
