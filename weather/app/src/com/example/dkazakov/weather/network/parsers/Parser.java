package com.example.dkazakov.weather.network.parsers;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;

public abstract class Parser {

    protected Context ctx;

    public abstract void parse(JsonReader reader) throws IOException;

    public void setContext(Context ctx) {
        this.ctx = ctx;
    }
}
