package com.android.afla.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.android.afla.R;
import com.android.afla.UI.Admin.DashboardAdmin;
import com.android.afla.UI.SharedPreference.SaveSharedPreference;
import com.android.afla.UI.SuperAdmin.DashboardSuperAdmin;
import com.android.afla.UI.User.DashboardUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (SaveSharedPreference.getPREF_userType(getApplicationContext())) {
                    case "1":
                        startActivity(new Intent(getApplicationContext(), DashboardSuperAdmin.class));
                        break;
                    case "2":
                        startActivity(new Intent(getApplicationContext(), DashboardAdmin.class));
                        break;
                    case "3":
                        startActivity(new Intent(getApplicationContext(), DashboardUser.class));
                        break;
                    default:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        break;
                }


            }
        }, 1000);


    }
}