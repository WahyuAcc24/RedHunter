package com.wr15.redhunter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wr15.redhunter.R;
import com.wr15.redhunter.model.MPengajuan;

import java.util.List;

public class HistoryBrgPengajuanHrdAdapter extends RecyclerView.Adapter<HistoryBrgPengajuanHrdAdapter.Holder>{

    private List<MPengajuan> historipengajuan;
    private ItemClickListener<MPengajuan> listenerpengajuan;

    public HistoryBrgPengajuanHrdAdapter(List<MPengajuan> historipengajuan) {
        this.historipengajuan = historipengajuan;
    }

    public void setListener(ItemClickListener listenerpengajuan) {
        this.listenerpengajuan = listenerpengajuan;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pengajuan_barang_hrd, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.txtnamapengajuan.setText("Nama Barang : " + historipengajuan.get(position).getNama_brg());
        holder.txtjmlhpengajuan.setText("Jumlah Barang : " + historipengajuan.get(position).getJumlah_brg());
        holder.txtdivisi.setText("Divisi : " + historipengajuan.get(position).getDivisi());
        holder.txtStatus.setText(historipengajuan.get(position).getStatus());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerpengajuan.onClicked(historipengajuan.get(position), position, v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return historipengajuan.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView txtnamapengajuan,  txtjmlhpengajuan, txtdivisi , txtStatus;
        private LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.linearpHistorybrghrd);
//            imgFoto = (ImageView) itemView.findViewById(R.id.img_foto);
            txtnamapengajuan = itemView.findViewById(R.id.txtpnamabrghrd);
            txtjmlhpengajuan = itemView.findViewById(R.id.txtpjmlhbrghrd);
            txtdivisi = itemView.findViewById(R.id.txtpdivisibrghrd);
            txtStatus = itemView.findViewById(R.id.txtstatuspengajuanhrd);

        }
    }




}
