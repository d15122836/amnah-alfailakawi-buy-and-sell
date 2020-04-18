package com.sellanddonate.app.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseActivity;
import com.sellanddonate.app.constant.Constants;

public class FragmentHandlerActivity extends BaseActivity {

    private FragmentManager manager;
   private FragmentTransaction transaction;
    private FrameLayout frameLayoutContainer;

    @Override
    protected int getContentId() {
        return R.layout.activity_fragment_handler;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        frameLayoutContainer = (FrameLayout) findViewById(R.id.fragmentContainer_id);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        // List of fragments
        SellFragment sellFragment = new SellFragment();
        ExploreFragment exploreFragment = new ExploreFragment();
        MyAdsFragment myAdsFragment = new MyAdsFragment();
        SellDetailFragment sellDetailFragment = new SellDetailFragment();
        ExploreDetailsFragment exploreDetailsFragment = new ExploreDetailsFragment();
        SettingFragment settingFragment = new SettingFragment();


        Intent slayer_intent = getIntent();
        Bundle bundle = slayer_intent.getExtras();
        if (bundle != null) {
            String index = bundle.getString(Constants.Extras.KEY_INDEX);

            assert index != null;
            switch (index) {

                default:
                case "sell":
                    FragmentReplacment(sellFragment);
                    break;

                case "explore":
                    FragmentReplacment(exploreFragment);
                    break;

                case "myAds":
                    FragmentReplacment(myAdsFragment);
                    break;
                case "mobileSell":
                    FragmentReplacment(sellDetailFragment);
                    break;

                case "mobileCat":
                    FragmentReplacment(exploreDetailsFragment);
                    break;

                case "profileSetting":
                   // FragmentReplacment(profileSettingFragment);
                    break;

                case "helpSetting":
                    //FragmentReplacment(helpCenterFragment);
                    break;

                case "setting":
                    FragmentReplacment(settingFragment);
                    break;

                case "product_all":
                    FragmentReplacment(exploreDetailsFragment);
                    break;
            }
        }
    }

    @Override
    protected void initActions() {

    }
    private void FragmentReplacment(Fragment fragment) {
        transaction.add(R.id.fragmentContainer_id, fragment);
        transaction.commit();
    }
}
