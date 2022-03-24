package com.android.afla.UI.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.afla.R;
import com.android.afla.UI.LoginActivity;
import com.android.afla.UI.SharedPreference.SaveSharedPreference;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Objects;

public class DashboardSuperAdmin extends AppCompatActivity {

    CardView cv_master_logout;
    CardView cv_create_admin;
    TextView tv_masterAdminName;
    String Username;
    GoogleSignInClient mGoogleSignInClient;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        tv_masterAdminName.setText(SaveSharedPreference.getPREF_email(this));



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
                signOut();
                Toast.makeText(DashboardSuperAdmin.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardSuperAdmin.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signOut(){
        SaveSharedPreference.removeValues(this);
        mGoogleSignInClient.signOut();
    }
}