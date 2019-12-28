package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.DataAddItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public List<DataAddItem> List;
//    private deleteListener listener;



    public void setData(java.util.List<DataAddItem> item){
        if (item != null){
            this.List = item;
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.list_name.setText(List.get(position).getNamaBarang());
        holder.list_barcode.setText(List.get(position).getBarcode());
        holder.list_tgl_kembali.setText(List.get(position).getTglAjuKembali());

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView list_name, list_barcode,list_tgl_kembali;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_barcode = itemView.findViewById(R.id.list_barcode);
            list_tgl_kembali = itemView.findViewById(R.id.list_tgl_kembali);
        }
    }
}
