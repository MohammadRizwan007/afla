package com.android.afla.UI.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.afla.R;
import com.android.afla.UI.LoginActivity;

import java.util.Objects;

public class DashboardSuperAdmin extends AppCompatActivity {

    CardView cv_master_logout;
    CardView cv_create_admin;
    TextView tv_masterAdminName;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_dashboard_super_admin);

        Intent intent = getIntent();
        Username = intent.getStringExtra("UserName");
        findViews();
        setListener();

        tv_masterAdminName.setText(Username);



    }

    private void findViews() {
        cv_master_logout = findViewById(R.id.cv_master_logout);
        cv_create_admin = findViewById(R.id.cv_create_admin);
        tv_masterAdminName = findViewById(R.id.tv_masterAdminName);
    }

    private void setListener() {

        cv_create_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardSuperAdmin.this, CreateAdmin.class);
                startActivity(intent);
            }
        });


        cv_master_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardSuperAdmin.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}