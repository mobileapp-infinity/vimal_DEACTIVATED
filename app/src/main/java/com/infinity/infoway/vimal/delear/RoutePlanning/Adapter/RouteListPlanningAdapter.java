package com.infinity.infoway.vimal.delear.RoutePlanning.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.RoutePlanningCopyOrViewActivity;
import com.infinity.infoway.vimal.delear.RoutePlanning.model.SaveRouteModel;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.GetRoutePlanningListPojo;
import com.infinity.infoway.vimal.delear.util.CommonUtils;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.infinity.infoway.vimal.delear.RoutePlanning.RoutePlanningListActivity.REQUEST_CODE;


public class RouteListPlanningAdapter extends RecyclerView.Adapter<RouteListPlanningAdapter.MyViewHolder> {
    private Context context;
    private GetRoutePlanningListPojo getRoutePlanningListPojo;
    String SELETED_DATE;
    private SharedPref sharedPref;
    ArrayAdapter<String> employeeArrayListAdapter;
    ArrayList<String> employeeNameList;
    ArrayList<Integer> employeeNameListId;
    ProgressDialog progDialog;
    HashMap<Integer, Integer> selectedValue = new HashMap<>();
    boolean isFromCopy;
    ArrayList<String> routeArrayList;
    ArrayList<String> routeIdArrayList;
    private boolean isDone = false;
    private HashMap<Integer, ArrayList<SaveRouteModel>> integerArrayListHashMap;
    LinearLayoutManager linearLayoutManager;
    ArrayList<SaveRouteModel> selectedOrderList;

    public RouteListPlanningAdapter(Context context, GetRoutePlanningListPojo getRoutePlanningListPojo, String SELETED_DATE, ArrayList<String> employeeNameList, ArrayList<Integer> employeeNameListId, HashMap<Integer, ArrayList<SaveRouteModel>> integerArrayListHashMap, boolean isFromCopy, ArrayList<String> routeArrayList, ArrayList<String> routeIdArrayList, LinearLayoutManager linearLayoutManager, ArrayList<SaveRouteModel> selectedOrderList,  HashMap<Integer, Integer> selectedValue) {
        this.context = context;
        this.getRoutePlanningListPojo = getRoutePlanningListPojo;
        this.SELETED_DATE = SELETED_DATE;
        this.selectedOrderList = selectedOrderList;
        sharedPref = new SharedPref(context);
        progDialog = new ProgressDialog(context);
        this.employeeNameList = employeeNameList;
        this.employeeNameListId = employeeNameListId;
        this.routeArrayList = routeArrayList;
        this.routeIdArrayList = routeIdArrayList;
        this.linearLayoutManager = linearLayoutManager;
        this.isFromCopy = isFromCopy;
        this.selectedValue =selectedValue;
        this.integerArrayListHashMap = integerArrayListHashMap;
        employeeArrayListAdapter = new ArrayAdapter<String>
                (context, R.layout.searchable_spinner_text_view,
                        employeeNameList);
        employeeArrayListAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflater_route_planning_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


     /*   String date = getRoutePlanningListPojo.getRECORDS().get(position).getRsoEffectiveDnt();
        String[] dateParts = date.split("-");
        String day = dateParts[0];
        String month = dateParts[1];

        try {
            DateFormat f = new SimpleDateFormat("yyyy/MM/dd'T' hh:mm:ss a");
            Date d = f.parse(getRoutePlanningListPojo.getRECORDS().get(position).getRsoEffectiveDnt());
            DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat time = new SimpleDateFormat("hh:mm:ss a");
            System.out.println("Date: " + date.format(d));
            System.out.println("Time: " + time.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        // Setting the pattern

        try {
            if (!CommonUtils.checkIsEmptyOrNullCommon(getRoutePlanningListPojo.getRECORDS().get(position).getRsoEffectiveDnt())) {
                holder.tvEffectiveDate.setText(getRoutePlanningListPojo.getRECORDS().get(position).getRsoEffectiveDnt());
            }
            if (!CommonUtils.checkIsEmptyOrNullCommon(getRoutePlanningListPojo.getRECORDS().get(position).getEff_time())) {
                holder.tvTime.setText(getRoutePlanningListPojo.getRECORDS().get(position).getEff_time());
            }
            if (!CommonUtils.checkIsEmptyOrNullCommon(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteName())) {
                holder.tvRouteName.setText(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteName());
            }

            if (!CommonUtils.checkIsEmptyOrNullCommon(getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonName())) {

                holder.tvSalesPeronName.setText(getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonName());
            }

            holder.spEmployeeName.setAdapter(employeeArrayListAdapter);

            //selectedValue.put(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId(), getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId());

            if (isFromCopy) {
                //

                for (int i = 0; i < routeIdArrayList.size(); i++) {
                    if (getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId().equals(Integer.parseInt(routeIdArrayList.get(i)))) {
                        holder.edSalesPerson.setText(routeArrayList.get(i));

                        break;

                    }

                }

                // holder.spEmployeeName.setSelection(employeeNameListId.indexOf(getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId()));


                try {


                    if (selectedValue != null && selectedValue.size() > 0) {
                        // holder.spEmployeeName.setSelection(employeeNameListId.get(selectedValue.get(getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId())));
                        holder.spEmployeeName.setSelection(employeeNameListId.indexOf(selectedValue.get(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId())));
                    }
                } catch (Exception e) {
                    holder.spEmployeeName.setSelection(0);
                    System.out.println("error");
                    e.printStackTrace();
                }

                holder.edate.setVisibility(View.GONE);
                holder.tvEffectiveDate.setVisibility(View.GONE);
                holder.tvTime.setVisibility(View.GONE);
                holder.etime.setVisibility(View.GONE);
                holder.tvRouteName.setVisibility(View.GONE);
                holder.routeName.setVisibility(View.GONE);
                holder.routeName.setVisibility(View.GONE);

                holder.tvSalesPeronName.setVisibility(View.GONE);
                holder.spEmployeeName.setClickable(true);
                holder.spEmployeeName.setEnabled(true);


            } else {
                holder.spEmployeeName.setClickable(false);
                holder.spEmployeeName.setEnabled(false);
            }


            if (!isFromCopy) {
                //-Remish
                holder.spEmployeeName.setSelection(employeeNameListId.indexOf(getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId()));
            }


            // if (getRoutePlanningListPojo.getRECORDS().ge)





      /*  for (int i = 0; i < employeeNameListId.size(); i++) {
            if (employeeNameListId.get(i) == getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId()) {
                holder.spEmployeeName.setSelection(employeeNameListId.get(position));
            }
        }*/


            try {
                selectedOrderList.add(new SaveRouteModel(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId(), getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId(), false));
                ArrayList<SaveRouteModel> saveRouteModels = new ArrayList<>();
                saveRouteModels.add(new SaveRouteModel(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId(), getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId(), false));
                integerArrayListHashMap.put(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId(), saveRouteModels);
            } catch (Exception e) {

            }


            holder.spEmployeeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i > 0) {


                        selectedValue.put(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId(), employeeNameListId.get(i));



                        /*saveRouteModelArrayList.add(new SaveRouteModel(getAllRouteListPojo.ge))*/
                        // selectedOrderList.add(new com.infinityinfoway.davat.delear.activity.test2.pojo.SaveRouteModel(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId(), employeeNameListId.get(i), true));
                        ArrayList<SaveRouteModel> saveRouteModels = new ArrayList<>();
                        saveRouteModels.add(new SaveRouteModel(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId(), employeeNameListId.get(i), true));
                        integerArrayListHashMap.put(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId(), saveRouteModels);


                    } else {

                      /*  if (selectedOrderList != null && selectedOrderList.size() > 0) {
                            selectedOrderList.remove(new com.infinityinfoway.davat.delear.activity.test2.pojo.SaveRouteModel(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId(), employeeNameListId.get(i), true));

                        }*/

                        if (integerArrayListHashMap != null && integerArrayListHashMap.size() > 0) {
                            integerArrayListHashMap.remove(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId());
                        }

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            holder.tvCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, RoutePlanningCopyOrViewActivity.class);
                    intent.putExtra("routeid", getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId());
                    intent.putExtra("selectedDate", SELETED_DATE);
                    intent.putExtra("isFromCopy", true);
                    intent.putExtra("routeName", getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteName());
                    intent.putExtra("salesPersonId", getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId());
                    intent.putExtra("salesPersonName", getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonName());


