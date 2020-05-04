package com.sellanddonate.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseFragment;
import com.sellanddonate.app.constant.Constants;


public class ExploreFragment extends BaseFragment implements View.OnClickListener {


    private RelativeLayout
            mobileCat_id,
            carCat_id,
            saleCat_id,
            electronicCat_id,
            bikeCat_id,
            propertyCat_id,product_all;


SharedPreferences prefs;



    @Override
    protected int getContentViewId() {
        return R.layout.fragment_explore;
    }

    @Override
    protected void init(View view) {
        getActivity().setTitle("People Ads Catagory");
        mobileCat_id = (RelativeLayout) findViewById(R.id.mobileCat_id);
        carCat_id = (RelativeLayout) findViewById(R.id.carCat_id);
        saleCat_id = (RelativeLayout) findViewById(R.id.saleCat_id);
        electronicCat_id = (RelativeLayout) findViewById(R.id.electronicCat_id);
        bikeCat_id = (RelativeLayout) findViewById(R.id.bikeCat_id);
        propertyCat_id = (RelativeLayout) findViewById(R.id.propertyCat_id);
        product_all = (RelativeLayout) findViewById(R.id.property_all_add);

        mobileCat_id.setOnClickListener(this);
        carCat_id.setOnClickListener(this);
        saleCat_id.setOnClickListener(this);
        electronicCat_id.setOnClickListener(this);
        bikeCat_id.setOnClickListener(this);
        propertyCat_id.setOnClickListener(this);
        product_all.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
       prefs= getActivity().getSharedPreferences("select", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        switch (v.getId()) {
            case R.id.mobileCat_id:

                editor.putString("selected","mobile");
                editor.apply();
                startFragmentHandler("mobileCat", 1);
                break;
            case R.id.carCat_id:

                editor.putString("selected","car");
                editor.apply();
                startFragmentHandler("mobileCat", 2);
                break;
            case R.id.saleCat_id:
                editor.putString("selected","house");
                editor.apply();
                startFragmentHandler("mobileCat", 3);
                break;
            case R.id.electronicCat_id:
                editor.putString("selected","electronic");
                editor.apply();
                startFragmentHandler("mobileCat", 4);
                break;
            case R.id.bikeCat_id:
                editor.putString("selected","bike");
                editor.apply();
                startFragmentHandler("mobileCat", 5);
                break;
            case R.id.propertyCat_id:
                editor.putString("selected","apartment");
                editor.apply();
                startFragmentHandler("mobileCat", 6);
                break;

            case R.id.property_all_add:
                editor.putString("selected","product_all");
                editor.apply();
                startFragmentHandler("mobileCat", 7);
                break;

        }
    }


    private void startFragmentHandler(String index, int value) {
        Intent _intent = new Intent(requireContext(), FragmentHandlerActivity.class);
        _intent.putExtra(Constants.Extras.KEY_INDEX, index);
        _intent.putExtra(Constants.Extras.KEY_VALUE, value);
        startActivity(_intent);

//        ExploreDetailsFragment fragment=new ExploreDetailsFragment();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mainn, fragment).addToBackStack(null).commit();

    }
}
