//package com.infinity.infoway.vimal.HR;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.infinity.infoway.vimal.R;
//import com.infinity.infoway.vimal.api.ApiClient;
//import com.infinity.infoway.vimal.api.ApiInterface;
//import com.infinity.infoway.vimal.database.SharedPref;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class ApproveRejectFragment extends Fragment {
//
//    //Added By harsh on 11-3-2021
//
//    private ApiInterface apiInterface;
//    private SharedPref getSharedPref;
//    private RecyclerView rvInOutDetails;
//    private ApiInterface apiService;
//    private ProgressDialog progDialog;
//
//    public ApproveRejectFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_approve_reject, container, false);
//        initView(view);
//        Get_All_Pin_Pout_Detail();
//        return view;
//    }
//
//    public static ApproveRejectFragment newInstance() {
//        ApproveRejectFragment fragment = new ApproveRejectFragment();
//        return fragment;
//    }
//
//
//    private void initView(View view) {
//
//
//        rvInOutDetails = view.findViewById(R.id.rvInOutDetails);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        rvInOutDetails.setLayoutManager(linearLayoutManager);
//        apiService = ApiClient.getClient().create(ApiInterface.class);
//        getSharedPref = new SharedPref(getActivity());
//
//
//    }
//
//
//    /**
//     * added on 11-3-2021 by harsh
//     * 1.app_version(Int32)
//     * 2.android_id(String)
//     * 3.device_id(Int32)
//     * 4.user_id(Int32)
//     * 5.key(String)
//     * 6.comp_id(Int32)
//     **/
//    ArrayList<GetAllInOutDetailPojo.RECORD> allInOutDetailPojos;
//    ApproveRejectAdapter approveRejectAdapter;
//
//    public void Get_All_Pin_Pout_Detail() {
//        if (progDialog != null && progDialog.isShowing()) {
//            progDialog.dismiss();
//        }
//
//        try {
//            progDialog = new ProgressDialog(getActivity());
//            progDialog.setIndeterminate(true);
//            progDialog.setMessage(getResources().getString(R.string.processing_request));
//            progDialog.setCancelable(false);
//            progDialog.show();
//        } catch (Exception ex) {
//        }
//
//        Call<GetAllInOutDetailPojo> call = apiService.Get_All_Pin_Pout_Detail(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY, getSharedPref.getCompanyId());
//
//        call.enqueue(new Callback<GetAllInOutDetailPojo>() {
//            @Override
//            public void onResponse(Call<GetAllInOutDetailPojo> call, Response<GetAllInOutDetailPojo> response) {
//
//                progDialog.dismiss();
//
//                try {
//
//                    if (response.isSuccessful() && response.body() != null) {
//                        allInOutDetailPojos = new ArrayList<>();
//                        GetAllInOutDetailPojo getAllInOutDetailPojo = response.body();
//
//                        if (getAllInOutDetailPojo != null && getAllInOutDetailPojo.getRECORDS().size() > 0) {
//
//
//                            for (int i = 0; i < getAllInOutDetailPojo.getRECORDS().size(); i++) {
//                                allInOutDetailPojos.add(getAllInOutDetailPojo.getRECORDS().get(i));
//                            }
//
//                            rvInOutDetails.setVisibility(View.VISIBLE);
//                            approveRejectAdapter = new ApproveRejectAdapter(getActivity(), getAllInOutDetailPojo, allInOutDetailPojos,rvInOutDetails, new ApproveRejectAdapter.IApproveReject() {
//                                @Override
//                                public void approveReject(GetAllInOutDetailPojo getAllInOutDetailPojo, int position, View view, ArrayList<GetAllInOutDetailPojo.RECORD> getAllInOutDetailPojoList) {
//
//                                    view.findViewById(R.id.tv_approve).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            Pin_Pout_Approval(getAllInOutDetailPojo.getRECORDS().get(position).getId() + "", "1", getAllInOutDetailPojo.getRECORDS().get(position).getEmpId() + "", getAllInOutDetailPojo.getRECORDS().get(position
//                                            ).getAtdtPunchInOutFlag() + "", allInOutDetailPojos, position);
//
//                                        }
//                                    });
//                                    view.findViewById(R.id.tv_reject).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            Pin_Pout_Approval(getAllInOutDetailPojo.getRECORDS().get(position).getId() + "", "2", getAllInOutDetailPojo.getRECORDS().get(position).getEmpId() + "", getAllInOutDetailPojo.getRECORDS().get(position
//                                            ).getAtdtPunchInOutFlag() + "", allInOutDetailPojos, position);
//                                        }
//                                    });
//
//                                }
//                            });
//                            rvInOutDetails.setAdapter(approveRejectAdapter);
//
//                        } else {
//                            rvInOutDetails.setVisibility(View.GONE);
//                            DialogUtils.Show_Toast(getActivity(), getAllInOutDetailPojo.getMESSAGE());
//
//                        }
//
//
//                    }
//
//
//                } catch (Exception e) {
//                    DialogUtils.Show_Toast(getActivity(), "error in response" + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAllInOutDetailPojo> call, Throwable t) {
//
//                progDialog.dismiss();
//                DialogUtils.Show_Toast(getActivity(), "Request Failed" + t.getMessage());
//
//            }
//        });
//
//
//    }
//
//    private void Pin_Pout_Approval(String id, String type, String emp_id, String flag,ArrayList<GetAllInOutDetailPojo.RECORD> allInOutDetailPojos ,int position) {
//        if (progDialog != null && progDialog.isShowing()) {
//            progDialog.dismiss();
//        }
//
//        try {
//            progDialog = new ProgressDialog(getActivity());
//            progDialog.setIndeterminate(true);
//            progDialog.setMessage(getResources().getString(R.string.processing_request));
//            progDialog.setCancelable(false);
//            progDialog.show();
//        } catch (Exception ex) {
//        }
//
//        Call<PinPoutApprovalPojo> call = apiService.Pin_Pout_Approval(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY, getSharedPref.getCompanyId(), id, type, emp_id, flag);
//
//
//        call.enqueue(new Callback<PinPoutApprovalPojo>() {
//            @Override
//            public void onResponse(Call<PinPoutApprovalPojo> call, Response<PinPoutApprovalPojo> response) {
//
//                progDialog.dismiss();
//                try {
//                    if (response.isSuccessful() && response.body() != null) {
//                        PinPoutApprovalPojo pinPoutApprovalPojo = response.body();
//
//                        if (pinPoutApprovalPojo != null && pinPoutApprovalPojo.getTOTAL() == 1) {
//
//                            DialogUtils.Show_Toast(getActivity(), pinPoutApprovalPojo.getMESSAGE());
//
//                            Get_All_Pin_Pout_Detail();
//
//
//
//
//                        } else {
//
//                            DialogUtils.Show_Toast(getActivity(), pinPoutApprovalPojo.getMESSAGE());
//                        }
//
//
//                    }
//
//
//                } catch (Exception e) {
//                    DialogUtils.Show_Toast(getActivity(), "Error in response" + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PinPoutApprovalPojo> call, Throwable t) {
//
//                progDialog.dismiss();
//                DialogUtils.Show_Toast(getActivity(), "Request Failed" + t.getMessage());
//
//            }
//        });
//
//
//    }
//
//}
