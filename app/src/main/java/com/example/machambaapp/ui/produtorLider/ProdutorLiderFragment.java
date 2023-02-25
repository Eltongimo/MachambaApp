package com.example.machambaapp.ui.produtorLider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.machambaapp.ActivityUserRegister;
import com.example.machambaapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ProdutorLiderFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FloatingActionButton floatingActionButton;
        View view= inflater.inflate(R.layout.fragment_rodutor_lider, container, false);

        floatingActionButton=view.findViewById(R.id.idfloatFviewPL);

      floatingActionButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(getContext(), ActivityUserRegister.class));
          }
      });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}