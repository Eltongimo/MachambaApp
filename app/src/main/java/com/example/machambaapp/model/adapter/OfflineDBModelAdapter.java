package com.example.machambaapp.model.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.OfflineDBModelForm;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import com.example.machambaapp.ui.admin.forms.ShowForm;
import com.example.machambaapp.ui.clientes.SelecionarCanteiroAlfobre;

import java.util.ArrayList;

public class OfflineDBModelAdapter extends RecyclerView.Adapter<OfflineDBModelAdapter.ViewHolder>{
    private final Context mContext;
    private final ArrayList<OfflineDBModelForm> forms;

    public OfflineDBModelAdapter(Context context, ArrayList<OfflineDBModelForm> etniasL){
        mContext = context;
        forms = etniasL;
    }

    @NonNull
    @Override
    public OfflineDBModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_offline_forms, parent, false);
        return new OfflineDBModelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineDBModelAdapter.ViewHolder holder, int position) {
        OfflineDBModelForm row = this.forms.get(position);
        holder.form.setText(row.getFormId());
        AlertDialog.Builder builder;
        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, ShowForm.class);
                intent.putExtra("formId", row.getFormId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView form;

        private IItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            form = itemView.findViewById(R.id.nomeClient);
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
