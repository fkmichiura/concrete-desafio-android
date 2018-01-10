package com.example.fabio.desafio_android;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fabio.desafio_android.Controllers.GitHubApi;
import com.example.fabio.desafio_android.Controllers.GithubService;
import com.example.fabio.desafio_android.Controllers.PullRequestAdapter;
import com.example.fabio.desafio_android.Models.Pull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PullActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.pull_recyclerview);

        GithubService service = GitHubApi.getApiClient().create(GithubService.class);
        Call<List<Pull>> requestPulls = service.listPullRequests("ReactiveX", "RxJava");
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
                }
            }

            @Override
            public void onFailure(Call<List<Pull>> call, Throwable t) {
                Toast.makeText(PullActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
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
}
