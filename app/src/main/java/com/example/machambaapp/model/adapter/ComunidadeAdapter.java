package com.example.machambaapp.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Comunidade;
import com.example.machambaapp.model.datamodel.Etnia;
import com.example.machambaapp.model.interfaces.IItemClickListener;

import java.util.ArrayList;

public class ComunidadeAdapter extends RecyclerView.Adapter<ComunidadeAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<Comunidade> comunidades;

    public ComunidadeAdapter(Context context, ArrayList<Comunidade> comunidadeC) {
        mContext = context;
        comunidades = comunidadeC;
    }
    @NonNull
    @Override
    public ComunidadeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_comunidade, parent, false);
        return new ComunidadeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComunidadeAdapter.ViewHolder holder, int position) {
        Comunidade Comunidade = this.comunidades.get(position);
        holder.Comunidade.setText(Comunidade.getNome());

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return comunidades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView Comunidade;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Comunidade = itemView.findViewById(R.id.nomeClient);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            this.mItemClickListener.onItemClick(view, getAdapterPosition());
        }

        public void setItemClickListener(IItemClickListener itemClickListener){
            this.mItemClickListener = itemClickListener;
        }
    }
}


