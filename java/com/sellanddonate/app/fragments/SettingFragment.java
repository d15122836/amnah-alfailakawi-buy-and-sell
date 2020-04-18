package com.sellanddonate.app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseFragment;
import com.sellanddonate.app.constant.Constants;
import com.sellanddonate.app.ui.activites.LoginActivity;
import com.sellanddonate.app.util.ToastUtil;

public class SettingFragment extends BaseFragment  implements View.OnClickListener {

    TextView titleTV,titleTV_email;
    RelativeLayout
            profileSettingLy_id,
            helpCenterLy_id,UserReport;
    AppCompatButton
            signOutBtn_id;
    String name;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_setting;

    }

    @Override
    protected void init(View view) {


        titleTV = (TextView) findViewById(R.id.titleTV_id);
        titleTV_email = (TextView) findViewById(R.id.titleTV_email);

        profileSettingLy_id = (RelativeLayout) findViewById(R.id.profileSettingLy_id);
        helpCenterLy_id = (RelativeLayout) findViewById(R.id.helpCenterLy_id);
        UserReport = (RelativeLayout) findViewById(R.id.user_report);

        signOutBtn_id = (AppCompatButton) findViewById(R.id.signOutBtn_id);
        signOutBtn_id.setOnClickListener(this);
        profileSettingLy_id.setOnClickListener(this);
        helpCenterLy_id.setOnClickListener(this);
        UserReport.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        displayName();


    }

    private void displayName() {
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("userName").getValue(String.class);

                titleTV.setText("User Name:"+name);
               String email = dataSnapshot.child("email").getValue(String.class);
                titleTV_email.setText("email:"+email);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                ToastUtil.showErrorLog("error","Failed to read : "+ error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.profileSettingLy_id:
               Intent i=new Intent(getActivity(),ProfileSettingFragment.class);
               getActivity().startActivity(i);
                break;

            case R.id.helpCenterLy_id:
               // startFragmentHandler("helpSetting",2);
                Intent ii=new Intent(getActivity(),HelpCenterFragment.class);
                getActivity().startActivity(ii);
                break;

            case R.id.user_report:
                // startFragmentHandler("helpSetting",2);
                Intent iii=new Intent(getActivity(),UserProfile.class);
                getActivity().startActivity(iii);
                break;

            case R.id.signOutBtn_id:

                 dialog();

                break;
        }
    }

    private void dialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to Sign Out?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        AuthUI.getInstance()
                                .signOut(requireContext())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // user is now signed out
                                        startActivity(new Intent(requireContext(), LoginActivity.class));
                                    }
                                });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void startFragmentHandler(String index,int value) {
        Intent _intent = new Intent(requireContext(), FragmentHandlerActivity.class);
        _intent.putExtra(Constants.Extras.KEY_INDEX, index);
        _intent.putExtra(Constants.Extras.KEY_VALUE, value);
        startActivity(_intent);
    }
}
