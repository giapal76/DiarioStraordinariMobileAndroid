package com.example.andrea.diariostraordinari.result;
import com.example.andrea.diariostraordinari.Adapter.Attore;
import com.example.andrea.diariostraordinari.Adapter.AttoriListAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class result_listaUtenti {
    @SerializedName("utenti")
    @Expose
    private List<Attore> utenti = null;

    public List<Attore> getUtenti() {
        return utenti;
    }

    public void setUtenti(List<Attore> utenti) {
        this.utenti = utenti;
    }
}

