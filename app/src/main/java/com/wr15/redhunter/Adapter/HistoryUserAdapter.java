package com.wr15.redhunter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.wr15.redhunter.R;
import com.wr15.redhunter.model.MUser;

import java.util.List;

public class HistoryUserAdapter extends RecyclerView.Adapter<HistoryUserAdapter.Holder>{

    private List<MUser> historiuser;
    private ItemClickListener<MUser> listeneruser;

    public HistoryUserAdapter(List<MUser> historiuser) {
        this.historiuser = historiuser;
    }

    public void setListener(ItemClickListener listeneruser) {
        this.listeneruser = listeneruser;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user_dev, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.txtnamauser.setText("Nama : " + historiuser.get(position).getNama_user());
        holder.txtdivisiuser.setText("Divisi : " + historiuser.get(position).getDivisi());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeneruser.onClicked(historiuser.get(position), position, v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return historiuser.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView txtnamauser,  txtdivisiuser;
        private LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.linearHistoryuser);
//            imgFoto = (ImageView) itemView.findViewById(R.id.img_foto);
            txtnamauser = itemView.findViewById(R.id.txtnamauser);
            txtdivisiuser = itemView.findViewById(R.id.txtdivisiuser);

        }
    }




}
