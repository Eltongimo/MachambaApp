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
import com.example.machambaapp.model.datamodel.Posto;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import java.util.ArrayList;
public class PostoAdapter extends RecyclerView.Adapter<PostoAdapter.ViewHolder>{
    private final Context mContext;
    private final ArrayList<Posto> postosAdministrativos;


    public PostoAdapter(Context context, ArrayList<Posto> postosAdministrativosP){
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
        holder.Posto.setText(Posto.getNome());

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
        private final TextView Posto;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Posto = itemView.findViewById(R.id.nomeClient);


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
