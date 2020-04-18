package com.sellanddonate.app.ui.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.adapter.ExploreDetailAddAdapter;
import com.sellanddonate.app.util.ToastUtil;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SellerProfileDetail extends AppCompatActivity {

    String name, phoneNumber, email, address, member_since, sellerId;
    TextView tv_name, tv_PhoneNumber, tv_email, tv_address, tv_member_since;



    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile_detail);
        init();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Intent intent = this.getIntent();

        sellerId = intent.getStringExtra("seller_id");


      //  ToastUtil.showToast(sellerId);



        displayName();


    }

    private void displayName() {
        DatabaseReference databaseReference =  firebaseDatabase.getReference("Users").child(sellerId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("userName").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String address = dataSnapshot.child("address").getValue(String.class);
                String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                String member_since = dataSnapshot.child("member_since").getValue(String.class);


                tv_name.setText("Seller Name: " + name);
                tv_PhoneNumber.setText("Phone Number:" + phoneNumber);
                tv_address.setText("Address:" + address);
                tv_email.setText("Phone Number:" + email);
                tv_member_since.setText("The Seller is selling Products since: \n" + member_since);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                ToastUtil.showErrorLog("error", "Failed to read : " + error.getMessage());
            }
        });
    }

    public void init() {
        tv_name = findViewById(R.id.tv_name);
        tv_PhoneNumber = findViewById(R.id.tv_phone_number);
        tv_email = findViewById(R.id.tv_email);
        tv_address = findViewById(R.id.tv_address);
        tv_member_since = findViewById(R.id.tv_member_since);


        Firebase.setAndroidContext(this);
    }

    public void display_text() {
        tv_name.setText("Seller Name: " + name);
        tv_PhoneNumber.setText("Phone Number:" + phoneNumber);
        tv_address.setText("Phone Number:" + address);
        tv_email.setText("Phone Number:" + email);
        tv_member_since.setText("The Seller is selling Products since: \n" + member_since);


    }


}

