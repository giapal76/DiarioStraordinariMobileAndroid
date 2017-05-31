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

import com.example.andrea.diariostraordinari.Adapter.Attore;
import com.example.andrea.diariostraordinari.Adapter.AttoriListAdapter;
import com.example.andrea.diariostraordinari.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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


        spinnerAttori2 = (AppCompatSpinner) findViewById(R.id.dbaSelezioneAttoriSpinner);

        //Si crea un Array di stringhe per visualizzarle negli spinner
        ArrayAdapter<CharSequence> adapterAttori = ArrayAdapter.createFromResource(this, R.array.attori_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterAttori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Applico l'adapter allo spinner
        spinnerAttori2.setAdapter(adapterAttori);

        insertId = (EditText) findViewById(R.id.newIdEditText);
        insertName = (EditText) findViewById(R.id.newNameEditText);
        insertSurname = (EditText) findViewById(R.id.newSurnameEditText);
        insertPass = (EditText) findViewById(R.id.newPassEditText);

        final Button insertButton = (Button) findViewById(R.id.new_Insert);

        /*** DA IMPLEMENTARE ***/
        /*** RIF. 1 ***/
        //Aggiungo un ClickListener per inserire il nuovo utente nel DB

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inserisciUtenteInDB(myView);

            }
        });

        myView = findViewById(R.id.newOpContainerLayout);



    }

    /*** DA IMPLEMENTARE ***/
    /*** RIF. 2 ***/
   //Tasto indietro
    @Override
    public void onBackPressed() {
        exitByBackKey();
    }

    protected void exitByBackKey() {

        //Qui creo un'AlertDialog per avvisare l'utente che si sta tornando a Nuovo_operaioActivity
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.back_DBA_request))
                .setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent i = new Intent(Nuovo_operaioActivity.this, DBAActivity.class);
                        startActivity(i);

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
    /*** VEDI RIF.1 ***/

    // Oggetto FirebaseDatabase per comunicare con il DB
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();

    private void inserisciUtenteInDB(View v){
        String tipoAttore = "";
        String userId = "";
        String nome = "";
        String cognome = "";
        String password = "";
        tipoAttore = spinnerAttori2.getSelectedItem().toString();
        userId = insertId.getText().toString();
        nome = insertName.getText().toString();
        cognome = insertSurname.getText().toString();
        password = insertPass.getText().toString();

        writeNewUser(tipoAttore, userId, nome, cognome, password);

        //Per il momento segnalo a video che ci stiamo lavorando
        Snackbar.make(v, getString(R.string.salvataggio_riuscito), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        //Cancello le stringhe dopo l'inserimento
        insertId.setText(" ");
        insertName.setText(" ");
        insertSurname.setText(" ");
        insertPass.setText(" ");

    }

    private void writeNewUser(String tipoAttore, String userId, String nome, String cognome, String password) {

        String table = "AttoriTestApp";

        Attore user = new Attore(tipoAttore, nome, cognome, password);

        mDatabase.child(table).child(tipoAttore).child(userId).setValue(user);

    }



}