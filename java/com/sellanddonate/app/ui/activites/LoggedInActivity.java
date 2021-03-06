package com.sellanddonate.app.ui.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irfaan008.irbottomnavigation.SpaceItem;
import com.irfaan008.irbottomnavigation.SpaceNavigationView;
import com.irfaan008.irbottomnavigation.SpaceOnClickListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseActivity;
import com.sellanddonate.app.constant.Constants;
import com.sellanddonate.app.fragments.BidFragment;
import com.sellanddonate.app.fragments.ExploreFragment;
import com.sellanddonate.app.fragments.FragmentHandlerActivity;
import com.sellanddonate.app.fragments.MyAdsFragment;
import com.sellanddonate.app.fragments.SettingFragment;
import com.sellanddonate.app.util.ToastUtil;


// TODO : Bottom navigation link
//    https://github.com/irfaan008/IRBottomNavigationView

public class LoggedInActivity extends BaseActivity {

    @Override
    protected int getContentId() {
        return R.layout.activity_logged_in;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mainn, new ExploreFragment()).commit();

       // TODO : Bottom nav
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Adds", R.drawable.ic_exploration));
        spaceNavigationView.addSpaceItem(new SpaceItem("My Bid", R.drawable.ic_chat));
        spaceNavigationView.addSpaceItem(new SpaceItem("My Ads", R.drawable.ic_ads));
      //  spaceNavigationView.addSpaceItem(new SpaceItem("My Ads", R.drawable.ic_ads));
        spaceNavigationView.addSpaceItem(new SpaceItem("Settings",R.drawable.ic_settings));
        spaceNavigationView.showTextOnly();

        displayName();;

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {

                Intent status_intent = new Intent(LoggedInActivity.this, FragmentHandlerActivity.class);
                status_intent.putExtra(Constants.Extras.KEY_INDEX, "sell");
                status_intent.putExtra(Constants.Extras.KEY_VALUE, 1);
                startActivity(status_intent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Fragment fragment=null;
                switch (itemIndex){

                   // case "Explore":
                    case 0:

                         fragment=new ExploreFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mainn, fragment).commit();

                        break;
                    case 1:
                       // ToastUtil.showInfoLog("name","cf 2 Item name : "+ itemName);
//                        Intent _intent = new Intent(LoggedInActivity.this, FragmentHandlerActivity.class);
//                        _intent.putExtra(Constants.Extras.KEY_INDEX, "myAds");
//                        _intent.putExtra(Constants.Extras.KEY_VALUE, 3);
//                        startActivity(_intent);
                        fragment=new BidFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mainn,fragment).commit();
                        break;

                    case 2:
                        fragment=new MyAdsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mainn, fragment).commit();
                        break;
                    case 3:
                        fragment=new SettingFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_mainn, fragment).commit();
                        break;

                }

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                ToastUtil.showInfoLog("name","cf reselected Item name : "+ itemName);

            }
        });
    }
    @Override
    protected void initActions() {

    }
    private void displayName() {
        // FirebaseAuth mAuth;
        //  FirebaseDatabase firebaseDatabase;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("userName").getValue(String.class);

                Toast.makeText(LoggedInActivity.this, "Welcome " + name, Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences
                        = getSharedPreferences("username",
                        MODE_PRIVATE);
                SharedPreferences.Editor myEdit
                        = sharedPreferences.edit();
                myEdit.putString("name", name);
                myEdit.apply();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                ToastUtil.showErrorLog("error", "Failed to read : " + error.getMessage());
            }
        });
    }

}
