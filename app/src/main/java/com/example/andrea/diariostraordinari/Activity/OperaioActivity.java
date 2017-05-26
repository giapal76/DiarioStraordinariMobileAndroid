package com.example.andrea.diariostraordinari.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.andrea.diariostraordinari.Adapter.MyPrintDocumentAdapter;
import com.example.andrea.diariostraordinari.R;

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
 * *** DA MODIFICARE ***
 *
 * VEDI RIF. 1
 *
 * VEDI RIF. 2
 *
 * VEDI activity_operaio.xml RIF. 3
 *
 */


/**
 * Schermata di compilazione degli straordinari per Operai
 */
public class OperaioActivity extends AppCompatActivity {

    //View dell'Activity per le notifiche all'utente
    private View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaio);

        /*** TEST ***/
        /*** RIF. 2 ***/
        //Bisognerà modificare tutta l'acquisizione delle stringhe per prenderle dal dDB e non dal file strings.xml

        //Spinner (menù a tendina)
        AppCompatSpinner spinnerZona = (AppCompatSpinner) findViewById(R.id.opZonaSpinner);
        AppCompatSpinner spinnerUnitàOperativa = (AppCompatSpinner) findViewById(R.id.opUnitàOperativaSpinner);
        AppCompatSpinner spinnerStraordinarioEffettuato = (AppCompatSpinner) findViewById(R.id.opStraordinarioEffettuatoSpinner);
        AppCompatSpinner spinnerOperaio = (AppCompatSpinner) findViewById(R.id.opOperaioSpinner);
        AppCompatSpinner spinnerComune = (AppCompatSpinner) findViewById(R.id.opComuneSpinner);
        AppCompatSpinner spinnerTipoStraordinario = (AppCompatSpinner) findViewById(R.id.opTipoStraordinarioSpinner);


        //Creo l'adapter per inserire i valori negli spinner
        //Si crea un Array di stringhe per visualizzarle negli spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterZone = ArrayAdapter.createFromResource(this,
                R.array.zone_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterUo = ArrayAdapter.createFromResource(this,
                R.array.uo_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterStraordinarioEffettuato = ArrayAdapter.createFromResource(this,
                R.array.straodinario_effettuato_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterOperai = ArrayAdapter.createFromResource(this,
                R.array.operai_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterComuni = ArrayAdapter.createFromResource(this,
                R.array.comuni_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterTipoStraordinario = ArrayAdapter.createFromResource(this,
                R.array.tipo_straodinario_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //Applico l'adapter allo spinner
        // Apply the adapter to the spinner
        spinnerZona.setAdapter(adapterZone);
        spinnerUnitàOperativa.setAdapter(adapterUo);
        spinnerStraordinarioEffettuato.setAdapter(adapterStraordinarioEffettuato);
        spinnerOperaio.setAdapter(adapterOperai);
        spinnerComune.setAdapter(adapterComuni);
        spinnerTipoStraordinario.setAdapter(adapterTipoStraordinario);

        /*** TEST ***/
        //Inserisco un Button per la stampa ma credo di modificarlo con un FloatButton più figo
        Button printButton = (Button) findViewById(R.id.opStampa_button);

        //Mi serve una View dove fare apparire le Snackbar
        myView = findViewById(R.id.opContainerLayout);

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Le Snackbar sono molto più fighe dei vecchi Toast per avvisare l'utente
                Snackbar.make(myView, getString(R.string.work_in_progress), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                /*** VEDI RIF. 1 ***/
                stampaDocumento();
            }
        });

    }

    /*** Gestione del tasto indietro ***/
    @Override
    public void onBackPressed() {
        exitByBackKey();
    }

    protected void exitByBackKey() {

        //Qui creo un'AlertDialog per avvisare l'utente che l'app sta per essere chiusa
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

    /*** RIF.1 ***/
    /*** Vedi Adapter/MyPrintDocumentAdapter.java ***/
    //Metodo per avviare la stampa di un documento
    private void stampaDocumento(){

        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        String jobName = this.getString(R.string.app_name) + " Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(this), null);

    }


}
