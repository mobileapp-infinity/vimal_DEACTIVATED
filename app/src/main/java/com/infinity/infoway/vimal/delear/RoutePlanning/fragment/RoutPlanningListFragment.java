package com.infinity.infoway.vimal.delear.RoutePlanning.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.Adapter.RoutePlanningDateWiseListAdapter;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.RoutePlanningDateWisePojo;




import java.text.DateFormat;
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
 * Use the {@link RoutPlanningListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutPlanningListFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ProgressDialog progDialog;
    private RecyclerView rvRoutePlanningList;
    EditText edtRoutePlanningFromDate;
    EditText edtRoutePlanningToDate;
    Button btnRoutePlanningGo;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String selectedToDateString, selectedFromDateString;
    private SimpleDateFormat sdf_full, serverDateFormat;
    private Date selectedToDate, selectedFromDate;
    private SharedPref sharedPref;

    public RoutPlanningListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CopyViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoutPlanningListFragment newInstance(String param1, String param2) {
        RoutPlanningListFragment fragment = new RoutPlanningListFragment();
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
        View view = inflater.inflate(R.layout.fragment_copy_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        progDialog = new ProgressDialog(getActivity());
        rvRoutePlanningList = view.findViewById(R.id.rvRoutePlanningList);
        sharedPref = new SharedPref(getActivity());
        rvRoutePlanningList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        edtRoutePlanningFromDate = view.findViewById(R.id.edtRoutePlanningFromDate);

        edtRoutePlanningToDate = view.findViewById(R.id.edtRoutePlanningToDate);
        btnRoutePlanningGo = view.findViewById(R.id.btnRoutePlanningGo);
        btnRoutePlanningGo.setOnClickListener(this);
        edtRoutePlanningToDate.setOnClickListener(this);
        edtRoutePlanningFromDate.setOnClickListener(this);

    }

    /**
     * Onclick Listner
     **/
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edtRoutePlanningFromDate) {
            onFromDateButtonClicked();

        } else if (v.getId() == R.id.edtRoutePlanningToDate) {
            onToDateButtonClicked();


        } else if (v.getId() == R.id.btnRoutePlanningGo) {

            if (edtRoutePlanningFromDate.getText().toString().contentEquals("")) {
                Toast.makeText(getActivity(), "Please Select From-date", Toast.LENGTH_LONG).show();
            } else if (edtRoutePlanningToDate.getText().toString().contentEquals("")) {
                Toast.makeText(getActivity(), "Please Select To-date", Toast.LENGTH_LONG).show();
            } else {
                if (compareDates(edtRoutePlanningFromDate.getText().toString(), edtRoutePlanningToDate.getText().toString())) {
                    getSaleRouteWiseSalesOfficerMapping(selectedFromDateString, selectedToDateString);
                } else {
                    Toast.makeText(getActivity(), "From-Date Can Not Be Longer Than To-Date", Toast.LENGTH_LONG).show();
                }
            }


        }

    }

    private void getSaleRouteWiseSalesOfficerMapping(String fdt, String tdt) {

        showProgressDialog();


        ApiImplementer.getSaleRouteWiseSalesOfficerMapping(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), sharedPref.getCompanyId(), fdt, tdt, new Callback<RoutePlanningDateWisePojo>() {
            @Override
            public void onResponse(Call<RoutePlanningDateWisePojo> call, Response<RoutePlanningDateWisePojo> response) {

                hideProgressDialog();

                try {

                    if (response.isSuccessful() && response.body() != null) {


                        RoutePlanningDateWisePojo routePlanningDateWisePojo = response.body();

                        if (routePlanningDateWisePojo != null && routePlanningDateWisePojo.getRECORDS().size() > 0) {

                            rvRoutePlanningList.setVisibility(View.VISIBLE);
                            RoutePlanningDateWiseListAdapter routePlanningDateWiseListAdapter = new RoutePlanningDateWiseListAdapter(getActivity(), routePlanningDateWisePojo, new RoutePlanningDateWiseListAdapter.IOnItemClickListner() {
                                @Override
                                public void onItemClicked(String selectedDate, RoutePlanningDateWisePojo routePlanningDateWisePojo, int pos) {



                                   /* String input = selectedDate;
                                    DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    Date date = null;
                                    try {
                                        date = inputFormatter.parse(input);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    @SuppressLint("SimpleDateFormat") DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
                                    String output = outputFormatter.format(date); // Output : 01/20/2012

                                    System.out.println(output);*/


                                }
                            });
                            rvRoutePlanningList.setAdapter(routePlanningDateWiseListAdapter);

                        } else {
                            rvRoutePlanningList.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), routePlanningDateWisePojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        }


                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error in response" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RoutePlanningDateWisePojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void showProgressDialog() {
        if (!getActivity().isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (!getActivity().isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
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
                    edtRoutePlanningFromDate.setText(sdf_full.format(selectedFromDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    /**
     * to-date Selector
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
                    edtRoutePlanningToDate.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }


}
