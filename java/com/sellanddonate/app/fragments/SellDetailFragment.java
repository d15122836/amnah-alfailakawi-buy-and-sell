package com.sellanddonate.app.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sellanddonate.app.R;
import com.sellanddonate.app.base.BaseFragment;
import com.sellanddonate.app.constant.Constants;
import com.sellanddonate.app.ui.activites.UploadAdsClass;
import com.sellanddonate.app.util.PathUtils;
import com.sellanddonate.app.util.Permissions;
import com.sellanddonate.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SellDetailFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private AppCompatButton
            uploadImagesBtn,
            postAdBtn, postDonationBtn;
    SharedPreferences pref;
   double lat, longt;
    private Spinner spinner;
    String currencyString = "$";
    public static ProgressBar progressBar;

    private EditText
            titleEtv,
            priceEtv,
            shortDescEtv;

    private String
            downloadUrl;

    RecyclerView recyclerViewPhotos;

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_sell_detail;
    }

    @Override
    protected void init(View view) {
        recyclerViewPhotos = (RecyclerView) findViewById(R.id.recyclerViewPhotos);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_uploading);
        uploadImagesBtn = (AppCompatButton) findViewById(R.id.uploadImages_id);
        postAdBtn = (AppCompatButton) findViewById(R.id.postAdBtn_id);
        postDonationBtn = (AppCompatButton) findViewById(R.id.postDonateBtn_id);
        spinner = (Spinner) findViewById(R.id.spinnerCuren);

        titleEtv = (EditText) findViewById(R.id.titleEtv);
        priceEtv = (EditText) findViewById(R.id.priceEtv);
        shortDescEtv = (EditText) findViewById(R.id.shortDescEtv);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLastLocation();
        spinner();
        if (titleEtv.toString().isEmpty()) {
            titleEtv.setError("Title Required");
            titleEtv.requestFocus();
        }
        if (priceEtv.toString().isEmpty()) {
            priceEtv.setError("price Required");
            priceEtv.requestFocus();
        }
        if (shortDescEtv.toString().isEmpty()) {
            shortDescEtv.setError("Description Required");
            shortDescEtv.requestFocus();
        }


        uploadImagesBtn.setOnClickListener(this);
        postAdBtn.setOnClickListener(this);


        try {
            downloadUrl = getActivity().getIntent().getStringExtra(Constants.Extras.DOWNLOAD_URI);
            ToastUtil.showInfoLog("check", "download uri : " + downloadUrl);
        } catch (NullPointerException e) {
            e.printStackTrace();

        }


    }

    public void spinner() {
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Dollar $");
        categories.add("Euro  €");
        categories.add("Pound ₤");
        categories.add("Dinar KD");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        // currencyString = parent.getItemAtPosition(position).toString();
        if (position == 0) {
            currencyString = "$";
        } else if (position == 1) {
            currencyString = "€";
        } else if (position == 2) {
            currencyString = "₤";
            //} else if (position == 3) {
        } else {
            currencyString = "KD";
        }
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + currencyString, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

        // Toast.makeText(getActivity(), "Selected: " + currencyString, Toast.LENGTH_LONG).show();
    }

    private void tryupload() {

        final String title = titleEtv.getText().toString().trim();
        final String price = priceEtv.getText().toString().trim();
        final String des = shortDescEtv.getText().toString().trim();

        if (title.isEmpty()) {
            titleEtv.setError("Please Fill");
            titleEtv.requestFocus();

//            FirebaseDatabase.getInstance().getReference("Users")
//                    .child("aaaadf")
//                    .setValue(dateFormatted);
        } else if (price.isEmpty()) {
            priceEtv.setError("Please Fill");
            priceEtv.requestFocus();
        } else if (des.isEmpty()) {
            shortDescEtv.setError("Please Fill");
            shortDescEtv.requestFocus();
        } else {
//            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("title", title);
//            hashMap.put("Price", price);
//            hashMap.put("description", des);
//           // hashMap.put("download_url", arrayList_upload_db);
//            DatabaseReference referenceLcl = FirebaseDatabase.getInstance().getReference();
//            Task task = referenceLcl.child("category").child(child_str.toLowerCase()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMap);
//            task.addOnSuccessListener(new OnSuccessListener() {
//                @Override
//                public void onSuccess(Object aVoid) {
//                    //the data is added and now we are sure to do something related
//                    ToastUtil.showToast("On success Called");
//                }
//            });
            progressBar.setVisibility(View.VISIBLE);
            UploadAdsClass obj = new UploadAdsClass(getActivity(), photoList, mArrayUri, title, price + " " + currencyString, des,lat,longt);
            obj.uploadImage();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.uploadImages_id:
                // TODO : create chooser

                if (Permissions.hasStoragePermissions(requireContext())) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Pictures"), Constants.Extras.REQUEST_CODE_SELECT_PHOTOS);
                } else {
                    Permissions.getStoragePermissions(requireContext());
                }
                break;

            case R.id.postAdBtn_id:
                // TODO : upload final info to firebase
                tryupload();

                break;

            case R.id.postDonateBtn_id:
                // TODO : upload final info to firebase
                tryupload();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == Constants.Extras.REQUEST_CODE_SELECT_PHOTOS && resultCode == RESULT_OK
                    && null != data) {
                imagePickingHandling(data);
                Log.e("data", data.toString());
            } else {
                ToastUtil.showToast("You haven't picked Any Media");
            }
        } catch (Exception e) {
            //ToastUtil.showToast("Something went wrong tip: check permission");
            ToastUtil.showErrorLog("error", "Error We got is : " + e.getMessage());
        }
    }

    List<Uri> mArrayUri;

    private void imagePickingHandling(@NonNull Intent data) {
        mArrayUri = new ArrayList<>();
        if (data.getData() != null) {
            Uri mImageUri = data.getData();
            mArrayUri.add(mImageUri);
        } else if (data.getClipData() != null) {
            ClipData mClipData = data.getClipData();
            for (int i = 0; i < mClipData.getItemCount(); i++) {
                ClipData.Item item = mClipData.getItemAt(i);
                Uri uri = item.getUri();
                mArrayUri.add(uri);
            }
        }
        if (mArrayUri.size() > 0) {
            //ToastUtil.showToast( "uri" + mArrayUri);
            usePhotos(mArrayUri);
            uploadImagesBtn.setVisibility(View.GONE);
        } else {
            ToastUtil.showToast("You haven't picked Image");
        }
    }

    //ArrayList<String> photoList;
    List photoList;

    private void usePhotos(List<Uri> mArrayUri) {
        // Intent intent = new Intent(requireContext(), HidePhotosActivity.class);


        // Converting uri to path
        photoList = new ArrayList<>();
        for (Uri uri : mArrayUri) {
            photoList.add(PathUtils.getPath(requireContext(), uri));
        }
        // ToastUtil.showInfoLog("data", photoList.toString());
        // intent.putStringArrayListExtra(Constants.Extras.EXTRA_PHOTOS_LIST, photoList);
        // intent.putParcelableArrayListExtra(Constants.Extras.photosUri, (ArrayList<? extends Parcelable>) mArrayUri);
        //intent.putExtra(Constants.Extras.MEDIA_TYPE, Constants.Extras.TYPE_IMAGE);
        // startActivity(intent);

        UploadAdsClass a = new UploadAdsClass(getActivity(), photoList, mArrayUri, recyclerViewPhotos);
        a.initalize();
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
//                                    latTextView.setText(location.getLatitude()+"");
//                                    lonTextView.setText(location.getLongitude()+"");
//
                                    ToastUtil.showToast(location.getLongitude() + " " + location.getLatitude());
                               lat=location.getLatitude();
                               longt=location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(getActivity(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
//            latTextView.setText(mLastLocation.getLatitude()+"");
            ToastUtil.showToast(mLastLocation.getLongitude() + " last  " + mLastLocation.getLatitude());
            lat=mLastLocation.getLatitude();
            longt=mLastLocation.getLongitude();
//            lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }
}
