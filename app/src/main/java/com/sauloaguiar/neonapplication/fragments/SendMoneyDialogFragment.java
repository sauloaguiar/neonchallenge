package com.sauloaguiar.neonapplication.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.data.Friend;
import com.sauloaguiar.neonapplication.data.FriendRepositories;
import com.sauloaguiar.neonapplication.data.FriendsRepository;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sauloaguiar on 11/24/16.
 */

public class SendMoneyDialogFragment extends DialogFragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_send_money, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int position = getArguments().getInt("position");
        Toast.makeText(getActivity(), "Element " + position + " clicked.", Toast.LENGTH_SHORT).show();

        FriendRepositories.getInMemoryRepo().getFriend(String.valueOf(position), new FriendsRepository.FriendsServiceCallback<Friend>() {
            @Override
            public void onLoaded(Friend friends) {
                ((CircleImageView) view.findViewById(R.id.photo)).setImageResource(friends.getImageResource());
                ((TextView) view.findViewById(R.id.name)).setText(friends.getName());
                ((TextView) view.findViewById(R.id.phone)).setText(friends.getPhone());
            }
        });

        return view;
    }
}

