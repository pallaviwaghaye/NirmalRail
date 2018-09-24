package com.webakruti.nirmalrail.ui;

import android.Manifest;
import android.app.AlertDialog;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;
import com.webakruti.nirmalrail.utils.Utils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_category_form);

        initViews();

    }

    private void initViews() {

        spinnerStations = (Spinner) findViewById(R.id.spinnerStations);
        spinnerPlatform = (Spinner) findViewById(R.id.spinnerPlatform);
        editTextComment = (EditText) findViewById(R.id.editTextComment);

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

        String[] stationList = getResources().getStringArray(R.array.stations);
        ArrayAdapter<String> adapterStation = new ArrayAdapter<String>(RailwayCategoryFormActivity.this, android.R.layout.simple_spinner_dropdown_item, stationList);
        spinnerStations.setAdapter(adapterStation);

        spinnerStations.setSelection(0, true);
        View v = spinnerStations.getSelectedView();
        setTextCustom(v);

        String[] platformList = getResources().getStringArray(R.array.platforms);
        ArrayAdapter<String> adapterPlatform = new ArrayAdapter<String>(RailwayCategoryFormActivity.this, android.R.layout.simple_spinner_dropdown_item, platformList);
        spinnerPlatform.setAdapter(adapterPlatform);

        spinnerPlatform.setSelection(0, true);
        View v1 = spinnerPlatform.getSelectedView();
        setTextCustom(v1);


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
            case R.id.buttonSubmit:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RailwayCategoryFormActivity.this, R.style.alertDialog);
                /*// Setting Dialog Title
                alertDialog.setTitle("Thank You !!!");*/
                // Setting Dialog Message
                alertDialog.setMessage("Thank You !!!");
                // Setting Icon to Dialog
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Check Status", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //SharedPreferenceManager.clearPreferences();
                        Intent intent = new Intent(RailwayCategoryFormActivity.this, MyRequestsActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
               /* // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });*/
                // Showing Alert Message
                alertDialog.show();


               /* Intent intent = new Intent(RailwayCategoryFormActivity.this, SuccessActivity.class);
                startActivity(intent);
                finish();

*/
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

}
