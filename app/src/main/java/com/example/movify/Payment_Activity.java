package com.example.movify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.HashMap;

public class Payment_Activity extends AppCompatActivity {

    FloatingActionButton btn_pickVideo;
    VideoView videoView;
    EditText etTitle;
    Button btn_Upload;

    private static final int VIDEO_PICK_GALLERY_CODE = 100;
    private static final int VIDEO_PICK_Camera_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private String[] camera_permissions;
    private Uri videoURI = null;
    private ProgressDialog progressDialog;
    private String Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn_pickVideo = findViewById(R.id.btn_pickVideo);
        btn_Upload = findViewById(R.id.btn_Upload);
        videoView = findViewById(R.id.videoView);
        etTitle = findViewById(R.id.etTitle);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Uploading video...");
        progressDialog.setCanceledOnTouchOutside(false);



        camera_permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};


        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Title = etTitle.getText().toString().trim();
                if (TextUtils.isEmpty(Title)){
                    Toast.makeText(Payment_Activity.this, "Title is required", Toast.LENGTH_SHORT).show();
                }else if (videoURI == null){
                    Toast.makeText(Payment_Activity.this, "Pick a video", Toast.LENGTH_SHORT).show();
                }else {
                    uploadVideoFirebase();
                }
            }
        });

        btn_pickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoPickDialog();
            }
        });


    }

    private void uploadVideoFirebase() {

        progressDialog.show();

        String timestamp ="" + System.currentTimeMillis();
        String FilepathAndName = "Videos/" + "video_" + timestamp;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(FilepathAndName);
        storageReference.putFile(videoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isSuccessful());
                Uri downloadURI = uriTask.getResult();
                if (uriTask.isSuccessful()){

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("title", "" + Title);
                    hashMap.put("videoURI", "" + downloadURI);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Videos");
                    reference.child(timestamp).setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Payment_Activity.this,"Request Sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Payment_Activity.this,"" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Payment_Activity.this,"" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void VideoPickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Video From").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            if (which == 0){
                if(!checkCameraPermission()){
                    RequestCameraPermission();
                }else {
                    videoPickCamera();
                }

            }else if (which == 1){
                videoPickGallery();
                }
            }
        }).show();


    }

    private void RequestCameraPermission(){
        ActivityCompat.requestPermissions(this,camera_permissions, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean result2 = ContextCompat.checkSelfPermission(this,Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED;

        return result1 && result2;
    }

    private void videoPickGallery(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select videos"),VIDEO_PICK_GALLERY_CODE);

    }

    private void videoPickCamera(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_PICK_Camera_CODE);
    }

    private void setVideoToVideoView(){
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videoURI);
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.pause();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted){
                        videoPickCamera();
                    }else {
                        Toast.makeText(this,"permissions required",Toast.LENGTH_SHORT).show();
                    }
                }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
       if (resultCode == RESULT_OK){
           if (requestCode == VIDEO_PICK_GALLERY_CODE){
               videoURI = data.getData();
               setVideoToVideoView();
           }else if (requestCode == VIDEO_PICK_Camera_CODE){
               videoURI = data.getData();
               setVideoToVideoView();
           }
       }
        super.onActivityResult(requestCode, resultCode, data);
    }
}