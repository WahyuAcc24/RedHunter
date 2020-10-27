package com.wr15.redhunter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wr15.redhunter.R;
import com.wr15.redhunter.model.MFile;

import java.util.List;

public class HistoryInventoryAdapter extends RecyclerView.Adapter<HistoryInventoryAdapter.Holder>{

    private List<MFile> historifile;
    private ItemClickListener<MFile> listenerfile;

    public HistoryInventoryAdapter(List<MFile> historifile) {
        this.historifile = historifile;
    }

    public void setListener(ItemClickListener listenerfile) {
        this.listenerfile = listenerfile;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_inventory, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.txtnamafile.setText("Nama Barang : " + historifile.get(position).getNama_brg());
        holder.txtjmlhfile.setText("Jumlah Barang : " + historifile.get(position).getJmlh_brg());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerfile.onClicked(historifile.get(position), position, v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return historifile.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView txtnamafile,  txtjmlhfile;
        private LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.linearHistoryinventory);
//            imgFoto = (ImageView) itemView.findViewById(R.id.img_foto);
            txtnamafile = itemView.findViewById(R.id.txtnamainventory);
            txtjmlhfile = itemView.findViewById(R.id.txtjmlhinventory);

        }
    }




}
