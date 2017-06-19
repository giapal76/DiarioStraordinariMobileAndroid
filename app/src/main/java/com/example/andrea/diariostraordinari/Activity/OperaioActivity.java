package com.example.andrea.diariostraordinari.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.andrea.diariostraordinari.Adapter.FragmentViewPagerAdapter;
import com.example.andrea.diariostraordinari.Adapter.MyPrintDocumentAdapter;
import com.example.andrea.diariostraordinari.Fragments.OneFragment;
import com.example.andrea.diariostraordinari.Fragments.ThreeFragment;
import com.example.andrea.diariostraordinari.Fragments.TwoFragment;
import com.example.andrea.diariostraordinari.R;

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
 * RIF. 7 GESTIONE TAB E FRAGMENT
 * RIF. 8 GESTIONE DELLA STAMPA
 *
 */

/*** COSE DA FARE ***
 *
 * Decidere per bene le scritte da mandare a video per l'utente
 *
 * */


/**
 * Schermata di compilazione degli straordinari per Operai
 */

public class OperaioActivity extends AppCompatActivity {

    //Variabili di classe
    /*** RIF. 7 ***/
    /*Variabili per la gesione delle tab*/
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //Icone per le tab
    private int[] tabIcons = {

            R.drawable.ic_today_white_36dp,
            R.drawable.ic_card_travel_white_36dp,
            R.drawable.ic_query_builder_white_36dp

    };
    //Variabile di controllo per l'apertura dell'activity
    private boolean fist = true;
    /*** RIF. 4 ***/
    //Titolo da settare nell'activity
    String titoloActivity = "Operaio Activity";

    /*** RIF. 8 ***/
    //Valori da stampare
    private final int values = 14;
    private String [] printableValues = new String[values];

    //Metodo onCreate per il caricamento delle parti grafiche dell'activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaio);

        /*Controllo per inizializzare l'array delle stringhe per la stampa solo all'apertura
        dell'activity, altrimenti resetta i campi quando si gira lo schermo */
        if(fist){

            inizializzaArrayStringhe();
            fist = false;

        }

