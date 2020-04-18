package com.sellanddonate.app.ui.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseActivity;
import com.sellanddonate.app.firebase.User;
import com.sellanddonate.app.util.ToastUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    AppCompatEditText
            userNameEtv,
            emailEtv,
            mobileEtv,
            addressEtv,
            passwordEtv,
            confirm_passwordEtv;

    AppCompatButton
            createAccountBtn, loginBtn;

    private FirebaseAuth mAuth;
    String dateFormatted;
    private ProgressBar progressBar;


    @Override
    protected int getContentId() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {


        userNameEtv = findViewById(R.id.userName_id);
        emailEtv = findViewById(R.id.email_id);
        mobileEtv = findViewById(R.id.mobile_id);
        addressEtv = findViewById(R.id.address_id);
        userNameEtv = findViewById(R.id.userName_id);
        passwordEtv = findViewById(R.id.password_id);
        confirm_passwordEtv = findViewById(R.id.confirm_password_id);

        createAccountBtn = findViewById(R.id.createAccountBtn_id);
        loginBtn = findViewById(R.id.loginBtn_id);

        progressBar = findViewById(R.id.progressBar);

        Date date = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("EEE, MMM d, ''yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormatted = formatter.format(date);
        // Firebase
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void initActions() {

        createAccountBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }


    private void registerUser() {

        progressBar.setVisibility(View.VISIBLE);

        final String userName = userNameEtv.getText().toString().trim();
        final String email = emailEtv.getText().toString().trim();
        final String mobile = mobileEtv.getText().toString().trim();
        final String address = addressEtv.getText().toString().trim();
        String password = passwordEtv.getText().toString().trim();
        String confirm_password = confirm_passwordEtv.getText().toString().trim();

        if (userName.isEmpty()) {
            userNameEtv.setError("User Name Required");
            userNameEtv.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEtv.setError("Email Required");
            emailEtv.requestFocus();
        }
       // if (mobile.length() != 10) {
       //     mobileEtv.setError("Invalid Phone Number");
        //    mobileEtv.requestFocus();
      //  }
        if (address.isEmpty()) {
            addressEtv.setError("Address Required");
            addressEtv.requestFocus();
        }
        if (!password.equals(confirm_password)) {
            passwordEtv.setError("Password Does Not Match");
            passwordEtv.requestFocus();
        }
        if (password.isEmpty()) {
            confirm_passwordEtv.setError("Password Required");
            confirm_passwordEtv.requestFocus();
        }


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // TODO : Reg success Store additional fields

                    User userInfo = new User(
                            userName,
                            email,
                            mobile,
                            address,
                            dateFormatted


                    );

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                ToastUtil.showToast("User Successfully Registered ");
                                ToastUtil.showErrorLog("error", "User registered successfully");


                    // Storing data into SharedPreferences
                                SharedPreferences sharedPreferences
                                        = getSharedPreferences("username",
                                        MODE_PRIVATE);
                                SharedPreferences.Editor myEdit
                                        = sharedPreferences.edit();
                                myEdit.putString("name",  userName);
                                myEdit.apply();
                                startActivity(new Intent(RegistrationActivity.this, LoggedInActivity.class));
                            } else {
                                ToastUtil.showToast("User Registration Failed");
                                ToastUtil.showErrorLog("error", "User Reg failed : " + task.getException().getMessage());

                            }
                        }
                    });

                } else {

                    ToastUtil.showErrorLog("error", "error we got is " + task.getException().getMessage());
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccountBtn_id:
                registerUser();

                break;
            case R.id.loginBtn_id:
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                break;
        }

    }
}
