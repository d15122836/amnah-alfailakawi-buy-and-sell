package com.sellanddonate.app.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseActivity;
import com.sellanddonate.app.firebase.UserProfileDetail;
import com.sellanddonate.app.firebase.historyDetail;

public class UserProfile extends BaseActivity {

    TextView electronic_tv, mobile_tv, bike_tv, apartmnt_tv, house_tv, car_tv, total_tv, earned_tv;
    DatabaseReference mDatabase;
    public static String Uidd;

    static int bike, house, apartment, mobile, car, electronics, total;

    @Override
    protected int getContentId() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        electronic_tv = findViewById(R.id.user_electronc);
        mobile_tv = findViewById(R.id.user_mobile);
        bike_tv = findViewById(R.id.user_bike);
        apartmnt_tv = findViewById(R.id.user_apartment);
        house_tv = findViewById(R.id.user_house);
        car_tv = findViewById(R.id.user_car);
        total_tv = findViewById(R.id.user_total);
        earned_tv = findViewById(R.id.user_earned);
        init();

    }

    @Override
    protected void initActions() {

    }

    private void init() {
        Uidd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("history").child(Uidd).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                        Log.e("History", userDetails.getValue() + "");
                        catagory(userDetails.getValue(UserProfileDetail.class).getProductName());
                        total(userDetails.getValue(UserProfileDetail.class).getPrice());
                    }
                }
                electronic_tv.setText("Electronic Sold: " + electronics);
                bike_tv.setText("Bike Sold: " + bike);
                car_tv.setText("Car Sold: " + car);
                house_tv.setText("House Sold: " + house);
                apartmnt_tv.setText("Apartment Sold: " + apartment);
                mobile_tv.setText("Mobile Sold: " + mobile);
                total_tv.setText("" + total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("myadds", databaseError + "a");
            }
        });

    }

    public void catagory(String a) {
        if (a.equals("bike")) {
            bike = bike + 1;
        } else if (a.equals("apartment")) {
            apartment = apartment + 1;
        } else if (a.equals("car")) {
            car = car + 1;
        } else if (a.equals("house")) {
            house = house + 1;
        } else if (a.equals("mobile")) {
            mobile += 1;
        } else {
            electronics += 1;
        }
    }

    public void total(int value) {
        total += value;
    }
}
