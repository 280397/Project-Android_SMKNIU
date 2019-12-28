package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.DataAddItem;
import com.example.myapplication.model.DataAjuKembaliItem;
import com.example.myapplication.model.DataKembaliItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FinishListAdapter extends  RecyclerView.Adapter<FinishListAdapter.ViewHolder> {


    public List<DataAjuKembaliItem> itemFinishList;
    private deleteFinishListener listener;



    public void setData(List<DataAjuKembaliItem> item){
        if (item != null){
            this.itemFinishList = item;
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FinishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_finish, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinishListAdapter.ViewHolder holder, final int position) {
        holder.finish_name.setText(itemFinishList.get(position).getNamaBarang());
        holder.finish_barcode.setText(itemFinishList.get(position).getBarcode());
        holder.btnhapusfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteSelected(itemFinishList.get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemFinishList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView finish_barcode, finish_name;
        Button btnhapusfinish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            finish_barcode = itemView.findViewById(R.id.finish_barcode);
            finish_name = itemView.findViewById(R.id.finish_name);
            btnhapusfinish = itemView.findViewById(R.id.btnhapusfinish);


        }
    }

    public void setOnclickListener(deleteFinishListener listener){
        this.listener = listener;
    }
    public interface deleteFinishListener{

        void deleteSelected(DataAjuKembaliItem item, int possition);
    }

    public void deleteList(int possition){
        itemFinishList.remove(possition);
        notifyItemRemoved(possition);
        notifyDataSetChanged();
    }
}
