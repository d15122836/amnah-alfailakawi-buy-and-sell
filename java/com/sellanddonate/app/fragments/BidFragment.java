package com.sellanddonate.app.fragments;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.adapter.BidAdapter;
import com.sellanddonate.app.adapter.MyAddsExlporeAdapter;
import com.sellanddonate.app.base.BaseFragment;
import com.sellanddonate.app.firebase.BidDetail;
import com.sellanddonate.app.firebase.MyAdds;

import java.util.ArrayList;


public class BidFragment extends BaseFragment {

    // DatabaseReference databaseReference;
    private final ArrayList<BidDetail> BidList = new ArrayList<>();
    private BidAdapter exploreAdapter;

    private final ArrayList<String> catogoryList = new ArrayList<>();
    private RecyclerView recyclerView;

    DatabaseReference mDatabase;
    public static String Uidd;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_bid;
    }

    @Override
    protected void init(View view) {

        Uidd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("bidder").child(Uidd).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Log.e("myadds", dataSnapshot.toString());
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                        Log.e("myBid", userDetails.getValue() + "  AAA");
//
                        BidDetail bidDetail=userDetails.getValue(BidDetail.class);
                        BidList.add(bidDetail);
//                        if (userDetails.child(Uidd).getValue() != null) {
//                            catagory_name = userDetails.getKey().toString();
//                            MyAdds MyAddsDetails = userDetails.child(Uidd).getValue(MyAdds.class);
//                            productList.add(MyAddsDetails);
//                            catogoryList.add(catagory_name);
//
//                        }

                    }
                    exploreAdapter.update(BidList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("myBid", databaseError + "a");
            }
        });
        initalize();

    }

    private void initalize() {
        recyclerView = (RecyclerView) findViewById(R.id.rvBid_id);

        exploreAdapter = new BidAdapter(getActivity(),BidList);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(exploreAdapter);

    }
}
