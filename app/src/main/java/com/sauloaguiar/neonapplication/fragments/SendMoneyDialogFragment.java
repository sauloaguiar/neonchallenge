package com.sauloaguiar.neonapplication.fragments;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sauloaguiar.neonapplication.R;
import com.sauloaguiar.neonapplication.data.Friend;
import com.sauloaguiar.neonapplication.data.FriendRepositories;
import com.sauloaguiar.neonapplication.data.FriendsRepository;

import java.text.NumberFormat;
import java.util.Locale;

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
        EditText sendMoneyText = (EditText)view.findViewById(R.id.moneyEditText);
        sendMoneyText.addTextChangedListener(new MoneyMask(sendMoneyText));

        return view;
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

