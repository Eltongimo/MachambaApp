package com.example.machambaapp.model.adapter;

import androidx.recyclerview.widget.RecyclerView;

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
import com.example.machambaapp.model.datamodel.Distrito;
import com.example.machambaapp.R;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import java.util.ArrayList;

public class DistritoAdapter extends RecyclerView.Adapter<DistritoAdapter.ViewHolder>{

    private final Context mContext;
    private final ArrayList<Distrito> mDistrito;


    public DistritoAdapter(Context context, ArrayList<Distrito> userPls){
        mContext = context;
        mDistrito = userPls;
    }


    @NonNull
    @Override
    public DistritoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_distrito, parent, false);
        return new DistritoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistritoAdapter.ViewHolder holder, int position) {
        Distrito Distrito = this.mDistrito.get(position);
        holder.Distrito.setText(Distrito.getNome());
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext.getApplicationContext());
        holder.Distrito.setText(Distrito.getNome());

        holder.apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Apagar Distrito!");
                builder.setMessage("Deseja mesmo apagar o distrito "+Distrito.getNome()+" ?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.deleteDistrito(Distrito.getKey());
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
        return mDistrito.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView Distrito;
        private final ImageView apagar;
        private final ImageView editar;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Distrito = itemView.findViewById(R.id.nomeClient);
            apagar = itemView.findViewById(R.id.apagarDistrito);
            editar = itemView.findViewById(R.id.atualizarDistrito);


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
