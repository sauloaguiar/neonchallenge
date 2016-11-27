package com.sauloaguiar.neonapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.data.Transaction;

import java.util.List;

/**
 * Created by sauloaguiar on 11/26/16.
 */

public class GraphHistoryAdapter extends RecyclerView.Adapter<GraphHistoryAdapter.ViewHolder> {

    private List<Transaction> transactions;

    public GraphHistoryAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public GraphHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.graph_line, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GraphHistoryAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
