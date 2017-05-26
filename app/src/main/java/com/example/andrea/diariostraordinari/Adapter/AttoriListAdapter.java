package com.example.andrea.diariostraordinari.Adapter;

import android.content.Context;
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

public class AttoriListAdapter extends ArrayAdapter<Attore> {

    private int resource;
    private LayoutInflater inflater;

    public AttoriListAdapter(Context context, int resurceId, ArrayList<Attore> objects){
        super(context, resurceId, objects);
        resource = resurceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        // Recuperiamo l'oggetti che dobbiamo inserire in questa posizione
        Attore attore = getItem(position);

        ViewHolder holder;

        //Recupero le parti grafiche dove inserire i valori della Card
        if (v == null) {
            v = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.nomeTextView = (TextView) v.findViewById(R.id.nomeAttore);
            holder.cognomeTextView = (TextView) v.findViewById(R.id.cognomeAttore);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        //Inserisco i valori nelle parti grafiche
        holder.nomeTextView.setText(attore.getNome());
        holder.cognomeTextView.setText(attore.getCognome());

        return v;
    }

    private static class ViewHolder {
        TextView nomeTextView;
        TextView cognomeTextView;
    }
}