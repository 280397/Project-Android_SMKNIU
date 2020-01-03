package com.example.myapplication.ui.addlist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ScanActivity;
import com.example.myapplication.ScanAdminAddActivity;
import com.example.myapplication.adapter.AddListAdapter;
import com.example.myapplication.model.DataAddItem;
import com.example.myapplication.model.ResponseAdd;
import com.example.myapplication.network.Initretrofit;
import com.example.myapplication.sharepref.SharedPreferences;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class AddFragment extends Fragment{
    private AddViewModel addViewModel;
    public static EditText et_tgl_kembali, et_keperluan;
    String kode, id_user_pjmm,id;
    private  Button btn;
    AddListAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Button btnadmin;
    private List<DataAddItem> add;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText txt_tgl, txt_jam;
    Button btn_get_datetime;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                ViewModelProviders.of(this).get(AddViewModel.class);



        View root = inflater.inflate(R.layout.fragment_add_list, container, false);
        recyclerView = root.findViewById(R.id.rv_add_list);

        btnadmin =root.findViewById(R.id.btnadmin);
//        et_tgl_kembali = root.findViewById(R.id.id_kembali);
        et_keperluan = root.findViewById(R.id.id_keperluan);
        btn = (Button) root.findViewById(R.id.btnscan);
        progressBar = root.findViewById(R.id.progadd);

        et_tgl_kembali = root.findViewById(R.id.txt_tgl);
//        txt_jam = (EditText) findViewById(R.id.txt_jam);
        btn_get_datetime = root.findViewById(R.id.btn_get_datetime);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        txt_tgl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txt_jam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txt_jam.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btn_get_datetime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,
                        "Tanggal : " + txt_tgl.getText().toString() + "\n" +
                                "Jam : " + txt_jam.getText().toString()
                        , Toast.LENGTH_SHORT
                ).show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivity(intent);
            }
        });

        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_tgl_kembali.getText())){
                    et_tgl_kembali.setError( "Form tidak boleh kosong!" );
                } else if (TextUtils.isEmpty(et_keperluan.getText())){
                    et_keperluan.setError("Form tidak boleh kosong!");
                }else {
                    Intent i = new Intent(getActivity(), ScanAdminAddActivity.class);
                    i.putExtra("tgl_aju_kembali",et_tgl_kembali.getText().toString());
                    i.putExtra("keperluan",et_keperluan.getText().toString());
                    startActivity(i);
                }


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
        recyclerView.setAdapter(adapter);
    }

    private void getData(final String id_user_pjm){
        Call<ResponseAdd> aadd = Initretrofit.getInstance().getAddList(id_user_pjm);
        aadd.enqueue(new Callback<ResponseAdd>() {
            @Override
            public void onResponse(Call<ResponseAdd> call, Response<ResponseAdd> response) {
                ResponseAdd res = response.body();
                Log.d("","mahardik"+res.getMessage());
                if (res.isStatus()){

                    add = res.getDataAdd();
                    adapter = new AddListAdapter();
                    adapter.setOnclickListener(new AddListAdapter.deleteListener() {
                        @Override
                        public void deleteSelected(DataAddItem item, int possition) {
                               delete(item.getId(),possition);
                        }
                    });
                    adapter.setData(add);
//                    id = add.get(0).getId();
                    id_user_pjmm = add.get(0).getIdUserPjm();
//                    id_user = add.get(0).getIdUser();
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

    private void delete(final String id, final int possition){
        Call<ResponseAdd> del = Initretrofit.getInstance().delAddList(id);
//        Log.d("taggg",id);
        del.enqueue(new Callback<ResponseAdd>() {
            @Override
            public void onResponse(Call<ResponseAdd> call, Response<ResponseAdd> response) {
                adapter.deleteList(possition);

                Toast.makeText(getActivity(), "Berhasil dihapus", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseAdd> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(Prefs.getString(SharedPreferences.getId(),""));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+resultCode);
        if (resultCode == RESULT_OK){
//            mengosongkan form
                et_tgl_kembali.setText("");
                et_keperluan.setText("");
                add.clear();
                adapter.setData(add);
        }
    }



}
