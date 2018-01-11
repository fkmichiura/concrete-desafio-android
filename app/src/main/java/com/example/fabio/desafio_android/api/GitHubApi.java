package com.example.fabio.desafio_android.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fabio on 10/01/2018.
 */

public class GitHubApi {

    private static Retrofit retrofit = null;

    //Cria o objeto Retrofit, responsável por realizar o contrato com a API
    public static Retrofit getApiClient(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(GithubService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
