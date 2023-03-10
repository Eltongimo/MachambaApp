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
import com.example.machambaapp.model.datamodel.Localidade;
import com.example.machambaapp.model.interfaces.IItemClickListener;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_etnia, parent, false);
        return new LocalidadeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalidadeAdapter.ViewHolder holder, int position) {
        Localidade Localidade = this.localidades.get(position);
        holder.Localidade.setText(Localidade.getNome());

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
        private final TextView Localidade;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Localidade = itemView.findViewById(R.id.nomeClient);


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
