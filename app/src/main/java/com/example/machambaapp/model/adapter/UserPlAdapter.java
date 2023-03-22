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

import com.example.machambaapp.ActivitySelectClient;
import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import com.example.machambaapp.model.update.UpdateEtnia;
import com.example.machambaapp.model.update.UpdateUserPL;

import java.util.ArrayList;

public class UserPlAdapter extends RecyclerView.Adapter<UserPlAdapter.ViewHolder>{

    private final Context mContext;
    private final ArrayList<Cliente.UserPl> mUserPl;

    public UserPlAdapter(Context context, ArrayList<Cliente.UserPl> userPls){
        mContext = context;
        mUserPl = userPls;
    }


    @NonNull
    @Override
    public UserPlAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_edit_user_pl, parent, false);
        return new UserPlAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPlAdapter.ViewHolder holder, int position) {
        Cliente.UserPl userPl = mUserPl.get(position);
        holder.nomeUserPl.setText(userPl.getNome());
        holder.imageView.setImageURI(userPl.getUriImage());
        holder.distrito.setText(userPl.getDistrito());
        holder.localidade.setText(userPl.getLocalidade());
        holder.comunidade.setText(userPl.getComunidade());
        holder.apelido.setText(userPl.getApelido());
        holder.posto.setText(userPl.getPostoAdministrativo());

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateUserPL.class);
                    /*Put some extra attribbutes of current user pl with intent putextra */

                intent.putExtra("distrito", userPl.getDistrito());
                intent.putExtra("posto",userPl.getPostoAdministrativo());
                intent.putExtra("localidade",userPl.getLocalidade());
                intent.putExtra("comunidade", userPl.getComunidade());
                intent.putExtra("nome",userPl.getNome());
                intent.putExtra("senha", userPl.getSenha());
                intent.putExtra("phone",userPl.getPhone());
                intent.putExtra("apelido", userPl.getApelido());
                intent.putExtra("key", userPl.getKey());

                ((Activity) mContext).finish();
                ((Activity) mContext).startActivity(intent);

            }
        });

        holder.apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Apagar Usuario PL");
                builder.setMessage("Deseja mesmo apagar a o usuario "+ userPl.getNome()+" ?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.deleteUserPL(userPl.getKey());
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



//        holder.setItemClickListener(new IItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
////                Fragment fragmentViewEspecies= SubEspecieFragment.newInstance("ESTO AQUI BARATA");
////                AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
////                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentViewEspecies).commit();
//
//                 Intent intent = new Intent(mContext, ActivitySelectClient.class);
//                 intent.putExtra("fullName", userPl.getNome());
//                 mContext.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return mUserPl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView nomeUserPl;
        private final TextView distrito;
        private final TextView localidade;
        private final TextView apelido;
        private final TextView comunidade;
        private final TextView posto;
        private final ImageView imageView;
        private IItemClickListener mItemClickListener;

        ImageView editar;
        ImageView apagar;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nomeUserPl = itemView.findViewById(R.id.nomeUserPl);
            imageView=itemView.findViewById(R.id.imageClientView);
            distrito=itemView.findViewById(R.id.idDistritoPl);
            localidade=itemView.findViewById(R.id.iDLocalidadePl);
            apelido = itemView.findViewById(R.id.nomeApelido);
            comunidade = itemView.findViewById(R.id.idComunidade);
            posto = itemView.findViewById(R.id.idPosto);
            editar = itemView.findViewById(R.id.editUserPl);
            apagar = itemView.findViewById(R.id.deleteUserPl);
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
