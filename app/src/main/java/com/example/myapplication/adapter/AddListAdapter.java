package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.DataAddItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddListAdapter extends RecyclerView.Adapter<AddListAdapter.ViewHolder>{

    public List<DataAddItem> itemAddList;
    private deleteListener listener;



    public void setData(List<DataAddItem> item){
        if (item != null){
            this.itemAddList = item;
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddListAdapter.ViewHolder holder, final int position) {
        holder.add_name.setText(itemAddList.get(position).getNamaBarang());
        holder.add_barcode.setText(itemAddList.get(position).getBarcode());
        holder.btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteSelected(itemAddList.get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemAddList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView add_barcode, add_name;
        Button btnhapus;

        public ViewHolder(@NonNull View itemView) {
                super(itemView);
               add_barcode = itemView.findViewById(R.id.add_barcode);
               add_name = itemView.findViewById(R.id.add_name);
               btnhapus = itemView.findViewById(R.id.btnhapus);


        }
    }

    public void setOnclickListener(deleteListener listener){
        this.listener = listener;
    }
    public interface deleteListener{

        void deleteSelected(DataAddItem item, int possition);
    }

    public void deleteList(int possition){
        itemAddList.remove(possition);
        notifyItemRemoved(possition);
        notifyDataSetChanged();
    }
}
