package com.sauloaguiar.neonapplication.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sauloaguiar.neonapplication.NeonApplication;
import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.data.Friend;
import com.sauloaguiar.neonapplication.data.FriendRepositories;
import com.sauloaguiar.neonapplication.data.FriendsRepository;
import com.sauloaguiar.neonapplication.network.NeonEndpoint;

import java.text.NumberFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sauloaguiar on 11/24/16.
 */

public class SendMoneyDialogFragment extends DialogFragment implements View.OnClickListener{

    private NeonEndpoint.NeonEndpointInterface endpoint;
    private Button sendButton;
    private EditText sendMoneyText;
    private int position;
    private Friend friend;
    private Dialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_send_money, container, false);
        makeDialogTransparent();
        endpoint = NeonEndpoint.getEndpoint();

        position = getArguments().getInt("position");

        FriendRepositories.getInMemoryRepo().getFriend(String.valueOf(position), new FriendsRepository.FriendsServiceCallback<Friend>() {
            @Override
            public void onLoaded(Friend friends) {
                friend = friends;
                ((CircleImageView) view.findViewById(R.id.friendPhoto)).setImageResource(friends.getImageResource());
                ((TextView) view.findViewById(R.id.name)).setText(friends.getName());
                ((TextView) view.findViewById(R.id.phone)).setText(friends.getPhone());
            }
        });
        sendMoneyText = (EditText)view.findViewById(R.id.moneyEditText);
        sendMoneyText.addTextChangedListener(new MoneyMask(sendMoneyText));

        sendButton = (Button) view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);

        return view;
    }

    private void makeDialogTransparent() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private double getDoubleFromString(String str) {
        double value = 0;
        try {
            boolean hasMask =
                    ((str.indexOf("R$") > -1 ||
                    str.indexOf("$") > -1) && (str.indexOf(".") > -1 ||
                    str.indexOf(",") > -1));

            // Checking for an existent mask in the text
            if (hasMask) {
                // Remove the mask
                str = str.replaceAll("[R$]", "").replaceAll("\\,\\w+", "")
                        .replaceAll("\\.\\w+", "");
            }

            // Parse to double
            value = Double.parseDouble(str);
        } catch (NumberFormatException e) {}
        return value;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendButton:
                progressDialog = new Dialog(getContext(), android.R.style.Theme_Translucent);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.setContentView(R.layout.neon_progress_dialog);
                progressDialog.setCancelable(true);
                progressDialog.show();

                double value = getDoubleFromString(sendMoneyText.getText().toString());
                endpoint.sendMoney(friend.getStringId(), getApplicationToken(), value).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext(), getString(R.string.money_sent_message), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), getString(R.string.money_sent_error), Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }
                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {}
                });
                break;

            default:
                break;
        }
    }

    public String getApplicationToken() {
        return ((NeonApplication)getActivity().getApplication()).getApplicationToken();
    }

    private class MoneyMask implements TextWatcher {
        final EditText field;

        public MoneyMask(EditText field) {
            super();
            this.field = field;
        }

        private boolean isUpdating = false;

        // Getting Brazilian Currency
        private NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int after) {
            // avoid the method to being executed if not necessary
            if (isUpdating) {
                isUpdating = false;
                return;
            }

            isUpdating = true;
            String str = s.toString();

            // Checking for an existent mask in the text
            boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) &&
                    (str.indexOf(".") > -1 || str.indexOf(",") > -1));


            if (hasMask) {
                // Remove the mask
                str = str.replaceAll("[R$]", "").replaceAll("[,]", "")
                        .replaceAll("[.]", "");
            }
            try {

                // Apply the number format
                str = nf.format(Double.parseDouble(str) / 100);
                field.setText(str);
                field.setSelection(field.getText().length());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    }
}

