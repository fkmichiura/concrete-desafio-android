package com.example.fabio.desafio_android.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Fabio on 10/01/2018.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener{
    
    private LinearLayoutManager manager;

    public PaginationScrollListener(LinearLayoutManager manager) {
        this.manager = manager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        
        int visItemCount = manager.getChildCount();
        int totalItemCount = manager.getItemCount();
        int firstVisItemPosition = manager.findFirstVisibleItemPosition();

        if(!isLoading() && !isLastPage()){
            if((visItemCount + firstVisItemPosition) >= totalItemCount
                    && firstVisItemPosition >= 0){
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();
    public abstract boolean isLoading();
    public abstract boolean isLastPage();
}
