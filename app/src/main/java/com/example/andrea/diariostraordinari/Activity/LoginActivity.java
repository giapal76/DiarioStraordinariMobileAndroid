package com.example.andrea.diariostraordinari.Activity;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonAccesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextEmail = (EditText) findViewById(R.id.matricola);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonAccesso = (Button) findViewById(R.id.sign_in_button);

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
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<result_accesso> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
