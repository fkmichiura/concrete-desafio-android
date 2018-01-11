package com.example.fabio.desafio_android.api;

import com.example.fabio.desafio_android.models.GitHubCatalog;
import com.example.fabio.desafio_android.models.Pull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Fabio on 05/01/2018.
 */

public interface GithubService {

    String BASE_URL = "https://api.github.com/";

    //Chamada para listar os repositórios
    @GET("search/repositories")
    Call<GitHubCatalog> listRepositories(
            @Query("q") String language,
            @Query("sort") String sort,
            @Query("page") int page);

    //Chamada para listar as Pull Requests de um determinado repositório de um usuário
    @GET("repos/{user}/{repository}/pulls?state=all")
    Call<List<Pull>> listPullRequests(
            @Path("user") String user,
            @Path("repository") String repository);
}
