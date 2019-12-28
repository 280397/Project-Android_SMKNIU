package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.model.Data;
import com.example.myapplication.model.DataItem;
import com.example.myapplication.model.ResponseData;
import com.example.myapplication.network.Initretrofit;
import com.example.myapplication.sharepref.SharedPreferences;
import com.example.myapplication.ui.addlist.AddFragment;
import com.google.android.gms.common.util.CrashUtils;
import com.google.zxing.Result;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "tag";

    private ImageView ivBgContent;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private AlertDialog.Builder dialog;
    private AlertDialog show;
    private Context context;

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new AlertDialog.Builder(this);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera\n Please go to app setting and add allow permission", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        };
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void handleResult(Result rawResult) {
//         Do something with the result here
         Log.d("lihatini", rawResult.getText()); // Prints scan results

         Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        checkbarang(rawResult.getText());

//        AddFragment.tvresult.setText(rawResult.getText());
//        onBackPressed();

//         If you would like to resume scanning,w(this);
//        call this method below:
//        scannerView.resumeCameraPrevie
    }

    private void checkbarang(final String barang) {
        Call<ResponseData> check = Initretrofit.getInstance().getPinjam(barang);

        check.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, final Response<ResponseData> response) {
                ResponseData res = response.body();
                if (res.isStatus()) {
                    Log.d("tag", res.getMessage());

                    final DataItem data = res.getData().get(0);
                    dialog.setMessage(barang +"\n"+data.getNamaBarang());
                    dialog.setTitle("Pinjam "+data.getNamaBarang()+"?");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Toast.makeText(ScanActivity.this, data.getNamaBarang(), Toast.LENGTH_SHORT).show();
//                            Log.d("tag",""+);
                            postSe(Prefs.getString(SharedPreferences.getId(),""),data.getBarcode());
                            Log.d(TAG, "onClick: "+data.getId()+"--"+SharedPreferences.getId()+"--"+data.getBarcode());
                        }
                    });

                    show = dialog.create();
                    show.show();

                    if (show.isShowing()) {
                        scannerView.stopCamera();
                    }

                } else {
                    dialog.setMessage(res.getMessage());
                    dialog.setTitle("Scan Result");
                    dialog.setMessage(barang);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(ScanActivity.this, "Barcode not registered!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    show = dialog.create();
                    show.show();

                    if (show.isShowing()) {
                        scannerView.stopCamera();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
//                Toast.makeText(ScanActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postSe (String id_user_pjm, String barcode){
        final Call<ResponseData> post = Initretrofit.getInstance().postPinjamSe(id_user_pjm, barcode);
        post.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d("halo", response.body().getMessage());


                Toast.makeText(ScanActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(ScanActivity.this, "Items in loan status or\nQueue items", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}