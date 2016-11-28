package com.sauloaguiar.neonapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sauloaguiar.neonapplication.NeonApplication;
import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.activities.HistoryActivity;
import com.sauloaguiar.neonapplication.activities.SendMoneyActivity;

/**
 * Created by sauloaguiar on 11/24/16.
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    public static Fragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {}

    private Button sendMoneyButton;
    private Button viewHistoryButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        sendMoneyButton = (Button) root.findViewById(R.id.sendMoneyButton);
        sendMoneyButton.setOnClickListener(this);

        viewHistoryButton = (Button) root.findViewById(R.id.viewHistoryButton);
        viewHistoryButton.setOnClickListener(this);

        return root;
    }

    private boolean isConnected() {
        return ((NeonApplication) getActivity().getApplication()).isConnected();
    }

    private boolean weHaveToken(){
        return !((NeonApplication) getActivity().getApplication()).getApplicationToken().equals("");
    }

     @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.sendMoneyButton:
                if(isConnected() || weHaveToken()) {
                    i = new Intent(getContext(), SendMoneyActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getContext(), "No Internet Connection!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.viewHistoryButton:
                if (isConnected() || weHaveToken()) {
                    i = new Intent(getContext(), HistoryActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getContext(), "No Internet Connection!", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }
}
