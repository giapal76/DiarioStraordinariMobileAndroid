package com.example.andrea.diariostraordinari.API;

import com.example.andrea.diariostraordinari.result.result_accesso;
import com.example.andrea.diariostraordinari.result.result_insert;
import com.example.andrea.diariostraordinari.result.result_listaUtenti;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIservice {

    @FormUrlEncoded
    @POST("accesso")
    Call<result_accesso> accessoUtente(@Field("idattore")String idattore, @Field("password")String password);

    @FormUrlEncoded
    @POST("insert")
    Call<result_insert> insertUtente(@Field("idattore")String idattore, @Field("tipo")String tipo, @Field("nome")String nome,
                                     @Field("cognome")String cognome, @Field("password")String password);


    /*** TEST ***
     * CI PROVO
     */

    @GET("listaUtenti")
    Call<result_listaUtenti> getListaUtenti();


}
