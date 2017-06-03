package com.example.andrea.diariostraordinari.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andrea.diariostraordinari.Adapter.Attore;
import com.example.andrea.diariostraordinari.Adapter.AttoriListAdapter;
import com.example.andrea.diariostraordinari.R;

import com.example.andrea.diariostraordinari.result.result_accesso;
import com.example.andrea.diariostraordinari.result.result_insert;
import com.example.andrea.diariostraordinari.API.APIservice;
import com.example.andrea.diariostraordinari.API.APIurl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.util.ArrayList;

/*** LEGENDA COMMENTI:
 *
 * /*** INIBITO *** = CODICE AUTOGENERATO NON RICHIESTO DALLE SPECIFICHE DEL PROGETTO
 *
 * /*** RIF N *** = RIFERIMENTO NUMERO N (SEGNALIBRO PER COMMENTI)
 *
 * /*** VEDI RIF N *** = LA PORZIONE DI PROGRAMMA IN BASSO E' LEGATA AL RIFERIMENTO N
 *
 * /*** DA IMPLEMENTARE *** = CODICE DA IMPLEMENTARE (VEDI COMMENTI IN BASSO)
 *
 * /*** TEST *** = CODICE MOMENTANEO DA MODIFICARE O ELIMINARE SUCCESSIVAMENTE
 *
 */

/*** COSE DA FARE
 *
 * *** DA IMPLEMENTARE ***
 *
 * VEDI RIF. 1
 *
 * *** DA IMPLEMENTARE ***
 *
 * VEDI RIF. 2
 *
 */

/***
 * Classe per generare un nuovo operaio nel DB Firebase
 */
public class Nuovo_operaioActivity extends AppCompatActivity {

    //View dell'Activity per le notifiche all'utente
    private View myView;
    EditText insertId;
    EditText insertName;
    EditText insertSurname;
    EditText insertPass;
    AppCompatSpinner spinnerAttori2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuovo_operaio);

        ArrayAdapter<CharSequence> adapterAttori = ArrayAdapter.createFromResource(this, R.array.attori_array, android.R.layout.simple_spinner_item);
        adapterAttori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAttori2.setAdapter(adapterAttori);

        insertId = (EditText) findViewById(R.id.newIdEditText);
        spinnerAttori2 = (AppCompatSpinner) findViewById(R.id.dbaSelezioneAttoriSpinner) ;
        insertName = (EditText) findViewById(R.id.newNameEditText);
        insertSurname = (EditText) findViewById(R.id.newSurnameEditText);
        insertPass = (EditText) findViewById(R.id.newPassEditText);

        final Button insertButton = (Button) findViewById(R.id.new_Insert);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }

            private void insert() {
                String idattore = insertId.getText().toString();
                String tipo = spinnerAttori2.getSelectedItem().toString();
                String nome = insertName.getText().toString();
                String cognome = insertSurname.getText().toString();
                String password = insertPass.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIurl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIservice service = retrofit.create(APIservice.class);  //accesso a tutti i servizi che ho fatto

                Call<result_insert> call = service.insertUtente(idattore, tipo, nome, cognome, password);
                call.enqueue(new Callback<result_insert>() {
                    @Override
                    public void onResponse(Call<result_insert> call, Response<result_insert> response) {
                        Toast.makeText(Nuovo_operaioActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        reset();
                    }

                    @Override
                    public void onFailure(Call<result_insert> call, Throwable t) {
                        Toast.makeText(Nuovo_operaioActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

private void reset (){
    insertId.setText(" ");
    insertName.setText(" ");
    insertSurname.setText(" ");
    insertPass.setText(" ");
}

}