package com.sellanddonate.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RelativeLayout;

import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseFragment;
import com.sellanddonate.app.constant.Constants;

public class SellFragment extends BaseFragment implements View.OnClickListener {

    RelativeLayout
            mobileBtn,
            houseBtn,
            bikeBtn,

            carBtn,
            appartmentBtn,
            electronicsBtn;

    SharedPreferences pref;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_sell;
    }

    @Override
    protected void init(View view) {

        mobileBtn = (RelativeLayout) findViewById(R.id.mobileSell_id);
        houseBtn = (RelativeLayout) findViewById(R.id.houseSell_id);
        bikeBtn = (RelativeLayout) findViewById(R.id.bikeSell_id);

        carBtn = (RelativeLayout) findViewById(R.id.carSell_id);
        appartmentBtn = (RelativeLayout) findViewById(R.id.appartmentsSell_id);
        electronicsBtn = (RelativeLayout) findViewById(R.id.electronicsSell_id);



        mobileBtn.setOnClickListener(this);
        houseBtn.setOnClickListener(this);
        bikeBtn.setOnClickListener(this);
        carBtn.setOnClickListener(this);
        appartmentBtn.setOnClickListener(this);
        electronicsBtn.setOnClickListener(this);
        pref=getActivity().getSharedPreferences("select", Context.MODE_PRIVATE);


    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor=pref.edit();
        switch (v.getId()){

            case R.id.mobileSell_id:

                editor.putString("selected","Mobile/");
                editor.apply();
                startFragmentHandler("mobileSell",4);
                break;

            case R.id.houseSell_id:
                editor.putString("selected","House/");
                editor.apply();
                startFragmentHandler("mobileSell",4);
//                startFragmentHandler("houseSell",5);
                break;

            case R.id.bikeSell_id:
                editor.putString("selected","Bike/");
                editor.apply();
                startFragmentHandler("mobileSell",4);
//                startFragmentHandler("bikeSell",6);
                break;


            case R.id.carSell_id:
                editor.putString("selected","Car/");
                editor.apply();
                startFragmentHandler("mobileSell",4);
//                startFragmentHandler("carSell",8);
                break;

            case R.id.appartmentsSell_id:
                editor.putString("selected","Apartment/");
                editor.apply();
                startFragmentHandler("mobileSell",4);
//                startFragmentHandler("appartmentSell",9);
                break;

            case R.id.electronicsSell_id:
                editor.putString("selected","Electronic/");
                editor.apply();
                startFragmentHandler("mobileSell",4);
//                startFragmentHandler("electronicsSell",10);
                break;

        }

    }

    private void startFragmentHandler(String index,int value) {
        Intent _intent = new Intent(requireContext(), FragmentHandlerActivity.class);
        _intent.putExtra(Constants.Extras.KEY_INDEX, index);
        _intent.putExtra(Constants.Extras.KEY_VALUE, value);
        startActivity(_intent);
    }
}
