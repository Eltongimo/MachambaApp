package com.example.machambaapp.ui.postoAdministrativo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.machambaapp.R;

public class PostoAdministrativoFragment extends Fragment {

    private PostoAdmiistrativoViewModel mViewModel;

    public static PostoAdministrativoFragment newInstance() {
        return new PostoAdministrativoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posto_administratio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PostoAdmiistrativoViewModel.class);
        // TODO: Use the ViewModel
    }

}