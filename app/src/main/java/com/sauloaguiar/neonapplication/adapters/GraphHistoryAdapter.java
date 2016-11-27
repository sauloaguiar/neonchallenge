package com.sauloaguiar.neonapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.data.Transaction;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        Transaction t = transactions.get(position);
        //holder.photo.setImageResource(t.get);
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(15, 15, 0, 0);
        holder.value.setText(String.valueOf(t.getValor()));
        holder.value.setLayoutParams(textParams);
//        holder.value.setPadding(0,0,0, 150);
//        holder.value.invalidate();
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.container.getLayoutParams();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 200);
        params.setMargins(15, 0, 0, 150);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        holder.line.setLayoutParams(params);
        //holder.line.invalidate();
        //holder.line.setPadding(10, 0, 0, 48);
        //holder.line.

        RelativeLayout.LayoutParams ringParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ringParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        ringParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        //ringParams.setMargins(0, 0, 0, 1);
        holder.ring.setLayoutParams(ringParams);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView value;
        View ring;
        View line;
        CircleImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView.findViewById(R.id.container);
            value = (TextView) itemView.findViewById(R.id.value);
            ring = (View) itemView.findViewById(R.id.ring);
            line = (View) itemView.findViewById(R.id.line);
            photo = (CircleImageView) itemView.findViewById(R.id.photo);

        }
    }
}
