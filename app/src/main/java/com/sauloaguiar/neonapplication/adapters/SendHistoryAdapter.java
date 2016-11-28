package com.sauloaguiar.neonapplication.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.data.Friend;
import com.sauloaguiar.neonapplication.data.Transaction;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sauloaguiar on 11/26/16.
 */

public class SendHistoryAdapter extends RecyclerView.Adapter<SendHistoryAdapter.ViewHolder> {

    private final List<Transaction> transactions;
    private final List<Friend> friends;
    private NumberFormat brazilianRealFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public SendHistoryAdapter(List<Transaction> transactions, List<Friend> friends) {
        this.transactions = transactions;
        this.friends = friends;
    }

    @Override
    public SendHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row_item, parent, false);
        return new SendHistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SendHistoryAdapter.ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        Friend friend = getFriendById(transaction.getClientId());
        holder.name.setText(friend.getName());
        holder.number.setText(friend.getPhone());
        holder.value.setText(brazilianRealFormat.format(transaction.getValor()));

        Friend f = getFriendById(transaction.getClientId());
        if (f.getImageResource() != -1) {
            holder.photo.setImageResource(f.getImageResource());
        } else {
            // put generated image
            TextDrawable drawable2 = TextDrawable.builder()
                    .beginConfig().height(60).width(60).endConfig()
                    .buildRound(String.valueOf(f.getName().charAt(0)), Color.TRANSPARENT);
            holder.photo.setImageDrawable(drawable2);
        }
    }

    private Friend getFriendById(String id) {
        for(Friend f : friends) {
            if(f.getStringId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView number;
        TextView value;
        CircleImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.friendName);
            number = (TextView) itemView.findViewById(R.id.friendPhone);
            value = (TextView) itemView.findViewById(R.id.friendTransactionValue);
            photo = (CircleImageView) itemView.findViewById(R.id.friendPhoto);
        }
    }
}
