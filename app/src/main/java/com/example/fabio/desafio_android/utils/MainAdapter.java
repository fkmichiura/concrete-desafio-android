package com.example.fabio.desafio_android.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fabio.desafio_android.models.GitHubCatalog;
import com.example.fabio.desafio_android.models.Item;
import com.example.fabio.desafio_android.PullActivity;
import com.example.fabio.desafio_android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabio on 08/01/2018.
 */

public class MainAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Item> repositories;

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    public MainAdapter(Context context) {
        this.context = context;
        repositories = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View loadingView = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(loadingView);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view1 = inflater.inflate(R.layout.custom_layout, parent, false);
        viewHolder = new MainViewHolder(view1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {

            case ITEM:
                MainViewHolder viewHolder = (MainViewHolder) holder;
                Item item = repositories.get(position);

                Uri uri = Uri.parse(item.getOwner().getAvatar_url());
                Context context = viewHolder.avatar.getContext();
                viewHolder.bind(item);

                viewHolder.repositoryName.setText(item.getFull_name());
                viewHolder.description.setText(item.getDescription());
                viewHolder.forks.setText(String.valueOf(item.getForks_count()));
                viewHolder.stars.setText(String.valueOf(item.getStargazers_count()));
                viewHolder.username.setText(item.getName());
                viewHolder.fullname.setText(item.getFull_name());

                //Imagem do avatar por URL
                Picasso.with(context).load(uri).into(viewHolder.avatar);
                break;

            case LOADING:
                LoadingViewHolder loadingVH = (LoadingViewHolder) holder;

                if (retryPageLoad) {
                    loadingVH.progressBar.setVisibility(View.GONE);

                } else {
                    loadingVH.progressBar.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return repositories == null ? 0 : repositories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == repositories.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    private class MainViewHolder extends RecyclerView.ViewHolder {

        private TextView repositoryName;
        private TextView description;
        private TextView forks;
        private TextView stars;
        private TextView username;
        private TextView fullname;
        private ImageView avatar;

        private MainViewHolder(View itemView) {
            super(itemView);

            repositoryName = itemView.findViewById(R.id.tv_rep_title);
            description = itemView.findViewById(R.id.tv_rep_descr);
            forks = itemView.findViewById(R.id.tv_fork_number);
            stars = itemView.findViewById(R.id.tv_star_number);
            username = itemView.findViewById(R.id.tv_rep_username);
            fullname = itemView.findViewById(R.id.tv_rep_fullname);
            avatar = itemView.findViewById(R.id.iv_rep_author);

        }

        private void bind(final Item item) {

            itemView.findViewById(R.id.main_cardview);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
                    Intent intent = new Intent(context, PullActivity.class);
                    intent.putExtra("user", item.getOwner().getLogin());
                    intent.putExtra("repository", item.getName());
                    context.startActivity(intent);
                }
            });
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        private LoadingViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.loadmore_progress);
        }
    }

    private void add(Item i) {
        repositories.add(i);
        notifyItemChanged(repositories.size() - 1);
    }

    public void addAll(GitHubCatalog repositories){
        for(Item i : repositories.items){
            add(i);
        }
    }

    public void addLoadingFooter(){
        isLoadingAdded = true;
        add(new Item());
    }

    public void removeLoadingFooter(){
        isLoadingAdded = false;

        int position = repositories.size() - 1;
        Item item = getItem(position);

        if(item != null){
            repositories.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Item getItem(int position) {
        return repositories.get(position);
    }
}
