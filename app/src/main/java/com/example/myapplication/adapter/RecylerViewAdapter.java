package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.DataItem;
import com.example.myapplication.network.Initretrofit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.View_Holder> {

    Context mContext;
    public List<DataItem> itemList;
    List<DataItem> itemListfull;

    public RecylerViewAdapter(List<DataItem> itemList) {


        this.itemList = itemList;
        itemListfull = new ArrayList<>(itemList);
        notifyDataSetChanged();

    }
//    public RecylerViewAdapter(Context mContext, List<DataItem> itemList) {
//
//        this.mContext = mContext;
//        this.itemList = itemList;
//
//
//    }

     @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        final View_Holder holder = new View_Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final View_Holder holder, final int position) {
        holder.barcode.setText(itemList.get(position).getBarcode());
        Picasso.get().load(Initretrofit.getIMG_URL()+itemList.get(position).getGambar()).into(holder.iv);
        holder.namaBarang.setText(itemList.get(position).getNamaBarang());
        holder.lokasi.setText(itemList.get(position).getIdLokasi());
        holder.model.setText(itemList.get(position).getModel());


        holder.ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DetailActivity.class);

                i.putExtra("barcode",itemList.get(position).getBarcode());
                i.putExtra("nama_barang",itemList.get(position).getNamaBarang());
                i.putExtra("merek",itemList.get(position).getMerk());
                i.putExtra("model",itemList.get(position).getModel());
                i.putExtra("kondisi",itemList.get(position).getIdKondisi());
                i.putExtra("lokasi",itemList.get(position).getIdLokasi());
                i.putExtra("posisi",itemList.get(position).getDtlLokasi());
                i.putExtra("sumber",itemList.get(position).getSumber());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
       return  itemList.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {
        TextView namaBarang, barcode, lokasi, model;
        ImageView iv;
        LinearLayout ly;
        public View_Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.ivGambar);
            namaBarang = itemView.findViewById(R.id.name);
            barcode = itemView.findViewById(R.id.id);
            lokasi = itemView.findViewById(R.id.id_lokasi);
            model = itemView.findViewById(R.id.model);
            ly = itemView.findViewById(R.id.Llay);

        }
    }

    public Filter getFilter(){
        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataItem> items= new ArrayList<>();
            if (constraint == null || constraint.length()==0){
                items.addAll(itemListfull);
            }else {
                String fillPatern = constraint.toString().toLowerCase().trim();
                for (DataItem it : itemListfull){
                    if (it.getNamaBarang().toLowerCase().contains(fillPatern)){
                        items.add(it);
                    }if (it.getBarcode().toLowerCase().contains(fillPatern)){
                        items.add(it);
                    }if (it.getIdLokasi().toLowerCase().contains(fillPatern)){
                        items.add(it);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = items;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
//    public void filterList(List<DataItem> filteredList) {
//        itemList = filteredList;
//        notifyDataSetChanged();
//    }
}
