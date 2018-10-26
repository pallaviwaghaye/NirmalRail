package com.webakruti.nirmalrail.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.adapter.RailwayCategoryAdapter;
import com.webakruti.nirmalrail.model.ColonyResponse;
import com.webakruti.nirmalrail.model.RailwayCategoryResponse;
import com.webakruti.nirmalrail.model.SaveComplaintResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.ui.MyRequestsActivity;
import com.webakruti.nirmalrail.ui.RailwayCategoryFormActivity;
import com.webakruti.nirmalrail.ui.SuccessActivity;
import com.webakruti.nirmalrail.utils.NetworkUtil;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;
import com.webakruti.nirmalrail.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import static android.app.Activity.RESULT_OK;


public class ColonyFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private LinearLayout linearLayoutCamera;
    //private Button buttonCamera;
    private ImageView imageViewPhoto;
    private File baseImage;
    private File photoFile;
    private String path;
    Uri outPutfileUri;

    private Spinner spinnerColony;
    ColonyResponse.Colony selectedColony;
    private EditText editTextColonyAddress;
    private EditText editTextColonyComment;
    private Button buttonColonySubmit;

    private View rootView;
    private ProgressDialog progressDialogForAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_colony, container, false);
        selectedColony = new ColonyResponse.Colony();
        selectedColony.setId(-1);
        selectedColony.setName("Select colony");
        initViews();

        if (NetworkUtil.hasConnectivity(getActivity())) {
            getColonys();

        } else {
            Toast.makeText(getActivity(), R.string.no_internet_message, Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    private void getColonys() {

        progressDialogForAPI = new ProgressDialog(getActivity());
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();

        SharedPreferenceManager.setApplicationContext(getActivity());
        String token = SharedPreferenceManager.getUserObjectFromSharedPreference().getSuccess().getToken();

        String headers = "Bearer " + token;
        Call<ColonyResponse> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).getColony(headers);
        requestCallback.enqueue(new Callback<ColonyResponse>() {
            @Override
            public void onResponse(Call<ColonyResponse> call, Response<ColonyResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    ColonyResponse details = response.body();
                    //  Toast.makeText(getActivity(),"Data : " + details ,Toast.LENGTH_LONG).show();
                    if (details.getSuccess().getStatus()) {

                        List<ColonyResponse.Colony> list = details.getSuccess().getColonies();
                        setColonySpinnerData(list);
                    }

                } else {
                    // Response code is 401
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<ColonyResponse> call, Throwable t) {

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

    private void setColonySpinnerData(List<ColonyResponse.Colony> colonyList) {

        List<ColonyResponse.Colony> finalList = new ArrayList<>();
        ColonyResponse.Colony colony = new ColonyResponse.Colony();
        colony.setId(-1);
        colony.setName("Select colony");
        finalList.add(colony);

        finalList.addAll(colonyList);


        // set spinner data
        ArrayAdapter<ColonyResponse.Colony> adapter = new ArrayAdapter<ColonyResponse.Colony>(getActivity(), android.R.layout.simple_spinner_dropdown_item, finalList);
        spinnerColony.setAdapter(adapter);

        //13th july
        spinnerColony.setSelection(0, true);
        View v = spinnerColony.getSelectedView();
        setTextCustom(v);


        spinnerColony.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedColony = (ColonyResponse.Colony) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initViews() {

        spinnerColony = (Spinner) rootView.findViewById(R.id.spinnerColony);
        editTextColonyAddress = (EditText) rootView.findViewById(R.id.editTextColonyAddress);
        editTextColonyComment = (EditText) rootView.findViewById(R.id.editTextColonyComment);


        linearLayoutCamera = (LinearLayout) rootView.findViewById(R.id.linearLayoutCamera);
        linearLayoutCamera.setOnClickListener(this);

        buttonColonySubmit = (Button) rootView.findViewById(R.id.buttonColonySubmit);
        buttonColonySubmit.setOnClickListener(this);

        imageViewPhoto = (ImageView) rootView.findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setOnClickListener(this);
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
            case R.id.buttonColonySubmit:

                if (selectedColony.getId() != -1) {
                    if (editTextColonyAddress.getText().toString().length() > 0) {
                        if (editTextColonyComment.getText().toString().length() > 0) {

                            if (NetworkUtil.hasConnectivity(getActivity())) {
                                callUploadForColony();
                            } else {
                                Toast.makeText(getActivity(), R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Please enter address", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please enter address", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getActivity(), "Please select colony", Toast.LENGTH_SHORT).show();
                }

        }

    }

    private void callUploadForColony() {
        // Station and Category Service


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
          /*  Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_SHORT).show();
            return;*/
            path = null;
        }


        progressDialogForAPI = new ProgressDialog(getActivity());
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();
/*
   @Part("colony_id") RequestBody colonyId,
            @Part("description") RequestBody description,
            @Part("address") RequestBody address
 */

        RequestBody colonyId = RequestBody.create(MediaType.parse("multipart/form-data"), selectedColony.getId() + "");
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), editTextColonyComment.getText().toString());
        RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), editTextColonyAddress.getText().toString());


        RequestBody requestBaseFile;
        MultipartBody.Part bodyImage = null;
        if (path != null) {
            // with image
            requestBaseFile = RequestBody.create(MediaType.parse("multipart/form-data"), baseImage);
            bodyImage = MultipartBody.Part.createFormData("image_path", "image" + System.currentTimeMillis(), requestBaseFile);


        } else {
            // without image
            requestBaseFile = RequestBody.create(MediaType.parse("multipart/form-data"), baseImage);
            bodyImage = MultipartBody.Part.createFormData("image_path", "image" + System.currentTimeMillis(), requestBaseFile);
        }


        String header = "Bearer " + SharedPreferenceManager.getUserObjectFromSharedPreference().getSuccess().getToken();

        Call<SaveComplaintResponse> colorsCall = RestClient.getApiService(ApiConstants.BASE_URL).uploadColonyRequest(header, bodyImage, colonyId, description, address);

        colorsCall.enqueue(new Callback<SaveComplaintResponse>() {
            @Override
            public void onResponse(Call<SaveComplaintResponse> call, Response<SaveComplaintResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    //
                    try {
                        SaveComplaintResponse saveComplaintResponse = response.body();

                        if (saveComplaintResponse.getSuccess() != null) {
                            if (saveComplaintResponse.getSuccess().getStatus()) {
                                //Toast.makeText(getApplicationContext(), saveComplaintResponse.getSuccess().getMsg(), Toast.LENGTH_SHORT).show();
                                Log.e("Upload", "Upload Successful");
                                showDialog();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Unable to reach server ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Unable to reach server ", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Unable to reach server ", Toast.LENGTH_SHORT).show();
                }


                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }

            }


            @Override
            public void onFailure(Call<SaveComplaintResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Time out error. ", Toast.LENGTH_SHORT).show();
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


    public void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.alertDialog);
        alertDialog.setTitle("Thank You !!!");
        alertDialog.setMessage("Thank You !!!");
        alertDialog.setPositiveButton("Check Status", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //SharedPreferenceManager.clearPreferences();
                Intent intent = new Intent(getActivity(), MyRequestsActivity.class);
                intent.putExtra("TYPE_COMPLAINTS", 1); // 1 - Colony
                startActivity(intent);

            }
        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }


    // -------------------------------------------------------CAMERA--------------------------------------------------------------------------------

    // Camera Permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startCameraActivity() {

        if (!checkPermission()) {

            if (shouldShowRequestPermissionRationale(CAMERA) && shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)
                    && shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                new TedPermission(getActivity())
                        .setPermissionListener(permissionlistener)
                        .setRationaleConfirmText("ALLOW")
                        .setRationaleMessage("App Requires Permission")
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                        .check();
            } else {
                new TedPermission(getActivity())
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
        int result = ContextCompat.checkSelfPermission(getActivity(), CAMERA);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {

                if (path != null) {
                    linearLayoutCamera.setVisibility(View.GONE);
                    imageViewPhoto.setVisibility(View.VISIBLE);
                    Bitmap bitmap = decodeSampledBitmapFromFile(path, Utils.DpToPixel(getActivity(), 270), Utils.DpToPixel(getActivity(), 150));

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
        outPutfileUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, // Which columns to return
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

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

}
