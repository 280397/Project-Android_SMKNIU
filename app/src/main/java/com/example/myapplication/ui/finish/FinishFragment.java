package com.example.myapplication.ui.finish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ScanFinishActivity;
import com.example.myapplication.ScanFinishAdminActivity;
import com.example.myapplication.adapter.FinishListAdapter;
import com.example.myapplication.model.DataAjuKembaliItem;
import com.example.myapplication.model.ResponseAjuKembali;
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

public class FinishFragment extends Fragment {
    String kode, id_user_pjmm,id;
    private Button btn;
    FinishListAdapter adapterfinish;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Button finish_btnadmin;
    TextView denda;
    private List<DataAjuKembaliItem> finish;
    private FinishViewModel finishViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        finishViewModel =
                ViewModelProviders.of(this).get(FinishViewModel.class);


        View root = inflater.inflate(R.layout.fragment_finish, container, false);
        recyclerView = root.findViewById(R.id.rv_finish_list);

        finish_btnadmin =root.findViewById(R.id.finish_btnadmin);
       denda = root.findViewById(R.id.id_denda);
        btn = (Button) root.findViewById(R.id.finish_btnscan);
        progressBar = root.findViewById(R.id.progfinish);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanFinishActivity.class);
                startActivity(intent);
            }
        });

        finish_btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanFinishAdminActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapterfinish);
    }
    private void getData(final String id_user_pjm){
        Call<ResponseAjuKembali> aadd = Initretrofit.getInstance().getfinishList(id_user_pjm);
        aadd.enqueue(new Callback<ResponseAjuKembali>() {
            @Override
            public void onResponse(Call<ResponseAjuKembali> call, Response<ResponseAjuKembali> response) {
                ResponseAjuKembali res = response.body();
                Log.d("","mahardik"+res.getMessage());
                if (res.isStatus()){

                   finish = res.getDataAjuKembali();
                    adapterfinish = new FinishListAdapter();
                    adapterfinish.setOnclickListener(new FinishListAdapter.deleteFinishListener(){
                        @Override
                        public void deleteSelected(DataAjuKembaliItem item, int possition) {
                            delete(item.getId(),possition);
                        }
                    });
                    adapterfinish.setData(finish);
                    id_user_pjmm = finish.get(0).getIdUserPjm();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapterfinish);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseAjuKembali> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal menampilkan data!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void delete(final String id, final int possition){
       Call<ResponseAjuKembali> delete = Initretrofit.getInstance().delFinishList(id);
       delete.enqueue(new Callback<ResponseAjuKembali>() {
           @Override
           public void onResponse(Call<ResponseAjuKembali> call, Response<ResponseAjuKembali> response) {
               adapterfinish.deleteList(possition);

               Toast.makeText(getActivity(), "Berhasil dihapus", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onFailure(Call<ResponseAjuKembali> call, Throwable t) {
               Toast.makeText(getActivity(), "Gagal dihapus", Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(Prefs.getString(SharedPreferences.getId(),""));

    }


}
