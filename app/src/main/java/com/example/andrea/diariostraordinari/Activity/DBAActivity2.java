package com.example.andrea.diariostraordinari.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

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

public class DBAActivity2 extends AppCompatActivity {

    //View dell'Activity per le notifiche all'utente
    private View myView;

    //ListView e adapter per visualizzare gli utenti in una lista
    ListView dbaListView;
    AttoriListAdapter attoriListAdapter;
    AppCompatSpinner spinnerAttori;

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
                activityStart(Nuovo_utenteActivity.class);

                return false;

            }
        });

        return true;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.MENU_0:
                modifica();
                return true;
            case R.id.MENU_1:
                String my_id = ((Attore) dbaListView.getAdapter().getItem(info.position)).getIdattore();
                elimina(my_id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
        spinnerAttori = (AppCompatSpinner) findViewById(R.id.dbaSelezioneAttoreSpinner);

        /*** TEST ***/
        /*** VEDI RIF. 2 ***/
        //Creo l'adapter per inserire i valori nello spinner
        //Si crea un Array di stringhe per visualizzarle negli spinner
        final ArrayAdapter<CharSequence> adapterAttori = ArrayAdapter.createFromResource(this,
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

                    listaUtenti(getApplicationContext(), false);

                }
                catch (Exception e){

                    Log.e("Exception: ", e.toString());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                try {

                    listaUtenti(getApplicationContext(), true);

                }
                catch (Exception e){
                    Log.e("Exception: ", e.toString());
                }

            }

        });

        registerForContextMenu(dbaListView);

    }

    //Metodo per lanciare correttamente una nuova activity
    private void activityStart(Class activity){

        Intent i = new Intent(DBAActivity2.this, activity);
        startActivity(i);


    }

    private void listaUtenti(final Context context, final boolean no_filter) {

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
                List<Attore> filtered_list = filtraLista(list, no_filter);
                attoriListAdapter = new AttoriListAdapter(context, R.layout.attore_view, new ArrayList<Attore>(filtered_list));
                dbaListView.setAdapter(attoriListAdapter);
                registerForContextMenu(dbaListView);

            }

            @Override
            public void onFailure(Call<result_listaUtenti> call, Throwable t) {
                Toast.makeText(DBAActivity2.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Attore> filtraLista(List<Attore> list, boolean no_filter){

        String filtro = spinnerAttori.getSelectedItem().toString();
        List<Attore> new_list = new ArrayList<Attore>();

        if(!no_filter) {
            for (Attore tmp : list) {

                if (tmp.getTipo().equals(filtro))
                    new_list.add(tmp);

            }
        }

        return new_list;

    }

    private void elimina(final String idattore){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIurl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice service = retrofit.create(APIservice.class);  //accesso a tutti i servizi che ho fatto

        Call<result_delete> call = service.cancellaUtente(idattore);
        call.enqueue(new Callback<result_delete>() {
            @Override
            public void onResponse(Call<result_delete> call, Response<result_delete> response) {
                Log.e("Response", response.body().getMessage());
                Toast.makeText(DBAActivity2.this, idattore + " eliminato", Toast.LENGTH_SHORT).show();
                listaUtenti(getApplicationContext(), false);
            }

            @Override
            public void onFailure(Call<result_delete> call, Throwable t) {
                Toast.makeText(DBAActivity2.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void modifica(){
        Toast.makeText(DBAActivity2.this, "Modifica " + getString(R.string.work_in_progress), Toast.LENGTH_SHORT).show();
    }

}
