package com.example.andrea.diariostraordinari.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.andrea.diariostraordinari.R;

import java.util.ArrayList;

/**
 * Created by Andrea on 25/04/17.
 */

/**
 * Adatper per gestire una lista di tipo attore Attori
 */

public class AttoriListAdapter extends ArrayAdapter<Attore> {

    //Variabili di classe
    private int resource;
    private LayoutInflater inflater;

    //Metodo costruttore
    public AttoriListAdapter(Context context, int resurceId, ArrayList<Attore> objects){

        super(context, resurceId, objects);
        resource = resurceId;
        inflater = LayoutInflater.from(context);

    }

    //Metodo per acquisire la View personalizzata di un Item di tipo Attore
    @Override
    public View getView(int position, View v, ViewGroup parent) {

        // Recuperiamo l'oggetto da visualizzare
        Attore attore = getItem(position);

        //Acquisisco i nomi tramite un oggetto ViewHolder
        ViewHolder holder;

        //Recupero gli elementi grafici dal file attore_view.xml
        if (v == null) {
            v = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.idTextView = (TextView) v.findViewById(R.id.idAttore);
            holder.nomeTextView = (TextView) v.findViewById(R.id.nomeAttore);
            holder.cognomeTextView = (TextView) v.findViewById(R.id.cognomeAttore);
            v.setTag(holder);
        }else {
            holder = (ViewHolder) v.getTag();
        }

        //Inserisco i valori dell'Attore selezionato negli elementi grafici
        holder.idTextView.setText(attore.getIdattore());
        holder.nomeTextView.setText(attore.getNome());
        holder.cognomeTextView.setText(attore.getCognome());
        //Setto il colore delle scritte
        holder.idTextView.setTextColor(Color.BLACK);
        holder.nomeTextView.setTextColor(Color.BLACK);
        holder.cognomeTextView.setTextColor(Color.BLACK);

        return v;
    }

    //Classe per nomenclare le TextView
    private static class ViewHolder {

        TextView idTextView;
        TextView nomeTextView;
        TextView cognomeTextView;

    }

}