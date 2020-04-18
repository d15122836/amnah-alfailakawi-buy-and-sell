package com.sellanddonate.app.util;


// Created by Abdullah_Shah 
// on 19-09-2019.
// Either write something worth reading or do something worth writing :)


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sellanddonate.app.Application;



public class ToastUtil {

    @Nullable
    private static Toast mToast = null;


    public static void showToast(String text){

        if (mToast==null){
            mToast = Toast.makeText(Application.getInstance(), text, Toast.LENGTH_LONG);
        }else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    public static void showInProgressToast(){

        if (mToast==null){
            mToast = Toast.makeText(Application.getInstance(), "In Progress !!", Toast.LENGTH_LONG);
        }else {
            mToast.setText("In Progress !!");
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showInfoLog(String TAG,String log){
        Log.e(TAG," " +log);
    }

    public static void showErrorLog(String TAG,String log){
        Log.e(TAG," " + log);
    }

/*    public static void showSnackbar(View view,String message){

        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }*/

}
