package com.sauloaguiar.neonapplication.fragments;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.Toast;

import com.sauloaguiar.neonapplication.NeonApplication;
import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.adapters.GraphHistoryAdapter;
import com.sauloaguiar.neonapplication.adapters.SendHistoryAdapter;
import com.sauloaguiar.neonapplication.data.Friend;
import com.sauloaguiar.neonapplication.data.FriendRepositories;
import com.sauloaguiar.neonapplication.data.FriendsRepository;
import com.sauloaguiar.neonapplication.data.Transaction;
import com.sauloaguiar.neonapplication.network.NeonEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sauloaguiar on 11/26/16.
 */

public class HistoryFragment extends Fragment {

    private List<Friend> contacts;
    private List<Transaction> transactions;

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

    private View separator;
    private NeonEndpoint.NeonEndpointInterface endpointInterface;
    private NeonApplication application;
    private Dialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_money, container, false);
        endpointInterface = NeonEndpoint.getEndpoint();
        application = NeonApplication.getInstance();
        showLoadingDialog();
        loadFriendsDataset();
        loadTransactions();
        return rootView;
    }

    private void showLoadingDialog(){
        progressDialog = new Dialog(getContext(), android.R.style.Theme_Translucent);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.neon_progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void loadTransactions() {
        endpointInterface.getTransactions(getApplicationToken()).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    transactions = response.body();
                    drawScreen(transactions);
                } else {
                    Toast.makeText(getContext(), R.string.loading_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) { }
        });
    }

    private String getApplicationToken() {
        return application.getApplicationToken();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        separator = view.findViewById(R.id.separator);
        separator.setVisibility(View.INVISIBLE);

        graphRecyclerView = (RecyclerView) view.findViewById(R.id.graphRecyclerView);
        mGraphLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false);
        graphRecyclerView.setLayoutManager(mGraphLayoutManager);
        graphRecyclerView.setVisibility(View.INVISIBLE);

        listRecyclerView = (RecyclerView) view.findViewById(R.id.friendListRecyclerView);
        mListLayoutManager = new LinearLayoutManager(getActivity());
        listRecyclerView.addItemDecoration(new DividerItemDecoration(listRecyclerView.getContext(), mListLayoutManager.getOrientation()));
        listRecyclerView.setLayoutManager(mListLayoutManager);

    }

    private void drawScreen(final List<Transaction> transactions){
        final List<Transaction> groupedTransactions = groupTransactions(transactions);

        // show separator
        separator.setVisibility(View.VISIBLE);

        // show graph
        graphRecyclerView.setVisibility(View.VISIBLE);
        graphRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                int height = graphRecyclerView.getHeight() - (int) getResources().getDimension(R.dimen.graph_friend_photo) - (2 * (int) getResources().getDimension(R.dimen.grid_line_space));
                mGraphAdapter = new GraphHistoryAdapter(groupedTransactions, contacts, height);
                graphRecyclerView.setAdapter(mGraphAdapter);
            }
        });

        mHistoryAdapter = new SendHistoryAdapter(transactions, contacts);
        listRecyclerView.setAdapter(mHistoryAdapter);

    }
    private void loadFriendsDataset() {
        FriendRepositories.getInMemoryRepo().getAllFriends(new FriendsRepository.FriendsServiceCallback<List<Friend>>() {
            @Override
            public void onLoaded(List<Friend> friends) {
                contacts = friends;
            }
        });
    }

    private List<Transaction> groupTransactions(List<Transaction> list){
        HashMap<String, Transaction> transactions = new HashMap<>();
        List<Transaction> copy = new ArrayList<>(list);
        for(Transaction t : copy) {
            if(transactions.containsKey(t.getClientId())){
                Transaction temp = transactions.get(t.getClientId());
                temp.increaseValue(t.getValor());
            } else {
                Transaction newReference = new Transaction(t.getId(), t.getClientId(), t.getValor(), t.getData());
                transactions.put(newReference.getClientId(), newReference);
            }
        }
        return new ArrayList(transactions.values());
    }


    /**
     * Fake data for testing before integrating to the rest api
     * @return
     */
    private List<Transaction> generateTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        int tid = 1;
        String userid = "1";
        Transaction t = new Transaction(String.valueOf(tid), userid, 15.00, "123123");
        transactions.add(t);
        tid++;

        t = new Transaction(String.valueOf(tid), "2", 30.50, "123123");
        transactions.add(t);
        tid++;

        t = new Transaction(String.valueOf(tid), "1", 45.10, "123123");
        transactions.add(t);
        tid++;

        t = new Transaction(String.valueOf(tid), "2", 30.05, "123123");
        transactions.add(t);
        tid++;

        t = new Transaction(String.valueOf(tid), "1", 45.00, "123123");
        transactions.add(t);
        tid++;

        t = new Transaction(String.valueOf(tid), "2", 30.50, "123123");
        transactions.add(t);
        tid++;

        t = new Transaction(String.valueOf(tid), "1", 45.00, "123123");
        transactions.add(t);
        tid++;

        return transactions;
    }
}
