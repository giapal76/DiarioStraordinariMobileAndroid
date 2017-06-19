package com.example.andrea.diariostraordinari.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.andrea.diariostraordinari.R;

/**
 * II FRAGMENT PER IL FORM DI COMPILAZIONE DELL'OPERAIO
 */

public class TwoFragment extends Fragment {

    public TwoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        AppCompatSpinner spinnerOperaio = (AppCompatSpinner) view.findViewById(R.id.opOperaioSpinner);
        AppCompatSpinner spinnerComune = (AppCompatSpinner) view.findViewById(R.id.opComuneSpinner);
        AppCompatSpinner spinnerTipoStraordinario = (AppCompatSpinner) view.findViewById(R.id.opTipoStraordinarioSpinner);


        //Creo i vari adapter per inserire i valori negli spinner
        //Operai
        ArrayAdapter<CharSequence> adapterOperai = ArrayAdapter.createFromResource(getContext(),
                R.array.operai_array, android.R.layout.simple_spinner_item);
        adapterOperai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Comuni
        ArrayAdapter<CharSequence> adapterComuni = ArrayAdapter.createFromResource(getContext(),
                R.array.comuni_array, android.R.layout.simple_spinner_item);
        adapterComuni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Tipo straordinario
        ArrayAdapter<CharSequence> adapterTipoStraordinario = ArrayAdapter.createFromResource(getContext(),
                R.array.tipo_straodinario_array, android.R.layout.simple_spinner_item);
        adapterTipoStraordinario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Applico gli adapter agli spinner
        spinnerOperaio.setAdapter(adapterOperai);
        spinnerComune.setAdapter(adapterComuni);
        spinnerTipoStraordinario.setAdapter(adapterTipoStraordinario);

        return view;

    }

}
