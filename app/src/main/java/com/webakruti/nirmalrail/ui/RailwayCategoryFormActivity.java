package com.webakruti.nirmalrail.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.webakruti.nirmalrail.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RailwayCategoryFormActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button buttonCamera;
    private ImageView imageViewPhoto;
    private File baseImage;
    private File photoFile;
    private String path;

    private Spinner spinnerStations;
    private Spinner spinnerPlatform;
    private EditText editTextComment;
    private ImageView imageViewBack;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_category_form);

        initViews();

    }

    private void initViews() {

        spinnerStations =(Spinner)findViewById(R.id.spinnerStations);
        spinnerPlatform = (Spinner)findViewById(R.id.spinnerPlatform);
        editTextComment = (EditText)findViewById(R.id.editTextComment);

        imageViewBack = (ImageView)findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonCamera = (Button) findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(this);

        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonCamera:
                startCameraActivity();
                break;
            case R.id.buttonSubmit:
                Intent intent = new Intent(RailwayCategoryFormActivity.this, MyRequestsActivity.class);
                startActivity(intent);
                finish();


        }

    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            startCamera();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }

    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startCameraActivity() {

        if (!checkPermission()) {

            if (shouldShowRequestPermissionRationale(CAMERA) && shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)
                    && shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                new TedPermission(RailwayCategoryFormActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setRationaleConfirmText("ALLOW")
                        .setRationaleMessage("App Requires Permission")
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                        .check();
            } else {
                new TedPermission(RailwayCategoryFormActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedCloseButtonText("Cancel")
                        .setDeniedMessage("If you reject permission,you can not use this service \n Please turn on permissions from Settings")
                        .setGotoSettingButtonText("Settings")
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                        .check();
            }
        } else {
            startCamera();
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(RailwayCategoryFormActivity.this, CAMERA);
        int result1 = ContextCompat.checkSelfPermission(RailwayCategoryFormActivity.this, WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(RailwayCategoryFormActivity.this, READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED;
    }


    private void startCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.i("Error", "IOException");
        }

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                imageViewPhoto.setVisibility(View.VISIBLE);
                Bitmap mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(path));
                if (mImageBitmap != null) {
                    imageViewPhoto.setImageBitmap(mImageBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "image" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Railway App");

        String fileName = "image_" + timeStamp + ".jpg";
        Bitmap bitmap = null;

        File file = new File(storageDir, fileName);
        FileOutputStream out = new FileOutputStream(file);

        //out = openFileInput(file);
       /* bitmap = BitmapFactory.decodeStream(fileInputStream);
        fileInputStream.close();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);*/
        path = file.getPath();
        return file;
    }

}
