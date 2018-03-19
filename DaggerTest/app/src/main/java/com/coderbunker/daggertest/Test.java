package com.coderbunker.daggertest;


import com.coderbunker.daggertest.dto.TestData;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static List<TestData> generateStubData() {
        List<TestData> result = new ArrayList<>();

        addRow("Shanghai", result);
        addRow("Moscow", result);
        addRow("Amsterdam", result);
        addRow("Berlin", result);
        addRow("London", result);
        addRow("Singapore", result);
        addRow("Kuala Lumpur", result);

        return result;
    }

    private static void addRow(String msg, List<TestData> result) {
        TestData testData = new TestData(msg);
        result.add(testData);
    }
 }
