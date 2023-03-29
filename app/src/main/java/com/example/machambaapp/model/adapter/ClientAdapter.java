package com.example.machambaapp.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machambaapp.ActivitySelectClient;
import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.interfaces.IItemClickListener;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder>{

    private final Context mContext;
    private final ArrayList<Cliente> mSpecies;

    public ClientAdapter(Context context, ArrayList<Cliente> species){
        mContext = context;
        mSpecies = species;
    }


    @NonNull
    @Override
    public ClientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_select_clientes, parent, false);
        return new ClientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ViewHolder holder, int position) {
        Cliente client = mSpecies.get(position);
        holder.nomeClient.setText(client.getNome()+ " "+ client.getApelido());
        //holder.foto.setImageURI(client.getFaceImage());

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

//                Fragment fragmentViewEspecies= SubEspecieFragment.newInstance("ESTO AQUI BARATA");
//                AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
//                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentViewEspecies).commit();

                 Intent intent = new Intent(mContext, ActivitySelectClient.class);
             //    intent.putExtra("Foto", client.getFaceImage());
                 intent.putExtra("fullName", client.getNome());
                 mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mSpecies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView nomeClient;
        private final ImageView foto;
        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nomeClient = itemView.findViewById(R.id.nomeClient);
            foto = itemView.findViewById(R.id.imageClient);

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
