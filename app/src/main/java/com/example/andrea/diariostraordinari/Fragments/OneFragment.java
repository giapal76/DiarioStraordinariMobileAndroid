package com.example.andrea.diariostraordinari.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.andrea.diariostraordinari.R;

public class OneFragment extends Fragment {

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_one, container, false);

        //Bisognerà modificare tutta l'acquisizione delle stringhe per prenderle dal dDB e non dal file strings.xml

        //Spinner (menù a tendina)
        AppCompatSpinner spinnerZona = (AppCompatSpinner) view.findViewById(R.id.opZonaSpinner);
        AppCompatSpinner spinnerUnitàOperativa = (AppCompatSpinner) view.findViewById(R.id.opUnitàOperativaSpinner);
        AppCompatSpinner spinnerStraordinarioEffettuato = (AppCompatSpinner) view.findViewById(R.id.opStraordinarioEffettuatoSpinner);

        //Creo l'adapter per inserire i valori negli spinner
        //Si crea un Array di stringhe per visualizzarle negli spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterZone = ArrayAdapter.createFromResource(getContext(),
                R.array.zone_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterUo = ArrayAdapter.createFromResource(getContext(),
                R.array.uo_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterUo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterStraordinarioEffettuato = ArrayAdapter.createFromResource(getContext(),
                R.array.straodinario_effettuato_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterStraordinarioEffettuato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Applico l'adapter allo spinner
        // Apply the adapter to the spinner
        spinnerZona.setAdapter(adapterZone);
        spinnerUnitàOperativa.setAdapter(adapterUo);
        spinnerStraordinarioEffettuato.setAdapter(adapterStraordinarioEffettuato);

        // Inflate the layout for getContext() fragment
        return view;
    }


}
