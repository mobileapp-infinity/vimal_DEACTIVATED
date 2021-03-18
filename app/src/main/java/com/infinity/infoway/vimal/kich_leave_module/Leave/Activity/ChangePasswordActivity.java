package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Pojo.ChangepswPojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    /**
     *
     */
    private CustomBoldTextView tv_emp_code;
    /**
     *
     */
    private CustomBoldTextView tv_version;
    /**
     *
     */
    private CustomBoldTextView tv_version_code;
    private LinearLayout ll_bottom;
    /**
     * Enter Old Paassword
     */
    private EditText edt_old_psw;
    /**
     * Enter New Password
     */
    private EditText edt_new_psw;
    /**
     * Enter Confirm Password
     */
    private EditText edt_confirm_psw;
    /**
     * Submit
     */
    private CustomBoldTextView tv_submit;
    /**
     * Sign Up
     */
    private CustomBoldTextView tv_sign_up;
    RequestQueue queue;
    MySharedPrefereces mySharedPrefereces;

    CheckBox chk_old_password,chk_new_password,chk_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_activity_change_password);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("Change Password");
    }

    private void initView() {
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        queue = Volley.newRequestQueue(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        rel = (RelativeLayout) findViewById(R.id.rel);
        toolbar_act = (Toolbar) findViewById(R.id.toolbar_act);
        toolbarContainer = (CoordinatorLayout) findViewById(R.id.toolbarContainer);
        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        edt_old_psw = (EditText) findViewById(R.id.edt_old_psw);
        edt_new_psw = (EditText) findViewById(R.id.edt_new_psw);
        edt_confirm_psw = (EditText) findViewById(R.id.edt_confirm_psw);
        tv_submit = (CustomBoldTextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_sign_up = (CustomBoldTextView) findViewById(R.id.tv_sign_up);

        PackageInfo pInfo = null;
        assert pInfo != null;

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        tv_version.setText(pInfo.versionName);
        tv_emp_code.setText(mySharedPrefereces.getEmpCode());

        chk_old_password =(CheckBox)findViewById(R.id.chk_old_password);
        chk_old_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edt_old_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_old_psw.setSelection(edt_old_psw.getText().toString().length());
                } else {
                    // hide password
                    edt_old_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_old_psw.setSelection(edt_old_psw.getText().toString().length());
                }
            }
        });
        chk_confirm_password=(CheckBox)findViewById(R.id.chk_confirm_password);
        chk_confirm_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edt_confirm_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_confirm_psw.setSelection(edt_confirm_psw.getText().toString().length());
                } else {
                    // hide password
                    edt_confirm_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_confirm_psw.setSelection(edt_confirm_psw.getText().toString().length());
                }
            }
        });
        chk_new_password=(CheckBox)findViewById(R.id.chk_new_password);
        chk_new_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked)
                {
                    // show password
                    edt_new_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_new_psw.setSelection(edt_new_psw.getText().toString().length());
                }
                else
                {
                    // hide password
                    edt_new_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_new_psw.setSelection(edt_new_psw.getText().toString().length());
                }
            }
        });
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            default:
                break;
            case R.id.tv_submit:
                if (validate())
                {
                    ChangePassword();
                }
                break;
        }
    }

    private void ChangePassword()
    {


        String old_psw = edt_old_psw.getText().toString();
        String new_psw = edt_new_psw.getText().toString();

        try
        {
            old_psw = URLEncoder.encode(old_psw, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        try
        {
            new_psw = URLEncoder.encode(new_psw, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        DialogUtils.showProgressDialog(ChangePasswordActivity.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Employee_Change_password + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "&oldPassword=" + old_psw + "&newPassword=" + new_psw + "";

        url = url.replace(" ","%20");

        System.out.println("Employee_Change_password URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                DialogUtils.hideProgressDialog();

                System.out.println("response of Employee_Change_password !!!!!!!!!!! " + response);
                response = response + "";
                if (response.length() > 5)
                {
                    response = "{\"Data\":" + response + "}";

                    System.out.println("sucess response Employee_Change_password !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    ChangepswPojo changepswPojo = gson.fromJson(response, ChangepswPojo.class);
                    if (changepswPojo != null)
                    {
                        if (changepswPojo.getData() != null)
                        {
                            if (changepswPojo.getData().get(0) != null)
                            {
                                if (changepswPojo.getData().size() > 0)
                                {
                                    if (!changepswPojo.getData().get(0).getMsg().contentEquals(""))
                                    {
                                        if (changepswPojo.getData().get(0).getMsg().compareToIgnoreCase("Password Changed")==0)
                                        {
                                            DialogUtils.Show_Toast(ChangePasswordActivity.this, changepswPojo.getData().get(0).getMsg());
                                            Intent intent = new Intent(ChangePasswordActivity.this, com.infinity.kich.Leave.Activity.MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                        {
                                            DialogUtils.Show_Toast(ChangePasswordActivity.this, changepswPojo.getData().get(0).getMsg());
                                        }

                                    }
                                }

                            }
                        }
                    }
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                DialogUtils.Show_Toast(ChangePasswordActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private boolean validate() {
        if (edt_old_psw.getText().toString().contentEquals("") || edt_old_psw.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(ChangePasswordActivity.this, "Enter Old Password");
            return false;
        } else if (edt_new_psw.getText().toString().contentEquals("") || edt_new_psw.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(ChangePasswordActivity.this, "Enter New Password");
            return false;
        } else if (edt_confirm_psw.getText().toString().contentEquals("") || edt_confirm_psw.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(ChangePasswordActivity.this, "Enter Confirm Password");
            return false;
        } else if (!edt_new_psw.getText().toString().contentEquals(edt_confirm_psw.getText().toString())) {
            DialogUtils.Show_Toast(ChangePasswordActivity.this, "Confirm Password Not Match");
            return false;
        }
        return true;
    }
}
