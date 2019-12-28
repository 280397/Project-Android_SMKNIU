package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.adapter.RecylerViewAdapter;
import com.example.myapplication.model.Data;
import com.example.myapplication.model.DataItem;
import com.example.myapplication.model.ResponseData;
import com.example.myapplication.network.Initretrofit;
import com.example.myapplication.sharepref.SharedPreferences;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecylerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.rv);

        LinearLayoutManager lln = new LinearLayoutManager(this);
        lln.setReverseLayout(true);
        recyclerView.setLayoutManager(lln);
        recyclerView.setHasFixedSize(true);
        getData();

    }
    private void getData(){
        final ProgressDialog loading = ProgressDialog.show(HomeActivity.this, "Loading Get Data...", "Please wait...", false, false);

        Call<ResponseData> call = Initretrofit.getInstance().getDatabarang();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ResponseData res =response.body();
                if (res.getData()!=null){
                    List<DataItem> data = res.getData();
                    adapter= new RecylerViewAdapter(data);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(HomeActivity.this, "Fail get Data", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(HomeActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
}
