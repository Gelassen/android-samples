package com.example.interview.screens.phonenumbers;

import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.interview.R;

/**
 * The intent of this class is encapsulate details of UI implementation and provide
 * convenient API to interact with UI in terms of business rules
 *
 * Created by John on 9/11/2016.
 */
public class PhoneNumbersPresenter {
    private RecyclerView recyclerView;
    private PhoneNumbersAdapter adapter;

    public PhoneNumbersPresenter(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        adapter = new PhoneNumbersAdapter(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    public void updatePhoneNumbers(Cursor cursor) {
        adapter.updateModel(cursor);
    }
}
