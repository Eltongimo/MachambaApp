package com.example.machambaapp.model.adapter;

import android.content.Context;
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
import com.example.machambaapp.model.UserPl;
import com.example.machambaapp.model.interfaces.IItemClickListener;

import java.util.ArrayList;

public class UserPlAdapter extends RecyclerView.Adapter<UserPlAdapter.ViewHolder>{

    private final Context mContext;
    private final ArrayList<UserPl> mUserPl;

    public UserPlAdapter(Context context, ArrayList<UserPl> userPls){
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
        UserPl userPl = mUserPl.get(position);
        holder.nomeUserPl.setText(userPl.getNome());
        holder.imageView.setImageURI(userPl.getUriImage());
        holder.distrito.setText(userPl.getDistrito());
        holder.localidade.setText(userPl.getLocalidade());
        holder.comunidade.setText(userPl.getComunidade());
        holder.apelido.setText(userPl.getApelido());
        holder.posto.setText(userPl.getPostoAdministrativo());


        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

//                Fragment fragmentViewEspecies= SubEspecieFragment.newInstance("ESTO AQUI BARATA");
//                AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
//                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentViewEspecies).commit();

                 Intent intent = new Intent(mContext, ActivitySelectClient.class);
                 intent.putExtra("fullName", userPl.getNome());
                 mContext.startActivity(intent);
            }
        });
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

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nomeUserPl = itemView.findViewById(R.id.nomeUserPl);
            imageView=itemView.findViewById(R.id.imageClientView);
            distrito=itemView.findViewById(R.id.idDistritoPl);
            localidade=itemView.findViewById(R.id.iDLocalidadePl);
            apelido = itemView.findViewById(R.id.nomeApelido);
            comunidade = itemView.findViewById(R.id.idComunidade);
            posto = itemView.findViewById(R.id.idPosto);

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
