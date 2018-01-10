package com.example.fabio.desafio_android.Controllers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fabio.desafio_android.Models.Item;
import com.example.fabio.desafio_android.PullActivity;
import com.example.fabio.desafio_android.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Fabio on 08/01/2018.
 */

public class MainAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Item> repositories;

    public MainAdapter(Context context, List<Item> repositories) {
        this.context = context;
        this.repositories = repositories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainViewHolder viewHolder = (MainViewHolder) holder;
        Item item = repositories.get(position);
        Uri uri = Uri.parse(item.getOwner().getAvatar_url());
        Context context = viewHolder.avatar.getContext();

        viewHolder.repositoryName.setText(item.getFull_name());
        viewHolder.description.setText(item.getDescription());
        viewHolder.forks.setText(String.valueOf(item.getForks_count()));
        viewHolder.stars.setText(String.valueOf(item.getStargazers_count()));
        viewHolder.username.setText(item.getName());
        viewHolder.fullname.setText(item.getFull_name());
        //Imagem do avatar por URL
        Picasso.with(context).load(uri).into(viewHolder.avatar);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView repositoryName;
        public TextView description;
        public TextView forks;
        public TextView stars;
        public TextView username;
        public TextView fullname;
        public ImageView avatar;

        public MainViewHolder(View itemView) {
            super(itemView);

            repositoryName = itemView.findViewById(R.id.tv_rep_title);
            description = itemView.findViewById(R.id.tv_rep_descr);
            forks = itemView.findViewById(R.id.tv_fork_number);
            stars = itemView.findViewById(R.id.tv_star_number);
            username = itemView.findViewById(R.id.tv_rep_username);
            fullname = itemView.findViewById(R.id.tv_rep_fullname);
            avatar = itemView.findViewById(R.id.iv_rep_author);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, PullActivity.class);
            context.startActivity(intent);
        }
    }

}
