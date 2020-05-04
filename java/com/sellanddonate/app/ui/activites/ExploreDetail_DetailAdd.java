package com.sellanddonate.app.ui.activites;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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

public class ExploreDetail_DetailAdd extends AppCompatActivity implements View.OnClickListener {

    String title, price, des, productId, sellerId;
    int high_bid;
    TextView tv_title, tv_price, tv_des, tv_avail;
    ViewPager viewPager;
    ArrayList<String> list;
    String unique_id;
    Button b_call, b_sms, b_chat;
    String child_str;
    DatabaseReference databaseReference;
    String chatWith;
    double lat, longt;
    boolean avail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_detail__detail_add);
        init();
        Intent intent = this.getIntent();
        title = intent.getStringExtra("title");
        price = intent.getStringExtra("price");
        des = intent.getStringExtra("des");
        productId = intent.getStringExtra("productId");
        sellerId = intent.getStringExtra("sellerId");
        high_bid = intent.getIntExtra("high_bid", 0);
        lat = intent.getDoubleExtra("lat", 0.00);
        longt = intent.getDoubleExtra("longt", 0.00);
        avail = intent.getBooleanExtra("avail", true);
        //list=intent.getParcelableArrayListExtra("list");
        list = intent.getStringArrayListExtra("list");
        unique_id = list.get(0);
        ToastUtil.showToast(longt + " log " + lat);

        SharedPreferences prefs = ExploreDetail_DetailAdd.this.getSharedPreferences("select", Context.MODE_PRIVATE);
        child_str = prefs.getString("selected", "bike");
        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference();


        display_text();
        display_dots();


    }

    public void init() {
        tv_title = findViewById(R.id.tv_title);
        tv_price = findViewById(R.id.tv_price);
        tv_des = findViewById(R.id.tv_des);
        tv_avail = findViewById(R.id.tv_avail);
        viewPager = findViewById(R.id.view_pager);

        b_call = findViewById(R.id.button_call);
        b_sms = findViewById(R.id.button_sms);
        b_chat = findViewById(R.id.button_chat);

        b_call.setOnClickListener(this);
        b_sms.setOnClickListener(this);
        b_chat.setOnClickListener(this);
        Firebase.setAndroidContext(this);
    }

    public void display_text() {
        tv_title.setText(title);
        tv_price.setText("Price:" + price);
        tv_des.setText("Description: \n" + des);
        if (avail) {
            tv_avail.setText("Status: Available");
            // tv_avail.setTextColor(Color.parseColor("#128F17"));
        } else {
            tv_avail.setText("Status: Sold");
            // tv_avail.setTextColor(Color.parseColor("#EE0519"));
        }


        viewPager.setOffscreenPageLimit(list.size() - 1);
        ExploreDetailAddAdapter adapter = new ExploreDetailAddAdapter(ExploreDetail_DetailAdd.this, list);
        viewPager.setAdapter(adapter);
    }

    public void display_dots() {
        SpringDotsIndicator dotsIndicator = (SpringDotsIndicator) findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_call:
                firebaseCallNumber(2);
                break;
            case R.id.button_sms:
                firebaseCallNumber(1);
                break;
            case R.id.button_chat:
                bid_dialog();
                break;
        }
    }

    private void bid_dialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        final EditText input = new EditText(ExploreDetail_DetailAdd.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Enter BID AMOUNT");
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(input);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(ll);
        builder.setTitle("BID amount you want to offer");
        builder.setPositiveButton("BID", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                String price = input.getText().toString().trim();

                firebase_bid(price);

            }


        });


        alertDialog = builder.create();
        alertDialog.show();


    }

    public void firebase_bid(String bid_amount) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

        Firebase ref = new Firebase("https://sell-and-donate.firebaseio.com/");
        Firebase newRefbidder = ref.child("bidder").push();
        // String a = newRefbidder.getKey().toString();
        String auth = FirebaseAuth.getInstance().getUid();
        SharedPreferences sharedPreferences = getSharedPreferences("username", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "Stranger");


        SharedPreferences prefs = ExploreDetail_DetailAdd.this.getSharedPreferences("select", Context.MODE_PRIVATE);
        String product = prefs.getString("selected", "bike");

        Map updatebidder = new HashMap();
        updatebidder.put("status", "pending");
        updatebidder.put("username", name);
        updatebidder.put("title", product);
        updatebidder.put("bidAmount", bid_amount);
        updatebidder.put("lat", lat);
        updatebidder.put("longt", longt);
        updatebidder.put("payment", "unpaid");
        updatebidder.put("productId", productId);

        Map updateseller = new HashMap();
        updateseller.put("productid", productId);
        updateseller.put("username", name);
        updateseller.put("bidAmount", bid_amount);
        updateseller.put("bidderId", auth);


        //Firebase ref = new Firebase.setAndroidContext();
        // ToastUtil.showToast(productId);

        Map fanoutObject = new HashMap();
        fanoutObject.put("/bidder/" + auth + "/" + productId + "/", updatebidder);

        int finalValue = Integer.parseInt(bid_amount);
        if (finalValue > high_bid) {
            fanoutObject.put("/category/" + child_str + "/" + sellerId + "/" + "bid", Integer.parseInt(bid_amount));
        } else {
        }

        fanoutObject.put("/seller/" + sellerId + "/" + product + "/" + auth + "/", updateseller);
        ref.updateChildren(fanoutObject); // atomic


    }

    public void firebaseCallNumber(final int type) {
        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phoneNumber");
        zonesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("tab", dataSnapshot.getValue(String.class));
                if (type == 1) {
                    sms(dataSnapshot.getValue(String.class));
                } else {
                    cal(dataSnapshot.getValue(String.class));
                }
                // Toast.makeText(ExploreDetail_DetailAdd.this, "" + dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("tab", "onCancelled", databaseError.toException());
            }
        });
    }


    public void sms(String phoneNumber) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        sendIntent.setData(Uri.parse("smsto:" + phoneNumber));
        //sendIntent.putExtra("sms_body", x);
        startActivity(sendIntent);
    }

    public void cal(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);


    }


}

