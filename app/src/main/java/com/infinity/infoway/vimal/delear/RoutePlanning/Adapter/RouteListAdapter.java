package com.infinity.infoway.vimal.delear.RoutePlanning.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllEmployeeByDesignationPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllRouteListPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.model.SaveRouteModel;

import com.infinity.infoway.vimal.delear.util.CommonUtils;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.MyViewHolder> {

    private Context context;
    GetAllRouteListPojo getAllRouteListPojo;
    private SharedPref sharedPref;
    private ArrayList<String> getEmployeeNameList;
    private ArrayList<GetAllRouteListPojo.RECORD> arrayList;
    ArrayAdapter<String> employeeArrayListAdapter;
    HashMap<Integer, Integer> selectedValue = new HashMap<>();
    private ArrayList<SaveRouteModel> saveRouteModelArrayList;
    private HashMap<Integer, ArrayList<SaveRouteModel>> integerArrayListHashMap;
    ArrayList<SaveRouteModel> selectedRecordList;

    public RouteListAdapter(Context context, GetAllRouteListPojo getAllRouteListPojo, ArrayList<GetAllRouteListPojo.RECORD> arrayList, ArrayList<String> employeeNameList, ArrayList<SaveRouteModel> saveRouteModelArrayList, ArrayList<Integer> employeeNameListId, HashMap<Integer, ArrayList<SaveRouteModel>> integerArrayListHashMap, ArrayList<SaveRouteModel> selectedRecordList) {
        this.context = context;
        this.getAllRouteListPojo = getAllRouteListPojo;
        this.arrayList = arrayList;
        this.saveRouteModelArrayList = saveRouteModelArrayList;
        this.integerArrayListHashMap = integerArrayListHashMap;
        this.employeeNameListId = employeeNameListId;
        this.employeeNameList = employeeNameList;
        this.selectedRecordList = selectedRecordList;
        sharedPref = new SharedPref(context);
        employeeArrayListAdapter = new ArrayAdapter<String>
                (context, R.layout.searchable_spinner_text_view,
                        employeeNameList);
        employeeArrayListAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);


        // selectedValue.put(0,0);


    }

    public RouteListAdapter(Context context, GetAllRouteListPojo getAllRouteListPojo, ArrayList<String> employeeNameList, HashMap<Integer, ArrayList<SaveRouteModel>> integerArrayListHashMap, ArrayList<Integer> employeeNameListId) {
        this.context = context;
        this.getAllRouteListPojo = getAllRouteListPojo;
        this.employeeNameList = employeeNameList;
        this.employeeNameListId = employeeNameListId;
        employeeArrayListAdapter = new ArrayAdapter<String>
                (context, R.layout.searchable_spinner_text_view,
                        employeeNameList);

        employeeArrayListAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
        this.integerArrayListHashMap = integerArrayListHashMap;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.inflater_route_list_item, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (!CommonUtils.checkIsEmptyOrNullCommon(getAllRouteListPojo.getRECORDS().get(position).getRouteName())) {
            holder.tvRouteName.setText(getAllRouteListPojo.getRECORDS().get(position).getRouteName());
        }else{
            holder.tvRouteName.setText("test");
        }
        holder.spEmployeeName.setTitle("Select Employee");
        holder.spEmployeeName.setAdapter(employeeArrayListAdapter);


        try {
            if (selectedValue != null && selectedValue.size() > 0) {
                holder.spEmployeeName.setSelection(selectedValue.get(getAllRouteListPojo.getRECORDS().get(position).getRouteId()));
            }
        } catch (Exception e) {
            holder.spEmployeeName.setSelection(0);
            System.out.println("error");
            e.printStackTrace();
        }


        holder.spEmployeeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                // selectedValue.remove(i);
                if (i > 0) {


                    selectedValue.put(getAllRouteListPojo.getRECORDS().get(position).getRouteId(), i);

                    /*saveRouteModelArrayList.add(new SaveRouteModel(getAllRouteListPojo.ge))*/

                    ArrayList<SaveRouteModel> saveRouteModels = new ArrayList<>();
                    saveRouteModels.add(new SaveRouteModel(getAllRouteListPojo.getRECORDS().get(position).getRouteId(), employeeNameListId.get(i), true));
                    selectedRecordList.add(new SaveRouteModel(getAllRouteListPojo.getRECORDS().get(position).getRouteId(), employeeNameListId.get(i), true));
                    integerArrayListHashMap.put(getAllRouteListPojo.getRECORDS().get(position).getRouteId(), saveRouteModels);
                    /*if (saveRouteModelArrayList != null && saveRouteModelArrayList.size() > 0) {
                        if (saveRouteModelArrayList.get(position).getRoute_id() == getAllRouteListPojo.getRECORDS().get(position).getRouteId()) {
                            saveRouteModelArrayList.set(position, saveRouteModelArrayList.get(position)).setSales_person_id(employeeNameListId.get(i));
                        } else {
                            saveRouteModelArrayList.add(new SaveRouteModel(getAllRouteListPojo.getRECORDS().get(position).getRouteId(), employeeNameListId.get(i)));
                        }
                    } else {
                        saveRouteModelArrayList.add(new SaveRouteModel(getAllRouteListPojo.getRECORDS().get(position).getRouteId(), employeeNameListId.get(i)));
                    }*/


                } else {
                    if (integerArrayListHashMap != null && integerArrayListHashMap.size() > 0) {
                        integerArrayListHashMap.remove(getAllRouteListPojo.getRECORDS().get(position).getRouteId());
                    }
                    selectedRecordList.remove(new SaveRouteModel(getAllRouteListPojo.getRECORDS().get(position).getRouteId(), employeeNameListId.get(i), false));

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //  }


        //  holder.spEmployeeName.setSelection(selectedValue.get(position));


    }

    @Override
    public int getItemCount() {
        return getAllRouteListPojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRouteName;
        SearchableSpinner spEmployeeName;

        public MyViewHolder(View itemView) {

            super(itemView);
            tvRouteName = itemView.findViewById(R.id.tvRouteName);
            spEmployeeName = itemView.findViewById(R.id.spEmployeeName);


        }


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);


    }

    ArrayList<String> employeeNameList = new ArrayList<>();
    ArrayList<Integer> employeeNameListId = new ArrayList<>();

    private void getAllEmployeeByDesignation(SearchableSpinner spEmployeeName) {


        ApiImplementer.getAllEmployeeByDesignationImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), String.valueOf(sharedPref.getCompanyId()), "sales_officer", new Callback<GetAllEmployeeByDesignationPojo>() {
            @Override
            public void onResponse(Call<GetAllEmployeeByDesignationPojo> call, Response<GetAllEmployeeByDesignationPojo> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        GetAllEmployeeByDesignationPojo getAllEmployeeByDesignationPojo = response.body();

                        employeeNameList = new ArrayList<>();
                        if (getAllEmployeeByDesignationPojo != null && getAllEmployeeByDesignationPojo.getRECORDS().size() > 0) {

                            for (int i = 0; i < getAllEmployeeByDesignationPojo.getRECORDS().size(); i++) {
                                employeeNameList.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getEmpUserName());
                            }
                            ArrayAdapter<String> employeeArrayListAdapter = new ArrayAdapter<String>
                                    (context, R.layout.searchable_spinner_text_view,
                                            employeeNameList);
                            employeeArrayListAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                            spEmployeeName.setAdapter(employeeArrayListAdapter);
                            spEmployeeName.setTitle("Select Employee");
                            spEmployeeName.setPositiveButton("Cancel");
                            spEmployeeName.setAdapter(employeeArrayListAdapter);


                        } else {
                            Toast.makeText(context, getAllEmployeeByDesignationPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetAllEmployeeByDesignationPojo> call, Throwable t) {

                Toast.makeText(context, "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
