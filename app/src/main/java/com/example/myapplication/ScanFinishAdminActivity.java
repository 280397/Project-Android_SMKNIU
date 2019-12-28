package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.adapter.AddListAdapter;
import com.example.myapplication.model.DataAdminItem;
import com.example.myapplication.model.ResponseAdd;
import com.example.myapplication.model.ResponseAdmin;
import com.example.myapplication.model.ResponseAjuKembali;
import com.example.myapplication.network.Initretrofit;
import com.example.myapplication.sharepref.SharedPreferences;
import com.google.zxing.Result;
import com.pixplicity.easyprefs.library.Prefs;

import static android.Manifest.permission.CAMERA;

public class ScanFinishAdminActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "tag";

    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private AlertDialog.Builder dialog;
    private AlertDialog show;
    private Context context;


    AddListAdapter adapter;
    String id_user_pjmm, id_admin;

//    public static EditText et_tgl_kembali, et_keperluan;

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

        Bundle extras = getIntent().getExtras();

        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }

    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
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
                    if (cameraAccepted) {
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
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

        checkadminfinish(rawResult.getText());

//        AddFragment.tvresult.setText(rawResult.getText());
//        onBackPressed();

//         If you would like to resume scanning,w(this);
//        call this method below:
//        scannerView.resumeCameraPrevie
    }

    private void checkadminfinish(final String admin) {
        Call<ResponseAdmin> checkadminfinish = Initretrofit.getInstance().getAdmin(admin);
        checkadminfinish.enqueue(new Callback<ResponseAdmin>() {
            @Override
            public void onResponse(Call<ResponseAdmin> call, Response<ResponseAdmin> response) {
                ResponseAdmin res = response.body();
                Log.d(TAG, "onResponse: "+response.body());
                if (res.isStatus()) {
                    Log.d("tag", res.getMessage());

//                    final DataAdminItem data = res.getData().get(0);
                    final DataAdminItem data = res.getDataAdmin().get(0);
                    dialog.setMessage(admin +"\n"+data.getName());
                    dialog.setTitle("Admin "+data.getName()+"?");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Toast.makeText(ScanActivity.this, data.getNamaBarang(), Toast.LENGTH_SHORT).show();
//                            Log.d("tag",""+);
                            kembali(Prefs.getString(SharedPreferences.getId(),""),data.getIdAdmin());
//                            pinjam(data.getIdAdmin());
//                            Log.d(TAG, "onClick: "+data.getId()+"--"+SharedPreferences.getId()+"--"+data.getBarcode());
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
                    dialog.setMessage(admin);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(ScanFinishAdminActivity.this, "Barcode not registered!", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseAdmin> call, Throwable t) {

            }
        });

    }

    public void kembali(String id_user_pjm, String id_admin ){
        Call<ResponseAjuKembali> pinjam = Initretrofit.getInstance().postDataKembali(id_user_pjm,id_admin);
        pinjam.enqueue(new Callback<ResponseAjuKembali>() {
            @Override
            public void onResponse(Call<ResponseAjuKembali> call, Response<ResponseAjuKembali> response) {
                Toast.makeText(ScanFinishAdminActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ScanFinishAdminActivity.this, DrawerActivity.class);
                startActivity(i);
                finish();
//

            }

            @Override
            public void onFailure(Call<ResponseAjuKembali> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.person);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}

