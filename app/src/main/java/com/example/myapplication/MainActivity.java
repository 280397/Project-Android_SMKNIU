package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.model.Data;
import com.example.myapplication.model.ResponseLogin;
import com.example.myapplication.network.ApiInterface;
import com.example.myapplication.network.Initretrofit;
import com.example.myapplication.sharepref.SharedPreferences;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    String uname, pass, name;
    Button masuk;
    ResponseLogin login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ui();
    }
    private void ui() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        if (Prefs.getString(SharedPreferences.getUsername(), null) == null) {
            initUi();
            return;
        } else {
            Intent i = new Intent(this, DrawerActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void initUi() {
        username = findViewById(R.id.et_usname);
        password = findViewById(R.id.et_pass);
        masuk = findViewById(R.id.btnmasuk);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( TextUtils.isEmpty(username.getText())){
                    username.setError( "Form tidak boleh kosong!" );

                } else if(TextUtils.isEmpty(password.getText())){
                    password.setError( "Form tidak boleh kosong!" );
                } else {
                    login();
                }
            }
        });
    }


    private void login() {
        final ProgressDialog loading = ProgressDialog.show(this, "Loading...", "Please wait...", false, false);
        uname = username.getText().toString();
        pass = password.getText().toString();
//        name = nama.getText().toString();
        Call<ResponseLogin> calla = Initretrofit.getInstance().getUser(uname, pass);
        calla.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                login = response.body();
                if (response.body() != null) {
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
                        Intent i = new Intent(MainActivity.this, DrawerActivity.class);
//                        i.putExtra("usename",uname);
//                        i.putExtra("name",name);
                        startActivity(i);
                        setPrefs(login.getData());
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                    loading.dismiss();


                } else {
//                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Login Failed!\nPlease check username and password!", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {

            }
        });
    }

    private void setPrefs(Data data) {
        Prefs.putString(SharedPreferences.getId(), data.getId());
        Prefs.putString(SharedPreferences.getName(), data.getName());
        Prefs.putString(SharedPreferences.getPassword(), data.getPassword());
        Prefs.putString(SharedPreferences.getUsername(), data.getPassword());
    }
}
