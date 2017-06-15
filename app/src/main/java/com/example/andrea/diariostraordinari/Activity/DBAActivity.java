package com.example.andrea.diariostraordinari.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.andrea.diariostraordinari.API.APIservice;
import com.example.andrea.diariostraordinari.API.APIurl;
import com.example.andrea.diariostraordinari.Adapter.Attore;
import com.example.andrea.diariostraordinari.Adapter.AttoriListAdapter;
import com.example.andrea.diariostraordinari.R;
import com.example.andrea.diariostraordinari.result.result_delete;
import com.example.andrea.diariostraordinari.result.result_listaUtenti;

import java.util.ArrayList;
import java.util.List;

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
 * RIF. 5 GESTIONE DEI MENU' CONTESTUALI
 * RIF. 6 GESTIONE SPINNER E ADAPTER
 * RIF. 7 GESTIONE LISTVIEW E ADAPTER
 *
 */

/*** COSE DA FARE ***
 *
 * Decidere per bene le scritte da mandare a video per l'utente
 *
 * */

/**
 * Schermata di gestione degli utenti per DBA
 */

public class DBAActivity extends AppCompatActivity {

    //Variabili di classe
    private ListView dbaListView;
    private AttoriListAdapter attoriListAdapter;
    private AppCompatSpinner dbaSpinnerAttori;
    //View dell'Activity per le notifiche all'utente
    private View dbaView;

    /*** VEDI RIF. 4 ***/
    //Stringa per il titolo dell'activity
    private String titoloActivity = "DBA Activity";

    /*** VEDI RIF. 4 ***/
    //Gestisco il menù presente nell'ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        //Aggiungo il menù in base allo stile grafico del file dbamenu.xml
        inflater.inflate(R.menu.dbamenu, menu);

        //Al primo (e unico) elemento selezionabile del menù aggiungo un ClickListener
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //Avvio la nuova Activity per aggiungere un nuovo utente
                activityStart(Nuovo_utenteActivity.class);

