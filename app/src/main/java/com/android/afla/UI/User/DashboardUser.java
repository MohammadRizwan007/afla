package com.android.afla.UI.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.afla.R;
import com.android.afla.UI.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard_user);
        getSupportActionBar().setTitle("Search with fun");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logOut){
            Toast.makeText(DashboardUser.this, "You Have SuccessFully LogOut", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DashboardUser.this, LoginActivity.class);
            startActivity(intent);
        }
//        switch (item.getItemId()) {
//
//            case R.id.logOut:
//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(DashboardUser.this, "You Have SuccessFully LogOut", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(DashboardUser.this, LoginActivity.class);
//                startActivity(intent);
//                break;
//
//
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}