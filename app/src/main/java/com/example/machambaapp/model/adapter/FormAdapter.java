package com.example.machambaapp.model.adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machambaapp.model.datamodel.OfflineDBModelForm;
import com.example.machambaapp.R;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.Normalizer;
import java.util.ArrayList;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.ViewHolder>{

    private final Context mContext;
    private final ArrayList<OfflineDBModelForm> mSpecies;

    public FormAdapter(Context context, ArrayList<OfflineDBModelForm> species){
        mContext = context;
        mSpecies = species;
    }

    @NonNull
    @Override
    public FormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_form_select_list, parent, false);
        return new FormAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormAdapter.ViewHolder holder, int position) {
        OfflineDBModelForm p = mSpecies.get(position);
        holder.pergunta.setText(p.getPergunta());
        holder.resposta.setText(p.getResposta());
    }

    @Override
    public int getItemCount() {
        return mSpecies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView pergunta;
        private final TextView resposta;
        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            pergunta = itemView.findViewById(R.id.pergunta);
            resposta = itemView.findViewById(R.id.resposta);

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
