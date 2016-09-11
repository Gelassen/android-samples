package com.example.interview.screens.newnumber;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.interview.R;
import com.example.interview.model.TelNumber;

/**
 * Created by John on 9/11/2016.
 */
public class NewPhonePresenter {
    private EditText phoneNumber;
    private EditText phonePrice;
    private EditText phoneOwner;

    private Button button;

    public NewPhonePresenter(View view, View.OnClickListener clickListener) {
        phoneNumber = (EditText) view.findViewById(R.id.phone_number);
        phoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                textView.setError(null);
                return true;
            }
        });
        phonePrice = (EditText) view.findViewById(R.id.phone_price);
        phonePrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                textView.setError(null);
                return true;
            }
        });
        phoneOwner = (EditText) view.findViewById(R.id.phone_owner);
        phoneOwner.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                textView.setError(null);
                return true;
            }
        });
        button = (Button) view.findViewById(R.id.add_new_phone);
        button.setOnClickListener(clickListener);
    }

    public boolean isValid() {
        // TODO later add validation for email and digits
        boolean result = true;
        if (TextUtils.isEmpty(phoneNumber.getText())) {
            result = false;
            phoneNumber.setError("Should not be empty");
        }
        if (TextUtils.isEmpty(phonePrice.getText())) {
            result = false;
            phonePrice.setError("Should not be empty");
        }
        if (TextUtils.isEmpty(phoneOwner.getText())) {
            result = false;
            phoneOwner.setError("Should not be empty");
        }

        return result;
    }

    public TelNumber getTelNumber() {
        TelNumber telNumber = new TelNumber();
        telNumber.setPhoneNumber(phoneNumber.getText().toString());
        telNumber.setPhoneNumberPrice(phoneNumber.getText().toString());
        telNumber.setPhoneNumberOwner(phoneOwner.getText().toString());
        return telNumber;
    }
}
