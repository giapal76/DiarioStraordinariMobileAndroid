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
 * I FRAGMENT PER IL FORM DI COMPILAZIONE DELL'OPERAIO
 */

public class OneFragment extends Fragment {

    public OneFragment() {
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


        //Collego gli elementi del file fragmnet_one.xml alla classe
        View view =  inflater.inflate(R.layout.fragment_one, container, false);
        AppCompatSpinner spinnerZona = (AppCompatSpinner) view.findViewById(R.id.opZonaSpinner);
        AppCompatSpinner spinnerUnitàOperativa = (AppCompatSpinner) view.findViewById(R.id.opUnitàOperativaSpinner);
        AppCompatSpinner spinnerStraordinarioEffettuato = (AppCompatSpinner) view.findViewById(R.id.opStraordinarioEffettuatoSpinner);

        //Creo i vari adapter per inserire i valori negli spinner
        //Zone
        ArrayAdapter<CharSequence> adapterZone = ArrayAdapter.createFromResource(getContext(),
                R.array.zone_array, android.R.layout.simple_spinner_item);
        adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Unità Operative
        ArrayAdapter<CharSequence> adapterUo = ArrayAdapter.createFromResource(getContext(),
                R.array.uo_array, android.R.layout.simple_spinner_item);
        adapterUo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Straordinario effettuato/da effettuare
        ArrayAdapter<CharSequence> adapterStraordinarioEffettuato = ArrayAdapter.createFromResource(getContext(),
                R.array.straodinario_effettuato_array, android.R.layout.simple_spinner_item);
        adapterStraordinarioEffettuato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Applico gli adapter agli spinner
        spinnerZona.setAdapter(adapterZone);
        spinnerUnitàOperativa.setAdapter(adapterUo);
        spinnerStraordinarioEffettuato.setAdapter(adapterStraordinarioEffettuato);

        return view;

    }

}
