package com.home.xing.network.parsers;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by Gleichmut on 9/4/2014.
 */
public abstract class Parser {

    protected Context ctx;

    public abstract void parse(JsonReader reader) throws IOException;

    public void setContext(Context ctx) {
        this.ctx = ctx;
    }
}
