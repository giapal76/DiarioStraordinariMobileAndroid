package com.example.andrea.diariostraordinari.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrea.diariostraordinari.API.APIservice;
import com.example.andrea.diariostraordinari.API.APIurl;
import com.example.andrea.diariostraordinari.R;
import com.example.andrea.diariostraordinari.result.result_insert;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
 * *** ERRORE SPINNER ***
 *
 * VEDI RIF . 3
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
public class Nuovo_utenteActivity extends AppCompatActivity {

    //View dell'Activity per le notifiche all'utente
    private View myView;
    EditText insertId;
    EditText insertName;
    EditText insertSurname;
    EditText insertPass;
    AppCompatSpinner spinnerAttori2;
    String titoloActivity = "Nuovo Utente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuovo_utente);
        //Setto il titolo del menù
        getSupportActionBar().setTitle(titoloActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        insertId = (EditText) findViewById(R.id.newIdEditText); //.1
        spinnerAttori2 = (AppCompatSpinner) findViewById(R.id.dbaSelezioneAttoriSpinner) ;
        insertName = (EditText) findViewById(R.id.newNameEditText);
        insertSurname = (EditText) findViewById(R.id.newSurnameEditText);
        insertPass = (EditText) findViewById(R.id.newPassEditText);

        //Commento giusto per aggiornare github
        /*** RIF. 3 ***/
        /*** ERA QUA L'ERRORE: tu richiamavi spinnerAttori2 nella riga 83 (.2) prima di collegarlo al file xml nella riga 72 (.1)
         *** TU AVEVI MESSO LE TRE RIGHE QUI SOTTO PRIMA DI QUELLE SOPRA E NON VA MAI FATTO PERCHE' DEVI SEMPRE PRIMA COLLEGARE IL FILE XML PER OGNI ELEMENTO**/
        ArrayAdapter<CharSequence> adapterAttori = ArrayAdapter.createFromResource(this, R.array.attori_array, android.R.layout.simple_spinner_item);
        adapterAttori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAttori2.setAdapter(adapterAttori); //.2




        /*** GESTIONE DEL FloatingActionButton ***/
        FloatingActionButton inserisci = (FloatingActionButton) findViewById(R.id.newFABinserisci);
        inserisci.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)  {
                insert();
            }
            private void insert(){

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
                        Toast.makeText(Nuovo_utenteActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        //Controllo importante in modo da non resettare i campi nel caso in cui l'insert non va a buon fine
                        if(!response.body().getError())
                            reset();

                    }

                    @Override
                    public void onFailure(Call<result_insert> call, Throwable t) {
                        Toast.makeText(Nuovo_utenteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    /*** Gestione del tasto indietro digitale ***/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                exitByBackKey();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*** Gestione del tasto indietro ***/
    @Override
    public void onBackPressed() {
        exitByBackKey();
    }

    protected void exitByBackKey() {

        activityStart(DBAActivity.class);

    }

    //Metodo per lanciare correttamente una nuova activity
    private void activityStart(Class activity){

        finish();
        Intent i = new Intent(Nuovo_utenteActivity.this, activity);
        startActivity(i);


    }

    private void reset () {
        insertId.setText(" ");
        insertName.setText(" ");
        insertSurname.setText(" ");
        insertPass.setText(" ");
    }

}