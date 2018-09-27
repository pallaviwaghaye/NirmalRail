package com.webakruti.nirmalrail.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.adapter.RailwayCategoryAdapter;
import com.webakruti.nirmalrail.model.RailwayCategoryResponse;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RailwayCategoryFormActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private LinearLayout linearLayoutCamera;
    private ImageView imageViewPhoto;
    private File baseImage;
    private File photoFile;
    private String path;

    private Spinner spinnerStations;
    private Spinner spinnerPlatform;
    String selectedStations = "Select station";
    String selectedPlatform = "Select platform";
    private EditText editTextComment;
    private ImageView imageViewBack;
    private Button buttonSubmit;
    Uri outPutfileUri;
    private ProgressDialog progressDialogForAPI;
    private RailwayCategoryResponse.Category serviceCategory;
    private ArrayList<List<SendRequestFormResponse.PlatformList>> listOfPlatFormsFinal;
    private RadioGroup radioGroup;
    private RadioButton radioFootOverBridge;
    private RadioButton radioDustbin;
    private String place = "Foot";
    private ImageView imageViewPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_category_form);
        SharedPreferenceManager.setApplicationContext(RailwayCategoryFormActivity.this);

        serviceCategory = (RailwayCategoryResponse.Category) getIntent().getSerializableExtra("ServiceCategory");

        initViews();

        if (NetworkUtil.hasConnectivity(RailwayCategoryFormActivity.this)) {
            callGetPlatFormStationAPI();
        } else {
            Toast.makeText(RailwayCategoryFormActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
        }

        handleUIVisibileNotVisible();

    }

    private void handleUIVisibileNotVisible() {

        switch (serviceCategory.getId()) {
            case 1:

                spinnerPlatform.setVisibility(View.VISIBLE);
                imageViewPF.setVisibility(View.VISIBLE);
                spinnerStations.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.GONE);
                break;
            case 2:
                spinnerPlatform.setVisibility(View.VISIBLE);
                spinnerStations.setVisibility(View.VISIBLE);
                imageViewPF.setVisibility(View.VISIBLE);

                radioGroup.setVisibility(View.GONE);

                break;
            case 3:
                spinnerPlatform.setVisibility(View.VISIBLE);
                spinnerStations.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.GONE);
                imageViewPF.setVisibility(View.VISIBLE);


                break;
            case 4:
                spinnerPlatform.setVisibility(View.VISIBLE);
                spinnerStations.setVisibility(View.VISIBLE);
                imageViewPF.setVisibility(View.VISIBLE);

                radioGroup.setVisibility(View.GONE);

                break;
            case 5:
                spinnerPlatform.setVisibility(View.VISIBLE);
                spinnerStations.setVisibility(View.VISIBLE);
                imageViewPF.setVisibility(View.VISIBLE);

                radioGroup.setVisibility(View.GONE);

                break;
            case 6:
                spinnerPlatform.setVisibility(View.VISIBLE);
                spinnerStations.setVisibility(View.VISIBLE);
                imageViewPF.setVisibility(View.VISIBLE);

                radioGroup.setVisibility(View.GONE);

                break;
            case 7:
                spinnerPlatform.setVisibility(View.GONE);
                spinnerStations.setVisibility(View.VISIBLE);
                imageViewPF.setVisibility(View.GONE);

                radioGroup.setVisibility(View.GONE);

                break;
            case 8:
                spinnerPlatform.setVisibility(View.VISIBLE);
                spinnerStations.setVisibility(View.VISIBLE);
                imageViewPF.setVisibility(View.VISIBLE);

                radioGroup.setVisibility(View.GONE);

                break;
            case 9:
                spinnerPlatform.setVisibility(View.VISIBLE);
                spinnerStations.setVisibility(View.VISIBLE);
                imageViewPF.setVisibility(View.VISIBLE);

                radioGroup.setVisibility(View.GONE);

                break;
            case 10:
                spinnerPlatform.setVisibility(View.VISIBLE);
                spinnerStations.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.GONE);
                imageViewPF.setVisibility(View.VISIBLE);


                break;
            case 11:

                spinnerStations.setVisibility(View.VISIBLE);
                imageViewPF.setVisibility(View.GONE);

                spinnerPlatform.setVisibility(View.VISIBLE);

                break;


        }
    }


    private void initViews() {

        spinnerStations = (Spinner) findViewById(R.id.spinnerStations);
        spinnerPlatform = (Spinner) findViewById(R.id.spinnerPlatform);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
        imageViewPF = (ImageView) findViewById(R.id.imageViewPF);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioFootOverBridge = (RadioButton) findViewById(R.id.radioFootOverBridge);
        radioDustbin = (RadioButton) findViewById(R.id.radioDustbin);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        linearLayoutCamera = (LinearLayout) findViewById(R.id.linearLayoutCamera);
        linearLayoutCamera.setOnClickListener(this);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setOnClickListener(this);


       /* String[] platformList = getResources().getStringArray(R.array.platforms);
        ArrayAdapter<String> adapterPlatform = new ArrayAdapter<String>(RailwayCategoryFormActivity.this, android.R.layout.simple_spinner_dropdown_item, platformList);
        spinnerPlatform.setAdapter(adapterPlatform);

        spinnerPlatform.setSelection(0, true);
        View v1 = spinnerPlatform.getSelectedView();
        setTextCustom(v1);*/


    }


    private void callGetPlatFormStationAPI() {


        final ProgressDialog progressDialog = new ProgressDialog(RailwayCategoryFormActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        SharedPreferenceManager.setApplicationContext(RailwayCategoryFormActivity.this);
        String token = SharedPreferenceManager.getUserObjectFromSharedPreference().getSuccess().getToken();

        String headers = "Bearer " + token;
        Call<SendRequestFormResponse> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).getStationPlatform(headers, serviceCategory.getId() + "");
        requestCallback.enqueue(new Callback<SendRequestFormResponse>() {
            @Override
            public void onResponse(Call<SendRequestFormResponse> call, Response<SendRequestFormResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    SendRequestFormResponse sendRequestFormResponse = response.body();

                    if (sendRequestFormResponse != null) {
                        handleStationPlatformData(sendRequestFormResponse);
                    }

                } else {
                    // Response code is 401
                }

                if (progressDialog != null) {
                    progressDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<SendRequestFormResponse> call, Throwable t) {

                if (t != null) {

                    if (progressDialog != null) {
                        progressDialog.cancel();
                    }
                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());
                }

            }
        });

    }

    private void handleStationPlatformData(SendRequestFormResponse sendRequestFormResponse) {
        List<SendRequestFormResponse.Station> stationList = sendRequestFormResponse.getSuccess().getStation();
        List<SendRequestFormResponse.Place> placeList = sendRequestFormResponse.getSuccess().getPlaces();
        Map<String, List<SendRequestFormResponse.PlatformList>> mapList = sendRequestFormResponse.getSuccess().getPlatformMap();

        if (placeList != null && placeList.size() > 0) {
            radioGroup.setVisibility(View.VISIBLE);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int selectedType) {
                    // find which radio button is selected
                    if (selectedType == R.id.radioFootOverBridge) {
                        spinnerPlatform.setVisibility(View.VISIBLE);

                        radioFootOverBridge.setButtonDrawable(R.drawable.form_checkbox_enabled);
                        radioDustbin.setButtonDrawable(R.drawable.form_checkbox_radio_disabled);
                    } else if (selectedType == R.id.radioDustbin) {
                        radioDustbin.setButtonDrawable(R.drawable.form_checkbox_enabled);
                        radioFootOverBridge.setButtonDrawable(R.drawable.form_checkbox_radio_disabled);
                        place = "Dust";
                        spinnerPlatform.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            radioGroup.setVisibility(View.GONE);
        }


        //
        listOfPlatFormsFinal = new ArrayList<>();

        if (mapList != null && mapList.size() > 0) {
            for (Map.Entry<String, List<SendRequestFormResponse.PlatformList>> entry : mapList.entrySet()) {
                List<SendRequestFormResponse.PlatformList> lisOfPlatForms = entry.getValue();
                listOfPlatFormsFinal.add(lisOfPlatForms);
            }

        }


        // set Station Spinner Value
        if (stationList != null && stationList.size() > 0 && listOfPlatFormsFinal != null && listOfPlatFormsFinal.size() > 0) {
            setStationSpinner(stationList);
            setPlatFormSpinnerData(0);
        }
    }

    private void setStationSpinner(List<SendRequestFormResponse.Station> stationList) {

        List<SendRequestFormResponse.Station> finalList = new ArrayList<>();

        SendRequestFormResponse.Station station = new SendRequestFormResponse.Station();
        station.setId(-1);
        station.setName(selectedStations);

        finalList.add(station);
        finalList.addAll(stationList);
        ArrayAdapter<SendRequestFormResponse.Station> adapterStation = new ArrayAdapter<SendRequestFormResponse.Station>(RailwayCategoryFormActivity.this, android.R.layout.simple_spinner_dropdown_item, finalList);
        spinnerStations.setAdapter(adapterStation);

        spinnerStations.setSelection(0, true);
        View v = spinnerStations.getSelectedView();
        setTextCustom(v);


        spinnerStations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                SendRequestFormResponse.Station station = (SendRequestFormResponse.Station) adapterView.getItemAtPosition(position);
                setPlatFormSpinnerData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setPlatFormSpinnerData(int position) {
        List<SendRequestFormResponse.PlatformList> finalList = new ArrayList<>();

        SendRequestFormResponse.PlatformList platform = new SendRequestFormResponse.PlatformList();
        platform.setPlatform(selectedPlatform);

        int pos = position - 1;
        if (pos == -1) {
            finalList.add(platform);
        } else {
            finalList.add(platform);
            List<SendRequestFormResponse.PlatformList> list = listOfPlatFormsFinal.get(pos);
            if (list != null && list.size() > 0) {
                finalList.addAll(list);
            }
        }


        ArrayAdapter<SendRequestFormResponse.PlatformList> adapterStation = new ArrayAdapter<SendRequestFormResponse.PlatformList>(RailwayCategoryFormActivity.this, android.R.layout.simple_spinner_dropdown_item, finalList);
        spinnerPlatform.setAdapter(adapterStation);
        View v = spinnerPlatform.getSelectedView();
        setTextCustom(v);

    }

    public void setTextCustom(View view) {
        TextView customTextView = ((TextView) view);
        if (customTextView != null) {
            //customTextView.setTextColor(getResources().getColor(R.color.white));
            customTextView.setTextColor(getResources().getColor(R.color.dark_blue));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.linearLayoutCamera:
                startCameraActivity();
                break;
            case R.id.imageViewPhoto:
                startCameraActivity();
                break;
            case R.id.buttonSubmit:


                if (NetworkUtil.hasConnectivity(RailwayCategoryFormActivity.this)) {

                    callSendRequestAPI();
                } else {
                    Toast.makeText(RailwayCategoryFormActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                }


              /*  AlertDialog.Builder alertDialog = new AlertDialog.Builder(RailwayCategoryFormActivity.this, R.style.alertDialog);
                alertDialog.setTitle("Thank You !!!");
                alertDialog.setMessage("Thank You !!!");
                alertDialog.setPositiveButton("Check Status", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //SharedPreferenceManager.clearPreferences();
                        Intent intent = new Intent(RailwayCategoryFormActivity.this, MyRequestsActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
               *//* // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });*//*
                // Showing Alert Message
                alertDialog.show();*/


        }

    }

    // -------------------------------------------------------CAMERA--------------------------------------------------------------------------------

    // Camera Permission
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

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            startCamera();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }

    };


    // Call back from StartActivityForResult,  REQUEST_IMAGE_CAPTURE should be same
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {

                if (path != null) {
                    linearLayoutCamera.setVisibility(View.GONE);
                    imageViewPhoto.setVisibility(View.VISIBLE);
                    Bitmap bitmap = decodeSampledBitmapFromFile(path, Utils.DpToPixel(RailwayCategoryFormActivity.this, 270), Utils.DpToPixel(RailwayCategoryFormActivity.this, 150));

                    imageViewPhoto.setImageBitmap(bitmap);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
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

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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


    // -------------------------------------------------------CAMERA--------------------------------------------------------------------------------


    public void callSendRequestAPI() {
        //creating request body for file


        progressDialogForAPI = new ProgressDialog(RailwayCategoryFormActivity.this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();


        File baseImage = new File(path);
        int compressionRatio = 2; //1 == originalImage, 2 = 50% compression, 4=25% compress
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(baseImage.getPath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(baseImage));
        } catch (Throwable t) {
            Log.e("ERROR", "Error compressing file." + t.toString());
            t.printStackTrace();
        }

        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), editTextComment.getText().toString());
        RequestBody serviceId = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
        RequestBody stationId = RequestBody.create(MediaType.parse("multipart/form-data"), "6");


        RequestBody requestBaseFile = RequestBody.create(MediaType.parse("multipart/form-data"), baseImage);
        MultipartBody.Part bodyImage = MultipartBody.Part.createFormData("image_path", "image" + System.currentTimeMillis(), requestBaseFile);


        String header = "Bearer " + SharedPreferenceManager.getUserObjectFromSharedPreference().getSuccess().getToken();

        Call<SaveComplaintResponse> colorsCall = RestClient.getApiService(ApiConstants.BASE_URL).uploadImage(header, bodyImage, description, serviceId, stationId);

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
