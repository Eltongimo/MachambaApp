package com.example.machambaapp.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machambaapp.ui.clientes.ActivitySelectClient;
import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import com.example.machambaapp.ui.clientes.SelecionarCulturas;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
        Picasso.get().load(  client.getImage()).into(new Target(){
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.foto.setImageBitmap(bitmap);
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                 Intent intent = new Intent(mContext, SelecionarCulturas.class);
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
