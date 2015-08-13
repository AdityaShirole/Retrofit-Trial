package com.ads.retrofittrial;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Aditya Shirole on 8/13/2015.
 */
public interface GithubService  {


    @GET("/users/{user}/repos")
    public void listRepos(@Path("user") String user, Callback<List<Repo>> response);
}
