package com.sellanddonate.app.base;




import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    final protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentId());
        initViews(savedInstanceState);
        initActions();
    }

    protected abstract int getContentId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initActions();
}