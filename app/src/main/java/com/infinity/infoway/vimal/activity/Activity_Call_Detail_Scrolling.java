package com.infinity.infoway.vimal.activity;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.infinity.infoway.vimal.R;

public class Activity_Call_Detail_Scrolling extends AppCompatActivity {

    private ImageView img_profile;
    private Bundle data;
    private RadioButton rb_new_call;
    private EditText et_retailer,et_name,et_mobile,et_select_city,et_state,et_country,et_address1,et_address2,et_select_distributor;
    private Dialog fullScreenDialog;
    private String resImageUrl="";

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_detail_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setTitle("");
        initControls();

        if(getIntent().getExtras()!=null){
            data=getIntent().getExtras();
            if(data!=null){

                try{
                    if(!TextUtils.isEmpty(data.getString("ImageUrl"))){
                        resImageUrl=data.getString("ImageUrl");
                        Glide.with(Activity_Call_Detail_Scrolling.this).load(resImageUrl).into(img_profile);
                    }
                }catch (Exception ex){}

                if(!TextUtils.isEmpty(data.getString("CallType"))){
                    rb_new_call.setVisibility(View.VISIBLE);
                    rb_new_call.setText(data.getString("CallType"));
                    if(!TextUtils.isEmpty(data.getString("DateTime"))){
                        rb_new_call.setText(data.getString("CallType")+" ( "+data.getString("DateTime")+" )");
                    }
                }else{
                    rb_new_call.setVisibility(View.GONE);
                }

                if(!TextUtils.isEmpty(data.getString("ShopName"))){
                    et_retailer.setText(data.getString("ShopName"));
                }
                if(!TextUtils.isEmpty(data.getString("ConName"))){
                    et_name.setText(data.getString("ConName"));
                }
                if(!TextUtils.isEmpty(data.getString("Mobile"))){
                    et_mobile.setText(data.getString("Mobile"));
                }
                if(!TextUtils.isEmpty(data.getString("City"))){
                    et_select_city.setText(data.getString("City"));
                }
                if(!TextUtils.isEmpty(data.getString("State"))){
                    et_state.setText(data.getString("State"));
                }
                if(!TextUtils.isEmpty(data.getString("Country"))){
                    et_country.setText(data.getString("Country"));
                }
                if(!TextUtils.isEmpty(data.getString("Address1"))){
                    et_address1.setText(data.getString("Address1"));
                }
                if(!TextUtils.isEmpty(data.getString("Address2"))){
                    et_address2.setText(data.getString("Address2"));
                }

                if(!TextUtils.isEmpty(data.getString("Distributor"))){
                    et_select_distributor.setText(data.getString("Distributor"));
                }

            }
        }

    }

    private void initControls(){
        img_profile=findViewById(R.id.img_profile);
        rb_new_call=findViewById(R.id.rb_new_call);
        et_retailer=findViewById(R.id.et_retailer);
        et_name=findViewById(R.id.et_name);
        et_mobile=findViewById(R.id.et_mobile);
        et_select_city=findViewById(R.id.et_select_city);
        et_state=findViewById(R.id.et_state);
        et_country=findViewById(R.id.et_country);
        et_address1=findViewById(R.id.et_address1);
        et_address2=findViewById(R.id.et_address2);
        et_select_distributor=findViewById(R.id.et_select_distributor);

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreenImageDisplay();
            }
        });
    }

    private void fullScreenImageDisplay() {

        if (fullScreenDialog != null && fullScreenDialog.isShowing()) {
            fullScreenDialog.dismiss();
        }

        try {
            fullScreenDialog = new Dialog(Activity_Call_Detail_Scrolling.this);
            fullScreenDialog.setContentView(R.layout.layout_dialog_full_screen_image);
            fullScreenDialog.setCancelable(false);

            ImageView imgViewDisplay = fullScreenDialog.findViewById(R.id.imgViewDisplay);


            try{
                    Glide.with(Activity_Call_Detail_Scrolling.this).load(resImageUrl).into(imgViewDisplay);
            }catch (Exception ex){}

            ImageButton imgBtnClose = fullScreenDialog.findViewById(R.id.imgBtnClose);
            imgBtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fullScreenDialog.dismiss();
                }
            });

            fullScreenDialog.show();

        } catch (Exception ex) {
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
