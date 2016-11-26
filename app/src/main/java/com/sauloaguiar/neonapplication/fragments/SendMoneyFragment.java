package com.sauloaguiar.neonapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.adapters.SendMoneyContactAdapter;
import com.sauloaguiar.neonapplication.data.Friend;
import com.sauloaguiar.neonapplication.data.FriendRepositories;
import com.sauloaguiar.neonapplication.data.FriendsRepository;

import java.util.List;

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
    //protected String[] contacts;
    List<Friend> contacts = null;
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
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation()));
        return rootView;
    }

    private void initDataset() {
        FriendRepositories.getInMemoryRepo().getAllFriends(new FriendsRepository.FriendsServiceCallback<List<Friend>>() {
            @Override
            public void onLoaded(List<Friend> friends) {
                contacts = friends;
            }
        });
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

