package com.sellanddonate.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sellanddonate.app.R;
import com.sellanddonate.app.firebase.BidDetail;
import com.sellanddonate.app.firebase.MyAdds;
import com.sellanddonate.app.util.ImageLoadingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.MyViewHolder> {

    private List<BidDetail> ExploreDetailList;
    private List<String> categoryList;
    double lat, longt;
    Context context;
    String str_price, str_pay_status;

    Button b_direction, b_pay;
    ;
    DatabaseReference mDatabase;

    public BidAdapter(List<MyAdds> exploreDetailList, List<String> catagory) {
        ExploreDetailList = new ArrayList<>();
        categoryList = catagory;


    }

    public BidAdapter(Context context, List<BidDetail> exploreDetailList) {
        ExploreDetailList = new ArrayList<>();
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_bid, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return ExploreDetailList.size();
    }

    public void update(ArrayList<BidDetail> productList) {

        ExploreDetailList.addAll(productList);

        notifyDataSetChanged();
    }

    public interface OnClick_RecyclerVieww {
        void onAdClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, numberOfPics;
        TextView price, catagory;

        ;

        // OnClick_RecyclerVieww onClick_ViewHolder;

        MyViewHolder(View itemView) {
            super(itemView);
            // this.onClick_ViewHolder=onClick_recyclerView;
            //  imageView = itemView.findViewById(R.id.ivImage);
            title = itemView.findViewById(R.id.tv_bid_title);
            // catagory = itemView.findViewById(R.id.txt_category);
            numberOfPics = itemView.findViewById(R.id.tv_bid_price);
            price = itemView.findViewById(R.id.tvbid_status);
            b_direction = itemView.findViewById(R.id.b_bid_direction);
            b_pay = itemView.findViewById(R.id.b_bid_pay);

            // itemView.setOnClickListener(this);
        }

        void bindView() {

            str_price = ExploreDetailList.get(getAdapterPosition()).getbidAmount();
            str_pay_status = ExploreDetailList.get(getAdapterPosition()).getPayment();
            title.setText("Bid for " + ExploreDetailList.get(getAdapterPosition()).getTitle());
            numberOfPics.setText("Bidding Price: " + str_price);


            // catagory.setText("Posted Add In: "+categoryList.get(getAdapterPosition()));
            String statuss = ExploreDetailList.get(getAdapterPosition()).getStatus();
            if (statuss.equals("pending")) {
                b_direction.setVisibility(View.INVISIBLE);
                b_pay.setVisibility(View.INVISIBLE);
                price.setText("Status: pending");

                price.setTextColor(Color.parseColor("#cccccc"));

            } else if (statuss.equals("reject")) {
                price.setText("Status: rejected");
                price.setTextColor(Color.parseColor("#EE0519"));
                b_direction.setVisibility(View.INVISIBLE);
                b_pay.setVisibility(View.INVISIBLE);
            } else {
                price.setText("Status: Accepted");
                price.setTextColor(Color.parseColor("#128F17"));
                b_direction.setVisibility(View.VISIBLE);
                b_pay.setVisibility(View.VISIBLE);

                b_direction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lat = ExploreDetailList.get(getAdapterPosition()).getLat();
                        longt = ExploreDetailList.get(getAdapterPosition()).getLongt();
                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", lat, longt, "destination");
                        Uri gmmIntentUri = Uri.parse(uri);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        context.startActivity(mapIntent);
                    }
                });
                if (str_pay_status.equals("paid")) {
                    b_pay.setText("Paid ");
                    // Toast.makeText(context, "Payment Paid ", Toast.LENGTH_SHORT).show();
                } else {
                    b_pay.setText("Pay Online");
                    b_pay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String productId = ExploreDetailList.get(getAdapterPosition()).getProductId();
                            Button btnTextChange = (Button) v;
                            dialog(productId, btnTextChange);
                        }
                    });
                }

            }


        }


    }

    public void dialog(final String productId, final Button b_payy) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_pay);
        // set the custom dialog components - text, image and button
        final EditText text_name = (EditText) dialog.findViewById(R.id.edt_card_holder);
        final EditText text_card_no = (EditText) dialog.findViewById(R.id.edt_card_number);
        final EditText text_cvc = (EditText) dialog.findViewById(R.id.edt_cvc);
        EditText text_expiry = (EditText) dialog.findViewById(R.id.edt_expirate_date);
        //text.setText("Android custom dialog example!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        //image.setImageResource(R.drawable.ic_launcher);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_pay_now);
        // if button is clicked, close the custom dialog
        dialogButton.setText("Pay " + str_price);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String str_card_num = text_card_no.getText().toString().trim();
                String str_cvc = text_cvc.getText().toString().trim();

                if (str_card_num.length() != 12) {
                    text_card_no.setError("Card Number Incorret");
                    text_card_no.requestFocus();
                } else if (str_cvc.length() != 3) {
                    text_cvc.setError("CVC Incorrect");
                    text_cvc.requestFocus();
                } else {

                    mDatabase.child("bidder").child(FirebaseAuth.getInstance().getUid())
                            .child(productId).child("payment").setValue("paid")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Payment Successful", Toast.LENGTH_SHORT).show();
                                    b_payy.setText("Paid");
                                    dialog.dismiss();
                                }
                            });


                }
            }
        });

        dialog.show();
    }


}
