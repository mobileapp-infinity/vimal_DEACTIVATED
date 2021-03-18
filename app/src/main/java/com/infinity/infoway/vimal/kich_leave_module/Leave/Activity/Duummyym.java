package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomTextView;

public class Duummyym extends AppCompatActivity {

    CustomBoldTextView tvtitile;
    LinearLayout llcmt;
    CustomTextView etvcommentapproval;
    LinearLayout llcommentapproval;
    CustomTextView tvcreditapproval;
    LinearLayout llpdcreditapproval;
    CustomTextView tvdutyleaveapproval;
    LinearLayout lldutyleaveapproval;
    LinearLayout llcomment;
    CardView cardviewleave;
    LinearLayout llexpdetail;
    EditText edtPDFrameworkCredit3;
    LinearLayout llPDFrameworkCredit3;
    EditText edtNosdutyleaves3;
    LinearLayout llNosdutyleaves3;
    EditText edtRemarksApprove3;
    LinearLayout llRemarksApprove3;
    LinearLayout ll3field;
    EditText edtPDFrameworkCredit;
    LinearLayout llPDFrameworkCredit;
    EditText edtNosdutyleaves;
    LinearLayout llNosdutyleaves;
    EditText edtLeaveFromDate;
    LinearLayout llLeaveFromDate;
    EditText edtRegistrationFees;
    LinearLayout llRegistrationFees;
    EditText edtTransportation;
    LinearLayout llTransportation;
    EditText edtAccommodation;
    LinearLayout llAccommodation;
    EditText edtLeaveExpense;
    LinearLayout llLeaveExpense;
    EditText edtTotalExpense;
    LinearLayout llTotalExpense;
    EditText edtUnutilizedBudget;
    LinearLayout llUnutilizedBudget;
    EditText edtSanctionedExpense;
    LinearLayout llSanctionedExpense;
    EditText edtRemarksApprove;
    LinearLayout llRemarksApprove;
    CustomBoldTextView txtapprove;
    CustomBoldTextView tvcancel;
    LinearLayout llupdatedelete;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pd_app_approve_window);
    }

    private void initView() {
        tvtitile = (CustomBoldTextView) findViewById(R.id.tv_titile);
        llcmt = (LinearLayout) findViewById(R.id.ll_cmt);
        etvcommentapproval = (CustomTextView) findViewById(R.id.etv_comment_approval);
        llcommentapproval = (LinearLayout) findViewById(R.id.ll_comment__approval);
        tvcreditapproval = (CustomTextView) findViewById(R.id.tv_credit_approval);
        llpdcreditapproval = (LinearLayout) findViewById(R.id.ll_pd_credit_approval);
        tvdutyleaveapproval = (CustomTextView) findViewById(R.id.tv_duty_leave_approval);
        lldutyleaveapproval = (LinearLayout) findViewById(R.id.ll_duty_leave_approval);
        llcomment = (LinearLayout) findViewById(R.id.ll_comment);
        cardviewleave = (CardView) findViewById(R.id.card_view_leave);
        llexpdetail = (LinearLayout) findViewById(R.id.ll_exp_detail);
        edtPDFrameworkCredit3 = (EditText) findViewById(R.id.edt_PD_Framework_Credit_3);
        llPDFrameworkCredit3 = (LinearLayout) findViewById(R.id.ll_PDFrameworkCredit_3);
        edtNosdutyleaves3 = (EditText) findViewById(R.id.edt_Nosdutyleaves_3);
        llNosdutyleaves3 = (LinearLayout) findViewById(R.id.ll_Nosdutyleaves_3);
        edtRemarksApprove3 = (EditText) findViewById(R.id.edt_RemarksApprove_3);
        llRemarksApprove3 = (LinearLayout) findViewById(R.id.ll_RemarksApprove_3);
        ll3field = (LinearLayout) findViewById(R.id.ll_3_field);
        edtPDFrameworkCredit = (EditText) findViewById(R.id.edt_PD_Framework_Credit);
        llPDFrameworkCredit = (LinearLayout) findViewById(R.id.ll_PDFrameworkCredit);
        edtNosdutyleaves = (EditText) findViewById(R.id.edt_Nosdutyleaves);
        llNosdutyleaves = (LinearLayout) findViewById(R.id.ll_Nosdutyleaves);
        edtLeaveFromDate = (EditText) findViewById(R.id.edt_LeaveFromDate);
        llLeaveFromDate = (LinearLayout) findViewById(R.id.ll_LeaveFromDate);
        edtRegistrationFees = (EditText) findViewById(R.id.edt_RegistrationFees);
        llRegistrationFees = (LinearLayout) findViewById(R.id.ll_RegistrationFees);
        edtTransportation = (EditText) findViewById(R.id.edt_Transportation);
        llTransportation = (LinearLayout) findViewById(R.id.ll_Transportation);
        edtAccommodation = (EditText) findViewById(R.id.edt_Accommodation);
        llAccommodation = (LinearLayout) findViewById(R.id.ll_Accommodation);
        edtLeaveExpense = (EditText) findViewById(R.id.edt_LeaveExpense);
        llLeaveExpense = (LinearLayout) findViewById(R.id.ll_LeaveExpense);
        edtTotalExpense = (EditText) findViewById(R.id.edt_TotalExpense);
        llTotalExpense = (LinearLayout) findViewById(R.id.ll_TotalExpense);
        edtUnutilizedBudget = (EditText) findViewById(R.id.edt_UnutilizedBudget);
        llUnutilizedBudget = (LinearLayout) findViewById(R.id.ll_UnutilizedBudget);
        edtSanctionedExpense = (EditText) findViewById(R.id.edt_SanctionedExpense);
        llSanctionedExpense = (LinearLayout) findViewById(R.id.ll_SanctionedExpense);
        edtRemarksApprove = (EditText) findViewById(R.id.edt_RemarksApprove);
        llRemarksApprove = (LinearLayout) findViewById(R.id.ll_RemarksApprove);
        txtapprove = (CustomBoldTextView) findViewById(R.id.txt_approve);
        tvcancel = (CustomBoldTextView) findViewById(R.id.tv_cancel);
        llupdatedelete = (LinearLayout) findViewById(R.id.ll_update_delete);
        rl = (RelativeLayout) findViewById(R.id.rl);
    }
}
