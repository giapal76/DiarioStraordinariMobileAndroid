package com.example.andrea.diariostraordinari.Adapter;

/**
 * Created by Andrea on 25/04/17.
 */

public class Attore {

    private String idattore;
    private String nome;
    private String cognome;
    private String password;


    public Attore(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Attore(String nome, String cognome, String password){
        this.idattore = idattore;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
    }

    public String getIdattore() {
        return idattore;
    }

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
