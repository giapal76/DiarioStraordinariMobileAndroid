package com.example.andrea.diariostraordinari.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.andrea.diariostraordinari.R;

public class TwoFragment extends Fragment {

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two, container, false);


        AppCompatSpinner spinnerOperaio = (AppCompatSpinner) view.findViewById(R.id.opOperaioSpinner);
        AppCompatSpinner spinnerComune = (AppCompatSpinner) view.findViewById(R.id.opComuneSpinner);
        AppCompatSpinner spinnerTipoStraordinario = (AppCompatSpinner) view.findViewById(R.id.opTipoStraordinarioSpinner);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterOperai = ArrayAdapter.createFromResource(getContext(),
                R.array.operai_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterOperai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterComuni = ArrayAdapter.createFromResource(getContext(),
                R.array.comuni_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterComuni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterTipoStraordinario = ArrayAdapter.createFromResource(getContext(),
                R.array.tipo_straodinario_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterTipoStraordinario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOperaio.setAdapter(adapterOperai);
        spinnerComune.setAdapter(adapterComuni);
        spinnerTipoStraordinario.setAdapter(adapterTipoStraordinario);

        // Inflate the layout for getContext() fragment
        return view;
    }

}
