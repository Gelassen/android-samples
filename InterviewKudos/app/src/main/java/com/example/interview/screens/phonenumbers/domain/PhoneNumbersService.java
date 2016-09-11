package com.example.interview.screens.phonenumbers.domain;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.interview.AppResultReceiver;
import com.example.interview.R;
import com.example.interview.model.TelNumber;
import com.example.interview.network.api.GetNumbersCommand;

/**
 * The intent of this class is encapsulate biz logic for tel numbers screen. In
 * case of requirement growth, this single class should be redesign to avoid God-object
 * anti-pattern
 *
 * Created by John on 9/11/2016.
 */
public class PhoneNumbersService {

    public void onPhoneNumberClick(Context context, TelNumber telNumber) {
        if (context == null || telNumber == null || TextUtils.isEmpty(telNumber.getPhoneNumberOwner())) return;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { telNumber.getPhoneNumberOwner() });
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.phone_number_email_title));
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.phone_number_email_subj));
        context.startActivity(Intent.createChooser(intent, ""));
    }

    public boolean onFirstRun(Context context, AppResultReceiver appResultReceiver) {
//        if (!Storage.isFirstRun(context)) return false;

        new GetNumbersCommand().start(context, appResultReceiver);

        return true;
    }
}
