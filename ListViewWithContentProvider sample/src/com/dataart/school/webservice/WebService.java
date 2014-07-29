package com.dataart.school.webservice;

import android.app.IntentService;
import android.content.Intent;

public class WebService extends IntentService {

    public static final String COMMAND = "command";
    
    public WebService() {
        super(WebService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HttpCommand command = (HttpCommand) intent.getSerializableExtra(COMMAND);
        command.setContext(this);
        command.execute();
    }

}
