package com.sauloaguiar.neonapplication.adapters;

import android.animation.ValueAnimator;
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
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sauloaguiar on 11/26/16.
 */

public class GraphHistoryAdapter extends RecyclerView.Adapter<GraphHistoryAdapter.ViewHolder> {

    private final int height;
    private final List<Friend> friends;
    private List<Transaction> transactions;
    private double maxValue = 0;
    private NumberFormat brazilianRealFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public GraphHistoryAdapter(List<Transaction> transactions, List<Friend> friends, int viewHeight) {
        this.transactions = transactions;
        this.height = viewHeight;
        this.friends = friends;
        orderTransactions();
    }

    private void orderTransactions() {
        Collections.sort(transactions);
        maxValue = transactions.get(0).getValor();
    }

    private int calculateHeightForValue(double value) {
        return  ((int)(height * value))/(int)maxValue;
    }

    @Override
    public GraphHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.graph_line, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GraphHistoryAdapter.ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.value.setText(brazilianRealFormat.format(transaction.getValor()));

        int valueHeight = calculateHeightForValue(transaction.getValor());

        ValueAnimator va = ValueAnimator.ofInt(10, valueHeight);
        va.setDuration(1800);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                holder.line.getLayoutParams().height = value.intValue();
                holder.line.requestLayout();
            }
        });
        va.start();

        ValueAnimator textValue = ValueAnimator.ofFloat(0, (float) transaction.getValor());
        textValue.setDuration(1800);
        textValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String value = (String) animation.getAnimatedValue();
                holder.value.setText("" + value);
                holder.value.requestLayout();
            }
        });

        Friend f = getFriendById(transaction.getClientId());
        if (f != null || f.getImageResource() != -1) {
            holder.photo.setImageResource(f.getImageResource());
        } else {
            // put generated image
            TextDrawable drawable2 = TextDrawable.builder()
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
        TextView value;
        View ring;
        View line;
        CircleImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);

            value = (TextView) itemView.findViewById(R.id.value);
            ring = itemView.findViewById(R.id.ring);
            line = itemView.findViewById(R.id.line);
            photo = (CircleImageView) itemView.findViewById(R.id.friendPhoto);
        }
    }
}
