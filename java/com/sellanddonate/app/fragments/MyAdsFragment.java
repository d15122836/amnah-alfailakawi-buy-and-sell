package com.sellanddonate.app.fragments;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.adapter.MyAddsExlporeAdapter;
import com.sellanddonate.app.base.BaseFragment;
import com.sellanddonate.app.firebase.MyAdds;
import com.sellanddonate.app.ui.activites.History_Seller;
import com.sellanddonate.app.ui.activites.Seller_id_detailActivity;
import com.sellanddonate.app.ui.myaddtab.Tab_MainActivity;
import com.sellanddonate.app.ui.myaddtab.ui.main.Historly_seller;
import com.sellanddonate.app.util.ToastUtil;

import java.util.ArrayList;


public class MyAdsFragment extends BaseFragment implements MyAddsExlporeAdapter.OnClick_RecyclerVieww {

    // DatabaseReference databaseReference;
    private final ArrayList<MyAdds> productList = new ArrayList<>();
    private MyAddsExlporeAdapter exploreAdapter;

    private final ArrayList<String> catogoryList = new ArrayList<>();
    private RecyclerView recyclerView;

    DatabaseReference mDatabase;
   public static String Uidd;
    String catagory_name;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_my_ads;
    }

    @Override
    protected void init(View view) {
        // Toast.makeText(getActivity(),"loaded",Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

        Intent i=new Intent(getActivity(), History_Seller.class);
        startActivity(i);
            }
        });




        Uidd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.e("myadds", "its here");
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("category").addListenerForSingleValueEvent(new ValueEventListener() {
        mDatabase.child("category").addListenerForSingleValueEvent(new ValueEventListener() {
            //        mDatabase.child("category").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Log.e("myadds", dataSnapshot.toString());
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                        //Log.d("myadds", userDetails.child(Uid).getValue()+"");
//                        Log.d("valueEmail:", userDetails.child("Email").getValue());
//                        Log.d("valueuserid:", userDetails.child("userid").getValue());

                        if (userDetails.child(Uidd).getValue() != null) {
                            Log.e("myadds", userDetails.getKey().toString());
                            catagory_name = userDetails.getKey().toString();
                            MyAdds MyAddsDetails = userDetails.child(Uidd).getValue(MyAdds.class);
                            // productList.add(MyAddsDetails.setCategory(a));
                            //MyAdds MyAddsDetails = userDetails.child(Uid).getKey().;
                            productList.add(MyAddsDetails);
                            catogoryList.add(catagory_name);

                        }

                    }
                    exploreAdapter.update(productList,catogoryList);
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
        recyclerView = (RecyclerView) findViewById(R.id.rvExplore_id);

        exploreAdapter = new MyAddsExlporeAdapter(productList, catogoryList, this);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(exploreAdapter);

    }


    @Override
    public void onAdClick(final int position) {
        //Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

//        new AlertDialog.Builder(getActivity())
//                //.setTitle("Choose your option")
//                 .setMessage("Do you want to delete or Update you Add")
//
//                // Specifying a listener allows you to take an action before dismissing the dialog.
//                // The dialog is automatically dismissed when a dialog button is clicked.
//                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        mDatabase.child("category").child(catogoryList.get(position)).child(Uidd).removeValue();
//                        //recyclerView.removeViewAt(position);
//                        productList.remove(position);
//                        catogoryList.remove(position);
//                        exploreAdapter.notifyItemChanged(position);
//
//                        initalize();
//                        exploreAdapter.update(productList,catogoryList);
//                    }
//                })
//
//                // A null listener allows the button to dismiss the dialog and take no further action.
//                .setNegativeButton("Update", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        SharedPreferences sharpref= getActivity().getSharedPreferences("select", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor edit=sharpref.edit();
//                        edit.putString("selected",catagory_name.toLowerCase());
//                        edit.apply();
//                        Log.e("myadds",Uidd);
//                        Intent i= new Intent(getActivity(),FragmentHandlerActivity.class);
//                        i.putExtra(Constants.Extras.KEY_INDEX, "mobileSell");
//                        //i.putExtra(Constants.Extras.KEY_VALUE, value);
//                        startActivity(i);
//
//                    }
//                }).setNeutralButton("Sold", new DialogInterface.OnClickListener(){
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mDatabase.child("category").child(catogoryList.get(position)).child(Uidd).child("available").setValue(false);
//            }
//        })
//                //.setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
       // ToastUtil.showToast("ad"+catogoryList.get(position));
        Intent i=new Intent(getActivity(), Seller_id_detailActivity.class);
        i.putExtra("name",catogoryList.get(position));
        startActivity(i);
    }
}
