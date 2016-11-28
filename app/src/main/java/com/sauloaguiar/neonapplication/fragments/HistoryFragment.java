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
import com.sauloaguiar.neonapplication.data.Friend;
import com.sauloaguiar.neonapplication.data.FriendRepositories;
import com.sauloaguiar.neonapplication.data.FriendsRepository;
import com.sauloaguiar.neonapplication.data.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sauloaguiar on 11/26/16.
 */

public class HistoryFragment extends Fragment {

    private List<Friend> contacts;

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
        initDataset();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final List<Transaction> transactions = groupTransactions(generateTransactions());

        graphRecyclerView = (RecyclerView) view.findViewById(R.id.graphRecyclerView);
        mGraphLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false);
        graphRecyclerView.setLayoutManager(mGraphLayoutManager);
        graphRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                int height = graphRecyclerView.getHeight() - (int) getResources().getDimension(R.dimen.graph_friend_photo) - (2 * (int) getResources().getDimension(R.dimen.grid_line_space));
                mGraphAdapter = new GraphHistoryAdapter(transactions, contacts, height);
                graphRecyclerView.setAdapter(mGraphAdapter);
            }
        });

        listRecyclerView = (RecyclerView) view.findViewById(R.id.friendListRecyclerView);
        mListLayoutManager = new LinearLayoutManager(getActivity());
        listRecyclerView.addItemDecoration(new DividerItemDecoration(listRecyclerView.getContext(), mListLayoutManager.getOrientation()));
        listRecyclerView.setLayoutManager(mListLayoutManager);
        mHistoryAdapter = new SendHistoryAdapter(transactions, contacts);
        listRecyclerView.setAdapter(mHistoryAdapter);
    }

    private void initDataset() {
        FriendRepositories.getInMemoryRepo().getAllFriends(new FriendsRepository.FriendsServiceCallback<List<Friend>>() {
            @Override
            public void onLoaded(List<Friend> friends) {
                contacts = friends;
            }
        });
    }

    private List<Transaction> groupTransactions(List<Transaction> list){
        HashMap<String, Transaction> transactions = new HashMap<>();
        for(Transaction t : list) {
            if(transactions.containsKey(t.getClientId())){
                Transaction temp = transactions.get(t.getClientId());
                temp.increaseValue(t.getValor());
            } else {
                transactions.put(t.getClientId(), t);
            }
        }
        return new ArrayList(transactions.values());
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

        t = new Transaction(String.valueOf(tid), "2", 30.00, "123123");
        transactions.add(t);
        tid++;

        t = new Transaction(String.valueOf(tid), "1", 45.00, "123123");
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
