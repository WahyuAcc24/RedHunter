package com.wr15.redhunter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wr15.redhunter.R;
import com.wr15.redhunter.model.MFile;
import com.wr15.redhunter.model.MUser;

import java.util.List;

public class HistoryFileAdapter extends RecyclerView.Adapter<HistoryFileAdapter.Holder>{

    private List<MFile> historifile;
    private ItemClickListener<MFile> listenerfile;

    public HistoryFileAdapter(List<MFile> historifile) {
        this.historifile = historifile;
    }

    public void setListener(ItemClickListener listenerfile) {
        this.listenerfile = listenerfile;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_file_dev, parent, false));
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
            item = itemView.findViewById(R.id.linearHistoryfile);
//            imgFoto = (ImageView) itemView.findViewById(R.id.img_foto);
            txtnamafile = itemView.findViewById(R.id.txtnamafile);
            txtjmlhfile = itemView.findViewById(R.id.txtjmlhfile);

        }
    }




}
