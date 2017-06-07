package com.example.andrea.diariostraordinari.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andrea.diariostraordinari.Adapter.Attore;
import com.example.andrea.diariostraordinari.Adapter.AttoriListAdapter;
import com.example.andrea.diariostraordinari.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
 * COMMENTARE TUTTE LE CLASSI E I FILE XML INERENTI ALLA LIST_VIEW ATTORE
 *
 */

/**
 * Schermata di gestione del DBA
 */
public class DBAActivity extends AppCompatActivity {

    // Oggetto FirebaseDatabase per comunicare con il DB
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();

    //View dell'Activity per le notifiche all'utente
    private View myView;

    //ListView e adapter per visualizzare gli utenti in una lista
    ListView dbaListView;
    AttoriListAdapter attoriListAdapter;

    String titoloActivity = "DBA Activity";

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
                activityStart(Nuovo_operaioActivity.class);

                return false;

            }
        });

        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dba);

        //Setto il titolo del menù
        getSupportActionBar().setTitle(titoloActivity);

        /*** TEST ***/
        /*** RIF. 1 ***/
        //Button provvisorio per inserire elementi nel DB
        Button testButton = (Button) findViewById(R.id.dbaTestButton);
        //TextView provvisoria per la visualizzazione dei risultati
        final TextView testTV = (TextView) findViewById(R.id.dbaTestTextView);

        /*** DA IMPLEMENTARE ***/
        /*** RIF. 2 ***/
        //Spinner (menù a tendina)
        final AppCompatSpinner spinnerAttori = (AppCompatSpinner) findViewById(R.id.dbaSelezioneAttoreSpinner);

        /*** TEST ***/
        /*** VEDI RIF. 2 ***/
        //Creo l'adapter per inserire i valori nello spinner
        //Si crea un Array di stringhe per visualizzarle negli spinner
        ArrayAdapter<CharSequence> adapterAttori = ArrayAdapter.createFromResource(this,
                R.array.attori_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterAttori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Applico l'adapter allo spinner
        spinnerAttori.setAdapter(adapterAttori);

        //Setto un Listener per gestire la selezione degli alementi nello Spinner
        spinnerAttori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DatabaseReference mDatabaseRef;
                mDatabaseRef = database.getReference();
                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    /*** TEST ***/
                    /*** VEDI RIF.1 ***/
                    /*TUTTO PROVVISORIO A SEGUITO DI VARIE PROVE.
                      ANDREBBE SEMPLICEMENTE SELEZIONATO IL FILTRO PER SCEGLIERE LA TABELLA
                      ADATTA ALLA VISUALIZZAZIONE DEGLI ELEMENTI NELLA LISTVIEW (VEDI RIF.2)*/

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String s = "";
                        testTV.setText(s);

                        String table = "AttoriTestApp";
                        String tipoAttore = spinnerAttori.getSelectedItem().toString();
                        String userId = "testAppId";

                        DataSnapshot filter = dataSnapshot.child(table).child(tipoAttore);

                        for (DataSnapshot snapshot : filter.getChildren()) {
                            try {
                                s += snapshot.getKey();
                                // s += readId(table, tipoAttore, userId, dataSnapshot);
                                s += "\n";
                                testTV.setText(s);


                            } catch (Exception e) {
                                Log.e(getString(R.string.error_DB_read), "2");
                            }
                        }
                    /*
/*
                        try {
                            Attore attore;
                            String s = "";
                            String table = "AttoriTestApp";
                            String tipoAttore = spinnerAttori.getSelectedItem().toString();
                            String userId = "testAppId";

                            attore = readUser(table, tipoAttore, userId, dataSnapshot);
                            s = getPrintableUser(tipoAttore, attore);

                            testTV.setText(s);


                        } catch (Exception e) {
                            Log.e(getString(R.string.error_DB_read), "1");
                        }
                        */

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dbaListView = (ListView) findViewById(R.id.dbaListView);

        attoriListAdapter = new AttoriListAdapter(this, R.layout.attore_view, new ArrayList<Attore>());
/*
        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("db", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("db", "Failed to read value.", error.toException());
            }
        });
*/


        //Listener per aggiornare i dati visualizzati in tempo reale
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /*** TEST ***/
                /*** VEDI RIF.1 **/

                String s = "";
                testTV.setText(s);

                String table = "AttoriTestApp";
                String tipoAttore = spinnerAttori.getSelectedItem().toString();
                String userId = "testAppId";

                DataSnapshot filter = dataSnapshot.child(table).child(tipoAttore);

                for (DataSnapshot snapshot : filter.getChildren()) {
                    try {
                        s += snapshot.getKey();
                       // s += readId(table, tipoAttore, userId, dataSnapshot);
                        s += "\n";
                        testTV.setText(s);


                    } catch (Exception e) {
                        Log.e(getString(R.string.error_DB_read), "2");
                    }
                }
            }/*
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    Attore attore;
                    String s = "";
                    String table = "AttoriTestApp";
                    String tipoAttore = spinnerAttori.getSelectedItem().toString();
                    String userId = "testAppId";

                    attore = readUser(table, tipoAttore, userId, dataSnapshot);
                    s = getPrintableUser(tipoAttore, attore);

                    testTV.setText(s);


                } catch (Exception e) {
                    Log.e(getString(R.string.error_DB_read), "2");
                }

            }*/

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String s = getString(R.string.error_DB_read) + " 3";
                Log.e(getString(R.string.error_DB_read), "2");
                Snackbar.make(myView, s, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        };
        //Aggiungo il listener all'oggetto DatabaseReference
        mDatabase.addValueEventListener(postListener);

        //Commento giusto per aggiornare github
        /*** TEST ***/
        /*** VEDI RIF.1 ***/
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipoAttore = spinnerAttori.getSelectedItem().toString();
                String id = "TestAppId";
                String nome = "TestAppNome";
                String cognome = "TestAppCognome";
                String password = "TestAppPassword";
                writeNewUser(tipoAttore, id, nome, cognome, password);
            }
        });

    }

    /*** Gestione del tasto indietro ***/
    @Override
    public void onBackPressed() {
        exitByBackKey();
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.exit_request))
                .setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finishAffinity();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }


    /*** DA IMPLEMENTARE ***/
    /*** RIF. 1 ***/

    /*

     */


    //Metodo per scrivere un nuovo utente nel DB
    private void writeNewUser(String tipoAttore, String userId, String nome, String cognome, String password) {

        String table = "AttoriTestApp";

        Attore user = new Attore(tipoAttore, nome, cognome, password);

        mDatabase.child(table).child(tipoAttore).child(userId).setValue(user);

    }

    /*** TEST ***/
    /*** VEDI RIF.2 ***/
    //Metodo provvisorio per leggere l'Id dell'elemento trovato nel DB
    private String readId(String table, String tipoAttore, String userId, DataSnapshot dataSnapshot) {

        String s = "";

        s = dataSnapshot.child(table).child(tipoAttore).getKey();
        //s = dataSnapshot.child(table).child(tipoAttore).getValue().toString();

        return s;

    }

    //Metodo per leggere un utente nel DB
    private Attore readUser(String table, String tipoAttore, String userId, DataSnapshot dataSnapshot) {

        Attore attore;

        attore = dataSnapshot.child(table).child(tipoAttore).child(userId).getValue(Attore.class);

        return attore;

    }

    //Metodo per convertire i valori della classe Attore in una serie di righe stampabili a video
    private String getPrintableUser(String tipoAttore, Attore attore){

        String s = "";

        s = tipoAttore + "\n- " + attore.getNome() + "\n- " + attore.getCognome() + "\n- " + attore.getPassword();

        return s;

    }

    //Metodo per lanciare correttamente una nuova activity
    private void activityStart(Class activity){

        Intent i = new Intent(DBAActivity.this, activity);
        startActivity(i);


    }


}
