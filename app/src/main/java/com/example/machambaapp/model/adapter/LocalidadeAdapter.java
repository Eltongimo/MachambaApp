package com.example.machambaapp.model.adapter;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Localidade;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import com.example.machambaapp.model.update.UpdateLocalidade;

import java.util.ArrayList;

public class LocalidadeAdapter extends RecyclerView.Adapter<LocalidadeAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<Localidade> localidades;


    public LocalidadeAdapter(Context context, ArrayList<Localidade> localidadesL){
        mContext = context;
        localidades = localidadesL;
    }


    @NonNull
    @Override
    public LocalidadeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_localidade, parent, false);
        return new LocalidadeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalidadeAdapter.ViewHolder holder, int position) {
        Localidade Localidade = this.localidades.get(position);
        holder.Localidade.setText(Localidade.getNome());
        holder.Distrito.setText(Localidade.getDistrito());
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext.getApplicationContext());
        holder.Localidade.setText(Localidade.getNome());

        SplashScreen.updateDistrito();

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateLocalidade.class);
                intent.putExtra("localidade", Localidade.getNome());
                intent.putExtra("distrito", Localidade.getDistrito());
                intent.putExtra("key", Localidade.getKey());
                ((Activity) mContext).finish();
                ((Activity) mContext).startActivity(intent);
            }
        });

        holder.apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Apagar Localidade!");
                builder.setMessage("Deseja mesmo apagar a localidade "+Localidade.getNome()+" ?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.deleteLocalidade(Localidade.getKey());
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
        return localidades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView Localidade, Distrito;
        private final ImageView apagar, editar;
        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Localidade = itemView.findViewById(R.id.nomeClient);
            Distrito = itemView.findViewById(R.id.idDistrito);
            apagar = itemView.findViewById(R.id.deletarLocalidade);
            editar = itemView.findViewById(R.id.atualizarLocalidade);

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
