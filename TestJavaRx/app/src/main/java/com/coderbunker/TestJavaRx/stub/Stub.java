package com.coderbunker.TestJavaRx.stub;


import java.util.ArrayList;
import java.util.List;

public class Stub {

    private static List<String> data = new ArrayList<>();

    static {
        data.add("Moscow");
        data.add("Shanghai");
        data.add("St. Petersburg");
        data.add("Berlin");
        data.add("Amsterdam");
        data.add("Singapore");
    }

    public static List<String> getData() {
        return data;
    }
}
