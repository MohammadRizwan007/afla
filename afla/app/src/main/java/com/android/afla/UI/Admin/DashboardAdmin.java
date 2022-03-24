package com.android.afla.UI.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class DashboardAdmin extends AppCompatActivity {
    TextView tv_AdminName;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Dashboard Admin");
        setContentView(R.layout.activity_dashboard_admin);

        tv_AdminName = findViewById(R.id.tv_AdminName);
        tv_AdminName.setText(SaveSharedPreference.getPREF_email(this));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logOut){
            signOut();
            Toast.makeText(DashboardAdmin.this, "You Have SuccessFully LogOut", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DashboardAdmin.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut(){
        SaveSharedPreference.removeValues(this);
        mGoogleSignInClient.signOut();
    }
}