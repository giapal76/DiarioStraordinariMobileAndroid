package com.example.andrea.diariostraordinari.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.andrea.diariostraordinari.API.APIservice;
import com.example.andrea.diariostraordinari.API.APIurl;
import com.example.andrea.diariostraordinari.R;
import com.example.andrea.diariostraordinari.result.result_accesso;

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
 *
 */

/*** COSE DA FARE ***
 *
 * Decidere per bene le scritte da mandare a video per l'utente
 * Scegliere una grafica più carina per il button
 *
 * */

/**
 * Schermata di login
 */

public class LoginActivity extends AppCompatActivity {

    //Variabili di classe
    private EditText editTextEmail, editTextPassword;
    private Button buttonAccesso;
    private View loginFormView;

    //Metodo onCreate per il caricamento delle parti grafiche dell'activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*** VEDI RIF. 4 ***/
        //Abilito il tasto indietro digitale nell'ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Collego gli elementi del file activity_login.xml alla classe
        editTextEmail = (EditText) findViewById(R.id.matricola);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonAccesso = (Button) findViewById(R.id.sign_in_button);
        loginFormView = findViewById(R.id.login_form);

        //Listener sul button di accesso
        buttonAccesso.setOnClickListener(new View.OnClickListener() {

            //Metodo per la gestione del click sul button
            @Override
            public void onClick(View v) {
                //Richiamo il metodo che gestisce l'accesso al DB
                accesso();
            }
        });

    }

    /*** VEDI RIF. 1 ***/
    //Metodo per gestire la connessione al DB e controllare le credenziali
    private void accesso() {
        //Acquisto i dati inseriti dall'utente nel form
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        //Mi collego al server locale per i servizi REST
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIurl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Accedo ai servizi REST
        APIservice service = retrofit.create(APIservice.class);

        //Effettuo una Call (di tipo result_accesso) per l'accesso al DB
        Call<result_accesso> call = service.accessoUtente(email, password);
        //Metto in coda la Call e gestisco la risposta
        call.enqueue(new Callback<result_accesso>() {

            //Connessione riuscita
            @Override
            public void onResponse(Call<result_accesso> call, Response<result_accesso> response) {

                //Notifico all'utente che la connessione è avvenuta
                Snackbar.make(loginFormView, response.body().getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Richiamo il metodo per gestire l'apertura dell'activity adatta all'utente
                apriNuovaSchermata(response);

            }

            //Connessione fallita
            @Override
            public void onFailure(Call<result_accesso> call, Throwable t) {

                //Notifico all'utente che la connessione non è avvenuta
                Snackbar.make(loginFormView, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }

    /*** VEDI RIF. 2 ***/
    //Metodo per aprire l'activity adatta in base al tipo di utente
    private void apriNuovaSchermata(Response<result_accesso> response) {

        //Controllo che la response della Call non ha restituito errori
        if(!response.body().getError()) {

            //Acquisisco il tipo utente dalla response della Call
            String tipoUser = response.body().getTipo();

            //Controllo il tipo utente per selezionare l'activity adatta
            if (tipoUser.equals(getString(R.string.user_developer)) || tipoUser.equals(getString(R.string.user_DBA)))
                activityStart(DBAActivity.class); //Avvio DBAActivity

            else if (tipoUser.equals(getString(R.string.user_operaio)))
                activityStart(OperaioActivity.class); //Avvio OperaioActivity

            else {
                //Il tipo utente trovato non è tra quelli previsti
                //lo comunico nel file di Log.e
                Log.e(getString(R.string.log_e_DB_read), getString(R.string.user_error));
                //e lo comunico all'utente
                  Snackbar.make(loginFormView, getString(R.string.error_DB_read), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }

    }

    /*** VEDI RIF. 2 ***/
    //Metodo per lanciare correttamente una nuova Activity
    private void activityStart(Class activity){

        finish();
        Intent i = new Intent(LoginActivity.this, activity);
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
    //Metodo per gestire l'uscita dall'app
    protected void exitByBackKey() {

        /*Comunico all'utente che l'app sta per essere chiusa e
          faccio scegliere all'utente se vuole procedere*/
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.exit_request))
                .setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Chiudo l'app
                        finishAffinity();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Non faccio nulla
                    }
                })
                .show();

    }

}
