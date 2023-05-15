package com.example.machambaapp.model.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.interfaces.IItemClickListener;
import com.example.machambaapp.model.update.UpdateUserPL;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
        try{
            Cliente.UserPl userPl = mUserPl.get(position);
            holder.nomeUserPl.setText(userPl.getNome()+" "+userPl.getApelido());
            holder.distrito.setText(userPl.getDistrito());

            SplashScreen.UpdateDataFromOnlineDatabase();


            Picasso.get().load( userPl.getImage()).into(new Target(){
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.imgPerfil.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

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
                    intent.putExtra("image", userPl.getImage());

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

        }catch (Exception e ){

        }

    }


    @Override
    public int getItemCount() {
        return mUserPl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView nomeUserPl;
        private final TextView distrito;
        private final ImageView imageView;
        private final ShapeableImageView imgPerfil;
        private IItemClickListener mItemClickListener;

        ImageView editar;
        ImageView apagar;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nomeUserPl = itemView.findViewById(R.id.nomeUserPl);
            imageView=itemView.findViewById(R.id.imageClientView);
            distrito=itemView.findViewById(R.id.idDistritoPl);
            editar = itemView.findViewById(R.id.editUserPl);
            apagar = itemView.findViewById(R.id.deleteUserPl);
            imgPerfil = itemView.findViewById(R.id.imageClientView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
         //   this.mItemClickListener.onItemClick(view, getAdapterPosition());
        }

        public void setItemClickListener(IItemClickListener itemClickListener){
            this.mItemClickListener = itemClickListener;
        }
    }}
