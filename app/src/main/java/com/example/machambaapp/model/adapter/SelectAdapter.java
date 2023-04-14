package com.example.machambaapp.model.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machambaapp.Cultura;
import com.example.machambaapp.R;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import com.example.machambaapp.model.update.UpdateCultura;

import java.util.ArrayList;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<Cultura> mCultura;

    public SelectAdapter(Context context, ArrayList<Cultura> culturas){
        mContext = context;
        mCultura = culturas;
    }

    @NonNull
    @Override
    public SelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cultura_select, parent, false);
        return new SelectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cultura cultura = this.mCultura.get(position);
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext.getApplicationContext());

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
}
}