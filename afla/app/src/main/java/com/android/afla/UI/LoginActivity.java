package com.android.afla.UI;

import static com.android.afla.NetworkConnectivity.InternetConnectivity.isNetworkAvailable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.android.afla.UI.Admin.DashboardAdmin;
import com.android.afla.UI.SharedPreference.SaveSharedPreference;
import com.android.afla.UI.SuperAdmin.DashboardSuperAdmin;
import com.android.afla.UI.User.DashboardUser;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button btnLogin;
    private TextView signUp;
    private TextView forgetPassword;
    private FirebaseFirestore fStore;
    private ImageView IV_facebookLogin;
    private ImageView IV_GoogleLogin;
    private FirebaseAuth fAuth;
    GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;


    String Email = "";
    String Password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        findViews();
        clickListener();
        createRequest();
    }

    private void clickListener() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
                checkFields();
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

        IV_facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SignInWithFaceBook();
                Toast.makeText(LoginActivity.this, "Available Soon", Toast.LENGTH_SHORT).show();
            }
        });

        IV_GoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInWithFaceBook();
            }
        });

    }

    private void createRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestIdToken("451012409384-sjtbj1plh9nfa067ec8jreclqt08hfm2.apps.googleusercontent.com")
                .requestEmail()
                .build();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("779716197153-4c79ldutbq4sqeg37af38hhd98odupbk.apps.googleusercontent.com")
//                .requestEmail()
//                .build();
//        mGoogleSigInClient = GoogleSignIn.getClient(LoginActivity.this,gso);
         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

    }

    private void SignInWithFaceBook() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void findViews() {
        email = findViewById(R.id.eT_email);
        password = findViewById(R.id.eT_password);
        btnLogin = findViewById(R.id.btnLogin);
        forgetPassword = findViewById(R.id.tv_forgetpass);
        signUp = findViewById(R.id.txt_singup);
        IV_facebookLogin = findViewById(R.id.IV_facebookLogin);
        IV_GoogleLogin = findViewById(R.id.IV_GoogleLogin);
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

    private void shared_preferences(Context context, String UserEmail, String UserType) {
        SaveSharedPreference.setValues(context, UserEmail, UserType);

    }

    private void getLogin() {
//        btnLogin.setEnabled(false);
        if (Email.trim().equalsIgnoreCase("anonymous@afla.com") && Password.equals("Anonymous@125")) {
            Intent loginIntent = new Intent(LoginActivity.this, DashboardSuperAdmin.class);
            loginIntent.putExtra("UserName", "Anonymous");
            shared_preferences(getApplicationContext(), "anonymous@afla.com", "1");
            Toast.makeText(LoginActivity.this, "login Successfully", Toast.LENGTH_SHORT).show();
            startActivity(loginIntent);
        } else {
            DocumentReference docRef = fStore.collection("admin").document(Email.trim().toLowerCase());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        // Document found in the offline cache
                        DocumentSnapshot document = task.getResult();

                        String adminEmail = document.getString("Email");

                        Log.e("LOGGER", "adminEmail " + document.getString("Email"));
                        Log.e("LOGGER", "adminPassword " + document.getString("Password"));

                        if (adminEmail != null) {
                            if (Email.trim().equalsIgnoreCase(document.getString("Email").trim()) && Password.equals(document.getString("Password"))) {
                                shared_preferences(getApplicationContext(), adminEmail, "2");
                                Intent adminIntent = new Intent(LoginActivity.this, DashboardAdmin.class);
                                startActivity(adminIntent);
                            } else {
                                clearFields();
                                Toast.makeText(LoginActivity.this, "User not found ! Check Email Password & Try Again", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            checkForUser();
                        }

                    } else {
                        checkForUser();
//                        Log.d(TAG, "Cached get failed: ", task.getException());
                    }
                }
            });
        }

    }

    private void checkForUser() {
        DocumentReference docRef = fStore.collection("user").document(Email.trim().toLowerCase());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    String userEmail = document.getString("Email");

                    if (userEmail != null) {
                        Log.e("LOGGER", "userEmail " + document.getString("Email"));
                        Log.e("LOGGER", "userPassword " + document.getString("Password"));
                        if (Email.trim().equalsIgnoreCase(document.getString("Email").trim()) && Password.equals(document.getString("Password"))) {
                            shared_preferences(getApplicationContext(), userEmail, "3");
                            Intent adminIntent = new Intent(LoginActivity.this, DashboardUser.class);
                            startActivity(adminIntent);
                        } else {
                            clearFields();
                            Toast.makeText(LoginActivity.this, "User not found ! Check Email Password & Try Again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        clearFields();
                        Toast.makeText(LoginActivity.this, "User not found ! Check Email Password & Try Again", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    clearFields();
                    Toast.makeText(LoginActivity.this, "User not found ! Check Email Password & Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                shared_preferences(getApplicationContext(), personEmail, "3");
                Intent intent = new Intent(LoginActivity.this, DashboardUser.class);
                startActivity(intent);
                Log.e("Email",""+personEmail);
            }
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void clearFields(){
        email.setText("");
        password.setText("");
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