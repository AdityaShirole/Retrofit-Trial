package com.ads.retrofittrial;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    protected Toolbar toolbar;
    protected EditText username;
    protected RecyclerView reposList;
    protected List<Repo> mRepos;
    protected RepoAdapter repoAdapter;
    protected GithubService githubApi;
    protected ProgressBar progressBar;
    protected TextView tvFetchingText;

    protected TextView btFetch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Retrofit Trial");
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        reposList = (RecyclerView) findViewById(R.id.list_repo);
        username = (EditText) findViewById(R.id.et_username);
        btFetch = (TextView) findViewById(R.id.bt_fetch);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvFetchingText = (TextView) findViewById(R.id.tv_fetching_text);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reposList.setLayoutManager(layoutManager);
        mRepos = new ArrayList<>();

        repoAdapter = new RepoAdapter(this,mRepos);
        reposList.setAdapter(repoAdapter);



        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();


        githubApi = restAdapter.create(GithubService.class);


        btFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepos.clear();
                reposList.getAdapter().notifyDataSetChanged();
                progressBar.setVisibility(View.VISIBLE);
                tvFetchingText.setVisibility(View.VISIBLE);
                getReposOfUser(githubApi);
            }
        });



    }

    private void getReposOfUser(GithubService githubApi) {
        githubApi.listRepos(username.getText().toString(),new Callback<List<Repo>>() {
            @Override
            public void success(List<Repo> repos, Response response) {
                progressBar.setVisibility(View.INVISIBLE);
                tvFetchingText.setVisibility(View.INVISIBLE);
                for(Repo repo : repos) {
                    Log.i("REPO NAME", repo.getName() + " : " + repo.getDescription());
                }
                mRepos = repos;
                if(reposList.getAdapter() == null) {
                    repoAdapter = new RepoAdapter(MainActivity.this,mRepos);
                    reposList.setAdapter(repoAdapter);
                } else {
                    repoAdapter = new RepoAdapter(MainActivity.this,mRepos);
                    reposList.setAdapter(repoAdapter);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
                tvFetchingText.setVisibility(View.INVISIBLE);
                Log.e("ERROR",error.getMessage());
                Toast.makeText(MainActivity.this,"Error : " + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
