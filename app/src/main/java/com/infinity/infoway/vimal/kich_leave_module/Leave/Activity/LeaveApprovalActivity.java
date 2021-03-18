//package com.infinity.infoway.rkuniversity.Activity;
//
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.infinity.infoway.rkuniversity.CommonCls.CustomBoldTextView;
//import com.infinity.infoway.rkuniversity.CommonCls.CustomTextView;
//import com.infinity.infoway.rkuniversity.CommonCls.MySharedPrefereces;
//import com.infinity.infoway.rkuniversity.R;
//
//public class LeaveApprovalActivity extends AppCompatActivity {
//    CustomBoldTextView txt_act;
//    ImageView iv_back;
//    CustomBoldTextView tv_approved;
//    CustomBoldTextView tv_reject;
//    CustomTextView edtempname;
//    LinearLayout llempname;
//    CustomTextView edtleavebalance;
//    LinearLayout llleavebalance;
//    CustomTextView edtfromdate;
//    CustomTextView ivcalendar;
//    LinearLayout llfromdate;
//    CustomTextView edtday;
//    LinearLayout llday;
//    CustomBoldTextView tv_emp_code,tv_version,tv_version_code;
//    MySharedPrefereces mySharedPrefereces;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_leave_approval);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
//        setSupportActionBar(toolbar);
//        iv_back = (ImageView) findViewById(R.id.iv_back);
//        iv_back.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                onBackPressed();
//            }
//        });
//        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
//        txt_act.setText("Approve Leave");
//
//        initView();
//    }
//
//    private void initView() {
//        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
//        tv_approved = (CustomBoldTextView) findViewById(R.id.tv_approved);
////        tv_approved.setText(getResources().getString(R.string.right));
//        tv_reject = (CustomBoldTextView) findViewById(R.id.tv_reject);
////        edtempname = (CustomTextView) findViewById(R.id.tv_reject);
//        llempname = (LinearLayout) findViewById(R.id.ll_emp_name);
//        edtleavebalance = (CustomTextView) findViewById(R.id.edt_leave_balance);
//        llleavebalance = (LinearLayout) findViewById(R.id.ll_leave_balance);
//        edtfromdate = (CustomTextView) findViewById(R.id.edt_from_date);
//        ivcalendar = (CustomTextView) findViewById(R.id.iv_calendar);
//        llfromdate = (LinearLayout) findViewById(R.id.ll_from_date);
//        edtday = (CustomTextView) findViewById(R.id.edt_day);
//        llday = (LinearLayout) findViewById(R.id.ll_day);
//
//        PackageInfo pInfo = null;
//        assert pInfo != null;
//
//        try
//        {
//            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//
//        } catch (PackageManager.NameNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//
//        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
//        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
//        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
//        tv_version.setText(pInfo.versionName);
//        tv_emp_code.setText(mySharedPrefereces.getEmpCode());
//    }
//}
