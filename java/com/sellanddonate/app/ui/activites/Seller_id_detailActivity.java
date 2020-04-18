package com.sellanddonate.app.ui.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.adapter.SellerIdAdpater;
import com.sellanddonate.app.firebase.SellerIdDetail;
import com.sellanddonate.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;


public class Seller_id_detailActivity extends AppCompatActivity implements SellerIdAdpater.OnClick_RecyclerVieww {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private PageViewModel pageViewModel;

    private final ArrayList<SellerIdDetail> productList = new ArrayList<>();
    private SellerIdAdpater exploreAdapter;

    // private final ArrayList<String> catogoryList = new ArrayList<>();
    private RecyclerView recyclerView;

    DatabaseReference mDatabase;
    public static String Uidd;
    String catagory_name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab__seller_id);


        Intent i = getIntent();
        catagory_name = i.getStringExtra("name");
        //ToastUtil.showToast("seller_id   "+catagory_name);
        init();
    }


    private void init() {
        Uidd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("seller").child(Uidd).child(catagory_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Log.e("myadds", dataSnapshot.toString());
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userDetails : dataSnapshot.getChildren()) {

                        Log.e("seller_id", userDetails.getValue().toString());
                        SellerIdDetail SellerAddsDetails = userDetails.getValue(SellerIdDetail.class);
                        productList.add(SellerAddsDetails);

//                        if (userDetails.child(Uidd).getValue() != null) {
//                            Log.e("myadds", userDetails.getKey().toString());
//                            catagory_name = userDetails.getKey().toString();
//                            MyAdds MyAddsDetails = userDetails.child(Uidd).getValue(MyAdds.class);
//                            // productList.add(MyAddsDetails.setCategory(a));
//                            //MyAdds MyAddsDetails = userDetails.child(Uid).getKey().;
//                            productList.add(MyAddsDetails);
//                            catogoryList.add(catagory_name);
//
//                        }

                    }
                    exploreAdapter.update(productList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("myadds", databaseError + "a");
            }
        });
        initalize();
    }

    private void initalize() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_seller_id);

        exploreAdapter = new SellerIdAdpater(productList, this);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(Seller_id_detailActivity.this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(exploreAdapter);

    }


    @Override
    public void onAdClick(final int position) {
        //Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

        new AlertDialog.Builder(Seller_id_detailActivity.this)
                //.setTitle("Choose your option")
                .setMessage("Do you want to Accept the Offer")

//                    }
//                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       // mDatabase.child("category").child(catogoryList.get(position)).child(Uidd).child("available").setValue(false);
                        mDatabase.child("bidder").child(productList.get(position).getBidderId()).child(productList.get(position).getProductid()).child("status").setValue("reject");

                    }
                }).setPositiveButton("Accept", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                int i = Integer.parseInt(productList.get(position).getBidAmount());
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("productName", catagory_name);
                hashMap.put("price", i);
                hashMap.put("sold", catagory_name + " sold to " + productList.get(position).getUsername() + " for " + i);



               // mDatabase.child("history").child(Uidd).child(productList.get(position).getProductid()).child("sold").setValue(catagory_name + " sold to " + productList.get(position).getUsername() + " for " + productList.get(position).getBidAmount());
                mDatabase.child("history").child(Uidd).child(productList.get(position).getProductid()).setValue(hashMap);
                mDatabase.child("category").child(catagory_name).child(Uidd).removeValue();
                mDatabase.child("seller").child(Uidd).child(catagory_name).removeValue();
                mDatabase.child("bidder").child(productList.get(position).getBidderId()).child(productList.get(position).getProductid()).child("status").setValue("accept");


                //String aa =mDatabase.child("bidder").orderByKey().equalTo(productList.get(position).getProductid());
                // ToastUtil.showToast(aa);

            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


        // ToastUtil.showToast("clicked");
    }
}