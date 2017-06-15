package com.example.andrea.diariostraordinari.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

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
 * /*** RIF. N *** = RIFERIMENTO NUMERO N (SEGNALIBRO PER COMMENTI)
 *
 * /*** VEDI RIF. N *** = LA PORZIONE DI PROGRAMMA IN BASSO E' LEGATA AL RIFERIMENTO N
 *
 * /*** DA IMPLEMENTARE *** = CODICE DA IMPLEMENTARE (VEDI COMMENTI IN BASSO)
 *
 * /*** TEST *** = CODICE MOMENTANEO DA MODIFICARE O ELIMINARE SUCCESSIVAMENTE
 *
 */

/*** RIFERIMENTI ***
 *
 * RIF. 1 SERVIZI REST PER IL DB TRAMITE API SU SERVER
 * RIF. 2 NAVIGAZIONE TRA ACTIVITY
 * RIF. 3 GESTIONE TASTO INDIETRO
 * RIF. 4 GESTIONE ACTIONBAR
 * RIF. 5 GESTIONE DEL FLOATING ACTION BUTTON
 * RIF. 6 GESTIONE SPINNER E ADAPTER
 *
 */

/*** COSE DA FARE ***
 *
 * Decidere per bene le scritte da mandare a video per l'utente
 *
 * */

/***
 * Classe per generare un nuovo utente nel DB
 */
public class Nuovo_utenteActivity extends AppCompatActivity {

    //Variabili di classe
    private EditText insertId;
    private EditText insertName;
    private EditText insertSurname;
    private EditText insertPass;
    private AppCompatSpinner nuovoUtenteSpinnerAttori;
    //View dell'Activity per le notifiche all'utente
    private View nuovoUtenteView;

    /*** VEDI RIF. 4 ***/
    //Stringa per il titolo dell'activity
    private String titoloActivity = "Nuovo Utente";

    //Metodo onCreate per il caricamento delle parti grafiche dell'activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuovo_utente);

        /*** VEDI RIF. 4 ***/
        //Setto il titolo e abilito il tasto indietro digitale nell'ActionBar
        getSupportActionBar().setTitle(titoloActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Collego gli elementi del file activity_login.xml alla classe
        nuovoUtenteView = findViewById(R.id.newOpContainerLayout);
        insertId = (EditText) findViewById(R.id.newIdEditText); //.1
        nuovoUtenteSpinnerAttori = (AppCompatSpinner) findViewById(R.id.dbaSelezioneAttoriSpinner) ;
        insertName = (EditText) findViewById(R.id.newNameEditText);
        insertSurname = (EditText) findViewById(R.id.newSurnameEditText);
        insertPass = (EditText) findViewById(R.id.newPassEditText);
        FloatingActionButton inserisci = (FloatingActionButton) findViewById(R.id.newFABinserisci);


        /*** VEDI RIF. 6 ***/
        //Creo l'adapter per inserire i valori nello spinner
        ArrayAdapter<CharSequence> adapterAttori = createSpinnerAdapter();
        //Applico l'adapter allo spinner
        nuovoUtenteSpinnerAttori.setAdapter(adapterAttori); //.2

        /*** VEDI RIF. 5 ***/
        //Setto un Listener per gestire l'inserimento dei valori dal form al DB
        inserisci.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                //Richiamo il metodo per l'inserimento delle stringhe nel DB
                insert();
            }

        });

    }

    /*** VEDI RIF. 6 ***/
    //Metodo per creare un ArrayAdapter di Stringhe da visualizzare nello spinner
    private ArrayAdapter<CharSequence> createSpinnerAdapter(){

        /*Creo l'adapter con le stringhe contenute in R.array.attori_array
          contenuto in res/values/strings*/
        final ArrayAdapter<CharSequence> adapterAttori = ArrayAdapter.createFromResource(this, R.array.attori_array, android.R.layout.simple_spinner_item);
        //Specifico il layout dello spinner
        adapterAttori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapterAttori;

    }

    /*** VEDI RIF. 1 ***/
    //Metodo per gestire la connessione al DB e per inserire un nuovo utente
    private void insert(){

        //Acquisisco le stringhe dal form
        String idattore = insertId.getText().toString();
        String tipo = nuovoUtenteSpinnerAttori.getSelectedItem().toString();
        String nome = insertName.getText().toString();
        String cognome = insertSurname.getText().toString();
        String password = insertPass.getText().toString();

        //Mi collego al server locale per i servizi REST
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIurl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Accedo ai servizi REST
        APIservice service = retrofit.create(APIservice.class);

        //Effettuo una Call (di tipo result_insert) per l'accesso al DB
        Call<result_insert> call = service.insertUtente(idattore, tipo, nome, cognome, password);
        //Metto in coda la Call e gestisco la risposta
        call.enqueue(new Callback<result_insert>() {

            //Connessione riuscita
            @Override
            public void onResponse(Call<result_insert> call, Response<result_insert> response) {

                //Notifico all'utente che la connessione è avvenuta
                Snackbar.make(nuovoUtenteView, response.body().getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                /*Controllo che non siano stati generati errori
                  in modo da non resettare i campi nel caso in cui l'insert non va a buon fine*/
                if(!response.body().getError())
                    //Richiamo il metodo per il formReset dei campi del form
                    formReset();

            }

            //Connessione fallita
            @Override
            public void onFailure(Call<result_insert> call, Throwable t) {

                //Notifico all'utente che la connessione non è avvenuta
                Snackbar.make(nuovoUtenteView, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }

    //Metodo per resettare il form di inserimento
    private void formReset() {

        insertId.setText("");
        insertName.setText("");
        insertSurname.setText("");
        insertPass.setText("");

    }

    /*** VEDI RIF. 2 ***/
    //Metodo per lanciare correttamente una nuova activity
    private void activityStart(Class activity){

        finish();
        Intent i = new Intent(Nuovo_utenteActivity.this, activity);
        startActivity(i);


    }

    /*** VEDI RIF. 3 ***/
    // Gestione del tasto indietro digitale
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

    /*** VEDI RIF. 3 ***/
    // Gestione del tasto indietro fisico
    @Override
    public void onBackPressed() {
        exitByBackKey();
    }

    /*** VEDI RIF. 3 ***/
    //Metodo per gestire l'uscita dall'activity
    protected void exitByBackKey() {

        //Metodo per avviare DBAActivity
        activityStart(DBAActivity.class);

    }

}