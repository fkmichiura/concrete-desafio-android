package com.example.fabio.desafio_android;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fabio.desafio_android.api.GitHubApi;
import com.example.fabio.desafio_android.api.GithubService;
import com.example.fabio.desafio_android.utils.MainAdapter;
import com.example.fabio.desafio_android.utils.PaginationScrollListener;
import com.example.fabio.desafio_android.models.GitHubCatalog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private GithubService service;

    private ProgressBar progressBar;

    private static final int PAGE_START = 1;
    private int CURRENT_PAGE = PAGE_START;
    private int TOTAL_PAGES = 34; //Número total de páginas de repositórios de Java
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recyclerview);
        progressBar = findViewById(R.id.main_progressbar);

        adapter = new MainAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                CURRENT_PAGE += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public boolean isLoading() {
                return isLastPage;
            }

            @Override
            public boolean isLastPage() {
                return isLoading;
            }
        });

        //Inicialização do serviço e recuperação dos dados da API
        service = GitHubApi.getApiClient().create(GithubService.class);
        loadFirstPage();
    }

    private Call<GitHubCatalog> callRepositoriesAPI(){
        return service.listRepositories("language:Java", "stars", CURRENT_PAGE);
    }

    //Realiza o consumo dos dados da API para mostrar na página inicial
    private void loadFirstPage(){

        callRepositoriesAPI().enqueue(new Callback<GitHubCatalog>() {
            @Override
            public void onResponse(Call<GitHubCatalog> call, Response<GitHubCatalog> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Código de erro: " + response.code(), Toast.LENGTH_LONG).show();
                }
                else {
                    GitHubCatalog repositories = response.body();
                    progressBar.setVisibility(View.GONE);

                    if(CURRENT_PAGE <= TOTAL_PAGES)
                        adapter.addAll(repositories);
                    else
                        isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<GitHubCatalog> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //Realiza o consumo dos dados da API para mostrar as próximas páginas
    private void loadNextPage(){

        callRepositoriesAPI().enqueue(new Callback<GitHubCatalog>() {
            @Override
            public void onResponse(Call<GitHubCatalog> call, Response<GitHubCatalog> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                }
                else {
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    GitHubCatalog repositories = response.body();
                    adapter.addAll(repositories);

                    if(CURRENT_PAGE <= TOTAL_PAGES)
                        adapter.addLoadingFooter();
                    else
                        isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<GitHubCatalog> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
