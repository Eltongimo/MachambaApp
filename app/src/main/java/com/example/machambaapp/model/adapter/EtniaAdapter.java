package com.example.machambaapp.model.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.machambaapp.model.datamodel.Etnia;
import com.example.machambaapp.R;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import java.util.ArrayList;

public class EtniaAdapter extends RecyclerView.Adapter<EtniaAdapter.ViewHolder>{
    private final Context mContext;
    private final ArrayList<Etnia> etnias;

    public EtniaAdapter(Context context, ArrayList<Etnia> etniasL){
        mContext = context;
        etnias = etniasL;
    }


    @NonNull
    @Override
    public EtniaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_etnia, parent, false);
        return new EtniaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtniaAdapter.ViewHolder holder, int position) {
        Etnia Etnia = this.etnias.get(position);
        holder.Etnia.setText(Etnia.getNome());

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return etnias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView Etnia;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Etnia = itemView.findViewById(R.id.nomeClient);


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
