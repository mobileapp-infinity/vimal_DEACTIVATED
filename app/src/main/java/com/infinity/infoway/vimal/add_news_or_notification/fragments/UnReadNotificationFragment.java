package com.infinity.infoway.vimal.add_news_or_notification.fragments;

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
import com.infinity.infoway.vimal.add_news_or_notification.activity.ViewNewsOrNotificationListActivity;
import com.infinity.infoway.vimal.add_news_or_notification.adapter.UnReadNotificationListAdapter;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.GetNewsAndMsgListPojo;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.DialogUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnReadNotificationFragment extends Fragment implements UnReadNotificationListAdapter.IOnListEmpty {

    private SharedPref getSharedPref;
    private RecyclerView unreadNoti;
    private Context context;
    private TextView tvNoDataFound;

    public UnReadNotificationFragment() {
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (ViewNewsOrNotificationListActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_un_read_notification, container, false);
        initView(view);
        getAllNewsOrNotificationListApiCall();
        return view;

    }

    private void initView(View view) {
        getSharedPref = new SharedPref(context);
        unreadNoti = view.findViewById(R.id.unreadNoti);
        tvNoDataFound = view.findViewById(R.id.tvNoDataFound);
    }

    private void getAllNewsOrNotificationListApiCall() {
        unreadNoti.setVisibility(View.GONE);
        tvNoDataFound.setVisibility(View.VISIBLE);
        DialogUtils.showProgressDialogNotCancelable(context, "");
        //            NewsOrNotificationImplementer.getNewsAndMsgApiImplementer(String.valueOf("0"),
//                    "testing", String.valueOf("0"),
//                    "1", String.valueOf("28"), new Callback<GetNewsAndMsgListPojo>() {
        NewsOrNotificationImplementer.getNewsAndMsgApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), String.valueOf(getSharedPref.getCompanyId()), new Callback<GetNewsAndMsgListPojo>() {
                    @Override
                    public void onResponse(Call<GetNewsAndMsgListPojo> call, Response<GetNewsAndMsgListPojo> response) {
                        DialogUtils.hideProgressDialog();
                        try {
                            if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                                ArrayList<GetNewsAndMsgListPojo.RECORD> recordArrayList = new ArrayList<>();

                                for (int i = 0; i < response.body().getRECORDS().size(); i++) {
                                    if (response.body().getRECORDS().get(i).getIsRead() == 0){
                                        recordArrayList.add(response.body().getRECORDS().get(i));
                                    }
                                }
                                if (recordArrayList.size() > 0){
                                    unreadNoti.setVisibility(View.VISIBLE);
                                    tvNoDataFound.setVisibility(View.GONE);
                                    unreadNoti.setAdapter(new UnReadNotificationListAdapter(context, recordArrayList,UnReadNotificationFragment.this));
                                }

                            } else {
                                unreadNoti.setVisibility(View.GONE);
                                tvNoDataFound.setVisibility(View.VISIBLE);
                                Toast.makeText(context, "No Data Found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            unreadNoti.setVisibility(View.GONE);
                            tvNoDataFound.setVisibility(View.VISIBLE);
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNewsAndMsgListPojo> call, Throwable t) {
                        DialogUtils.hideProgressDialog();
                        unreadNoti.setVisibility(View.GONE);
                        tvNoDataFound.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClear() {
        unreadNoti.setVisibility(View.GONE);
        tvNoDataFound.setVisibility(View.VISIBLE);
    }
}