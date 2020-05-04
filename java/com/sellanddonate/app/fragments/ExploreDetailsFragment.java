package com.sellanddonate.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.adapter.ExploreAdapter;
import com.sellanddonate.app.base.BaseFragment;
import com.sellanddonate.app.firebase.SellDetails;
import com.sellanddonate.app.ui.activites.ExploreDetail_DetailAdd;
import com.sellanddonate.app.ui.activites.SellerProfileDetail;
import com.sellanddonate.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ExploreDetailsFragment extends BaseFragment implements ExploreAdapter.OnClick_RecyclerView {
    private DatabaseReference databaseReference;

    private static List<?> list;
    private ArrayList<String> seller_list = new ArrayList<String>();
    private ArrayList<String> catagory_list = new ArrayList<String>();

    private ExploreAdapter exploreAdapter;
    private RecyclerView recyclerView;
    private final ArrayList<SellDetails> productList = new ArrayList<>();
    int type;
    double lat, longt;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_explore_details;
    }

    @Override
    protected void init(View view) {
        getActivity().setTitle("Active Ads");
        final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        list = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rvExplore_id);

        SharedPreferences prefs = getActivity().getSharedPreferences("select", Context.MODE_PRIVATE);
        String child_str = prefs.getString("selected", "bike");
        if (child_str.equals("product_all")) {
            // ToastUtil.showToast("all product");
            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("category");
            ToastUtil.showErrorLog("uid", Uid);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot downloadData : dataSnapshot.getChildren()) {

                            for (DataSnapshot downloadDataChildren : downloadData.getChildren()) {
                                SellDetails sellDetails = downloadDataChildren.getValue(SellDetails.class);
                                ToastUtil.showErrorLog("uid", downloadDataChildren.getKey().toLowerCase() + "    ok");
                                catagory_list.add(downloadData.getKey());
                                type = 1;

                                if (downloadDataChildren.getKey().toLowerCase().equals(Uid.toLowerCase())) {
                                    //ToastUtil.showToast("ok");
                                } else {

                                    productList.add(sellDetails);
                                    seller_list.add(downloadDataChildren.getKey());
                                }
                                // ToastUtil.showErrorLog("getKey", downloadData.getValue() + "   addd " );
                                ToastUtil.showErrorLog("getKey", downloadDataChildren.getValue() + "   addd ");
                            }
                        }

                        //TODO: pass to adapter
                        exploreAdapter.update(productList);
                    }

                    ToastUtil.showInfoLog("data", "datasnapshot : " + dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("category")
                    .child(child_str.toLowerCase());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot downloadData : dataSnapshot.getChildren()) {
                            SellDetails sellDetails = downloadData.getValue(SellDetails.class);
                            type = 0;
                            // String sellerId=downloadData.getKey();
                            if (downloadData.getKey().equals(Uid)) {
                                //ToastUtil.showToast("ok");
                            } else {
                                productList.add(sellDetails);
                                seller_list.add(downloadData.getKey());
                            }
                            // key_list.add(downloadData.getKey().toString());
                            //ToastUtil.showErrorLog("loggg",new Gson().toJson(sellDetails));
                            ToastUtil.showErrorLog("getKey", downloadData.getKey() + "  addd " + downloadData.getValue());
                        }

                        //TODO: pass to adapter
                        exploreAdapter.update(productList);

                    }

                    ToastUtil.showInfoLog("data", "datasnapshot : " + dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        initalize();
    }

    public static List<?> convertObjectToList(Object obj) {
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[]) obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>) obj);
        }
        return list;
    }

    private void initalize() {

        exploreAdapter = new ExploreAdapter(productList, this);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(exploreAdapter);

    }

    @Override
    public void onAddClick(int position, int type_adapter) {
        if (type_adapter == 1) {
            if (type == 1) {
                String categoryName = catagory_list.get(position);
                //ToastUtil.showToast(categoryName);
                SharedPreferences prefs = getActivity().getSharedPreferences("select", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("selected", categoryName.toLowerCase());
                editor.apply();
            }
            SellDetails sellDetails = productList.get(position);
            String sellerId = seller_list.get(position);

            String title = sellDetails.getTitle();
            String des = sellDetails.getDescription();
            String price = sellDetails.getPrice();
            String productId = sellDetails.getProductId();
            boolean avail = sellDetails.isAvailable();
            ArrayList<String> listt = sellDetails.getDownload_url();
            //Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), ExploreDetail_DetailAdd.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("value_send", productList);
            intent.putExtra("title", title);
            intent.putExtra("des", des);
            intent.putExtra("price", price);
            intent.putExtra("avail", avail);
            intent.putExtra("productId", productId);
            intent.putExtra("sellerId", sellerId);
            intent.putExtra("high_bid", sellDetails.getBid());
            intent.putExtra("lat", sellDetails.getLat());
            intent.putExtra("longt", sellDetails.getLongt());
            intent.putStringArrayListExtra("list", listt);
            startActivity(intent);
        } else {
            String sellerId = seller_list.get(position);
            Intent i = new Intent(getActivity(), SellerProfileDetail.class);
            Bundle bundle = new Bundle();
            i.putExtra("seller_id", sellerId);
            startActivity(i);
        }
    }
}
