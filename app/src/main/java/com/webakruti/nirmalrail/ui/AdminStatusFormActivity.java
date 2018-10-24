package com.webakruti.nirmalrail.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;
import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.model.AdmintGetComplaintResponse;
import com.webakruti.nirmalrail.model.OTPResponse;
import com.webakruti.nirmalrail.model.SaveComplaintResponse;
import com.webakruti.nirmalrail.model.SendRequestFormResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.utils.NetworkUtil;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;
import com.webakruti.nirmalrail.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AdminStatusFormActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewName;
    private TextView textViewMobileNo;
    private TextView textViewComplaintStations;
    private TextView textViewComplaintPlatform;
    private TextView textViewComplaintStatus;
    private Spinner spinnerChangeStatus;
    private EditText editTextComment;
    private ImageView imageViewCamera;
    private ImageView imageViewGallery;
    private ImageView imageViewPhoto;
    private Button buttonSubmit;
    private ProgressDialog progressDialogForAPI;
    public static final int REQ_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private LinearLayout linearLayoutCamera;
    private File baseImage;
    private File photoFile;
    private String path;
    Uri outPutfileUri;
    private LinearLayout linearLayoutSpinner;
    private LinearLayout linearLayoutCompleteInfo;
    private ImageView imageViewBefore;
    private ImageView imageViewAfter;
    private LinearLayout linearLayoutPhotos;
    private String statusInfo;
    private AdmintGetComplaintResponse.Complaint complaint;
    private String selectedChangedStatus = "Select";
    private ImageView imageViewBack;
    private TextView textViewBefore;
    private TextView textViewAfter;

    private LinearLayout linearLayoutStationPlatform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_status_form);
        initViews();
        int id = getIntent().getIntExtra("id", -1);
        statusInfo = getIntent().getStringExtra("STATUS_INFO");

        if (statusInfo.equalsIgnoreCase(AdminHomeActivity.COMPLETED)) {
            linearLayoutSpinner.setVisibility(View.GONE);
            textViewAfter.setVisibility(View.VISIBLE);
            imageViewAfter.setVisibility(View.VISIBLE);
            textViewBefore.setVisibility(View.VISIBLE);
            imageViewBefore.setVisibility(View.VISIBLE);
            linearLayoutPhotos.setVisibility(View.GONE);
            editTextComment.setEnabled(false);
            buttonSubmit.setVisibility(View.GONE);
        } else {
            linearLayoutSpinner.setVisibility(View.VISIBLE);
            textViewAfter.setVisibility(View.GONE);
            imageViewAfter.setVisibility(View.GONE);
            textViewBefore.setVisibility(View.VISIBLE);
            imageViewBefore.setVisibility(View.VISIBLE);
            linearLayoutPhotos.setVisibility(View.VISIBLE);
            editTextComment.setEnabled(true);
            buttonSubmit.setVisibility(View.VISIBLE);
        }


        // set Spinner of Change Status, values

        setSpinnerData();

        if (NetworkUtil.hasConnectivity(AdminStatusFormActivity.this)) {
            callGetComplaintDetailsAPI(id);
        } else {
            Toast.makeText(AdminStatusFormActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
        }
    }


    private List<String> getListOfStatus() {

        List<String> list = new ArrayList<>();

        switch (statusInfo) {
            case AdminHomeActivity.NEW:
                list.add("Select");
                list.add(AdminHomeActivity.IN_PROGRESS);
                list.add(AdminHomeActivity.COMPLETED);
                list.add(AdminHomeActivity.INVALID);
                break;

            case AdminHomeActivity.IN_PROGRESS:
                list.add("Select");
                list.add(AdminHomeActivity.NEW);
                list.add(AdminHomeActivity.COMPLETED);
                list.add(AdminHomeActivity.INVALID);
                break;

            case AdminHomeActivity.INVALID:
                list.add("Select");
                list.add(AdminHomeActivity.NEW);
                list.add(AdminHomeActivity.IN_PROGRESS);
                list.add(AdminHomeActivity.COMPLETED);
                break;

        }

        return list;
    }


    public void setSpinnerData() {
        ArrayAdapter<String> adapterStation = new ArrayAdapter<String>(AdminStatusFormActivity.this, android.R.layout.simple_spinner_dropdown_item, getListOfStatus());
        spinnerChangeStatus.setAdapter(adapterStation);

        spinnerChangeStatus.setSelection(0, true);
        View v = spinnerChangeStatus.getSelectedView();
        setTextCustom(v);


        spinnerChangeStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedChangedStatus = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void setTextCustom(View view) {
        TextView customTextView = ((TextView) view);
        if (customTextView != null) {
            //customTextView.setTextColor(getResources().getColor(R.color.white));
            customTextView.setTextColor(getResources().getColor(R.color.dark_blue));
        }
    }

    private void initViews() {

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewMobileNo = (TextView) findViewById(R.id.textViewMobileNo);
        textViewComplaintStations = (TextView) findViewById(R.id.textViewComplaintStations);
        textViewComplaintPlatform = (TextView) findViewById(R.id.textViewComplaintPlatform);
        textViewBefore = (TextView) findViewById(R.id.textViewBefore);
        textViewAfter = (TextView) findViewById(R.id.textViewAfter);
        textViewComplaintStatus = (TextView) findViewById(R.id.textViewComplaintStatus);
        spinnerChangeStatus = (Spinner) findViewById(R.id.spinnerChangeStatus);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
        imageViewCamera = (ImageView) findViewById(R.id.imageViewCamera);
        imageViewGallery = (ImageView) findViewById(R.id.imageViewGallery);
        imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        linearLayoutSpinner = (LinearLayout) findViewById(R.id.linearLayoutSpinner);
        linearLayoutCompleteInfo = (LinearLayout) findViewById(R.id.linearLayoutCompleteInfo);
        imageViewBefore = (ImageView) findViewById(R.id.imageViewBefore);
        imageViewAfter = (ImageView) findViewById(R.id.imageViewAfter);
        linearLayoutPhotos = (LinearLayout) findViewById(R.id.linearLayoutPhotos);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        linearLayoutStationPlatform = (LinearLayout)findViewById(R.id.linearLayoutStationPlatform);

        imageViewCamera.setOnClickListener(this);
        imageViewGallery.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imageViewCamera:
                startCameraActivity();

                break;

            case R.id.imageViewGallery:
                takePicGallery();
                break;

            case R.id.buttonSubmit:
                if (NetworkUtil.hasConnectivity(AdminStatusFormActivity.this)) {

                    if (!selectedChangedStatus.equalsIgnoreCase("Select")) {
                        if (editTextComment.getText().toString().length() > 0) {
                            callUploadRequestAPI();
                        } else {
                            Toast.makeText(AdminStatusFormActivity.this, "Please write comments", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AdminStatusFormActivity.this, "Please change status", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AdminStatusFormActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.imageViewBack:

                finish();
                break;

        }


    }

    // Camera Permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startCameraActivity() {

        if (!checkPermission()) {

            if (shouldShowRequestPermissionRationale(CAMERA) && shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)
                    && shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                new TedPermission(AdminStatusFormActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setRationaleConfirmText("ALLOW")
                        .setRationaleMessage("App Requires Permission")
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                        .check();
            } else {
                new TedPermission(AdminStatusFormActivity.this)
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

    // Camera Functions

    private void startCamera() {
        pickImageCamera();
    }

    public void pickImageCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String fileName = "image_" + timeStamp;
        //create parameters for Intent with filename
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
        //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
        outPutfileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //create new Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); // response will come in onActivityForResult
        path = getRealPathFromURI(outPutfileUri);

    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, // Which columns to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth,
                                                     int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);
        try {
            return checkRotation(pathName, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;

        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private static Bitmap checkRotation(String photoPath, Bitmap bitmap) throws IOException {
        ExifInterface ei = new ExifInterface(photoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Log.e("orientation", "" + orientation);

        Bitmap rotatedbitmap;

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedbitmap = rotateImage(bitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedbitmap = rotateImage(bitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedbitmap = rotateImage(bitmap, 270);
                break;
            case ExifInterface.ORIENTATION_NORMAL:
                rotatedbitmap = bitmap;
                break;
            default:
                rotatedbitmap = bitmap;
                break;
        }
        return rotatedbitmap;
    }

    @Nullable
    public static Bitmap rotateImage(Bitmap source, float angle) {
        try {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                    true);
        } catch (OutOfMemoryError e) {

        }
        return null;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(AdminStatusFormActivity.this, CAMERA);
        int result1 = ContextCompat.checkSelfPermission(AdminStatusFormActivity.this, WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(AdminStatusFormActivity.this, READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED;
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


    private void takePicGallery() {

        if (!checkGalleryPermission()) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                new TedPermission(getApplicationContext())
                        .setPermissionListener(permissionlistenerGallery)
                        .setRationaleConfirmText("ALLOW")
                        .setRationaleMessage("B2C Requires Permission")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                        .check();
            } else {
                new TedPermission(getApplicationContext())
                        .setPermissionListener(permissionlistenerGallery)
                        .setDeniedCloseButtonText("Cancel")
                        .setDeniedMessage("If you reject permission,you can not use this service \n Please turn on permissions from Settings")
                        .setGotoSettingButtonText("Settings")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                        .check();
            }
        } else {
            path = null;
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            startActivityForResult(i, REQ_CODE_PICK_IMAGE);
        }

    }

    private boolean checkGalleryPermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED;
    }

    PermissionListener permissionlistenerGallery = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            path = null;
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            startActivityForResult(i, REQ_CODE_PICK_IMAGE);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }


    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    path = Utils.getRealPathFromURI(selectedImage, AdminStatusFormActivity.this);

                    imageViewPhoto.setVisibility(View.VISIBLE);
                    Bitmap bitmap = decodeSampledBitmapFromFile(path, Utils.DpToPixel(AdminStatusFormActivity.this, 270), Utils.DpToPixel(AdminStatusFormActivity.this, 150));

                    imageViewPhoto.setImageBitmap(bitmap);

                    if (path == null) {
                        Toast.makeText(AdminStatusFormActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    try {

                        if (path != null) {
                            imageViewPhoto.setVisibility(View.VISIBLE);
                            Bitmap bitmap = decodeSampledBitmapFromFile(path, Utils.DpToPixel(AdminStatusFormActivity.this, 270), Utils.DpToPixel(AdminStatusFormActivity.this, 150));

                            imageViewPhoto.setImageBitmap(bitmap);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        }
    }

    private void callGetComplaintDetailsAPI(int id) {


        progressDialogForAPI = new ProgressDialog(this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();


        SharedPreferenceManager.setApplicationContext(AdminStatusFormActivity.this);
        String token = SharedPreferenceManager.getAdminObjectFromSharedPreference().getSuccess().getToken();

        String headers = "Bearer " + token;

        Call<AdmintGetComplaintResponse> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).getAdminComplaintById(headers, id + "");
        requestCallback.enqueue(new Callback<AdmintGetComplaintResponse>() {
            @Override
            public void onResponse(Call<AdmintGetComplaintResponse> call, Response<AdmintGetComplaintResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    AdmintGetComplaintResponse result = response.body();
                    if (result.getSuccess() != null) {
                        if (result.getSuccess().getStatus()) {
                            complaint = result.getSuccess().getComplaint();
                            textViewName.setText(complaint.getName());
                            textViewMobileNo.setText(complaint.getMobile());

                           if(result.getSuccess().getComplaint().getColonyname() == null) {

                               linearLayoutStationPlatform.setVisibility(View.VISIBLE);
                                /*textViewComplaintStations.setVisibility(View.VISIBLE);
                                textViewComplaintPlatform.setVisibility(View.VISIBLE);*/
                               textViewComplaintStations.setText(complaint.getStationname());
                               textViewComplaintPlatform.setText(complaint.getAtPlatform());
                               textViewComplaintStatus.setText(complaint.getStatus());

                            }
                            else {
                               linearLayoutStationPlatform.setVisibility(View.GONE);
                                /*textViewComplaintStations.setVisibility(View.GONE);
                                textViewComplaintPlatform.setVisibility(View.GONE);*/
                            }

                            if (complaint.getStatus().equalsIgnoreCase("invalid")) {
                                textViewComplaintStatus.setBackgroundColor(getResources().getColor(R.color.red));
                                textViewComplaintStatus.setText(complaint.getStatus());
                            } else if (complaint.getStatus().equalsIgnoreCase("inprocess")) {
                                textViewComplaintStatus.setBackgroundColor(getResources().getColor(R.color.yellow));
                                textViewComplaintStatus.setText(complaint.getStatus());
                            } else if (complaint.getStatus().equalsIgnoreCase("complete")) {
                                textViewComplaintStatus.setBackgroundColor(getResources().getColor(R.color.green));
                                textViewComplaintStatus.setText(complaint.getStatus());
                            } else {
                                textViewComplaintStatus.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                                textViewComplaintStatus.setText(complaint.getStatus());
                            }

                            Picasso.with(AdminStatusFormActivity.this).load(complaint.getBeforeImgUrl()).placeholder(R.drawable.image_back).resize(300, 300).into(imageViewBefore);


                            if (statusInfo.equalsIgnoreCase(AdminHomeActivity.COMPLETED)) {

                                Picasso.with(AdminStatusFormActivity.this).load(complaint.getBeforeImgUrl()).placeholder(R.drawable.image_back).resize(300, 300).into(imageViewBefore);
                                Picasso.with(AdminStatusFormActivity.this).load(complaint.getAfterImgUrl()).placeholder(R.drawable.image_back).resize(300, 300).into(imageViewAfter);
                                if (complaint.getComment() != null) {
                                    editTextComment.setText(complaint.getComment());
                                } else {
                                    editTextComment.setText("N/A");

                                }
                            }

                        } else {
                            Toast.makeText(AdminStatusFormActivity.this, "OTP Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AdminStatusFormActivity.this, "Mobile number is not registered. Please register first.", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    // Response code is 401
                    Toast.makeText(AdminStatusFormActivity.this, "OTP Error", Toast.LENGTH_SHORT).show();
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }


            @Override
            public void onFailure(Call<AdmintGetComplaintResponse> call, Throwable t) {

                if (t != null) {

                    if (progressDialogForAPI != null) {
                        progressDialogForAPI.cancel();
                    }
                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());
                }

            }
        });
    }


    private void callUploadRequestAPI() {


        File baseImage = null;
        if (path != null) {
            baseImage = new File(path);

            int compressionRatio = 2; //1 == originalImage, 2 = 50% compression, 4=25% compress
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(baseImage.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(baseImage));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
        } else {
            Toast.makeText(AdminStatusFormActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialogForAPI = new ProgressDialog(AdminStatusFormActivity.this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();


        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), editTextComment.getText().toString());
        RequestBody status = RequestBody.create(MediaType.parse("multipart/form-data"), selectedChangedStatus);
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), complaint.getId() + "");


        RequestBody requestBaseFile = RequestBody.create(MediaType.parse("multipart/form-data"), baseImage);
        MultipartBody.Part bodyImage = MultipartBody.Part.createFormData("complete_img", "image" + System.currentTimeMillis(), requestBaseFile);


        String header = "Bearer " + SharedPreferenceManager.getAdminObjectFromSharedPreference().getSuccess().getToken();

        Call<SaveComplaintResponse> colorsCall = RestClient.getApiService(ApiConstants.BASE_URL).uploadAdminComplaintUpdaate(header, id, status, description, bodyImage);

        colorsCall.enqueue(new Callback<SaveComplaintResponse>() {
            @Override
            public void onResponse(Call<SaveComplaintResponse> call, Response<SaveComplaintResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    //
                    try {
                        SaveComplaintResponse saveComplaintResponse = response.body();

                        if (saveComplaintResponse.getSuccess() != null) {
                            if (saveComplaintResponse.getSuccess().getStatus()) {
                                Toast.makeText(getApplicationContext(), saveComplaintResponse.getSuccess().getMsg(), Toast.LENGTH_SHORT).show();
                                Log.e("Upload", "Upload Successful");
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to reach server ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Unable to reach server ", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Unable to reach server ", Toast.LENGTH_SHORT).show();
                }


                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }

            }


            @Override
            public void onFailure(Call<SaveComplaintResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Time out error. ", Toast.LENGTH_SHORT).show();
                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
                if (t != null) {

                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());

                }
            }
        });

    }

}
