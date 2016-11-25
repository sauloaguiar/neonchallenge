package com.sauloaguiar.neonapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.adapters.SendMoneyContactAdapter;

/**
 * Created by sauloaguiar on 11/24/16.
 */
public class SendMoneyFragment extends Fragment {

    private LinearLayoutManager mLayoutManager;


    public static SendMoneyFragment newInstance(){
        return new SendMoneyFragment();
    }

    public SendMoneyFragment(){}

    protected RecyclerView recyclerView;
    protected SendMoneyContactAdapter madapter;
    protected String[] contacts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_send_money, container, false);
        initDataset();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        
        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        madapter = new SendMoneyContactAdapter(contacts, new SendMoneyContactAdapter.AdapterListenerCallback() {
            @Override
            public void itemSelected(int position) {
                showDialog(position);
            }
        });

        recyclerView.setAdapter(madapter);
        return rootView;
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        contacts = new String[20];
        for (int i = 0; i < 20; i++) {
            contacts[i] = "This is element #" + i;
        }

    }

    private void showDialog(int position){
        Bundle b = new Bundle();
        b.putInt("position", position);

        FragmentManager fm = getFragmentManager();
        SendMoneyDialogFragment dialogFragment = new SendMoneyDialogFragment();
        dialogFragment.setArguments(b);
        dialogFragment.show(fm, "Sample Fragment");
    }
}

