package com.home.template;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.home.template.test.Word;
import com.home.template.test.WordDao;
import com.home.template.test.WordRoomDatabase;

public class NewEntryActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WordDao dao = WordRoomDatabase.getDatabase(this).wordDao();
        dao.insert(new Word("str"));
    }
}
