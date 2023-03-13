package com.example.machambaapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.machambaapp.ActivityUserPL;
import com.example.machambaapp.databinding.FragmentHomeBinding;
import com.example.machambaapp.ui.admin.views.ActivityViewAddCultura;
import com.example.machambaapp.ui.admin.views.ActivityViewComunidade;
import com.example.machambaapp.ui.admin.views.ActivityViewDistrito;
import com.example.machambaapp.ui.admin.views.ActivityViewEtnia;
import com.example.machambaapp.ui.admin.views.ActivityViewLocalidade;
import com.example.machambaapp.ui.admin.views.ActivityViewPostoAdmnistrativo;
import com.example.machambaapp.R;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        CardView cardViewUserPl=(CardView) view.findViewById(R.id.idCardUserPlR);
        CardView cardViewDistrito=(CardView) view.findViewById(R.id.idCardOpctDistrito);
        CardView cardViewComunidade=(CardView) view.findViewById(R.id.idCardComunidade);
        CardView cardViewLocalidade=(CardView) view.findViewById(R.id.idCardLocalidade);
        CardView cardViewEtnia=(CardView) view.findViewById(R.id.idCardEtnia);
        CardView cardViewCultura=(CardView) view.findViewById(R.id.idCardCultura);
        CardView cardViewPostoAdministrativo=(CardView) view.findViewById(R.id.idCardPosto);



        cardViewUserPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityUserPL.class));
            }
        });

        cardViewComunidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityViewComunidade.class));
            }
        });

        cardViewEtnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityViewEtnia.class));
            }
        });

        cardViewCultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityViewAddCultura.class));
            }
        });

        cardViewDistrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityViewDistrito.class));
            }
        });

        cardViewLocalidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityViewLocalidade.class));
            }
        });

        cardViewPostoAdministrativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityViewPostoAdmnistrativo.class));
            }
        });


        return view;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getContext(), " volt", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}