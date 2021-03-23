package com.infinity.infoway.vimal.add_news_or_notification.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add_news_or_notification.NewsOrNotificationImplementer;
import com.infinity.infoway.vimal.add_news_or_notification.adapter.ReadNotificationListAdapter;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.GetNewsAndMsgListPojo;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.DialogUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadNotificationFragment extends Fragment {
    private SharedPref getSharedPref;
    private RecyclerView readNoti;
    private Activity context;
    private TextView tvNoDataFound;

    public ReadNotificationFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_notification, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        getSharedPref = new SharedPref(context);
        readNoti = view.findViewById(R.id.readNoti);
        tvNoDataFound = view.findViewById(R.id.tvNoDataFound);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getAllNewsOrNotificationListApiCall();
        }
    }


    private void getAllNewsOrNotificationListApiCall() {
        readNoti.setVisibility(View.GONE);
        tvNoDataFound.setVisibility(View.VISIBLE);
        DialogUtils.showProgressDialogNotCancelable(context, "");
        NewsOrNotificationImplementer.getNewsAndMsgApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), String.valueOf(getSharedPref.getCompanyId()), new Callback<GetNewsAndMsgListPojo>() {
                    @Override
                    public void onResponse(Call<GetNewsAndMsgListPojo> call, Response<GetNewsAndMsgListPojo> response) {
                        DialogUtils.hideProgressDialog();
                        try {
                            if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                                readNoti.setVisibility(View.VISIBLE);
                                tvNoDataFound.setVisibility(View.GONE);

                                ArrayList<GetNewsAndMsgListPojo.RECORD> recordArrayList = new ArrayList<>();

                                for (int i = 0; i < response.body().getRECORDS().size(); i++) {
                                    if (response.body().getRECORDS().get(i).getIsRead() == 1) {
                                        recordArrayList.add(response.body().getRECORDS().get(i));
                                    }
                                }

                                readNoti.setAdapter(new ReadNotificationListAdapter(context, recordArrayList));
                            } else {
                                readNoti.setVisibility(View.GONE);
                                tvNoDataFound.setVisibility(View.VISIBLE);
                                Toast.makeText(context, "No Data Found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            readNoti.setVisibility(View.GONE);
                            tvNoDataFound.setVisibility(View.VISIBLE);
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNewsAndMsgListPojo> call, Throwable t) {
                        DialogUtils.hideProgressDialog();
                        readNoti.setVisibility(View.GONE);
                        tvNoDataFound.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}