package com.example.andrea.diariostraordinari.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrea.diariostraordinari.R;

/**
 * III FRAGMENT PER IL FORM DI COMPILAZIONE DELL'OPERAIO
 */

public class ThreeFragment extends Fragment {

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Metodo onCreateView per la creazione della View del Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Collego gli elementi del file fragmnet_two.xml alla classe
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        //Non c'è niente da settare in fase di creazione della View nel III Fragment

        return view;

    }

}