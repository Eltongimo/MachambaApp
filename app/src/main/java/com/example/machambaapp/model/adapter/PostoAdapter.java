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
import com.example.machambaapp.model.datamodel.Etnia;
import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Localidade;
import com.example.machambaapp.model.datamodel.Posto;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import java.util.ArrayList;
public class PostoAdapter extends RecyclerView.Adapter<PostoAdapter.ViewHolder>{
    private final Context mContext;
    private final ArrayList<Posto> postosAdministrativos;


    public PostoAdapter(Context context, ArrayList<Posto> postosAdministrativosP ){
        mContext = context;
        postosAdministrativos = postosAdministrativosP;

    }
    @NonNull
    @Override
    public PostoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_posto_administrativo, parent, false);
        return new PostoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostoAdapter.ViewHolder holder, int position) {
        Posto Posto = this.postosAdministrativos.get(position);
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext.getApplicationContext());
        holder.Posto.setText(Posto.getNome());
        holder.Localidade.setText(Posto.getLocalidade());

        holder.apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Apagar Posto Administrativo!");
                builder.setMessage("Deseja mesmo apagar o posto "+Posto.getNome()+" ?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.deletePosto(Posto.getKey());
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
        return postosAdministrativos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView Posto, Localidade;
        private final ImageView apagar;
        private final ImageView editar;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Posto = itemView.findViewById(R.id.nomeClient);
            Localidade = itemView.findViewById(R.id.nomeLocalidade);
            apagar = itemView.findViewById(R.id.deletarPosto);
            editar = itemView.findViewById(R.id.atualizarPosto);


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
