package com.infinity.infoway.vimal.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;

public class Suspecting_Main extends AppCompatActivity implements View.OnClickListener {
    private String title_screen = "";
    /**
     * Add \nSuspecting Report
     */
    private TextView txt_title_add_sus;
    private LinearLayout linear_add_sus;
    /**
     * View/Edit \nSuspecting Report
     */
    private TextView txt_title_view_sus;
    private LinearLayout linear_view_sus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suspecting_main);
        initView();
    }

    private void initView() {
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }

        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_title_add_sus = (TextView) findViewById(R.id.txt_title_add_sus);
        linear_add_sus = (LinearLayout) findViewById(R.id.linear_add_sus);
        linear_add_sus.setOnClickListener(this);
        txt_title_view_sus = (TextView) findViewById(R.id.txt_title_view_sus);
        linear_view_sus = (LinearLayout) findViewById(R.id.linear_view_sus);
        linear_view_sus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.linear_add_sus:

                Intent intent = new Intent(Suspecting_Main.this, Vimal_Suspecting_Entry.class);
                intent.putExtra("title_screen", "Add Suspecting Report");
                startActivity(intent);
                break;
            case R.id.linear_view_sus:

                 intent = new Intent(Suspecting_Main.this, Vimal_Suspecting_View_Edit.class);
                intent.putExtra("title_screen", "View/Edit Suspecting Report");
                startActivity(intent);
                break;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
