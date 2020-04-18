package com.sellanddonate.app.ui.myaddtab.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.sellanddonate.app.adapter.MyAddsExlporeAdapter;
import com.sellanddonate.app.adapter.SellerIdAdpater;
import com.sellanddonate.app.firebase.MyAdds;
import com.sellanddonate.app.firebase.SellerIdDetail;
import com.sellanddonate.app.ui.myaddtab.Tab_MainActivity;
import com.sellanddonate.app.util.ToastUtil;

import java.util.ArrayList;


public class Seller_id_detail extends Fragment implements SellerIdAdpater.OnClick_RecyclerVieww {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private PageViewModel pageViewModel;

    private final ArrayList<SellerIdDetail> productList = new ArrayList<>();
    private SellerIdAdpater exploreAdapter;

    private final ArrayList<String> catogoryList = new ArrayList<>();
    private RecyclerView recyclerView;

    DatabaseReference mDatabase;
    public static String Uidd;
    String catagory_name;

    View root;

    public static Seller_id_detail newInstance(int index) {
        Seller_id_detail fragment = new Seller_id_detail();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tab__seller_id, container, false);
        // final TextView textView = root.findViewById(R.id.section_label);
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        ToastUtil.showToast("seller_id");
        init();
        return root;
    }


    private void init() {
        Uidd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("seller").child(Uidd).child("mobile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Log.e("myadds", dataSnapshot.toString());
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                        //Log.d("myadds", userDetails.child(Uid).getValue()+"");
//                        Log.d("valueEmail:", userDetails.child("Email").getValue());
//                        Log.d("valueuserid:", userDetails.child("userid").getValue());

                        Log.e("seller_id",userDetails.getValue().toString());
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
        //initalize();
    }

//    private void initalize() {
//        recyclerView = (RecyclerView) root.findViewById(R.id.rv_seller_id);
//
//        exploreAdapter = new SellerIdAdpater(productList,  this);
//        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(requireContext());
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setAdapter(exploreAdapter);
//
//    }


    @Override
    public void onAdClick(final int position) {
        //Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

        new AlertDialog.Builder(getActivity())
                //.setTitle("Choose your option")
                 .setMessage("Do you want to Accept the Offer")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
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

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //mDatabase.child("category").child(catogoryList.get(position)).child(Uidd).child("available").setValue(false);
                    }
                }).setPositiveButton("Accept", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

           mDatabase.child("history").child(Uidd).child(productList.get(position).getProductid()).child("sold").setValue("Sold to "+productList.get(position).getUsername() +" for "+productList.get(position).getBidAmount());
             //   mDatabase.child("category").child(catogoryList.get(position)).child(Uidd).removeValue();
              //  mDatabase.child("category").child(catogoryList.get(position)).child(Uidd).removeValue();
            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();



        ToastUtil.showToast("clicked");
    }
}