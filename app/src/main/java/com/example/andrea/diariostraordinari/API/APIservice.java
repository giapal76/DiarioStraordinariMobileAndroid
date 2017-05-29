package com.example.andrea.diariostraordinari.API;

import com.example.andrea.diariostraordinari.result.result_accesso;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIservice {

    @FormUrlEncoded
    @POST("accesso")
    Call<result_accesso> accessoUtente(@Field("idattore")String idattore, @Field("password")String password);
}
