package com.sauloaguiar.neonapplication.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sauloaguiar.neonapplication.NeonApplication;
import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.fragments.MainFragment;
import com.sauloaguiar.neonapplication.network.NeonEndpoint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NeonEndpoint.NeonEndpointInterface endpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(null == savedInstanceState) {
            initFragment(MainFragment.newInstance());
        }



        // load token
        if(!weHaveToken()) {
            retrieveToken();
        }

    }

    private boolean weHaveToken(){
        return !((NeonApplication)getApplication()).getApplicationToken().equals("");
    }

    private void saveToken(String token) {
        ((NeonApplication)getApplication()).saveApplicationToken(token);
    }

    private void retrieveToken() {
        endpoint = NeonEndpoint.getEndpoint();
        endpoint.getToken(getString(R.string.username), getString(R.string.email)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "response: " + response.body(), Toast.LENGTH_SHORT).show();
                    String token = response.body();
                    saveToken(token);
                } else {
                    Toast.makeText(getApplicationContext(), "problem: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, fragment);
        transaction.commit();
    }
}
