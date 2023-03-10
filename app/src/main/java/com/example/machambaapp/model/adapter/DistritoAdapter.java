package com.example.machambaapp.model.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.machambaapp.model.datamodel.Distrito;
import com.example.machambaapp.R;
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

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Distrito = itemView.findViewById(R.id.nomeClient);


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