                    ((Activity) context).startActivityForResult(intent, REQUEST_CODE);


                }
            });

            holder.tvView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RoutePlanningCopyOrViewActivity.class);
                    intent.putExtra("routeid", getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId());
                    intent.putExtra("selectedDate", SELETED_DATE);
                    intent.putExtra("isFromCopy", false);
                    intent.putExtra("routeName", getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteName());
                    intent.putExtra("salesPersonId", getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId());
                    intent.putExtra("salesPersonName", getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonName());
                    ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
                    /* context.startActivity(intent);*/

                }
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        isDone = true;
    }

    @Override
    public int getItemCount() {
        return getRoutePlanningListPojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvEffectiveDate, tvTime, edSalesPerson, routeName, edate, etime;
        TextView tvCopy;
        TextView tvView;
        TextView tvRouteName;
        TextView tvSalesPeronName;
        SearchableSpinner spEmployeeName;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvEffectiveDate = itemView.findViewById(R.id.tvEffectiveDate);
            tvTime = itemView.findViewById(R.id.tvEffectiveTime);
            etime = itemView.findViewById(R.id.etime);
            edSalesPerson = itemView.findViewById(R.id.edSalesPerson);
            routeName = itemView.findViewById(R.id.routeName);
            edate = itemView.findViewById(R.id.edate);
            tvRouteName = itemView.findViewById(R.id.tvRouteName);
            tvSalesPeronName = itemView.findViewById(R.id.tvSalesPeronName);
            tvView = itemView.findViewById(R.id.tvView);
            tvCopy = itemView.findViewById(R.id.tvCreateSo);
            spEmployeeName = itemView.findViewById(R.id.spEmployeeName);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }


    //ArrayList<String> employeeNameList;


    private void showProgressDialog() {
        if (!((Activity) context).isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(context.getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (!((Activity) context).isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }
}
