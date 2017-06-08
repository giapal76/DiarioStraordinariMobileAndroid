package com.example.andrea.diariostraordinari.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrea.diariostraordinari.R;
import com.example.andrea.diariostraordinari.result.result_accesso;
import com.example.andrea.diariostraordinari.API.APIservice;
import com.example.andrea.diariostraordinari.API.APIurl;
import com.example.andrea.diariostraordinari.Adapter.Attore;
import android.support.design.widget.Snackbar;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonAccesso;
    private View loginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.matricola);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonAccesso = (Button) findViewById(R.id.sign_in_button);
        loginFormView = findViewById(R.id.login_form);

        buttonAccesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accesso();
            }

            private void accesso() {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIurl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIservice service = retrofit.create(APIservice.class);  //accesso a tutti i servizi che ho fatto

                Call<result_accesso> call = service.accessoUtente(email, password);
                call.enqueue(new Callback<result_accesso>() {
                    @Override
                    public void onResponse(Call<result_accesso> call, Response<result_accesso> response) {
                    apriNuovaSchermata(response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<result_accesso> call, Throwable t) {
                        Snackbar.make(loginFormView, getString(R.string.user_not_found), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });

    }

    //Metodo per aprire la schermata adatta dell'app in base al tipo di user
    private void apriNuovaSchermata(String tipoUser) {



        if (tipoUser.equals(getString(R.string.user_developer)) || tipoUser.equals(getString(R.string.user_DBA))) {

            finish();
            activityStart(DBAActivity.class);
        }

        else if (tipoUser.equals(getString(R.string.user_operaio))) {
            finish();
            /*** TEST ***/
            /*** RIF. 6 ***/
            //Provo i fragments
            //activityStart(OperaioActivity.class);
            activityStart(OperaioActivity2.class);
        }

        else {

            Log.e(getString(R.string.log_e_DB_read), getString(R.string.user_error));
            Log.e( getString(R.string.log_e_DB_read), "TIPO USER RILEVATO: " + tipoUser);
            Snackbar.make(loginFormView, getString(R.string.error_DB_read), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

    }

    //Metodo per lanciare correttamente una nuova Activity
    private void activityStart(Class activity){

        Intent i = new Intent(LoginActivity.this, activity);
        startActivity(i);


    }

}
