package com.example.interview.screens.newnumber;

import android.os.Bundle;
import android.view.View;

import com.example.interview.BaseActivity;
import com.example.interview.R;

/**
 * Created by John on 9/11/2016.
 */
public class NewPhoneActivity extends BaseActivity implements NewPhoneService.Callbacks{

    private NewPhonePresenter presenter;
    private NewPhoneService newPhoneService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newPhoneService = new NewPhoneService();
        newPhoneService.serListener(this);
        presenter = new NewPhonePresenter(findViewById(R.id.root), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter.isValid()) {
                    newPhoneService.saveNewPhone(NewPhoneActivity.this, presenter.getTelNumber());
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_phone;
    }

    @Override
    public void onFinishSave() {
        finish();
    }
}
