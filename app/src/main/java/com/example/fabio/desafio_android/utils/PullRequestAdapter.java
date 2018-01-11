package com.example.fabio.desafio_android.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fabio.desafio_android.models.Pull;
import com.example.fabio.desafio_android.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Fabio on 09/01/2018.
 */

public class PullRequestAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Pull> pulls;

    public PullRequestAdapter(Context context, List<Pull> users) {
        this.context = context;
        this.pulls = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pull_layout, parent, false);
        return new PullRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PullRequestViewHolder viewHolder = (PullRequestViewHolder) holder;
        Pull pull = pulls.get(position);
        Uri uri = Uri.parse(pull.getUser().getAvatar_url());
        Context context = viewHolder.pullAvatar.getContext();

        viewHolder.pullAuthorName.setText(pull.getUser().getLogin());
        viewHolder.pullTitle.setText(pull.getTitle());
        viewHolder.pullDate.setText(pull.getCreated_at());
        viewHolder.pullBody.setText(pull.getBody());
        //Imagem do avatar por URL
        Picasso.with(context).load(uri).into(viewHolder.pullAvatar);
    }

    @Override
    public int getItemCount() {
        return pulls.size();
    }

    private class PullRequestViewHolder extends RecyclerView.ViewHolder {

        private TextView pullAuthorName;
        private TextView pullTitle;
        private TextView pullDate;
        private TextView pullBody;
        private ImageView pullAvatar;

        private PullRequestViewHolder(View itemView) {
            super(itemView);

            pullAuthorName = itemView.findViewById(R.id.tv_pull_username);
            pullTitle = itemView.findViewById(R.id.tv_pull_title);
            pullDate = itemView.findViewById(R.id.tv_pull_date);
            pullBody = itemView.findViewById(R.id.tv_pull_body);
            pullAvatar = itemView.findViewById(R.id.iv_pull_author);
        }
    }
}
