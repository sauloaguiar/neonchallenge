package com.sauloaguiar.neonapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.adapters.GraphHistoryAdapter;
import com.sauloaguiar.neonapplication.adapters.SendHistoryAdapter;
import com.sauloaguiar.neonapplication.data.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sauloaguiar on 11/26/16.
 */

public class HistoryFragment extends Fragment {

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    public HistoryFragment(){}

    private RecyclerView graphRecyclerView;
    private LinearLayoutManager mGraphLayoutManager;
    private GraphHistoryAdapter mGraphAdapter;

    private RecyclerView listRecyclerView;
    private LinearLayoutManager mListLayoutManager;
    private SendHistoryAdapter mHistoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_money, container, false);

        List<Transaction> transactions = generateTransactions();

        graphRecyclerView = (RecyclerView) rootView.findViewById(R.id.graphRecyclerView);
        mGraphLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false);
        graphRecyclerView.setLayoutManager(mGraphLayoutManager);
        mGraphAdapter = new GraphHistoryAdapter(transactions);
        graphRecyclerView.setAdapter(mGraphAdapter);

        listRecyclerView = (RecyclerView) rootView.findViewById(R.id.friendListRecyclerView);
        mListLayoutManager = new LinearLayoutManager(getActivity());
        listRecyclerView.addItemDecoration(new DividerItemDecoration(listRecyclerView.getContext(), mListLayoutManager.getOrientation()));
        listRecyclerView.setLayoutManager(mListLayoutManager);
        mHistoryAdapter = new SendHistoryAdapter(transactions);
        listRecyclerView.setAdapter(mHistoryAdapter);

        return rootView;
    }


    private List<Transaction> generateTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        int tid = 1;
        String userid = "1";
        Transaction t = new Transaction(String.valueOf(tid), userid, 15.00, "123123");
        transactions.add(t);
        tid++;

        t = new Transaction(String.valueOf(tid), "2", 30.00, "123123");
        transactions.add(t);
        tid++;

        t = new Transaction(String.valueOf(tid), "1", 45.00, "123123");
        transactions.add(t);
        tid++;

        return transactions;
    }
}
