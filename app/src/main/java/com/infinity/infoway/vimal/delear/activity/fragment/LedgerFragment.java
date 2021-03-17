package com.infinity.infoway.vimal.delear.activity.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.Get_Account_Ledger_Of_Login_User;
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.LedgerAdapter;

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
 * Use the {@link LedgerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LedgerFragment extends Fragment implements View.OnClickListener {


    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;

    /**
     * From-Date To-date
     **/
    EditText etledgerFromDate, etledgerToDate;
    private Date today, selectedToDate, selectedFromDate;
    private String selectedToDateString, selectedFromDateString;
    private SimpleDateFormat sdf_full, serverDateFormat;

    /**
     * From-Date To-date
     **/
    private RequestQueue queue;
    private RecyclerView rvPerformLedgerList;
    private LinearLayoutManager linearLayoutManager;
    private ConstraintLayout main;


    private Button btnGetLedgerList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LedgerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LedgerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LedgerFragment newInstance(String param1, String param2) {
        LedgerFragment fragment = new LedgerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ledger, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        queue = Volley.newRequestQueue(getActivity());
        apiService = ApiClient.getClient().create(ApiInterface.class);
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        main = view.findViewById(R.id.main_ledger);
        serverDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        getSharedPref = new SharedPref(getActivity());
        etledgerFromDate = (EditText) view.findViewById(R.id.etledgerFromDate);
        rvPerformLedgerList = (RecyclerView) view.findViewById(R.id.rvPerformLedgerList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        etledgerToDate = (EditText) view.findViewById(R.id.etledgerToDate);
        btnGetLedgerList = (Button) view.findViewById(R.id.btnGetLedgerList);
        etledgerFromDate.setOnClickListener(this);
        etledgerToDate.setOnClickListener(this);
        btnGetLedgerList.setOnClickListener(this);
    }


    /**compare date function added on 29-09-2020**/
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
                    etledgerFromDate.setText(sdf_full.format(selectedFromDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

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
                    etledgerToDate.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.etledgerFromDate) {
            onFromDateButtonClicked();
        } else if (v.getId() == R.id.etledgerToDate) {
            onToDateButtonClicked();
        } else if (v.getId() == R.id.btnGetLedgerList) {
            if (TextUtils.isEmpty(etledgerFromDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please Select From-Date", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(etledgerToDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please Select To-Date", Toast.LENGTH_LONG).show();
            } else {

                if (compareDates(etledgerFromDate.getText().toString().trim(), etledgerToDate.getText().toString().trim())) {
                    Get_Account_Ledger_Of_Login_User();
                } else {
                    Toast.makeText(getActivity(), "From-Date Can Not Be Longer Than To-Date ", Toast.LENGTH_LONG).show();
                }


            }

        }

    }

    private Get_Account_Ledger_Of_Login_User get_account_ledger_of_login_user;

    private void Get_Account_Ledger_Of_Login_User() {
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

        Call<Get_Account_Ledger_Of_Login_User> call = apiService.get_account_ledger_of_login_user(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                selectedFromDateString
                ,
                selectedToDateString
                ,
                getSharedPref.getCompanyId() + ""


        );


        call.enqueue(new Callback<Get_Account_Ledger_Of_Login_User>() {
            @Override
            public void onResponse(Call<Get_Account_Ledger_Of_Login_User> call, Response<Get_Account_Ledger_Of_Login_User> response) {
                progDialog.dismiss();
                if (response.isSuccessful()) {
                    get_account_ledger_of_login_user = response.body();

                    if (get_account_ledger_of_login_user != null && get_account_ledger_of_login_user.getTOTAL() > 0) {

                        rvPerformLedgerList.setVisibility(View.VISIBLE);
                        LedgerAdapter ledgerAdapter = new LedgerAdapter(getActivity(), get_account_ledger_of_login_user);
                        rvPerformLedgerList.setLayoutManager(linearLayoutManager);
                        rvPerformLedgerList.setAdapter(ledgerAdapter);
                        ledgerAdapter.notifyDataSetChanged();

                    } else {
                        rvPerformLedgerList.setVisibility(View.GONE);
                        progDialog.dismiss();
                        displaySnackBar(get_account_ledger_of_login_user.getMESSAGE().toString());

                    }


                }

            }

            @Override
            public void onFailure(Call<Get_Account_Ledger_Of_Login_User> call, Throwable t) {
                progDialog.dismiss();
                System.out.println("ex" + t.getMessage());

            }
        });


    }


    private Snackbar paymentSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(main, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }

}
