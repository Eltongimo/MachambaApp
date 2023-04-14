package com.example.machambaapp.model.adapter;

import android.app.Activity;
import android.app.Activity.*;
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

import com.example.machambaapp.ActivityLogin;
import com.example.machambaapp.Cultura;
import com.example.machambaapp.MainActivity;
import com.example.machambaapp.R;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import com.example.machambaapp.model.update.UpdateCultura;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class CulturaAdapter extends RecyclerView.Adapter<CulturaAdapter.ViewHolder>{

    private final Context mContext;
    private final ArrayList<Cultura> mCultura;

    public CulturaAdapter(Context context, ArrayList<Cultura> culturas){
        mContext = context;
        mCultura = culturas;
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
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext.getApplicationContext());

        holder.cultura.setText(cultura.getCultura());

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateCultura.class);
                intent.putExtra("cultura", cultura.getCultura());
                intent.putExtra("key", cultura.getKey());
                ((Activity) mContext).finish();
                ((Activity) mContext).startActivity(intent);
            }
        });

        holder.apagar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Apagar cultura");
                builder.setMessage("Deseja mesmo apagar a cultura "+cultura.getCultura()+" ?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.deleteCultura(cultura.getKey());
                        Toast.makeText(mContext, "Apagado com sucesso!", Toast.LENGTH_SHORT).show();
                        ((Activity) mContext).recreate();

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }


        });

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

    }

    private void recreate() {
        recreate();
    }

    @Override
    public int getItemCount() {
        return mCultura.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView cultura;
        private  final TextView chave;
        private final ImageView apagar;
        private final ImageView editar;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cultura = itemView.findViewById(R.id.nomeCultura);
            apagar = itemView.findViewById(R.id.apagar);
            editar = itemView.findViewById(R.id.editar);
            chave = itemView.findViewById(R.id.chave);
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
