package com.example.fabio.desafio_android;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fabio.desafio_android.api.GitHubApi;
import com.example.fabio.desafio_android.api.GithubService;
import com.example.fabio.desafio_android.utils.PullRequestAdapter;
import com.example.fabio.desafio_android.models.Pull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PullActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.pull_recyclerview);
        callPullRequests();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callPullRequests(){

        //Recupera dados de "user" e "repository"
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user");
        String repository = bundle.getString("repository");

        //Faz a chamada do serviço para requisição das Pull Requests
        GithubService service = GitHubApi.getApiClient().create(GithubService.class);
        Call<List<Pull>> requestPulls = service.listPullRequests(user, repository);
        requestPulls.enqueue(new Callback<List<Pull>>() {
            @Override
            public void onResponse(Call<List<Pull>> call, Response<List<Pull>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(PullActivity.this, "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                }
                else {
                    List<Pull> pulls = response.body();

                    recyclerView.setAdapter(new PullRequestAdapter(PullActivity.this, pulls));
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                            PullActivity.this,
                            LinearLayoutManager.VERTICAL,
                            false);
                    recyclerView.setLayoutManager(layoutManager);

                    ProgressBar mainProgressBar = findViewById(R.id.pull_progressbar);
                    mainProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Pull>> call, Throwable t) {
                Toast.makeText(PullActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
