package com.sellanddonate.app.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;

import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseActivity;
import com.sellanddonate.app.util.ToastUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    AppCompatEditText
            emailLoginEtv,
            passwordLoginEtv;

    AppCompatButton
            loginActivityBtn,
            loginBtn_id;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;




    @Override
    protected int getContentId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        emailLoginEtv = findViewById(R.id.email_login_id);
        passwordLoginEtv = findViewById(R.id.password_login_id);

        progressBar = findViewById(R.id.progressBar);



        loginActivityBtn = findViewById(R.id.loginActivityBtn_id);
        loginBtn_id = findViewById(R.id.loginBtn_id);

        // FireBase
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void initActions() {

        loginActivityBtn.setOnClickListener(this);
        loginBtn_id.setOnClickListener(this);
    }

    public void loginWithEmail(){
        progressBar.setVisibility(View.VISIBLE);
        final String email = emailLoginEtv.getText().toString().trim();
        String password = passwordLoginEtv.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLoginEtv.setError("Email Required");
            emailLoginEtv.requestFocus();
        }
        if (password.isEmpty()) {
            emailLoginEtv.setError("Password Required");
            emailLoginEtv.requestFocus();
        }
        if (!email.isEmpty()){
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                ToastUtil.showToast("Log in success");
                                progressBar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(LoginActivity.this,LoggedInActivity.class));
                            }else {
                                ToastUtil.showToast("Some thing went wrong ");
                                ToastUtil.showErrorLog("error","error we got is : " + task.getException().getMessage());
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        }else{
            ToastUtil.showToast("Try again");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginActivityBtn_id:

                loginWithEmail();

               /* if (mAuth.getCurrentUser()!= null){
                    startActivity(new Intent(LoginActivity.this,LoggedInActivity.class));
                }else {
                    ToastUtil.showToast("Something went wrong");
                }*/
                break;
            case R.id.loginBtn_id:
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            // TODO : Handle the current user
        }
    }
}
