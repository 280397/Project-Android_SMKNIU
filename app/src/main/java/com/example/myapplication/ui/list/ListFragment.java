package com.example.myapplication.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ListAdapter;
import com.example.myapplication.model.DataAddItem;
import com.example.myapplication.model.ResponseAdd;
import com.example.myapplication.network.Initretrofit;
import com.example.myapplication.sharepref.SharedPreferences;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment{
    private ListViewModel listViewModel;
    public static EditText et_tgl_kembali, et_keperluan;
    String id_user_pjmm, id_user;
    private Button btn;
    ListAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private List<DataAddItem> list;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                ViewModelProviders.of(this).get(ListViewModel.class);



        View root = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = root.findViewById(R.id.rv_list);
        progressBar = root.findViewById(R.id.proglist);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    private void getData(final String id_user_pjm){
        Call<ResponseAdd> aadd = Initretrofit.getInstance().getList(id_user_pjm);

        aadd.enqueue(new Callback<ResponseAdd>() {
            @Override
            public void onResponse(Call<ResponseAdd> call, Response<ResponseAdd> response) {
                ResponseAdd res = response.body();
//                Log.d("","cobaaa"+res.getDataList());
//                Log.d("","cobaaa"+res.getDataAdd());
//                Log.d("","cobaaa"+res.getMessage());
                if (res.isStatus()){

                    list = res.getDataList();
                    adapter = new ListAdapter();
                    adapter.setData(list);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseAdd> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal menampilkan data!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        getData(Prefs.getString(SharedPreferences.getId(),""));


    }
}