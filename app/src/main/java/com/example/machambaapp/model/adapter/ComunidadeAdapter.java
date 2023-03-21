package com.example.machambaapp.model.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Comunidade;
import com.example.machambaapp.model.datamodel.Etnia;
import com.example.machambaapp.model.helper.DatabaseHelper;
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
        holder.PostoAdministrativo.setText(Comunidade.getPostoAdministrativo());
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext.getApplicationContext());
        holder.Comunidade.setText(Comunidade.getNome());

        holder.apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Apagar Comunidade!");
                builder.setMessage("Deseja mesmo apagar a comunidade "+Comunidade.getNome()+" ?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.deleteComunidade(Comunidade.getKey());
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

    @Override
    public int getItemCount() {
        return comunidades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView Comunidade, PostoAdministrativo;
        private final ImageView apagar, editar;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Comunidade = itemView.findViewById(R.id.nomeClient);
            PostoAdministrativo = itemView.findViewById(R.id.nomePosto);
            apagar = itemView.findViewById(R.id.deletarComunidade);
            editar = itemView.findViewById(R.id.atualizarComunidade);

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


