package com.android.afla.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.afla.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private FirebaseFirestore fStore;
    private String userID;
    private EditText userName, Password, Email, Phone;
    private FirebaseAuth fAuth;
    private Button register;
    private TextView passStat, txt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        register = findViewById(R.id.btnRegister);
        userName = findViewById(R.id.eT_userName);
        Password = findViewById(R.id.eT_password);
        Phone = findViewById(R.id.eT_phone);
        txt_login = findViewById(R.id.txt_login);
        Email = findViewById(R.id.eT_email);


        //TextView
        passStat = (TextView) findViewById(R.id.tv_passStat);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        // 2) registration
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register.setEnabled(false);
                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String fullName = userName.getText().toString();
                final String number = Phone.getText().toString();

                if (TextUtils.isEmpty(fullName)) {
                    userName.setError("User Name is Required");
                    register.setEnabled(true);
                } else if (TextUtils.isEmpty(email)) {
                    Email.setError("Email is Required.");
                    register.setEnabled(true);
                } else if (TextUtils.isEmpty(password)) {
                    Password.setError("Password is Required.");
                    register.setEnabled(true);
                } else if (password.length() < 6) {
                    Password.setError("Password Must be >= 6 Characters");
                    register.setEnabled(true);
                }
//                else if (number.isEmpty() || number.length() < 10) {
//                    Phone.setError("Enter Valid Number");
//                    Phone.requestFocus();
//                    register.setEnabled(true);
//                }
                else {
                    Toast.makeText(SignUpActivity.this, "Will Be Available Soon, Wait !!", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(SignUpActivity.this, "Please, Wait !!", Toast.LENGTH_SHORT).show();
//                    saveData();
                }
            }


        });

        //Validate password Pattern
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = Password.getText().toString().trim();
                validatePassword(password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                Toast.makeText(SignUpActivity.this, "Login has been called", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    // Validate password Pattern
    public void validatePassword(String pass) {
        Pattern CapsLetter = Pattern.compile("[A-Z]");
        Pattern SmallLetter = Pattern.compile("[a-z]");
        Pattern numCase = Pattern.compile("[0-9]");
        if (!CapsLetter.matcher(pass).find() || !SmallLetter.matcher(pass).find() || !numCase.matcher(pass).find()) {
            passStat.setText("Password is weak");
//            passStat.setTextColor(android.R.color.holo_red_dark);
        } else if (pass.length() < 7) {
            passStat.setText("Length is short");
            //passStatus.setTextColor(android.R.color.holo_red_dark);
        } else {
            passStat.setText("password is Strong");
//            passStat.setTextColor(android.R.color.holo_green_light);
        }

    }

    public void saveData() {
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String fullName = userName.getText().toString();
        final String number = Phone.getText().toString();

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // send verification link
                    FirebaseUser fuser = fAuth.getCurrentUser();
                    assert fuser != null;
                    fuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Verification Link send to your Gmail", Toast.LENGTH_SHORT).show();

                            } else
                                Toast.makeText(SignUpActivity.this, "Failed to send verification Try again", Toast.LENGTH_SHORT).show();
                        }
                    });

                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Name", fullName);
                    user.put("email", email);
                    user.put("phone", number);
                    user.put("password", password);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "onSuccess: user Profile is created for " + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: " + e.toString());
                        }
                    });

                    Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(loginIntent);

                } else {
                    Toast.makeText(SignUpActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    register.setEnabled(true);
                }
            }

        });

    }
}