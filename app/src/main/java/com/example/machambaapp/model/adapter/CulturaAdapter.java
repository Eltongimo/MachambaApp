package com.example.machambaapp.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machambaapp.Cultura;
import com.example.machambaapp.R;
import com.example.machambaapp.model.interfaces.IItemClickListener;

import java.util.ArrayList;

public class CulturaAdapter extends RecyclerView.Adapter<CulturaAdapter.ViewHolder>{

    private final Context mContext;
    private final ArrayList<Cultura> mCultura;

    {

    }
    public CulturaAdapter(Context context, ArrayList<Cultura> userPls){
        mContext = context;
        mCultura = userPls;
    }


    @NonNull
    @Override
    public CulturaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_culturas, parent, false);
        return new CulturaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CulturaAdapter.ViewHolder holder, int position) {
        Cultura cultura = this.mCultura.get(position);
        holder.cultura.setText(cultura.getCultura());

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCultura.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView cultura;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cultura = itemView.findViewById(R.id.nomeCultura);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            this.mItemClickListener.onItemClick(view, getAdapterPosition());
        }

        public void setItemClickListener(IItemClickListener itemClickListener){
            this.mItemClickListener = itemClickListener;
        }
    }}
