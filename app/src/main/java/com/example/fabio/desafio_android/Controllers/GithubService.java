package com.example.fabio.desafio_android.Controllers;

import com.example.fabio.desafio_android.Models.GithubCatalog;
import com.example.fabio.desafio_android.Models.Pull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Fabio on 05/01/2018.
 */

public interface GithubService {

    String BASE_URL = "https://api.github.com/";

    @GET
    Call<GithubCatalog> listRepositories(@Url String url);

    @GET("repos/{user}/{repository}/pulls?state=all")
    Call<List<Pull>> listPullRequests(
            @Path("user") String user,
            @Path("repository") String repository);
}
