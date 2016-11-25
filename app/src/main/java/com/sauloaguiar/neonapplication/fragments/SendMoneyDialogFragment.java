package com.sauloaguiar.neonapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sauloaguiar.neonapplication.R;

/**
 * Created by sauloaguiar on 11/24/16.
 */

public class SendMoneyDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_send_money, container, false);

        int position = getArguments().getInt("position");
        Toast.makeText(getActivity(), "Element " + position + " clicked.", Toast.LENGTH_SHORT).show();
        return view;
    }
}

