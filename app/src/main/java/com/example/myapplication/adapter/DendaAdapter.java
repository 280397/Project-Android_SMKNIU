package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.DataDendaItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DendaAdapter extends RecyclerView.Adapter<DendaAdapter.View_Holder>{
    public List<DataDendaItem> itemDenda;



    public void setData(List<DataDendaItem> item){
        if (item != null){
            this.itemDenda = item;
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DendaAdapter.View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.denda, parent, false);
        return new View_Holder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        holder.sh_denda.setText(itemDenda.get(position).getDend());

    }

    @Override
    public int getItemCount() {
        return itemDenda.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {
        TextView sh_denda;
        public View_Holder(@NonNull View itemView) {
            super(itemView);
            sh_denda = itemView.findViewById(R.id.txt_denda);
        }
    }
}
