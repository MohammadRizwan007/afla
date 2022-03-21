package com.android.afla.UI;

import static com.android.afla.NetworkConnectivity.InternetConnectivity.isNetworkAvailable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.afla.R;
import com.android.afla.UI.SuperAdmin.DashboardSuperAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button btnLogin;
    private TextView signUp;
    private TextView forgetPassword;
    private FirebaseAuth fAuth;

    String Email = "";
    String Password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);
        findViews();
        clickListener();
    }

    private void clickListener() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
                checkFields();

//                fAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            shared_preferences(getApplicationContext(), email.getText().toString());
//                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        } else {
//                            btnLogin.setEnabled(true);
//                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                Toast.makeText(LoginActivity.this, "SignUp has been called", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Will be Available Soon", Toast.LENGTH_SHORT).show();
//                Intent resetPassword = new Intent(LoginActivity.this, ResetPasswordActivity.class);
//                startActivity(resetPassword);
            }
        });

    }

    private void findViews() {
        email = findViewById(R.id.eT_email);
        password = findViewById(R.id.eT_password);
        btnLogin = findViewById(R.id.btnLogin);
        forgetPassword = findViewById(R.id.tv_forgetpass);
        signUp = findViewById(R.id.txt_singup);
        fAuth = FirebaseAuth.getInstance();
    }

    private void checkFields() {

        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            email.setError("Email is Required.");
            btnLogin.setEnabled(true);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please Enter a Valid Email");
            btnLogin.setEnabled(true);
        } else if (TextUtils.isEmpty(Password)) {
            password.setError("Password is Required.");
            btnLogin.setEnabled(true);
        } else {
            if (checkInternet(0)) {
                getLogin();
            }
        }
    }

    private void getLogin() {
        btnLogin.setEnabled(false);
        if (Email.equals("anonymous@afla.com") && Password.equals("Anonymous@125")){
            Intent loginIntent = new Intent(LoginActivity.this, DashboardSuperAdmin.class);
            Toast.makeText(LoginActivity.this, "login Successfully", Toast.LENGTH_SHORT).show();
            startActivity(loginIntent);
        }else {
            btnLogin.setEnabled(true);
            Toast.makeText(LoginActivity.this, "Please enter correct credentials", Toast.LENGTH_SHORT).show();
        }

    }


    public void ShowHidePass(View view) {

        if (view.getId() == R.id.show_pass_btn) {

            if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.hide_password);

                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.show_password);
                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    private boolean checkInternet(final int check) {
        if (!isNetworkAvailable(getApplicationContext())) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make((parentLayout), R.string.internet_message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Respond to the click, such as by undoing the modification that caused
                            // this message to be displayed
                            if (!isNetworkAvailable(getApplicationContext())) {
                                checkInternet(check);
                            } else {
                                if (check == 0) {
                                    getLogin();
                                }
                            }
                        }
                    });
            snackbar.show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }


}