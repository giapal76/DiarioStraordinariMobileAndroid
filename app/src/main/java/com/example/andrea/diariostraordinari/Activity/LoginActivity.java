package com.example.andrea.diariostraordinari.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andrea.diariostraordinari.Adapter.Attore;
import com.example.andrea.diariostraordinari.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

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
 * VEDI RIF. 5
 *
 * *** TEST ***
 *
 * VEDI RIF. 6
 *
 */

/**
 * Schermata di login
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    //Potrebbe non servire
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    // Oggetto FirebaseDatabase per comunicare con il DB
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private UserLoginTask authTask = null;
    // UI references.
    // Referenze per la parte grafica presente in activity_login.xml
    private AutoCompleteTextView matricolaView;
    private EditText passwordView;
    private View progressView;
    private View loginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Setup del form per il login
        matricolaView = (AutoCompleteTextView) findViewById(R.id.matricola);
        /*** INIBITO ***/
        /*** RIF. 1 ***/
        // populateAutoComplete();

        passwordView = (EditText) findViewById(R.id.password);
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                //Controlla se è stato premuto il Button sing_in_button oppure Invio da tastiera (IME_NULL)
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);

    }

    //Popolamento automatico del campo matricola
    /*** VEDI RIF. 1 ***/

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    /*** VEDI RIF. 1 ***/
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(matricolaView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    // Metodo per il controllo degli errori di inserimento
    private void attemptLogin() {
        if (authTask != null) {
            return;
        }

        // Reset errors.
        matricolaView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String matricola = matricolaView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        /*** INIBITO ***/
        /*** RIF. 2 ***/
        /*** E' stato cancellato il controllo sull'immissione delle email presente di default ***/

        // Controlla se la password inserita è corretta
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Mostra uno spinner di caricamento mentre controlla le credenziali
            showProgress(true);
            // Si effettua il login
            authTask = new UserLoginTask(matricola, password);
            authTask.execute((Void) null);
        }
    }

    /*** INIBITO ***/
    /*** VEDI RIF. 2 ***/
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    /*** RIF. 5 ***/
    /* Metodo molto importante, qui è possibile effetturare i controlli sulla validità della password */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     *
     * Interfaccia di caricamento autogenerata
     *
     * Shows the progress UI and hides the login form.
     *
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /*** INIBITO ***/
    /*** VEDI RIF. 2 ***/
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        matricolaView.setAdapter(adapter);
    }

    //Metodo per aprire la schermata adatta dell'app in base al tipo di user
    private void apriNuovaSchermata(String tipoUser) {

        finish();

        if (tipoUser.equals(getString(R.string.user_developer)) || tipoUser.equals(getString(R.string.user_DBA)))

            activityStart(DBAActivity.class);

        else if (tipoUser.equals(getString(R.string.user_operaio)))

            /*** TEST ***/
            /*** RIF. 6 ***/
            //Provo i fragments
            //activityStart(OperaioActivity.class);
            activityStart(OperaioActivity2.class);

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

    // Gestione del tasto indietro
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

    /*** VEDI RIF. 3 ***/
    //Metodo per verificare le credenziali inserite nel DB
    private void controllaCredenziali(String userId, String userPassword, DataSnapshot dataSnapshot) {

        String user_tmp = "";

        String tabella = getString(R.string.table_firebase);
        String tipoAttore = "";

        DataSnapshot filtro_tipo_attore;

        int i = 0;
        boolean trovato_user = false;

        while((!trovato_user) && (i<3)) {

            //Itero per cercare in tutti i tipi attore
            switch (i){

                case 0:
                    tipoAttore = getString(R.string.user_developer);
                    break;
                case 1:
                    tipoAttore = getString(R.string.user_DBA);
                    break;
                case 2:
                    tipoAttore = getString(R.string.user_operaio);
                    break;

            }

            //Assegno il filtro in base al tipo attore selezionato
            filtro_tipo_attore = dataSnapshot.child(tabella).child(tipoAttore);

            //Tento una connessione ad DB
            try {

                //Itero tra tutti gli utenti dello stesso tipo per cercare l'utente
                for (DataSnapshot snapshot : filtro_tipo_attore.getChildren()) {

                    //Confronto la matricola inserita con l'Id selezionato
                    user_tmp = snapshot.getKey();
                    trovato_user = user_tmp.equals(userId);

                    //Procedo alla verifica della password se l'id corrisponde
                    if (trovato_user) {
                        String pass = snapshot.getValue(Attore.class).getPassword();
                        //Confronto la password inserita con quella presente nel DB
                        if (pass.equals(userPassword)) {
                            //Avvio il metodo per la schermata adatta al tipo utente
                            apriNuovaSchermata(tipoAttore);
                        } else utenteNonTrovato();
                        break;
                    }

                }

            } catch (Exception e) {

                //Rileva un errore di connessione e lo manifesta all'utente
                Log.e(getString(R.string.log_e_DB_read), getString(R.string.error_DB_read));
                Snackbar.make(loginFormView, getString(R.string.error_DB_read), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

            i++;

        }

        if(!trovato_user) utenteNonTrovato();

    }

    //Metodo per informare l'utente che il login non è stato effettuato per incorrettezza dei dati inseriti
    void utenteNonTrovato(){

        Snackbar.make(loginFormView, getString(R.string.user_not_found), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String myMatricola;
        private final String myPassword;

        UserLoginTask(String matricola, String password) {
            myMatricola = matricola;
            myPassword = password;
        }

        //Qui andrebbe testata la connessione ad internet
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            // TODO: register the new account here.
            return true;
        }

        /*** RIF. 3 ***/
        //Qui connetto l'app al DB Firebase per verificare le credenziali per il login
        @Override
        protected void onPostExecute(final Boolean success) {

            authTask = null;
            showProgress(false);

            //Connetto il DB Firebase
            DatabaseReference mDatabaseRef;

            mDatabaseRef = database.getReference();
            mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (success) {
                        //Avvio il metodo per la ricerca delle credenziali
                        controllaCredenziali(myMatricola, myPassword, dataSnapshot);
                    } else {
                        passwordView.setError(getString(R.string.error_incorrect_password));
                        passwordView.requestFocus();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        @Override
        protected void onCancelled() {
            authTask = null;
            showProgress(false);
        }
    }

}
