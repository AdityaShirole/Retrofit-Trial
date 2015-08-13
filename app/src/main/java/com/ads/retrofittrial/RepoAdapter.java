package com.ads.retrofittrial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aditya Shirole on 8/13/2015.
 */
public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoHolder> {
    protected Context mContext;
    protected List<Repo> repos;

    public RepoAdapter(Context mContext, List<Repo> repos) {
        this.mContext = mContext;
        this.repos = repos;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_repo,viewGroup,false);
        return new RepoHolder(v);
    }

    @Override
    public void onBindViewHolder(RepoHolder repoHolder, int position) {
        repoHolder.repoName.setText(repos.get(position).getName());
        repoHolder.repoDescription.setText(repos.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        protected TextView repoName;
        protected TextView repoDescription;
        public RepoHolder(View itemView) {
            super(itemView);
            repoName = (TextView) itemView.findViewById(R.id.tv_repo_name);
            repoDescription = (TextView) itemView.findViewById(R.id.tv_repo_description);
        }
    }
}
