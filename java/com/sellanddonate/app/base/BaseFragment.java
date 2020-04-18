package com.sellanddonate.app.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(getContentViewId(), container, false);
        init(view);

        return view;
    }

    protected abstract int getContentViewId();

    protected abstract void init(View view);

    public View findViewById(int id) {
        return view.findViewById(id);
    }
}
