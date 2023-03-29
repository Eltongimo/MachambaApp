package com.example.machambaapp.ui.clientes;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.machambaapp.ActivitySelect;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.ui.admin.addforms.AddUserActivity;
import com.example.machambaapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

public class ClientesFragment extends Fragment {

    private ClientesViewModel mViewModel;
    private ShapeableImageView imageProfile;
    private TextView name;

    public static ClientesFragment newInstance() {
        return new ClientesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_clientes, container, false);
        CardView cardViewVisita=view.findViewById(R.id.idCardVisitaF);
        CardView cardViewClientes=view.findViewById(R.id.idCardClientF);

        imageProfile = view.findViewById(R.id.imagePL);
        name = view.findViewById(R.id.namePL);

        name.setText(SplashScreen.currentUser.getNome()+" "+SplashScreen.currentUser.getApelido());

        Picasso.get().load(SplashScreen.currentUser.getImage()).into(new Target(){
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageProfile.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


        cardViewClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddUserActivity.class));
            }
        });

        cardViewVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivitySelect.class));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ClientesViewModel.class);
        // TODO: Use the ViewModel
    }

}