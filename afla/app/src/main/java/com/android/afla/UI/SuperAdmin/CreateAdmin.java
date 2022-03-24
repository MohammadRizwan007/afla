package com.android.afla.UI.SuperAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.afla.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAdmin extends AppCompatActivity {

    EditText eT_admin_userName;
    EditText eT_admin_password;
    Button btnRegisterAdmin;
    ProgressBar pb_create_admin;

    String userEmail;
    String UserPass;

    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_create_admin);

        fStore = FirebaseFirestore.getInstance();

        findViews();
        setListener();

    }

    private void findViews() {
        eT_admin_userName = findViewById(R.id.eT_admin_userName);
        eT_admin_password = findViewById(R.id.eT_admin_password);
        btnRegisterAdmin = findViewById(R.id.btnRegisterAdmin);
        pb_create_admin = findViewById(R.id.pb_create_admin);
    }

    private void setListener() {
        btnRegisterAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
            }
        });
    }

    private void checkFields() {
        userEmail = eT_admin_userName.getText().toString();
        UserPass = eT_admin_password.getText().toString();

        if (userEmail.equals("") || UserPass.equals("")) {
            Toast.makeText(this, "Fields Cannot Be Left Empty", Toast.LENGTH_SHORT).show();
        } else {
            createUser();
        }
    }

    private void createUser() {

        pb_create_admin.setVisibility(View.VISIBLE);

        Map<String, Object> user = new HashMap<>();
        user.put("Email", userEmail);
        user.put("Password", UserPass);
        user.put("UserType", "1"); // Admin
//        Task<DocumentReference> documentReference = fStore.collection("users").document("admin").collection(userName).add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(CreateAdmin.this, "Admin Created Successfully", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(CreateAdmin.this, "Some Error Occurred! Please Try Again Later", Toast.LENGTH_SHORT).show();
//            }
//        });

        DocumentReference documentReference = fStore.collection("admin").document(userEmail.trim().toLowerCase());
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pb_create_admin.setVisibility(View.GONE);
                clearFields();
                Toast.makeText(CreateAdmin.this, "Admin Created Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pb_create_admin.setVisibility(View.GONE);
                clearFields();
                Toast.makeText(CreateAdmin.this, "Some Error Occurred! Please Try Again Later", Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "onFailure: " + e.toString());
            }
        });

    }

    private void clearFields(){
        eT_admin_userName.setText("");
        eT_admin_password.setText("");

    }
}