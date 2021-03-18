package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

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
import com.infinity.kich.Leave.Pojo.ForgotPswPojo;
public class ForgotpasswordActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Enter User Name
     */
    private EditText edt_uname;
    private Spinner spin_company;
    private LinearLayout ll_company;
    /**
     * Submit
     */
    private CustomBoldTextView tv_sign_in;
    /**
     * Sign Up
     */

    RequestQueue queue;
    private CustomBoldTextView tv_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        initView();
    }

    private void initView()
    {
        queue = Volley.newRequestQueue(this);
        edt_uname = (EditText) findViewById(R.id.edt_uname);

        tv_sign_in = (CustomBoldTextView) findViewById(R.id.tv_sign_in);
        tv_sign_in.setOnClickListener(this);
        tv_sign_up = (CustomBoldTextView) findViewById(R.id.tv_sign_up);
        tv_sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_sign_in:

                if (edt_uname.getText().toString().contentEquals("") || edt_uname.getText().toString().length() < 0)
                {
                    DialogUtils.Show_Toast(ForgotpasswordActivity.this,"Enter User Name");
                }
                else
                {
                    ForgotPswApiCall();
                }


                break;
            case R.id.tv_sign_up:
                break;
        }
    }

    private void ForgotPswApiCall()
    {

        DialogUtils.showProgressDialog(ForgotpasswordActivity.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Employee_Forgot_password + "&user_name=" + edt_uname.getText().toString().trim() + "&ip=" + "1" + "";
        url =  url.replace(" ","%20");

        System.out.println("Employee_Forgot_password URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();

                System.out.println("response of Employee_Forgot_password !!!!!!!!!!! " + response);
                response = response + "";
                if (response.length()>5)
                {
                    response = "{\"Data\":" + response + "}";

                    System.out.println("sucess response Employee_Forgot_password !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    ForgotPswPojo forgotPswPojo = gson.fromJson(response, ForgotPswPojo.class);
                    if (forgotPswPojo != null) {
                        if (forgotPswPojo.getData() != null) {
                            if (forgotPswPojo.getData().get(0) != null) {
                                if (forgotPswPojo.getData().size() > 0) {

                                    if (!forgotPswPojo.getData().get(0).getMsg().contentEquals("")) {
                                        DialogUtils.Show_Toast(ForgotpasswordActivity.this, forgotPswPojo.getData().get(0).getMsg());
                                        Intent intent =new Intent(ForgotpasswordActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }


                            }
                        }
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(ForgotpasswordActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }
}
