package com.sellanddonate.app.ui.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseActivity;
import com.sellanddonate.app.ui.activites.LoggedInActivity;
import com.sellanddonate.app.ui.activites.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends BaseActivity {

    private static final int SPLASH_DISPLAY_LENGHT = 3000;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected int getContentId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(SplashScreenActivity.this, LoggedInActivity.class);
            endSplash(intent);
        }else {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            endSplash(intent);
        }
    }


    @Override
    protected void initActions() {

    }

    private void endSplash(final Intent intent) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                SplashScreenActivity.this.finish();

            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
