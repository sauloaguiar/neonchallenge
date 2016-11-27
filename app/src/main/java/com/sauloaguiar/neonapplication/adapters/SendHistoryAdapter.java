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

public class SendHistoryAdapter extends RecyclerView.Adapter<SendHistoryAdapter.ViewHolder> {

    private final List<Transaction> transactions;

    public SendHistoryAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public SendHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row_item, parent, false);
        return new SendHistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SendHistoryAdapter.ViewHolder holder, int position) {

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