        /*** VEDI RIF. 4 ***/
        //Setto il titolo e abilito il tasto indietro digitale nell'ActionBar
        getSupportActionBar().setTitle(titoloActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Collego gli elementi del file activity_operaio.xml alla classe
        FloatingActionButton creaPDF = (FloatingActionButton) findViewById(R.id.FABpdf);

        /*** VEDI RIF. 7 ***/
        //Collego i Fragments all'Activity
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        //Setup della TabBar
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        //Listener per la selezione della TAB
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                //Acquisisco la TAB corrente
                int item_corrente = viewPager.getCurrentItem();

                //Richiamo il metodo per ottenere le stringhe da passare al PDF
                acquistaStringhe(item_corrente, true);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Detector dello swipe da una tab all'altra
        final GestureDetector gesture = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {

                        //Acquisisco la TAB corrente
                        int item_corrente = viewPager.getCurrentItem();

                        //Richiamo il metodo per ottenere le stringhe da passare al PDF
                        acquistaStringhe(item_corrente, true);

                        return super.onFling(e1, e2, velocityX, velocityY);

                    }
                });

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        /*** VEDI RIF. 5 ***/
        //Setto un Listener per gestire la creazione del file PDF
        creaPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int item_corrente = viewPager.getCurrentItem();
                //Richiamo il metodo per creare il file PDF
                stampaDocumento(item_corrente);

            }
        });

    }

    /*** VEDI RIF. 7 ***/
    //Metodo per settare le icone delle tab
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    /*** VEDI RIF. 7 ***/
    //Metodo per settare la descrizione delle tab
    private void setupViewPager(ViewPager viewPager) {
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "DATA");
        adapter.addFragment(new TwoFragment(), "DESCRIZIONE");
        adapter.addFragment(new ThreeFragment(), "ORARIO");
        viewPager.setAdapter(adapter);
    }

    /*****++++++++++++++++++++++++++++++++*/
    /*** CONTINUARE A COMMENTARE DA QUI ***/
    /***++++++++++++++++++++++++++++++++***/

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

    /*** Gestione del tasto indietro fisico ***/
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

    private void inizializzaArrayStringhe(){

        for(int i = 0; i < values; i++) {
            printableValues[i] = "";
        }

    }

    /*** RIF.1 ***/
    /*** Vedi Adapter/MyPrintDocumentAdapter.java ***/
    //Metodo per avviare la stampa di un documento
    private void stampaDocumento(int item_corrente){

        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        String jobName = this.getString(R.string.app_name) + " Document";

        acquistaStringhe(item_corrente, false);

        printManager.print(jobName, new MyPrintDocumentAdapter(this, values, printableValues), null);

    }

    private void acquistaStringheOneFragment(){

        AppCompatSpinner spinnerZona = (AppCompatSpinner) findViewById(R.id.opZonaSpinner);
        AppCompatSpinner spinnerUnitàOperativa = (AppCompatSpinner) findViewById(R.id.opUnitàOperativaSpinner);
        AppCompatSpinner spinnerStraordinarioEffettuato = (AppCompatSpinner) findViewById(R.id.opStraordinarioEffettuatoSpinner);
        DatePicker datePickerGiorno = (DatePicker) findViewById(R.id.opGiornoDatePicker);

        printableValues[0] = spinnerZona.getSelectedItem().toString();
        printableValues[1] = spinnerUnitàOperativa.getSelectedItem().toString();
        printableValues[2] = spinnerStraordinarioEffettuato.getSelectedItem().toString();
        printableValues[3] = Integer.toString(datePickerGiorno.getDayOfMonth());
        printableValues[4] = Integer.toString(datePickerGiorno.getMonth());
        printableValues[5] = Integer.toString(datePickerGiorno.getYear());

    }

    private void acquistaStringheTwoFragment(){

        AppCompatSpinner spinnerOperaio = (AppCompatSpinner) findViewById(R.id.opOperaioSpinner);
        EditText editTextDescrizioneLavoro = (EditText) findViewById(R.id.opDescrizioneLavoroEditText);
        AppCompatSpinner spinnerComune = (AppCompatSpinner) findViewById(R.id.opComuneSpinner);
        AppCompatSpinner spinnerTipoStraordinario = (AppCompatSpinner) findViewById(R.id.opTipoStraordinarioSpinner);

        printableValues[6] = spinnerOperaio.getSelectedItem().toString();
        printableValues[7] = editTextDescrizioneLavoro.getText().toString();
        printableValues[8] = spinnerComune.getSelectedItem().toString();
        printableValues[9] = spinnerTipoStraordinario.getSelectedItem().toString();

    }

    private void acquistaStringheThreeFragment(){

        TimePicker timePickerInizio = (TimePicker) findViewById(R.id.opTimePickerInizio);
        TimePicker timePickerFine = (TimePicker) findViewById(R.id.opTimePickerFine);

        printableValues[10] = formattaOrario(timePickerInizio.getCurrentHour());
        printableValues[11] = formattaOrario(timePickerInizio.getCurrentMinute());
        printableValues[12] = formattaOrario(timePickerFine.getCurrentHour());
        printableValues[13] = formattaOrario(timePickerFine.getCurrentMinute());

    }

    private void acquistaStringhe(int item_corrente, boolean is_swiped){

        try {

            switch (item_corrente) {

                case 0:
                    acquistaStringheOneFragment();
                    break;

                case 1:
                    acquistaStringheTwoFragment();
                    break;

                case 2:
                    acquistaStringheThreeFragment();
                    break;

                default:
                    break;

            }

        }catch (Exception e){
            if(is_swiped)
                Log.e("OperaioActivity.java", "IMPOSSIBILE ACQUISIRE LE STRINGHE DAL FRAGMENT DURANTE LO SWIPE");
            else
                Log.e("OperaioActivity.java", "IMPOSSIBILE ACQUISIRE LE STRINGHE DAL FRAGMENT CON IL FLOATING BUTTON");
        }
    }

    private String formattaOrario(int n){

        if(n < 10)
            return "0" + Integer.toString(n);
        else
            return Integer.toString(n);

    }

}
