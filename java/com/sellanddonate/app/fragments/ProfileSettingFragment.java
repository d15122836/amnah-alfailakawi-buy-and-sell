package com.sellanddonate.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseActivity;
import com.sellanddonate.app.firebase.User;
import com.sellanddonate.app.ui.activites.LoggedInActivity;
import com.sellanddonate.app.ui.activites.LoginActivity;
import com.sellanddonate.app.ui.activites.RegistrationActivity;
import com.sellanddonate.app.util.ToastUtil;


public class ProfileSettingFragment extends BaseActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    EditText etv_username, etv_address, etv_phoneNumber;
    String name, adrress, phoneNumber,email,member_since;

    Button btn_update, btn_cancel;

    @Override
    protected int getContentId() {
        return R.layout.fragment_profile_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        etv_username = findViewById(R.id.userName_id);
        etv_address = findViewById(R.id.address_id);
        etv_phoneNumber = findViewById(R.id.mobile_id);

        btn_update = findViewById(R.id.updateAccountBtn_id);
        btn_cancel = findViewById(R.id.cancelBtn_id);


        displayName();
    }

    @Override
    protected void initActions() {
        btn_update.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    private void displayName() {
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("userName").getValue(String.class);

                etv_username.setText(name);
                adrress = dataSnapshot.child("address").getValue(String.class);
                etv_address.setText(adrress);
                email=dataSnapshot.child("email").getValue(String.class);
                phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                etv_phoneNumber.setText(phoneNumber);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                ToastUtil.showErrorLog("error", "Failed to read : " + error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateAccountBtn_id:
                editTextGetData();

                break;
            case R.id.cancelBtn_id:
                finish();
                break;
        }
    }

    public void editTextGetData(){
         name =etv_username.getText().toString().trim();
        adrress = etv_address.getText().toString().trim();
        phoneNumber= etv_phoneNumber.getText().toString().trim();
      //  final String address = addressEtv.getText().toString().trim();
        update();
    }


    public void update() {
        User userInfo = new User(
                name,
                email,
                phoneNumber,
                adrress,
                member_since
        );

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //  progressBar.setVisibility(View.INVISIBLE);
                    ToastUtil.showToast("User Successfully Update ");
                    ToastUtil.showErrorLog("error", "User registered successfully");
                    finish();
                } else {
                    ToastUtil.showToast("User Update Failed");
                    ToastUtil.showErrorLog("error", "User Reg failed : " + task.getException().getMessage());

                }
            }
        });
    }
}
