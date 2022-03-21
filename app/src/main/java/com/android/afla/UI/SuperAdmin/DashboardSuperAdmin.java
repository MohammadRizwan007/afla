package com.android.afla.UI.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.afla.R;
import com.android.afla.UI.LoginActivity;

import java.util.Objects;

public class DashboardSuperAdmin extends AppCompatActivity {

    CardView cv_master_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_dashboard_super_admin);

        cv_master_logout = findViewById(R.id.cv_master_logout);
        cv_master_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardSuperAdmin.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}