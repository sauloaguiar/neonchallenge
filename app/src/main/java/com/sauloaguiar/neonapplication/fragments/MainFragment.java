package com.sauloaguiar.neonapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.activities.SendMoneyActivity;

/**
 * Created by sauloaguiar on 11/24/16.
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    public static Fragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        root.findViewById(R.id.sendMoneyButton).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendMoneyButton:
                Intent i = new Intent(getContext(), SendMoneyActivity.class);
                startActivity(i);
                break;


            default:
                break;
        }
    }
}
