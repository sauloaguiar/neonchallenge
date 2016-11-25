package com.sauloaguiar.neonapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sauloaguiar.neonapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sauloaguiar on 11/24/16.
 */

public class SendMoneyContactAdapter extends RecyclerView.Adapter<SendMoneyContactAdapter.ViewHolder> {

    public interface AdapterListenerCallback {
        void itemSelected(int position);
    }


    private String[] contacts;
    private AdapterListenerCallback callback;

    public SendMoneyContactAdapter(String[] contacts, AdapterListenerCallback callback){
        this.contacts = contacts;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_money_row_item, parent, false);
        return new ViewHolder(v, callback);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // update with real data
        //holder.name.setText(contacts[position]);
    }

    @Override
    public int getItemCount() {
        return contacts.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView photo;
        TextView name;
        TextView phone;

        public ViewHolder(final View itemView, final AdapterListenerCallback callback) {
            super(itemView);
            // Define click listener for the ViewHolder's View.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.itemSelected(getAdapterPosition());

                }
            });

            photo = (CircleImageView) itemView.findViewById(R.id.photo);
            name = (TextView) itemView.findViewById(R.id.friendName);
            phone = (TextView) itemView.findViewById(R.id.friendPhone);
        }
    }
}
