package com.sellanddonate.app.ui.activites;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.adapter.HistoryAdapter;
import com.sellanddonate.app.adapter.MyAddsExlporeAdapter;
import com.sellanddonate.app.firebase.MyAdds;
import com.sellanddonate.app.firebase.historyDetail;

import java.util.ArrayList;

public class History_Seller extends AppCompatActivity {

    private final ArrayList<MyAdds> productList = new ArrayList<>();
    private HistoryAdapter exploreAdapter;

    private final ArrayList<historyDetail> catogoryList = new ArrayList<>();
    private RecyclerView recyclerView;

    DatabaseReference mDatabase;
    public static String Uidd;
    String catagory_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history__seller);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

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

                        historyDetail MyAddsDetails = userDetails.getValue(historyDetail.class);
                        catogoryList.add(MyAddsDetails);
// productList.add(MyAddsDetails.setCategory(a));
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
                    exploreAdapter.update( catogoryList);
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
        recyclerView = (RecyclerView) findViewById(R.id.rv_history);

        exploreAdapter = new HistoryAdapter( catogoryList);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(History_Seller.this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(exploreAdapter);

    }
}


