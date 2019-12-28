package com.example.myapplication.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RecylerViewAdapter;
import com.example.myapplication.model.DataItem;
import com.example.myapplication.model.ResponseData;
import com.example.myapplication.network.Initretrofit;
import com.example.myapplication.sharepref.SharedPreferences;

import java.util.List;

public class HomeFragment extends Fragment {


    private RecyclerView rv;
    private RecylerViewAdapter adapter;
    private List<DataItem> mitemList;
    List<DataItem> data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rv = root.findViewById(R.id.rv_gallery);
        EditText editText = root.findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });


        LinearLayoutManager lln = new LinearLayoutManager(getActivity());
        lln.setReverseLayout(true);
        rv.setLayoutManager(lln);
        rv.setHasFixedSize(true);
        getData();
        return root;

    }

    private void getData(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading Get Data...", "Please wait...", false, false);

        Call<ResponseData> call = Initretrofit.getInstance().getDatabarang();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ResponseData res =response.body();
                if (res.getData()!=null){
                    data = res.getData();
                    adapter= new RecylerViewAdapter(data);
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(getActivity(), "Fail get Data", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal menampilkan data!\nSilakan cek koneksi anda!", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }


}