package com.sellanddonate.app.ui.activites;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sellanddonate.app.R;
import com.sellanddonate.app.adapter.photosAdapter;
import com.sellanddonate.app.base.BaseFragment;
import com.sellanddonate.app.constant.Constants;
import com.sellanddonate.app.fragments.SellDetailFragment;
import com.sellanddonate.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sellanddonate.app.constant.Constants.Extras.EXTRA_PHOTOS_LIST;

public class UploadAdsClass {

    List<String> photoList = new ArrayList<>();
    List<Uri> photolistUri = new ArrayList<>();

    private RecyclerView recyclerViewPhotos;
    SharedPreferences pref;
    private photosAdapter photosAdapter;
    Context context;

    double lat,longt;
    String title, description, price;
    boolean available;


    public UploadAdsClass() {

    }

    public UploadAdsClass(Context ct) {
        this.context = ct;

    }

    public UploadAdsClass(FragmentActivity activity, List photoList, List<Uri> mArrayUri, RecyclerView rv) {
        this.context = activity;
        this.photoList = photoList;
        this.photolistUri = mArrayUri;
        this.recyclerViewPhotos = rv;

    }


    public UploadAdsClass(FragmentActivity activity, List photoList, List<Uri> mArrayUri) {
        this.context = activity;
        this.photoList = photoList;
        this.photolistUri = mArrayUri;


    }

    public UploadAdsClass(FragmentActivity activity, List photoList, List<Uri> mArrayUri, String title, String price, String des,double lat,double longt) {
        this.context = activity;
        this.photoList = photoList;
        this.photolistUri = mArrayUri;
        this.title = title;
        this.price = price;
        this.description = des;
        this.lat=lat;
        this.longt=longt;

    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hide_photos);

    // photoList = getIntent().getStringArrayListExtra(EXTRA_PHOTOS_LIST);
    // mediaType = getIntent().getStringExtra(Constants.Extras.MEDIA_TYPE);
    // photolistUri = getIntent().getParcelableArrayListExtra(Constants.Extras.photosUri);


    //  initalize();


    public void initalize() {


        photosAdapter = new photosAdapter(photoList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerViewPhotos.setLayoutManager(gridLayoutManager);
        recyclerViewPhotos.setAdapter(photosAdapter);

    }

    int i = 0;
    public List arrayList_upload_db = new ArrayList<String>();


    public void uploadImage() {

        pref = context.getSharedPreferences("select", Context.MODE_PRIVATE);
        final String child_str = pref.getString("selected", "House/");

        // ToastUtil.showToast(""+child_str);
        Log.e("image", photoList.toString());
        // progressBar.setVisibility(View.VISIBLE);
        if (photolistUri != null) {
            for (i = 0; i < photoList.size(); i++) {
                ToastUtil.showInfoLog("image1", photolistUri.get(i).toString());
                SellDetailFragment.progressBar.setVisibility(View.VISIBLE);
                String fileName = getFileName(photolistUri.get(i));
                final StorageReference fileuploadto = FirebaseStorage.getInstance().getReference().child(child_str).child(fileName);


                fileuploadto.putFile(photolistUri.get(i))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //  String imageStorageLoc = taskSnapshot.getStorage().getDownloadUrl().toString();
                                // ToastUtil.showInfoLog("image2", "location: " + i);
                                // progressBar.setVisibility(View.INVISIBLE);
//                                DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference().child("category/");
//                                dbRef.child("mix/").child("imageLink"+i).setValue(imageStorageLoc);
//                                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
//
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                      //  String url=String.valueOf(uri);
//                                      //  ArrayList<String> a=new ArrayList<String>();
//                                      ///  a.add(url);
//                                      //  DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference().child("category/");
//                                      //  dbRef.child("mix/").child("imageLink"+i).setValue(url);
//
//
//                                    }
//                               });


                                fileuploadto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = String.valueOf(uri);

                                        arrayList_upload_db.add(url);
                                        //  if(arrayList_upload_db.size()==i-1){
                                        storeLinkToFirebase(arrayList_upload_db, child_str);
                                        //}

                                        Log.e("data a", "size " + arrayList_upload_db.size());
                                        // storeLinkToFirebase(url, child_str);
                                        // Toast.makeText(context, "success" + arrayList_upload_db.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                ToastUtil.showInfoLog("image", "Images failed to upload");
                                SellDetailFragment.progressBar.setVisibility(View.GONE);

                            }
                        })

                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());


                                ToastUtil.showInfoLog("image", "Progress " + progress);
                            }
                        });
            }
        }
    }


    public void storeLinkToFirebase(List url, String child_str) {
       String authId= FirebaseAuth.getInstance().getCurrentUser().getUid();
       String catagoryUpload=child_str.toLowerCase().split("/")[0];
       ToastUtil.showToast(catagoryUpload);
        DatabaseReference referenceLcl = FirebaseDatabase.getInstance().getReference();
        String key = referenceLcl.push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title", title);
        hashMap.put("price", price);
        hashMap.put("description", description);
        hashMap.put("download_url", arrayList_upload_db);
        hashMap.put("available", true);
        hashMap.put("productId",key);
        hashMap.put("authId",authId);
        hashMap.put("bid",0);
        hashMap.put("lat",lat);
        hashMap.put("longt",longt);
        hashMap.put("catagory",catagoryUpload.toLowerCase());

        Task task = referenceLcl.child("category").child(child_str.toLowerCase()).child(authId).setValue(hashMap);
        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object aVoid) {
                //the data is added and now we are sure to do something related
                ToastUtil.showToast("On success Called");
                SellDetailFragment.progressBar.setVisibility(View.GONE);
            }
        });

    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
