package com.example.andrea.diariostraordinari.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andrea.diariostraordinari.API.APIservice;
import com.example.andrea.diariostraordinari.API.APIurl;
import com.example.andrea.diariostraordinari.Adapter.Attore;
import com.example.andrea.diariostraordinari.Adapter.AttoriListAdapter;
import com.example.andrea.diariostraordinari.R;
import com.example.andrea.diariostraordinari.result.result_listaUtenti;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DBAActivity2 extends AppCompatActivity {

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
        setContentView(R.layout.activity_dba2);

        myView = findViewById(R.id.dbaContainerLayout);
        dbaListView = (ListView) findViewById(R.id.dbaListView);

        //Setto il titolo del menù
        getSupportActionBar().setTitle(titoloActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        //Setto un Listener per gestire la selezione degli elementi nello Spinner
        spinnerAttori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    listaUtenti(getApplicationContext());
                }
                catch (Exception e){
                    Log.e("Exception: ", e.toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        attoriListAdapter = new AttoriListAdapter(this, R.layout.attore_view, new ArrayList<Attore>());
        dbaListView.setAdapter(attoriListAdapter);

    }

    //Metodo per lanciare correttamente una nuova activity
    private void activityStart(Class activity){

        Intent i = new Intent(DBAActivity2.this, activity);
        startActivity(i);


    }

    private void listaUtenti(final Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIurl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice service = retrofit.create(APIservice.class);  //accesso a tutti i servizi che ho fatto

        Call<result_listaUtenti> call = service.getListaUtenti();
        call.enqueue(new Callback<result_listaUtenti>() {
            @Override
            public void onResponse(Call<result_listaUtenti> call, Response<result_listaUtenti> response) {

                List<Attore> list = response.body().getUtenti();
                attoriListAdapter = new AttoriListAdapter(context, R.layout.attore_view, new ArrayList<Attore>(list));
                dbaListView.setAdapter(attoriListAdapter);

            }

            @Override
            public void onFailure(Call<result_listaUtenti> call, Throwable t) {
                Toast.makeText(DBAActivity2.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
