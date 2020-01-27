package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    String barcode,name,model, lokasi,kondisi,sum,merek,posisi;
    TextView show1,show2,show3,show4,show5,show6,show7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        show1= findViewById(R.id.show1);
        show2= findViewById(R.id.show2);
        show3= findViewById(R.id.show3);
        show4= findViewById(R.id.show4);
        show5= findViewById(R.id.show5);
        show6= findViewById(R.id.show6);
        show7= findViewById(R.id.show7);
        barcode = getIntent().getExtras().getString("barcode");
        name = getIntent().getExtras().getString("nama_barang");
        kondisi = getIntent().getExtras().getString("kondisi");
        lokasi = getIntent().getExtras().getString("lokasi");
        sum = getIntent().getExtras().getString("sumber");
        merek = getIntent().getExtras().getString("merek");
        model = getIntent().getExtras().getString("model");
        posisi = getIntent().getExtras().getString("posisi");
        show1.setText(": "+barcode);
        show2.setText(": "+name);
        show3.setText(": "+merek);
        show4.setText(": "+model);
        show5.setText(": "+kondisi);
        show6.setText(": "+lokasi);
        show7.setText(": "+posisi);

    }


}
