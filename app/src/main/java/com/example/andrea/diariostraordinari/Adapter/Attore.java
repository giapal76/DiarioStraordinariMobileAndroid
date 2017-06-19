package com.example.andrea.diariostraordinari.Adapter;

/**
 * Created by Andrea on 25/04/17.
 */


/**
 * Classe Attore per definire la struttura di un oggetto Attore nel DB
 */

public class Attore {

    //Variabili di classe
    private String idattore;
    private String tipo;
    private String nome;
    private String cognome;
    private String password;


    //Metodo ostruttore vuoto richiesto di Default per il cast da parte del DB Firebase
    public Attore(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    //Metodo costruttore
    public Attore(String idattore, String tipo, String nome, String cognome, String password){

        this.idattore = idattore;
        this.tipo = tipo;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;

    }

    //Metodi getter per le proprietà degli oggetti Attore
    public String getIdattore() {
        return idattore;
    }

    public String getTipo() { return tipo; }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getPassword() {
        return password;
    }

}