                return false;

            }
        });

        return true;

    }

    /*** VEDI RIF. 5 ***/
    //Setto il menù contestuale con la pressione prolungata sugli elementi della listView
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        //Aggiungo il menù in base allo stile grafico del file dbamenu.xml
        inflater.inflate(R.menu.context_menu, menu);
    }

    /*** VEDI RIF. 5 ***/
    //Listener per gli elementi del menù contestuale
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //Discerno l'elemento selezionato
        switch (item.getItemId()) {
            //Nel caso del primo elemento
            case R.id.MENU_0:
                //Richiamo il metodo per la modifica dei dati dell'utente selezionato nel DB
                modifica();
                return true;
            //Nel caso del secondo elemento
            case R.id.MENU_1:
                //Reperisco l'id dell'utente selezionato
                String my_id = ((Attore) dbaListView.getAdapter().getItem(info.position)).getIdattore();
                //Richiamo il metodo per l'eliminazione dei dati dell'utente selezionato nel DB
                elimina(my_id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //Metodo onCreate per il caricamento delle parti grafiche dell'activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dba);

        /*** VEDI RIF. 4 ***/
        //Setto il titolo e abilito il tasto indietro digitale nell'ActionBar
        getSupportActionBar().setTitle(titoloActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Collego gli elementi del file activity_login.xml alla classe
        dbaView = findViewById(R.id.dbaContainerLayout);
        dbaListView = (ListView) findViewById(R.id.dbaListView);
        dbaSpinnerAttori = (AppCompatSpinner) findViewById(R.id.dbaSelezioneAttoreSpinner);

        /*** VEDI RIF. 6 ***/
        //Creo l'adapter per inserire i valori nello spinner
        final ArrayAdapter<CharSequence> adapterAttori = createSpinnerAdapter();
        //Applico l'adapter allo spinner
        dbaSpinnerAttori.setAdapter(adapterAttori);
        //Setto un Listener per gestire la selezione degli elementi nello Spinner
        dbaSpinnerAttori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Tento una connessione
                try {

                    //Richiamo il metodo per gestire l'accesso al DB
                    listaUtenti(getApplicationContext(), false);

                }
                catch (Exception e){

                    //Segnalo l'eccezione nel file Log.e
                    Log.e("Exception: ", e.toString());

                }

            }

            //Nessuna selezione
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //Tento una connessione
                try {

                    //Richiamo il metodo per gestire l'accesso al DB inibendo il filtro della ListView
                    listaUtenti(getApplicationContext(), true);

                }
                catch (Exception e){

                    //Segnalo l'eccezione nel file Log.e
                    Log.e("Exception: ", e.toString());

                }

            }

        });

        /*** VEDI RIF. 5 ***/
        //Registro il menù contestuale alla ListView
        registerForContextMenu(dbaListView);

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
    //Metodo per gestire la connessione al DB e per reperire gli utenti da visualizzarli nella listView
    private void listaUtenti(final Context context, final boolean no_filter) {

        //Mi collego al server locale per i servizi REST
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIurl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Accedo ai servizi REST
        APIservice service = retrofit.create(APIservice.class);

        //Effettuo una Call (di tipo result_listUtenti) per l'accesso al DB
        Call<result_listaUtenti> call = service.getListaUtenti();
        //Metto in coda la Call e gestisco la risposta
        call.enqueue(new Callback<result_listaUtenti>() {

            //Connessione riuscita
            @Override
            public void onResponse(Call<result_listaUtenti> call, Response<result_listaUtenti> response) {

                /*** VEDI RIF. 7 ***/
                //Acquisisco la lista di valori dalla response della Call
                List<Attore> list = response.body().getUtenti();
                //Richiamo il metodo per filtrare la lista in base allo spinner
                List<Attore> filtered_list = filtraLista(list, no_filter);
                //Creo l'adapter complesso per gli elementi della ListView con un'istanza della classe AttoriListAdapter
                attoriListAdapter = new AttoriListAdapter(context, R.layout.attore_view, new ArrayList<Attore>(filtered_list));
                //Setto l'adapter alla ListView
                dbaListView.setAdapter(attoriListAdapter);
                /*** VEDI RIF. 5 ***/
                //Registro il menù contestuale alla ListView
                registerForContextMenu(dbaListView);

            }

            //Connessione fallita
            @Override
            public void onFailure(Call<result_listaUtenti> call, Throwable t) {

                //Notifico all'utente che la connessione non è avvenuta
                Snackbar.make(dbaView, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    /*** VEDI RIF. 6 ***/
    /*** VEDI RIF. 7 ***/
    //Metodo per filtrare la lista in base allo spinner
    private List<Attore> filtraLista(List<Attore> list, boolean no_filter){

        String filtro = dbaSpinnerAttori.getSelectedItem().toString();
        List<Attore> new_list = new ArrayList<Attore>();

        //Controllo se la lista non deve essere filtrata
        if(!no_filter) {
            //Scorro tutta la lista di partenza
            for (Attore tmp : list) {

                //La nuova lista avrà solo gli elementi della vecchia che soddisfano il filtro
                if (tmp.getTipo().equals(filtro))
                    new_list.add(tmp);

            }
        }

        return new_list;

    }

    /*** VEDI RIF. 1 ***/
    //Metodo per gestire la connessione al DB e per eliminare l'utente selezionato
    private void elimina(final String idattore){

        //Mi collego al server locale per i servizi REST
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIurl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Accedo ai servizi REST
        APIservice service = retrofit.create(APIservice.class);

        //Effettuo una Call (di tipo result_delete) per l'accesso al DB
        Call<result_delete> call = service.cancellaUtente(idattore);
        //Metto in coda la Call e gestisco la risposta
        call.enqueue(new Callback<result_delete>() {

            //Connessione riuscita
            @Override
            public void onResponse(Call<result_delete> call, Response<result_delete> response) {

                //Notifico all'utente che l'utente è stato eliminato dal DB
                Snackbar.make(dbaView, idattore + " eliminato", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                /*** VEDI RIF. 7 ***/
                //Ricarico la ListView
                listaUtenti(getApplicationContext(), false);

            }

            //Connessione fallita
            @Override
            public void onFailure(Call<result_delete> call, Throwable t) {

                //Notifico all'utente che la connessione non è avvenuta
                Snackbar.make(dbaView, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }

    /*** VEDI RIF. 1 ***/
    //Metodo per gestire la connessione al DB e per modificare i dati dell'utente selezionato
    private void modifica(){
        //Notifico all'utente che la funzione è in fase di sviluppo
        Snackbar.make(dbaView, getString(R.string.work_in_progress), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /*** VEDI RIF. 2 ***/
    //Metodo per lanciare correttamente una nuova activity
    private void activityStart(Class activity){

        Intent i = new Intent(DBAActivity.this, activity);
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
